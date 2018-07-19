package cn.qiniu.entity.videoReview;

import java.io.Serializable;

/**
 * 提交审核视频参数实体类
 * @author ch
 *
 */
public class VideoSubmitDataAttribute implements Serializable {
	private static final long serialVersionUID = 3559533002594201715L;
	private String id;
	
	private String desc;
	
	private VideoSubmitMeta meta;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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