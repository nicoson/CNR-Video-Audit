package cn.qiniu.entity.video.callback;

import java.math.BigDecimal;


/**
 * 截帧对应结果Entity
 * @author Hong Yingjie
 *
 */
public class Result {

	// 判定结果
	private String label;
	// 判定结果可信度
	private BigDecimal score;
	// 是否要人工审核
	private boolean review;
	
	public BigDecimal getScore() {
		return score;
	}
	
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	
	public boolean isReview() {
		return review;
	}
	public void setReview(boolean review) {
		this.review = review;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
