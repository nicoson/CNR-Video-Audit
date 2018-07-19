package cn.qiniu.service.videoReview;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerCuts;
import cn.qiniu.entity.ReviewVideoManagerCutsExample;
import cn.qiniu.entity.ReviewVideoManagerExample;
import cn.qiniu.entity.ReviewVideoManagerSegments;
import cn.qiniu.entity.ReviewVideoManagerSegmentsExample;
import cn.qiniu.entity.ReviewVideoTask;
import cn.qiniu.form.entity.ManualForm;
import cn.qiniu.form.entity.Segements;
import cn.qiniu.form.review.Cuts;
import cn.qiniu.form.review.Op;
import cn.qiniu.form.review.Ops;
import cn.qiniu.form.review.Review;
import cn.qiniu.form.review.Segments;
import cn.qiniu.form.review.Video;
import cn.qiniu.form.review.VideoForm;
import cn.qiniu.mapper.ReviewVideoManagerCutsMapper;
import cn.qiniu.mapper.ReviewVideoManagerMapper;
import cn.qiniu.mapper.ReviewVideoManagerSegmentsMapper;
import cn.qiniu.mapper.ReviewVideoMapper;
import cn.qiniu.util.DictUtil;

import com.alibaba.fastjson.JSON;

/**
 * 视频审核服务类
 * @author    Ling
 * @version   2017-09-26
 */
@Service
public class ReviewVideoService {
	
	// 审核视频管理表Mapper
	@Autowired
	private  ReviewVideoMapper mapper;
	
	@Autowired
	ReviewVideoManagerMapper reviewVideoManagerMapper;
	
	@Autowired
	ReviewVideoManagerSegmentsMapper reviewVideoManagerSegmentsMapper;
	
	@Autowired
	ReviewVideoManagerCutsMapper reviewVideoManagerCutsMapper;
	
	/**
	 * 获取审核任务列表
	 * @return job_id列表
	 */
	public List<ReviewVideoTask> selectReviewVideoTaskList(){
		return mapper.selectReviewVideoTaskList();
	}

	/**
	 * 获取审核视频结果信息
	 */
	public String getVideoApplication(String jobId) {
		ReviewVideoManagerExample example = new ReviewVideoManagerExample();
		example.createCriteria().andJobIdEqualTo(jobId)
		.andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0);
		//获取视频管理表 信息
		ReviewVideoManager videoManager = reviewVideoManagerMapper.selectByExample(example).get(0);
		
