package cn.qiniu.entity.videoReplace;


import java.util.List;

public class VideoReplaceLevelsegment {
	private String op;
	private int offsetBegin;
	private int offsetEnd;
	private String uri;
	private List<VideoReplaceLevelsegmentcuts> cuts;
	private String level;
//	private String manualLevel;
//	private String roboticLevel;
//	private BigDecimal roboticScore;
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public int getOffsetBegin() {
		return offsetBegin;
	}
	public void setOffsetBegin(int offsetBegin) {
		this.offsetBegin = offsetBegin;
	}
	public int getOffsetEnd() {
		return offsetEnd;
	}
	public void setOffsetEnd(int offsetEnd) {
		this.offsetEnd = offsetEnd;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public List<VideoReplaceLevelsegmentcuts> getCuts() {
		return cuts;
	}
	public void setCuts(List<VideoReplaceLevelsegmentcuts> cuts) {
		this.cuts = cuts;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
//	public String getManualLevel() {
//		return manualLevel;
//	}
//	public void setManualLevel(String manualLevel) {
//		this.manualLevel = manualLevel;
//	}
//	public String getRoboticLevel() {
//		return roboticLevel;
//	}
//	public void setRoboticLevel(String roboticLevel) {
//		this.roboticLevel = roboticLevel;
//	}
//	public BigDecimal getRoboticScore() {
//		return roboticScore;
//	}
//	public void setRoboticScore(BigDecimal roboticScore) {
//		this.roboticScore = roboticScore;
//	}
	
}
