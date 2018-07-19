package cn.qiniu.service.video;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.delegator.entity.VideoForm;
import cn.qiniu.delegator.video.VideoListDelegator;
import cn.qiniu.delegator.video.VideoManagerDelegator;
import cn.qiniu.delegator.video.VideoReviewDelegator;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.SysUser;
import cn.qiniu.entity.VideoCountByLevel;
import cn.qiniu.entity.VideoListSearch;
import cn.qiniu.entity.videoReview.VideoSubmit;
import cn.qiniu.entity.videoReview.VideoSubmitData;
import cn.qiniu.entity.videoReview.VideoSubmitDataAttribute;
import cn.qiniu.entity.videoReview.VideoSubmitOp;
import cn.qiniu.entity.videoReview.VideoSubmitOps;
import cn.qiniu.entity.videoReview.VideoSubmitParams;
import cn.qiniu.form.video.VideoListForm;
import cn.qiniu.form.video.VideoSubmitForm;
import cn.qiniu.util.common.CommonUtil;
import cn.qiniu.util.common.Constant;

@Service
public class VideoListService {
	@Autowired
    private VideoManagerDelegator managerDelegator;
	@Autowired
    private VideoReviewDelegator reviewDelegator;
	@Autowired
    private VideoListDelegator listDelegator;
	
	/**
	 * 获取视频列表最大页数
	 * @param search
	 * @return
	 */
	public String getPageMaxSize(VideoListSearch search){
		String pageNum = search.getPageNum();
		String pageSize = search.getPageSize();
		String reviewLevel = search.getReviewLevel();
		String[] reviewLevels = reviewLevel.split(",");
		return managerDelegator.getPageMaxSize(pageNum, pageSize, reviewLevels);
	}
	
	/**
	  * 分页
	  * @param search 视频列表-查询条件
	  * @param pager 分页参数
	  * @return 分页数据
	  */
	 public List<VideoForm> tbPageList(VideoListSearch search){
		 String pageNum = search.getPageNum();
		 String pageSize = search.getPageSize();
		 String reviewLevel = search.getReviewLevel();
		 String[] reviewLevels = reviewLevel.split(",");
		 
		 List<VideoForm> list = managerDelegator.tbPageList(pageNum, pageSize, reviewLevels);
		 
		 //未开始label
		 String reviewState0 = Constant.REVIEW_STATE_LABEL.REVIEW_STATE_0;
		 //处理中label
		 String reviewState1 = Constant.REVIEW_STATE_LABEL.REVIEW_STATE_1;
		 //通过label
		 String reviewState2 = Constant.REVIEW_STATE_LABEL.REVIEW_STATE_2;
		 //不通过label
		 String reviewState3 = Constant.REVIEW_STATE_LABEL.REVIEW_STATE_3;
		 //处理失败label
		 String reviewState4 = Constant.REVIEW_STATE_LABEL.REVIEW_STATE_4;
		 
		 //判断是否有数据
		 if(list != null && list.size()>0){
			 //根据数据转换字典项lable值
			 for(VideoForm videoForm : list){
				 //根据机审状态-0,机审
				 if(Constant.REVIEW_STAGE.REVIEW_STAGE_0.equals(videoForm.getReviewStage())){
//					//机审状态 0-已机审   机审等级  0-无     则机审状态-已通过
//					 if(Constant.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(videoForm.getReview().getRoboticLevel())){
//						 //设置状态-机审已通过
//						 videoForm.getReview().setRoboticState(reviewState2);
//					 }else if(Constant.REVIEW_LEVEL.REVIEW_LEVEL_1.equals(videoForm.getReview().getRoboticLevel())
//							 || Constant.REVIEW_LEVEL.REVIEW_LEVEL_2.equals(videoForm.getReview().getRoboticLevel())
//							 || Constant.REVIEW_LEVEL.REVIEW_LEVEL_3.equals(videoForm.getReview().getRoboticLevel())){
//						 //机审状态 0-已机审   机审等级 非 0-无     则机审状态-未通过
//						 videoForm.getReview().setRoboticState(reviewState3);
//					 }else{
						 //未开始
						 videoForm.getReview().setRoboticState(reviewState1);
						 videoForm.getReview().setManualState(reviewState0);
						 videoForm.getReview().setRoboticLevel(Constant.REVIEW_LEVEL.REVIEW_LEVEL_99);
//						 }
					
				 }
				 //已机审、待人审
				 else if(Constant.REVIEW_STAGE.REVIEW_STAGE_1.equals(videoForm.getReviewStage())){
					 //机审状态 0-已机审   机审等级  0-无     则机审状态-已通过
					 if(Constant.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(videoForm.getReview().getRoboticLevel())){
						 //设置状态-机审已通过
						 videoForm.getReview().setRoboticState(reviewState2);
					 }else if(Constant.REVIEW_LEVEL.REVIEW_LEVEL_1.equals(videoForm.getReview().getRoboticLevel())
							 || Constant.REVIEW_LEVEL.REVIEW_LEVEL_2.equals(videoForm.getReview().getRoboticLevel())
							 || Constant.REVIEW_LEVEL.REVIEW_LEVEL_3.equals(videoForm.getReview().getRoboticLevel())){
						 //机审状态 0-已机审   机审等级 非 0-无     则机审状态-未通过
						 videoForm.getReview().setRoboticState(reviewState3);
					 }else{
						 //未开始
						 videoForm.getReview().setRoboticState(reviewState0);
					 }
					 videoForm.getReview().setManualState(reviewState0);
				 }
				 
				 //已人审
				 else if(Constant.REVIEW_STAGE.REVIEW_STAGE_2.equals(videoForm.getReviewStage())){
					 //机审等级  0-无
					 if(Constant.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(videoForm.getReview().getRoboticLevel())){
						 //已通过
						 videoForm.getReview().setRoboticState(reviewState2);
					 }else if(Constant.REVIEW_LEVEL.REVIEW_LEVEL_1.equals(videoForm.getReview().getRoboticLevel())
							 || Constant.REVIEW_LEVEL.REVIEW_LEVEL_2.equals(videoForm.getReview().getRoboticLevel())
							 || Constant.REVIEW_LEVEL.REVIEW_LEVEL_3.equals(videoForm.getReview().getRoboticLevel())){
						 //未通过
						 videoForm.getReview().setRoboticState(reviewState3);
					 }
					 
					 //人审
					 if(Constant.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(videoForm.getReview().getManualLevel())){
						 videoForm.getReview().setManualState(reviewState2);
					 }else if(Constant.REVIEW_LEVEL.REVIEW_LEVEL_1.equals(videoForm.getReview().getManualLevel())
							 || Constant.REVIEW_LEVEL.REVIEW_LEVEL_2.equals(videoForm.getReview().getManualLevel())
							 || Constant.REVIEW_LEVEL.REVIEW_LEVEL_3.equals(videoForm.getReview().getManualLevel())){
						 //未通过
						 videoForm.getReview().setManualState(reviewState3);
					 }
				 }else if(Constant.REVIEW_STATE.REVIEW_STATE_4.equals(videoForm.getReviewStage())){
					 videoForm.getReview().setRoboticState(reviewState4);
					 videoForm.getReview().setManualState(reviewState0);
				 }
				 
				 
				 //机审危险等级转为label值
				 if(Constant.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(videoForm.getReview().getRoboticLevel())){
					 videoForm.getReview().setRoboticLevel(Constant.REVIEW_LEVEL_LABEL.REVIEW_LEVEL_LABEL_0); 
				 }else if(Constant.REVIEW_LEVEL.REVIEW_LEVEL_1.equals(videoForm.getReview().getRoboticLevel())){
					 videoForm.getReview().setRoboticLevel(Constant.REVIEW_LEVEL_LABEL.REVIEW_LEVEL_LABEL_1);  
				 }else if(Constant.REVIEW_LEVEL.REVIEW_LEVEL_2.equals(videoForm.getReview().getRoboticLevel())){
					 videoForm.getReview().setRoboticLevel(Constant.REVIEW_LEVEL_LABEL.REVIEW_LEVEL_LABEL_2);  
				 }else if(Constant.REVIEW_LEVEL.REVIEW_LEVEL_3.equals(videoForm.getReview().getRoboticLevel())){
					 videoForm.getReview().setRoboticLevel(Constant.REVIEW_LEVEL_LABEL.REVIEW_LEVEL_LABEL_3);  
				 }else{
					 videoForm.getReview().setRoboticLevel(Constant.REVIEW_LEVEL_LABEL.REVIEW_LEVEL_LABEL_99);  
				 }
				 
				 //直播类型
				 if(Constant.VIDEO_TYPE.VIDEO_TYPE_2.equals(videoForm.getVideoType())){
					 videoForm.setVideoType(Constant.VIDEO_TYPE_LABEL.VIDEO_TYPE_LABEL_2);
				 }else{
					 videoForm.setVideoType(Constant.VIDEO_TYPE_LABEL.VIDEO_TYPE_LABEL_1);
				 }
			 }
		 }
		 return list;
	 }
	 
