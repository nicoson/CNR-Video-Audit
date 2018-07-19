package cn.qiniu.entity.videoCallBack;

/**
 * 提交审核视频，片段信息返回数据
 * @author ch
 *
 */
public class ResultCallBackCut {
	//截帧时间位置
	private int offset;
	
	//截帧对应结果
	private ResultCallBackResult result;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public ResultCallBackResult getResult() {
		return result;
	}

	public void setResult(ResultCallBackResult result) {
		this.result = result;
	}
}
