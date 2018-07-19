package cn.qiniu.service.videoReview;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import cn.qiniu.config.Global;
import cn.qiniu.controller.videoReview.ReviewVideoController;
import cn.qiniu.entity.LiveVideoPulpCuts;
import cn.qiniu.entity.ReviewLevelSetting;
import cn.qiniu.entity.ReviewLevelSettingExample;
import cn.qiniu.entity.ReviewTaskState;
import cn.qiniu.entity.ReviewTaskStateExample;
import cn.qiniu.entity.ReviewVideoInformation;
import cn.qiniu.entity.ReviewVideoInformationExample;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerCuts;
import cn.qiniu.entity.ReviewVideoManagerExample;
import cn.qiniu.entity.ReviewVideoManagerSegments;
import cn.qiniu.entity.ReviewVideoOps;
import cn.qiniu.entity.ReviewVideoOpsExample;
import cn.qiniu.entity.video.callback.Cut;
import cn.qiniu.entity.video.callback.Label;
import cn.qiniu.entity.video.callback.Segment;
import cn.qiniu.entity.video.callback.VideoResult;
import cn.qiniu.entity.videoReview.VideoSubmit;
import cn.qiniu.entity.videoReview.VideoSubmitOp;
import cn.qiniu.mapper.LiveVideoPulpCutsMapper;
import cn.qiniu.mapper.LiveVideoTerrorCutsMapper;
import cn.qiniu.mapper.ReviewLevelSettingMapper;
import cn.qiniu.mapper.ReviewTaskStateMapper;
import cn.qiniu.mapper.ReviewVideoInformationMapper;
import cn.qiniu.mapper.ReviewVideoManagerCutsMapper;
import cn.qiniu.mapper.ReviewVideoManagerMapper;
import cn.qiniu.mapper.ReviewVideoManagerSegmentsMapper;
import cn.qiniu.mapper.ReviewVideoOpsMapper;
import cn.qiniu.service.QiniuApiService;
import cn.qiniu.util.CommonUtil;
import cn.qiniu.util.DictUtil;
import cn.qiniu.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class VideoReviewSubmitService {
	@Autowired
	private  ReviewTaskStateMapper reviewTaskStateMapper;
	
	@Autowired
	private  ReviewLevelSettingMapper reviewLevelSettingMapper;
	
	@Autowired
	private  ReviewVideoOpsMapper reviewVideoOpsMapper;
	
	@Autowired
	private  ReviewVideoManagerSegmentsMapper reviewVideoManagerSegmentsMapper;
	
	@Autowired
	private  ReviewVideoManagerCutsMapper reviewVideoManagerCutsMapper;
	
	@Autowired
	private  ReviewVideoManagerMapper reviewVideoManagerMapper;
	
	@Autowired
	private  ReviewVideoInformationMapper reviewVideoInformationMapper;
	
	@Autowired
	private  LiveVideoPulpCutsMapper liveVideoPulpCutsMapper;
	
	@Autowired
	private  LiveVideoTerrorCutsMapper liveVideoTerrorCutsMapper;
	
	@Autowired
	private  QiniuApiService qiniuApiService;
	
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(ReviewVideoController.class);
	
	/**
	 * 拼接条件-根据视频id查询审核视频操作项目
	 */
	public ReviewVideoOpsExample getOpsExample(String videoId){
		ReviewVideoOpsExample example = new ReviewVideoOpsExample();
		example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		.andVideoIdEqualTo(videoId);
		return example;
	}
	
	/**
	 * 创建视频信息
	 */
	@Transactional
	public int createVideoInfo(String jobId,VideoSubmit record,String videoCover,String videoTime){
		int count = 0;
		//新增【审核视频管理表】
		count += insertReviewVideoManager(jobId,record,videoCover,videoTime);
		//新增【审核视频任务信息表】
		count += insertReviewVideoInformation(jobId,record);
		//新增【审核视频操作项目】
		count += insertReviewVideoOps(jobId,record);
		insertReviewTaskState(jobId);
		return count;
	}
	
	/**
	 * 直播视频数据入库
	 */
	@Transactional
	public int createLiveVideoInfo(JSONObject jsonObject){
		int count = 0;
		String videoId = CommonUtil.getUUID();
		
		try {
			count += insertReviewVideoManager(jsonObject,videoId);
			count += insertReviewVideoInformation(jsonObject,videoId);
			insertReviewTaskState(jsonObject.getString("jobId"));
		} catch (Exception e) {
			throw e;
		}
		
		return count;
	}
	
	/**
	 * 新增【审核任务状态】
	 * @param jobId
	 */
	private void insertReviewTaskState(String jobId){
		Date date = new Date();
		//获取当前系统时间年月日时分秒时间戳·
		String timeStamp = Long.toString(date.getTime() / 1000);
		ReviewTaskState record = new ReviewTaskState();
		record.setId(CommonUtil.getUUID());
		record.setJobId(jobId);
		record.setTimeCreated(Integer.parseInt(timeStamp));
		record.setTimeDone(null);
		record.setCreatedAt(new Date());
		record.setUpdatedAt(new Date());
		record.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);
		reviewTaskStateMapper.insertSelective(record);
	}
	
	/**
	 * 新增【审核视频管理表】
	 * @param jobId
	 * @param record
	 */
	private int insertReviewVideoManager(String jobId,VideoSubmit record,String videoCover,String videoTime){
		//截取视频名称
		String videoName = record.getData().getUri().substring(record.getData().getUri().lastIndexOf("/")+1);
		int size = videoName.indexOf(".");
		videoName = videoName.substring(0,size);
		ReviewVideoManager manager = new ReviewVideoManager();
		//id
		manager.setId(record.getData().getAttribute().getId());
		//审核任务id	
		manager.setJobId(jobId);
		// 视频资源的原始地址
		manager.setSource(record.getData().getUri());
		// 视频资源的归档地址
		manager.setVideoUri(record.getData().getUri());
		//视频封面的地址
		manager.setVideoCover(videoCover);
		// 审核阶段
		manager.setReviewStage(DictUtil.REVIEW_STAGE.REVIEW_STAGE_0);
		// 审核的结果等级
		manager.setReviewLevel(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99);
		
		manager.setVideoType(record.getParams().getVideoType());
		
		//设置视频名称,如果有,设置名称,如果没有,截取名称
		if(!StringUtil.isNullOrEmpty(record.getData().getAttribute().getDesc())){
			manager.setVideoName(record.getData().getAttribute().getDesc());
		}else{
			manager.setVideoName(videoName);
		}
		// 人工审核的结果等级
		manager.setReviewManualLevel(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99);
		// 机器审核的结果等级
		manager.setReviewRoboticLevel(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99);
		// 视频时长
		manager.setVideoTime(videoTime);
		// 审核人ID
		//创建人
		manager.setCreatedBy(null);
		//创建时间	
		manager.setCreatedAt(new Date());
		//更新人
		manager.setUpdatedBy(null);
		//更新时间
		manager.setUpdatedAt(new Date());
		//删除标志
		manager.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);
		return reviewVideoManagerMapper.insert(manager);		
	}
	
	/**
	 * 新增【审核视频管理表】-直播
	 * @param jobId
	 * @param record
	 */
	private int insertReviewVideoManager(JSONObject jsonObject,String videoId){
		String videoName = "";
		String jobId = jsonObject.getString("jobId");
		String data = jsonObject.getString("data");
		JSONObject dataJson = JSONObject.parseObject(data);
		String url = "";
		if(dataJson != null){
			String attribute = dataJson.getString("attribute");
			JSONObject attributeJson = JSONObject.parseObject(attribute);
			videoName = attributeJson.getString("desc");
		}
		if(dataJson != null){
			url = dataJson.getString("uri");
		}else {
			url = jsonObject.getString("url");
		}
		String videoType = "";
		String params = jsonObject.getString("params");
		if(params != null){
			JSONObject paramsJson = JSONObject.parseObject(params);
			videoType = paramsJson.getString("videoType");
		}else{
			videoType = jsonObject.getString("videoType");
		}
		
		String videoUri = jsonObject.getString("rtmp");
		ReviewVideoManager manager = new ReviewVideoManager();
		//id
		manager.setId(videoId);
		//审核任务id	
		manager.setJobId(jobId);
		// 视频资源的原始地址
		manager.setSource(url);
		// 视频资源的归档地址
		manager.setVideoUri(videoUri);
		// 审核阶段
		manager.setReviewStage(DictUtil.REVIEW_STAGE.REVIEW_STAGE_0);
		// 审核的结果等级
		manager.setReviewLevel(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99);
		// 视频类型
		manager.setVideoType(videoType);
		//设置视频名称
		if(!StringUtil.isNullOrEmpty(videoName)){
			manager.setVideoName(videoName);
		}else{
			manager.setVideoName(videoId);
		}
		// 人工审核的结果等级
		manager.setReviewManualLevel(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99);
		// 机器审核的结果等级
		manager.setReviewRoboticLevel(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99);
		//直播停止标识
		manager.setLiveEnd(DictUtil.LIVE_END_FLG.LIVE_END_FLG_0);
		//创建人
		manager.setCreatedBy(null);
		//创建时间	
		manager.setCreatedAt(new Date());
		//更新人
		manager.setUpdatedBy(null);
		//更新时间
		manager.setUpdatedAt(new Date());
		//删除标志
		manager.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);
		return reviewVideoManagerMapper.insert(manager);		
	}
	
	/**
	 * 新增【审核视频任务信息表】-直播
	 * @param jobId
	 * @param record
	 */
	private int insertReviewVideoInformation(JSONObject jsonObject,String videoId){
		String jobId = jsonObject.getString("jobId");
		String data = jsonObject.getString("data");
		String url = "";
		if(data != null){
			JSONObject dataJosn = JSONObject.parseObject(data);
			url = dataJosn.getString("uri");
		}else{
			url = jsonObject.getString("url");
		}
		String params = jsonObject.getString("params");
		String videoType = "";
		String cburl = "";
		if(params != null){
			JSONObject paramsJson = JSONObject.parseObject(params);
			videoType = paramsJson.getString("videoType");
			cburl = paramsJson.getString("cburl");
		}else{
			videoType = jsonObject.getString("videoType");
			cburl = jsonObject.getString("cburl");
		}
		ReviewVideoInformation information = new ReviewVideoInformation();
		//审核视频任务id job_id		
		information.setId(jobId);
		//视频唯一标识符	
		information.setVideoId(videoId);
		//视频资源地址		
		information.setVideoUri(url);
		//视频信息描述desc		
		information.setVideoDesc(null);
		//视频原信息			
		information.setVideoMeta(null);
		//停止审核标志	
		information.setStopReviewFlg(DictUtil.LIVE_END_FLG.LIVE_END_FLG_0);
		//是否等待结果返回			
		information.setWait(null);
		//审核任务类型	
		information.setReviewType(videoType);
		//视频归档地址	
		information.setVideoSave(null);
		//操作的默认回调地址		
		information.setHookHost(cburl);
		//创建人			
		information.setCreatedBy(null);
		//创建时间	
		information.setCreatedAt(new Date());
		//更新人
		information.setUpdatedBy(null);
		//更新时间	
		information.setUpdatedAt(new Date());
		//删除标志													
		information.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);
		return reviewVideoInformationMapper.insert(information);
	}
	
	/**
	 * 新增【审核视频任务信息表】
	 * @param jobId
	 * @param record
	 */
	private int insertReviewVideoInformation(String jobId,VideoSubmit record){
		ReviewVideoInformation information = new ReviewVideoInformation();
		//审核视频任务id job_id		
		information.setId(jobId);
		//视频唯一标识符	
		information.setVideoId(record.getData().getAttribute().getId());
		//视频资源地址		
		information.setVideoUri(record.getData().getUri());
		//视频信息描述desc		
		information.setVideoDesc(record.getData().getAttribute().getDesc());
		//视频原信息			
		information.setVideoMeta(null);
		//停止审核标志 TODO		
		information.setStopReviewFlg(null);
		//是否等待结果返回			
		information.setWait(record.getParams().isWait());
		//审核任务类型	
		information.setReviewType(String.valueOf(record.getParams().getReviewType()));
		//视频归档地址	
		information.setVideoSave(record.getParams().getSave());
		//操作的默认回调地址		
		if (record.getParams().getHook() != null)
			information.setHookHost(record.getParams().getHook().getHost());
		//创建人			
		information.setCreatedBy(null);
		//创建时间	
		information.setCreatedAt(new Date());
		//更新人
		information.setUpdatedBy(null);
		//更新时间	
		information.setUpdatedAt(new Date());
		//删除标志													
		information.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);
		return reviewVideoInformationMapper.insert(information);
	}
	
	/**
	 * 新增【审核视频操作项目】
	 * @param jobId
	 * @param record
	 */
	private int insertReviewVideoOps(String jobId,VideoSubmit record){
		int count = 0;
		ReviewVideoOps ops = new ReviewVideoOps();
		
		VideoSubmitOp pulpOps = record.getParams().getOps().getPulp();
		VideoSubmitOp politicianOps = record.getParams().getOps().getPolitician();
		VideoSubmitOp terrorOps = record.getParams().getOps().getTerror();
		
		//审核任务id	
		ops.setJobId(jobId);
		//视频id	
		ops.setVideoId(record.getData().getAttribute().getId());
		//创建人
		ops.setCreatedBy(null);
		//创建时间	
		ops.setCreatedAt(new Date());
		//更新人					
		ops.setUpdatedBy(null);
		//更新时间	
		ops.setUpdatedAt(new Date());
		//删除标志	
		ops.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);

		if(pulpOps != null){
			//id
			ops.setId(CommonUtil.getUUID());
			//审核项目名称		
			ops.setOp(Global.REVIEW_OP_PULP);
			//视频审核操作等级
//			ops.setLevels(StringUtils.join(pulpOps.getLevel(),","));
			//视频审核结果保存地址		
			ops.setOpSave(pulpOps.getSave());
			//操作的回调地址
			if (pulpOps.getHook() != null)
				ops.setOpHookHost(pulpOps.getHook().getHost());
			count += reviewVideoOpsMapper.insert(ops);
		}
		if(politicianOps != null){
			//id
			ops.setId(CommonUtil.getUUID());
			//审核项目名称		
			ops.setOp(Global.REVIEW_OP_POLITICIAN);
			//视频审核操作等级
//			ops.setLevels(StringUtils.join(politicianOps.getLevel(),","));
			//视频审核结果保存地址		
			ops.setOpSave(politicianOps.getSave());
			//操作的回调地址
			if (politicianOps.getHook() != null)
				ops.setOpHookHost(politicianOps.getHook().getHost());
			count += reviewVideoOpsMapper.insert(ops);
		}
		if(terrorOps != null){
			//id
			ops.setId(CommonUtil.getUUID());
			//审核项目名称		
			ops.setOp(Global.REVIEW_OP_TERROR);
			//视频审核操作等级
//			ops.setLevels(StringUtils.join(terrorOps.getLevel(),","));
			//视频审核结果保存地址		
			ops.setOpSave(terrorOps.getSave());
			//操作的回调地址
			if (terrorOps.getHook() != null)
				ops.setOpHookHost(terrorOps.getHook().getHost());
			count += reviewVideoOpsMapper.insert(ops);
		}
		return count;
	}
	
	/**
	 * 拼接查询条件-根据videoId查询审核视频任务信息表
	 */
	public ReviewVideoInformationExample getInformarionExample(String videoId){
		ReviewVideoInformationExample example = new ReviewVideoInformationExample();
		example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		.andVideoIdEqualTo(videoId);
		return example;
		
	}
	
	/**
	 * 根据videoId查询视频的审核任务类型和审核结果
	 * @param videoId
	 * @return 归档地址
	 */
	public String getReviewType(String videoId){
		List<ReviewVideoInformation> informationList = reviewVideoInformationMapper.selectByExample(getInformarionExample(videoId));
		ReviewVideoManager reviewVideoManager = reviewVideoManagerMapper.selectByPrimaryKey(videoId);
		if(informationList != null && informationList.size()>0){
			//审核任务类型为机审，并且审核通过
			if(DictUtil.REVIEW_TYPE.REVIEW_TYPE_1.equals(informationList.get(0).getReviewType())
					&& DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(reviewVideoManager.getReviewLevel())
					){
				return informationList.get(0).getVideoSave();
			}
		}
		return "";
	}

	/**
	 * 审核视频回调
	 * @param videoId
	 * @param op
	 * @param result
	 * @return
	 */
	@Transactional(isolation=Isolation.READ_UNCOMMITTED)
	public synchronized String callbackVideoResult(String videoId, String op, VideoResult result) {
		String uri = updateVideoManager(videoId, op, result);
		if(result.getSegments() != null){
			//新增【审核视频管理截帧表】
			insertReviewVideoManagerSegments(videoId, op, result);
		}
		return uri;
	}
	
	/**
	 * 新增【审核视频管理截帧表】
	 * @param videoId
	 * @param op
	 * @param result
	 */
	private void insertReviewVideoManagerSegments(String videoId, String op, VideoResult result){
		ReviewVideoManagerSegments segments = new ReviewVideoManagerSegments();
		String segmentId = "";
		ReviewLevelSettingExample example = new ReviewLevelSettingExample();
		List<ReviewLevelSetting> list = null;
		
		//视频id		
		segments.setVideoId(videoId);
		//审核项目		
		segments.setOp(op);
		//创建人	
		segments.setCreatedBy(null);
		//创建时间		
		segments.setCreatedAt(new Date());
		//更新人		
		segments.setUpdatedBy(null);
		//更新时间	
		segments.setUpdatedAt(new Date());
		//删除标志		
		segments.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);
		
		for(Segment segment : result.getSegments()){
			segmentId = CommonUtil.getUUID();
			//id	
			segments.setId(segmentId);
			String videoLabel = null;
			BigDecimal videoScore = null;
			String level = null;
			//获取片段label的最高等级
			for(Label label : segment.getLabels()){
				//判断涉政情况下,
				if(Global.REVIEW_OP_POLITICIAN.equals(op)
						&& 
						!StringUtil.isNullOrEmpty(label.getLabel())){
					label.setLabel(Global.PILITICIAN_LABEL_1);
				}
				//涉政结果为正常情况
				if(Global.REVIEW_OP_POLITICIAN.equals(op)
						&& 
						"0".equals(label.getScore())){
					continue;
				}
				
				
				example.clear();
				example.createCriteria().andOpsOpEqualTo(op).andOpsOpLabelEqualTo(label.getLabel()).andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0);
				list = reviewLevelSettingMapper.selectByExample(example);
				
				if(list == null || list.size()==0){
					continue;
				}
				
				if (videoLabel == null){
					videoLabel = label.getLabel();
					videoScore = label.getScore();
					level = list.get(0).getOpsOpLevel();
				}
				else {
					if(list != null && list.size()>0){
						if(Integer.parseInt(list.get(0).getOpsOpLevel()) > Integer.parseInt(level)){
							videoLabel = label.getLabel();
							videoScore = label.getScore();
							level = list.get(0).getOpsOpLevel();
						}
					}
//					//涉黄
//					// TODO涉黄label越小危险等级越高，其他label越小，危险等级越低,待完善，通过等级设置表比较危险等级
//					if(Global.REVIEW_OP_PULP.equals(op)){
//						if (Integer.parseInt(label.getLabel()) < Integer.parseInt(videoLabel)){
//							videoLabel = label.getLabel();
//							videoScore = label.getScore();
//						}
//					}else{
//						if (Integer.parseInt(label.getLabel()) > Integer.parseInt(videoLabel)){
//							videoLabel = label.getLabel();
//							videoScore = label.getScore();
//						}
//					}
				}
			}
			//根据label和op获取相应的危险的等级
