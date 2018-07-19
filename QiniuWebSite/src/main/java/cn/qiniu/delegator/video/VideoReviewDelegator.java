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
import com.qiniu.util.StringMap;

@Component
public class VideoReviewDelegator {
	private static final String VIDEO_SUBMIT = "/v1/jobs";
	
	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
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
	
	/**
     * 提交审核申请
     *
     * @return job id
     */
	public String submitVideo(VideoSubmit record) {
    	String result = "";
		try {
			String uri = getVideoReviewUrl(VIDEO_SUBMIT);
			ContentType contentType= ContentType.APPLICATION_JSON;
			byte[] body = JSONObject.toJSONString(record).getBytes();
		    StringMap authorization = commonDelegator.authorizationV2(uri, "POST", body, contentType.toString());
		    Map<String, String> header = (Map) authorization.map();
		    
			StringEntity httpEntity = new StringEntity(JSON.toJSONString(record), contentType);
			//提交视频
			result = httpClientTemplate.doPost(uri, contentType, httpEntity,JSONObject.toJSONString(record), header, true);
		} catch (Exception e) {
			return "";
		}
		return result;
	}    
}
