package cn.qiniu.service.videoReview;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.entity.ReviewTaskState;
import cn.qiniu.entity.ReviewTaskStateExample;
import cn.qiniu.mapper.ReviewTaskStateMapper;
import cn.qiniu.util.DictUtil;

@Service
public class ReviewTaskStateService {
	@Autowired
	private  ReviewTaskStateMapper mapper;
	
	/**
	 * 拼接条件
	 */
	private ReviewTaskStateExample geteExample(String jobId){
		ReviewTaskStateExample example = new ReviewTaskStateExample();
		example.createCriteria().andJobIdEqualTo(jobId)
		.andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0);
		return example;
	}
	
	/**
	 * 根据jobId获取审核任务状态
	 * @return
	 */
	public ReviewTaskState getTaskState(String jobId){
		List<ReviewTaskState> recordList = mapper.selectByExample(geteExample(jobId));
		if(recordList != null && recordList.size()>0){
			return recordList.get(0);
		}
		return null;
	}
}
