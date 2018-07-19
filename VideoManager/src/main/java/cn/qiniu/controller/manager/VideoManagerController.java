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
import cn.qiniu.util.common.CommonUtil;

import com.alibaba.fastjson.JSONObject;

@RestController
public class VideoManagerController extends BaseController{

	// 日志对象
	private Logger logger = LoggerFactory.getLogger(VideoManagerController.class);
	
	// 视频管理服务
	@Autowired
	private VideoManagerService videoManagerService;
	
	/**
	 * 获取视频列表最大页数
	 * @param request
	 * @return 视频管理列表
	 */
	@RequestMapping(value = "/v1/maxPageSize", method = RequestMethod.GET)
	public String getMaxPageSize(HttpServletRequest request){
		// 分页页数
		String pageNum = request.getParameter("pageNum");
		// 分页大小
		String pageSize = request.getParameter("pageSize");
		// 审核阶段
		String reviewStage=request.getParameter("reviewStage");
		// 审核的结果等级
		String[] reviewLevels = request.getParameterValues("reviewLevel");
		logger.info(String.format("get getMaxPageSize start.param:%s",getRequestParamJson(request.getParameterMap())));
		String	result = videoManagerService.getMaxPageSize(pageNum, pageSize,reviewLevels);
		logger.info(String.format("get getMaxPageSize end.result:%s",result));
		return result;
	}
	
	/**
	 * 获取视频管理列表
	 * @param request
	 * @return 视频管理列表
	 */
	@RequestMapping(value = "/v1/videos", method = RequestMethod.GET)
	public String getVideos(HttpServletRequest request){
		String result = "";
		String reviewLevel="";
		// 分页页数
		String pageNum = request.getParameter("pageNum");
		// 分页大小
		String pageSize = request.getParameter("pageSize");
		// 审核阶段
		String reviewStage=request.getParameter("reviewStage");
		// 审核的结果等级
		String[] reviewLevels = request.getParameterValues("reviewLevel");
		String authorization = request.getHeader("Authorization");
		// 获取请求类型
		String method = request.getMethod();
		StringBuffer url = request.getRequestURL();
		String path = new String(url);
		for (int i = 0; i < reviewLevels.length; i++) {
			reviewLevel =reviewLevel + "&reviewLevel=" + reviewLevels[i];
		}
		//拼接请求地址
		path = path+"?pageNum="+pageNum+"&pageSize="+pageSize+reviewLevel;
		//获取请求内容类型
		String contentType = request.getContentType();
		logger.info(String.format("get videos start.param:%s",getRequestParamJson(request.getParameterMap())));
		//验证鉴权
		boolean flg = validateAuthorization(path,authorization,method,contentType,null);
		logger.info(String.format("get videos validateAuthorization:%s",flg));
		if(flg){
			result = videoManagerService.getVideoList(pageNum, pageSize,reviewLevels,reviewStage);
		}
		
		logger.info("get videos end");
		return result;
	}
	
	/**
	 * 获取视频信息
	 * @param videoId
	 * @return
	 */
	@RequestMapping(value = "/video/{videoId}", method = RequestMethod.GET)
	public String getVideoInfo(@PathVariable String videoId,HttpServletRequest request){
		logger.info(String.format("get getVideoInfo start.param:%s",videoId));
		String result = "";
		String authorization = request.getHeader("Authorization");
		// 获取请求类型
		String method = request.getMethod();
		StringBuffer url = request.getRequestURL();
		String path = new String(url);
		//获取请求内容类型
		String contentType = request.getContentType();
		//验证鉴权
		boolean flg = validateAuthorization(path,authorization,method,contentType,null);
		logger.info(String.format("get getVideoInfo validateAuthorization:%s",flg));
		if(flg){
			result = videoManagerService.getVideoInfo(videoId);
		}
		logger.warn("getVideoApplicationByVideoId："+result);
		return result;
	}
	
	/**
	 * 批量删除文件
	 * @param request
	 * @return 视频管理列表
	 */
	@RequestMapping(value = "/v1/files/delete", method = RequestMethod.POST)
	public String deleteFiles(HttpServletRequest request){
		//获取请求参数
		String str = CommonUtil.getPostRequestParam(request);
		JSONObject jsonObject = JSONObject.parseObject(str);
		logger.info(String.format("post delete files start.param:%s",getRequestBodyJson(jsonObject)));
		String result =videoManagerService.deleteBucketFiles(jsonObject);
		logger.info(String.format("post delete files end.result:%s",result));
		return result;
	}

}
