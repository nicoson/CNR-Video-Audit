package cn.qiniu.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.framework.httpclient.HttpClientTemplate;

import com.google.common.collect.Maps;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.UrlSafeBase64;


@Service
public class QiniuApiService {
	// 央广视讯logo
	@Autowired
	private String cnrLogo;
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
	// 水印队列
	@Autowired
	private String watermarkPipeline;
	// 水印回调地址
	@Autowired
	private String qiniuCallbackWatermark;
	
	@Autowired
	private String liveApiUrl;
		
	// 获取片段
	private static final String V1_MP4 = "/v1/mp4";
	// 请求类型-JSON
	private static final String ContentType_JSON = "application/json";
	
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
	
	/**
	 * 截取视频，生成mp4格式视频
	 * @param jobId
	 * @param startTime
	 * @param endTime
	 * @param saveAs
	 * @return
	 */
	public String makeMp4Video(String jobId,String startTime,String endTime,String newKey){
		//直播分析地址
		String url = liveApiUrl + V1_MP4;
		Map<String,String> params = Maps.newHashMap();
		//视频url
		params.put("id", jobId);
		//开始时间
		params.put("start", startTime);
		//结束时间
		params.put("end", endTime);
		params.put("name", newKey);
		String result;
		try {
			result = httpClientTemplate.doPost(url,params);
		} catch (Exception e) {
			throw e;
		}
		return result;
		
	}
}
