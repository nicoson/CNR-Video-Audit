package cn.qiniu.entity.video.callback;

/**
 * 截帧Entity
 * 
 * @author Hong Yingjie
 * 
 */
public class Cut {
	// 截帧的时间位置
	private int offset;
	// 截帧的对应结果
	private Result result;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

}
