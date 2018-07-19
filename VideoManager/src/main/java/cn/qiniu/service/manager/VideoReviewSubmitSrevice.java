package cn.qiniu.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.delegator.manager.VideoReviewSubmitDelegator;
import cn.qiniu.entity.videoReview.VideoSubmit;

@Service
public class VideoReviewSubmitSrevice {

	@Autowired
	private VideoReviewSubmitDelegator videoReviewSubmitDelegator;
	
	
	/**
	 * 提交审核视频
	 * @param 
	 * @return
	 */
	
	public Object submitVideo(VideoSubmit record) {
		return videoReviewSubmitDelegator.submitVideo(record);
	}
	
}