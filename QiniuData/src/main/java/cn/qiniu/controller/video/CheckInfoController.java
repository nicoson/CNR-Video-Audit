package cn.qiniu.controller.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.qiniu.entity.VideoManualFinish;
import cn.qiniu.form.video.CheckInfoForm;
import cn.qiniu.form.video.ReviewListForm;
import cn.qiniu.service.UtilService;
import cn.qiniu.service.video.CheckInfoService;

@RestController
public class CheckInfoController{
	@Autowired
	private CheckInfoService checkInfoService;
	@Autowired
	private UtilService utilService;
	
	/**
     * 视频审核页面-【审核完成】
     * @return
     */
    @RequestMapping(value = "/videoManualFinish", method = RequestMethod.POST)
	public Object videoManualFinish(@RequestBody VideoManualFinish record){
    	return checkInfoService.videoManualFinish(record);
	}
    
    /**
     * 审核视频一览-审核完成
     * @return
     */
    @RequestMapping(value = "/videoManualFinishByPage", method = RequestMethod.POST)
	public Object videoManualFinishByPage(@RequestBody CheckInfoForm form){
    	return checkInfoService.videoManualFinishByPage(form.getSearch());
	}
	
	 /**
     * 获取审核视频一览页面
     *
     * @return
     */
    @RequestMapping(value = "/checkInfoList", method = RequestMethod.POST)
    public Object tbList(@RequestBody ReviewListForm form) {
    	Object list = checkInfoService.getCheckVideoList(form.getSearch());
        return list;
    }

    /**
     * 根据videoId获取审核视频管理表
     * @param form
     * @return
     */
    @RequestMapping(value = "/getVideoInfo", method = RequestMethod.POST)
	public Object getVideoInfo(@RequestBody CheckInfoForm form){
    	Object result = checkInfoService.getVideoInfo(form);
    	return result;
	}
    
    /**
     * 根据videoId获取审核视频管理片段表数据
     * @param form
     * @return
     */
    @RequestMapping(value = "/getVideoSegments", method = RequestMethod.POST)
	public Object getVideoSegments(@RequestBody CheckInfoForm form){
    	Object result = checkInfoService.getVideoSegments(form);
    	return result;
	}
    
    /**
     * 根据videoId获取审核视频管理截帧表数据
     * @param form
     * @return
     */
    @RequestMapping(value = "/getVideoCuts", method = RequestMethod.POST)
	public Object getVideoCuts(@RequestBody CheckInfoForm form){
    	Object result = checkInfoService.getVideoCuts(form);
    	return result;
	}
    
//    /**
//     * 根据videoId获取视频时长管理表数据
//     * @param form
//     * @return
//     */
//    @RequestMapping(value = "/getVideoTimeInfo", method = RequestMethod.POST)
//	public Object getVideoTimeInfo(@RequestBody CheckInfoForm form){
//    	Object result = checkInfoService.getVideoTimeInfo(form);
//    	return result;
//	}
//    
    /**
     * 视频审核页面-【确定】
     * @return
     */
    @RequestMapping(value = "/checkVideoInReview", method = RequestMethod.POST)
	public Object checkVideoInReview(@RequestBody VideoManualFinish record){
    	
    	Object result = checkInfoService.checkVideoInReview(record);
    	return result;
	}
}
