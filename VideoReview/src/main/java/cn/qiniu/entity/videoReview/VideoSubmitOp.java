package cn.qiniu.entity.videoReview;

import java.io.Serializable;

/**
 * 提交审核视频参数实体类
 * @author ch
 *
 */
public class VideoSubmitOp implements Serializable {
	private static final long serialVersionUID = 3559533002594201715L;
	private String[] level;
	
	private String save;
	
	private VideoSubmitHook hook;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSave() {
		return save;
	}

	public void setSave(String save) {
		this.save = save;
	}

	public String[] getLevel() {
		return level;
	}

	public void setLevel(String[] level) {
		this.level = level;
	}

	public VideoSubmitHook getHook() {
		return hook;
	}

	public void setHook(VideoSubmitHook hook) {
		this.hook = hook;
	}
	
}