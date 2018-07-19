package cn.qiniu.entity.videoReviewToQiniu;

import java.io.Serializable;

/**
 * 审核视频提交识别服务实体类
 * @author ch
 *
 */
public class QiniuReviewTerminate implements Serializable{
	private static final long serialVersionUID = 3559533002594201715L;
	private int mode;
	
	private QiniuReviewTerminateLabel labels;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public QiniuReviewTerminateLabel getLabels() {
		return labels;
	}

	public void setLabels(QiniuReviewTerminateLabel labels) {
		this.labels = labels;
	}
	
}
