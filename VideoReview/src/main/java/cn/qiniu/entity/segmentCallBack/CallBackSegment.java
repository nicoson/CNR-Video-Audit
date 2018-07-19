package cn.qiniu.entity.segmentCallBack;

import java.util.List;

/**
 * 提交审核信息-片段返回信息
 * @author ch
 *
 */
public class CallBackSegment {
	//片段开始时间
	private int offset_begin;
	
	//片段结束时间
	private int offset_end;
	
	//审核等级
	private List<CallBackLabels> labels;
	
	private List<CallBackCut> cuts;

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

	public List<CallBackLabels> getLabels() {
		return labels;
	}

	public void setLabels(List<CallBackLabels> labels) {
		this.labels = labels;
	}

	public List<CallBackCut> getCuts() {
		return cuts;
	}

	public void setCuts(List<CallBackCut> cuts) {
		this.cuts = cuts;
	}
}
