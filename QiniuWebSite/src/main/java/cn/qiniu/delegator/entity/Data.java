package cn.qiniu.delegator.entity;

public class Data {

	private Attribute attribute;
	// 视频资源地址
	private String uri;

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
