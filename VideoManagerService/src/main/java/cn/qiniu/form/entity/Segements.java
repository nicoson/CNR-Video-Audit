package cn.qiniu.form.entity;

public class Segements {
	
	private String segementId;
	
	private String op;

	private Integer offsetBegin;

	private Integer offsetEnd;
	
	private String level;

	private String manualLevel;
	

	

	public String getSegementId() {
		return segementId;
	}

	public void setSegementId(String segementId) {
		this.segementId = segementId;
	}

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

}
