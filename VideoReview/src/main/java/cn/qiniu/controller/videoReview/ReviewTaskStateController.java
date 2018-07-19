package cn.qiniu.controller.videoReview;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.qiniu.controller.common.BaseController;
import cn.qiniu.entity.videoReview.ReviewTaskState;
import cn.qiniu.service.videoReview.ReviewTaskStateService;
import com.alibaba.fastjson.JSONObject;

@RestController
public class ReviewTaskStateController extends BaseController{
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(ReviewTaskStateController.class);
	@Autowired
	private ReviewTaskStateService reviewTaskStateService;
	
	/**
	 * 根据jobId获取审核任务状态
	 * @return jobId
	 */
	@RequestMapping(value = "/v1/job/{jobId}/state", method = RequestMethod.GET)
	private ReviewTaskState getJobState(@PathVariable String jobId,HttpServletRequest request){
		logger.info(String.format("get jobState start.param:%s",jobId));
		ReviewTaskState result = new ReviewTaskState();
		String authorization = request.getHeader("Authorization");
		// 获取请求类型
		String method = request.getMethod();
		StringBuffer url = request.getRequestURL();
		String path = new String(url);
		//获取请求内容类型
		String contentType = request.getContentType();
		//验证鉴权
		boolean flg = validateAuthorization(path,authorization,method,contentType,null);
		logger.info(String.format("get jobState validateAuthorization:%s",flg));
		if(flg){
			result = reviewTaskStateService.getTaskState(jobId);
		}
		logger.info(String.format("get jobState end.param:%s",JSONObject.toJSONString(result)));
		return result;
	}
}
