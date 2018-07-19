package cn.qiniu.form.video;
/**
 * video文件下的实体类 用于获取视频管理列表 页面显示
 * @author html
 *
 */
public class Video {
	//视频名称
	private String videoName;
	
	//创建时间
	private String createAt;
	
	//视频资源归档地址
	private String uri;
	//视频资源封面地址
	private String cover;

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

}
