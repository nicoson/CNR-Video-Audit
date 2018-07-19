package cn.qiniu.controller.common;

import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qiniu.config.MessageConstants;
import cn.qiniu.entity.basic.Response;
import cn.qiniu.exceptions.VideoManagerException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/***
 * BaseController
 * 
 * @author chen hua
 * @version 2017-09-28
 */
public class BaseController {
	
	// AccessKey
	@Autowired
	private String accessKey;
	
	// SecretKey
	@Autowired
	private String secretKey;
    
	public String getJson(boolean success, String msg, Object data, boolean... needConvert) {
		Response result = new Response();
		result.setSuccess(success);
		result.setErrorMsg(msg);
		result.setData(data);
		//返回信息
		return JSON.toJSONString(result);
	}

	protected String getRequestParamJson(Map<String,String[]> parameterMap) {
		
		if (parameterMap == null) return "";
		JSONObject jsonObject = new JSONObject();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {  
			  jsonObject.put(entry.getKey(),entry.getValue());
		}  
		
		//返回信息
		return jsonObject.toJSONString();
	}
	protected String getRequestBodyJson(Object object) {
		//返回信息
		return JSON.toJSONString(object);
	}
	
	/**
	 * 验证签名
	 * @param path
	 * @param authorization
	 * @param method
	 * @param contentType
	 * @param body
	 * @return
	 */
	public boolean validateAuthorization(String path,String authorization,String method,String contentType,byte[] body){
		Map<String, String> map = getHeader(path,method,body,contentType);
		String str = map.get("Authorization");
		if(str.equals(authorization)){
			return true;
		}
		throw new VideoManagerException(HttpStatus.SC_UNAUTHORIZED,
				MessageConstants.AUTH_CODE,MessageConstants.AUTH_MESSAGE);
	}
	
	/**
	 * 取得Head部（包含鉴权）
	 * @param url			访问地址
	 * @param method		请求方式
	 * @param body			请求内容
	 * @param contentType	请求类型
	 * @return Head部信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, String> getHeader(String url, String method, byte[] body, String contentType){
		Auth me = Auth.create(accessKey, secretKey);
		StringMap authorization = me.authorizationV2(url, method, body, contentType);
		return (Map) authorization.map();
	}
}
