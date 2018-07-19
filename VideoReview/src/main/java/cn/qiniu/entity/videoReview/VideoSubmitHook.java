package cn.qiniu.entity.videoReview;

import java.io.Serializable;

/**
 * 提交审核视频参数实体类
 * @author ch
 *
 */
public class VideoSubmitHook implements Serializable {
	private static final long serialVersionUID = 3559533002594201715L;
	private String host;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
}