		VideoForm video = new VideoForm();
		//设置视频id
		video.setId(videoManager.getId());
		//设置视频资源原始地址
		video.setSource(videoManager.getSource());
		Video vi = new Video();
		//设置视频资源归档地址
		vi.setUri(videoManager.getVideoUri());
		//视频资源封面地址
		vi.setCover(videoManager.getVideoCover());
		video.setVideo(vi);
		//视频审核阶段
//		video.setReviewStage(videoManager.getReviewStage());
		Review review = new Review();
		//审核结果等级
		review.setLevel(videoManager.getReviewLevel());
//		//人工审核结果等级
//		review.setManualLevel(videoManager.getReviewManualLevel());
//		//机器审核结果等级
//		review.setRoboticLevel(videoManager.getReviewRoboticLevel());
//		//机器审核的可行性度
//		review.setRoboticScore(videoManager.getReviewRoboticScore()
//				.floatValue());
		Ops ops = new Ops();
		Op pulp = new Op();
		//监黄  人工审核等级
		pulp.setManualLevel(videoManager.getReviewOpsPulpManualLevel());
		//监黄  机器审核等级
		pulp.setRoboticLevel(videoManager.getReviewOpsPulpRoboticLevel());
//		//监黄  机器审核可信性
//		pulp.setRoboticScore(videoManager.getReviewOpsPulpRoboticScore()
//				.floatValue());
		ops.setPulp(pulp);
		Op terror = new Op();
		//鉴暴  人工审核等级
		terror.setManualLevel(videoManager.getReviewOpsTerrorManualLevel());
		//鉴暴  机器审核等级
		terror.setRoboticLevel(videoManager.getReviewOpsTerrorRoboticLevel());
		//鉴暴  机器可信性
//		terror.setRoboticScore(videoManager.getReviewOpsTerrorRoboticScore()
//				.floatValue());
		ops.setTerror(terror);
		Op politician = new Op();
		//涉政 人工审核等级
		politician.setManualLevel(videoManager
				.getReviewOpsPoliticianManualLevel());
		//涉政 机器审核等级
		politician.setRoboticLevel(videoManager
				.getReviewOpsPoliticianRoboticLevel());
		//涉政 机器审核可信性
//		politician.setRoboticScore(videoManager
//				.getReviewOpsPoliticianRoboticScore().floatValue());
		ops.setPolitician(politician);
		review.setOps(ops);
		ReviewVideoManagerSegmentsExample segmentsExample = new ReviewVideoManagerSegmentsExample();
		segmentsExample.createCriteria().andVideoIdEqualTo(videoManager.getId())
		.andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0);
		
		//视频id查询视频管理片段表
		List<ReviewVideoManagerSegments> listSegments = reviewVideoManagerSegmentsMapper
				.selectByExample(segmentsExample);
		List<Segments> list = new ArrayList<Segments>();
		for (ReviewVideoManagerSegments segment : listSegments) {
			Segments segments = new Segments();
			//设置 片段审核项目
			segments.setOp(segment.getOp());
			//设置 片段开始时间
			segments.setOffsetBegin(segment.getOffsetBegin());
			//设置 片段结束时间
			segments.setOffsetEnd(segment.getOffsetEnd());
			//设置 片段归档地址
			segments.setUri(segment.getUri());
			
			ReviewVideoManagerCutsExample cutsExample = new ReviewVideoManagerCutsExample();
			cutsExample.createCriteria().andSegmentIdEqualTo(segment.getId())
			.andVideoIdEqualTo(segment.getVideoId())
			.andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0);
			
			List<ReviewVideoManagerCuts> listCuts = reviewVideoManagerCutsMapper
					.selectByExample(cutsExample);
			
			List<Cuts> lists = new ArrayList<Cuts>();
			for (ReviewVideoManagerCuts cut : listCuts) {
				Cuts cuts = new Cuts();
				//设置截帧 时间
				cuts.setOffset(cut.getOffset());
				//设置截帧视频地址
				cuts.setUri(cut.getUri());
				lists.add(cuts);
			}
			
			segments.setCuts(lists);
			segments.setLevel(segment.getLevel());
//			segments.setManualLevel(segment.getManualLevel());
			segments.setRoboticLevel(segment.getRoboticLevel());
//			if(segment.getRoboticScore() != null){
//				segments.setRoboticScore(segment.getRoboticScore().floatValue());
//			}
			list.add(segments);
		}
		review.setSegments(list);
		video.setReview(review);
		
		String json = JSON.toJSONString(video);
		return json;
	}
	
	/**
	 * 根据videoId  提交人工审核结果
	 * @param videoId
	 * @return
	 */
	public String submitManualApplication(ManualForm form,String videoId) {
		ReviewVideoManager mainRecord = new ReviewVideoManager();
		mainRecord.setId(videoId);
		//设置人工审核危险等级
		mainRecord.setReviewLevel(form.getLevel());
		if(form.getOps().getPolitician()!=null){
			//设置涉政 人工审核危险等级
			mainRecord.setReviewOpsPoliticianManualLevel(form.getOps()
					.getPolitician().getLevel());
		}
		if(form.getOps().getPulp()!=null){
			//设置监黄 人工审核危险等级
			mainRecord.setReviewOpsPulpManualLevel(form.getOps().getPulp()
					.getLevel());
		}
		if(form.getOps().getTerror()!=null){
			//设置鉴暴  人工审核危险等级
			mainRecord.setReviewOpsTerrorManualLevel(form.getOps().getTerror()
					.getLevel());
		}
		//插入数据
		int	count =	reviewVideoManagerMapper.insertSelective(mainRecord);
		ReviewVideoManagerSegments segments= new ReviewVideoManagerSegments();
		//获取审核片段
		List<Segements> segmentsList =form.getSegments();
		if(segmentsList != null || segmentsList.size()>0){
			for(Segements segment:segmentsList){
				//设置segementId
				segments.setId(segment.getSegementId());
				//设置危险等级项目
				segments.setOp(segment.getOp());
				//设置开始时间
				segments.setOffsetBegin(segment.getOffsetBegin());
				//设置自结束时间
				segments.setOffsetEnd(segment.getOffsetEnd());
				//设置危险等级
				segments.setLevel(segment.getLevel());
				//设置人审危险等级
				segments.setManualLevel(segment.getManualLevel());
				//插入数据
				int	count2 = reviewVideoManagerSegmentsMapper.insertSelective(segments);
			}
		}
		return UUID.randomUUID().toString();
	}
}
