package cn.qiniu.mapper;

import java.util.List;

import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.form.video.VideoListSearch;

public interface VideoPageListMapper {
	/**
	 * 获取视频列表
	 * @param search
	 * @return
	 */
	List<ReviewVideoManager> selectVideoPageList(VideoListSearch search);
	
	String selectMaxPageSize(VideoListSearch search);
}