package cn.qiniu.entity;

/**
 * 用于接收相应危险等级数量的实体
 * @author ch
 *
 */
public class VideoCountByLevel {
	//等级【高】的审核视频数量
	private String hightCount;
	
	//等级【中】的审核视频数量
	private String middleCount;
	
	//等级【低】的审核视频数量
	private String lowerCount;

	public String getHightCount() {
		return hightCount;
	}

	public void setHightCount(String hightCount) {
		this.hightCount = hightCount;
	}

	public String getMiddleCount() {
		return middleCount;
	}

	public void setMiddleCount(String middleCount) {
		this.middleCount = middleCount;
	}

	public String getLowerCount() {
		return lowerCount;
	}

	public void setLowerCount(String lowerCount) {
		this.lowerCount = lowerCount;
	}
}