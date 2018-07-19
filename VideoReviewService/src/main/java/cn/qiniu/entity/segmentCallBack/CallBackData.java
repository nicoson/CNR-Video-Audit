package cn.qiniu.entity.segmentCallBack;

/**
 * 提交审核视频-片段信息返回数据
 * @author ch
 *
 */
public class CallBackData {
	//视频id
	private String id;
	
	private String op;
	
	private CallBackSegment result;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public CallBackSegment getResult() {
		return result;
	}

	public void setResult(CallBackSegment result) {
		this.result = result;
	}
}
