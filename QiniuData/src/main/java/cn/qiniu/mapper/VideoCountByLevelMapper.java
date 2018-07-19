package cn.qiniu.mapper;

import cn.qiniu.entity.VideoCountByLevel;
import cn.qiniu.form.video.VideoListSearch;

public interface VideoCountByLevelMapper {
	
    /**
     * 查询危险等级对应审核视频数量
     * @return
     */
	VideoCountByLevel selectCountByLevel(VideoListSearch search);
}
