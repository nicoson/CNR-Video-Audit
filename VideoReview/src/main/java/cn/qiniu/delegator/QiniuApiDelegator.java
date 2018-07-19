package cn.qiniu.delegator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.config.MessageConstants;
import cn.qiniu.entity.api.IdentifyResult;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReview;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewOp;
import cn.qiniu.exceptions.VideoReviewServerException;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.StringUtils;
import com.qiniu.util.UrlSafeBase64;

@Component
public class QiniuApiDelegator {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(QiniuApiDelegator.class);
	// 提交直播视频uri
	private static final String V1_START = "/v1/start";
	// 图片鉴黄uri
	private static final String V1_VIDEO = "/v1/video";
	// 图片鉴黄uri
	private static final String V1_PULP = "/v1/pulp";
	// 图片鉴暴恐uri
	private static final String V1_TERROR = "/v1/terror";
	// 图片鉴政治人物
	private static final String V1_POLITICIAN = "/v1/face/search/politician";
	// 请求类型-JSON
	private static final String ContentType_JSON = "application/json";
	// 七牛API URI(Host:argus.atlab.ai)
	@Autowired 
	private String atlabArgusUri;
	//直播回调间隔秒数
	@Autowired 
	private String liveCallbackInt;
	// 直播回调地址
	@Autowired 
	private String liveVideoCallback;
	// Http Client
	@Autowired
	private HttpClientTemplate httpClientTemplate; 
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
	// 直播api服务
	@Autowired
	private String liveApiUrl;
	// 水印队列
	@Autowired
	private String watermarkPipeline;
	//打水印回调地址
	@Autowired
	private String qiniuCallbackWatermark;
	//截帧回调地址
	@Autowired
	private String videoCoverCallback;
	
	/**
	 * 提交点播视频分析
	 * @param record		
	 * @param videoId
	 * @return 成功/失败
	 */
	public String submitVideo(QiniuReview record,String videoId){
		String resultJson = null;
		String url = atlabArgusUri + V1_VIDEO + "/" + videoId;
		String pulp_betaUrl = "http://localhost:8888"+ V1_VIDEO;
		try {
			//保存鉴黄请求信息
			QiniuReviewOp pulp_beta = record.getOps().get(0);
			//去除鉴黄信息发送涉政、鉴暴恐请求
			record.getOps().remove(0);
			String bodyJson = JSON.toJSONString(record);
			
			//重置鉴黄信息
			record.setOps(new ArrayList<QiniuReviewOp>());
			record.getOps().add(pulp_beta);
			String bodyJson1 = JSON.toJSONString(record)+"&&&{\"job\":\""+videoId+"\"}";
//			String bodyJson1 = JSON.toJSONString(record);
			
			
			byte[] body = bodyJson.getBytes();
			
			
			ContentType contentType= ContentType.create(ContentType_JSON);
			Map<String, String> header = this.getHeader(url, "POST", body, ContentType_JSON);
			StringEntity httpEntity = new StringEntity(bodyJson, contentType);
			//发送请求：政治人物、暴恐鉴定
			resultJson = httpClientTemplate.doPost(url, contentType, httpEntity,bodyJson, header, true);
			
			
			
			
//			bodyJson1 = bodyJson1+"$$$"+resultJson;
			byte[] body1 = bodyJson1.getBytes();
			Map<String, String> header1 = this.getHeader(pulp_betaUrl, "POST", body1, ContentType_JSON);
			StringEntity httpEntity1 = new StringEntity(bodyJson1, contentType);
			//发送请求：鉴黄
			httpClientTemplate.doPost(pulp_betaUrl, contentType, httpEntity1,bodyJson1, header1, true);
			
		} catch (Exception e) {
			throw e;
			
		}
		
		
		return resultJson;
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
		
		logger.info(String.format("set watermark start.param:{bucket:%s,key:%s,fops:%s,pipeline:%s,notifyURL:%s,force:%s}", bucket, key, fops, watermarkPipeline, qiniuCallbackWatermark, true));
		try {
		    String persistentId = operationManager.pfop(bucket, key, fops, watermarkPipeline, qiniuCallbackWatermark, true);
			logger.info(String.format("set watermark end.result:{bucket:%s,key:%s,persistentId:%s}", bucket, key,persistentId));
			return true;
		} catch (QiniuException e) {
			logger.info(String.format("set watermark failed.result:%s}",e.response.toString()));
		    return false;
		}	
	}

	/**
	 * 合并视频
	 * @param key		源视频
	 * @param newKey	目标视频
	 * @return 成功/失败
	 */
	public String mergeVideo(String key,String newKey){
		
		String name = UrlSafeBase64.encodeToString(bucket + ":" + new Date().getTime() + ".mp4");
		//待处理文件名
		Auth auth = Auth.create(accessKey, secretKey);
		//数据处理指令，支持多个指令
		String url1 = UrlSafeBase64.encodeToString("http://ow8zztdmn.bkt.clouddn.com/movie.mp4");
		String fops = "avconcat/2/format/mp4/index/1/" + url1 + "|saveas/" + name;
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		//构建持久化数据处理对象
		OperationManager operationManager = new OperationManager(auth, cfg);
		try {
			String persistentId = operationManager.pfop(bucket, "movie.mp4", fops, watermarkPipeline, qiniuCallbackWatermark, true);
			System.out.println(persistentId);
			return persistentId;
		} catch (QiniuException e) {
			System.err.println(e.response.toString());
		}
		return null;
	}

