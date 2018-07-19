package cn.qiniu.controller.video;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.qiniu.controller.common.BaseController;
import cn.qiniu.entity.AssignReviewSearch;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.basic.AjaxJson;
import cn.qiniu.service.video.AssignReviewService;

@Controller
@Scope("prototype")
public class AssignReviewController extends BaseController{
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(AssignReviewController.class);
	
	@Autowired
    private AssignReviewService assignReviewService;

	/**
     * 获取待审核视频信息
     * @param search 查询条件
     * @return 待审核视频信息
     */
	@ResponseBody
    @RequestMapping(value = "/assignReview", method = RequestMethod.POST)
    public AjaxJson getCheckInfoList(AssignReviewSearch search){
		logger.info(String.format("post getCheckInfoList start.param:%s",JSONObject.toJSONString(search)));
		AjaxJson j = new AjaxJson();
		LinkedHashMap<String, Object> body = new LinkedHashMap<>();
	/*	SysUser user = getLoginUser();
		String loginName = user.getLoginNm();
		search.setLoginName(loginName);*/
		//查询视频列表-分页
		ReviewVideoManager result = assignReviewService.getCheckInfo(search);
		logger.info(String.format("post getCheckInfoList end.result:%s",JSONObject.toJSONString(result)));
		body.put("checkVideo", result);
		j.setBody(body);
		return j;
    }
}
