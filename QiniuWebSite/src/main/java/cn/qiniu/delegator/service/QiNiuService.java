//package cn.qiniu.delegator.service;
//
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//
//import cn.qiniu.delegator.CommonDelegator;
//import cn.qiniu.delegator.entity.Attribute;
//import cn.qiniu.delegator.entity.Data;
//import cn.qiniu.delegator.entity.Job;
//import cn.qiniu.delegator.entity.Level;
//import cn.qiniu.delegator.entity.ManualVideoBody;
//import cn.qiniu.delegator.entity.Meta;
//import cn.qiniu.delegator.entity.Op;
//import cn.qiniu.delegator.entity.Ops;
//import cn.qiniu.delegator.entity.Segments;
//import cn.qiniu.delegator.entity.VideoBody;
//import cn.qiniu.delegator.entity.VideoManager;
//import cn.qiniu.delegator.entity.VideosList;
//import cn.qiniu.framework.httpclient.HttpClientTemplate;
//
//
//@Service
//public class QiNiuService extends CommonDelegator {
//	// 待拼接提交审核申请地址
//	private static String VIDEO = "/v1/video";
//
//	// 待拼接提交视频人工审核结果地址
//	private static String MANUAL = "/manual";
//
//	// 待拼接获取视频信息地址
//	private static String V1_VIDEO = "/v1/video";
//
//	// 待拼接获取视频列表地址
//	private static String VIDEOS = "/v1/videos";
//
//	@Autowired
//	private HttpClientTemplate httpClientTemplate;
//
//	@Autowired
//	private QiniuTokenService service;
//
//	/**
//	 * 提交审核申请
//	 *
//	 * @return(job_id)
//	 */
//	public String submitReview(String videoId) {
//		// 拼接https地址
//		String uri = VIDEO + "/" + videoId;
//		VideoBody videobody = new VideoBody();
//		boolean wait=true;
//		videobody.setWait(wait);
//		Data data = new Data();
//		String url="";
//		data.setUri(url);
//		Meta meta = new Meta();
//		Attribute att = new Attribute();
//		att.setMeta(meta);
//		videobody.setData(data);
//		String bodystr = JSON.toJSONString(videobody);
//		String jobjson = "" ;
//		try {
//			String Data = "<GET> + ' ' + <"
//					+ uri
//					+ "> + '\nHost: ' + <atlab.ai> + '\nContent-Type: ' + <application/json> + '\n\n'+ <"
//					+ bodystr + ">";
//			String secretKey = "hC2pI3urQ8-EaE283lhfmwTAxvpjBHNcYgWG8ama";
//			String QiniuToken = service.getSignature(Data, secretKey);
//			Map<String, String> params = new HashMap<>();
//			params.put("Authentication", QiniuToken);
//			// TODO
//			String json = httpClientTemplate.doPost(uri, videobody, params);
//			Job job = parseObject(json, Job.class);
////			id = job.getJob_id();
//			jobjson = JSON.toJSONString(job);
//		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return jobjson;
//	}
//
//	/**
//	 * 提交视频人工审核结果
//	 *
//	 * @return(level)
//	 */
//	public String submitManualReview(String videoId) {
//		// 拼接https地址
//		String uri = VIDEO + "/" + videoId + MANUAL;
//		ManualVideoBody manualbody = new ManualVideoBody();
//		Segments segments = new Segments();
//		segments.setOp(null);
//		segments.setOffsetBegin(null);
//		segments.setOffsetEnd(null);
//		segments.setLevel(null);
//		segments.setManualLevel(null);
//		Ops ops = new Ops();
//		Op op = new Op();
//		op.setLevel(null);
//		ops.setOp(op);
//		manualbody.setLevel(null);
//		manualbody.setOps(ops);
//		manualbody.setSegments(segments);
//		String bodystr = JSON.toJSONString(manualbody);
//		String leveljson = "" ;
//		try {
//			String Data = "<POST> + ' ' + <"
//					+ uri
//					+ "> + '\nHost: ' + <atlab.ai> + '\nContent-Type: ' + <application/json> + '\n\n'+ <"
//					+ bodystr + ">";
//			String secretKey = "hC2pI3urQ8-EaE283lhfmwTAxvpjBHNcYgWG8ama";
//			String QiniuToken = service.getSignature(Data, secretKey);
//			Map<String, String> params = new HashMap<>();
//			params.put("Authentication", QiniuToken);
//			String json = httpClientTemplate.doPost(uri, manualbody, params);
//			Level levels = parseObject(json, Level.class);
////			level = levels.getLevel();
//			leveljson = JSON.toJSONString(levels);
//		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return leveljson;
//	}
//
//	/**
//	 * 获取视频信息
//	 *
//	 * @return(视频信息)
//	 */
//	public String getVideo(String videoId) {
//		// 拼接https地址
//		String uri = V1_VIDEO + "/" + videoId;
//		try {
//			String Data = "<GET> + ' ' + <"
//					+ uri
//					+ "> + '\nHost: ' + <atlab.ai> + '\nContent-Type: ' + <application/json> + '\n\n'";
//			String secretKey = "hC2pI3urQ8-EaE283lhfmwTAxvpjBHNcYgWG8ama";
//			String QiniuToken = service.getSignature(Data, secretKey);
//			Map<String, String> param = new HashMap<>();
//			param.put("Authentication", QiniuToken);
//			// TODO
//			// return JSON.toJSONString(param);
//			// String json = httpClientTemplate.doPost(uri,param);
//			// VideoManager video = parseObject(json, VideoManager.class);
//		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
//
//	/**
//	 * 获取视频列表
//	 *
//	 * @return(视频列表信息)
//	 */
//	public String getVideoList(int pageNum, int pageSize, int reviewStage,
//			List<Integer> reviewLevel) {
//		// 拼接https地址
//		String uri = VIDEOS;
//		Map<String, Object> params = new HashMap<>();
//		params.put("pageNum", pageNum);
//		params.put("pageSize", pageSize);
//		params.put("reviewStage", reviewStage);
//		params.put("reviewLevel", reviewLevel);
//		 String level = "" ;
//		 for(int i=0; i<reviewLevel.size();i++){
//		 String Levels="&reviewLevel="+reviewLevel.get(i);
//		 level += Levels;
//		 }
//		String RawQuery = "pageNum=" + pageNum + "&pageSize=" + pageSize
//				+ "&reviewStage=" + reviewStage + level;
//		try {
//			String Data = "<GET> + ' ' + <"
//					+ uri
//					+ ">  + ?<"
//					+ RawQuery
//					+ "> + \nHost: ' + <atlab.ai> + '\nContent-Type: ' + <application/json> + '\n\n'";
//			String secretKey = "hC2pI3urQ8-EaE283lhfmwTAxvpjBHNcYgWG8ama";
//			String QiniuToken = service.getSignature(Data, secretKey);
//			// Map<String, String> param = new HashMap<>();
//			// param.put("Authentication", QiniuToken);
//			// String json =
//			// httpClientTemplate.doPost(uri,RawQuery,ContentType,param);
//			params.put("Authentication", QiniuToken);
//			// String json=JSON.toJSONString(params);
//		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
//
//}