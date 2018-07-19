package cn.qiniu.delegator.video;

import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.delegator.entity.VideoForm;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

import com.google.common.collect.Maps;
import com.qiniu.util.StringMap;

@Component
public class VideoManagerDelegator {
	private static final String VIDEO_PAGELIST = "/v1/videos";
	private static final String VIDEO_MAXSIZE = "/v1/maxPageSize";
	@Autowired
	private HttpClientTemplate httpClientTemplate;
	@Autowired
	private String videoManagerUri;
	@Autowired
	private CommonDelegator commonDelegator;
	
	/**
	 * 拼接VideoManager 请求地址
	 * @param url
	 * @return
	 */
	private String getManagerUrl(String url){
		return videoManagerUri + url;
	}
	
    /**
	 *	视频列表(分页)
	 */
    public List<VideoForm> tbPageList(String pageNum,String pageSize,String[] reviewLevels) {
    	
    	String response = "";
    	List<VideoForm> result = null;
		String reviewLevel = reviewLevels[0];
		for (int i = 1; i < reviewLevels.length; i++) {
			reviewLevel =reviewLevel + "&reviewLevel=" + reviewLevels[i];
		}
		//拼接请求地址
		String url = getManagerUrl(VIDEO_PAGELIST)+"?pageNum="+pageNum+"&pageSize="+pageSize+"&reviewLevel="+reviewLevel;
		Map<String,String> parameters = Maps.newHashMap();
		try {
			ContentType contentType = ContentType.create("application/x-www-form-urlencoded", Consts.UTF_8);
		    StringMap authorization = commonDelegator.authorizationV2(url, "GET", null, contentType.toString());
		    Map<String, String> header = (Map) authorization.map();
		    response = httpClientTemplate.doGet(url, parameters, header);
			//取得视频信息
//			response = httpClientTemplate.doGet(url);
			result = commonDelegator.parseArray(response, VideoForm.class);
			
		} catch (Exception e) {
			return null;
		}
		return result;
    }
    
    /**
   	 *	获取视频列表最大页数
   	 */
	public String getPageMaxSize(String pageNum,String pageSize,String[] reviewLevels) {
	   	String reviewLevel = reviewLevels[0];
	   	for (int i = 1; i < reviewLevels.length; i++) {
	   		reviewLevel =reviewLevel + "&reviewLevel=" + reviewLevels[i];
	   	}
	   	//拼接请求地址
	   	String url = getManagerUrl(VIDEO_MAXSIZE)+"?pageNum="+pageNum+"&pageSize="+pageSize+"&reviewLevel="+reviewLevel;
		return httpClientTemplate.doGet(url);
	}
}
