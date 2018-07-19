package cn.qiniu.service.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerCuts;
import cn.qiniu.entity.ReviewVideoManagerCutsExample;
import cn.qiniu.entity.ReviewVideoManagerExample;
import cn.qiniu.entity.ReviewVideoManagerSegments;
import cn.qiniu.entity.ReviewVideoManagerSegmentsExample;
import cn.qiniu.entity.videoReplace.VideoReplace;
import cn.qiniu.entity.videoReplace.VideoReplaceLevelsegment;
import cn.qiniu.entity.videoReplace.VideoReplaceLevelsegmentcuts;
import cn.qiniu.mapper.ReviewVideoManagerCutsMapper;
import cn.qiniu.mapper.ReviewVideoManagerMapper;
import cn.qiniu.mapper.ReviewVideoManagerSegmentsMapper;
import cn.qiniu.util.CommonUtil;

import com.alibaba.fastjson.JSON;

/**
 * 更新视频信息service
 * 
 * @author yu99
 * 
 */
@Service
public class VideoManagerReplaceService {

	@Autowired
	private ReviewVideoManagerMapper reviewVideoManagerMapper;

	@Autowired
	ReviewVideoManagerSegmentsMapper reviewVideoManagerSegmentsMapper;

	@Autowired
	ReviewVideoManagerCutsMapper reviewVideoManagerCutsMapper;

