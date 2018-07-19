package cn.qiniu.entity.videoReview;

/**
 * 提交审核视频参数实体类
 * @author ch
 *
 */
public class VideoSubmitData {
	
	private String uri;
	
	private VideoSubmitDataAttribute attribute;

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