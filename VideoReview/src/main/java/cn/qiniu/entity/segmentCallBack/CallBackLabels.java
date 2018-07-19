package cn.qiniu.entity.segmentCallBack;

/**
 * 提交审核视频，片段信息返回数据
 * @author ch
 *
 */
public class CallBackLabels {
	private String label;
	
	private float score;

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
}
