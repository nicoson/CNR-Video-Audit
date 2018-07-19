package cn.qiniu.service.videoReview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.delegator.videoReviewDelegator.ReviewTaskStateDelegator;
import cn.qiniu.entity.videoReview.ReviewTaskState;

@Service
public class ReviewTaskStateService {
	
	@Autowired
	private ReviewTaskStateDelegator reviewTaskStateDelegator;
	
	/**
	 * 根据jobId获取任务状态
	 * @param jobId
	 * @return
	 */
	public ReviewTaskState getTaskState(String jobId){
		return reviewTaskStateDelegator.getTaskState(jobId);
	}
}
