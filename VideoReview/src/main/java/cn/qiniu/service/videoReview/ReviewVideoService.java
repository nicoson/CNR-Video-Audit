package cn.qiniu.service.videoReview;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.delegator.videoReviewDelegator.VideoReviewDelegator;
import cn.qiniu.entity.videoReview.ReviewVideoTask;
import cn.qiniu.entity.videoReview.VideoForm;
import cn.qiniu.form.entity.ManualForm;

/**
 * 视频审核服务类
 * @author    Ling
 * @version   2017-09-26
 */
@Service
public class ReviewVideoService {
	@Autowired
	private VideoReviewDelegator videoReviewDelegator;
	
	public List<ReviewVideoTask> getReviewVideoTaskList(){
		return videoReviewDelegator.getReviewVideoTaskList();
	}
	/**
	 * 根据jobId
	 * @param jobId
	 * @return 视频审核结果
	 */
	public VideoForm  getReviewVideoId(String jobId){
		return videoReviewDelegator.getReviewVideoId(jobId);
	}
	
	/**
	 * 提交人工审核结果
	 * @param videoId
	 * @return
	 */
	public String submitManualRewview(String videoId,ManualForm form){
		//获取返回的uuid
		return videoReviewDelegator.submitManualRewview(videoId,form);
	}
}
