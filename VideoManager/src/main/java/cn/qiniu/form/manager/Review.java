package cn.qiniu.form.manager;

import java.util.List;


public class Review {
	
	private String level;

	private String manualLevel;
	
	private String roboticLevel;

	private float roboticScore;
	
	private Ops ops;
	
	private  List<Segments> segments;

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

	public Ops getOps() {
		return ops;
	}

	public void setOps(Ops ops) {
		this.ops = ops;
	}

	public List<Segments> getSegments() {
		return segments;
	}

	public void setSegments(List<Segments> segments) {
		this.segments = segments;
	}


}
