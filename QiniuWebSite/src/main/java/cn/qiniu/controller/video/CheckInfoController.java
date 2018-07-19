package cn.qiniu.controller.video;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.qiniu.controller.common.BaseController;
import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.CheckInfoSearch;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerSegments;
import cn.qiniu.entity.SysUser;
import cn.qiniu.entity.VideoInformation;
import cn.qiniu.entity.basic.AjaxJson;
import cn.qiniu.form.video.ReviewListSearch;
//import cn.qiniu.service.video.CheckDetailInfoService;
import cn.qiniu.service.video.CheckInfoService;
import cn.qiniu.service.video.VideoListService;

import com.alibaba.fastjson.JSONObject;

@Controller
@Scope("prototype")
public class CheckInfoController extends BaseController {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(CheckInfoController.class);
	@Autowired
	private CheckInfoService checkInfoService;
	@Autowired
	private VideoListService videoListService;
	@Autowired
	private CommonDelegator commonDelegator;
	
	/*
	 * 审核列表-分页
	 * 
	 * */
	@RequestMapping(value = "/showCheckInfoList")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,ReviewListSearch search,ModelMap modelMap) {
		modelMap.put("search",search);
		return new ModelAndView("video/checkInfoList", modelMap);
	}
	
	/**
	 * 获取审核视频列表
	 */
	@ResponseBody
	@RequestMapping(value = "/video/showCheckInfoPageListDate")
	private AjaxJson getVideoList(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap,ReviewListSearch search){
		logger.info(String.format("getVideoList start.param:%s",JSONObject.toJSONString(search)));
		AjaxJson j = new AjaxJson();
		LinkedHashMap<String, Object> body = new LinkedHashMap<>();
		//获取当前登录人
		SysUser user = getLoginUser();
		//保存登录者id
		search.setLoginName(user.getLoginNm());
		//查询视频列表
		List<ReviewVideoManager> result = checkInfoService.getCheckVideoList(search);
		logger.info(String.format("getVideoList end.result:%s",JSONObject.toJSONString(result)));
		body.put("checkVideoList", result);
		j.setBody(body);
		return j;
	}
	
	/**
	 * 点击审核按钮，获取视频数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check/checkVideo")
	private AjaxJson videoDelete(HttpServletRequest request, HttpServletResponse response,CheckInfoSearch search){
		AjaxJson j = new AjaxJson();
		//定义返回值
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		try {
			//获取视频所有数据
			VideoInformation info = checkInfoService.getVideoInformation(search);
			body.put("info", info);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
		}
		j.setBody(body);
		return j;
	}
	
	/**
	 * 【审核】按钮弹出页面，【确认】按钮操作
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check/checkVideoInReview")
	private AjaxJson checkVideoInReview(HttpServletRequest request, HttpServletResponse response){
		AjaxJson j = new AjaxJson();
		String jsonData = request.getParameter("checkedData");
		
		ReviewVideoManagerSegments segments = commonDelegator.parseObject(jsonData, ReviewVideoManagerSegments.class);
		//定义返回值
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		try {
			//获取当前登录者
			SysUser user = getLoginUser();
			//获取视频所有数据
			int result = checkInfoService.checkVideoInReview(segments,user);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
		}
		j.setBody(body);
		return j;
	}
	
	/**
	 * 视频审核页面-审核完成-人审结束
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check/videoManualFinish")
	private AjaxJson videoManualFinish(HttpServletRequest request, HttpServletResponse response){
		AjaxJson j = new AjaxJson();
		String jsonData = request.getParameter("videoTimeData");
		//获取当前登录人
		SysUser user = getLoginUser();
		
		List<ReviewVideoManagerSegments> list = commonDelegator.parseArray(jsonData, ReviewVideoManagerSegments.class);
		
		//定义返回值
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		try {
			//更新数据
			int count = checkInfoService.videoManualFinish(list,user);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
		}
		j.setBody(body);
		return j;
	}
	
	/**
	 * 点击“x”-人审结束
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check/videoManualFinishByPage")
	private AjaxJson videoManualFinishByPage(HttpServletRequest request, HttpServletResponse response){
		AjaxJson j = new AjaxJson();
		String videoId = request.getParameter("videoId");
		//获取当前登录人
		SysUser user = getLoginUser();
		//定义返回值
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		try {
			//更新数据
			int count = checkInfoService.videoManualFinishByPage(videoId,user);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
		}
		j.setBody(body);
		return j;
	}
}
