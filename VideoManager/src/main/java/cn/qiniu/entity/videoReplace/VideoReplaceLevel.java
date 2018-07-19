package cn.qiniu.entity.videoReplace;

import java.math.BigDecimal;
import java.util.List;

public class VideoReplaceLevel {
	private String level;
	private String manualLevel;
	private String roboticLevel;
	private BigDecimal roboticScore;
	private VideoReplaceLevelOp ops;
	private List<VideoReplaceLevelsegment> segments;
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
	public BigDecimal getRoboticScore() {
		return roboticScore;
	}
	public void setRoboticScore(BigDecimal roboticScore) {
		this.roboticScore = roboticScore;
	}
	public VideoReplaceLevelOp getOps() {
		return ops;
	}
	public void setOps(VideoReplaceLevelOp ops) {
		this.ops = ops;
	}
	public List<VideoReplaceLevelsegment> getSegments() {
		return segments;
	}
	public void setSegments(List<VideoReplaceLevelsegment> segments) {
		this.segments = segments;
	}
	
}
