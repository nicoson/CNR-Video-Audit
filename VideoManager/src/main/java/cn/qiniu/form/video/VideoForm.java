package cn.qiniu.form.video;
/**
 * 获取视频管理列表 页面显示
 * @author html
 *
 */
public class VideoForm {
		//id
		private String id;
		
		//视频资源原始地址
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
