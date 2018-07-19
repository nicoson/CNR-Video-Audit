package cn.qiniu.controller.videoReview;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.qiniu.controller.common.BaseController;
import cn.qiniu.entity.video.callback.VideoResult;
import cn.qiniu.entity.videoReview.VideoSubmit;
import cn.qiniu.service.videoReview.VideoReviewSubmitService;
import cn.qiniu.util.CommonDelegator;
import cn.qiniu.util.CommonUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 视频提交处理Controller
 * 
 * @author Chen Hua
 * @version 2017-09-26
 */
@RestController
public class VideoReviewSubmitController extends BaseController {
	
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(VideoReviewSubmitController.class);
	
	// 视频审核提交Service
	@Autowired
	private VideoReviewSubmitService videoReviewSubmitService;
	@Autowired
	private CommonDelegator commonDelegator;
	
	/**
	 * 提交点播视频
	 * @return jobId
	 */
	@RequestMapping(value = "/v1/createVideoInfo", method = RequestMethod.POST)
	private int createVideoInfo(HttpServletRequest request){
		//获取请求参数
		JSONObject jsonObject = CommonUtil.getPostRequestParam(request);
		logger.debug(String.format("post createVideoInfo start.param:%s",jsonObject.toJSONString()));
		String jobId = jsonObject.getString("jobId");
		String jsonRecord = jsonObject.getString("submitInfo");
		String videoCover = jsonObject.getString("videoCover");
		String videoTime = jsonObject.getString("videoTime");
		VideoSubmit record = commonDelegator.parseObject(jsonRecord, VideoSubmit.class);
		int count = videoReviewSubmitService.createVideoInfo(jobId, record,videoCover,videoTime);
		return count;
	}
	
	/**
	 * 提交直播视频
	 * @return jobId
	 */
	@RequestMapping(value = "/v1/createLiveVideoInfo", method = RequestMethod.POST)
	private int createLiveVideoInfo(HttpServletRequest request){
		//获取请求参数
		JSONObject jsonObject = CommonUtil.getPostRequestParam(request);
		logger.debug(String.format("post createLiveVideoInfo start.param:%s",jsonObject.toJSONString()));
		int count = videoReviewSubmitService.createLiveVideoInfo(jsonObject);
		return count;
	}
	
	/**
	 * 审核视频完成回调
	 * @return jobId
	 */
	@RequestMapping(value = "/v1/callbackVideoResult", method = RequestMethod.POST)
	private String callbackVideoResult(HttpServletRequest request){
		//获取请求参数
		JSONObject jsonObject = CommonUtil.getPostRequestParam(request);
		logger.info(String.format("post createVideoInfo start.param:%s",jsonObject.toJSONString()));
		String videoId = jsonObject.getString("id");
		String op = jsonObject.getString("op");
		VideoResult result = jsonObject.getObject("result", VideoResult.class);
		String url = videoReviewSubmitService.callbackVideoResult(videoId, op,result);
		logger.info(String.format("post createVideoInfo end.param:%s",url));
		return	url;
	}
	
	/**
	 * 校验视频ID唯一性
	 * @return Id
	 */
	@RequestMapping(value = "/v1/check/video/{videoId}", method = RequestMethod.GET)
	private String checkVideoId(@PathVariable String videoId){
		//获取请求参数
		logger.debug(String.format("get checkVideoId start.param:{videoId: %s}",videoId));
		String result = null;
		if (videoReviewSubmitService.checkVideoId(videoId))
			result = getReturnJson(true, "");
		else
			result = getReturnJson(false, "");
		logger.debug(String.format("get checkVideoId end.result:%s",result));
		return result;
	}
	
	/**
	 * 根据videoId查询视频审核任务类型和结果
	 * return 归档地址
	 */
	@RequestMapping(value = "/reviewType/result", method = RequestMethod.POST)
	private String getReviewType(@RequestBody String videoId){
		logger.info(String.format("post getReviewType start.param: %s",videoId));
		String result = videoReviewSubmitService.getReviewType(videoId);
		logger.info(String.format("post getReviewType end.result:%s",result));
		return result;
	}
	
	/**
	 * 根据videoId和op返回片段回调地址
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(value = "/get/sgement/callback/url", method = RequestMethod.POST)
	private String getSegmentCallbackUrl(@RequestBody JSONObject jsonObject){
		logger.info(String.format("post getSegmentCallbackUrl start.param: %s",jsonObject.toJSONString()));
		String result = videoReviewSubmitService.getSegmentCallbackUrl(jsonObject);
		logger.info(String.format("post getSegmentCallbackUrl end.result:%s",result));
		return result;
	}
		
	/**
	 * 根据videoId返回视频回调地址
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(value = "/get/result/callback/url", method = RequestMethod.POST)
	private String getResultCallbackUrl(@RequestBody JSONObject json){
		logger.info(String.format("post getResultCallbackUrl start.param: %s",json.toJSONString()));
		String result = videoReviewSubmitService.getResultCallbackUrl(json);
		logger.info(String.format("post getResultCallbackUrl end.result:%s",result));
		return result;
	}
	
	/**
	 * 直播视频帧回调
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/v1/cuts/callback", method = RequestMethod.POST)
	private String cutsCallback(@RequestBody JSONObject json){
		logger.info(String.format("post liveCutsCallback start.param: %s",json.toJSONString()));
		String result = videoReviewSubmitService.cutsCallback(json);
		logger.info(String.format("post liveCutsCallback end.result:%s",result));
		return result;
	}
	
	@RequestMapping(value = "/v1/segment/callback", method = RequestMethod.POST)
	private String segmentCallback(@RequestBody JSONObject json){
		logger.info(String.format("post segmentCallback start.param: %s",json.toJSONString()));
		String result = videoReviewSubmitService.segmentCallback(json);
		logger.info(String.format("post segmentCallback end.result:%s",result));
		return result;
	}
}
