package cn.qiniu.entity.videoReviewToQiniu;

import java.io.Serializable;

/**
 * 审核视频提交识别服务实体类
 * @author ch
 *
 */
public class QiniuReviewParams implements Serializable {
	private static final long serialVersionUID = 3559533002594201715L;
	private boolean async;
	
	private QiniuReviewSegment segment;
	
	private QiniuReviewVframe vframe;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public QiniuReviewSegment getSegment() {
		return segment;
	}

	public void setSegment(QiniuReviewSegment segment) {
		this.segment = segment;
	}

	public QiniuReviewVframe getVframe() {
		return vframe;
	}

	public void setVframe(QiniuReviewVframe vframe) {
		this.vframe = vframe;
	}
	
}
