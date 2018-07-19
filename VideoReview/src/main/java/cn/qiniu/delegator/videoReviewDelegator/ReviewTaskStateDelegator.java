package cn.qiniu.delegator.videoReviewDelegator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.videoReview.ReviewTaskState;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

@Component
public class ReviewTaskStateDelegator {
	private static final String V1_JOB = "/v1/job";
	private static final String STATE = "state";
	
	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	@Autowired
	private String videoReviewServiceUrl;
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	/**
     * 拼接https地址
     *
     * @return
     */
	private String getUrl(String url) {
		return videoReviewServiceUrl + url;
	}
	
	/**
	 * 根据jobId获取审核任务状态
	 */
	public ReviewTaskState getTaskState(String jobId){
		
		ReviewTaskState record = null;
		try {
			String uri = getUrl(V1_JOB) + "/" + jobId +"/"+STATE;
			//取得视频信息
			String result = httpClientTemplate.doGet(uri);
			record = commonDelegator.parseObject(result, ReviewTaskState.class);
		} catch (Exception e) {
			e.toString();
			return null;
		}
		return record;
	}
}