	/**
	 * 查询危险等级对应的审核视频数量
	 */
	public VideoCountByLevel getCountByDangerLevel(VideoListSearch search) {
		VideoListForm form = new VideoListForm();
		form.setSearch(search);
		
		return listDelegator.getCountByDangerLevel(form);
	}

	/**
	 * 上传视频
	 * @param form
	 * @return
	 */
	public String submitForm(VideoSubmitForm form) {
		VideoSubmit record = new VideoSubmit();
		
		VideoSubmitData data = new VideoSubmitData();
		data.setUri(form.getUri());
		
		VideoSubmitDataAttribute attribute = new VideoSubmitDataAttribute();
		//设置视频id-uuid
		attribute.setId(CommonUtil.getUUID());
		attribute.setDesc(form.getVideoName());
		
		VideoSubmitOps ops = new VideoSubmitOps();
		
		VideoSubmitOp pulp = new VideoSubmitOp();
		VideoSubmitOp terror = new VideoSubmitOp();
		VideoSubmitOp politician = new VideoSubmitOp();
		ops.setPulp(pulp);
		ops.setPolitician(politician);
		ops.setTerror(terror);
		
		VideoSubmitParams params = new VideoSubmitParams();
		params.setVideoType(Constant.VIDEO_TYPE.VIDEO_TYPE_2);
		params.setWait(false);
		params.setReviewType(Integer.parseInt(Constant.REVIEW_TYPE.REVIEW_TYPE_2));
		params.setOps(ops);
		
		data.setAttribute(attribute);
		record.setData(data);
		record.setParams(params);
		
		String jobId = reviewDelegator.submitVideo(record);
		return jobId;
	}
	 /**
	  * 删除功能
	  * @param form
	  * @return
	  * @author lqf 2017-09-29
	  */
	public int videoDelete(String videoId) {
		ReviewVideoManager info = new ReviewVideoManager();
		info.setId(videoId);
		int  count = listDelegator.videoDelete(info);
		return count;
	}
		
	/**
	 * 获取用户权限
	 * @param userId
	 * @return
	 */
	public String getRoleType(SysUser user){
		return listDelegator.getRoleType(user);
	}
	
}
