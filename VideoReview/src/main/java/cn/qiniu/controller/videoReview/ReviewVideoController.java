package cn.qiniu.controller.videoReview;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.qiniu.controller.common.BaseController;
import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.videoReview.ReviewVideoTask;
import cn.qiniu.entity.videoReview.VideoForm;
import cn.qiniu.form.entity.ManualForm;
import cn.qiniu.service.videoReview.ReviewVideoService;
import cn.qiniu.util.common.CommonUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 视频审核控制类
 * @author    Ling
 * @version   2017-09-26
 */
@RestController
public class ReviewVideoController extends BaseController{
	
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(ReviewVideoController.class);

	@Autowired
	private ReviewVideoService reviewVideoService;
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	/**
	 * 获取审核视频任务列表
	 * @return
	 */
	@RequestMapping(value = "/v1/jobs", method = RequestMethod.GET)
	public List<ReviewVideoTask> getVideoTaskList(HttpServletRequest request){
		List<ReviewVideoTask> result = null;
		logger.info("get getVideoTaskList start");
		String authorization = request.getHeader("Authorization");
		// 获取请求类型
		String method = request.getMethod();
		StringBuffer url = request.getRequestURL();
		String path = new String(url);
		//获取请求内容类型
		String contentType = request.getContentType();
		//验证鉴权
		boolean flg = validateAuthorization(path,authorization,method,contentType,null);
		logger.info(String.format("get jobs validateAuthorization:%s",flg));
		if(flg){
			result = reviewVideoService.getReviewVideoTaskList();
		}
		logger.info(String.format("get getVideoTaskList end.result:%s",JSONObject.toJSONString(result)));
		return result;
	}
	/**
	 * 根据jobId  获取视频审核结果
	 * @param jobId
	 * @return 视频审核结果
	 * @author lqf
	 */
	@RequestMapping(value = "/job/{jobId}/result", method = RequestMethod.GET)
	public VideoForm getReviewVideoId(@PathVariable String jobId,HttpServletRequest request){
		logger.info(String.format("get getReviewVideoId start.param:%s",jobId));
		VideoForm  videoForm = new VideoForm();
		String authorization = request.getHeader("Authorization");
		// 获取请求类型
		String method = request.getMethod();
		StringBuffer url = request.getRequestURL();
		String path = new String(url);
		//获取请求内容类型
		String contentType = request.getContentType();
		//验证鉴权
		boolean flg = validateAuthorization(path,authorization,method,contentType,null);
		logger.info(String.format("get getReviewVideoId validateAuthorization:%s",flg));
		if(flg){
			videoForm = reviewVideoService.getReviewVideoId(jobId);
		}
		logger.info(String.format("get getReviewVideoId end.result:%s",JSONObject.toJSONString(videoForm)));
		return videoForm;
	}
	
	/**
	 * 提交人工审核结果
	 * @param videoId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v1/video/{videoId}/manual", method = RequestMethod.POST)
	public String submitManualRewview(@PathVariable String  videoId,HttpServletRequest request){
		logger.info("get submitManualRewview start");
		String  result = null;
		//获取请求参数
		String str = CommonUtil.getPostRequestParam(request);
		JSONObject jsonObject = JSONObject.parseObject(str);
		ManualForm form = commonDelegator.parseObject(jsonObject.toJSONString(),ManualForm.class);
		String authorization = request.getHeader("Authorization");
		// 获取请求类型
		String method = request.getMethod();
		StringBuffer url = request.getRequestURL();
		String path = new String(url);
		//获取请求内容类型
		String contentType = request.getContentType();
		//验证鉴权
		boolean flg = validateAuthorization(path,authorization,method,contentType,str.getBytes());
		logger.info(String.format("get submitManualRewview validateAuthorization:%s",flg));
		if(flg){
			result = reviewVideoService.submitManualRewview(videoId,form);
		}
		logger.info(String.format("get submitManualRewview end.result:%s",result));
		return result;
	}
}
