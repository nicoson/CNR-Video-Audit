package cn.qiniu.entity.videoReview;

/**
 * 提交审核视频参数实体类
 * @author ch
 *
 */
public class VideoSubmit {
	
	private VideoSubmitData data;
	
	private VideoSubmitParams params;

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