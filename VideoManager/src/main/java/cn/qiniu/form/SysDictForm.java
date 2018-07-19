package cn.qiniu.form;

import cn.qiniu.entity.SysDict;
import cn.qiniu.entity.basic.SysDictSearch;
import cn.qiniu.util.common.PagerForm;

public class SysDictForm {
	private SysDictSearch search;
    private SysDict record;
    private PagerForm pager;
	public SysDictSearch getSearch() {
		return search;
	}
	public void setSearch(SysDictSearch search) {
		this.search = search;
	}
	public SysDict getRecord() {
		return record;
	}
	public void setRecord(SysDict record) {
		this.record = record;
	}
	public PagerForm getPager() {
		return pager;
	}
	public void setPager(PagerForm pager) {
		this.pager = pager;
	}
}
