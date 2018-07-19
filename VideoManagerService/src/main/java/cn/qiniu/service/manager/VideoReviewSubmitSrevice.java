package cn.qiniu.service.manager;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.qiniu.config.Global;
import cn.qiniu.entity.ReviewVideoInformation;
import cn.qiniu.entity.ReviewVideoInformationExample;
import cn.qiniu.entity.ReviewVideoOps;
import cn.qiniu.entity.videoReview.VideoSubmit;
import cn.qiniu.mapper.ReviewVideoInformationMapper;
import cn.qiniu.mapper.ReviewVideoOpsMapper;
import cn.qiniu.util.CommonUtil;
import cn.qiniu.util.DictUtil;

@Service
public class VideoReviewSubmitSrevice {

	@Autowired
	private  ReviewVideoInformationMapper mapper;
	
	@Autowired
	private  ReviewVideoOpsMapper reviewVideoOpsMapper;
	
	
	/**
	 * 拼接条件-videoId
	 */
	private ReviewVideoInformationExample getExample(String videoId){
		ReviewVideoInformationExample example = new ReviewVideoInformationExample();
		example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		.andVideoIdEqualTo(videoId);
		return example;
	}
	
	
	
	/**
	 * 提交审核视频-数据入库
	 * @return jobId
	 */
	@Transactional(readOnly=false)
	public int submitVideo(VideoSubmit record){
		int count = 0;
		try {
			List<ReviewVideoInformation> list = mapper.selectByExample(getExample(record.getData().getAttribute().getId()));
			//如果没有相同videoId的数据
			if(list == null || list.size() == 0){
				// 新增【审核视频任务信息表】数据
				count += makeReviewVideoInformation(record);
				//
				count += makeReviewVideoOps(record);
			}
		} catch (Exception e) {
			e.toString();
			// TODO: handle exception
		}
		return count;
	}
	
	/**
	 * 新增【审核视频任务信息表】数据
	 */
	private int makeReviewVideoInformation(VideoSubmit record){
		ReviewVideoInformation info = new ReviewVideoInformation();
		//设置jobId
		info.setId(record.getJobId());
		//视频唯一标识符		
		info.setVideoId(record.getData().getAttribute().getId());
		//视频资源地址	
		info.setVideoUri(record.getData().getUri());
		//视频信息描述desc	
		info.setVideoDesc(record.getData().getAttribute().getDesc());
		//视频原信息	
//		info.setVideoMeta(record.getData().getAttribute().getMeta());
		//是否等待返回结果
		info.setWait(record.getParams().isWait());
		//审核任务类型		
		info.setReviewType(String.valueOf(record.getParams().getReviewType()));
		//视频归档地址	
		info.setVideoSave(record.getParams().getSave());
		//操作默认的回调地址	
		info.setHookHost(record.getParams().getHook().getHost());
		//创建人	
		info.setCreatedBy(null);
		//创建时间		
		info.setCreatedAt(new Date());
		//更新人		
		info.setUpdatedBy(null);
		//更新时间		
		info.setUpdatedAt(new Date());
		//停止审核标志	
		// TODO
		info.setStopReviewFlg(null);
		//删除标志													
		info.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);
		return mapper.insertSelective(info);
	}
	
	/**
	 * 新增【审核视频操作项目】数据
	 * @param record
	 * @return
	 */
	private int makeReviewVideoOps(VideoSubmit record){
		int count = 0;
		
		ReviewVideoOps info = new ReviewVideoOps();
		//审核任务id						
		info.setJobId(record.getJobId());
		//视频id		
		info.setVideoId(record.getData().getAttribute().getId());
		//创建人	
		info.setCreatedBy(null);
		//创建时间		
		info.setCreatedAt(new Date());
		//更新人	
		info.setUpdatedBy(null);
		//更新时间	
		info.setUpdatedAt(new Date());
		//删除标志		
		info.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);
		
		//涉黄
		if(record.getParams().getOps().getPulp() != null){
			//id	
			info.setId(CommonUtil.getUUID());
			//审核项目名称	
			info.setOp(Global.REVIEW_OP_PULP);
			//数组转String,逗号分割
			String levels = StringUtils.join(record.getParams().getOps().getPulp().getLevel(), ",");
			//视频审核操作等级	
			info.setLevels(levels);
			//视频审核结果保存地址			
			info.setOpSave(record.getParams().getOps().getPulp().getSave());
			//操作的回调地址		
			info.setOpHookHost(record.getParams().getOps().getPulp().getHook().getHost());
			count += reviewVideoOpsMapper.insert(info);
		}
		//鉴暴
		if(record.getParams().getOps().getTerror() != null){
			//id	
			info.setId(CommonUtil.getUUID());
			//审核项目名称	
			info.setOp(Global.REVIEW_OP_TERROR);
			//数组转String,逗号分割
			String levels = StringUtils.join(record.getParams().getOps().getTerror().getLevel(), ",");
			//视频审核操作等级	
			info.setLevels(levels);
			//视频审核结果保存地址			
			info.setOpSave(record.getParams().getOps().getTerror().getSave());
			//操作的回调地址		
			info.setOpHookHost(record.getParams().getOps().getTerror().getHook().getHost());
			count +=reviewVideoOpsMapper.insert(info);
		}
		//涉政
		if(record.getParams().getOps().getPolitician() != null){
			//id	
			info.setId(CommonUtil.getUUID());
			//审核项目名称	
			info.setOp(Global.REVIEW_OP_POLITICIAN);
			//数组转String,逗号分割
			String levels = StringUtils.join(record.getParams().getOps().getPolitician().getLevel(), ",");
			//视频审核操作等级	
			info.setLevels(levels);
			//视频审核结果保存地址			
			info.setOpSave(record.getParams().getOps().getPolitician().getSave());
			//操作的回调地址		
			info.setOpHookHost(record.getParams().getOps().getPolitician().getHook().getHost());
			count += reviewVideoOpsMapper.insert(info);
		}
		
		return count;
	}
	
	
}