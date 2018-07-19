package cn.qiniu.entity.videoReviewToQiniu;

import java.io.Serializable;

/**
 * 审核视频提交识别服务实体类
 * @author ch
 *
 */
public class QiniuReviewOp implements Serializable{
	private static final long serialVersionUID = 3559533002594201715L;
	private String op;
	
	private String hookURL;
	
	private String hookURL_segment;
	
	private QiniuReviewOpParams params;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getHookURL() {
		return hookURL;
	}

	public void setHookURL(String hookURL) {
		this.hookURL = hookURL;
	}

	public String getHookURL_segment() {
		return hookURL_segment;
	}

	public void setHookURL_segment(String hookURL_segment) {
		this.hookURL_segment = hookURL_segment;
	}

	public QiniuReviewOpParams getParams() {
		return params;
	}

	public void setParams(QiniuReviewOpParams params) {
		this.params = params;
	}
	
}
