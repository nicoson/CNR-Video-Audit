package cn.qiniu.entity.videoReviewToQiniu;

import java.io.Serializable;

/**
 * 审核视频提交识别服务实体类
 * @author ch
 *
 */
public class QiniuReviewLabel implements Serializable {
	private static final long serialVersionUID = 3559533002594201715L;
	private String label;
	
	private int select;
	
	private float score;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getSelect() {
		return select;
	}

	public void setSelect(int select) {
		this.select = select;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
}
