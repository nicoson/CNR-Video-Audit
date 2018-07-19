package cn.qiniu.delegator.videoReviewDelegator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.videoReview.ReviewVideoTask;
import cn.qiniu.entity.videoReview.VideoForm;
import cn.qiniu.form.entity.ManualForm;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

@Component
public class VideoReviewDelegator {
	private static final String VIDEO_REVIEW_JOBS = "/v1/jobs";
	//根据jobid 获取审核审核结果  VideoReviewController 取审核结果
	private static final String REVIEW_VIDEO_RESULT = "/reviewResult";
	// 提交人工审核结果
	private static final String SUBMIT_MANUAL_REVIEW = "/v1/video/manual";
	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	@Autowired
	private String videoReviewServiceUrl;
	
	@Autowired
	private String videoManagerUrl;
	
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
	 *获取审核视频任务列表
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<ReviewVideoTask> getReviewVideoTaskList(){
		String response = httpClientTemplate.doGet(getUrl(VIDEO_REVIEW_JOBS));
		return commonDelegator.parseArray(response, ReviewVideoTask.class);
	}
	/**
	 * 根据jobid 
	 * @param jobId
	 * @return 视频审核 结果
	 */
	public VideoForm getReviewVideoId(String jobId){
		String response=null;
		String url =null;
		VideoForm videoform = new VideoForm();
		//取得审核列表信息
		url = getUrl(REVIEW_VIDEO_RESULT)+"/"+jobId;
		response= httpClientTemplate.doGet(url);
		videoform = commonDelegator.parseObject(response, VideoForm.class);
		return videoform;
	}
	/**
	 * 提交人工审核结果
	 * @param videoId
	 * @return
	 */
	public String submitManualRewview(String videoId,ManualForm form){
		String url = getUrl(SUBMIT_MANUAL_REVIEW)+"/"+videoId;
		return httpClientTemplate.doPost(url,form);
	}
}
