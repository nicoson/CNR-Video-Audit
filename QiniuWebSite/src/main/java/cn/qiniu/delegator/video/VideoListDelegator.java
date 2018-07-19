package cn.qiniu.delegator.video;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.delegator.CommonDelegator;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.SysUser;
import cn.qiniu.entity.VideoCountByLevel;
import cn.qiniu.form.video.VideoListForm;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

import com.google.common.collect.Maps;

@Component
public class VideoListDelegator {
	private static final String VIDEO_DELETE = "/videoDelete";
	private static final String VIDEO_CLOSURE = "/videoClosure";
	private static final String CHECK_VIDEO_COUNT_BY_LEVEL = "/getCountByDangerLevel";
	private static final String GET_ROLE_TYPE = "/getRoleType";
	
	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	@Autowired
	private String qiniuDataManagerUrl;
	
	@Autowired
	private String videoManagerUri;
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	@Autowired
	private String qiniuDataUrl;
	
	/**
     * 拼接https地址
     *
     * @return
     */
	private String getUrl(String url) {
		return qiniuDataManagerUrl + url;
	}
	
	/**
     * 拼接https地址
     *
     * @return
     */
	private String getQiniuData(String url) {
		return qiniuDataUrl + url;
	}
	
//    /**
//	 *	视频列表(分页)
//	 */
//    public String tbPageList(String pageNum,String pageSize,String reviewStage,List<String> reviewLevelList) {
//    	
//    	String response = "";
//		Map<String,String> params = Maps.newHashMap();
//		//设置参数:页码
//		params.put("pageNum", pageNum);
//		//设置参数:记录条数
//		params.put("pageSize", pageSize);
//		//设置参数:审核阶段
//		params.put("reviewStage", reviewStage);
//		for (String reviewLevel : reviewLevelList)
//		{
//			params.put("reviewLevel", reviewLevel);
//		}
//				
//		try {
//			//取得视频信息
//			//response = httpClientTemplate.doGet(getUrl(VIDEO_PAGELIST), params);
//			response = httpClientTemplate.doGet(getManagerUrl(VIDEO_PAGELIST), params);
//		} catch (Exception e) {
//			return "";
//		}
//		return response;
//    }
    
    /**
	 *	删除数据
	 */
    public int videoDelete(ReviewVideoManager record) {
    	int result = 0;
		try {
			String response = httpClientTemplate.doPost(getQiniuData(VIDEO_DELETE), record);
			result = Integer.parseInt(response);
		} catch (Exception e) {
			throw e;
		}
		return result;
    }
    
    /**
	 *	封禁视频
	 */
    public int videoClosure(VideoListForm form) {
    	int result = 0;
		Map<String,Object> params = Maps.newHashMap();
		//设置参数:条件
		params.put("search", form.getSearch());
		//设置参数:记录
		params.put("record", form.getRecord());
		try {
			String response = httpClientTemplate.doPost(getUrl(VIDEO_CLOSURE), params);
			result = Integer.parseInt(response);
		} catch (Exception e) {
			throw e;
		}
		return result;
    }
    
    /**
	 *	查询危险等级对应的审核视频数量
	 */
    public VideoCountByLevel getCountByDangerLevel(VideoListForm form) {
    	VideoCountByLevel result = new VideoCountByLevel();
		Map<String,Object> params = Maps.newHashMap();
		params.put("search", form.getSearch());
		try {
			String response = httpClientTemplate.doPost(getQiniuData(CHECK_VIDEO_COUNT_BY_LEVEL),params);
			result = commonDelegator.parseObject(response,VideoCountByLevel.class);
		} catch (Exception e) {
			throw e;
		}
		return result;
    }
    
    /**
     * 获取用户角色
     * @param userId
     * @return
     */
    public String getRoleType(SysUser user){
    	return httpClientTemplate.doPost(getQiniuData(GET_ROLE_TYPE), user);
    }
}
