package cn.qiniu.service.video;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.qiniu.config.Global;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerExample;
import cn.qiniu.form.video.AssignReviewSearch;
import cn.qiniu.mapper.CheckInfoListMapper;
import cn.qiniu.mapper.ReviewVideoManagerMapper;
import cn.qiniu.util.DictUtil;

@Service
public class AssignReviewService {

	@Autowired
	private ReviewVideoManagerMapper reviewVideoManagerMapper;
	@Autowired
	private CheckInfoListMapper checkInfoListMapper;
	
	
	@Transactional(readOnly=false)
	public Object assignReview(AssignReviewSearch search)
	{
//		VideoManager video = null;
		//获取待审核视频
		ReviewVideoManager reviewVideo = getAssignReview(search);
		
//		if (reviewVideo != null)
//		{
//			video.setId(reviewVideo.getId());
//			video.setSource(reviewVideo.getSource());
//			video.getVideo().setUri(reviewVideo.getVideoUri());
//			video.getVideo().setCover(reviewVideo.getVideoCover());
//			video.setReviewStage(reviewVideo.getReviewStage());
//			video.setReview(review);
//		}
		return reviewVideo;
	}
	
	private ReviewVideoManager getAssignReview(AssignReviewSearch search)
	{
		ReviewVideoManagerExample  example = new ReviewVideoManagerExample();
		example.createCriteria().andReviewerIdIsNull()
		.andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0);
		example.setOrderByClause("review_robotic_level desc");
		
//		List<ReviewVideoManager> list = reviewVideoManagerMapper.selectByExample(example);
		List<ReviewVideoManager> list = checkInfoListMapper.getCheckInfoList();
		
		ReviewVideoManager result = null;
		// 取得一个待审核视频
		for(int i=0;i<list.size();i++){
			ReviewVideoManager video=list.get(i);
			String reviewLevel = video.getReviewLevel();
			// 取得审核等级为高的视频
			if (Global.YES.equals(search.getHigher())
					&& DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_3.equals(reviewLevel))
			{
				result = video;
				break;
			}
			// 取得审核等级为中的视频
			if (Global.YES.equals(search.getMedium())
					&& DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_2.equals(reviewLevel))
			{
				result = video;
				break;
			}
			// 取得审核等级为低的视频
			if (Global.YES.equals(search.getLow())
					&& DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_1.equals(reviewLevel))
			{
				result = video;
				break;
			}
			
		}
		
		// 更新视频的审核人
		if (result != null)
		{
			example.clear();
			example.createCriteria().andIdEqualTo(result.getId())
				.andUpdatedAtEqualTo(result.getUpdatedAt());
			result.setReviewerId(search.getLoginName());
			try {
				int count = reviewVideoManagerMapper.updateByExampleSelective(result, example);
				// 排他处理
				if (count == 0)
					result = null;		
			} catch (Exception e) {
				// TODO: handle exception
				e.toString();
			}
		}		
		return result;
	}
	
}
