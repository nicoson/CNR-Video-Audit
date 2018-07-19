package cn.qiniu.service.video;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.qiniu.config.Global;
import cn.qiniu.entity.ReviewLevelSetting;
import cn.qiniu.entity.ReviewLevelSettingExample;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerCuts;
import cn.qiniu.entity.ReviewVideoManagerCutsExample;
import cn.qiniu.entity.ReviewVideoManagerExample;
import cn.qiniu.entity.ReviewVideoManagerSegments;
import cn.qiniu.entity.ReviewVideoManagerSegmentsExample;
import cn.qiniu.entity.VideoManualFinish;
import cn.qiniu.form.video.CheckInfoForm;
import cn.qiniu.form.video.CheckInfoSearch;
import cn.qiniu.form.video.ReviewListSearch;
import cn.qiniu.mapper.ReviewLevelSettingMapper;
import cn.qiniu.mapper.ReviewVideoManagerCutsMapper;
import cn.qiniu.mapper.ReviewVideoManagerMapper;
import cn.qiniu.mapper.ReviewVideoManagerSegmentsMapper;
import cn.qiniu.util.CommonDelegator;
import cn.qiniu.util.DictUtil;
import cn.qiniu.util.StringUtil;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.UrlSafeBase64;

@Service
public class CheckInfoService {
	// AccessKey
	@Autowired
	private String accessKey;
	
	// SecretKey
	@Autowired
	private String secretKey;
	
	// Bucket
	@Autowired
	private String bucket;
	
	// 央广视讯logo
	@Autowired
	private String cnrLogo;
	// 水印队列
	@Autowired
	private String watermarkPipeline;
	
	// 水印回调地址
	@Autowired
	private String qiniuCallbackWatermark;
	
	@Autowired
	private ReviewVideoManagerMapper reviewVideoManagerMapper;
	@Autowired
	private ReviewVideoManagerSegmentsMapper reviewVideoManagerSegmentsMapper;
	@Autowired
	private ReviewVideoManagerCutsMapper reviewVideoManagerCutsMapper;
//	@Autowired
//	private ReviewVideoManagerTimeMapper reviewVideoManagerTimeMapper;
	@Autowired
	private CommonDelegator commonDelegator;
	@Autowired
	private ReviewLevelSettingMapper reviewLevelSettingMapper;
	
	/**
     *  检索条件
     *
     * @return
     */
	private ReviewVideoManagerSegmentsExample getRecodeExample(String videoId){
		//定义检索条件
		ReviewVideoManagerSegmentsExample example = new ReviewVideoManagerSegmentsExample();
		ReviewVideoManagerSegmentsExample.Criteria criteria = example.createCriteria();
		//设置条件-未删除
		criteria.andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		//设置条件-videoId
		.andVideoIdEqualTo(videoId)
		.andLevelIsNotNull();
		//设置排序-更新时间
		example.setOrderByClause("updated_at");
		return example;
	}
	
