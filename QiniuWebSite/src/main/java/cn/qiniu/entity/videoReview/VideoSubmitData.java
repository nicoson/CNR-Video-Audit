package cn.qiniu.entity.videoReview;

import java.io.Serializable;

/**
 * 提交审核视频参数实体类
 * @author ch
 *
 */
public class VideoSubmitData implements Serializable {
	private static final long serialVersionUID = 3559533002594201715L;
	
	private String uri;
	
	private VideoSubmitDataAttribute attribute;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public VideoSubmitDataAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(VideoSubmitDataAttribute attribute) {
		this.attribute = attribute;
	}
	
	
}