	/**
	 * 更新视频信息
	 * 
	 * @return
	 */
	public String replaceVideoInfo(VideoReplace videoReplace) {
		String videoId = videoReplace.getId();
        
		ReviewVideoManager rvm = new ReviewVideoManager();
		// 查询数据
		ReviewVideoManagerExample managerExample = new ReviewVideoManagerExample();
		managerExample.createCriteria().andIdEqualTo(videoId);

		ReviewVideoManagerSegments rvms = new ReviewVideoManagerSegments();
		ReviewVideoManagerSegmentsExample segmentsExample = new ReviewVideoManagerSegmentsExample();
		segmentsExample.createCriteria().andVideoIdEqualTo(videoId);

		ReviewVideoManagerCuts rvmc = new ReviewVideoManagerCuts();
		ReviewVideoManagerCutsExample cutsExample = new ReviewVideoManagerCutsExample();
		cutsExample.createCriteria().andVideoIdEqualTo(videoId);

		// List<VideoReplace> list = new ArrayList<VideoReplace>();
		// for (VideoReplace rep : list) {
		// 视频审核片段
		List<VideoReplaceLevelsegment> reviveSgmentList = videoReplace
				.getReview().getSegments();
		for (VideoReplaceLevelsegment segment : reviveSgmentList) {
			reviewVideoManagerSegmentsMapper.deleteByExample(segmentsExample);
			String segmentsId = CommonUtil.getUUID();
			String op = segment.getOp();
			int offsetBegin = segment.getOffsetBegin();
			int offsetEnd = segment.getOffsetEnd();
			String uri = segment.getUri();
			// 视频审核截帧
			List<VideoReplaceLevelsegmentcuts> levelsegmentcuts = new ArrayList<VideoReplaceLevelsegmentcuts>();
			for (VideoReplaceLevelsegmentcuts cuts : levelsegmentcuts) {
				reviewVideoManagerCutsMapper.deleteByExample(cutsExample);
				int offset = cuts.getOffset();
				String cutUri = cuts.getUri();
				//设置videoId
				rvmc.setVideoId(videoId);
				//设置cuts主键Id
				rvmc.setId(CommonUtil.getUUID());
				// 设置segmentId
				rvmc.setSegmentId(segmentsId);							
				// 设置截帧表的截帧时间
				rvmc.setOffset(offset);
				// 设置截帧表的资源地址
				rvmc.setUri(cutUri);
				// 设创建时间
				rvmc.setCreatedAt(new Date());
				//设置更新时间
				rvmc.setUpdatedAt(new Date());
				// update
				reviewVideoManagerCutsMapper.insertSelective(rvmc);
			}

			String level = segment.getLevel();
			// String manualLevel =segment.getManualLevel();
			// String roboticLevel = segment.getRoboticLevel();
			// BigDecimal roboticScore =segment.getRoboticScore();
			// 设置片段表的审核项目
			rvms.setOp(op);
			// 设置片段起始时间
			rvms.setOffsetBegin(offsetBegin);
			// 设置片段结束时间
			rvms.setOffsetEnd(offsetEnd);
			// 设置片段资源地址
			rvms.setUri(uri);
			// 设置审核的结果等级
			rvms.setLevel(level);
			// 设置主键id
			rvms.setId(segmentsId);
			// 设置videoId
			rvms.setVideoId(videoId);
			// 设创建时间
			rvms.setCreatedAt(new Date());
			// 设置更新时间
			rvms.setUpdatedAt(new Date());
			// update
			reviewVideoManagerSegmentsMapper.insertSelective(rvms);
		}
		
		// 设置id
		rvm.setId(videoId);
		// 设置资源视频原始id
		rvm.setSource(videoReplace.getSource());
		// 设置视频资源的归档id
		rvm.setVideoUri(videoReplace.getVideo().getUri());
		// 设置视频封面地址
		rvm.setVideoCover(videoReplace.getVideo().getCover());
		// 设置审核阶段数据
		rvm.setReviewStage(videoReplace.getReviewStage());
		// 设置审核的结果等级
		rvm.setReviewLevel(videoReplace.getReview().getLevel());
		// 设置人工审核的结果等级
		rvm.setReviewManualLevel(videoReplace.getReview().getManualLevel());
		// 设置机器审核的结果等级
		rvm.setReviewRoboticLevel(videoReplace.getReview().getRoboticLevel());
		// 设置机器审核的可行性度
		rvm.setReviewRoboticScore(videoReplace.getReview().getRoboticScore());

		// 设置鉴黄人工审核结果等级
		if (videoReplace.getReview().getOps().getPulp() != null) {
			// 设置鉴黄人工审核结果等级
			rvm.setReviewOpsPulpManualLevel(videoReplace.getReview().getOps()
					.getPulp().getManualLevel());
			// 设置鉴黄机审结果等级
			rvm.setReviewOpsPulpRoboticLevel(videoReplace.getReview().getOps()
					.getPulp().getRoboticLevel());

			// 设置鉴黄的机审可行度
			rvm.setReviewOpsPulpRoboticScore(videoReplace.getReview().getOps()
					.getPulp().getRoboticScore());

		}

		if (videoReplace.getReview().getOps().getPolitician() != null) {
			// 设置政治人工审核结果等级
			rvm.setReviewOpsPoliticianManualLevel(videoReplace.getReview()
					.getOps().getPolitician().getManualLevel());
			// 设置政治机审结果等级
			rvm.setReviewOpsPoliticianRoboticLevel(videoReplace.getReview()
					.getOps().getPolitician().getRoboticLevel());
			// 设置政治的机审可行度
			rvm.setReviewOpsPoliticianRoboticScore(videoReplace.getReview()
					.getOps().getPolitician().getRoboticScore());

		}

		if (videoReplace.getReview().getOps().getTerror() != null) {
			// 设置暴力人工审核结果等级
			rvm.setReviewOpsTerrorManualLevel(videoReplace.getReview().getOps()
					.getTerror().getManualLevel());
			// 设置暴力机审结果等级
			rvm.setReviewOpsTerrorRoboticLevel(videoReplace.getReview()
					.getOps().getTerror().getRoboticLevel());
			// 设置暴力的机审可行度
			rvm.setReviewOpsTerrorRoboticScore(videoReplace.getReview()
					.getOps().getTerror().getRoboticScore());
		}

		// 设置更新时间
		rvm.setUpdatedAt(new Date());

		int count = reviewVideoManagerMapper.updateByExample(rvm,
				managerExample);

		//

		String json = JSON.toJSONString(videoReplace);
		return json;

	}
}