//			level = labelTolevel(videoLabel,op);
			//片段起始时间	
			segments.setOffsetBegin(segment.getOffset_begin());
			//片段结束时间	
			segments.setOffsetEnd(segment.getOffset_end());
			//片段资源地址		
			segments.setUri(null);
			//审核的结果等级		
			segments.setLevel(videoLabel);
			//人工审核的结果等级			
			segments.setManualLevel(null);
			//机器审核的结果等级	
			segments.setRoboticLevel(videoLabel);
			//机器审核的可行性度	
			segments.setRoboticScore(videoScore);
			//如果危险等级为不正常，数据入库
			if(!DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(level)){
				reviewVideoManagerSegmentsMapper.insert(segments);
				//新增【审核视频管理截帧表】
				insertReviewVideoManagerCuts(segmentId,videoId,segment.getCuts());
			}
			
			
		}
	}
	
	/**
	 * 新增【审核视频管理截帧表】
	 * @param segmentId
	 * @param videoId
	 * @param cutsList
	 */
	private void insertReviewVideoManagerCuts(String segmentId,String videoId,List<Cut> cutsList){
		ReviewVideoManagerCuts cuts = new ReviewVideoManagerCuts();
		
		//视频id			
		cuts.setVideoId(videoId);
		//片段id		
		cuts.setSegmentId(segmentId);
		//创建人		
		cuts.setCreatedBy(null);
		//创建时间	
		cuts.setCreatedAt(new Date());
		//更新人	
		cuts.setUpdatedBy(null);
		//更新时间	
		cuts.setUpdatedAt(new Date());
		//删除标志	
		cuts.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);
		for(Cut cut : cutsList){
			//id				
			cuts.setId(CommonUtil.getUUID());
			//截帧时间	
			cuts.setOffset(cut.getOffset());
			//截帧资源地址
			cuts.setUri(null);
			reviewVideoManagerCutsMapper.insert(cuts);
		}
	}

	/**
	 * 审核完成回调-如过审核任务类型是机审，并且审核通过，返回归档地址(打水印)
	 * @param videoId
	 * @param op
	 * @param result
	 * @return
	 */
	private String updateVideoManager(String videoId, String op, VideoResult result) {
		
		
		logger.info(String.format("updateVideoManager start.param:%s","videoId:"+videoId+","+"----op:"+op+JSONObject.toJSONString(result)));
		
		ReviewVideoManager manager = reviewVideoManagerMapper.selectByPrimaryKey(videoId);
		ReviewLevelSettingExample example = new ReviewLevelSettingExample();
		List<ReviewLevelSetting> settingList = null;
		if (manager == null) {
			return null;
		}
		String videoLabel = null;
		BigDecimal videoScore = null;
		String level = null;
		String opLevel = null;
		if (result.getLabels() != null) {
			
			//遍历labels，获取最高等级Label
			for (cn.qiniu.entity.video.callback.Label label : result.getLabels()) {
				
				//判断涉政情况下,
				if(Global.REVIEW_OP_POLITICIAN.equals(op)
						&& 
						!StringUtil.isNullOrEmpty(label.getLabel())){
					label.setLabel(Global.PILITICIAN_LABEL_1);
				}
				
				//获取所遍历到的数据对应的危险等级
				example.clear();
				example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
				.andOpsOpEqualTo(op).andOpsOpLabelEqualTo(label.getLabel());
				
				settingList = reviewLevelSettingMapper.selectByExample(example);
				if(settingList != null && settingList.size()>0){
					//获取setting表对应等级
					level = settingList.get(0).getOpsOpLevel();
				}
				
				if (StringUtil.isNullOrEmpty(videoLabel)){
					videoLabel = label.getLabel();
					videoScore = label.getScore();
					opLevel = level;
				}
				else if (Integer.parseInt(opLevel) < Integer.parseInt(level)){
					videoLabel = label.getLabel();
					videoScore = label.getScore();
					opLevel = level;
				}
			}
			//机审危险等级label
//			manager.setReviewRoboticLevel(opLevel);
			//机审可行性度
//			manager.setReviewRoboticScore(videoScore);
			
			if (Global.REVIEW_OP_PULP.equals(op)){
				// 鉴黄的机器审核的结果等级
				manager.setReviewOpsPulpRoboticLevel(videoLabel);
				// 鉴黄的机器审核的可行性度
				manager.setReviewOpsPulpRoboticScore(videoScore);
			} 
			else if (Global.REVIEW_OP_TERROR.equals(op)){
				// 鉴暴的机器审核的结果等级
				manager.setReviewOpsTerrorRoboticLevel(videoLabel);;
				// 鉴暴的机器审核的可行性度
				manager.setReviewOpsTerrorRoboticScore(videoScore);;
			}
			else if (Global.REVIEW_OP_POLITICIAN.equals(op)){
				// 政治人物的机器审核的结果等级
				manager.setReviewOpsPoliticianRoboticLevel(videoLabel);
				// 政治人物的机器审核的可行性度
				manager.setReviewOpsPoliticianRoboticScore(videoScore);
			}
		} 
		else {
			if (Global.REVIEW_OP_PULP.equals(op)){
				// 鉴黄的机器审核的结果等级
				manager.setReviewOpsPulpRoboticLevel("2");
				// 鉴黄的机器审核的可行性度
				manager.setReviewOpsPulpRoboticScore(new BigDecimal(1));
			} 
			else if (Global.REVIEW_OP_TERROR.equals(op)){
				// 鉴暴的机器审核的结果等级
				manager.setReviewOpsTerrorRoboticLevel("0");;
				// 鉴暴的机器审核的可行性度
				manager.setReviewOpsTerrorRoboticScore(new BigDecimal(1));;
			}
			else if (Global.REVIEW_OP_POLITICIAN.equals(op)){
				// 政治人物的机器审核的结果等级
				manager.setReviewOpsPoliticianRoboticLevel("0");
				// 政治人物的机器审核的可行性度
				manager.setReviewOpsPoliticianRoboticScore(new BigDecimal(1));
			}
		}
			
		//更新人
		manager.setUpdatedBy(null);
		//更新时间
		manager.setUpdatedAt(new Date());
		
		String pulpLabel = manager.getReviewOpsPulpRoboticLevel();
		String terrorLabel = manager.getReviewOpsTerrorRoboticLevel();
		String politicianLabel = manager.getReviewOpsPoliticianRoboticLevel();
		if (pulpLabel != null
				&& terrorLabel != null
				&& politicianLabel != null){
			// 审核阶段
			manager.setReviewStage(DictUtil.REVIEW_STAGE.REVIEW_STAGE_1);
			// 审核的结果等级
			String reviewLevel = getReviewLevel(pulpLabel,terrorLabel,politicianLabel);
			manager.setReviewLevel(reviewLevel);
			// 机器审核的结果等级
			manager.setReviewRoboticLevel(reviewLevel);
			// 机器审核的可行性度
			BigDecimal pulpScore = manager.getReviewOpsPulpRoboticScore();
			BigDecimal terrorScore = manager.getReviewOpsTerrorRoboticScore();
			BigDecimal politicianScore = manager.getReviewOpsPoliticianRoboticScore();
			BigDecimal score = getReviewScore(pulpScore,terrorScore,politicianScore);
			manager.setReviewRoboticScore(score);
			updateReviewTaskState(manager.getJobId());
			
		}
		if(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(manager.getReviewLevel())){
			//截取归档地址获取文件名
			String key = manager.getVideoUri().substring(manager.getVideoUri().lastIndexOf("/")+1);
			//设置图片名称
			int mp4Size = key.indexOf(".");
			String newKey = key.substring(0,mp4Size)+"_R.mp4";
			qiniuApiService.setWatermark(key, newKey);
			int bucketSize = manager.getVideoUri().lastIndexOf("/")+1;
			//获取打水印的视频归档地址
			String videoUri = manager.getVideoUri().substring(0, bucketSize)+newKey;
			//将原视频替换为打水印视频
			manager.setVideoUri(videoUri);
		}
		
		reviewVideoManagerMapper.updateByPrimaryKeySelective(manager);
		if(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(manager.getReviewLevel())){
			return manager.getVideoUri();
		}
		
		
		
		
		
		
		logger.info(String.format("updateVideoManager end.param:%s","videoId:"+videoId+","+"----op:"+op+JSONObject.toJSONString(result)));
//		if (pulpLabel != null
//				&& terrorLabel != null
//				&& politicianLabel != null){
//			List<ReviewVideoInformation> informationList = reviewVideoInformationMapper.selectByExample(getInformarionExample(videoId));
//			//审核任务类型为机审，并且审核通过
//			if(Integer.parseInt(DictUtil.REVIEW_TYPE.REVIEW_TYPE_1) == informationList.get(0).getReviewType()
//					&& DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(manager.getReviewLevel())
//					){
//				return informationList.get(0).getVideoSave();
//			}
//		}
		return "";
	}

	private BigDecimal getReviewScore(BigDecimal pulpScore,
			BigDecimal terrorScore, BigDecimal politicianScore) {
		BigDecimal score = new BigDecimal(1);
		if (score.compareTo(pulpScore) > 0)
			score = pulpScore;
		if (score.compareTo(terrorScore) > 0)
			score = terrorScore;
		if (score.compareTo(politicianScore) > 0)
			score = politicianScore;
		return score;
	}
	
	/**
	 * 更新【审核任务状态】
	 * @param jobId
	 */
	private void updateReviewTaskState(String jobId){
		Date date = new Date();
		//获取当前系统时间年月日时分秒时间戳·
		String timeStamp = Long.toString(date.getTime() / 1000);
		ReviewTaskStateExample example = new ReviewTaskStateExample();
		example.createCriteria().andJobIdEqualTo(jobId);
		List<ReviewTaskState> list = reviewTaskStateMapper.selectByExample(example);
		if(list != null && list.size()>0){
			list.get(0).setTimeDone(Integer.parseInt(timeStamp));
			reviewTaskStateMapper.updateByExampleSelective(list.get(0), example);
		}
	}

	/**
	 * 根据label获取危险等级
	 * @param pulpLabel
	 * @param terrorLabel
	 * @param politicianLabel
	 * @return
	 */
	private String getReviewLevel(String pulpLabel, String terrorLabel,
			String politicianLabel) {
		
		int level = 0;

		ReviewLevelSettingExample example = new ReviewLevelSettingExample();
		List<ReviewLevelSetting> settingList = reviewLevelSettingMapper.selectByExample(example);
		for (ReviewLevelSetting setting : settingList) {
			if (Global.REVIEW_OP_PULP .equals(setting.getOpsOp()) 
					&& pulpLabel.equals(setting.getOpsOpLabel())) {
				int pulpLevel = Integer.parseInt(setting.getOpsOpLevel());
				if (pulpLevel > level) {
					level = pulpLevel;
				}
			}
			if (Global.REVIEW_OP_TERROR .equals(setting.getOpsOp()) 
					&& terrorLabel.equals(setting.getOpsOpLabel())) {
				int terrorLevel = Integer.parseInt(setting.getOpsOpLevel());
				if (terrorLevel > level)
					level = terrorLevel;
			}
			if (Global.REVIEW_OP_POLITICIAN .equals(setting.getOpsOp()) 
					&& politicianLabel.equals(setting.getOpsOpLabel())) {
				int politicianLevel = Integer.parseInt(setting.getOpsOpLevel());
				if (politicianLevel > level)
					level = politicianLevel;
			}
		}

		return String.valueOf(level);
	}
	
	/**
	 * 根据视频id和op获取视频片段回调地址
	 * @param jsonObject
	 * @return
	 */
	public String getSegmentCallbackUrl(JSONObject jsonObject){
		//获取视频id
		String videoId = jsonObject.getString("id");
		//获取op
		String op = jsonObject.getString("op");
		ReviewVideoOpsExample example = new ReviewVideoOpsExample();
		example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		.andOpEqualTo(op).andVideoIdEqualTo(videoId);
		
		List<ReviewVideoOps> list = reviewVideoOpsMapper.selectByExample(example);
		if(list != null && list.size()>0){
			//返回片段回调地址
			return list.get(0).getOpHookHost();
		}
		return null;
	}
	
	/**
	 * 根据视频id和op获取视频片段回调地址
	 * @param jsonObject
	 * @return
	 */
	public String getResultCallbackUrl(JSONObject json){
		//获取视频id
		String videoId = json.getString("id");
		ReviewVideoInformationExample example = new ReviewVideoInformationExample();
		example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		.andVideoIdEqualTo(videoId);
		
		List<ReviewVideoInformation> list = reviewVideoInformationMapper.selectByExample(example);
		if(list != null && list.size()>0){
			//返回视频回调地址
			return list.get(0).getHookHost();
		}
		return null;
	}

	/**
	 * 校验视频ID唯一性
	 * @param videoId 视频ID
	 * @return true：不存在 false：存在
	 */
	public boolean checkVideoId(String videoId) {
		ReviewVideoManagerExample example = new ReviewVideoManagerExample();
		example.createCriteria().andIdEqualTo(videoId);
		int count = reviewVideoManagerMapper.countByExample(example);
		if (count > 0)
			return false;
		else
			return true;
	}
	
	/**
	 * 直播视频帧回调
	 * @param json
	 * @return
	 */
	public String cutsCallback(JSONObject json){
		List<Object> jsonList = json.getJSONArray("result");
		String reqid = "";
		if(jsonList != null && jsonList.size()>0){
			JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(jsonList.get(0)));
			reqid = jsonObject.getString("reqid");
			String callback = jsonObject.getString("callback");
			if("snapshot".equals(callback)){
				for(Object info : jsonList ){
					//新增【直播视频帧鉴黄信息表】数据
					insertLiveVideoPulpCuts(JSONObject.parseObject(JSON.toJSONString(info)));
					
					updateReviewVideoManager(JSONObject.parseObject(JSON.toJSONString(info)));
				}
			}else if("mp4".equals(callback)){
				updateReviewVideoManager(jsonObject);
			}
		}
		return reqid;
	}
	
	/**
	 * 直播视频帧回调
	 * @param json
	 * @return
	 */
	public String segmentCallback(JSONObject json){
		String segmentId = json.getString("segmentId");
		String url = json.getString("url");
		ReviewVideoManagerSegments record = new ReviewVideoManagerSegments();
		record.setId(segmentId);
		record.setUri(url);
		record.setUpdatedAt(new Date());
		reviewVideoManagerSegmentsMapper.updateByPrimaryKeySelective(record);
		return "";
	}
	
	/**
	 * 新增【直播视频帧鉴黄信息表】数据
	 * @param json
	 */
	private void insertLiveVideoPulpCuts(JSONObject json){
		//jobId
		String jobId = json.getString("id");
		//图片地址
		String url = json.getString("url");
		//图片时间戳
		String timestamp = json.getString("timestamp");
		//回调记录id
		String reqid = json.getString("reqid");
		
		LiveVideoPulpCuts record = new LiveVideoPulpCuts();
		//id										
		record.setId(CommonUtil.getUUID());
		//相关任务id	
		record.setJobId(jobId);
		//图片地址		
		record.setUrl(url);
		//图片帧的绝对时间戳
		record.setTimestamp(timestamp);
		//本次回调记录的ID号
		record.setReqid(reqid);
		//创建人			
		record.setCreatedBy(null);
		//创建时间		
		record.setCreatedAt(new Date());
		//更新人	
		record.setUpdatedBy(null);
		//更新时间	
		record.setUpdatedAt(new Date());
		//删除标志													
		record.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);
		liveVideoPulpCutsMapper.insert(record);
	}
	
	/**
	 * 更新manager表数据
	 * @param json
	 */
	private void updateReviewVideoManager(JSONObject json){
		//jobId
		String jobId = json.getString("id");
		//图片地址/mp4视频地址
		String url = json.getString("url");
		String callback = json.getString("callback");
		
		ReviewVideoManagerExample example = new ReviewVideoManagerExample();
		example.createCriteria()
		.andJobIdEqualTo(jobId);
		List<ReviewVideoManager> list = reviewVideoManagerMapper.selectByExample(example);
		
		if("snapshot".equals(callback)){
			if(list != null && list.size()>0 && StringUtil.isNullOrEmpty(list.get(0).getVideoCover())){
				list.get(0).setVideoCover(url);
				reviewVideoManagerMapper.updateByExampleSelective(list.get(0), example);
			}
		}else if("mp4".equals(callback)){
			if(list != null && list.size()>0){
				list.get(0).setVideoUri(url);
				reviewVideoManagerMapper.updateByExampleSelective(list.get(0), example);
			}
		}
	}
}
