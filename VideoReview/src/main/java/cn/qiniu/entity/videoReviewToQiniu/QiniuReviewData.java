package cn.qiniu.entity.videoReviewToQiniu;

import java.io.Serializable;

/**
 * 审核视频提交识别服务实体类
 * @author ch
 *
 */
public class QiniuReviewData implements Serializable {
	private static final long serialVersionUID = 3559533002594201715L;
	private String uri;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
