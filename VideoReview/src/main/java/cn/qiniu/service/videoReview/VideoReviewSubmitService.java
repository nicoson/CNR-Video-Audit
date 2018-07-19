package cn.qiniu.service.videoReview;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.config.MessageConstants;
import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.delegator.QiniuApiDelegator;
import cn.qiniu.delegator.VideoReviewServiceDelegator;
import cn.qiniu.delegator.videoReviewDelegator.VideoReviewSubmitDelegator;
import cn.qiniu.entity.videoReview.VideoSubmit;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReview;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewData;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewLabel;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewOp;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewOpParams;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewOther;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewParams;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewSegment;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewVframe;
import cn.qiniu.exceptions.VideoReviewException;
import cn.qiniu.util.common.Constant;
import cn.qiniu.util.common.StringUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 视频分析提交Service
 * 
 * @author Chen Hua
 * @version 2017-09-26
 */
@Service
public class VideoReviewSubmitService {
	// 片段处理理逻辑
	@Autowired
	private String segmentMode;
	// 片段处理理参数
	@Autowired
	private String segmentInterval;
	// 截帧处理理逻辑
	@Autowired
	private String vframeMode;
	// 截帧处理理逻辑
	@Autowired
	private String vframeInterval;
	@Autowired
	private VideoReviewServiceDelegator delegator;
	@Autowired
	private QiniuApiDelegator apiDelegator;
	//视频审核完成回调
	@Autowired
	private String apiResultCallback;
	//视频审核片段回调
	@Autowired
	private String apiSegmentCallback;
			
	/*	`
	 * 提交审核视频
	 */
	@Autowired
	private VideoReviewSubmitDelegator videoReviewSubmitDelegator;
	@Autowired
	private CommonDelegator commonDelegator;
	
	/**
	 * 提交审核视频
	 * @param record 视频审核请求内容
	 * @return jobId
	 */
	public Object submitVideo(JSONObject jsonObject){
		// 任务的唯⼀一标识
		String jobId = null;
		String videoType = null;
		String params = jsonObject.getString("params");
		//获取params
		JSONObject paramsJsonObject = JSONObject.parseObject(params);
		//获取视频类型
		if(paramsJsonObject != null){
			videoType = paramsJsonObject.getString("videoType");
		}else{
			videoType = jsonObject.getString("videoType");
		}
		try {
			VideoSubmit record = commonDelegator.parseObject(jsonObject.toString(), VideoSubmit.class);
			// 校验提交参数
			this.checkSubmitVideoParam(record);
			// 提交的是点播视频
			if (Constant.VIDEO_TYPE.VIDEO_TYPE_1.equals(videoType)){
				// 提交点播识别分析服务
				jobId = apiDelegator.submitVideo(makeQiniuReview(record),record.getData().getAttribute().getId());
				JSONObject jobIdJson = JSONObject.parseObject(jobId);
				jobId = jobIdJson.getString("job");
				//获取视频元信息
				String videoAvinfo = apiDelegator.getVideoAvinfo(record.getData().getUri());
				//获取视频时长
				JSONObject json = JSONObject.parseObject(videoAvinfo);
				List<Object> list = json.getJSONArray("streams");
				Object aaa = JSONObject.toJSON(list.get(0));
				json = JSONObject.parseObject(aaa.toString());
				String videoTime = json.getString("duration");
				
				if (StringUtil.isNullOrEmpty(jobId))
					throw new VideoReviewException(MessageConstants.EMPTY_CODE,
							MessageConstants.EMPTY_MESSAGE, "jobId");
				delegator.createVideoInfo(record,jobId,videoTime);
			}else{
				//获取视频uri
				String url = "";
				String data = jsonObject.getString("data");
				if(data != null){
					JSONObject dataJson = JSONObject.parseObject(data);
					url = dataJson.getString("uri");
				}else{
					url = jsonObject.getString("url");
				}
				//提交直播分析
				String request = apiDelegator.submitLiveVideo(url);
				JSONObject obj = JSONObject.parseObject(request);
//				jobId = obj.getString("id");
				delegator.createLiveVideoInfo(jsonObject,obj);
			}
		} catch (Exception e) {
			throw e;
		}
		return jobId;
	}
	

