package cn.qiniu.entity.videoReplace;

import java.math.BigDecimal;

public class VideoReplaceLevelOpResult {
	private String manualLevel;
	private String roboticLevel;
	private BigDecimal roboticScore;

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

	public BigDecimal getRoboticScore() {
		return roboticScore;
	}

	public void setRoboticScore(BigDecimal roboticScore) {
		this.roboticScore = roboticScore;
	}

}
