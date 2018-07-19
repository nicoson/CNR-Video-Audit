package cn.qiniu.controller.review;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.ReviewLevelSetting;
import cn.qiniu.service.review.VideoReviewLevelService;

@RestController
@Scope("prototype")
public class VideoLevelController {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(VideoLevelController.class);
	@Autowired
	private VideoReviewLevelService videoReviewLevelService;
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	@RequestMapping(value = "/showSetLevel")
	public ModelAndView tbPageList(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("video/setLevel");
	}
	
	/**
	 * 配置视频审核配置
	 * 
	 */
	@RequestMapping(value = "/v1/config/Postlevel", method = RequestMethod.POST)
	private int setReviewVideolevel(HttpServletRequest request) {
		//从request中获取value
		String danderLeveData = request.getParameter("danderLeveData");
		logger.info(String.format("post setReviewVideolevel start.param:%s",danderLeveData));
		int count = videoReviewLevelService.setReviewVideolevel(danderLeveData);
		logger.info("post setReviewVideolevel end.");
		return count;
	}
	
	/**
	 * 获取视频审核配置
	 */
	@RequestMapping(value = "/v1/config/level", method = RequestMethod.GET)
	private List<ReviewLevelSetting> getReviewVideolevel() {
		logger.info("post getReviewVideolevel start");
		List<ReviewLevelSetting> list = videoReviewLevelService.getReviewVideolevel();
		logger.info(String.format("post getReviewVideolevel end.result:%s",JSONObject.toJSONString(list)));
		return list;
	}
}
