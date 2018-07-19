package cn.qiniu.form.video;


public class ReviewListSearch {
	
	//危险等级
	private String dangerLevel;
	
	//用户id
	private String loginName;
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getDangerLevel() {
		return dangerLevel;
	}

	public void setDangerLevel(String dangerLevel) {
		this.dangerLevel = dangerLevel;
	}
	
}