package cn.qiniu.controller.videoReview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.qiniu.entity.ReviewVideoTask;
import cn.qiniu.form.entity.ManualForm;
import cn.qiniu.service.videoReview.ReviewVideoService;

/**
 * 视频审核控制类
 * @author    Ling
 * @version   2017-09-26
 */
@RestController
public class ReviewVideoController {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(ReviewVideoController.class);
	// 视频审核服务
	@Autowired
	private ReviewVideoService service;
	
	/**
	 * 获取审核视频任务列表
	 * @return jobId列表
	 */
	@RequestMapping(value = "/v1/jobs", method = RequestMethod.GET)
	public List<ReviewVideoTask> getJobs(){
		logger.info("get getJobs start");
		List<ReviewVideoTask> list = service.selectReviewVideoTaskList();
		logger.info(String.format("get getJobs end.result:%s",JSONObject.toJSONString(list)));
		return list;
	}
	
	/**
	 * 根据jobId 获取视频审核结果 
	 */
	@RequestMapping(value = "/reviewResult/{jobId}", method = RequestMethod.GET)
	public String getVideoApplication(@PathVariable String jobId) {
		logger.info(String.format("get getVideoApplication start.param:%s",jobId));
		String json = service.getVideoApplication(jobId);
		logger.info(String.format("get getVideoApplication end.result:%s",json));
		return json;
	}
	
	/**
	 * 根据视频id 提交视频人工审核结果
	 * @param videoId
	 * @param form
	 * @return uuid
	 */
	@RequestMapping(value = "/v1/video/manual/{videoId}", method = RequestMethod.POST)
	public String submitVideoManualApplication(@PathVariable String videoId,@RequestBody ManualForm form){
		logger.info(String.format("post submitVideoManualApplication start.param:%s",videoId));
		String submitUUID = service.submitManualApplication(form,videoId);
		Map<String, String> result = new HashMap<>();
		result.put("submitUUID", submitUUID);
		String json = JSON.toJSONString(result);
		logger.info(String.format("post submitVideoManualApplication end.param:%s",json));
		return json;
	}
}
