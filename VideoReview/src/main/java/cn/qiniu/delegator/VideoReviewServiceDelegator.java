package cn.qiniu.delegator;

import java.util.Map;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.entity.videoReview.VideoSubmit;
import cn.qiniu.framework.httpclient.HttpClientTemplate;
import cn.qiniu.util.common.StringUtil;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

/**
 * 视频审核服务Delegator
 * 
 * @author Chen Hua
 * @version 2017-09-26
 */
@Component
public class VideoReviewServiceDelegator {
	// 点播视频提交处理URI
	private static final String V1_CREATR_VIDEO_INFO = "/v1/createVideoInfo";
	
	// 直播视频提交处理URI
	private static final String V1_CREATR_LIVE_VIDEO_INFO = "/v1/createLiveVideoInfo";
	
	// 点播视频分析结果回调
	private static final String V1_CALLBACK_VIDEO_RESULT = "/v1/callbackVideoResult";

	// 点播视频分析结果回调
	private static final String V1_CHECK_VIDEO_ID = "/v1/check/video";

	// 请求类型-JSON
	private static final String ContentType_JSON = "application/json";
	
	//获取用户提交的视频url
	private static final String GET_RESULT_CALLBACK_URL = "/get/result/callback/url";
	
	//直播视频帧回调
	private static final String V1_CUTS_CALLBACK = "/v1/cuts/callback";
	
	//直播视频片段回调
	private static final String V1_SEGMENT_CALLBACK = "/v1/segment/callback";
		
	@Autowired
	private QiniuApiDelegator qiniuApiDelegator;
	
	// 七牛API URI
	@Autowired
	private String videoReviewServiceUrl;
	
	// Http Client
	@Autowired
	private HttpClientTemplate httpClientTemplate; 
	//取视频某一秒为视频封面
	@Autowired
	private String videoCover;
	
	/**
	 * 提交视频
	 * @param record
	 * @param jobId
	 * @return
	 */
	public String createVideoInfo(VideoSubmit record,String jobId,String videoTime){
		//获取归档地址
		String videoSave = record.getParams().getSave();
		//api提交视频，视频地址就是归档地址
		if(StringUtil.isNullOrEmpty(videoSave)){
			videoSave = record.getData().getUri();
		}
		//截取归档地址获取文件名
		String fileName = videoSave.substring(videoSave.lastIndexOf("/")+1);
		//设置图片名称
		int mp4Size = fileName.indexOf(".");
		String targetName = fileName.substring(0,mp4Size)+"_C.jpg";
		//获取视频封面,截取视频第十帧
		qiniuApiDelegator.getVideoPic(fileName,targetName,videoCover);
		
		int bucketSize = videoSave.lastIndexOf("/")+1;
		String videoCover = videoSave.substring(0, bucketSize)+targetName;
		
		String url = videoReviewServiceUrl + V1_CREATR_VIDEO_INFO;
		JSONObject jsonData= new JSONObject();
		jsonData.put("jobId", jobId);
		jsonData.put("submitInfo", record);
		jsonData.put("videoCover", videoCover);
		jsonData.put("videoTime", videoTime);
		String json = jsonData.toJSONString();
		ContentType contentType = ContentType.create(ContentType_JSON);
		String result = httpClientTemplate.doPost(url, json, contentType);
		return result;
	}
	
	/**
	 * 提交直播视频
	 * @param record
	 * @param jobId
	 * @return
	 */
	public String createLiveVideoInfo(JSONObject jsonObject,JSONObject obj){
		String url = videoReviewServiceUrl + V1_CREATR_LIVE_VIDEO_INFO;
		String jobId = obj.getString("id");
		//归档地址
		String rtmp = obj.getString("hls");
		String data = jsonObject.getString("data");
		JSONObject dataJson = JSONObject.parseObject(data);
		String attribute = dataJson.getString("attribute");
		JSONObject attributeJson = JSONObject.parseObject(attribute);
		String desc = attributeJson.getString("desc");
		
		jsonObject.put("jobId", jobId);
		jsonObject.put("rtmp", rtmp);

		jsonObject.put("desc", desc);
		String result = "";
		String json = jsonObject.toJSONString();
//		ContentType contentType = ContentType.create(ContentType_JSON);
		try {
//			result = httpClientTemplate.doPost(url, json, contentType);
			result = httpClientTemplate.doPost(url, jsonObject);
		} catch (Exception e) {
			throw e;
		}
		
		return result;
	}
	
	/**
	 * 审核完成回调
	 * 如过返回归档地址不为空，则表示审核任务类型为机审，并且审核通过
	 * @param json
	 */
	public String callbackVideoResult(String json){
		String url = videoReviewServiceUrl + V1_CALLBACK_VIDEO_RESULT;
		String callbackUrl = videoReviewServiceUrl + GET_RESULT_CALLBACK_URL;
		ContentType contentType = ContentType.create(ContentType_JSON);
		String saveUrl = httpClientTemplate.doPost(url, json, contentType);
		
		//判断归档地址是否为空
		if(!StringUtil.isNullOrEmpty(saveUrl)){
			String resultCallbackUrl = httpClientTemplate.doPost(callbackUrl,json,contentType);
		}
		return saveUrl;
	}

	/**
	 * 审核完成回调
	 * 如过返回归档地址不为空，则表示审核任务类型为机审，并且审核通过
	 * @param json
	 */
	public boolean checkVideoId(String videoId){
		String url = videoReviewServiceUrl + V1_CHECK_VIDEO_ID + "/" + videoId;
		String returnJson = httpClientTemplate.doGet(url);
		JSONObject jsonObject = JSONObject.parseObject(returnJson);
		return jsonObject.getBooleanValue("success");
	}
	
	/**
	 * 直播视频分析-截帧回调
	 * @param id	相关任务的id  jobID
	 * @param url	图片地址，可以直接获取得到
	 * @param timestamp	图片帧的绝对时间戳
	 * @param reqid	本次回调记录的ID号
	 * @return
	 */
	public String cutsCallback(JSONObject jsonObject){
		// 数据入库
		return httpClientTemplate.doPost(videoReviewServiceUrl+V1_CUTS_CALLBACK, jsonObject);
	}
	
	/**
	 * 直播分析-片段返回
	 * @param id	jobId
	 * @param url	片段的播放地址
	 * @param segmentId	片段id
	 * @return
	 */
	public String segmentCallback(String id,String url,String segmentId){
		Map<String,Object> params = Maps.newHashMap();
		params.put("id", id);
		params.put("url", url);
		params.put("segmentId", segmentId);
		return httpClientTemplate.doPost(videoReviewServiceUrl+V1_SEGMENT_CALLBACK, params);
	}
}