	private void checkSubmitVideoParam(VideoSubmit param) {
		// DATA（非空校验）
		if (param.getData() == null)
			throw new VideoReviewException(MessageConstants.EMPTY_CODE,
					MessageConstants.EMPTY_MESSAGE, "data");
			
		// URL（非空校验）
		if (StringUtil.isNullOrEmpty(param.getData().getUri()))
			throw new VideoReviewException(MessageConstants.EMPTY_CODE,
					MessageConstants.EMPTY_MESSAGE, "data.uri");
		
		// data.attribute.id（非空校验）
		String videoId = param.getData().getAttribute().getId();
		if (StringUtil.isNullOrEmpty(videoId))
			throw new VideoReviewException(MessageConstants.EMPTY_CODE,
					MessageConstants.EMPTY_MESSAGE, "data.attribute.id");

		// data.attribute.desc（非空校验）
				String desc = param.getData().getAttribute().getDesc();
				if (StringUtil.isNullOrEmpty(desc))
					throw new VideoReviewException(MessageConstants.EMPTY_CODE,
							MessageConstants.EMPTY_MESSAGE, "data.attribute.desc");
				
		// data.attribute.id（存在性校验）
		if (delegator.checkVideoId(videoId) == false)
			throw new VideoReviewException(MessageConstants.EXIST_CODE,
					MessageConstants.EXIST_MESSAGE, "data.attribute.id：" + videoId);

		// params（非空校验）
		if (param.getParams() == null)
			throw new VideoReviewException(MessageConstants.EMPTY_CODE,
					MessageConstants.EMPTY_MESSAGE, "params");
			
		// 审核任务类型（非空校验）
		if (param.getParams().getReviewType() == 0)
			throw new VideoReviewException(MessageConstants.EMPTY_CODE,
					MessageConstants.EMPTY_MESSAGE, "params.reviewType");
			
		// 视频审核操作项目（非空校验）
		if (param.getParams().getOps() == null)
			throw new VideoReviewException(MessageConstants.EMPTY_CODE,
					MessageConstants.EMPTY_MESSAGE, "params.ops");
	}

