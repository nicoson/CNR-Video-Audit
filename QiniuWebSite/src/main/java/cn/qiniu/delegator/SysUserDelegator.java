package cn.qiniu.delegator;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.entity.SysUser;
import cn.qiniu.form.SysUserForm;
import cn.qiniu.framework.httpclient.HttpClientTemplate;
import cn.qiniu.util.common.Pager;

import com.google.common.collect.Maps;

@Component
public class SysUserDelegator {

	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	@Autowired
	private String qiniuDataUrl;
	
	private static final String TB_ADD = "/sysUserAdd";
	private static final String TB_UPDATE = "/sysUserUpdate";
	private static final String TB_UPDATE_BYPK = "/sysUserUpdateByPk";
	private static final String TB_COUNT = "/sysUserCount";
	private static final String TB_LIST = "/sysUserList";
	private static final String TB_PAGELIST = "/sysUserPageList";
	
	/**
     * 拼接https地址
     *
     * @return
     */
	private String getQiniuData(String url) {
		return qiniuDataUrl + url;
	}
	
	/**
	 *	新增
	 */
    public int tbAdd(SysUserForm form) {
    	int result = 0;
		Map<String,Object> params = Maps.newHashMap();
		//设置参数:记录
		params.put("record", form.getRecord());
		try {
			String response = httpClientTemplate.doPost(getQiniuData(TB_ADD), params);
			result = Integer.parseInt(response);
		} catch (Exception e) {
			
		}
		return result;
    }
    
	/**
	 *	修改
	 */
    public int tbUpdate(SysUserForm form) {
    	int result = 0;
		Map<String,Object> params = Maps.newHashMap();
		//设置参数:条件
		params.put("search", form.getSearch());
		//设置参数:记录
		params.put("record", form.getRecord());
		try {
			String response = httpClientTemplate.doPost(getQiniuData(TB_UPDATE), params);
			result = Integer.parseInt(response);
		} catch (Exception e) {

		}
		return result;
    }
    
	/**
	 *	修改ByPk
	 */
    public int tbUpdateByPk(SysUserForm form) {
    	int result = 0;
		Map<String,Object> params = Maps.newHashMap();
		//设置参数:记录
		params.put("record", form.getRecord());
		try {
			String response = httpClientTemplate.doPost(getQiniuData(TB_UPDATE_BYPK), params);
			result = Integer.parseInt(response);
		} catch (Exception e) {

		}
		return result;
    }
    
    /**
	 *	数量
	 */
    public int tbCount(SysUserForm form) {  	
    	int result = 0;
		Map<String,Object> params = Maps.newHashMap();
		//设置参数:条件
		params.put("search", form.getSearch());
		try {
			String response = httpClientTemplate.doPost(getQiniuData(TB_COUNT), params);
			result = Integer.parseInt(response);
		} catch (Exception e) {
			
		}
		return result;
    }
    
    /**
	 *	单个实体
	 */
    public SysUser select(SysUserForm form) {	
    	List<SysUser> result = null;
		Map<String,Object> params = Maps.newHashMap();
		//设置参数:条件
		params.put("search", form.getSearch());
		try {
			//取得用户信息
			String response = httpClientTemplate.doPost(getQiniuData(TB_LIST), params);
			result = commonDelegator.parseObjectList(response, SysUser.class);
		} catch (Exception e) {
			
		}
		if(result != null && result.size() > 0) {
			return result.get(0);
		}
		return null;
    }
    
    /**
	 *	列表
	 */
    public List<SysUser> tbList(SysUserForm form) {	
    	List<SysUser> result = null;
		Map<String,Object> params = Maps.newHashMap();
		//设置参数:条件
		params.put("search", form.getSearch());
		try {
			//取得用户信息
			String response = httpClientTemplate.doPost(getQiniuData(TB_LIST), params);
			result = commonDelegator.parseObjectList(response, SysUser.class);
		} catch (Exception e) {
			
		}
		return result;
    }
    
    /**
	 *	分页
	 */
    public Pager<SysUser> tbPageList(SysUserForm form) {
    	Pager<SysUser> result = null;
		Map<String,Object> params = Maps.newHashMap();
		//设置参数:条件
		params.put("search", form.getSearch());
		//设置参数:条件
		params.put("pager", form.getPager());
		try {
			//取得用户信息
			String response = httpClientTemplate.doPost(getQiniuData(TB_PAGELIST), params);
			//定义结果集
			result = commonDelegator.parsePager(response, SysUser.class);;
		} catch (Exception e) {
			return result;
		}
		return result;
    }
}
