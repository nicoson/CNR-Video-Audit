package cn.qiniu.entity.video.callback;

import java.util.List;

/**
 * 片段Entity
 * 
 * @author Hong Yingjie
 * 
 */
public class Segment {
	// 片段起始的时间位置
	private int offset_begin;
	// 片段結束的时间位置
	private int offset_end;
	// 片段的判定结果
	private List<Label> labels;
	// 片段的截帧
	private List<Cut> cuts;

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

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public List<Cut> getCuts() {
		return cuts;
	}

	public void setCuts(List<Cut> cuts) {
		this.cuts = cuts;
	}

}