	/**
	 * 生成提交识别服务实体类
	 */
	private QiniuReview makeQiniuReview(VideoSubmit record){
		//定义实体
		QiniuReview qiniuReview = new QiniuReview();
		QiniuReviewData qiniuReviewData = new QiniuReviewData();
		QiniuReviewParams qiniuReviewParams = new QiniuReviewParams();
		QiniuReviewSegment qiniuReviewSegment = new  QiniuReviewSegment();
		QiniuReviewVframe qiniuReviewVframe = new QiniuReviewVframe();
		
		List<QiniuReviewOp> qiniuReviewOpList = new ArrayList<>();
		
		//设置视频地址
		qiniuReviewData.setUri(record.getData().getUri());
		qiniuReview.setData(qiniuReviewData);
		
		qiniuReviewSegment.setInterval(Integer.parseInt(segmentInterval));
		qiniuReviewSegment.setMode(Integer.parseInt(segmentMode));
		
		qiniuReviewVframe.setInterval(Integer.parseInt(vframeInterval));
		qiniuReviewVframe.setMode(Integer.parseInt(vframeMode));
		
		//是否等待
		qiniuReviewParams.setAsync(!record.getParams().isWait());
		qiniuReviewParams.setSegment(qiniuReviewSegment);
		qiniuReviewParams.setVframe(qiniuReviewVframe);
		qiniuReview.setParams(qiniuReviewParams);
		
		if(record.getParams().getOps().getPulp() != null){
			QiniuReviewOp qiniuReviewOp = new QiniuReviewOp();
			qiniuReviewOp.setOp("pulp_beta");
			//回调地址
			qiniuReviewOp.setHookURL(apiResultCallback);
			qiniuReviewOp.setHookURL_segment(apiSegmentCallback);
			List<QiniuReviewLabel> labels = new ArrayList<QiniuReviewLabel>();
			QiniuReviewLabel label = new QiniuReviewLabel();
			label.setLabel("0");
			label.setSelect(2);
			label.setScore(0);
			labels.add(label);
			label = new QiniuReviewLabel();
			label.setLabel("1");
			label.setSelect(2);
			label.setScore(0);
			labels.add(label);
			label = new QiniuReviewLabel();
			label.setLabel("2");
			label.setSelect(1);
			label.setScore(0);
			labels.add(label);
			QiniuReviewOpParams qiniuReviewOpParams = new QiniuReviewOpParams();
//			qiniuReviewOpParams.setLabels(labels);
			qiniuReviewOp.setParams(qiniuReviewOpParams);
			qiniuReviewOpList.add(qiniuReviewOp);
		}
		if(record.getParams().getOps().getPolitician() != null){
			QiniuReviewOp qiniuReviewOp = new QiniuReviewOp();
			qiniuReviewOp.setOp("face_group_search");
			//回调地址
			qiniuReviewOp.setHookURL(apiResultCallback);
//			qiniuReviewOp.setHookURL_segment(record.getParams().getOps().getPolitician().getHook().getHost());
			qiniuReviewOp.setHookURL_segment(apiSegmentCallback);
			List<QiniuReviewLabel> labels = new ArrayList<QiniuReviewLabel>();
			QiniuReviewLabel label = new QiniuReviewLabel();
			label.setLabel("0");
			label.setSelect(2);
			label.setScore(0);
			labels.add(label);
			label = new QiniuReviewLabel();
			label.setLabel("1");
			label.setSelect(1);
			label.setScore(0);
			labels.add(label);
			QiniuReviewOpParams qiniuReviewOpParams = new QiniuReviewOpParams();
			
//			qiniuReviewOpParams.setLabels(labels);
			
			QiniuReviewOther other = new QiniuReviewOther();
			other.setGroup("facebank");
			qiniuReviewOpParams.setOther(other);
			
			qiniuReviewOp.setParams(qiniuReviewOpParams);
			qiniuReviewOpList.add(qiniuReviewOp);
			
		}
		if(record.getParams().getOps().getTerror() != null){
			QiniuReviewOp qiniuReviewOp = new QiniuReviewOp();
			qiniuReviewOp.setOp("terror");
			qiniuReviewOp.setHookURL(apiResultCallback);
//			qiniuReviewOp.setHookURL_segment(record.getParams().getOps().getTerror().getHook().getHost());
			qiniuReviewOp.setHookURL_segment(apiSegmentCallback);
			List<QiniuReviewLabel> labels = new ArrayList<QiniuReviewLabel>();
			QiniuReviewLabel label = new QiniuReviewLabel();
			label.setLabel("0");
			label.setSelect(1);
			label.setScore(0);
			labels.add(label);
			label = new QiniuReviewLabel();
			label.setLabel("1");
			label.setSelect(2);
			label.setScore(0);
			labels.add(label);
			QiniuReviewOpParams qiniuReviewOpParams = new QiniuReviewOpParams();
//			qiniuReviewOpParams.setLabels(labels);
			qiniuReviewOp.setParams(qiniuReviewOpParams);
			qiniuReviewOpList.add(qiniuReviewOp);
		}
		qiniuReview.setOps(qiniuReviewOpList);
		return qiniuReview;
	}
	
	/**
	 * 提交审核视频-片段信息返回数据
	 */
	public String callBackSegment(JSONObject jsonObject){
		
		return videoReviewSubmitDelegator.callBackSegment(jsonObject);
	}

	/**
	 * 提交审核视频-审核完成返回数据
	 */
	public String callbackVideoResult(String jsonString) {
		return delegator.callbackVideoResult(jsonString);
	}
	
	/**
	 * 直播视频回调
	 * @param list
	 * @return
	 */
	public String callbackLiveBroadcast(JSONObject jsonObject){
		return delegator.cutsCallback(jsonObject);
	}
}
