package cn.qiniu.delegator.video;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.CheckInfoSearch;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerCuts;
import cn.qiniu.entity.ReviewVideoManagerSegments;
import cn.qiniu.form.video.CheckInfoForm;
import cn.qiniu.form.video.ReviewListSearch;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

import com.google.common.collect.Maps;
@Component
public class CheckInfoDelegator {

	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	@Autowired
	private String qiniuDataUrl;
	
	private static final String TB_LIST = "/checkInfoList";
	private static final String GET_VIDEO_INFO = "/getVideoInfo";
	private static final String GET_VIDEO_SEGMENTS = "/getVideoSegments";
	private static final String GET_VIDEO_CUTS = "/getVideoCuts";
	private static final String GET_VIDEO_TIME_INFO = "/getVideoTimeInfo";
	private static final String VIDEO_MANUAL_FINISH = "/videoManualFinish";
	private static final String VIDEO_MANUAL_FINISH_BY_PAGE = "/videoManualFinishByPage";
	private static final String CHECK_VIDEO_INREVIEW = "/checkVideoInReview";
	
	/**
     * 拼接https地址
     *
     * @return
     */
	private String getQiniuData(String url) {
		return qiniuDataUrl + url;
	}
	
	/**
	 *	列表
	 */
    public List<ReviewVideoManager> getCheckVideoList(ReviewListSearch search) {	
    	String response = "";
    	List<ReviewVideoManager> result = null;
		Map<String,Object> params = Maps.newHashMap();
		//设置参数:条件
		params.put("search", search);
		try {
			//取得审核列表信息
			response = httpClientTemplate.doPost(getQiniuData(TB_LIST), params);
			result = commonDelegator.parseArray(response, ReviewVideoManager.class);
		} catch (Exception e) {
			// 异常处理
			 throw e;
		}
		return result;
    }
    
    /**
     * 根据videoId获取审核视频管理表
     * @param form
     * @return
     */
    public ReviewVideoManager getVideoInfo(CheckInfoForm form){
    	ReviewVideoManager result = new ReviewVideoManager();
    	Map<String,Object> params = Maps.newHashMap();
    	//设置参数:条件
    	params.put("search", form.getSearch());
    	try {
    	//取得审核列表信息
		String response = httpClientTemplate.doPost(getQiniuData(GET_VIDEO_INFO), params);
		result = commonDelegator.parseObject(response, ReviewVideoManager.class);
    	} catch (Exception e) {
			// 异常处理
			 throw e;
		}
    	return result;
    }
    
    /**
     * 根据videoId获取审核视频管理片段表数据
     * @param form
     * @return
     */
    public List<ReviewVideoManagerSegments> getVideoSegments(CheckInfoForm form){
    	List<ReviewVideoManagerSegments> result = null;
    	Map<String,Object> params = Maps.newHashMap();
    	//设置参数:条件
    	params.put("search", form.getSearch());
    	try {
    	//取得审核列表信息
		String response = httpClientTemplate.doPost(getQiniuData(GET_VIDEO_SEGMENTS), params);
		result = commonDelegator.parseObjectList(response, ReviewVideoManagerSegments.class);
    	} catch (Exception e) {
			// 异常处理
			 throw e;
		}
    	return result;
    }
    
    /**
     * 根据videoId获取审核视频管理截帧表数据
     * @param form
     * @return
     */
    public List<ReviewVideoManagerCuts> getVideoCuts(CheckInfoForm form){
    	List<ReviewVideoManagerCuts> result = null;
    	Map<String,Object> params = Maps.newHashMap();
    	//设置参数:条件
    	params.put("search", form.getSearch());
    	try {
    	//取得审核列表信息
		String response = httpClientTemplate.doPost(getQiniuData(GET_VIDEO_CUTS), params);
		result = commonDelegator.parseObjectList(response, ReviewVideoManagerCuts.class);
    	} catch (Exception e) {
			// 异常处理
			 throw e;
		}
    	return result;
    }
    
//    /**
//     * 根据videoId获取视频时长管理表数据
//     * @param form
//     * @return
//     */
//    public ReviewVideoManagerTime getVideoTimeInfo(CheckInfoForm form){
//    	ReviewVideoManagerTime result = new ReviewVideoManagerTime();
//    	Map<String,Object> params = Maps.newHashMap();
//    	//设置参数:条件
//    	params.put("search", form.getSearch());
//    	try {
//    	//取得审核列表信息
//		String response = httpClientTemplate.doPost(getQiniuData(GET_VIDEO_TIME_INFO), params);
//		result = commonDelegator.parseObject(response, ReviewVideoManagerTime.class);
//    	} catch (Exception e) {
//			// 异常处理
//			 throw e;
//		}
//    	return result;
//    }
    
    /**
     * 视频审核页面-【审核完成】
     * @return
     */
    public int videoManualFinish(List<ReviewVideoManagerSegments> list){
    	int count = 0;
    	Map<String,Object> params = Maps.newHashMap();
    	//设置参数:条件
    	params.put("list", list);
    	try {
    		String response = httpClientTemplate.doPost(getQiniuData(VIDEO_MANUAL_FINISH), params);
    		count = Integer.parseInt(response);
    	} catch (Exception e) {
			// 异常处理
			 throw e;
		}
    	return count;
    }
    
    /**
     * 审核视频一览-【审核完成】
     * @return
     */
    public int videoManualFinishByPage(CheckInfoSearch search){
    	int count = 0;
    	Map<String,Object> params = Maps.newHashMap();
    	//设置参数:条件
    	params.put("search", search);
    	try {
    		String response = httpClientTemplate.doPost(getQiniuData(VIDEO_MANUAL_FINISH_BY_PAGE), params);
    		count = Integer.parseInt(response);
    	} catch (Exception e) {
			// 异常处理
			 throw e;
		}
    	return count;
    }
    
    
    /**
     * 视频审核【确认】
     * @return
     */
    public int checkVideoInReview(ReviewVideoManagerSegments record){
    	int count = 0;
    	Map<String,Object> params = Maps.newHashMap();
    	//设置参数:条件
    	params.put("record", record);
    	try {
    		String response = httpClientTemplate.doPost(getQiniuData(CHECK_VIDEO_INREVIEW), params);
    		count = Integer.parseInt(response);
    	} catch (Exception e) {
			// 异常处理
			 throw e;
		}
    	return count;
    }
}
