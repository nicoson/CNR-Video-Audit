package cn.qiniu.service.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.delegator.review.VideoReviewLevelDelegator;
import cn.qiniu.entity.levelSetting.LevelSettingOps;

@Service
public class VideoReviewLevelService {
	@Autowired
	private VideoReviewLevelDelegator videoReviewLevelDelegator;

	/**
	 * 设置视频审核配置
	 * 
	 * @param ops
	 * @return
	 */
	public int setReviewVideolevel(LevelSettingOps ops) {
		int count = 0;
		count = videoReviewLevelDelegator.setReviewVideoLevel(ops);
		return count;
	}

	/**
	 * 获取视频审核配置
	 * 
	 * @return
	 */
	public String getReviewVideolevel() {
		String result = null;
		result = videoReviewLevelDelegator.getReviewVideoLevel();
		return result;
	}
}
