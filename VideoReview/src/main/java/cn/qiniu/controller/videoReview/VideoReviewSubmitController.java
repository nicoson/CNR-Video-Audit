package cn.qiniu.controller.videoReview;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.qiniu.controller.common.BaseController;
import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.service.videoReview.VideoReviewSubmitService;
import cn.qiniu.util.common.CommonUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 视频分析提交Controller
 * 
 * @author Chen Hua
 * @version 2017-09-26
 */
@RestController
public class VideoReviewSubmitController extends BaseController {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(VideoReviewSubmitController.class);
	
	@Autowired
	private VideoReviewSubmitService videoReviewSubmitService;
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	/**
	 * 提交审核视频
	 * @param record 视频审核请求内容
	 * @return jobId
	 */
	@RequestMapping(value = "/v1/jobs", method = RequestMethod.POST)
	private Object submitVideo(HttpServletRequest request){
		Object result = null;
		//获取请求参数
		String str = CommonUtil.getPostRequestParam(request);
		logger.info(String.format("post jobs start.param:%s",str));
		JSONObject jsonObject = JSONObject.parseObject(str);
		String authorization = request.getHeader("Authorization");
		// 获取请求类型
		String method = request.getMethod();
		StringBuffer url = request.getRequestURL();
		String path = new String(url);
		//获取请求内容类型
		String contentType = request.getContentType();
		//验证鉴权
		boolean flg = validateAuthorization(path,authorization,method,contentType,str.getBytes());
		logger.info(String.format("post jobs validateAuthorization:%s",flg));
		if(flg){
			result = videoReviewSubmitService.submitVideo(jsonObject);
		}
		logger.info(String.format("post jobs end.result:%s",result));
		return result;
	}
	
	/**
	 * 提交审核视频-视频片段返回数据
	 */
	@RequestMapping(value = "/v1/callback/segment", method = RequestMethod.POST)
	private String segmentCallBack(HttpServletRequest request){
		String result = "";
		//获取请求参数
		String str = CommonUtil.getPostRequestParam(request);
		logger.info(String.format("post jobs callback of segment start.param:%s",str));
		JSONObject jsonObject = JSONObject.parseObject(str);
		
		System.out.println(jsonObject);
		 //数据入库
		result = videoReviewSubmitService.callBackSegment(jsonObject);
		logger.info(String.format("post jobs callback of segment end.result:%s",result));
		return result;
	}
	
	/**
	 * 提交审核视频-视频审核完成返回数据
	 */
	@RequestMapping(value = "/v1/callback/result", method = RequestMethod.POST)
	private String callbackVideoResult(HttpServletRequest request) {
		// 获取请求参数
		String str = CommonUtil.getPostRequestParam(request);
		logger.info(String.format(
				"post jobs callback of result start.param:%s",str));
		JSONObject jsonObject = JSONObject.parseObject(str);
		
		System.out.println(jsonObject);
		// 数据入库
		String result = videoReviewSubmitService.callbackVideoResult(jsonObject
				.toJSONString());
		logger.info(String.format("post jobs callback of result end.result:%s",
				result));
		return result;
	}
	
	/**
	 * 直播视频回调
	 */
	@RequestMapping(value = "/v1/callback/liveBroadcast", method = RequestMethod.POST)
	private String callbackLiveBroadcast(HttpServletRequest request) {
		// 获取请求参数
		String str = CommonUtil.getPostRequestParam(request);
		logger.info(String.format(
				"post jobs callback of liveBroadcast start.param:%s",str));
		JSONObject jsonObject = JSONObject.parseObject(str);
		System.out.println(jsonObject);
		String reqid = videoReviewSubmitService.callbackLiveBroadcast(jsonObject);
		logger.info(String.format("post jobs callback of liveBroadcast end.result:%s",
				reqid));
		return reqid;
	}
	
	/**
	 * 点播视频截取封面回调
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v1/callback/videoCover", method = RequestMethod.POST)
	private String callbackVideoCover(HttpServletRequest request) {
		//获取请求参数
		String str = CommonUtil.getPostRequestParam(request);
		logger.info(String.format(
				"post jobs callback of videoCover start.param:%s",str));
		JSONObject jsonObject = JSONObject.parseObject(str);
		
		System.out.println(jsonObject);
		logger.info(String.format("post jobs callback of videoCover end.result:%s",
				jsonObject));
		return str;
	}
	
	/**
	 * 视频水印回调
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v1/callback/setWatermark", method = RequestMethod.POST)
	private String callbackSetWatermark(HttpServletRequest request) {
		//获取请求参数
		String str = CommonUtil.getPostRequestParam(request);
		logger.info(String.format(
				"post jobs callback of setWatermark start.param:%s",str));
		JSONObject jsonObject = JSONObject.parseObject(str);
		
		System.out.println(jsonObject);
		logger.info(String.format("post jobs setWatermark of videoCover end.result:%s",
				jsonObject));
		return str;
	}
}
