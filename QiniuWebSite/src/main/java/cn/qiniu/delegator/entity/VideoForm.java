package cn.qiniu.delegator.entity;

public class VideoForm {
	private String liveEnd;
	
	//机审状态
	private String reviewStageStr;
	
	private String id;
	
	private String videoType;
	
	private String videoTime;

	private String source;

	private Video video;

	private String reviewStage;
	
	private Review review;
	
	public String getLiveEnd() {
		return liveEnd;
	}

	public void setLiveEnd(String liveEnd) {
		this.liveEnd = liveEnd;
	}

	public String getVideoTime() {
		return videoTime;
	}

	public void setVideoTime(String videoTime) {
		this.videoTime = videoTime;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public String getReviewStageStr() {
		return reviewStageStr;
	}

	public void setReviewStageStr(String reviewStageStr) {
		this.reviewStageStr = reviewStageStr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public String getReviewStage() {
		return reviewStage;
	}

	public void setReviewStage(String reviewStage) {
		this.reviewStage = reviewStage;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

}
