package cn.qiniu.mapper;

import java.util.List;

import cn.qiniu.entity.ReviewVideoManager;

public interface CheckInfoListMapper {
	
    /**
     * 获取待审核视频
     * @return
     */
	List<ReviewVideoManager> getCheckInfoList();
}
