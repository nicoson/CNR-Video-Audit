package cn.qiniu.mapper;

import java.util.List;

import cn.qiniu.entity.ReviewVideoTask;

public interface ReviewVideoMapper {
	/*
	 * 获取审核视频任务列表
	 */
	List<ReviewVideoTask> selectReviewVideoTaskList();
	/**
	 * 获取审核视频结果
	 * @param jobId
	 * @return
	 */
	String selectReviewVideoId(String jobId);
	
}
