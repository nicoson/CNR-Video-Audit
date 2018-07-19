package cn.qiniu.entity.segmentCallBack;

/**
 * 提交审核视频，片段信息返回数据
 * @author ch
 *
 */
public class CallBackCut {
	//截帧时间位置
	private int offset;
	
	//截帧对应结果
	private CallBackResult result;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public CallBackResult getResult() {
		return result;
	}

	public void setResult(CallBackResult result) {
		this.result = result;
	}
}
