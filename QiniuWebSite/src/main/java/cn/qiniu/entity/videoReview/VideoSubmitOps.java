package cn.qiniu.entity.videoReview;

import java.io.Serializable;

/**
 * 提交审核视频参数实体类
 * @author ch
 *
 */
public class VideoSubmitOps implements Serializable {
	private static final long serialVersionUID = 3559533002594201715L;
	
	private VideoSubmitOp pulp;
	
	private VideoSubmitOp terror;
	
	private VideoSubmitOp politician;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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