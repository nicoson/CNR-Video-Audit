package cn.qiniu.entity.api;

import java.math.BigDecimal;

/**
 * 识别结果实体类
 * 
 * @author Chen Hua
 * @version 2017-09-26
 */
public class IdentifyResult {
	// 分类
	private int label;
	// 是否需要人工复审该图片
	private boolean review;
	// 识别概率值
	private BigDecimal score;
	
	public int getLabel() {
		return label;
	}
	
	public void setLabel(int label) {
		this.label = label;
	}
	
	public boolean isReview() {
		return review;
	}
	
	public void setReview(boolean review) {
		this.review = review;
	}
	
	public BigDecimal getScore() {
		return score;
	}
	
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	
	
}
