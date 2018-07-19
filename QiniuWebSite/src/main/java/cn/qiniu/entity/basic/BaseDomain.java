package cn.qiniu.entity.basic;


/**
 * Project:BaseDomain.java
 * statement:公共Model
 * @author:lvweijun
 * @Date:2016-11-06
 */
public class BaseDomain {
	/** 删除标志 **/
	private String delFlg;
	/** 创建时间 **/
	private String createdAt;
	/** 创建人**/
	private String createdBy;
	/** 更新时间 **/
	private String updatedAt;
	/** 更新人**/
	private String updatedBy;
	
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
