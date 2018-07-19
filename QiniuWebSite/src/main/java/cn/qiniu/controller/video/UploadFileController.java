package cn.qiniu.controller.video;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



import com.alibaba.fastjson.JSONObject;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.videoReview.VideoSubmit;
import cn.qiniu.service.video.UploadFileService;

/**
 * 2017-10-18
 * @author lqf
 *文件上传
 */
@RestController
@Scope("prototype")
public class UploadFileController {
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	/**
	 * 获取 上传凭证uploadToken
	 * @return
	 */
	@RequestMapping(value = "/getUploadToken", method = RequestMethod.GET)
	public   String  getUploadToken(){
		String uploadTonken = uploadFileService.getUploadToken();
		  JSONObject uptoken = new JSONObject();
		  uptoken.put("uptoken", uploadTonken);
//			 {"uptoken":"xxx"}
		return  uptoken.toString();
	}
	
	/**
	 *提交  上传的视频
	 * @return
	 */
	@RequestMapping(value = "/submitVideo", method = RequestMethod.POST)
	public String  submitVideo(HttpServletRequest request){
		//获取请求参数
		String jsonRecord = request.getParameter("VideoSubmit");
		VideoSubmit record = commonDelegator.parseObject(jsonRecord, VideoSubmit.class);
		//VideoSubmit record = new VideoSubmit();
		String result = uploadFileService.submitVideo(record);
		return  result;
	}
}
