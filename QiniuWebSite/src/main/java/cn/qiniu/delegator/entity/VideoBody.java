package cn.qiniu.delegator.entity;

public class VideoBody {

	// 是否等待结果返回
	private Boolean wait;

	private Data data;

	public Boolean getWait() {
		return wait;
	}

	public void setWait(Boolean wait) {
		this.wait = wait;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
