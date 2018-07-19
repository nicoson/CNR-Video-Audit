package cn.qiniu.entity.videoReviewToQiniu;

import java.io.Serializable;

/**
 * 审核视频提交识别服务实体类
 * @author ch
 *
 */
public class QiniuReviewOther implements Serializable{
	private static final long serialVersionUID = 3559533002594201715L;
    
	private String group;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
}