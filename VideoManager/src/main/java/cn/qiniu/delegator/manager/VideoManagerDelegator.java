package cn.qiniu.delegator.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.framework.httpclient.HttpClientTemplate;

/**
 * 视频管理服务Delegator
 * 
 * @author Ling QiongFang
 * @version 2017-09-26
 */
@Component
public class VideoManagerDelegator {
	// 获取视频管理列表
	private static final String VIDEO_PAGELIST = "/v1/videos";

	private static final String VIDEO_MAXPAGESIZE = "/v1/maxPageSize";
	// 获取视频信息
	private static final String VIDEO_INFO = "/v1/video";

	// HttpClient
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

	/**
	 * 获取视频列表(分页)
	 * 
	 * @param pageNum
	 *            分页页数
	 * @param pageSize
	 *            分页大小
	 * @param reviewStage
	 *            审核阶段(0 ：未审核； 1 ：初步审核（机审结果）； 2 ：审核结束)
	 * @param reviewLevel
	 *            审核的结果等级(0 ：无； 1 ：低； 2 ：中； 3 ：高)
	 * @return 视频管理列表
	 */
	public String getVideoList(String pageNum, String pageSize,String[] reviewLevels,String reviewStage) {
		String reviewLevel = reviewLevels[0];
		for (int i = 1; i < reviewLevels.length; i++) {
			reviewLevel = reviewLevel + "," + reviewLevels[i];
		}
		//拼接请求地址
		String url = getManagerService(VIDEO_PAGELIST)+"?pageNum="+pageNum+"&pageSize="+pageSize+"&reviewLevel="+reviewLevel+"&reviewStage="+reviewStage;
//		String url = getManagerService(VIDEO_PAGELIST)+"?pageNum="+pageNum+"&pageSize="+pageSize+"&reviewLevel="+reviewLevel;
		return httpClientTemplate.doGet(url);
	}
	
	/**
	 * 获取视频列表最大页数
	 * @param pageNum
	 * @param pageSize
	 * @param reviewLevels
	 * @return
	 */
	public String getMaxPageSize(String pageNum, String pageSize,String[] reviewLevels) {
		String reviewLevel = reviewLevels[0];
		for (int i = 1; i < reviewLevels.length; i++) {
			reviewLevel = reviewLevel + "," + reviewLevels[i];
		}
		//拼接请求地址
		String url = getManagerService(VIDEO_MAXPAGESIZE)+"?pageNum="+pageNum+"&pageSize="+pageSize+"&reviewLevel="+reviewLevel;
		return httpClientTemplate.doGet(url);
	}

	/**
	 * 获取视频信息
	 * 
	 * @param videoId
	 *            视频ID
	 * @return 视频信息
	 */
	public String getVideoInfo(String videoId) {
		String url = getManagerService(VIDEO_INFO) + "/" + videoId;
		return httpClientTemplate.doGet(url);
	}
}
