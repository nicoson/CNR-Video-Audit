package cn.qiniu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.qiniu.config.Global;
import cn.qiniu.entity.SysUser;
import cn.qiniu.entity.SysUserExample;
import cn.qiniu.form.SysUserForm;
import cn.qiniu.mapper.SysUserMapper;
import cn.qiniu.util.PagerForm;

import com.github.pagehelper.PageHelper;
@Service
public class SysUserService {
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private SysUserMapper mapper;

	/**
     *  初始化分页组件
     *
     * @return
     */
	private Object initPager(PagerForm pagerForm) {
		PageHelper.startPage(pagerForm.getCurrent(), pagerForm.getRowCount());
		return null;
	}
	
	/**
     *  组装检索条件
     *
     * @return
     */
	private SysUserExample getExample(SysUserForm form){
		//定义检索条件
		SysUserExample example = new SysUserExample();
		SysUserExample.Criteria criteria = example.createCriteria();
		//example.setOrderByClause("user_id");
		utilService.convertExample(criteria, form.getSearch(), new ArrayList<String>());
		return example;
	}
	
	/**
     *  新增
     *
     * @return
     */
	@Transactional
	public Object tbAdd(SysUserForm form) {
		//设置参数
		form.getRecord().setDelFlg(Global.DEL_FLG.DEL_FLG_0);
		form.getRecord().setCreatedAt(new Date());
		form.getRecord().setUpdatedAt(new Date());
		form.getRecord().setUpdatedBy(form.getRecord().getCreatedBy());	
		//转换返回结果 插入条数
		return mapper.insertSelective(form.getRecord());
	}
	
	/**
	 * 修改
	 * 
	 * @return
	 */
	@Transactional
	public Object tbUpdate(SysUserForm form) {
		//设置参数
		form.getRecord().setUpdatedAt(new Date());
		//转换返回结果 更新条数
		return mapper.updateByExampleSelective(form.getRecord(),getExample(form));
	}
	
	/**
	 * 修改ByPk
	 * 
	 * @return
	 */
	@Transactional
	public Object tbUpdateByPk(SysUserForm form) {
		//设置参数
		form.getRecord().setUpdatedAt(new Date());
		//转换返回结果 更新条数
		return mapper.updateByPrimaryKeySelective(form.getRecord());
	}
	
	/**
	 * 统计数量
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public Object tbCount(SysUserForm form) {
		//转换返回结果  统计数量
		return mapper.countByExample(getExample(form));
	}
	
//	/**
//	 * 列表
//	 * 
//	 * @return
//	 */
//	@Transactional(readOnly=true)
//	public Object tbList(SysUserForm form) {
//		//转换返回结果,不包含分页信息
//		return utilService.buildVoWithoutPage(mapper.selectByExample(getExample(form)));
//	}
//	
//	/**
//	 * 分页显示
//	 * 
//	 * @return
//	 */
//	@Transactional(readOnly=true)
//	public Object tbPageList(SysUserForm form) {
//		//设置分页信息（current：当前页码      rowCount：每页显示行数）
//		initPager(form.getPager());
//		List<SysUser> list = mapper.selectByExample(getExample(form));
//		//转换返回结果（包含：进行code转换后的检索结果集、分页信息）,默认的结果集中的字段都以String类型展现
//		return utilService.buildVoWithPage(list, 
//				form.getPager(),
//				mapper.countByExample(getExample(form)));
//	}
	
}
