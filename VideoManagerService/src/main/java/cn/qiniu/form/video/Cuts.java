package cn.qiniu.form.video;
/**
 * video文件下的实体类 用于获取视频管理列表 页面显示
 * @author html
 *
 */
public class Cuts {
	
	private Integer offset;

	private String uri;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
