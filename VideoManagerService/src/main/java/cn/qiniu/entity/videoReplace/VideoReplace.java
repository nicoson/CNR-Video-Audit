package cn.qiniu.entity.videoReplace;

public class VideoReplace {
	private String id;
	private String source;
	private VideoReplaceUri video;
	private String reviewStage;
	private VideoReplaceLevel review;

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

	public VideoReplaceUri getVideo() {
		return video;
	}

	public void setVideo(VideoReplaceUri video) {
		this.video = video;
	}

	public String getReviewStage() {
		return reviewStage;
	}

	public void setReviewStage(String reviewStage) {
		this.reviewStage = reviewStage;
	}

	public VideoReplaceLevel getReview() {
		return review;
	}

	public void setReview(VideoReplaceLevel review) {
		this.review = review;
	}

}
