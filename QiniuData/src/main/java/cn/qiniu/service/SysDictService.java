package cn.qiniu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.qiniu.entity.SysDict;
import cn.qiniu.entity.SysDictExample;
import cn.qiniu.form.SysDictForm;
import cn.qiniu.mapper.SysDictMapper;
import cn.qiniu.util.DictUtil;
import cn.qiniu.util.PagerForm;

import com.github.pagehelper.PageHelper;

@Service
public class SysDictService {

	@Autowired
	private UtilService utilService;
	
	@Autowired
	private SysDictMapper mapper;

	private Object initPager(PagerForm pagerForm) {
		PageHelper.startPage(pagerForm.getCurrent(), pagerForm.getRowCount());
		return null;
	}
	
	private SysDictExample getExample(SysDictForm form){
		//定义检索条件
		SysDictExample example = new SysDictExample();
		SysDictExample.Criteria criteria = example.createCriteria();
		utilService.convertExample(criteria, form.getSearch(), new ArrayList<String>());
		return example;
	}
	
	/*
	 * 统计数量
	 * 
	 * @return
	 */ 
	@Transactional(readOnly=true)
	public Object tbCount(SysDictForm form) {
		//转换返回结果  统计数量
		return mapper.countByExample(getExample(form));
	}
	
	/*
	 * 列表
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public Object tbList(SysDictForm form) {
		//转换返回结果,不包含分页信息
		return utilService.buildVoWithoutPage(mapper.selectByExample(getExample(form)));
	}
	
	/*
	 * 分页显示
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public Object tbPageList(SysDictForm form) {
		//设置分页信息（current：当前页码      rowCount：每页显示行数）
		initPager(form.getPager());
		//转换返回结果（包含：进行code转换后的检索结果集、分页信息）,默认的结果集中的字段都以String类型展现
		return utilService.buildVoWithPage(mapper.selectByExample(getExample(form)), 
				form.getPager(),
				mapper.countByExample(getExample(form)));
	}
	
	private SysDictExample getSysDictExample(SysDictForm form){
		SysDictExample example = new SysDictExample();
		//设置条件-未删除
		example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		//设置条件-类型
		.andTypeEqualTo(form.getSearch().getType());
		return example;
	}
	
	/*
	 * 根据字典Type查询
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public Object selectByDictionaryCode(SysDictForm form) {
		List<SysDict> sysDictList = mapper.selectByExample(getSysDictExample(form));
		
		return utilService.buildVoWithoutPage(sysDictList);
		
	}
	
	private SysDictExample getSysDictLabelExample(String value,String type){
		SysDictExample example = new SysDictExample();
		//设置条件-未删除
		example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		//设置条件-类型
		.andTypeEqualTo(type)
		.andValueEqualTo(value);
		return example;
	}
	
	/*
	 * 根据字典Type查询
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getDictLabelByValue(String value,String type) {
		String label = "";
		List<SysDict> sysDictList = mapper.selectByExample(getSysDictLabelExample(value,type));
		
		if(sysDictList != null && sysDictList.size()>0){
			label = sysDictList.get(0).getLabel();
		}
		
		return label;
		
	}
	
	
}
