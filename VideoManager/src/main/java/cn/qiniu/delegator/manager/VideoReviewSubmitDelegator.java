package cn.qiniu.delegator.manager;

import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.StringMap;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.videoReview.VideoSubmit;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

@Component
public class VideoReviewSubmitDelegator {
	
	private static final String V1_JOBS = "/v1/jobs";
	
	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	@Autowired
	private String videoManagerServiceUri;
	
	
	
	/**
     * 拼接https地址
     *
     * @return
     */
	private String getManagerService(String url) {
		return videoManagerServiceUri + url;
	}
	
	
	/**
	 * 提交审核视频
	 * @param 
	 * @return
	 */
	public String submitVideo(VideoSubmit record){
		String jobId = "";
		try {
			String uri = getManagerService(V1_JOBS);
			byte[] body = JSONObject.toJSONString(record).getBytes();
		    StringMap authorization = commonDelegator.authorizationV2(uri, "POST", body, "application/json");
		    Map<String, String> header = (Map) authorization.map();
		    ContentType contentType= ContentType.create("application/json");
			StringEntity httpEntity = new StringEntity(JSON.toJSONString(record), contentType);
			//取得审核列表信息
			jobId = httpClientTemplate.doPost(uri, contentType, httpEntity,JSONObject.toJSONString(record), header, true);
		} catch (Exception e) {
			e.toString();
			return null;
		}
		return jobId;
	}
	
}
