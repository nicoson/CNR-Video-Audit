package cn.qiniu.controller.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.qiniu.form.video.AssignReviewSearch;
import cn.qiniu.service.video.AssignReviewService;

@RestController
public class AssignReviewController {
    
	@Autowired
	private AssignReviewService service;
	/**
     * 获取待审核视频信息
     * @param search 查询条件
     * @return 待审核视频信息
     */
    @RequestMapping(value = "/assignReview", method = RequestMethod.POST)
    public Object getCheckInfoList(@RequestBody AssignReviewSearch search){
    	Object result = service.assignReview(search);
    	return result;
    }
}
