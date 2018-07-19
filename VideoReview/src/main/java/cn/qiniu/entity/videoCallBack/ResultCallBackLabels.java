package cn.qiniu.entity.videoCallBack;

/**
 * 视频审核完成范回数据实体
 * @author ch
 *
 */
public class ResultCallBackLabels {
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
