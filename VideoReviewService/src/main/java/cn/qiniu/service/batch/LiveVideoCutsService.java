package cn.qiniu.service.batch;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import cn.qiniu.entity.LiveVideoPulpCuts;
import cn.qiniu.entity.LiveVideoPulpCutsExample;
import cn.qiniu.entity.ReviewTaskState;
import cn.qiniu.entity.ReviewTaskStateExample;
import cn.qiniu.entity.ReviewVideoInformation;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerExample;
import cn.qiniu.framework.httpclient.HttpClientTemplate;
import cn.qiniu.mapper.LiveVideoPoliticianCutsMapper;
import cn.qiniu.mapper.LiveVideoPulpCutsMapper;
import cn.qiniu.mapper.LiveVideoTerrorCutsMapper;
import cn.qiniu.mapper.ReviewLevelSettingMapper;
import cn.qiniu.mapper.ReviewTaskStateMapper;
import cn.qiniu.mapper.ReviewVideoInformationMapper;
import cn.qiniu.mapper.ReviewVideoManagerMapper;
import cn.qiniu.mapper.ReviewVideoManagerSegmentsMapper;
import cn.qiniu.service.QiniuApiService;
import cn.qiniu.util.CommonUtil;
import cn.qiniu.util.DictUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

@Service
public class LiveVideoCutsService {
	
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
	private ReviewVideoInformationMapper reviewVideoInformationMapper;
	@Autowired
	private ReviewTaskStateMapper reviewTaskStateMapper;
	// 央广视讯logo
	@Autowired
	private String cnrLogo;
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
	// Bucket
	@Autowired
	private String bucket;
	// 水印队列
	@Autowired
	private String watermarkPipeline;
	// 水印回调地址
	@Autowired
	private String qiniuCallbackWatermark;
	// 直播回调地址
	@Autowired 
	private String liveVideoCallback;
	//直播回调间隔秒数
	@Autowired 
	private String liveCallbackInt;
	//直播回调间隔秒数
	@Autowired 
	private QiniuApiService qiniuApiService;
	//直播回调间隔秒数
	@Autowired 
	private String liveCuts;
	// 请求类型-JSON
	private static final String ContentType_JSON = "application/json";
	// 提交直播视频uri
	private static final String V1_START = "/v1/start";
	// 停止直播
	private static final String V1_PAUSE = "/v1/pause";
	
