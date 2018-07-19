package cn.qiniu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.delegator.SysDictDelegator;
import cn.qiniu.entity.SysDict;
import cn.qiniu.entity.basic.SysDictSearch;
import cn.qiniu.form.SysDictForm;
import cn.qiniu.util.common.Constant;

@Service
public class SysDictService {
	@Autowired
    private SysDictDelegator delegator;
	 /**
	  * 分页
	  * @param search 视频列表-查询条件
	  * @param pager 分页参数
	  * @return 分页数据
	  */
	 public List<SysDict> queryBasDictionaryItemByCode(SysDictSearch search){
		 search.setDelFlg(Constant.DEL_FLG.DEL_FLG_0);
		 SysDictForm form = new SysDictForm();
		 form.setSearch(search);
		 return delegator.selectByDictionaryCode(form);
	 }
	 
	 /**
	  * 根据value、type查询字典表label
	  */
	 public String getDictLabelByValue(String value,String type){
		 return delegator.getDictLabelByValue(value,type);
	 }
}
