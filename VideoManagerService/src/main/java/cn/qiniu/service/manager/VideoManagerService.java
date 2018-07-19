package cn.qiniu.service.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerCuts;
import cn.qiniu.entity.ReviewVideoManagerCutsExample;
import cn.qiniu.entity.ReviewVideoManagerExample;
import cn.qiniu.entity.ReviewVideoManagerSegments;
import cn.qiniu.entity.ReviewVideoManagerSegmentsExample;
import cn.qiniu.form.video.Cuts;
import cn.qiniu.form.video.Op;
import cn.qiniu.form.video.Ops;
import cn.qiniu.form.video.Review;
import cn.qiniu.form.video.Segments;
import cn.qiniu.form.video.Video;
import cn.qiniu.form.video.VideoForm;
import cn.qiniu.form.video.VideoListSearch;
import cn.qiniu.mapper.ReviewVideoManagerCutsMapper;
import cn.qiniu.mapper.ReviewVideoManagerMapper;
import cn.qiniu.mapper.ReviewVideoManagerSegmentsMapper;
import cn.qiniu.mapper.VideoPageListMapper;
import cn.qiniu.util.DictUtil;
import cn.qiniu.util.StringUtil;

import com.alibaba.fastjson.JSON;

/**
 * 视频管理服务
 * 
 * @author Ling QiongFang
 * @version 2017-09-26
 */
@Service
public class VideoManagerService {
	
	@Autowired
	VideoPageListMapper videoPageListMapper;	
	
	@Autowired
	ReviewVideoManagerMapper reviewVideoManagerMapper;
	
	@Autowired
	ReviewVideoManagerSegmentsMapper reviewVideoManagerSegmentsMapper;
	
	@Autowired
	ReviewVideoManagerCutsMapper reviewVideoManagerCutsMapper;
	
	@Autowired
	private String liveCuts;
	
	/**
	 * 获取视频列表
	 */
	public String getVideoList(String pageNum,String pageSize,String[] reviewLevels,String reviewStage) {
		String videoTime = "";
		//直播时长
		int liveTime;
		//计算分页数据
		int pageSizeToInt = Integer.parseInt(pageSize);
		int pageStart = Integer.parseInt(pageNum);
		//计算开始数据
		pageStart = (pageStart-1)*pageSizeToInt;
		VideoListSearch search = new VideoListSearch();
		search.setPageStart(pageStart);
		//保存每页条数
		search.setPageEnd(pageSizeToInt);
		search.setReviewLevel(StringUtils.join(reviewLevels, ","));
		search.setReviewStage(reviewStage);
		//查询数据
		List<ReviewVideoManager> list = null;
			list = videoPageListMapper.selectVideoPageList(search);
		List<VideoForm> video = new ArrayList<VideoForm>();
		for (ReviewVideoManager manager : list) {
			//页面上接收的实体类
			VideoForm videoForm = new VideoForm();
			//设置id
			videoForm.setId(manager.getId());
			//直播停止标识
			videoForm.setLiveEnd(manager.getLiveEnd());
			//视频类型
			videoForm.setVideoType(manager.getVideoType());
			//设置元地址
			videoForm.setSource(manager.getSource());
			if(DictUtil.VIDEO_TYPE.VIDEO_TYPE_1.equals(manager.getVideoType())){
				
				if(!StringUtil.isNullOrEmpty(manager.getVideoTime())){
					videoTime = manager.getVideoTime().substring(0,manager.getVideoTime().indexOf("."));
					videoForm.setVideoTime(StringUtil.secToTime(Integer.parseInt(videoTime)));
				}else{
					videoForm.setVideoTime("00:00:00");
				}
			}else if(DictUtil.VIDEO_TYPE.VIDEO_TYPE_2.equals(manager.getVideoType())
					&& DictUtil.LIVE_END_FLG.LIVE_END_FLG_1.equals(manager.getLiveEnd())){
				liveTime = Integer.parseInt(liveCuts);
				liveTime = liveTime/1000;
				videoForm.setVideoTime(StringUtil.secToTime(liveTime));
			}
			if(StringUtil.isNullOrEmpty(videoForm.getVideoTime())){
				videoForm.setVideoTime("--:--:--");
			}
			
			Video vi = new Video();
			//设置视频地址
			vi.setUri(manager.getVideoUri());
			//设置视频封面地址
			vi.setCover(manager.getVideoCover());
			//设置视频名称
			vi.setVideoName(manager.getVideoName());
			//设置创建时间
			vi.setCreateAt(new SimpleDateFormat("yyyy-MM-dd").format(manager.getCreatedAt()));
			videoForm.setVideo(vi);
			//设置审核阶段
			videoForm.setReviewStage(manager.getReviewStage());
			Review review = new Review();
			//设置审核等级
			review.setLevel(manager.getReviewLevel());
			//设置人工审核结果
			review.setManualLevel(manager.getReviewManualLevel());
			//设置机器审核结果
			review.setRoboticLevel(manager.getReviewRoboticLevel());
			videoForm.setReview(review);
			video.add(videoForm);
		}
		String json = JSON.toJSONString(video);
		return json;

	}
	
