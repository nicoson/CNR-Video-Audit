package cn.qiniu.controller.common;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;

import cn.qiniu.entity.SysUser;
import cn.qiniu.entity.basic.Response;

/***
 * BaseAction
 * 
 * @author lvweijun
 * @version V1.00 2016-10-18
 */
public class BaseController {
	protected static Logger log = Logger.getLogger(BaseController.class);
    /**
     * 获取用户信息
     */
	public SysUser getLoginUser() {
		Object userObj = SecurityUtils.getSubject().getSession().getAttribute("user");
		if (userObj != null) {
			SysUser user = (SysUser)userObj;
			return user;
		}
		return null;
	}
	
	public String getJson(boolean success, String msg, Object data, boolean... needConvert) {
		Response result = new Response();
		result.setSuccess(success);
		result.setErrorMsg(msg);
		result.setData(data);
		//返回信息
		return com.alibaba.fastjson.JSON.toJSONString(result);
	}
}
