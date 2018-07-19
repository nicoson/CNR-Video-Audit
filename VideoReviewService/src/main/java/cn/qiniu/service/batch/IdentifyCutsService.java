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
import cn.qiniu.entity.LiveVideoPulpCuts;
import cn.qiniu.entity.LiveVideoPulpCutsExample;
import cn.qiniu.entity.LiveVideoTerrorCuts;
import cn.qiniu.entity.ReviewLevelSetting;
import cn.qiniu.entity.ReviewLevelSettingExample;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerExample;
import cn.qiniu.entity.api.IdentifyResult;
import cn.qiniu.entity.config.MessageConstants;
import cn.qiniu.entity.exceptions.VideoReviewServerException;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

@Service
public class IdentifyCutsService {
	
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
	
	// 七牛API URI(Host:argus.atlab.ai)
	@Autowired 
	private String atlabArgusUri;
	
	// AccessKey
	@Autowired
	private String accessKey;
	
	// SecretKey
	@Autowired
	private String secretKey;
	
	// 请求类型-JSON
	private static final String ContentType_JSON = "application/json";
	
	// 获取片段
	private static final String V1_PLAYBACK = "/v1/playback";
	
	// 图片鉴黄uri
	private static final String V1_PULP = "/v1/pulp";
	
	// 图片鉴暴恐uri
	private static final String V1_TERROR = "/v1/terror";
	
	// 图片鉴政治人物
	private static final String V1_POLITICIAN = "/v1/face/search/politician";
	
	@Transactional(isolation=Isolation.READ_UNCOMMITTED)
	public synchronized void execute(){
		String url = "";
		//涉黄返回实体
		IdentifyResult  pulpResult = null;
		//涉政返回实体
		IdentifyResult politicianResult = null;
		//涉暴恐返回实体
		IdentifyResult terrorResult = null;
		LiveVideoPulpCuts pulpInfo = null;
		LiveVideoPoliticianCuts politicianInfo = new LiveVideoPoliticianCuts();
		LiveVideoTerrorCuts terrorInfo = new LiveVideoTerrorCuts();
		ReviewVideoManager managerInfo = new ReviewVideoManager();
		//查询涉黄截帧表未鉴定的数据
		LiveVideoPulpCutsExample pulpExample = new LiveVideoPulpCutsExample();
		pulpExample.createCriteria().andDelFlgEqualTo(Global.DEL_FLG.DEL_FLG_0)
		.andLabelIsNull();
		pulpExample.setOrderByClause("timestamp asc");
		List<LiveVideoPulpCuts> pulpCutsList = liveVideoPulpCutsMapper.selectByExample(pulpExample);
		if(pulpCutsList != null && pulpCutsList.size()>0){
			//遍历所有数据，进行鉴定
			for(LiveVideoPulpCuts pulpCuts : pulpCutsList){
				pulpInfo = pulpCuts;
				//获取图片地址
				url = pulpCuts.getUrl();
				//图片鉴黄
				pulpResult = identifySexy(url,false);
				//图片涉政
				politicianResult = identifyPolitician(url,false);
				//图片鉴暴恐
				terrorResult = identifyTerror(url,false);
				pulpInfo = updateLiveVideoPulpCuts(pulpCuts,pulpResult);		
				//新增涉政截帧数据
				insertLiveVideoPoliticianCuts(pulpInfo,politicianInfo,politicianResult);
				//新增涉暴恐截帧数据
				insertLiveVideoTerrorCuts(pulpInfo,terrorInfo,terrorResult);
				//更新manager数据
				updateReviewVideoManager(managerInfo,pulpInfo,terrorResult,politicianResult);
				
			}
		}
	}
	
