package cn.qiniu.service.video;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.delegator.video.UploadFileDelegator;
import cn.qiniu.entity.videoReview.VideoSubmit;
import cn.qiniu.entity.videoReview.VideoSubmitData;
import cn.qiniu.entity.videoReview.VideoSubmitDataAttribute;
import cn.qiniu.util.common.CommonUtil;

@Service
public class UploadFileService {
	@Autowired
	private UploadFileDelegator uploadFileDelegator;
	
	public String  getUploadToken(){
		String uploadTonken = uploadFileDelegator.getUploadToken();
		
		return  uploadTonken;
	}
	
	/**
	 * 提交 上传的视频
	 * @return
	 */
	public String  submitVideo(VideoSubmit record){
		
		VideoSubmitData data=	record.getData();
		VideoSubmitDataAttribute  attribute =data.getAttribute(); 
		attribute.setId(CommonUtil.getUUID());
		data.setAttribute(attribute);
		record.setData(data);
		String  res = uploadFileDelegator.submitVideo(record);
		return  res;
	}
}
