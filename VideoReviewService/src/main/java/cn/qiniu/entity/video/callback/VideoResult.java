package cn.qiniu.entity.video.callback;

import java.util.List;

/**
 * 视频审核结果Entity
 * 
 * @author Hong Yingjie
 * 
 */
public class VideoResult {
	// 判定结果列表
	private List<Label> labels;
	// 判定片段列表
	private List<Segment> segments;

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}

}
