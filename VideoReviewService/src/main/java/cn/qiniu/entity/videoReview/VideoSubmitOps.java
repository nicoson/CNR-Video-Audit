package cn.qiniu.entity.videoReview;

/**
 * 提交审核视频参数实体类
 * @author ch
 *
 */
public class VideoSubmitOps {
	
	private VideoSubmitOp pulp;
	
	private VideoSubmitOp terror;
	
	private VideoSubmitOp politician;

	public VideoSubmitOp getPulp() {
		return pulp;
	}

	public void setPulp(VideoSubmitOp pulp) {
		this.pulp = pulp;
	}

	public VideoSubmitOp getTerror() {
		return terror;
	}

	public void setTerror(VideoSubmitOp terror) {
		this.terror = terror;
	}

	public VideoSubmitOp getPolitician() {
		return politician;
	}

	public void setPolitician(VideoSubmitOp politician) {
		this.politician = politician;
	}
	
}