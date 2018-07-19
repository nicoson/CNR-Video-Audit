package cn.qiniu.form.video;

/**
 * 审核信息表查询条件
 * @author html
 *
 */
public class CheckInfoSearch {
		//操作人id
		private String loginName;
		
		//视频id
		private String videoId;

		public String getLoginName() {
			return loginName;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		public String getVideoId() {
			return videoId;
		}

		public void setVideoId(String videoId) {
			this.videoId = videoId;
		}
}
