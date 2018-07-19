package cn.qiniu.form.mock;

import java.util.List;

public class Segments {

	private String op;

	private Integer offsetBegin;

	private Integer offsetEnd;

	private String uri;

	private List<Cuts> cuts;

	private String level;

	private String manualLevel;

	private String roboticLevel;

	private float roboticScore;

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public Integer getOffsetBegin() {
		return offsetBegin;
	}

	public void setOffsetBegin(Integer offsetBegin) {
		this.offsetBegin = offsetBegin;
	}

	public Integer getOffsetEnd() {
		return offsetEnd;
	}

	public void setOffsetEnd(Integer offsetEnd) {
		this.offsetEnd = offsetEnd;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public List<Cuts> getCuts() {
		return cuts;
	}

	public void setCuts(List<Cuts> cuts) {
		this.cuts = cuts;
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

	public float getRoboticScore() {
		return roboticScore;
	}

	public void setRoboticScore(float roboticScore) {
		this.roboticScore = roboticScore;
	}

}
