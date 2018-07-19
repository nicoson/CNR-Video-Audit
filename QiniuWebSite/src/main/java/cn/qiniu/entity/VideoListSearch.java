package cn.qiniu.entity;

import java.util.List;

public class VideoListSearch {
	// 当前页码
	String pageNum;
	
	// 记录条数/页
	String pageSize;
	
	// 审核阶段
	String reviewStage;
	
	//当前登录者
	String loginName;
	
	//危险等级
	String reviewLevel;
	
	//危险等级List(前台参数转换的list)
	private List<String> reviewLevelList;
	
	public String getReviewLevel() {
		return reviewLevel;
	}

	public void setReviewLevel(String reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the pageNum
	 */
	public String getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the pageSize
	 */
	public String getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the reviewStage
	 */
	public String getReviewStage() {
		return reviewStage;
	}

	/**
	 * @param reviewStage the reviewStage to set
	 */
	public void setReviewStage(String reviewStage) {
		this.reviewStage = reviewStage;
	}

	/**
	 * @return the reviewLevelList
	 */
	public List<String> getReviewLevelList() {
		return reviewLevelList;
	}

	/**
	 * @param reviewLevelList the reviewLevelList to set
	 */
	public void setReviewLevelList(List<String> reviewLevelList) {
		this.reviewLevelList = reviewLevelList;
	}

}