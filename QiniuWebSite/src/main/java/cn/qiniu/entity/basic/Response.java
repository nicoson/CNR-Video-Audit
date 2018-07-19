package cn.qiniu.entity.basic;

import java.io.Serializable;

/**  
 * Response
 * 定义向页面传输数据的格式
 */ 
public class Response implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private boolean success;
	private String errorMsg;
	private Object data;
//	private Pager<?> pager;
//	private OrderBean orderBean;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
//	public PaginationMore getPager() {
//		return pager;
//	}
//	public void setPager(PaginationMore pager) {
//		this.pager = pager;
//	}
//	public OrderBean getOrderBean() {
//		return orderBean;
//	}
//	public void setOrderBean(OrderBean orderBean) {
//		this.orderBean = orderBean;
//	}
}