	/**
	 * 图片鉴黄
	 * @param url		图片地址
	 * @param isBucket	是否七牛云的图片
	 * @return 成功/失败
	 */
	public IdentifyResult identifySexy(String url,boolean isBucket){
		String resultJson = null;
		// 待鉴别的图片是七牛云的图片
		if (isBucket) {
			String identifyUrl = url + "/?qpulp";
			resultJson = httpClientTemplate.doGet(identifyUrl);
		}
		else {
			String identifyUrl = atlabArgusUri + V1_PULP;
			JSONObject jsonData= new JSONObject();
			jsonData.put("uri", url);
			JSONObject jsonMain= new JSONObject();
			jsonMain.put("data", jsonData);
			String bodyJson = jsonMain.toJSONString();
			byte[] body = bodyJson.getBytes();
			ContentType contentType= ContentType.create(ContentType_JSON);
			Map<String, String> header = this.getHeader(identifyUrl, "POST", body, ContentType_JSON);
			StringEntity httpEntity = new StringEntity(bodyJson, contentType);
			resultJson = httpClientTemplate.doPost(identifyUrl, contentType, httpEntity,bodyJson, header, true);
		}
		
		IdentifyResult result = null;
		
		if (resultJson != null){
			JSONObject resultJsonObject = JSONObject.parseObject(resultJson);
			if (resultJsonObject.getInteger("code") == 0) {
				result = resultJsonObject.getObject("result", IdentifyResult.class);
			}
		}
		
		if (result == null) {
			throw new VideoReviewServerException(MessageConstants.IDENTIF_SEXY_FAILED_CODE, MessageConstants.IDENTIF_SEXY_FAILED_MESSAGE, resultJson);
		}
		
		return result;

	}	
	
	/**
	 * 图片鉴暴恐
	 * @param url		图片地址
	 * @param isBucket	是否七牛云的图片
	 * @return 成功/失败
	 */
	public IdentifyResult identifyTerror(String url,boolean isBucket){
		String resultJson = null;
		// 待鉴别的图片是七牛云的图片
		if (isBucket) {
			String identifyUrl = url + "/?qterror";
			resultJson = httpClientTemplate.doGet(identifyUrl);
		}
		else {
			String identifyUrl = atlabArgusUri + V1_TERROR;
			JSONObject jsonData= new JSONObject();
			jsonData.put("uri", url);
			JSONObject jsonMain= new JSONObject();
			jsonMain.put("data", jsonData);
			String bodyJson = jsonMain.toJSONString();
			byte[] body = bodyJson.getBytes();
			ContentType contentType= ContentType.create(ContentType_JSON);
			Map<String, String> header = this.getHeader(identifyUrl, "POST", body, ContentType_JSON);
			StringEntity httpEntity = new StringEntity(bodyJson, contentType);
			resultJson = httpClientTemplate.doPost(identifyUrl, contentType, httpEntity,bodyJson, header, true);
		}
		
		IdentifyResult result = null;
		
		if (resultJson != null){
			JSONObject resultJsonObject = JSONObject.parseObject(resultJson);
			if (resultJsonObject.getInteger("code") == 0) {
				result = resultJsonObject.getObject("result", IdentifyResult.class);
			}
		}
		
		if (result == null) {
			throw new VideoReviewServerException(MessageConstants.IDENTIF_SEXY_FAILED_CODE, MessageConstants.IDENTIF_SEXY_FAILED_MESSAGE, resultJson);
		}
		
		return result;

	}
	
