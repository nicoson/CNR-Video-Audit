package cn.qiniu.form.video;
/**
 * video文件下的实体类 用于获取视频管理列表 页面显示
 * @author html
 *
 */
public class VideoForm {
		private String liveEnd;
	
		private String videoTime;
		//id
		private String id;
		//视频类型
		private String videoType;
		//视频资源原始地址
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
