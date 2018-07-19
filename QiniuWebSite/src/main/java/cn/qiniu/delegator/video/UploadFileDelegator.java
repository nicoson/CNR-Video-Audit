package cn.qiniu.delegator.video;

import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.videoReview.VideoSubmit;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;


/**
 * 文件上传
 * @author lqf
 *
 */
@Component
public class UploadFileDelegator {
	
	private static final String VIDEO_SUBMIT = "/v1/jobs";
	
	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	@Autowired
	private String accessKey;
	
	// SecretKey
	@Autowired
	private String secretKey;
	
	// Bucket
	@Autowired
	private String bucket;
	
	@Autowired
	private String videoReviewUrl;
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	
	/**
     * 拼接videoReview地址
     *
     * @return
     */
	private String getVideoReviewUrl(String url) {
		return videoReviewUrl + url;
	}
	
	public  String  getUploadToken(){
		// 生成鉴权
		Auth auth = Auth.create(accessKey, secretKey);
		//获取上传 凭证
		String upLoadToken =auth.uploadToken(bucket);
		return upLoadToken;
	}
	
	/**
	 * 提交 上传的视频
	 * @return
	 */
	public String  submitVideo(VideoSubmit record){
		String result = "";
		try {
			String uri = getVideoReviewUrl(VIDEO_SUBMIT);
			byte[] body = JSONObject.toJSONString(record).getBytes();
			ContentType contentType= ContentType.APPLICATION_JSON;
		    StringMap authorization = commonDelegator.authorizationV2(uri, "POST", body, contentType.toString());
		    Map<String, String> header = (Map) authorization.map();
		    
			StringEntity httpEntity = new StringEntity(JSON.toJSONString(record), contentType);
			//提交视频
			result = httpClientTemplate.doPost(uri, contentType, httpEntity,JSONObject.toJSONString(record), header, true);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
}
