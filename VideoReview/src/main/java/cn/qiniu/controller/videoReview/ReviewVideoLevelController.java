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
import cn.qiniu.entity.levelSetting.LevelSettingOps;
import cn.qiniu.service.videoReview.ReviewVideoLevelService;
import cn.qiniu.util.common.CommonUtil;

@RestController
public class ReviewVideoLevelController extends BaseController {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(ReviewVideoLevelController.class);
	@Autowired
	private ReviewVideoLevelService reviewVideoLevelService;

	@Autowired
	private CommonDelegator commonDelegator;
	
	/**
	 * 设置视频审核配置
	 */
	@RequestMapping(value = "/v1/config", method = RequestMethod.POST)
	private int setReviewVideolevel(HttpServletRequest request) {
		int count = 0;
		//获取请求参数
		String str = CommonUtil.getPostRequestParam(request);
		logger.info(String.format("post setConfig start.param:%s",str));
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
		logger.info(String.format("post setConfig validateAuthorization:%s",flg));
		if(flg){
			count = reviewVideoLevelService.setReviewVideolevel(ops);
		}
		logger.info(String.format("post setConfig end.result:%s",String.valueOf(count)));
		return count;
	}

	/**
	 * 获取视频审核配置
	 */
	@RequestMapping(value = "/v1/config", method = RequestMethod.GET)
	private String getReviewVideolevel(HttpServletRequest request) {
		logger.info("get getConfig start");
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
		logger.info(String.format("get getConfig validateAuthorization:%s",flg));
		if(flg){
			result = reviewVideoLevelService.getReviewVideolevel();
		}
		logger.info(String.format("get setConfig end.result:%s",result));
		return result;
	}
}
