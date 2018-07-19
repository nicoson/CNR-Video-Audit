package cn.qiniu.service.videoReview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.delegator.videoReviewDelegator.VideoReviewLevelDelegator;
import cn.qiniu.entity.levelSetting.LevelSettingOps;

@Service
public class ReviewVideoLevelService {
	@Autowired
	private VideoReviewLevelDelegator videoReviewLevelDelegator;

	/**
	 * 设置视频审核配置
	 * 
	 * @param ops
	 * @return
	 */
	public int setReviewVideolevel(LevelSettingOps ops) {
		return videoReviewLevelDelegator.setReviewVideoLevel(ops);
	}

	/**
	 * 获取视频审核配置
	 * 
	 * @return
	 */
	public String getReviewVideolevel() {
		return videoReviewLevelDelegator.getReviewVideoLevel();
	}
}
