package cn.qiniu.form;

import cn.qiniu.entity.SysResource;
import cn.qiniu.entity.SysResourceSearch;

public class SysResourceForm{
	private SysResourceSearch search;
	private SysResource record;
	public SysResourceSearch getSearch() {
		return search;
	}
	public void setSearch(SysResourceSearch search) {
		this.search = search;
	}
	public SysResource getRecord() {
		return record;
	}
	public void setRecord(SysResource record) {
		this.record = record;
	}
	
}
