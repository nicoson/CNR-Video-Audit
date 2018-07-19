package cn.qiniu.entity.videoReview;

/**
 * 提交审核视频参数实体类
 * @author ch
 *
 */
public class VideoSubmitOp {
	private String[] level;
	
	private String save;
	
	private VideoSubmitHook hook;
	
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