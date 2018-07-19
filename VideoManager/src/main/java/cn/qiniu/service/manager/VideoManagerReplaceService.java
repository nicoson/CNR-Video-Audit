package cn.qiniu.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.delegator.manager.VideoManagerReplaceDelegator;
import cn.qiniu.entity.videoReplace.VideoReplace;

@Service
public class VideoManagerReplaceService {

	@Autowired
	private VideoManagerReplaceDelegator videoManagerReplaceDelegator;

	/**
	 * 更新视频信息
	 */
	public String replaceVideoInfo(VideoReplace videoReplace) {

		String result = null;
		result = videoManagerReplaceDelegator.replaceVideoInfo(videoReplace);
		return result;

	}

}
