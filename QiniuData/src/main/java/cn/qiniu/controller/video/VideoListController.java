package cn.qiniu.controller.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.SysUser;
import cn.qiniu.form.video.VideoListForm;
import cn.qiniu.service.video.VideoListService;

@RestController
public class VideoListController {
	
	@Autowired
	private VideoListService service;

    /**
     * 删除视频
     * @return
     */
    @RequestMapping(value = "/videoDelete", method = RequestMethod.POST)
    public int videoDelete(@RequestBody ReviewVideoManager record) {
    	int result = service.videoDelete(record);
        return result;
    }
    /**
     * 封禁视频
     * @return
     */
    @RequestMapping(value = "/videoClosure", method = RequestMethod.POST)
    public Object videoClosure(@RequestBody VideoListForm form) {
        return  service.videoClosure(form);
    }
    
    /**
     * 查询危险等级对应的审核视频数量
     * @return
     */
    @RequestMapping(value = "/getCountByDangerLevel", method = RequestMethod.POST)
    public Object getCountByDangerLevel(@RequestBody VideoListForm form) {
        return  service.getCountByDangerLevel(form);
    }
    
    /**
     * 获取用户角色
     * @return
     */
    @RequestMapping(value = "/getRoleType", method = RequestMethod.POST)
    public String getRoleType(@RequestBody SysUser user) {
    	return service.getRoleType(user);
    }
    
    
}
