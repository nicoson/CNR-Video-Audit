package cn.qiniu.controller.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.qiniu.entity.videoReplace.VideoReplace;
import cn.qiniu.service.manager.VideoManagerReplaceService;

@RestController
public class VideoManagerReplaceController {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(VideoManagerReplaceController.class);
	@Autowired
	private VideoManagerReplaceService videoManagerReplaceService;

	/**
	 * 更新视频信息
	 * 
	 * @param videoId
	 * @return
	 */
	@RequestMapping(value = "/v1/videoReplace/{videoId}", method = RequestMethod.POST)
	public String renewVideoInfo(@RequestBody VideoReplace videoReplace) {
		logger.info(String.format("post renewVideoInfo start.param:%s",JSONObject.toJSONString(videoReplace)));
		String result = videoManagerReplaceService.replaceVideoInfo(videoReplace);
		logger.info(String.format("post renewVideoInfo end.result:%s",result));
		return result;
	}
}