	/**
	 * 截取超过一小时未停止的直播
	 */
	@Transactional(isolation=Isolation.READ_UNCOMMITTED)
	public synchronized void execute(){
		//视频地址
		String source = "";
		//归档地址
		String videoUri = "";
		String jobId = "";
		String videoId = "";
		//接收直播返回数据
		String resultJson = "";
		JSONObject jsonObject = new JSONObject();
		//获取当前系统时间
		Date date = new Date();
		//查询正在直播的数据
		ReviewVideoManagerExample example = new ReviewVideoManagerExample();
		//未删除
		example.createCriteria().andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		//直播
		.andVideoTypeEqualTo(DictUtil.VIDEO_TYPE.VIDEO_TYPE_2)
		//直播中
		.andLiveEndEqualTo(DictUtil.LIVE_END_FLG.LIVE_END_FLG_0);
		
		List<ReviewVideoManager> liveList = reviewVideoManagerMapper.selectByExample(example);
		
		if(liveList != null && liveList.size()>0){
			//遍历正在直播数据
			for(ReviewVideoManager info : liveList){
				//判断直播时间是否达到一小时
				if(date.getTime() - info.getCreatedAt().getTime() >= Integer.parseInt(liveCuts)){
					//直播时间大于等于一小时,停止当前直播，发起新直播
					source = info.getSource();
					//根据直播地址再提交新直播,返回jobId
					resultJson = submitLiveVideo(source);
					jsonObject = JSONObject.parseObject(resultJson);
					videoUri = jsonObject.getString("hls");
					//获取新直播的jobId
					jobId = jsonObject.getString("id");
					videoId = CommonUtil.getUUID();
					
					//新直播数据入库
					insertReviewVideoManager(videoId,jobId,source, videoUri);
					insertReviewVideoInformation(videoId,jobId,source, videoUri);
					insertReviewTaskState(jobId);
					//停止当前直播，直播标识更改为直播停止
					liveVideoPause(info.getJobId());
					updateReviewTaskState(info.getJobId());
					//转换m3u8为mp4
					makeMp4Video(info.getJobId(),info.getVideoUri());
					
					info.setUpdatedAt(date);
					info.setLiveEnd(DictUtil.LIVE_END_FLG.LIVE_END_FLG_1);
//					//如果视频为通过，则视频打水印
//					if(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_0.equals(info.getReviewLevel())){
//						
//						//截取归档地址获取文件名
//						String key = info.getVideoUri().substring(info.getVideoUri().lastIndexOf("/")+1);
//						//设置图片名称
//						int mp4Size = key.indexOf(".");
//						String newKey = key.substring(0,mp4Size)+"_R.mp4";
//						setWatermark(key, newKey);
//						
//						int bucketSize = info.getVideoUri().lastIndexOf("/")+1;
//						//将原视频替换为打水印视频
//						info.setVideoUri(info.getVideoUri().substring(0, bucketSize)+newKey);
//						
//					}
					reviewVideoManagerMapper.updateByPrimaryKeySelective(info);
					
				}
				
			}
		}
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
	 * 提交直播视频分析
	 * @param record		
	 * @param videoId
	 * @return 成功/失败
	 */
	public String submitLiveVideo(String videoUrl){
		String resultJson = null;
		//设置回调地址
		String cburl = liveVideoCallback;
		//直播分析地址
		String url = liveApiUrl + V1_START;
		
		Map<String,String> params = Maps.newHashMap();
		//视频url
		params.put("url", videoUrl);
		//回调url
		params.put("cburl", cburl);
		//回调间隔秒数
		params.put("int", liveCallbackInt);
		String bodyJson = JSON.toJSONString(params);
		byte[] body = bodyJson.getBytes();
		ContentType contentType= ContentType.create(ContentType_JSON);
		Map<String, String> header = this.getHeader(url, "POST", body, ContentType_JSON);
		StringEntity httpEntity = new StringEntity(bodyJson, contentType);
		resultJson = httpClientTemplate.doPost(url, contentType, httpEntity,bodyJson, header, true);
		return resultJson;
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
	 * 新增【审核视频管理表】-直播
	 * @param jobId
	 * @param record
	 */
	private int insertReviewVideoManager(String videoId,String jobId,String source,String videoUri){
		ReviewVideoManager manager = new ReviewVideoManager();
		//id
		manager.setId(videoId);
		//审核任务id	
		manager.setJobId(jobId);
		// 视频资源的原始地址
		manager.setSource(source);
		// 视频资源的归档地址
		manager.setVideoUri(videoUri);
		// 审核阶段
		manager.setReviewStage(DictUtil.REVIEW_STAGE.REVIEW_STAGE_0);
		// 审核的结果等级
		manager.setReviewLevel(DictUtil.REVIEW_LEVEL.REVIEW_LEVEL_99);
		// 视频类型
		manager.setVideoType(DictUtil.VIDEO_TYPE.VIDEO_TYPE_2);
		//设置视频名称
		manager.setVideoName(videoId);
		
		// 人工审核的结果等级
		// 机器审核的结果等级
		// 机器审核的可行性度
		// 鉴黄的人工审核的结果等级
		// 鉴黄的机器审核的结果等级
		// 鉴黄的机器审核的可行性度
		// 鉴暴的人工审核的结果等级
		// 鉴暴的机器审核的结果等级
		// 鉴暴的机器审核的可行性度
		// 政治人物的人工审核的结果等级
		// 政治人物的机器审核的结果等级
		// 政治人物的机器审核的可行性度
		// 视频时长
		// 审核人ID
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
	private int insertReviewVideoInformation(String videoId,String jobId,String source,String videoUri){
		ReviewVideoInformation information = new ReviewVideoInformation();
		//审核视频任务id job_id		
		information.setId(jobId);
		//视频唯一标识符	
		information.setVideoId(videoId);
		//视频资源地址		
		information.setVideoUri(source);
		//视频信息描述desc		
		information.setVideoDesc(null);
		//视频原信息			
		information.setVideoMeta(null);
		//停止审核标志	
		information.setStopReviewFlg(DictUtil.LIVE_END_FLG.LIVE_END_FLG_0);
		//是否等待结果返回			
		information.setWait(null);
		//审核任务类型	
		information.setReviewType(DictUtil.REVIEW_TYPE.REVIEW_TYPE_2);
		//视频归档地址	
		information.setVideoSave(videoUri);
		//操作的默认回调地址		
		information.setHookHost(liveVideoCallback);
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
	
	private LiveVideoPulpCutsExample getExample(String jobId){
		LiveVideoPulpCutsExample example = new LiveVideoPulpCutsExample();
		example.createCriteria().andJobIdEqualTo(jobId);
		example.setOrderByClause("timestamp asc");
		return example;
	}
	
	/**
	 * m3u8格式转换为mp4格式
	 * @param jobId
	 * @param vodeoUrl
	 */
	private void makeMp4Video(String jobId,String videoUrl){
		//截取文件名称
		String newKey = videoUrl.substring(0,videoUrl.lastIndexOf("."));
		newKey = newKey.substring(newKey.lastIndexOf("/")+1, newKey.length());
		List<LiveVideoPulpCuts> list = liveVideoPulpCutsMapper.selectByExample(getExample(jobId));
		if(list != null && list.size()>0){
			String startTime = list.get(0).getTimestamp();
			String endTime = list.get(list.size()-1).getTimestamp();
			qiniuApiService.makeMp4Video(jobId, startTime, endTime, newKey);
		}
	}
}
