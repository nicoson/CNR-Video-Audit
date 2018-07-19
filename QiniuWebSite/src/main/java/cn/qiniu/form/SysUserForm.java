package cn.qiniu.form;

import cn.qiniu.entity.SysUser;
import cn.qiniu.entity.SysUserSearch;
import cn.qiniu.util.common.PagerForm;

public class SysUserForm {
	private SysUserSearch search;
    private SysUser record;
    private PagerForm pager;
	public SysUserSearch getSearch() {
		return search;
	}
	public void setSearch(SysUserSearch search) {
		this.search = search;
	}
	public SysUser getRecord() {
		return record;
	}
	public void setRecord(SysUser record) {
		this.record = record;
	}
	public PagerForm getPager() {
		return pager;
	}
	public void setPager(PagerForm pager) {
		this.pager = pager;
	}
	
    
}
