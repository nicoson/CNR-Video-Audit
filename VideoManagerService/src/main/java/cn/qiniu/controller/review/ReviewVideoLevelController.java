package cn.qiniu.controller.review;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.qiniu.entity.ReviewLevelSetting;
import cn.qiniu.entity.levelSetting.LevelSettingOps;
import cn.qiniu.service.review.ReviewVideoLevel;

@RestController
public class ReviewVideoLevelController {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(ReviewVideoLevelController.class);
	@Autowired
	private ReviewVideoLevel reviewVideoLevel;

	/**
	 * 设置视频审核配置
	 */
	@RequestMapping(value = "/v1/con1", method = RequestMethod.POST)
	private int setReviewVideolevel(@RequestBody LevelSettingOps ops) {
		logger.info(String.format("post setReviewVideolevel start.param:%s",JSONObject.toJSONString(ops)));
		int count = reviewVideoLevel.setReviewLevel(ops);
		logger.info("post setReviewVideolevel end");
		return count;
	}

	/**
	 * 获取视频审核配置
	 */
	@RequestMapping(value = "/v1/con1", method = RequestMethod.GET)
	private List<ReviewLevelSetting> getReviewVideolevel() {
		logger.info(String.format("get getReviewVideolevel start"));
		List<ReviewLevelSetting> list = reviewVideoLevel.getReviewLevel();
		logger.info(String.format("get getReviewVideolevel end.result:%s",JSONObject.toJSONString(list)));
		return list;
	}
}
