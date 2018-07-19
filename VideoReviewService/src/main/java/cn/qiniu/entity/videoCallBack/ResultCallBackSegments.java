package cn.qiniu.entity.videoCallBack;

import java.util.List;

/**
 * 视频审核完成返回数据实体
 * @author ch
 *
 */
public class ResultCallBackSegments {
	private int offset_begin;
	
	private int offset_end;
	
	private String label;
	
	private float score;
	
	private List<ResultCallBackCut> cuts;

	public int getOffset_begin() {
		return offset_begin;
	}

	public void setOffset_begin(int offset_begin) {
		this.offset_begin = offset_begin;
	}

	public int getOffset_end() {
		return offset_end;
	}

	public void setOffset_end(int offset_end) {
		this.offset_end = offset_end;
	}

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

	public List<ResultCallBackCut> getCuts() {
		return cuts;
	}

	public void setCuts(List<ResultCallBackCut> cuts) {
		this.cuts = cuts;
	}
}
