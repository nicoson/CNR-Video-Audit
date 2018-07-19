package cn.qiniu.form.video;

import cn.qiniu.entity.ReviewVideoManager;

public class ReviewListForm {
	private ReviewVideoManager record;
	private ReviewListSearch search;
	public ReviewVideoManager getRecord() {
		return record;
	}
	public void setRecord(ReviewVideoManager record) {
		this.record = record;
	}
	public ReviewListSearch getSearch() {
		return search;
	}
	public void setSearch(ReviewListSearch search) {
		this.search = search;
	}
	
}
