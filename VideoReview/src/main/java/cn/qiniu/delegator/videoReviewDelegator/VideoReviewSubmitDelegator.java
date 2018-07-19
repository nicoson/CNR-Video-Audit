package cn.qiniu.delegator.videoReviewDelegator;

import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.delegator.QiniuApiDelegator;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.videoReview.VideoSubmit;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReview;
import cn.qiniu.framework.httpclient.HttpClientTemplate;
import cn.qiniu.util.common.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

@Component
public class VideoReviewSubmitDelegator {
	@Autowired
	private String accessKey;
	@Autowired
	private String secretKey;
	@Autowired
	private String bucket;
	
	//提交审核视频输入入库uri
	private static final String V1_JOBS = "/v1/jobs";
	
	//提交识别服务拼接uri
	private static final String V1_VIDEO = "/v1/video";
	
	//将识别服务返回的jobId入库
	private static final String JOBID_SUBMIT = "/jobId/submit";
	
	//获取用户提交的片段url
	private static final String GET_SEGMENT_CALLBACK_URL = "/get/sgement/callback/url";
	
	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	@Autowired
	private String atlabArgusUri;
	
	@Autowired
	private String videoManagerUrl;
	
	@Autowired
	private String videoReviewServiceUrl;
	
	@Autowired
	private QiniuApiDelegator qiniuApiDelegator;
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	/**
	 * 拼接videoManager地址
	 * @param url
	 * @return
	 */
	private String getManagerUrl(String url){
		return videoManagerUrl+url;
	}
	
	/**
	 * 拼接videoReviewService地址
	 */
	private String getvideoReviewServiceUrl(String url){
		return videoReviewServiceUrl+url;
	}
	
	/**
	 * 提交审核视频-数据入库
	 */
	public int submitVideo(VideoSubmit record){
		//数据入库
		String response = httpClientTemplate.doPost(getManagerUrl(V1_JOBS),record);
		int count = Integer.parseInt(response);
		return count;
	}
	
	/**
	 * 提交审核视频-提交识别
	 */
	public String submitToQiniu(QiniuReview record,String videoId){
		String result = "";
		try {
			byte[] body = JSON.toJSONString(record).getBytes();
			String url = atlabArgusUri+ V1_VIDEO + "/" + videoId;
			
			//实体类转byte数据
			StringMap authorization = authorizationV2(url,"POST",body,"application/json");
			
			Map<String, String> header = (Map) authorization.map();
			ContentType contentType= ContentType.create("application/json");
			
			StringEntity httpEntity = new StringEntity(JSON.toJSONString(record), contentType);
			
			//提交识别,返回jobId
			// TODO 提交识别uri待修改
//			String jobId = httpClientTemplate.doPost(qiniuApiUri+V1_VIDEO, JSON.toJSONString(record), contentType, header);
			String jobId = httpClientTemplate.doPost(url, contentType, httpEntity,JSON.toJSONString(record), header, true);
			
			ReviewVideoManager manager = new ReviewVideoManager();
			manager.setId(videoId);
			manager.setJobId(jobId);
			//将返回的jobId入库
			result = httpClientTemplate.doPost(getvideoReviewServiceUrl(JOBID_SUBMIT),manager);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	/**
	 * 提交审核视频-识别返回的片段信息
	 */
	public String callBackSegment(JSONObject jsonObject){
		String result = "";
		try {
			//根据videoId和op查询用户提交的回调url
			String segmentCallbackUrl = httpClientTemplate.doPost(getvideoReviewServiceUrl(GET_SEGMENT_CALLBACK_URL),jsonObject);
			
			if(!StringUtil.isNullOrEmpty(segmentCallbackUrl)){
				//片段数据提交给用户
				result = httpClientTemplate.doPost(segmentCallbackUrl,jsonObject);
			}
			
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	/**
	 * 签名
	 * @param url
	 * @param method
	 * @param body
	 * @param contentType
	 * @return
	 */
	public StringMap authorizationV2(String url, String method, byte[] body, String contentType){
		Auth me = Auth.create(accessKey, secretKey);
		StringMap authorization = me.authorizationV2(url, method, body, contentType);
		return authorization;
	}

}
