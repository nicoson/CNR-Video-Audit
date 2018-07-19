package cn.qiniu.entity.videoReviewToQiniu;

import java.io.Serializable;
import java.util.List;

/**
 * 审核视频提交识别服务实体类
 * @author ch
 *
 */
public class QiniuReviewOpParams implements Serializable{
	private static final long serialVersionUID = 3559533002594201715L;
	private List<QiniuReviewLabel> labels;
	
	private QiniuReviewTerminate terminate;
	
	private QiniuReviewOther other;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<QiniuReviewLabel> getLabels() {
		return labels;
	}

	public void setLabels(List<QiniuReviewLabel> labels) {
		this.labels = labels;
	}

	public QiniuReviewTerminate getTerminate() {
		return terminate;
	}

	public void setTerminate(QiniuReviewTerminate terminate) {
		this.terminate = terminate;
	}

	public QiniuReviewOther getOther() {
		return other;
	}

	public void setOther(QiniuReviewOther other) {
		this.other = other;
	}

}
