package cn.qiniu.delegator.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.AssignReviewSearch;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

@Component
public class AssignReviewDelegator {

	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	@Autowired
	private String qiniuDataUrl;
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	/**
     * 拼接https地址
     *
     * @return
     */
	private String getQiniuData(String url) {
		return qiniuDataUrl + url;
	}
	
	private static final String GET_CHECKINFO_VIDEO = "/assignReview";
	
    /**
	 *	审核视频列表(分页)
	 */
    public ReviewVideoManager getCheckInfo(AssignReviewSearch search) {
    	String response = "";
    	ReviewVideoManager result = null;
		try {
			//取得审核视频
			response = httpClientTemplate.doPost(getQiniuData(GET_CHECKINFO_VIDEO), search);
			result = commonDelegator.parseObject(response, ReviewVideoManager.class);
			
		} catch (Exception e) {
			throw e;
		}
		return result;
    }
    
}
