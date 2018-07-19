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
	
	private List<ResultCallBackLabels> labels;
	
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
	public List<ResultCallBackLabels> getLabels() {
		return labels;
	}

	public void setLabels(List<ResultCallBackLabels> labels) {
		this.labels = labels;
	}

	public List<ResultCallBackCut> getCuts() {
		return cuts;
	}

	public void setCuts(List<ResultCallBackCut> cuts) {
		this.cuts = cuts;
	}
}
