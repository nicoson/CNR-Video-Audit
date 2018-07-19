package cn.qiniu.delegator;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.entity.SysResource;
import cn.qiniu.form.SysResourceForm;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

import com.google.common.collect.Maps;

@Component
public class SysResourceDelegator {

	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	@Autowired
	private String qiniuDataUrl;
	
	private static final String TB_LIST = "/sysResourceList";
	//取得员工权限非管理员
	private static final String GET_USER_RES = "/getUserResList";
	
	/**
     * 拼接https地址
     *
     * @return
     */
	private String getQiniuData(String url) {
		return qiniuDataUrl + url;
	}
    
    /**
	 *	列表
	 */
    public List<SysResource> tbList(SysResourceForm form) {	
    	List<SysResource> result = null;
		Map<String,Object> params = Maps.newHashMap();
		//设置参数:条件
		params.put("search", form.getSearch());
		try {
			//取得用户信息
			String response = httpClientTemplate.doPost(getQiniuData(TB_LIST), params);
			result = commonDelegator.parseObjectList(response, SysResource.class);
		} catch (Exception e) {
			
		}
		return result;
    }
    
	/**
	 * 取得用户菜单 非管理员
	 * @param userId 用户Id
	 * @return List<OpResource> 用户菜单
	 */
	public List<SysResource> getUserMenuList(SysResourceForm form) {
		//定义返回结果
		List<SysResource> result = null;
		Map<String, Object> params = Maps.newHashMap();
		//设置参数:用户ID
		params.put("search", form.getSearch());
		
		try {
			//取得用户信息
			String response = httpClientTemplate.doPost(getQiniuData(GET_USER_RES), params);
			//定义结果集
			result = commonDelegator.parseObjectList(response, SysResource.class);
		} catch (Exception e) {
			return null;
		}
		return result;
	}
}
