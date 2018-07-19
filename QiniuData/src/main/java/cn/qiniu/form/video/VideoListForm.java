package cn.qiniu.form.video;

import cn.qiniu.util.PagerForm;

public class VideoListForm {
	private VideoListSearch search;
    private PagerForm pager;
	public VideoListSearch getSearch() {
		return search;
	}
	public void setSearch(VideoListSearch search) {
		this.search = search;
	}
	public PagerForm getPager() {
		return pager;
	}
	public void setPager(PagerForm pager) {
		this.pager = pager;
	}
}
