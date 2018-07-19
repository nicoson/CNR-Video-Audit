package cn.qiniu.controller.common;

import java.util.Map;

import cn.qiniu.entity.basic.Response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/***
 * BaseController
 * 
 * @author chen hua
 * @version 2017-09-28
 */
public class BaseController {
    
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
}
