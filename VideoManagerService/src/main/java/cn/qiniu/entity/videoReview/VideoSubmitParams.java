package cn.qiniu.entity.videoReview;

/**
 * 提交审核视频参数实体类
 * @author ch
 *
 */
public class VideoSubmitParams {
	private boolean wait;
	
	private int reviewType;
	
	private String save;
	
	private VideoSubmitOps ops;
	
	private VideoSubmitHook hook;

	public boolean isWait() {
		return wait;
	}

	public void setWait(boolean wait) {
		this.wait = wait;
	}

	public int getReviewType() {
		return reviewType;
	}

	public void setReviewType(int reviewType) {
		this.reviewType = reviewType;
	}

	public String getSave() {
		return save;
	}

	public void setSave(String save) {
		this.save = save;
	}

	public VideoSubmitOps getOps() {
		return ops;
	}

	public void setOps(VideoSubmitOps ops) {
		this.ops = ops;
	}

	public VideoSubmitHook getHook() {
		return hook;
	}

	public void setHook(VideoSubmitHook hook) {
		this.hook = hook;
	}
	
}