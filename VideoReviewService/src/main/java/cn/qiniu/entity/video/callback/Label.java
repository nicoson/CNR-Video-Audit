package cn.qiniu.entity.video.callback;

import java.math.BigDecimal;

/**
 * 截帧Entity
 * 
 * @author Hong Yingjie
 * 
 */
public class Label {
	// 判定结果
	private String label;
	// 判定结果可信度
	private BigDecimal score;

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
