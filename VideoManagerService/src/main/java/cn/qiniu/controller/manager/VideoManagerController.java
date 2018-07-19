package cn.qiniu.controller.manager;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.qiniu.controller.common.BaseController;
import cn.qiniu.service.manager.VideoManagerService;

/**
 * 视频管理Controller
 * 
 * @author Ling QiongFang
 * @version 2017-09-26
 */
@RestController
public class VideoManagerController extends BaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private VideoManagerService videoManagerService;

	/**
	 * 获取视频管理列表
	 * 
	 * @param request
	 * @return 视频列表
	 */
	@RequestMapping(value = "/v1/videos", method = RequestMethod.GET)
	public String getVideos(HttpServletRequest request) {
		logger.info(String.format("get videos start.param:%s",
				getRequestParamJson(request.getParameterMap())));
		// 获取参数
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String[] reviewLevel = request.getParameterValues("reviewLevel");
		String reviewStage = request.getParameter("reviewStage");
		String json = videoManagerService.getVideoList(pageNum, pageSize,
				reviewLevel,reviewStage);
		logger.info(String.format("get videos end.result:%s", json));
		return json;
	}
	
	/**
	 * 获取视频管理列表最大页数
	 * 
	 * @param request
	 * @return 视频列表
	 */
	@RequestMapping(value = "/v1/maxPageSize", method = RequestMethod.GET)
	public int getMaxPageSize(HttpServletRequest request) {
		logger.info(String.format("get getMaxPageSize start.param:%s",
				getRequestParamJson(request.getParameterMap())));
		// 获取参数
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String[] reviewLevel = request.getParameterValues("reviewLevel");
		int manPageSize = videoManagerService.getMaxPageSize(pageNum, pageSize,
				reviewLevel);
		logger.info(String.format("get getMaxPageSize end.result:%s", manPageSize));
		return manPageSize;
	}

	/**
	 * 根据videoId获取视频信息
	 * 
	 * @param videoId
	 * @return 视频信息
	 */
	@RequestMapping(value = "/v1/video/{videoId}", method = RequestMethod.GET)
	public String getVideoInfo(@PathVariable String videoId) {
		logger.info(String.format("get video info start.param:{videoId:%s}",
				videoId));
		// 获取视频信息
		String json = videoManagerService.getVideoInfo(videoId);
		logger.info(String.format("get video info end.result:%s", json));
		return json;
	}
}
