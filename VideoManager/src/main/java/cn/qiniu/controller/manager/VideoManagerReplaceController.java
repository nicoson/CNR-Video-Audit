package cn.qiniu.controller.manager;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.qiniu.controller.common.BaseController;
import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.videoReplace.VideoReplace;
import cn.qiniu.service.manager.VideoManagerReplaceService;
import cn.qiniu.util.common.CommonUtil;

import com.alibaba.fastjson.JSONObject;

@RestController
public class VideoManagerReplaceController extends BaseController{
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(VideoManagerReplaceController.class);
	// 视频更新服务
	@Autowired
	private VideoManagerReplaceService videoManagerReplaceService;
	@Autowired
	private CommonDelegator commonDelegator;

	/**
	 * 更新视频信息
	 * 
	 * @param videoId
	 * @return
	 */
	@RequestMapping(value = "/v1/video/{videoId}", method = RequestMethod.POST)
	public String getVideoInfo(HttpServletRequest request) {
		String result = "";
		String str = CommonUtil.getPostRequestParam(request);
		logger.info(String.format("post getVideoInfo start.param:%s",str));
		JSONObject object = JSONObject.parseObject(str);
		VideoReplace videoReplace = commonDelegator.parseObject(object.toJSONString(),VideoReplace.class);
		String authorization = request.getHeader("Authorization");
		// 获取请求类型
		String method = request.getMethod();
		StringBuffer url = request.getRequestURL();
		String path = new String(url);
		//获取请求内容类型
		String contentType = request.getContentType();
		//验证鉴权
		boolean flg = validateAuthorization(path,authorization,method,contentType,str.getBytes());
		logger.info(String.format("post getVideoInfo validateAuthorization:%s",flg));
		if(flg){
			result = videoManagerReplaceService.replaceVideoInfo(videoReplace);
		}
		logger.info(String.format("post getVideoInfo end.result:%s",result));
		return result;
	}
}
