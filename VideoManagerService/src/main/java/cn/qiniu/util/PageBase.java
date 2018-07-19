package cn.qiniu.util;

public class PageBase {

	private int pageNo;	//当前页数
	
	private int pageSize;	//页面显示条数
	
	private int totalSize;	//总页数
	

	public PageBase() {
		super();
	}

	public PageBase(int pageNo, int pageSize, int totalSize) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalSize = totalSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	
}
