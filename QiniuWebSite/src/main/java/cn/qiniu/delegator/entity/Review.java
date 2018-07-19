package cn.qiniu.delegator.entity;

public class Review {
	//人审状态
	private String manualState;

	//机审状态
	private String roboticState;
		
	private String level;

	private String manualLevel;

	private String roboticLevel;
	
	public String getManualState() {
		return manualState;
	}

	public void setManualState(String manualState) {
		this.manualState = manualState;
	}

	public String getRoboticState() {
		return roboticState;
	}

	public void setRoboticState(String roboticState) {
		this.roboticState = roboticState;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getManualLevel() {
		return manualLevel;
	}

	public void setManualLevel(String manualLevel) {
		this.manualLevel = manualLevel;
	}

	public String getRoboticLevel() {
		return roboticLevel;
	}

	public void setRoboticLevel(String roboticLevel) {
		this.roboticLevel = roboticLevel;
	}

}
