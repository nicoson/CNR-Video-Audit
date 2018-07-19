package cn.qiniu.delegator.entity;

public class Op {

	private String level;

	private String manualLevel;

	private String roboticLevel;

	private float roboticScore;

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

	public float getRoboticScore() {
		return roboticScore;
	}

	public void setRoboticScore(float roboticScore) {
		this.roboticScore = roboticScore;
	}

}
