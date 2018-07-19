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
import cn.qiniu.entity.videoReview.VideoSubmit;
import cn.qiniu.service.manager.VideoReviewSubmitSrevice;
import cn.qiniu.util.common.CommonUtil;

import com.alibaba.fastjson.JSONObject;

@RestController
public class VideoReviewSubmitController extends BaseController{
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(VideoReviewSubmitController.class);
	@Autowired
	private VideoReviewSubmitSrevice videoReviewSubmitSrevice;
	@Autowired
	private CommonDelegator commonDelegator;
	
	/**
	 * 提交审核视频
	 */
	@RequestMapping(value = "/v1/jobs", method = RequestMethod.POST)
	public Object getVideoApplication(HttpServletRequest request) {
		Object result = "";
		String str = CommonUtil.getPostRequestParam(request);
		logger.info(String.format("post getVideoApplication start.param:%s",str));
		JSONObject object = JSONObject.parseObject(str);
		VideoSubmit record = commonDelegator.parseObject(object.toJSONString(),VideoSubmit.class);
		String authorization = request.getHeader("Authorization");
		// 获取请求类型
		String method = request.getMethod();
		StringBuffer url = request.getRequestURL();
		String path = new String(url);
		//获取请求内容类型
		String contentType = request.getContentType();
		//验证鉴权
		boolean flg = validateAuthorization(path,authorization,method,contentType,str.getBytes());
		logger.info(String.format("post getVideoApplication validateAuthorization:%s",flg));
		if(flg){
			result = videoReviewSubmitSrevice.submitVideo(record);
		}
		logger.info(String.format("post getVideoApplication end.result:%s",result));
		return result;
	}
}
