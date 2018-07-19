package cn.qiniu.service.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.delegator.review.VideoReviewLevelDelegator;
import cn.qiniu.entity.ReviewLevelSetting;
import cn.qiniu.entity.levelSetting.Level;
import cn.qiniu.entity.levelSetting.LevelSettingOp;
import cn.qiniu.entity.levelSetting.LevelSettingOps;
import cn.qiniu.util.common.Constant;

@Service
public class VideoReviewLevelService {
	@Autowired
	private VideoReviewLevelDelegator videoReviewLevelDelegator;
	@Autowired
	private CommonDelegator commonDelegator;
	
	/**
	 * 设置视频审核配置
	 * 
	 * @return
	 */
	public int setReviewVideolevel(String json) {
		String op = "";
		LevelSettingOps ops = new LevelSettingOps();
		LevelSettingOp settingOp = new LevelSettingOp();
		Level pulp = new Level();
		Level terror = new Level();
		Level politician = new Level();
		List<ReviewLevelSetting> list = commonDelegator.parseArray(json, ReviewLevelSetting.class);
		int pulpSize=0;
		int terrorSize=0;
		int politicianSize=0;
		if(list != null && list.size()>0){
			for(ReviewLevelSetting info : list){
				op = info.getOpsOp();
				switch (op) {
				case Constant.REVIEW_OP_PULP :
					pulpSize++;
					break;
				case Constant.REVIEW_OP_TERROR :
					terrorSize++;
					break;
				case Constant.REVIEW_OP_POLITICIAN :
					politicianSize++;
					break;
				default:
					break;
				}
			}
		}
		int[] pulpLevel = new int[pulpSize];;
		int[] terrorLevel = new int[terrorSize];;
		int[] politicianLevel = new int[politicianSize];;
		
		if(list != null && list.size()>0){
			for(ReviewLevelSetting info : list){
				op = info.getOpsOp();
				switch (op) {
					case Constant.REVIEW_OP_PULP :
						pulpLevel[Integer.parseInt(info.getOpsOpLabel())] = Integer.parseInt(info.getOpsOpLevel());
						break;
					case Constant.REVIEW_OP_TERROR :
						terrorLevel[Integer.parseInt(info.getOpsOpLabel())] = Integer.parseInt(info.getOpsOpLevel());
						break;
					case Constant.REVIEW_OP_POLITICIAN :
						politicianLevel[Integer.parseInt(info.getOpsOpLabel())] = Integer.parseInt(info.getOpsOpLevel());
						break;
					default:
						break;
				}
			}
		}
		pulp.setLevel(pulpLevel);
		terror.setLevel(terrorLevel);
		politician.setLevel(politicianLevel);
		settingOp.setPulp(pulp);
		settingOp.setTerror(terror);
		settingOp.setPolitician(politician);
		ops.setOps(settingOp);
		return videoReviewLevelDelegator.setReviewVideoLevel(ops);
	}

	/**
	 * 获取视频审核配置
	 * 
	 * @return
	 */
	public List<ReviewLevelSetting> getReviewVideolevel() {
		List<ReviewLevelSetting> list = videoReviewLevelDelegator
				.getReviewVideoLevel();
		return list;
	}
}
