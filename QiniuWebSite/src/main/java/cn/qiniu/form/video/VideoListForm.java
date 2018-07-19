package cn.qiniu.form.video;

import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.VideoListSearch;
import cn.qiniu.util.common.PagerForm;

public class VideoListForm {
	private VideoListSearch search;
    private ReviewVideoManager record;
    private PagerForm pager;
	public VideoListSearch getSearch() {
		return search;
	}
	public void setSearch(VideoListSearch search) {
		this.search = search;
	}
	public ReviewVideoManager getRecord() {
		return record;
	}
	public void setRecord(ReviewVideoManager record) {
		this.record = record;
	}
	public PagerForm getPager() {
		return pager;
	}
	public void setPager(PagerForm pager) {
		this.pager = pager;
	}
    
}
