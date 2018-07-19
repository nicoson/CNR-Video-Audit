package cn.qiniu.entity.videoCallBack;

import java.util.List;

/**
 * 视频审核完成范回数据实体
 * @author ch
 *
 */
public class ResultCallBack {
	private String label;
	
	private float score;
	
	private List<ResultCallBackSegments> segments;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public List<ResultCallBackSegments> getSegments() {
		return segments;
	}

	public void setSegments(List<ResultCallBackSegments> segments) {
		this.segments = segments;
	}
}
