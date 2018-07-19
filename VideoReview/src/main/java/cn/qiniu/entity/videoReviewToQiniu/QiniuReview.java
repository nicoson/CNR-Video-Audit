package cn.qiniu.entity.videoReviewToQiniu;

import java.io.Serializable;
import java.util.List;

/**
 * 审核视频提交识别服务实体类
 * @author ch
 *
 */
public class QiniuReview implements Serializable {
	private static final long serialVersionUID = 3559533002594201715L;
	private QiniuReviewData data;
	
	private QiniuReviewParams params;
	
	private List<QiniuReviewOp> ops;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public QiniuReviewData getData() {
		return data;
	}

	public void setData(QiniuReviewData data) {
		this.data = data;
	}

	public QiniuReviewParams getParams() {
		return params;
	}

	public void setParams(QiniuReviewParams params) {
		this.params = params;
	}

	public List<QiniuReviewOp> getOps() {
		return ops;
	}

	public void setOps(List<QiniuReviewOp> ops) {
		this.ops = ops;
	}
}