	/**
	 * 图片涉政
	 * @param url		图片地址
	 * @param isBucket	是否七牛云的图片
	 * @return 成功/失败
	 */
	public IdentifyResult identifyPolitician(String url,boolean isBucket){
		String resultJson = null;
		// 待鉴别的图片是七牛云的图片
		if (isBucket) {
			String identifyUrl = url + "/?qpolitician";
			resultJson = httpClientTemplate.doGet(identifyUrl);
		}
		else {
			String identifyUrl = atlabArgusUri + V1_POLITICIAN;
			JSONObject jsonData= new JSONObject();
			jsonData.put("uri", url);
			JSONObject jsonMain= new JSONObject();
			jsonMain.put("data", jsonData);
			String bodyJson = jsonMain.toJSONString();
			byte[] body = bodyJson.getBytes();
			ContentType contentType= ContentType.create(ContentType_JSON);
			Map<String, String> header = this.getHeader(identifyUrl, "POST", body, ContentType_JSON);
			StringEntity httpEntity = new StringEntity(bodyJson, contentType);
			resultJson = httpClientTemplate.doPost(identifyUrl, contentType, httpEntity,bodyJson, header, true);
		}
		
		// 初始化结果
		IdentifyResult result = new IdentifyResult();
		result.setLabel(0);
		result.setReview(false);
		result.setScore(new BigDecimal(1));
		
		if (resultJson != null){
			JSONObject resultJsonObject = JSONObject.parseObject(resultJson);
			if (resultJsonObject.getInteger("code") == 0) {
				JSONObject context = resultJsonObject.getJSONObject("result");
				if (context != null) {
					JSONArray array = context.getJSONArray("detections");
					if (array != null) {
						for (int i = 0; i < array.size(); i++) {
							JSONObject detection = array.getJSONObject(i);
							if (detection != null ) {
								JSONObject value = detection.getJSONObject("value");
								String name = value.getString("name");
								if (name != null) {
									result.setLabel(1);
									result.setReview(value.getBooleanValue("review"));
									result.setScore(value.getBigDecimal("score"));
									break;
								}
							}
								
				        }
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 取得Head部（包含鉴权）
	 * @param url			访问地址
	 * @param method		请求方式
	 * @param body			请求内容
	 * @param contentType	请求类型
	 * @return Head部信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, String> getHeader(String url, String method, byte[] body, String contentType){
		Auth me = Auth.create(accessKey, secretKey);
		StringMap authorization = me.authorizationV2(url, method, body, contentType);
		return (Map) authorization.map();
	}
	
	
	/**
	 * 更新涉黄截帧表，图片涉黄鉴定
	 * @param cuts
	 * @param pulpResult
	 * @return
	 */
	private LiveVideoPulpCuts updateLiveVideoPulpCuts(LiveVideoPulpCuts cuts,IdentifyResult pulpResult){
		//设置label
		cuts.setLabel(String.valueOf(pulpResult.getLabel()));
		if(pulpResult.isReview()){
			//人审
			cuts.setReviewType(DictUtil.REVIEW_TYPE.REVIEW_TYPE_2);
		}else{
			//机审
			cuts.setReviewType(DictUtil.REVIEW_TYPE.REVIEW_TYPE_1);
		}
		//设置可行性度
		cuts.setScore(pulpResult.getScore());
		//设置更新时间
		cuts.setUpdatedAt(new Date());
		liveVideoPulpCutsMapper.updateByPrimaryKeySelective(cuts);
		return cuts;
	}
	
	/**
	 * 新增【直播视频帧涉政信息表】数据
	 * @param cuts
	 * @param politicianInfo
	 */
	private void insertLiveVideoPoliticianCuts(LiveVideoPulpCuts cuts,LiveVideoPoliticianCuts politicianInfo,IdentifyResult politicianResult){
		//设置id
		politicianInfo.setId(CommonUtil.getUUID());
		//相关任务id	
		politicianInfo.setJobId(cuts.getJobId());
		//图片地址			
		politicianInfo.setUrl(cuts.getUrl());
		//图片帧的绝对时间戳		
		politicianInfo.setTimestamp(cuts.getTimestamp());
		//本次回调记录的ID号	
		politicianInfo.setReqid(cuts.getReqid());
		//图片涉政鉴定结果		
		politicianInfo.setLabel(String.valueOf(politicianResult.getLabel()));
		//是否需要人审标识		
		if(politicianResult.isReview()){
			//人审
			politicianInfo.setReviewType(DictUtil.REVIEW_TYPE.REVIEW_TYPE_2);
		}else{
			//机审
			politicianInfo.setReviewType(DictUtil.REVIEW_TYPE.REVIEW_TYPE_1);
		}
		//机审可行性度		
		politicianInfo.setScore(politicianResult.getScore());
		//创建人													
		//创建时间	
		politicianInfo.setCreatedAt(new Date());
		//更新人													
		//更新时间		
		politicianInfo.setUpdatedAt(new Date());
		//删除标志													
		politicianInfo.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);
		liveVideoPoliticianCutsMapper.insert(politicianInfo);
	}
	
	/**
	 * 新增【直播视频帧涉政信息表】数据
	 * @param cuts
	 * @param politicianInfo
	 */
	private void insertLiveVideoTerrorCuts(LiveVideoPulpCuts cuts,LiveVideoTerrorCuts terrorInfo,IdentifyResult terrorResult){
		//设置id
		terrorInfo.setId(CommonUtil.getUUID());
		//相关任务id	
		terrorInfo.setJobId(cuts.getJobId());
		//图片地址			
		terrorInfo.setUrl(cuts.getUrl());
		//图片帧的绝对时间戳		
		terrorInfo.setTimestamp(cuts.getTimestamp());
		//本次回调记录的ID号	
		terrorInfo.setReqid(cuts.getReqid());
		//图片涉暴恐鉴定结果		
		terrorInfo.setLabel(String.valueOf(terrorResult.getLabel()));
		//是否需要人审标识		
		if(terrorResult.isReview()){
			//人审
			terrorInfo.setReviewType(DictUtil.REVIEW_TYPE.REVIEW_TYPE_2);
		}else{
			//机审
			terrorInfo.setReviewType(DictUtil.REVIEW_TYPE.REVIEW_TYPE_1);
		}
		//机审可行性度		
		terrorInfo.setScore(terrorResult.getScore());
		//创建人													
		//创建时间	
		terrorInfo.setCreatedAt(new Date());
		//更新人													
		//更新时间		
		terrorInfo.setUpdatedAt(new Date());
		//删除标志													
		terrorInfo.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_0);
		liveVideoTerrorCutsMapper.insert(terrorInfo);
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
	 * 更新manager表数据
	 * @param json
	 */
	private void updateReviewVideoManager(ReviewVideoManager managerInfo,LiveVideoPulpCuts pulpInfo,IdentifyResult terrorResult,
			IdentifyResult politicianResult){
		//最终label
		String label = null;
		//鉴黄数据
		String pulpLabel = pulpInfo.getLabel();
		BigDecimal pulpScore = pulpInfo.getScore();
				
		//鉴暴恐数据
		String terrorLabel = String.valueOf(terrorResult.getLabel());
		BigDecimal terrorScore = terrorResult.getScore();
		
		//鉴暴恐数据
		String politicianLabel = String.valueOf(politicianResult.getLabel());
		BigDecimal politicianScore = politicianResult.getScore();
		
		ReviewVideoManagerExample example = new ReviewVideoManagerExample();
		example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		.andJobIdEqualTo(pulpInfo.getJobId());
		
		List<ReviewVideoManager> list = reviewVideoManagerMapper.selectByExample(example);
		
		if(list != null && list.size()>0){
			managerInfo = list.get(0);
			//获取原涉黄label,如果不为空且等级高于现label,则设置原label
			label =  managerInfo.getReviewOpsPoliticianRoboticLevel();
			if(!StringUtil.isNullOrEmpty(label) && Integer.parseInt(label) > Integer.parseInt(pulpLabel)){
				managerInfo.setReviewOpsPulpRoboticLevel(label);
			}else{
				//为空或者等级低于现label,设置现label
				managerInfo.setReviewOpsPulpRoboticLevel(pulpLabel);
				managerInfo.setReviewOpsPulpRoboticScore(pulpScore);
			}
			
			//获取原涉暴恐label,如果不为空且等级高于现label,则设置原label
			label =  managerInfo.getReviewOpsTerrorRoboticLevel();
			if(!StringUtil.isNullOrEmpty(label) && Integer.parseInt(label) > Integer.parseInt(terrorLabel)){
				managerInfo.setReviewOpsTerrorRoboticLevel(label);
			}else{
				//为空或者等级低于现label,设置现label
				managerInfo.setReviewOpsTerrorRoboticLevel(terrorLabel);
				managerInfo.setReviewOpsTerrorRoboticScore(terrorScore);
			}
			//获取原涉政label,如果不为空且等级高于现label,则设置原label
			label =  managerInfo.getReviewOpsPoliticianRoboticLevel();
			if(!StringUtil.isNullOrEmpty(label) && Integer.parseInt(label) > Integer.parseInt(terrorLabel)){
				managerInfo.setReviewOpsPoliticianRoboticLevel(label);
			}else{
				//为空或者等级低于现label,设置现label
				managerInfo.setReviewOpsPoliticianRoboticLevel(politicianLabel);
				managerInfo.setReviewOpsPoliticianRoboticScore(politicianScore);
			}
			if(DictUtil.REVIEW_STAGE.REVIEW_STAGE_2.equals(managerInfo.getReviewStage())){
				managerInfo.setReviewerId(null);
			}
			
			managerInfo.setReviewStage(DictUtil.REVIEW_STAGE.REVIEW_STAGE_1);
			
			//获取最终等级
			String level = getReviewLevel(managerInfo.getReviewOpsPulpRoboticLevel(),managerInfo.getReviewOpsTerrorRoboticLevel(),managerInfo.getReviewOpsPoliticianRoboticLevel());
			BigDecimal score = getReviewScore(managerInfo.getReviewOpsPulpRoboticScore(),managerInfo.getReviewOpsTerrorRoboticScore(),managerInfo.getReviewOpsPoliticianRoboticScore());
			if(!StringUtil.isNullOrEmpty(managerInfo.getReviewLevel())
					&& !StringUtil.isNullOrEmpty(level)
					&& Integer.parseInt(level) > Integer.parseInt(managerInfo.getReviewLevel())){
				managerInfo.setReviewRoboticLevel(level);
				managerInfo.setReviewRoboticScore(score);
				managerInfo.setReviewLevel(level);
			}
			managerInfo.setUpdatedAt(new Date());
			reviewVideoManagerMapper.updateByExampleSelective(managerInfo, example);
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
	
}
