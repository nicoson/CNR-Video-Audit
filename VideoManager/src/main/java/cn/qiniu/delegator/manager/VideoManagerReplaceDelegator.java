package cn.qiniu.delegator.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.entity.videoReplace.VideoReplace;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

@Component
public class VideoManagerReplaceDelegator {

	// 获取视频管理列表
	private static final String REPLACE_INFO = "/v1/videoReplace";

	@Autowired
	private HttpClientTemplate httpClientTemplate;

	// 视频管理服务URL
	@Autowired
	private String videoManagerServiceUri;

	/**
	 * 拼接URL
	 * 
	 * @param url
	 *            方法路径
	 * @return 完整URL
	 */
	private String getManagerService(String url) {
		return videoManagerServiceUri + url;
	}

	public String replaceVideoInfo(VideoReplace videoReplace) {
		String url = getManagerService(REPLACE_INFO);
		return httpClientTemplate.doPost(url, videoReplace);
	}
}
