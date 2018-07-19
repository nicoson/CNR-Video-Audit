package cn.qiniu.service.video;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.delegator.video.CheckInfoDelegator;
import cn.qiniu.entity.CheckInfoSearch;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerSegments;
import cn.qiniu.entity.SysUser;
import cn.qiniu.entity.VideoInformation;
import cn.qiniu.form.video.CheckInfoForm;
import cn.qiniu.form.video.ReviewListSearch;
import cn.qiniu.util.common.Constant;
import cn.qiniu.util.common.DateUtil;

@Service
public class CheckInfoService {
	@Autowired
    private CheckInfoDelegator delegator;
	
	 /**
	  * 分页
	  * @param search 视频列表-查询条件
	  * @return 分页数据
	  */
	 public List<ReviewVideoManager> getCheckVideoList(ReviewListSearch search){
		 return this.delegator.getCheckVideoList(search);
	 }
	 
	 /**
	  * 根据视频id查询相信信息
	  * @param search 视频列表-查询条件
	  * @param pager 分页参数
	  * @return 分页数据
	  */
	 public VideoInformation getVideoInformation(CheckInfoSearch search){
		 VideoInformation info = new VideoInformation();
		 CheckInfoForm form = new CheckInfoForm();
		 form.setSearch(search);
		 //获取视频信息
		 ReviewVideoManager videoInfo = delegator.getVideoInfo(form);
		 //获取审核视频管理片段表数据
		 List<ReviewVideoManagerSegments> videoSegements = delegator.getVideoSegments(form);
		 List<ReviewVideoManagerSegments> newVideoSegments = new ArrayList<>();
		 //获取视频时长管理表数据
//		 ReviewVideoManagerTime videoTimeInfo =  delegator.getVideoTimeInfo(form);
		 if(videoSegements != null && videoSegements.size()>0){
			 for(ReviewVideoManagerSegments reviewVideoManagerSegments : videoSegements){
				 if(Constant.VIDEO_TYPE.VIDEO_TYPE_1.equals(videoInfo.getVideoType())){
					 //点播视频,转换所有毫秒数为   hh:mm:ss 格式   并保存
					 reviewVideoManagerSegments.setOffsetBeginStr(DateUtil.getTime((long)reviewVideoManagerSegments.getOffsetBegin()));
					 reviewVideoManagerSegments.setOffsetEndStr(DateUtil.getTime((long)reviewVideoManagerSegments.getOffsetEnd()));
				 }
				 if(Constant.VIDEO_TYPE.VIDEO_TYPE_2.equals(videoInfo.getVideoType())){
					 //直播视频,转换所有毫秒数为date,取时分秒
					 reviewVideoManagerSegments.setOffsetBeginStr(DateUtil.longTimeToDate((long)reviewVideoManagerSegments.getOffsetBegin(),"HH:mm:ss"));
					 reviewVideoManagerSegments.setOffsetEndStr(DateUtil.longTimeToDate((long)reviewVideoManagerSegments.getOffsetEnd(),"HH:mm:ss"));
				 }
				 newVideoSegments.add(reviewVideoManagerSegments);
			 }
		 }
		 //保存视频数据
		 info.setVideoInfo(videoInfo);
		 info.setVideoSegements(newVideoSegments);
		 return info;
	 }
	 
	 /**
	  * 视频审核页面-【审核完成】
	  */
	 public int videoManualFinish(List<ReviewVideoManagerSegments> list,SysUser user){
		 Date date = new Date();
		 if(list != null && list.size()>0){
			 for(ReviewVideoManagerSegments info : list){
				 //设置更新时间
				 info.setUpdatedAt(date);
				 //设置更新人
				 info.setUpdatedBy(user.getLoginNm());
			 }
		 }
		 return delegator.videoManualFinish(list);
	 }
	 
	 /**
	  * 审核视频一览-【审核完成】
	  */
	 public int videoManualFinishByPage(String videoId,SysUser user){
		 CheckInfoSearch search = new CheckInfoSearch();
		 search.setLoginName(user.getLoginNm());
		 search.setVideoId(videoId);
		 
		 return delegator.videoManualFinishByPage(search);
	 }
	 
	 /**
	  * 视频审核页面-【确认】
	  */
	 public int checkVideoInReview(ReviewVideoManagerSegments record,SysUser user){
		 //设置更新时间
		 record.setUpdatedAt(new Date());
		 //设置更新人
		 record.setUpdatedBy(user.getLoginNm());
		 
		 return delegator.checkVideoInReview(record);
	 }
}
