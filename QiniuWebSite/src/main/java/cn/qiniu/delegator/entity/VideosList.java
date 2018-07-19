package cn.qiniu.delegator.entity;

public class VideosList {

	private String id;

	private String source;

	private Video video;

	private String reviewStage;

	private Review review;

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
