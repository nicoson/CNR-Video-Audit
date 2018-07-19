package cn.qiniu.entity.videoCallBack;

import java.util.List;

/**
 * 视频审核完成范回数据实体
 * @author ch
 *
 */
public class ResultCallBack {
	private List<ResultCallBackLabels> labels;
	
	private List<ResultCallBackSegments> segments;

	public List<ResultCallBackLabels> getLabels() {
		return labels;
	}

	public void setLabels(List<ResultCallBackLabels> labels) {
		this.labels = labels;
	}

	public List<ResultCallBackSegments> getSegments() {
		return segments;
	}

	public void setSegments(List<ResultCallBackSegments> segments) {
		this.segments = segments;
	}
	
}