	/**
	 * 获取视频列表最大页数
	 * @param pageNum
	 * @param pageSize
	 * @param reviewLevels
	 * @return
	 */
	public int getMaxPageSize(String pageNum,String pageSize,String[] reviewLevels){
		VideoListSearch search = new VideoListSearch();
		search.setReviewLevel(StringUtils.join(reviewLevels, ","));
		//查询最大记录条数
		String maxPageSize = videoPageListMapper.selectMaxPageSize(search);
		
		int totalPage = (Integer.parseInt(maxPageSize) + Integer.parseInt(pageSize) -1) / Integer.parseInt(pageSize) == 0 ? 1 :(Integer.parseInt(maxPageSize) + Integer.parseInt(pageSize) -1) / Integer.parseInt(pageSize) ;
		return totalPage;
	}
	

	/**
	 * 获取视频信息
	 */
	public String getVideoInfo(String videoId) {
		ReviewVideoManagerExample example = new ReviewVideoManagerExample();
		example.createCriteria().andIdEqualTo(videoId);
		//根据videoId查询视频管理信息
		ReviewVideoManager videoManager = reviewVideoManagerMapper.selectByExample(example)
				.get(0);
		VideoForm video = new VideoForm();
		//设置视频id
		video.setId(videoId);
		//视频资源的原始地址
		video.setSource(videoManager.getSource());
		Video vi = new Video();
		//设置视频资源的归档地址
		vi.setUri(videoManager.getVideoUri());
		//视频资源的封面地址
		vi.setCover(videoManager.getVideoCover());
		video.setVideo(vi);
		//审核阶段
		video.setReviewStage(videoManager.getReviewStage());
		Review review = new Review();
		//审核的结果等级
		review.setLevel(videoManager.getReviewLevel());
		//人工审核等级
		review.setManualLevel(videoManager.getReviewManualLevel());
		//机器审核等级
		review.setRoboticLevel(videoManager.getReviewRoboticLevel());
		//机器审核的可行性度
		review.setRoboticScore(videoManager.getReviewRoboticScore()
				.floatValue());
		Ops ops = new Ops();
		Op pulp = new Op();
		//鉴黄人工审核危险等级
		pulp.setManualLevel(videoManager.getReviewOpsPulpManualLevel());
		//鉴黄机器审核危险等级
		pulp.setRoboticLevel(videoManager.getReviewOpsPulpRoboticLevel());
		//鉴黄的机器审核的可行性度
		pulp.setRoboticScore(videoManager.getReviewOpsPulpRoboticScore()
				.floatValue());
		ops.setPulp(pulp);
		
		Op terror = new Op();
		//鉴暴 人工审核危险等级
		terror.setManualLevel(videoManager.getReviewOpsTerrorManualLevel());
		//鉴暴 机器审核危险等级
		terror.setRoboticLevel(videoManager.getReviewOpsTerrorRoboticLevel());
		//鉴暴 机器审核的可行性度
		terror.setRoboticScore(videoManager.getReviewOpsTerrorRoboticScore()
				.floatValue());
		ops.setTerror(terror);
		
		Op politician = new Op();
		//涉政 人工审核危险等级
		politician.setManualLevel(videoManager
				.getReviewOpsPoliticianManualLevel());
		//涉政 机器审核危险等级
		politician.setRoboticLevel(videoManager
				.getReviewOpsPoliticianRoboticLevel());
		//涉政 机器审核的可行性度
		politician.setRoboticScore(videoManager
				.getReviewOpsPoliticianRoboticScore().floatValue());
		ops.setPolitician(politician);
		
		review.setOps(ops);
		
		ReviewVideoManagerSegmentsExample segmentsExample = new ReviewVideoManagerSegmentsExample();
		segmentsExample.createCriteria().andVideoIdEqualTo(videoId);
		//根据videoId 查询审核视频管理片段信息
		List<ReviewVideoManagerSegments> listSegments = reviewVideoManagerSegmentsMapper
				.selectByExample(segmentsExample);
		
		List<Segments> list = new ArrayList<Segments>();
		for (ReviewVideoManagerSegments segment : listSegments) {
			Segments segments = new Segments();
			//设置审核项目
			segments.setOp(segment.getOp());
			//片段起始时间
			segments.setOffsetBegin(segment.getOffsetBegin());
			//片段结束时间
			segments.setOffsetEnd(segment.getOffsetEnd());
			//片段资源地址
			segments.setUri(segment.getUri());
			
			ReviewVideoManagerCutsExample cutsExample = new ReviewVideoManagerCutsExample();
			cutsExample.createCriteria().andSegmentIdEqualTo(segment.getId());
			//根据videoId获取 审核视频管理截帧表里的信息
			List<ReviewVideoManagerCuts> listCuts = reviewVideoManagerCutsMapper
					.selectByExample(cutsExample);
			
			List<Cuts> lists = new ArrayList<Cuts>();
			for (ReviewVideoManagerCuts cut : listCuts) {
				Cuts cuts = new Cuts();
				//设置截帧时间
				cuts.setOffset(cut.getOffset());
				//截帧资源地址
				cuts.setUri(cut.getUri());
				lists.add(cuts);
			}
			segments.setCuts(lists);
			//设置截帧危险等级
			segments.setLevel(segment.getLevel());
			//设置截帧人审危险等级
			segments.setManualLevel(segment.getManualLevel());
			//设置截帧机器审核危险等级
			segments.setRoboticLevel(segment.getRoboticLevel());
			if(segment.getRoboticScore() != null){
				//设置截帧机器审核可行性
				segments.setRoboticScore(segment.getRoboticScore().floatValue());
			}
			
			list.add(segments);
		}
		review.setSegments(list);
		video.setReview(review);
		String json = JSON.toJSONString(video);
		return json;
	}
	
	
	
}