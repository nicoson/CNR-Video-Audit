package cn.qiniu.entity;

import java.util.List;

/**
 * 单个视频的所有详细信息
 * @author ch
 *
 */
public class VideoInformation  {
	//视频信息
	private ReviewVideoManager videoInfo;
	
	//获取审核视频管理片段表数据
	private List<ReviewVideoManagerSegments> videoSegements;
	 
	//获取审核视频管理截帧表数据
	private List<ReviewVideoManagerCuts> videoCuts;
	
//	//获取视频时长管理表数据
//	private ReviewVideoManagerTime videoTimeInfo;

	public ReviewVideoManager getVideoInfo() {
		return videoInfo;
	}

	public void setVideoInfo(ReviewVideoManager videoInfo) {
		this.videoInfo = videoInfo;
	}

	public List<ReviewVideoManagerSegments> getVideoSegements() {
		return videoSegements;
	}

	public void setVideoSegements(List<ReviewVideoManagerSegments> videoSegements) {
		this.videoSegements = videoSegements;
	}

	public List<ReviewVideoManagerCuts> getVideoCuts() {
		return videoCuts;
	}

	public void setVideoCuts(List<ReviewVideoManagerCuts> videoCuts) {
		this.videoCuts = videoCuts;
	}
//
//	public ReviewVideoManagerTime getVideoTimeInfo() {
//		return videoTimeInfo;
//	}
//
//	public void setVideoTimeInfo(ReviewVideoManagerTime videoTimeInfo) {
//		this.videoTimeInfo = videoTimeInfo;
//	}
	
}