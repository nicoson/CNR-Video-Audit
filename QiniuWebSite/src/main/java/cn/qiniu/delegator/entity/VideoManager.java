package cn.qiniu.delegator.entity;

public class VideoManager {

	private String id;

	private String source;

	private Video video;

	private String reviewStage;

	private Review revier;

	private Segments segments;

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

	public Review getRevier() {
		return revier;
	}

	public void setRevier(Review revier) {
		this.revier = revier;
	}

	public Segments getSegments() {
		return segments;
	}

	public void setSegments(Segments segments) {
		this.segments = segments;
	}

}
