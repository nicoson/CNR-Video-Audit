package cn.qiniu.controller.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.qiniu.entity.videoReview.VideoSubmit;
import cn.qiniu.service.manager.VideoReviewSubmitSrevice;

@RestController
public class VideoReviewSubmitController {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(VideoReviewSubmitController.class);
	@Autowired
	private  VideoReviewSubmitSrevice videoReviewSubmitSrevice;
	
	/**
	 * 提交审核视频
	 */
	@RequestMapping(value = "/v1/jobs", method = RequestMethod.POST)
	public int submitVideo(@RequestBody VideoSubmit record) {
		logger.info(String.format("post submitVideo start.param:%s",JSONObject.toJSONString(record)));
		int count = videoReviewSubmitSrevice.submitVideo(record);
		logger.info("post submitVideo end");
		return count;
	}
}
