package cn.qiniu.delegator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;

@Component
public class QiniuApiDelegator {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(QiniuApiDelegator.class);

	// AccessKey
	@Autowired
	private String accessKey;
	
	// SecretKey
	@Autowired
	private String secretKey;
	
	// Bucket
	@Autowired
	private String bucket;
	
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
}
