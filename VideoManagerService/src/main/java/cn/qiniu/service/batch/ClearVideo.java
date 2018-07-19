package cn.qiniu.service.batch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.config.Global;
import cn.qiniu.delegator.ClearVideoDelegator;
import cn.qiniu.entity.ReviewTaskStateExample;
import cn.qiniu.entity.ReviewVideoInformationExample;
import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerCutsExample;
import cn.qiniu.entity.ReviewVideoManagerExample;
import cn.qiniu.entity.ReviewVideoManagerSegmentsExample;
import cn.qiniu.entity.ReviewVideoOpsExample;
import cn.qiniu.mapper.ReviewTaskStateMapper;
import cn.qiniu.mapper.ReviewVideoInformationMapper;
import cn.qiniu.mapper.ReviewVideoManagerCutsMapper;
import cn.qiniu.mapper.ReviewVideoManagerMapper;
import cn.qiniu.mapper.ReviewVideoManagerSegmentsMapper;
import cn.qiniu.mapper.ReviewVideoOpsMapper;

@Service
public class ClearVideo {
	@Autowired
	private ClearVideoDelegator clearVideoDelegator;
	@Autowired
	private Integer videoClearDays;
	@Autowired
	private ReviewVideoManagerMapper reviewVideoManagerMapper;
	@Autowired
	private ReviewTaskStateMapper reviewTaskStateMapper;
//	@Autowired
//	private ReviewVideoManagerTimeMapper reviewVideoManagerTimeMapper;
	@Autowired
	private ReviewVideoInformationMapper reviewVideoInformationMapper;
	@Autowired
	private ReviewVideoOpsMapper reviewVideoOpsMapper;
	@Autowired
	private ReviewVideoManagerCutsMapper reviewVideoManagerCutsMapper;
	@Autowired
	private ReviewVideoManagerSegmentsMapper reviewVideoManagerSegmentsMapper;

	/**
	 * 物理清除已删除数据
	 */
	/**
	 * 
	 */
	public void execute() {
		Date date = new Date();
		// TODO 取出N天review_video_manager表中del_flg为1的数据

		// 查询数据
		ReviewVideoManagerExample videoExample = new ReviewVideoManagerExample();
		videoExample.createCriteria()
				.andDelFlgEqualTo(Global.DEL_FLG.DEL_FLG_1);
		// videoExample.setOrderByClause(null);
		List<ReviewVideoManager> list = reviewVideoManagerMapper
				.selectByExample(videoExample);

		if (list != null && list.size() > 0) {
			// 遍历满足条件的记录
			for (ReviewVideoManager record : list) {
				// 当前时间减去更新时间 相差多少秒
				if (date.getTime() - record.getUpdatedAt().getTime() > 86400000) {
					try {

						// 根据video id删除review_video_manager_segments的数据
						deleteManagerSegments(record.getId());

						// 根据video id删除review_video_manager_cuts的数据
						deleteManagerCuts(record.getId());

						// 根据video id删除review_video_ops的数据
						deleteVideoOps(record.getId());

						// 根据video id删除review_video_information的数据
						deleteVideoInformation(record.getId());

//						// 根据video id删除review_video_manager_time的数据
//						deleteVideoManagerTime(record.getId());

						// 根据job id删除review_task_state的数据
						deleteTaskState(record.getJobId());

						// 根据video id删除review_video_manager的数据
						deleteManager(record);

					} catch (Exception e) {
						// TODO: 记入Log后继续
					}
				}
			}
		}
	}

	private void deleteManager(ReviewVideoManager manager) {
		// 根据video id删除review_video_manager的数据
		reviewVideoManagerMapper.deleteByPrimaryKey(manager.getId());
		List<Object> list = new ArrayList<Object>();
		// 根据归档地址调用API删除云端视频
		String videoSourceName = manager.getSource().substring(
				manager.getSource().lastIndexOf("/") + 1);

		// 根据归档地址调用API删除云端水印视频
		String videoUrlName = manager.getVideoUri().substring(
				manager.getVideoUri().lastIndexOf("/") + 1);
		// 根据封面地址调用API删除云端封面
		String VideoCoverName = manager.getVideoCover().substring(
				manager.getVideoCover().lastIndexOf("/") + 1);

		list.add(videoSourceName);
		list.add(videoUrlName);
		list.add(VideoCoverName);
		clearVideoDelegator.ClearVideo(list);

	}

	private void deleteTaskState(String jobId) {
		// 删除审核任务状态表视频内容
		ReviewTaskStateExample example = new ReviewTaskStateExample();
		// TODO
		example.createCriteria().andJobIdEqualTo(jobId);
		reviewTaskStateMapper.deleteByExample(example);
	}

//	private void deleteVideoManagerTime(String videoId) {
//		// 删除视频时长管理表视频内容
//		ReviewVideoManagerTimeExample example = new ReviewVideoManagerTimeExample();
//		example.createCriteria().andIdEqualTo(videoId);
//		reviewVideoManagerTimeMapper.deleteByExample(example);
//	}

	private void deleteVideoInformation(String videoId) {
		// 删除审核视频任务信息表视频内容
		ReviewVideoInformationExample example = new ReviewVideoInformationExample();
		example.createCriteria().andVideoIdEqualTo(videoId);
		reviewVideoInformationMapper.deleteByExample(example);
	}

	private void deleteVideoOps(String videoId) {
		// 删除审核视频操作项目表视频内容
		ReviewVideoOpsExample example = new ReviewVideoOpsExample();
		example.createCriteria().andVideoIdEqualTo(videoId);
		reviewVideoOpsMapper.deleteByExample(example);
	}

	private void deleteManagerCuts(String videoId) {
		// 删除视频管理截帧表视频内容
		ReviewVideoManagerCutsExample example = new ReviewVideoManagerCutsExample();
		example.createCriteria().andVideoIdEqualTo(videoId);
		reviewVideoManagerCutsMapper.deleteByExample(example);
	}

	private void deleteManagerSegments(String videoId) {
		// 删除视频管理片段表视频内容
		ReviewVideoManagerSegmentsExample example = new ReviewVideoManagerSegmentsExample();
		example.createCriteria().andVideoIdEqualTo(videoId);
		reviewVideoManagerSegmentsMapper.deleteByExample(example);
	}

}
