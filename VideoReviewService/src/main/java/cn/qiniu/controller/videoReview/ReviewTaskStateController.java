package cn.qiniu.controller.videoReview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.qiniu.entity.ReviewTaskState;
import cn.qiniu.service.videoReview.ReviewTaskStateService;

@RestController
public class ReviewTaskStateController {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(ReviewTaskStateController.class);
	@Autowired
	private ReviewTaskStateService reviewTaskStateService;
	
	/**
	 * 根据jobId获取审核任务状态
	 * @return jobId
	 */
	@RequestMapping(value = "/v1/job/{jobId}/state", method = RequestMethod.GET)
	private ReviewTaskState getJobState(@PathVariable String jobId){
		logger.info(String.format("get getJobState start.param:%s",jobId));
		ReviewTaskState result = reviewTaskStateService.getTaskState(jobId);
		logger.info(String.format("get getJobState end.result:%s",JSONObject.toJSONString(result)));
		return result;
	}
}
