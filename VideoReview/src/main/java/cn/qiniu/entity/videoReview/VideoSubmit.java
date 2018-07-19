package cn.qiniu.entity.videoReview;

import java.io.Serializable;

/**
 * 提交审核视频参数实体类
 * @author ch
 *
 */
public class VideoSubmit implements Serializable {
	private static final long serialVersionUID = 3559533002594201715L;
	private String jobId;
	
	private VideoSubmitData data;
	
	private VideoSubmitParams params;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public VideoSubmitData getData() {
		return data;
	}

	public void setData(VideoSubmitData data) {
		this.data = data;
	}

	public VideoSubmitParams getParams() {
		return params;
	}

	public void setParams(VideoSubmitParams params) {
		this.params = params;
	}
}