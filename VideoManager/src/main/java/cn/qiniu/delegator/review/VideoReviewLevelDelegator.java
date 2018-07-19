package cn.qiniu.delegator.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.levelSetting.LevelSettingOps;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

@Component
public class VideoReviewLevelDelegator {
	// 设置视频审核配置
	private static final String SET_LEVEL = "/v1/con1";
	// 获取视频审核配置
	private static final String GET_LEVEL = "/v1/con1";
	@Autowired
	private HttpClientTemplate httpClientTemplate;

	@Autowired
	private CommonDelegator commonDelegator;

	@Autowired
	private String videoManagerServiceUri;

	/**
	 * 拼接https地址
	 * 
	 * @return
	 */
	private String getManagerService(String url) {
		return videoManagerServiceUri + url;
	}

	/**
	 * 设置视频审核配置
	 */
	public int setReviewVideoLevel(LevelSettingOps ops) {
		// 取得审核列表信息
		String response = httpClientTemplate.doPost(getManagerService(SET_LEVEL), ops);
		int i = Integer.parseInt(response);
		return i;
	}

	/**
	 * 获取视频审核配置
	 */
	public String getReviewVideoLevel() {
		// 取得审核列表信息
		return httpClientTemplate.doGet(getManagerService(GET_LEVEL));
	}
}
