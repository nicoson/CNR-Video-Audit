package cn.qiniu.form.video;

/**
 * 审核信息表查询条件
 * @author html
 *
 */
public class CheckDetailInfoSearch {
	//视频id
	private String videoId;
	//截帧id
	private String interceptId;
	//审核类型(人审、机审)
	private String checkType;
	//删除标识
	private String delFlg;
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getInterceptId() {
		return interceptId;
	}
	public void setInterceptId(String interceptId) {
		this.interceptId = interceptId;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
}
