package cn.qiniu.controller.video;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.qiniu.controller.common.BaseController;
import cn.qiniu.delegator.entity.VideoForm;
import cn.qiniu.entity.SysUser;
import cn.qiniu.entity.VideoCountByLevel;
import cn.qiniu.entity.VideoListSearch;
import cn.qiniu.entity.basic.AjaxJson;
import cn.qiniu.form.video.VideoSubmitForm;
import cn.qiniu.service.video.VideoListService;

@RestController
@Scope("prototype")
public class VideoListController extends BaseController{
	
	@Autowired
	private VideoListService videoListService;
	@Autowired
	private String bucketDomain;
	
	private String errorStr ="视频列表";
	
	@RequestMapping(value = "/video/videoPageList")
	public ModelAndView tbPageList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("video/videoList");
	}
	
	/**
	 * 获取视频列表
	 */
	@ResponseBody
	@RequestMapping(value = "/video/videoPageListData", method = RequestMethod.POST)
	public AjaxJson getVideoList(VideoListSearch search){
		AjaxJson j = new AjaxJson();
		LinkedHashMap<String, Object> body = new LinkedHashMap<>();
		List<VideoForm> result = null; 
		String pageMaxNum = "";
		try{
			//返回分页数据
			body.put("pageNum", search.getPageNum());
			//返回分页数据
			body.put("pageSize", search.getPageSize());
			//获取视频列表
			result = videoListService.tbPageList(search);
			body.put("data", result);
			pageMaxNum = videoListService.getPageMaxSize(search);
			//最大页数
			body.put("pageMaxNum", pageMaxNum);
			//bucket域名
			body.put("bucketDomain", bucketDomain);
		} catch (Exception e) {
			log.error("[查询"+errorStr+"记录报错]", e);
		}
		j.setBody(body);
		return j;
	}
	
	/**
	 * 提交单个视频列表
	 */
	@ResponseBody
	@RequestMapping(value = "/video/submitVideo", method = RequestMethod.POST)
	public AjaxJson submitVideo(VideoSubmitForm form){
		AjaxJson j = new AjaxJson();
		LinkedHashMap<String, Object> body = new LinkedHashMap<>();
		String result = ""; 
		
		try{
//			SysUser user = getLoginUser();
//			if (user == null)
//				return j;

			result = videoListService.submitForm(form);
			body.put("data", result);
		} catch (Exception e) {
			log.error("[查询"+errorStr+"记录报错]", e);
		}
		j.setBody(body);
		return j;
	}
	

	/**
	 * 查询危险等级【高】【中】【低】审核视频数量
	 */
	@ResponseBody
	@RequestMapping(value = "/video/getCountByDangerLevel")
	private AjaxJson getCountByDangerLevel(HttpServletRequest request, HttpServletResponse response){
		AjaxJson j = new AjaxJson();
		//定义返回值
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		try {
			//获取当前登录人
			SysUser user = getLoginUser();
			VideoListSearch search = new VideoListSearch();
			//保存登录者id
			search.setLoginName(user.getLoginNm());
			VideoCountByLevel videoCountByLevel = videoListService.getCountByDangerLevel(search);
			body.put("videoCountByLevel", videoCountByLevel);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
		}
		j.setBody(body);
		return j;
	}
	/**
	 * 删除功能
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/video/videoDelete", method = RequestMethod.POST)
	   public AjaxJson videoDelete(String  videoId) {
		   AjaxJson j = new AjaxJson();
			LinkedHashMap<String, Object> body = new LinkedHashMap<>();
			try {
				int result= videoListService.videoDelete(videoId);
				body.put("data", result);
			} catch (Exception e) {
				e.printStackTrace();
				j.setSuccess(false);
			}
			j.setBody(body);
			return j;
	   }
}
