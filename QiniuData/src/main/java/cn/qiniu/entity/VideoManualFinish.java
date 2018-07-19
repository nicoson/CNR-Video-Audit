package cn.qiniu.entity;

import java.util.List;

/**
 * 【审核完成】按钮返回数据实体
 * @author ch
 *
 */
public class VideoManualFinish {
	//【确定】
	ReviewVideoManagerSegments record;
	
	//【审核完成】
	List<ReviewVideoManagerSegments> list;

	public ReviewVideoManagerSegments getRecord() {
		return record;
	}

	public void setRecord(ReviewVideoManagerSegments record) {
		this.record = record;
	}

	public List<ReviewVideoManagerSegments> getList() {
		return list;
	}

	public void setList(List<ReviewVideoManagerSegments> list) {
		this.list = list;
	}
	
}
