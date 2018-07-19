package cn.qiniu.entity.videoReview;

/**
 * 提交审核视频参数实体类
 * @author ch
 *
 */
public class VideoSubmitDataAttribute {
	
	private String id;
	
	private String desc;
	
	private VideoSubmitMeta meta;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public VideoSubmitMeta getMeta() {
		return meta;
	}

	public void setMeta(VideoSubmitMeta meta) {
		this.meta = meta;
	}
	
}