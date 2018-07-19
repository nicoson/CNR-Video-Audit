package cn.qiniu.form.video;

import java.util.List;

public class VideoListSearch {
	
	int pageStart;
	
	int pageEnd;
	// 当前页码
	String pageNum;
	
	// 记录条数/页
	String pageSize;
	
	// 审核阶段
	String reviewStage;
	
	//当前登录者
	String loginName;
	
	//危险等级(前台拼接好的字符串)
	String reviewLevel;

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}

	//危险等级List(前台参数转换的list)
	private List<String> reviewLevelList;

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getReviewStage() {
		return reviewStage;
	}

	public void setReviewStage(String reviewStage) {
		this.reviewStage = reviewStage;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getReviewLevel() {
		return reviewLevel;
	}

	public void setReviewLevel(String reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

	public List<String> getReviewLevelList() {
		return reviewLevelList;
	}

	public void setReviewLevelList(List<String> reviewLevelList) {
		this.reviewLevelList = reviewLevelList;
	}
	
}