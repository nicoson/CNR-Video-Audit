package cn.qiniu.service.batch;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import cn.qiniu.config.Global;
import cn.qiniu.entity.LiveVideoPoliticianCuts;
import cn.qiniu.entity.LiveVideoPoliticianCutsExample;
import cn.qiniu.entity.LiveVideoPulpCuts;
import cn.qiniu.entity.LiveVideoPulpCutsExample;
import cn.qiniu.entity.LiveVideoTerrorCuts;
import cn.qiniu.entity.LiveVideoTerrorCutsExample;
import cn.qiniu.entity.ReviewLevelSetting;
import cn.qiniu.entity.ReviewLevelSettingExample;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerExample;
import cn.qiniu.entity.ReviewVideoManagerSegments;
import cn.qiniu.framework.httpclient.HttpClientTemplate;
import cn.qiniu.mapper.LiveVideoPoliticianCutsMapper;
import cn.qiniu.mapper.LiveVideoPulpCutsMapper;
import cn.qiniu.mapper.LiveVideoTerrorCutsMapper;
import cn.qiniu.mapper.ReviewLevelSettingMapper;
import cn.qiniu.mapper.ReviewVideoManagerMapper;
import cn.qiniu.mapper.ReviewVideoManagerSegmentsMapper;
import cn.qiniu.util.CommonUtil;
import cn.qiniu.util.DictUtil;
import cn.qiniu.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.UrlSafeBase64;

@Service
public class MakeVideoSegmentsService {
	
	//涉政
	@Autowired
	private LiveVideoPoliticianCutsMapper liveVideoPoliticianCutsMapper;
	
	//涉黄
	@Autowired
	private LiveVideoPulpCutsMapper liveVideoPulpCutsMapper;
	
	//涉暴恐
	@Autowired
	private LiveVideoTerrorCutsMapper liveVideoTerrorCutsMapper;
	
	@Autowired
	private ReviewVideoManagerMapper reviewVideoManagerMapper;
	
	@Autowired
	private ReviewVideoManagerSegmentsMapper reviewVideoManagerSegmentsMapper;
	
	@Autowired
	private ReviewLevelSettingMapper reviewLevelSettingMapper;
	
	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	@Autowired
	private String liveApiUrl;
	
	// AccessKey
	@Autowired
	private String accessKey;
	
	// SecretKey
	@Autowired
	private String secretKey;
	
	// Bucket
	@Autowired
	private String bucket;
	
	// 水印队列
	@Autowired
	private String watermarkPipeline;
	
	// 水印回调地址
	@Autowired
	private String qiniuCallbackWatermark;
	
	//直播超过此时长未收到数据，则视为直播停止
	@Autowired
	private String livePause;
	//视频超过此时长任为处理中，为处理失败
	@Autowired
	private String videoReviewFail;
	// 央广视讯logo
	@Autowired
	private String cnrLogo;
	
	// 请求类型-JSON
	private static final String ContentType_JSON = "application/json";
	
	// 获取片段
	private static final String V1_PLAYBACK = "/v1/playback";
	
	// 获取片段
	private static final String V1_PAUSE = "/v1/pause";
	
	@Transactional(isolation=Isolation.READ_UNCOMMITTED)
	public synchronized void makeVideoSegments(){
		//更新直播标识(直播停止)
		updateManagerFlg();
		//超时还为处理中的数据,为处理失败
		updateReviewStage();
		//查询涉黄截帧表，获取片段
		makePulpSegments();
		//查询涉暴恐截帧表，获取片段
		makeTerroeSegments();
		//查询涉政截帧表，获取片段
		makePoliticianSegments();
	}
	
	/**
	 * 超时还为处理中的数据,为处理失败
	 */
	public void updateReviewStage(){
		ReviewVideoManagerExample managerExample = new ReviewVideoManagerExample();
		managerExample.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		.andReviewStageEqualTo(DictUtil.REVIEW_STATE.REVIEW_STATE_0);
		List<ReviewVideoManager> list = reviewVideoManagerMapper.selectByExample(managerExample);
		
		Date date = new Date();
		if(list != null && list.size()>0){
			//遍历数据，判断数据的最后更新时间是否超时
			for(ReviewVideoManager info : list ){
				if(date.getTime() - info.getUpdatedAt().getTime() > Integer.parseInt(videoReviewFail)){
					info.setReviewStage(DictUtil.REVIEW_STATE.REVIEW_STATE_4);
					info.setUpdatedAt(date);
					reviewVideoManagerMapper.updateByPrimaryKeySelective(info);
				}
			}
		}
	}
	
