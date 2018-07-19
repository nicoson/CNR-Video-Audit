package cn.qiniu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.qiniu.form.SysResourceForm;
import cn.qiniu.mapper.SysPermissionListMapper;

@Service
public class sysPermissionListService {

	@Autowired
	private UtilService utilService;
	
	@Autowired
	private SysPermissionListMapper mapper;
	
	/**
	 * 列表
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public Object tbList(SysResourceForm form) {
		//转换返回结果,不包含分页信息
		return mapper.getUserResList(form.getSearch());
	}
}
