package cn.qiniu.util.common;

import java.util.List;

/**
 * Project:Pager.java statement: 分页Bean
 * 
 * @author:吴飞
 * @Date:2014年9月9日
 */
public class Pager<T> {
	private int current = 1; // 页码
	private int rowCount = 15; // 每页行数
	private int totalRowCount; // 总行数
	private int totalPageCount = 1; // 总页数
	private List<T> data;

	/**
	 * @return List<T> data
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * @param data
	 *            设置 data
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * @return int totalRowCount
	 */
	public int getTotalRowCount() {
		return totalRowCount;
	}

	/**
	 * @param totalRowCount
	 *            设置 totalRowCount
	 */
	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

	/**
	 * @return int totalPageCount
	 */
	public int getTotalPageCount() {
		return totalPageCount;
	}

	/**
	 * @param totalPageCount
	 *            设置 totalPageCount
	 */
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	/**
	 * @return int current
	 */
	public int getCurrent() {
		return current;
	}

	/**
	 * @param current
	 *            设置 current
	 */
	public void setCurrent(int current) {
		this.current = current;
	}

	/**
	 * @return int rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            设置 rowCount
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
}
