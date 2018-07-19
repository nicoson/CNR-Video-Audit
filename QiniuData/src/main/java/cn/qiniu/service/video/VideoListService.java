package cn.qiniu.service.video;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.SysRole;
import cn.qiniu.entity.SysUser;
import cn.qiniu.entity.SysUserRole;
import cn.qiniu.entity.SysUserRoleExample;
import cn.qiniu.entity.VideoCountByLevel;
import cn.qiniu.form.video.VideoListForm;
import cn.qiniu.mapper.ReviewVideoManagerMapper;
import cn.qiniu.mapper.SysRoleMapper;
import cn.qiniu.mapper.SysUserRoleMapper;
import cn.qiniu.mapper.VideoCountByLevelMapper;
import cn.qiniu.service.UtilService;
import cn.qiniu.util.DictUtil;

@Service
public class VideoListService {

	@Autowired
	private UtilService utilService;
	
	@Autowired
	private VideoCountByLevelMapper videoCountByLevelMapper;
	
	@Autowired
	private ReviewVideoManagerMapper reviewVideoManagerMapper;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	/**
	 * 删除视频  
	 * 
	 * @return
	 */
	public int videoDelete(ReviewVideoManager record) {
		//设置删除标志位
		record.setDelFlg(DictUtil.DEL_FLG.DEL_FLG_1);
		return reviewVideoManagerMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 封禁视频
	 * 
	 * @return
	 */
	public Object videoClosure(VideoListForm form) {
		// TODO 封禁功能
		return null;
	}
	
	/**
	 * 查询危险等级对应的审核视频数量
	 * @return
	 */
	public Object getCountByDangerLevel(VideoListForm form) {
		VideoCountByLevel videoCountByLevel = videoCountByLevelMapper.selectCountByLevel(form.getSearch());
		//修改数据
		return videoCountByLevel;
	}
	
	/**
	 * 获取用户角色
	 * @param userId
	 * @return
	 */
	public String getRoleType(SysUser user){
		SysUserRoleExample userRoleExample = new SysUserRoleExample();
		userRoleExample.createCriteria()
		.andDelFlgEqualTo(DictUtil.DEL_FLG.DEL_FLG_0)
		.andUserIdEqualTo(user.getUserId());
		List<SysUserRole> userRoleList = sysUserRoleMapper.selectByExample(userRoleExample);
		if(userRoleList != null && userRoleList.size()>0){
			String roleId = userRoleList.get(0).getRoleId();
			//根据角色id查询角色，返回角色类型
			SysRole role = sysRoleMapper.selectByPrimaryKey(roleId);
			return role.getRoleNm();
		}
		return "";
	}
}
