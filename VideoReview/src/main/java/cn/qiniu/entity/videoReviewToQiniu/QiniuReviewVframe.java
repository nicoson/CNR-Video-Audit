package cn.qiniu.entity.videoReviewToQiniu;

import java.io.Serializable;

/**
 * 审核视频提交识别服务实体类
 * @author ch
 *
 */
public class QiniuReviewVframe implements Serializable {
	private static final long serialVersionUID = 3559533002594201715L;
	private int mode;
	
	private int interval;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}
}