	/**
	 * 查询涉黄截帧表，获取片段
	 */
	private void makePulpSegments(){
		//查询涉黄截帧表数据
		LiveVideoPulpCutsExample pulpExample = new LiveVideoPulpCutsExample();
		pulpExample.createCriteria().andDelFlgEqualTo(Global.DEL_FLG.DEL_FLG_0)
		.andLabelIsNotNull();
		pulpExample.setOrderByClause("timestamp asc");
		List<LiveVideoPulpCuts> pulpCutsList = liveVideoPulpCutsMapper.selectByExample(pulpExample);
		//开始时间
		String startTime = "";
		//结束时间
		String endTime = "";
		//审核科目
		String op = "";
		//归档地址
		String url = null;
		String jobId = "";
		//定义遍历记录的条数
		int i = 0;
		//获取片段标识符
		boolean flag = false;
		String resultJson = "";
		//遍历涉黄截帧数据，获取时间段
		if(pulpCutsList != null && pulpCutsList.size()>0){
			op = Global.REVIEW_OP_PULP;
			for(LiveVideoPulpCuts cuts : pulpCutsList){
				i++;
				//相关任务id
				jobId = cuts.getJobId();
				//判断审核结果是否为正常
				if(!"2".equals(cuts.getLabel())){
					//审核结果不正常,如果有开始时间，设置结束时间，如果没有开始时间，设置开始时间
					if(StringUtil.isNullOrEmpty(startTime)){
						//判断是否第一帧
						if(i>1){
							//取前一帧
							startTime = pulpCutsList.get(i-1).getTimestamp();
						}
					}else{
						if(i<pulpCutsList.size()){
							//取后一帧
							endTime = pulpCutsList.get(i).getTimestamp();
						}else{
							endTime = cuts.getTimestamp();
						}
						
					}
				}
				else{
					//审核结果为正常,判断开始时间结束时间是否有数据
					if(!StringUtil.isNullOrEmpty(startTime)
							&& StringUtil.isNullOrEmpty(endTime)){
						//开始时间不为空，结束时间为空,则结束时间=开始时间
						endTime = cuts.getTimestamp();
						flag = true;
					}
					else if(!StringUtil.isNullOrEmpty(startTime)
							&& !StringUtil.isNullOrEmpty(endTime)){
						//开始时间不为空，结束时间不为空
						flag = true;
					}
				}
				//最后一条数据
				if( i == pulpCutsList.size() && !StringUtil.isNullOrEmpty(startTime)){
					if(StringUtil.isNullOrEmpty(endTime)){
						endTime = cuts.getTimestamp();
					}
					flag = true;
				}
				
				if(flag){
					//获取片段	
					resultJson = makePlayback(jobId,startTime,endTime);
					JSONObject jsonObject = JSONObject.parseObject(resultJson);
					//片段url
					url = jsonObject.getString("url");
					//更新manager表数据,返回videoId
					String videoId = updateManager(jobId,op,cuts.getLabel(),cuts.getScore());
					//新增片段表数据
					insertSgement(videoId,op,url,cuts.getLabel(),cuts.getScore(),startTime,endTime);
					startTime = "";
					endTime = "";
					flag = false;
				}
				//更新当前记录删除
				cuts.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_1);
				liveVideoPulpCutsMapper.updateByPrimaryKeySelective(cuts);
				
			}
		}
	}
	
	/**
	 * 查询涉政截帧表，获取片段
	 */
	private void makePoliticianSegments(){
		//查询涉黄截帧表数据
		LiveVideoPoliticianCutsExample politicianExample = new LiveVideoPoliticianCutsExample();
		politicianExample.createCriteria().andDelFlgEqualTo(Global.DEL_FLG.DEL_FLG_0);
		politicianExample.setOrderByClause("timestamp asc");
		List<LiveVideoPoliticianCuts> politicianCutsList = liveVideoPoliticianCutsMapper.selectByExample(politicianExample);
		//开始时间
		String startTime = "";
		//结束时间
		String endTime = "";
		//审核科目
		String op = "";
		//归档地址
		String url = null;
		String jobId = "";
		//定义遍历记录的条数
		int i = 0;
		//获取片段标识符
		boolean flag = false;
		String resultJson = "";
		//遍历涉黄截帧数据，获取时间段
		if(politicianCutsList != null && politicianCutsList.size()>0){
			op = Global.REVIEW_OP_POLITICIAN;
			for(LiveVideoPoliticianCuts cuts : politicianCutsList){
				i++;
				//相关任务id
				jobId = cuts.getJobId();
				//判断审核结果是否为正常
				if(!"0".equals(cuts.getLabel())){
					//审核结果不正常,如果有开始时间，设置结束时间，如果没有开始时间，设置开始时间
					if(StringUtil.isNullOrEmpty(startTime)){
						//判断是否第一帧
						if(i>1){
							//取前一帧
							startTime = politicianCutsList.get(i-1).getTimestamp();
						}
					}else{
						if(i<politicianCutsList.size()){
							//取后一帧
							endTime = politicianCutsList.get(i).getTimestamp();
						}else{
							endTime = cuts.getTimestamp();
						}
						
					}
				}
				else{
					//审核结果为正常,判断开始时间结束时间是否有数据
					if(!StringUtil.isNullOrEmpty(startTime)
							&& StringUtil.isNullOrEmpty(endTime)){
						//开始时间不为空，结束时间为空,则结束时间=开始时间
//						startTime = String.valueOf(Integer.parseInt(startTime)-5000 < 0 ? 0:Integer.parseInt(startTime)-5000);
						endTime = cuts.getTimestamp();
						flag = true;
					}
					else if(!StringUtil.isNullOrEmpty(startTime)
							&& !StringUtil.isNullOrEmpty(endTime)){
						//开始时间不为空，结束时间不为空
//						startTime = String.valueOf(Integer.parseInt(startTime)-5000 < 0 ? 0:Integer.parseInt(startTime)-5000);
//						endTime = String.valueOf(Integer.parseInt(endTime)-5000 < 0 ? 0:Integer.parseInt(endTime)-5000);
						flag = true;
					}
				}
				//最后一条数据
				if( i == politicianCutsList.size() && !StringUtil.isNullOrEmpty(startTime)){
					if(StringUtil.isNullOrEmpty(endTime)){
						endTime = cuts.getTimestamp();
//						endTime = String.valueOf(Integer.parseInt(cuts.getTimestamp())-5000 < 0 ? 0:Integer.parseInt(cuts.getTimestamp())-5000);
					}
					flag = true;
				}
				
				if(flag){
//					startTime = String.valueOf(Integer.parseInt(startTime)-5000 < 0 ? 0:Integer.parseInt(startTime)-5000);
//					endTime = String.valueOf(Integer.parseInt(endTime)-5000 < 0 ? 0:Integer.parseInt(endTime)-5000);
					//获取片段	
					resultJson = makePlayback(jobId,startTime,endTime);
					JSONObject jsonObject = JSONObject.parseObject(resultJson);
					//片段url
					url = jsonObject.getString("url");
					//更新manager表数据,返回videoId
					String videoId = updateManager(jobId,op,cuts.getLabel(),cuts.getScore());
					//新增片段表数据
					insertSgement(videoId,op,url,cuts.getLabel(),cuts.getScore(),startTime,endTime);
					startTime = "";
					endTime = "";
					flag = false;
				}
				//更新当前记录删除
				cuts.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_1);
				liveVideoPoliticianCutsMapper.updateByPrimaryKeySelective(cuts);
				
			}
		}
	}
	
	/**
	 * 查询涉暴恐截帧表，获取片段
	 */
	private void makeTerroeSegments(){
		//查询涉黄截帧表数据
		LiveVideoTerrorCutsExample terrorExample = new LiveVideoTerrorCutsExample();
		terrorExample.createCriteria().andDelFlgEqualTo(Global.DEL_FLG.DEL_FLG_0);
		terrorExample.setOrderByClause("timestamp asc");
		List<LiveVideoTerrorCuts> terrorCutsList = liveVideoTerrorCutsMapper.selectByExample(terrorExample);
		//开始时间
		String startTime = "";
		//结束时间
		String endTime = "";
		//审核科目
		String op = "";
		//归档地址
		String url = null;
		String jobId = "";
		//定义遍历记录的条数
		int i = 0;
		//获取片段标识符
		boolean flag = false;
		String resultJson = "";
		//遍历涉黄截帧数据，获取时间段
		if(terrorCutsList != null && terrorCutsList.size()>0){
			op = Global.REVIEW_OP_TERROR;
			for(LiveVideoTerrorCuts cuts : terrorCutsList){
				i++;
				//相关任务id
				jobId = cuts.getJobId();
				//判断审核结果是否为正常
				if(!"0".equals(cuts.getLabel())){
					//审核结果不正常,如果有开始时间，设置结束时间，如果没有开始时间，设置开始时间
					if(StringUtil.isNullOrEmpty(startTime)){
						//判断是否第一帧
						if(i>1){
							//取前一帧
							startTime = terrorCutsList.get(i-1).getTimestamp();
						}
					}else{
						if(i<terrorCutsList.size()){
							//取后一帧
							endTime = terrorCutsList.get(i).getTimestamp();
						}else{
							endTime = cuts.getTimestamp();
						}
						
					}
				}
				else{
					//审核结果为正常,判断开始时间结束时间是否有数据
					if(!StringUtil.isNullOrEmpty(startTime)
							&& StringUtil.isNullOrEmpty(endTime)){
						//开始时间不为空，结束时间为空,则结束时间=开始时间
//						startTime = String.valueOf(Integer.parseInt(startTime)-5000 < 0 ? 0:Integer.parseInt(startTime)-5000);
						endTime = cuts.getTimestamp();
						flag = true;
					}
					else if(!StringUtil.isNullOrEmpty(startTime)
							&& !StringUtil.isNullOrEmpty(endTime)){
						//开始时间不为空，结束时间不为空
//						startTime = String.valueOf(Integer.parseInt(startTime)-5000 < 0 ? 0:Integer.parseInt(startTime)-5000);
//						endTime = String.valueOf(Integer.parseInt(endTime)-5000 < 0 ? 0:Integer.parseInt(endTime)-5000);
						flag = true;
					}
				}
				//最后一条数据
				if( i == terrorCutsList.size() && !StringUtil.isNullOrEmpty(startTime)){
					if(StringUtil.isNullOrEmpty(endTime)){
						endTime = cuts.getTimestamp();
					}
					flag = true;
				}
				
				if(flag){
					//获取片段	
					resultJson = makePlayback(jobId,startTime,endTime);
					JSONObject jsonObject = JSONObject.parseObject(resultJson);
					//片段url
					url = jsonObject.getString("url");
					//更新manager表数据,返回videoId
					String videoId = updateManager(jobId,op,cuts.getLabel(),cuts.getScore());
					//新增片段表数据
					insertSgement(videoId,op,url,cuts.getLabel(),cuts.getScore(),startTime,endTime);
					startTime = "";
					endTime = "";
					flag = false;
				}
				//更新当前记录删除
				cuts.setUpdatedAt(new Date());
				cuts.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_1);
				liveVideoTerrorCutsMapper.updateByPrimaryKeySelective(cuts);
				
			}
		}
	}
	
	/**
	 * 获取片段
	 * @param id
	 * @param start
	 * @param end
	 */
	public String makePlayback(String id,String start,String end){
		String resultJson = null;
		try {
			//直播分析地址
			String url = liveApiUrl + V1_PLAYBACK;
			
			Map<String,String> params = Maps.newHashMap();
			//视频url
			params.put("id", id);
			//开始时间
			params.put("start", start);
			//结束时间
			params.put("end", end);
			String bodyJson = JSON.toJSONString(params);
			ContentType contentType= ContentType.create(ContentType_JSON);
			StringEntity httpEntity = new StringEntity(bodyJson, contentType);
			resultJson = httpClientTemplate.doPost(url, contentType, httpEntity,bodyJson);
		} catch (Exception e) {
			throw e;
		}
		
		return resultJson;
		
	}
	
	/**
	 * 拼接查询条件-jobId
	 * @param jobId
	 * @return
	 */
	private ReviewVideoManagerExample getManagerExample(String jobId){
		ReviewVideoManagerExample example = new ReviewVideoManagerExample();
		example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		.andJobIdEqualTo(jobId);
		return  example;
	}
	
	/**
	 * 根据jobId更新【审核视频管理表】数据
	 * @param jobId
	 * @return videoId
	 */
	private String updateManager(String jobId,String op,String label,BigDecimal score){
		String videoId = "";
		String newLevel = "";
		String oldLevel = "";
		List<ReviewVideoManager> list = reviewVideoManagerMapper.selectByExample(getManagerExample(jobId));
		if(list != null && list.size()>0){
			videoId = list.get(0).getId();
			ReviewVideoManager manager = list.get(0);
			//直播有不正常片段，设置机审结束
			manager.setReviewStage(DictUtil.REVIEW_STAGE.REVIEW_STAGE_1);
//			//机审危险等级label
//			manager.setReviewRoboticLevel(label);
//			//机审可行性度
//			manager.setReviewRoboticScore(score);
			
			if (Global.REVIEW_OP_PULP.equals(op)){
				newLevel = getLevelByOpAndLabel(op,label);
				oldLevel = getLevelByOpAndLabel(op,manager.getReviewOpsPulpRoboticLevel());
				if(Integer.parseInt(newLevel) > Integer.parseInt(oldLevel)){
					// 鉴黄的机器审核的结果等级
					manager.setReviewOpsPulpRoboticLevel(label);
					// 鉴黄的机器审核的可行性度
					manager.setReviewOpsPulpRoboticScore(score);
				}
				
			} 
			else if (Global.REVIEW_OP_TERROR.equals(op)){
				newLevel = getLevelByOpAndLabel(op,label);
				oldLevel = getLevelByOpAndLabel(op,manager.getReviewOpsTerrorRoboticLevel());
				if(Integer.parseInt(newLevel) > Integer.parseInt(oldLevel)){
					// 鉴暴的机器审核的结果等级
					manager.setReviewOpsTerrorRoboticLevel(label);;
					// 鉴暴的机器审核的可行性度
					manager.setReviewOpsTerrorRoboticScore(score);;
				}
				
				
			}
			else if (Global.REVIEW_OP_POLITICIAN.equals(op)){
				newLevel = getLevelByOpAndLabel(op,label);
				oldLevel = getLevelByOpAndLabel(op,manager.getReviewOpsPoliticianRoboticLevel());
				if(Integer.parseInt(newLevel) > Integer.parseInt(oldLevel)){
					// 政治人物的机器审核的结果等级
					manager.setReviewOpsPoliticianRoboticLevel(label);
					// 政治人物的机器审核的可行性度
					manager.setReviewOpsPoliticianRoboticScore(score);
				}
			}
			
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
				BigDecimal roboticScore = getReviewScore(pulpScore,terrorScore,politicianScore);
				manager.setReviewRoboticScore(roboticScore);
			}
			reviewVideoManagerMapper.updateByPrimaryKeySelective(manager);
		}
		return videoId;
		
	}
	
	
	/**
	 * 根据label获取最终危险等级
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
			if (Global.REVIEW_OP_PULP.equals(setting.getOpsOp()) 
					&& pulpLabel.equals(setting.getOpsOpLabel())) {
				int pulpLevel = Integer.parseInt(setting.getOpsOpLevel());
				if (pulpLevel > level) {
					level = pulpLevel;
				}
			}
			if (Global.REVIEW_OP_TERROR.equals(setting.getOpsOp()) 
					&& terrorLabel.equals(setting.getOpsOpLabel())) {
				int terrorLevel = Integer.parseInt(setting.getOpsOpLevel());
				if (terrorLevel > level)
					level = terrorLevel;
			}
			if (Global.REVIEW_OP_POLITICIAN.equals(setting.getOpsOp()) 
					&& politicianLabel.equals(setting.getOpsOpLabel())) {
				int politicianLevel = Integer.parseInt(setting.getOpsOpLevel());
				if (politicianLevel > level)
					level = politicianLevel;
			}
		}

		return String.valueOf(level);
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
	 * 
	 * @param videoId
	 * @param op
	 * @param url
	 * @param label
	 * @param score
	 * @param startTime
	 * @param endTime
	 */
	private void insertSgement(String videoId,String op,String url,String label,BigDecimal score,String startTime,String endTime){
		
		ReviewVideoManagerSegments segments = new ReviewVideoManagerSegments();
		//id	
		segments.setId(CommonUtil.getUUID());
		//视频id
		segments.setVideoId(videoId);
		//审核项目						
		segments.setOp(op);
		//片段起始时间		
		segments.setOffsetBegin(Integer.parseInt(startTime));
		//片段结束时间	
		segments.setOffsetEnd(Integer.parseInt(endTime));
		//片段资源地址			
		segments.setUri(url);
		//审核的结果等级		
		segments.setLevel(label);
		//人工审核的结果等级													
		//机器审核的结果等级			
		segments.setRoboticLevel(label);
		//机器审核的可行性度	
		segments.setRoboticScore(score);
		//创建人	
		
		//创建时间	
		segments.setCreatedAt(new Date());
		//更新人													
		//更新时间	
		segments.setUpdatedAt(new Date());
		//删除标志													
		segments.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);
		reviewVideoManagerSegmentsMapper.insert(segments);
	}
	
	/**
	 * 直播长时间没有返回片段数据，设置直播结束
	 */
	public void updateManagerFlg(){
		ReviewVideoManagerExample managerExample = new ReviewVideoManagerExample();
		managerExample.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		//直播视频
		.andVideoTypeEqualTo(DictUtil.VIDEO_TYPE.VIDEO_TYPE_2)
		//直播中
		.andLiveEndEqualTo(DictUtil.LIVE_END_FLG.LIVE_END_FLG_0);
		
		LiveVideoPulpCutsExample cutsExample = new LiveVideoPulpCutsExample();
//		cutsExample.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0);
		LiveVideoPoliticianCutsExample politicianCutsExample = new LiveVideoPoliticianCutsExample();
		LiveVideoTerrorCutsExample terrorCutsExample = new LiveVideoTerrorCutsExample();
		
		
		List<ReviewVideoManager> list = reviewVideoManagerMapper.selectByExample(managerExample);
		List<LiveVideoPulpCuts> cutsList = null;
		
		Date date = new Date();
		String level = null;
		BigDecimal roboticScore = new BigDecimal(1);
		
		if(list != null && list.size()>0){
			for(ReviewVideoManager manager : list){
				cutsExample.clear();
				//根据id查询截帧数据
				cutsExample.createCriteria().andJobIdEqualTo(manager.getJobId());
				cutsExample.setOrderByClause("created_at desc");
				cutsList = liveVideoPulpCutsMapper.selectByExample(cutsExample);
				
				if(cutsList != null && cutsList.size()>0){
					//如果当前系统时间-当前jobId的最后cuts生成时间 > 某时间，则直播结束
					if(date.getTime()-cutsList.get(0).getCreatedAt().getTime() > Integer.parseInt(livePause)){
						//获取直播第一帧和最后一帧时间切片
						String startTime = cutsList.get(cutsList.size()-1).getTimestamp();
						String endTime = cutsList.get(0).getTimestamp();
						//生成片段
						String resultJson = makePlayback(cutsList.get(0).getJobId(),startTime,endTime);
						JSONObject jsonObject = JSONObject.parseObject(resultJson);
						
						//片段url
						manager.setVideoUri(jsonObject.getString("url"));
						
						//停止直播任务
						liveVideoPause(manager.getJobId());
						
						manager.setLiveEnd(DictUtil.LIVE_END_FLG.LIVE_END_FLG_1);
						//设置机审结束
						manager.setReviewStage(DictUtil.REVIEW_STAGE.REVIEW_STAGE_1);
						
						if(StringUtil.isNullOrEmpty(manager.getReviewOpsPulpRoboticLevel())){
							manager.setReviewOpsPulpRoboticLevel(Global.PULP_LABEL_2);
							manager.setReviewOpsPulpRoboticScore(roboticScore);
						}
						if(StringUtil.isNullOrEmpty(manager.getReviewOpsTerrorRoboticLevel())){
							manager.setReviewOpsTerrorRoboticLevel(Global.TERROR_LABEL_0);
							manager.setReviewOpsTerrorRoboticScore(roboticScore);
						}
						if(StringUtil.isNullOrEmpty(manager.getReviewOpsPoliticianRoboticLevel())){
							manager.setReviewOpsPoliticianRoboticLevel(Global.PILITICIAN_LABEL_0);
							manager.setReviewOpsPoliticianRoboticScore(roboticScore);
						}
						
						//判断涉黄、涉政、涉暴恐是否都为空，如果是，则视频正常
						if(StringUtil.isNullOrEmpty(manager.getReviewOpsPulpRoboticLevel()) 
								&& StringUtil.isNullOrEmpty(manager.getReviewOpsTerrorRoboticLevel())
								&& StringUtil.isNullOrEmpty(manager.getReviewOpsPoliticianRoboticLevel())){
							manager.setReviewLevel(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_0);
							manager.setReviewRoboticLevel(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_0);
							manager.setReviewRoboticScore(roboticScore);
						}else{
							level = getReviewLevel(manager.getReviewOpsPulpRoboticLevel(),manager.getReviewOpsTerrorRoboticLevel(),manager.getReviewOpsPoliticianRoboticLevel());
							manager.setReviewLevel(level);
							manager.setReviewRoboticLevel(level);
							roboticScore = getReviewScore(manager.getReviewOpsPulpRoboticScore(),manager.getReviewOpsTerrorRoboticScore(),manager.getReviewOpsPoliticianRoboticScore());
							manager.setReviewRoboticScore(roboticScore);
						}
						manager.setUpdatedAt(date);
						
						//直播结束并且审核通过,视频打水印
//						if(DictUtil.LIVE_END_FLG.LIVE_END_FLG_1.equals(manager.getLiveEnd())
//								&& DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(manager.getReviewLevel())){
//							//截取归档地址获取文件名
//							String key = manager.getVideoUri().substring(manager.getVideoUri().lastIndexOf("/")+1);
//							//设置图片名称
//							int mp4Size = key.indexOf(".");
//							String newKey = key.substring(0,mp4Size)+"_R.mp4";
//							setWatermark(key, newKey);
//							
//							int bucketSize = manager.getVideoUri().lastIndexOf("/")+1;
//							//将原视频替换为打水印视频
//							manager.setVideoUri(manager.getVideoUri().substring(0, bucketSize)+newKey);
//						}
						reviewVideoManagerMapper.updateByPrimaryKeySelective(manager);
						
						cutsExample.clear();
						politicianCutsExample.clear();
						terrorCutsExample.clear();
						//删除直播cuts表对应数据
						cutsExample.createCriteria().andJobIdEqualTo(manager.getJobId());
						politicianCutsExample.createCriteria().andJobIdEqualTo(manager.getJobId());
						terrorCutsExample.createCriteria().andJobIdEqualTo(manager.getJobId());
						liveVideoPulpCutsMapper.deleteByExample(cutsExample);
						liveVideoPoliticianCutsMapper.deleteByExample(politicianCutsExample);
						liveVideoTerrorCutsMapper.deleteByExample(terrorCutsExample);
					}
				}
				
			}
		}
	}
	
	/**
	 * 根据jobId暂停直播任务
	 * @param jobId
	 * @return
	 */
	public String liveVideoPause(String jobId){
		String resultJson = null;
		try {
			//直播分析地址
			String url = liveApiUrl + V1_PAUSE;
			
			Map<String,String> params = Maps.newHashMap();
			//jobId
			params.put("id", jobId);
			resultJson = httpClientTemplate.doPost(url, params);
		} catch (Exception e) {
			throw e;
		}
		return resultJson;
	}
	
	/**
	 * 根据审核科目和label获取危险等级
	 * @param op
	 * @param label
	 * @return
	 */
	private String getLevelByOpAndLabel(String op,String label){
		ReviewLevelSettingExample example = new ReviewLevelSettingExample();
		example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		.andOpsOpEqualTo(op).andOpsOpLabelEqualTo(label);
		List<ReviewLevelSetting> list = reviewLevelSettingMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			return list.get(0).getOpsOpLevel();
		}
		return DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_0;
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
