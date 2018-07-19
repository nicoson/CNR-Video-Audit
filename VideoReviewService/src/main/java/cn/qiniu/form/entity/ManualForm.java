package cn.qiniu.form.entity;

import java.util.List;

/**
 * 该文件夹下是 提交人工审核实体类
 * @author html
 *
 */
public class ManualForm {
	private String  videoId;
	
	private String level;
	
	private Ops ops;
	
	private List<Segements> segments;

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

	public List<Segements> getSegments() {
		return segments;
	}

	public void setSegments(List<Segements> segments) {
		this.segments = segments;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	



	

}