	/**
     *  组装审核一览页面数据查询条件
     *
     * @return
     */
	private ReviewVideoManagerExample getListExample(ReviewListSearch search){
		//定义检索条件
		ReviewVideoManagerExample example = new ReviewVideoManagerExample();
		ReviewVideoManagerExample.Criteria criteria = example.createCriteria();
		//设置条件-审核人id为当前登录者
		criteria.andReviewerIdEqualTo(search.getLoginName())
		//设置条件-审核阶段
		.andReviewStageEqualTo(DictUtil.REVIEW_STAGE.REVIEW_STAGE_1)
		//设置条件-危险等级
		.andReviewLevelEqualTo(search.getDangerLevel())
		//设置条件-未删除
		.andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0);
		//设置排序-更新时间
		example.setOrderByClause("updated_at");
		return example;
	}
	
	/**
	 * 审核视频列表
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<ReviewVideoManager> getCheckVideoList(ReviewListSearch search) {
		List<ReviewVideoManager> list = reviewVideoManagerMapper.selectByExample(getListExample(search));
		return list;
	}
	
	/**
	 * 根据videoId获取审核视频管理表
	 * @param search
	 * @return
	 */
	@Transactional(readOnly=true)
	public ReviewVideoManager getVideoInfo(CheckInfoForm form){
		//根据主键获取数据
		ReviewVideoManager info = reviewVideoManagerMapper.selectByPrimaryKey(form.getSearch().getVideoId());
		return  info;
	}
	
	/**
	 * 根据videoId获取审核视频管理片段表数据
	 * @param search
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<ReviewVideoManagerSegments> getVideoSegments(CheckInfoForm form){
		ReviewVideoManagerSegmentsExample example = new ReviewVideoManagerSegmentsExample();
		example.createCriteria().andVideoIdEqualTo(form.getSearch().getVideoId())
		.andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0).andLevelIsNotNull();
		example.setOrderByClause("offset_begin");
		List<ReviewVideoManagerSegments> list = reviewVideoManagerSegmentsMapper.selectByExample(example);
		return  list;
	}
	
	/**
	 * 根据videoId 查询截帧信息
	 * @param search
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<ReviewVideoManagerCuts> getVideoCuts(CheckInfoForm form){
		ReviewVideoManagerCutsExample example = new ReviewVideoManagerCutsExample();
		example.createCriteria().andVideoIdEqualTo(form.getSearch().getVideoId())
		.andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0);
		List<ReviewVideoManagerCuts> list = reviewVideoManagerCutsMapper.selectByExample(example);
		return  list;
	}
	
//	/**
//	 * 根据videoId 查询截帧信息
//	 * @param search
//	 * @return
//	 */
//	@Transactional(readOnly=true)
//	public ReviewVideoManagerTime getVideoTimeInfo(CheckInfoForm form){
//		ReviewVideoManagerTime info = reviewVideoManagerTimeMapper.selectByPrimaryKey(form.getSearch().getVideoId());
//		return  info;
//	}
	
	/**
	 * 【审核完成】按钮
	 * @param search
	 * @return
	 */
	@Transactional(readOnly=false)
	public int videoManualFinish(VideoManualFinish record){
		ReviewLevelSettingExample settingExample = new ReviewLevelSettingExample();
		List<ReviewLevelSetting> settingList = null;
		//最终危险等级
		String reviewLevel = DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99;
		String level = "";
		String pulpLabel = "";
		String terrorLabel = "";
		String politicianLabel = "";
		String pulpLevel =  DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99;
		String terrorLevel =  DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99;
		String politicianLevel =  DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99;
		int count = 0;
//		List<ReviewVideoManagerSegments> segmentsList = record.getList();
		ReviewVideoManagerSegmentsExample example = new ReviewVideoManagerSegmentsExample();
		example.createCriteria().andVideoIdEqualTo(record.getList().get(0).getVideoId())
		.andLevelIsNotNull();
		List<ReviewVideoManagerSegments> segmentsList = reviewVideoManagerSegmentsMapper.selectByExample(example);
		//获取视频id
		String videoId = segmentsList.get(0).getVideoId();
		ReviewVideoManager reviewVideo = reviewVideoManagerMapper.selectByPrimaryKey(videoId);
		//获取更新人id
		String loginName = segmentsList.get(0).getUpdatedBy();
		//获取更新时间
		Date date = segmentsList.get(0).getUpdatedAt();
		
		//更新数据
		if(segmentsList != null && segmentsList.size()>0){
			for(ReviewVideoManagerSegments segments:segmentsList){

				//判断是否已人审
				if(segments.getManualLevel() == null || segments.getManualLevel() == ""){
					//设置人审等级-机审等级
					segments.setManualLevel(segments.getRoboticLevel());
					//设置最终等级-机审等级
					segments.setLevel(segments.getRoboticLevel());
				}
				//设置更新时间
				segments.setUpdatedAt(date);
				//设置更新人
				segments.setUpdatedBy(loginName);
				
				settingExample.clear();
				settingExample.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
				.andOpsOpEqualTo(segments.getOp()).andOpsOpLabelEqualTo(segments.getLevel());
				
				settingList = reviewLevelSettingMapper.selectByExample(settingExample);
				if(settingList != null && settingList.size()>0){
					level = settingList.get(0).getOpsOpLevel();
					//获取涉黄、涉暴、涉政的三个最高等级label
					switch (segments.getOp()) {
						//涉黄
						case Global.REVIEW_OP_PULP:
							if(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99.equals(pulpLevel)){
								pulpLevel = level;
								pulpLabel = segments.getLevel();
							}
							//判断当前等级是否高于其他数据等级
							else if(Integer.parseInt(level)>Integer.parseInt(pulpLevel)){
								pulpLevel = level;
								pulpLabel = segments.getLevel();
							}
							break;
						//涉暴恐
						case Global.REVIEW_OP_TERROR:
							if(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99.equals(terrorLevel)){
								terrorLevel = level;
								terrorLabel = segments.getLevel();
							}
							//判断当前等级是否高于其他数据等级
							else if(Integer.parseInt(level)>Integer.parseInt(terrorLevel)){
								terrorLevel = level;
								terrorLabel = segments.getLevel();
							}
							break;
						//涉政
						case Global.REVIEW_OP_POLITICIAN:
							if(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99.equals(politicianLevel)){
								politicianLevel = level;
								politicianLabel = segments.getLevel();
							}
							//判断当前等级是否高于其他数据等级
							if(Integer.parseInt(level)>Integer.parseInt(politicianLevel)){
								politicianLevel = level;
								politicianLabel = segments.getLevel();
							}
							break;
							
						default:
							break;
					}
				}
				//更新片段表
				count += reviewVideoManagerSegmentsMapper.updateByPrimaryKeySelective(segments);
			}
		}
		
		List<Integer> list = new ArrayList<>();
		if(!DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99.equals(pulpLevel)){
			list.add(Integer.parseInt(pulpLevel));
		}
		if(!DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99.equals(terrorLevel)){
			list.add(Integer.parseInt(terrorLevel));	
		}
		if(!DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99.equals(politicianLevel)){
			list.add(Integer.parseInt(politicianLevel));
		}
		
		//获取涉黄、涉政、涉暴中的最高危险等级
		reviewLevel = String.valueOf(Collections.max(list));
		
		//设置人审等级-机审等级
		reviewVideo.setReviewManualLevel(reviewLevel);
		
		//设置最终审核等级
		reviewVideo.setReviewLevel(reviewLevel);
		//设置审核阶段-已人审
		reviewVideo.setReviewStage(DictUtil.REVIEW_STAGE.REVIEW_STAGE_2);
		//设置更新人
		reviewVideo.setUpdatedBy(loginName);
		//设置更新时间
		reviewVideo.setUpdatedAt(date);
		//设置 ：鉴黄的人工审核的结果等级 review_ops_pulp_manual_level
		if(!StringUtil.isNullOrEmpty(pulpLabel)){
			reviewVideo.setReviewOpsPulpManualLevel(pulpLabel);
		}
		
		//设置：鉴暴的人工审核的结果等级 review_ops_terror_manual_level
		if(!StringUtil.isNullOrEmpty(terrorLabel)){
			reviewVideo.setReviewOpsTerrorManualLevel(terrorLabel);
		}
		
		//设置：政治人物的人工审核的结果等级 review_ops_politician_manual_level
		if(!StringUtil.isNullOrEmpty(politicianLabel)){
			reviewVideo.setReviewOpsPoliticianManualLevel(politicianLabel);
		}
		//判断人审结果是否为通过，如果是，视频打水印
		if(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(reviewVideo.getReviewLevel())){
			//截取归档地址获取文件名
			String key = reviewVideo.getVideoUri().substring(reviewVideo.getVideoUri().lastIndexOf("/")+1);
			//设置图片名称
			int mp4Size = key.indexOf(".");
			String newKey = key.substring(0,mp4Size)+"_R.mp4";
			setWatermark(key, newKey);
			
			int bucketSize = reviewVideo.getVideoUri().lastIndexOf("/")+1;
			//获取打水印的视频归档地址
			String videoUri = reviewVideo.getVideoUri().substring(0, bucketSize)+newKey;
			//将原视频替换为打水印视频
			reviewVideo.setVideoUri(videoUri);
		}
		
		//更新【审核视频管理表】数据
		count = count+reviewVideoManagerMapper.updateByPrimaryKeySelective(reviewVideo);
		
		return  count;
	}
	
	/**
	 * 视频审核页面【确定】
	 * @param search
	 * @return
	 */
	@Transactional(readOnly=false)
	public int checkVideoInReview(VideoManualFinish record){
		ReviewVideoManagerSegments segments = record.getRecord();
		if (StringUtil.isNullOrEmpty(segments.getOp())){
			ReviewVideoManagerSegments originSegment = reviewVideoManagerSegmentsMapper.selectByPrimaryKey(record.getRecord().getId());
			String op = originSegment.getOp();
			segments.setOp(op);
			if (Global.REVIEW_OP_PULP .equals(originSegment.getOp())) {
				segments.setManualLevel("2");
				segments.setLevel("2");
			}
			else if (Global.REVIEW_OP_TERROR .equals(originSegment.getOp())) {
				segments.setManualLevel("1");
				segments.setLevel("1");
			}
			else if (Global.REVIEW_OP_POLITICIAN.equals(originSegment.getOp())) {
				segments.setManualLevel("1");
				segments.setLevel("1");
			}
		}
		return reviewVideoManagerSegmentsMapper.updateByPrimaryKeySelective(segments);
	}
	
	/**
	 * 审核视频一览-【审核完成】
	 * @param search
	 * @return
	 */
	@Transactional(readOnly=false)
	public int videoManualFinishByPage(CheckInfoSearch search){
		ReviewLevelSettingExample example = new ReviewLevelSettingExample();
		
		//最终危险等级
		String reviewLevel = DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99;
		String level = "";
		String pulpLabel = "";
		String terrorLabel = "";
		String politicianLabel = "";
		String pulpLevel =  DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99;
		String terrorLevel =  DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99;
		String politicianLevel =  DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99;
		
		Date date = new Date();
		int count = 0;
		String videoId = search.getVideoId();
		List<ReviewLevelSetting> settingList = null;
		//查询【审核视频管理表】
		ReviewVideoManager reviewVideoManager = reviewVideoManagerMapper.selectByPrimaryKey(videoId);
		//查询【审核视频管理片段表】
		List<ReviewVideoManagerSegments> segmentsList = reviewVideoManagerSegmentsMapper.selectByExample(getRecodeExample(videoId));
		
		//判断片段表是否有数据
		if(segmentsList != null && segmentsList.size()>0){
			for(ReviewVideoManagerSegments segments : segmentsList){
				//判断是否已人审
				if(segments.getManualLevel() == null || segments.getManualLevel() == ""){
					//设置人审等级-机审等级
					segments.setManualLevel(segments.getRoboticLevel());
					//设置最终等级-机审等级
					segments.setLevel(segments.getRoboticLevel());
				}
				//设置更新时间
				segments.setUpdatedAt(date);
				//设置更新人
				segments.setUpdatedBy(search.getLoginName());
				
				example.clear();
				example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
				.andOpsOpEqualTo(segments.getOp()).andOpsOpLabelEqualTo(segments.getLevel());
				
				settingList = reviewLevelSettingMapper.selectByExample(example);
				if(settingList != null && settingList.size()>0){
					level = settingList.get(0).getOpsOpLevel();
					//获取涉黄、涉暴、涉政的三个最高等级label
					switch (segments.getOp()) {
						//涉黄
						case Global.REVIEW_OP_PULP:
							if(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99.equals(pulpLevel)){
								pulpLevel = level;
								pulpLabel = segments.getLevel();
							}
							//判断当前等级是否高于其他数据等级
							else if(Integer.parseInt(level)>Integer.parseInt(pulpLevel)){
								pulpLevel = level;
								pulpLabel = segments.getLevel();
							}
							
							break;
						//涉暴恐
						case Global.REVIEW_OP_TERROR:
							if(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99.equals(terrorLevel)){
								terrorLevel = level;
								terrorLabel = segments.getLevel();
							}
							//判断当前等级是否高于其他数据等级
							else if(Integer.parseInt(level)>Integer.parseInt(terrorLevel)){
								terrorLevel = level;
								terrorLabel = segments.getLevel();
							}
							break;
						//涉政
						case Global.REVIEW_OP_POLITICIAN:
							if(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99.equals(politicianLevel)){
								politicianLevel = level;
								politicianLabel = segments.getLevel();
							}
							//判断当前等级是否高于其他数据等级
							if(Integer.parseInt(level)>Integer.parseInt(politicianLevel)){
								politicianLevel = level;
								politicianLabel = segments.getLevel();
							}
							break;
							
						default:
							break;
					}
				}
				//更新片段表
				count += reviewVideoManagerSegmentsMapper.updateByPrimaryKeySelective(segments);
			}
		}
		
		List<Integer> list = new ArrayList<>();
		if(!DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99.equals(pulpLevel)){
			list.add(Integer.parseInt(pulpLevel));
		}
		if(!DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99.equals(terrorLevel)){
			list.add(Integer.parseInt(terrorLevel));	
		}
		if(!DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99.equals(politicianLevel)){
			list.add(Integer.parseInt(politicianLevel));
		}
		
		//获取涉黄、涉政、涉暴中的最高危险等级
		reviewLevel = String.valueOf(Collections.max(list));
		
		//设置人审等级-机审等级
		reviewVideoManager.setReviewManualLevel(reviewLevel);
		
		//设置最终审核等级
		reviewVideoManager.setReviewLevel(reviewLevel);
		//设置审核阶段-审核结束
		reviewVideoManager.setReviewStage(DictUtil.REVIEW_STAGE.REVIEW_STAGE_2);
		//设置 ：鉴黄的人工审核的结果等级 review_ops_pulp_manual_level
		if(!StringUtil.isNullOrEmpty(pulpLabel)){
			reviewVideoManager.setReviewOpsPulpManualLevel(pulpLabel);
		}
		
		//设置：鉴暴的人工审核的结果等级 review_ops_terror_manual_level
		if(!StringUtil.isNullOrEmpty(terrorLabel)){
			reviewVideoManager.setReviewOpsTerrorManualLevel(terrorLabel);
		}
		
		//设置：政治人物的人工审核的结果等级 review_ops_politician_manual_level
		if(!StringUtil.isNullOrEmpty(politicianLabel)){
			reviewVideoManager.setReviewOpsPoliticianManualLevel(politicianLabel);
		}
		//设置更新人
		reviewVideoManager.setUpdatedBy(search.getLoginName());
		//设置更新时间
		reviewVideoManager.setUpdatedAt(date);
		
		//判断人审结果是否为通过，如果是，视频打水印
		if(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(reviewVideoManager.getReviewLevel())){
			//截取归档地址获取文件名
			String key = reviewVideoManager.getVideoUri().substring(reviewVideoManager.getVideoUri().lastIndexOf("/")+1);
			//设置图片名称
			int mp4Size = key.indexOf(".");
			String newKey = key.substring(0,mp4Size)+"_R.mp4";
			setWatermark(key, newKey);
			
			int bucketSize = reviewVideoManager.getVideoUri().lastIndexOf("/")+1;
			//获取打水印的视频归档地址
			String videoUri = reviewVideoManager.getVideoUri().substring(0, bucketSize)+newKey;
			//将原视频替换为打水印视频
			reviewVideoManager.setVideoUri(videoUri);
		}
		
		//更新 审核视频管理表
		count += reviewVideoManagerMapper.updateByPrimaryKeySelective(reviewVideoManager);
		return count;
	}
	
	/**
	 * 设置视频水印
	 * @param key		源视频
	 * @param newKey	目标视频
	 * @return 成功/失败
	 */
	public boolean setWatermark(String key,String newKey){
		
		// 生成鉴权
		Auth auth = Auth.create(accessKey, secretKey);
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		String saveAs = UrlSafeBase64.encodeToString(bucket + ":" + newKey);
		// 水印图片地址
		String logoUrl = UrlSafeBase64.encodeToString(cnrLogo);
		// 操作
//		String fops = "avthumb/mp4/wmImage/" + logoUrl;
		String fops = "avthumb/mp4/wmImage/" + logoUrl + "|saveas/" + saveAs;
		// 创建OperationManager对象
		OperationManager operationManager = new OperationManager(auth, cfg);
		
		try {
		    String persistentId = operationManager.pfop(bucket, key, fops, watermarkPipeline, qiniuCallbackWatermark, true);
			return true;
		} catch (QiniuException e) {
		    return false;
		}	
	
	}
}
