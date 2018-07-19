package cn.qiniu.delegator.entity;

public class ManualVideoBody {

	private String level;

	private Ops ops;

	private Segments segments;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Ops getOps() {
		return ops;
	}

	public void setOps(Ops ops) {
		this.ops = ops;
	}

	public Segments getSegments() {
		return segments;
	}

	public void setSegments(Segments segments) {
		this.segments = segments;
	}

}
