package cn.qiniu.form.video;

/**
 * 分配待审核视频查询条件
 *
 */
public class AssignReviewSearch {
	// 等级-低
	private String low;
	// 等级-中
	private String medium;
	// 等级-高
	private String higher;
	// 用户登录ID
	private String loginName;
	/**
	 * @return the low
	 */
	public String getLow() {
		return low;
	}
	/**
	 * @param low the low to set
	 */
	public void setLow(String low) {
		this.low = low;
	}
	/**
	 * @return the medium
	 */
	public String getMedium() {
		return medium;
	}
	/**
	 * @param medium the medium to set
	 */
	public void setMedium(String medium) {
		this.medium = medium;
	}
	/**
	 * @return the higher
	 */
	public String getHigher() {
		return higher;
	}
	/**
	 * @param higher the higher to set
	 */
	public void setHigher(String higher) {
		this.higher = higher;
	}
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}
