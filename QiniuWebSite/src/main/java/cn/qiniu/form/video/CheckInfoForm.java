package cn.qiniu.form.video;

import cn.qiniu.entity.CheckInfoSearch;
import cn.qiniu.util.common.PagerForm;

public class CheckInfoForm {
	private CheckInfoSearch search;
	private PagerForm pager;
	
	public CheckInfoSearch getSearch() {
		return search;
	}
	public void setSearch(CheckInfoSearch search) {
		this.search = search;
	}
	public PagerForm getPager() {
		return pager;
	}
	public void setPager(PagerForm pager) {
		this.pager = pager;
	}
}
