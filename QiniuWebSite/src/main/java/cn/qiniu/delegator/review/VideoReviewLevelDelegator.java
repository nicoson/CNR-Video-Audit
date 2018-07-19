package cn.qiniu.delegator.review;

import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.qiniu.util.StringMap;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.ReviewLevelSetting;
import cn.qiniu.entity.levelSetting.LevelSettingOps;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

@Component
public class VideoReviewLevelDelegator {
	//获取视频审核配置
	private static final String GET_LEVEL = "/v1/con2";
	//设置视频审核配置
	private static final String SET_LEVEL = "/v1/con2";
	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	@Autowired
	private String videoManagerUri; 
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	/**
     * 拼接https地址
     *
     * @return
     */
	private String getVideoReviewLevelUrl(String url) {
		return videoManagerUri + url;
	}
	
	/**
	 * 设置视频审核配置
	 */
	public int setReviewVideoLevel(LevelSettingOps ops) {
		String response = "";
		String uri = getVideoReviewLevelUrl(SET_LEVEL);
		
		byte[] body = JSONObject.toJSONString(ops).getBytes();
	    StringMap authorization = commonDelegator.authorizationV2(uri, "POST", body, "application/json");
	    Map<String, String> header = (Map) authorization.map();
	    ContentType contentType= ContentType.create("application/json");
		StringEntity httpEntity = new StringEntity(JSON.toJSONString(ops), contentType);
		// 取得审核列表信息
		response = httpClientTemplate.doPost(uri, contentType, httpEntity,JSONObject.toJSONString(ops), header, true);
		int i = Integer.parseInt(response);
		return i;
	}
	
	/**
	 * 获取视频审核配置
	 */
	public List<ReviewLevelSetting> getReviewVideoLevel() {
		Map<String,String> parameters = Maps.newHashMap();
		String response = "";
		
		String url = getVideoReviewLevelUrl(GET_LEVEL);
		ContentType contentType = ContentType.create("application/x-www-form-urlencoded", Consts.UTF_8);
	    StringMap authorization = commonDelegator.authorizationV2(url, "GET", null, contentType.toString());
	    Map<String, String> header = (Map) authorization.map();
	    // 取得审核列表信息
	    response = httpClientTemplate.doGet(url, parameters, header);
		
		List<ReviewLevelSetting> list = commonDelegator.parseArray(response, ReviewLevelSetting.class);
		
		return list;
	}
}
