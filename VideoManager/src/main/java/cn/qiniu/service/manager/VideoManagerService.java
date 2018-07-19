package cn.qiniu.service.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.config.MessageConstants;
import cn.qiniu.delegator.QiniuApiDelegator;
import cn.qiniu.delegator.manager.VideoManagerDelegator;
import cn.qiniu.exceptions.VideoManagerException;
import cn.qiniu.util.common.StringUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 视频管理服务
 * 
 * @author Ling QiongFang
 * @version 2017-09-26
 */
@Service
public class VideoManagerService {

	// 视频管理服务Delegator
	@Autowired
	private VideoManagerDelegator videoManagerDelegator;
	
	@Autowired
	private QiniuApiDelegator qiniuApiDelegator;

	/**
	 * 获取视频列表(分页)
	 * 
	 * @param pageNum
	 *            分页页数
	 * @param pageSize
	 *            分页大小
	 * @param reviewStage
	 *            审核阶段(0 ：未审核； 1 ：初步审核（机审结果）； 2 ：审核结束)
	 * @param reviewLevel
	 *            审核的结果等级(0 ：无； 1 ：低； 2 ：中； 3 ：高)
	 * @return 视频管理列表
	 */
	public String getVideoList(String pageNum, String pageSize, String[] reviewLevel,String reviewStage) {
		// 校验
		checkVideoListParam(pageNum, pageSize, reviewLevel);
		return videoManagerDelegator.getVideoList(pageNum, pageSize, reviewLevel,reviewStage);
	}
	
	public String getMaxPageSize(String pageNum, String pageSize, String[] reviewLevel){
		return videoManagerDelegator.getMaxPageSize(pageNum, pageSize, reviewLevel);
	}

	/**
	 * 获取视频信息
	 * 
	 * @param videoId
	 *            视频ID
	 * @return 视频信息
	 */
	public String getVideoInfo(String videoId) {
		// 校验
		checkVideoInfoParam(videoId);
		return videoManagerDelegator.getVideoInfo(videoId);
	}

	/**
	 * 校验获取视频列表参数
	 * 
	 * @param pageNum
	 *            分页页数
	 * @param pageSize
	 *            分页大小
	 * @param reviewStage
	 *            审核阶段(0 ：未审核； 1 ：初步审核（机审结果）； 2 ：审核结束)
	 * @param reviewLevels
	 *            审核的结果等级(0 ：无； 1 ：低； 2 ：中； 3 ：高)
	 */
	private void checkVideoListParam(String pageNum, String pageSize, String[] reviewLevels) {
		// 分页页数（非空校验/数值校验）
		if (StringUtil.isNullOrEmpty(pageNum))
			throw new VideoManagerException(MessageConstants.EMPTY_CODE,
					MessageConstants.EMPTY_MESSAGE, "pageNum");
		if (StringUtil.isNotNumeric(pageNum))
			throw new VideoManagerException(MessageConstants.NOT_NUMERIC_CODE,
					MessageConstants.NOT_NUMERIC_MESSAGE, "pageNum");

		// 分页大小（非空校验/数值校验）
		if (StringUtil.isNullOrEmpty(pageSize))
			throw new VideoManagerException(MessageConstants.EMPTY_CODE,
					MessageConstants.EMPTY_MESSAGE, "pageSize");
		if (StringUtil.isNotNumeric(pageSize))
			throw new VideoManagerException(MessageConstants.NOT_NUMERIC_CODE,
					MessageConstants.NOT_NUMERIC_MESSAGE, "pageSize");

		// 审核的结果等级（非空校验）
		if (reviewLevels == null)
			throw new VideoManagerException(MessageConstants.EMPTY_CODE,
					MessageConstants.EMPTY_MESSAGE, "reviewLevel");
		if (reviewLevels.length == 0)
			throw new VideoManagerException(MessageConstants.EMPTY_CODE,
					MessageConstants.EMPTY_MESSAGE, "reviewLevel");
		for (String reviewLevel : reviewLevels) {
			if (StringUtil.isNotNumeric(reviewLevel))
				throw new VideoManagerException(
						MessageConstants.NOT_NUMERIC_CODE,
						MessageConstants.NOT_NUMERIC_MESSAGE, "reviewLevel");
		}
	}
	/**
	 * 校验获取视频信息参数
	 * 
	 * @param videoId
	 *            视频ID
	 */
	private void checkVideoInfoParam(String videoId) {
		// 视频ID（非空校验）
		if (StringUtil.isNullOrEmpty(videoId))
			throw new VideoManagerException(MessageConstants.EMPTY_CODE,
					MessageConstants.EMPTY_MESSAGE, "videoId");
	}

	public String deleteBucketFiles(JSONObject jsonObject) {
		List<Object> list = jsonObject.getJSONArray("keys");
//		JSONArray array = jsonObject.getJSONArray("keys");
//		List<String> keys = new ArrayList<String>();
//		Iterator<Object> it = array.iterator();
//		while (it.hasNext()) {
//			JSONObject ob = (JSONObject) it.next();
//			if(ob.getString("key")!=null){
//                keys.add(ob.getString("key"));
//            }
////			array.iterator().next();
//		}
		
		String[] keyList = (String[])list.toArray(new String[list.size()]);
		qiniuApiDelegator.deleteFiles(keyList);
		return null;
	}
}