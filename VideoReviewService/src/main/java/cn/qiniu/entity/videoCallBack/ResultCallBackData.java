package cn.qiniu.entity.videoCallBack;

/**
 * 视频审核完成范回数据实体
 * @author ch
 *
 */
public class ResultCallBackData {

	//视频id
	private String id;
	
	//op
	private String op;
	
	private ResultCallBack result;

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

	public ResultCallBack getResult() {
		return result;
	}

	public void setResult(ResultCallBack result) {
		this.result = result;
	}
	
}
