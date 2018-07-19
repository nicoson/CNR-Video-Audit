package cn.qiniu.delegator;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import cn.qiniu.framework.httpclient.HttpClientTemplate;

@Component
public class ClearVideoDelegator {

	private static final String CLEAR_VIDEO = "/v1/files/delete";
	@Autowired
	private HttpClientTemplate httpClientTemplate;
	@Autowired
	private String videoManagerUri;
	/**
	 * 拼接https地址
	 * 
	 * @return
	 */

	private String getManagerService(String url) {
		return videoManagerUri + url;
	}
	/**
	 * 
	 */
	public String ClearVideo(List<Object> list) {
		String url = getManagerService(CLEAR_VIDEO);
		Map<String,Object> params = Maps.newHashMap();
    	//设置参数:条件
    	params.put("keys", list);
		return httpClientTemplate.doPost(url, params);		
	}
}