	/**
	 * 高清视频
	 * @param key		源视频
	 * @param newKey	目标视频
	 * @return 成功/失败
	 */
	public String changeVideoSize(String key){
		
		if (org.apache.commons.lang3.StringUtils.isEmpty(key) || !key.contains(".")) {
			return null;
		}
		int index = key.indexOf(".");
		String fileName = key.substring(0, index);
		String fileSub = key.substring(index);
		Auth auth = Auth.create(accessKey, secretKey);
		//数据处理指令，支持多个指令
		String name = UrlSafeBase64.encodeToString(bucket + ":" + fileName + "_hd" + fileSub);
		String fops = "avthumb/mp4/ab/160k/ar/44100/acodec/libfaac/r/30/vb/2400k/vcodec/libx264/s/1280x720/autoscale/1/stripmeta/0|saveas/" + name;
		String name1 = UrlSafeBase64.encodeToString(bucket + ":" + fileName + "_ts" + fileSub);
		String fops1 = "avthumb/mp4/ab/128k/ar/44100/acodec/libfaac/r/30/vb/1200k/vcodec/libx264/s/854x480/autoscale/1/stripmeta/0|saveas/" + name1;
		String name2 = UrlSafeBase64.encodeToString(bucket + ":" + fileName + "_bd" + fileSub);
		String fops2 = "avthumb/mp4/ab/160k/ar/44100/acodec/libfaac/r/30/vb/5400k/vcodec/libx264/s/1920x1080/autoscale/1/strpmeta/0|saveas/" + name2;
		//将多个数据处理指令拼接起来
		String persistentOpfs = StringUtils.join(new String[]{
				fops, fops1, fops2
		}, ";");
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		//...其他参数参考类注释
		//构建持久化数据处理对象
		OperationManager operationManager = new OperationManager(auth, cfg);
		try {
			String persistentId = operationManager.pfop(bucket, key, persistentOpfs, watermarkPipeline, qiniuCallbackWatermark, true);
			return persistentId;
		} catch (QiniuException e) {
			System.err.println(e.response.toString());
			e.printStackTrace();
		}
		return null;	
	}

	/**
	 * 剪切视频
	 * @param key		源视频
	 * @param newKey	目标视频
	 * @return 成功/失败
	 */
	public String cutVideo(String key,String newKey,String start,String end){
		
		Auth auth = Auth.create(accessKey, secretKey);
		String saveAs = UrlSafeBase64.encodeToString(bucket + ":" + newKey);
		//数据处理指令，支持多个指令
//		String name = UrlSafeBase64.encodeToString(bucket + ":" + new Date().getTime() + ".mp4");
		String fops = "avthumb/mp4/ss/" + start + "/t/" + (Double.parseDouble(end) - Double.parseDouble(start)) + "|saveas/" + saveAs;
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		//...其他参数参考类注释
		//构建持久化数据处理对象
		OperationManager operationManager = new OperationManager(auth, cfg);
		try {
			String persistentId = operationManager.pfop(bucket, key, fops, watermarkPipeline, qiniuCallbackWatermark, true);
			return persistentId;
		} catch (QiniuException e) {
			System.err.println(e.response.toString());
			e.printStackTrace();
		}
		return null;
		
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
	 * 图片鉴黄-七牛云图片
	 * @param url	图片url
	 * @return
	 */
	public String pulpReview(String url){
		String result = httpClientTemplate.doGet(url+"?qpulp");
		return result;
	}
	
	/**
	 * 图片鉴暴恐-七牛云图片
	 * @param url	图片url
	 * @return
	 */
	public String terrorReview(String url){
		String result = httpClientTemplate.doGet(url+"?qterror");
		return result;
	}
	
	/**
	 * 图片政治人物识别-七牛云图片
	 * @param url	图片url
	 * @return
	 */
	public String politicianReview(String url){
		String result = httpClientTemplate.doGet(url+"?qpolitician");
		return result;
	}
	
	/**
	 * 获取视频某一帧图片
	 * @param key	视频名称
	 * @param targetName	图片名称
	 * @param time	时间点
	 * @return	归档地址
	 */
	public String getVideoPic(String key, String targetName, String time) {
		String name = UrlSafeBase64.encodeToString(bucket + ":" + targetName);
		//待处理文件名
		Auth auth = Auth.create(accessKey, secretKey);
		//数据处理指令，支持多个指令
		if(time=="" || time==null){
			time = "0.001";
		}
		String fops = "vframe/jpg/offset/"+time+"/w/110/h/60|saveas/" + name;
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		//构建持久化数据处理对象
		OperationManager operationManager = new OperationManager(auth, cfg);
		try {
			String persistentId = operationManager.pfop(bucket, key, fops, watermarkPipeline, videoCoverCallback, true);
			return persistentId;
		} catch (QiniuException e) {
			System.err.println(e.response.toString());
		}
		return null;
	}
	/**
	 * 批量删除文件
	 * @param key	视频名称
	 * @param targetName	图片名称
	 * @param time	时间点
	 * @return	归档地址
	 */
	public boolean deleteFiles(String[] keyList) {
		// 生成鉴权
		Auth auth = Auth.create(accessKey, secretKey);

		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());

		//构建持久化数据处理对象
		BucketManager bucketManager = new BucketManager(auth, cfg);
		
		try {
			BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
			batchOperations.addDeleteOp(bucket, keyList);
			Response response = bucketManager.batch(batchOperations);
		    BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
		    for (int i = 0; i < keyList.length; i++) {
		        BatchStatus status = batchStatusList[i];
		        String key = keyList[i];
		        System.out.print(key + "\t");
		        if (status.code == 200) {
		    		logger.info(String.format("delete %s success.", key));
		        } else {
		    		logger.info(String.format("delete %s failed.reason:%s", key,status.data.error));
		        }
		    }
		} catch (QiniuException e) {
			logger.error(e.response.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * 获取视频元信息
	 * @param 视频url
	 * @return
	 */
	public String getVideoAvinfo(String url){
		return httpClientTemplate.doGet(url+"?avinfo");
	}
}
