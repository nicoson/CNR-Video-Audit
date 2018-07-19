package cn.qiniu.service.review;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.config.Global;
import cn.qiniu.entity.ReviewLevelSetting;
import cn.qiniu.entity.ReviewLevelSettingExample;
import cn.qiniu.entity.levelSetting.LevelSettingOps;
import cn.qiniu.mapper.ReviewLevelSettingMapper;
import cn.qiniu.util.CommonUtil;
import cn.qiniu.util.DictUtil;

@Service
public class ReviewVideoLevel {
	@Autowired
	private ReviewLevelSettingMapper reviewLevelSettingMapper;

	/**
	 * 设置视频审核配置
	 * 
	 * @param ops
	 * @return
	 */
	public int setReviewLevel(LevelSettingOps ops) {
		ReviewLevelSetting setlevel = new ReviewLevelSetting();
		ReviewLevelSettingExample example = new ReviewLevelSettingExample();
		int result = 0;

		// 设置鉴黄等级
		if (ops.getOps().getPulp() != null) {
			int[] array = ops.getOps().getPulp().getLevel();
			// 删除表中数据
			reviewLevelSettingMapper.deleteByExample(example);
			for (int i = 0; i < array.length; i++) {

				// 设置主键id
				setlevel.setId(CommonUtil.getUUID());
				setlevel.setOpsOp(Global.REVIEW_OP_PULP);
				setlevel.setOpsOpLevel(String.valueOf(array[i]));
				setlevel.setOpsOpLabel(String.valueOf(i));
				// 设置创建时间
				setlevel.setCreatedAt(new Date());
				// 设置修改时间
				setlevel.setUpdatedAt(new Date());
				// 插入数据
				reviewLevelSettingMapper.insertSelective(setlevel);
				result++;

			}
		}
		// 设置暴力 等级
		if (ops.getOps().getTerror() != null) {

			int[] array1 = ops.getOps().getTerror().getLevel();
			for (int i = 0; i < array1.length; i++) {
				// 设置主键id
				setlevel.setId(CommonUtil.getUUID());
				setlevel.setOpsOp(Global.REVIEW_OP_TERROR);
				setlevel.setOpsOpLevel(String.valueOf(array1[i]));
				setlevel.setOpsOpLabel(String.valueOf(i));
				// 设置创建时间
				setlevel.setCreatedAt(new Date());
				// 设置修改时间
				setlevel.setUpdatedAt(new Date());
				// 插入数据
				reviewLevelSettingMapper.insertSelective(setlevel);
				result++;

			}
		}
		// 设置政治等级
		if (ops.getOps().getPolitician() != null) {

			int[] array2 = ops.getOps().getPolitician().getLevel();
			for (int i = 0; i < array2.length; i++) {
				// 设置主键id
				setlevel.setId(CommonUtil.getUUID());
				setlevel.setOpsOp(Global.REVIEW_OP_POLITICIAN);
				setlevel.setOpsOpLevel(String.valueOf(array2[i]));
				setlevel.setOpsOpLabel(String.valueOf(i));
				// 设置创建时间
				setlevel.setCreatedAt(new Date());
				// 设置修改时间
				setlevel.setUpdatedAt(new Date());
				// 插入数据
				reviewLevelSettingMapper.insertSelective(setlevel);
				result++;

			}
		}

		return result;

	}

	/**
	 * 获取视频审核配置
	 * 
	 * @return
	 */
	public List<ReviewLevelSetting> getReviewLevel() {
		// // 定义涉黄等级数组
		// int[] pulplevels = new int[5];
		//
		// List<Integer> pulplevelList = new ArrayList<>();
		// List<Integer> terrorlevelList = new ArrayList<>();
		// List<Integer> politicianlevelList = new ArrayList<>();
		// Ops ops = new Ops();
		//
		// Op terror = new Op();
		//
		// Op pulp = new Op();
		//
		// Op politician = new Op();

		ReviewLevelSettingExample example = new ReviewLevelSettingExample();

		example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0);
		//设置排序
		example.setOrderByClause("ops_op,ops_op_label");//根据表中ops_op排序
		List<ReviewLevelSetting> reviewLevelSettingList = reviewLevelSettingMapper
				.selectByExample(example);

		// for (ReviewLevelSetting e : reviewLevelSettingList) {
		//
		// // 获取视频项目
		//
		// if (Global.REVIEW_OP_PULP.equals(e.getOpsOp())) {
		// // 涉黄
		// // 获取等级
		// int level = Integer.valueOf(e.getOpsOpLevel());
		// // 获取下标
		// int label = Integer.valueOf(e.getOpsOpLabel());
		//
		// // pulplevels[label] = level;
		//
		// pulplevelList.add(label, level);
		// terrorlevelList.add(label, level);
		// politicianlevelList.add(label, level);
		//
		// // int i = pulplevelList.get(label);
		// // i = level;
		//
		// // 定义数组
		// // int [] levels = new int[10];
		// // for(int i=0;i<10;i++){
		// // levels[i]= k;
		// // }
		// //
		// // pulp.setLevel(levels);
		// // ops.setPulp(pulp);
		// }
		//
		// // ops.getOps(e.getOpsOp());
		// // 获取视频项目危险等级
		// // ops.setOpsOpLable(e.getOpsOpLabel());
		// // 获取视频项目等级
		// // ops.setOpsOpLevel(e.getOpsOpLevel());
		// }
		// // 保存涉黄等级数组
		// // pulp.setLevel(pulplevels);
		// pulp.setLevel(pulplevelList);
		// terror.setLevel(terrorlevelList);
		// politician.setLevel(politicianlevelList);
		// ops.setPulp(pulp);
		// ops.setTerror(terror);
		// ops.setPolitician(politician);

		// String json = JSON.toJSONString(ops);

		return reviewLevelSettingList;

	}
}
