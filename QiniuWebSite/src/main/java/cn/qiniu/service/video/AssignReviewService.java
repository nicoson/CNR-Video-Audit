package cn.qiniu.service.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.delegator.video.AssignReviewDelegator;
import cn.qiniu.entity.AssignReviewSearch;
import cn.qiniu.entity.ReviewVideoManager;

@Service
public class AssignReviewService {
	@Autowired
    private AssignReviewDelegator delegator;
	
	 /**
	  * 获取审核视频
	  */
	 public ReviewVideoManager getCheckInfo(AssignReviewSearch search){
		 return this.delegator.getCheckInfo(search);
	 }
}
