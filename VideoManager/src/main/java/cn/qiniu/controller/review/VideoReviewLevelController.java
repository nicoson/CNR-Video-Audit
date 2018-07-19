package cn.qiniu.controller.review;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.qiniu.controller.common.BaseController;
import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.levelSetting.LevelSettingOps;
import cn.qiniu.service.review.VideoReviewLevelService;
import cn.qiniu.util.common.CommonUtil;

@RestController
public class VideoReviewLevelController extends BaseController {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(VideoReviewLevelController.class);
	@Autowired
	private VideoReviewLevelService videoReviewLevelService;
	@Autowired
	private CommonDelegator commonDelegator;
	
	/**
	 * 设置视频审核配置
	 */
	@RequestMapping(value = "/v1/con2", method = RequestMethod.POST)
	private int setReviewVideolevel(HttpServletRequest request) {
	
		int count = 0;
		//获取请求参数
		String str = CommonUtil.getPostRequestParam(request);
		logger.info(String.format("post setReviewVideolevel start.param:%s",str));
		LevelSettingOps ops = commonDelegator.parseObject(str, LevelSettingOps.class);
		String authorization = request.getHeader("Authorization");
		// 获取请求类型
		String method = request.getMethod();
		StringBuffer url = request.getRequestURL();
		String path = new String(url);
		//获取请求内容类型
		String contentType = request.getContentType();
		//验证鉴权
		boolean flg = validateAuthorization(path,authorization,method,contentType,str.getBytes());
		logger.info(String.format("post setReviewVideolevel validateAuthorization:%s",flg));
		if(flg){
			count = videoReviewLevelService.setReviewVideolevel(ops);
		}
		logger.info("post setReviewVideolevel end");
		return count;
	}

	/**
	 * 获取视频审核配置
	 */
	@RequestMapping(value = "/v1/con2", method = RequestMethod.GET)
	private String getReviewVideolevel(HttpServletRequest request) {
		logger.info("get getReviewVideolevel start");
		String result = null;
		String authorization = request.getHeader("Authorization");
		// 获取请求类型
		String method = request.getMethod();
		StringBuffer url = request.getRequestURL();
		String path = new String(url);
		//获取请求内容类型
		String contentType = request.getContentType();
		//验证鉴权
		boolean flg = validateAuthorization(path,authorization,method,contentType,null);
		logger.info(String.format("get getReviewVideolevel validateAuthorization:%s",flg));
		if(flg){
			result = videoReviewLevelService.getReviewVideolevel();
		}
		logger.info(String.format("get getReviewVideolevel end.result:%s",result));
		return result;
	}
}
