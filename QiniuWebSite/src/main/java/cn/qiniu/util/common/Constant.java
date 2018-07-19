package cn.qiniu.util.common;

/**
 * 常量类
 * 
 * @author lvweijun
 * @version V1.00 2016-10-18
 */
public interface Constant {
	/**
	 * 审核科目
	 */
	public static final String REVIEW_OP_PULP = "pulp";
	public static final String REVIEW_OP_TERROR = "terror";
	public static final String REVIEW_OP_POLITICIAN = "face_group_search";
	
	/**
	 * 涉黄label
	 */
	public static final String PULP_LABEL_0 = "0";//色情
	public static final String PULP_LABEL_1 = "1";//性感
	public static final String PULP_LABEL_2 = "2";//正常
	
	/**
	 * 涉政label
	 */
	public static final String PILITICIAN_LABEL_0 = "0";
	public static final String PILITICIAN_LABEL_1 = "1";
	
	/**
	 * 涉暴恐label
	 */
	public static final String TERROR_LABEL_0 = "0";//正常
	public static final String TERROR_LABEL_1 = "1";//暴恐
	
	/**
	 * 视频类型
	 */
	public static final String TYPE_VIDEO_TYPE = "video_type";
	public static final class VIDEO_TYPE {
        /**点播**/
		public static final String VIDEO_TYPE_1 = "1";
        /**直播**/
		public static final String VIDEO_TYPE_2 = "2";
	}
	
	/**
	 * 视频类型label
	 */
	public static final class VIDEO_TYPE_LABEL {
        /**点播**/
		public static final String VIDEO_TYPE_LABEL_1 = "点播";
        /**直播**/
		public static final String VIDEO_TYPE_LABEL_2 = "直播";
	}
	
	/**
	 * 审核任务类型
	 */
	public static final String TYPE_REVIEW_TYPE = "review_type";
	public static final class REVIEW_TYPE {
        /**机审**/
		public static final String REVIEW_TYPE_1 = "1";
        /**人审**/
		public static final String REVIEW_TYPE_2 = "2";
	}
	
	/**
	 * 审核结果类型
	 */
	public static final String TYPE_CHECK_RESULT_TYPE = "check_result_type";
	public static final class CHECK_RESULT_TYPE {
        /**涉政事件**/
		public static final String CHECK_RESULT_TYPE_0 = "0";
        /**涉政人物**/
		public static final String CHECK_RESULT_TYPE_1 = "1";
        /**暴恐**/
		public static final String CHECK_RESULT_TYPE_2 = "2";
        /**黄色**/
		public static final String CHECK_RESULT_TYPE_3 = "3";
        /**性感**/
		public static final String CHECK_RESULT_TYPE_4 = "4";
	}

	/**
	 * 删除标识
	 */
	public static final String TYPE_DEL_FLG = "del_flg";
	public static final class DEL_FLG {
        /**未删除**/
		public static final String DEL_FLG_0 = "0";
        /**删除**/
		public static final String DEL_FLG_1 = "1";
	}

	/**
	 * 机审/人审标识
	 */
	public static final String TYPE_MACHINE_ARTIFICIAL = "machine_artificial";
	public static final class MACHINE_ARTIFICIAL {
        /**机审**/
		public static final String MACHINE_ARTIFICIAL_0 = "0";
        /**人审**/
		public static final String MACHINE_ARTIFICIAL_1 = "1";
	}

	/**
	 * 危险等级
	 */
	public static final String TYPE_REVIEW_LEVEL = "review_level";
	public static final class REVIEW_LEVEL {
        /**无**/
		public static final String REVIEW_LEVEL_0 = "0";
        /**低**/
		public static final String REVIEW_LEVEL_1 = "1";
        /**中**/
		public static final String REVIEW_LEVEL_2 = "2";
        /**高**/
		public static final String REVIEW_LEVEL_3 = "3";
        /**未知**/
		public static final String REVIEW_LEVEL_99 = "99";
	}
	
	/**
	 * 危险等级label
	 */
	public static final class REVIEW_LEVEL_LABEL {
        /**无**/
		public static final String REVIEW_LEVEL_LABEL_0 = "无";
        /**低**/
		public static final String REVIEW_LEVEL_LABEL_1 = "低";
        /**中**/
		public static final String REVIEW_LEVEL_LABEL_2 = "中";
        /**高**/
		public static final String REVIEW_LEVEL_LABEL_3 = "高";
        /**未知**/
		public static final String REVIEW_LEVEL_LABEL_99 = "未知";
	}

	/**
	 * 设置危险等级
	 */
	public static final String TYPE_REVIEW_LEVEL_SETTING = "review_level_setting";
	public static final class REVIEW_LEVEL_SETTING {
        /**无**/
		public static final String REVIEW_LEVEL_SETTING_0 = "0";
        /**低**/
		public static final String REVIEW_LEVEL_SETTING_1 = "1";
        /**中**/
		public static final String REVIEW_LEVEL_SETTING_2 = "2";
        /**高**/
		public static final String REVIEW_LEVEL_SETTING_3 = "3";
	}

	/**
	 * 审核阶段
	 */
	public static final String TYPE_REVIEW_STAGE = "review_stage";
	public static final class REVIEW_STAGE {
        /**未审核**/
		public static final String REVIEW_STAGE_0 = "0";
        /**初步审核(机审结果)**/
		public static final String REVIEW_STAGE_1 = "1";
        /**审核结束**/
		public static final String REVIEW_STAGE_2 = "2";
	}

	/**
	 * 审核状态
	 */
	public static final String TYPE_REVIEW_STATE = "review_state";
	public static final class REVIEW_STATE {
        /**未开始**/
		public static final String REVIEW_STATE_0 = "0";
        /**处理中**/
		public static final String REVIEW_STATE_1 = "1";
        /**通过**/
		public static final String REVIEW_STATE_2 = "2";
        /**不通过**/
		public static final String REVIEW_STATE_3 = "3";
		/**处理失败**/
		public static final String REVIEW_STATE_4 = "4";
	}
	/**
	 * 审核状态label
	 */
	public static final class REVIEW_STATE_LABEL {
        /**未开始**/
		public static final String REVIEW_STATE_0 = "未开始";
        /**处理中**/
		public static final String REVIEW_STATE_1 = "处理中";
        /**通过**/
		public static final String REVIEW_STATE_2 = "通过";
        /**不通过**/
		public static final String REVIEW_STATE_3 = "不通过";
		/**处理失败**/
		public static final String REVIEW_STATE_4 = "处理失败";
	}

	/**
	 * 是/否
	 */
	public static final String TYPE_YES_NO = "yes_no";
	public static final class YES_NO {
        /**否**/
		public static final String YES_NO_N = "N";
        /**是**/
		public static final String YES_NO_Y = "Y";
	}
	
	/**
	 * 字符0
	 */
	public static final String STRING_ZERO = "0";

	/**
	 * 字符小数点
	 */
	public static final String STRING_DOT = ".";
	
	/**
	 * 日期格式
	 */
	public static class DATE_FORMAT {
		public static final String PATTERN1 = "yyyy-MM-dd";
		public static final String PATTERN2 = "yyyy-MM-dd HH:mm:ss";
		public static final String PATTERN3 = "yyyy年MM月dd日";
		public static final String PATTERN4 = "yyyy-MM";
		public static final String PATTERN5 = "yyyyMMdd";
	}
	
	/**
	 * 管理员ID
	 */
	public static final String STAFF_ADMIN_ID = "1";
	
	/**
	 * 文件上传路径
	 */
	public static final class UPLOAD_FILE_PATH {
		//默认文件保存路径
		public static final String DEFAULT = "uploadFiles/";
		//图片上传路径
		public static final String PIC = "uploadFiles/uploadImgs/";
		//协议文件上传路径
		public static final String PROTPCOL = "uploadFiles/uploadProtocol/";
	}
	
	/**
	 * Excel模板路径
	 */
	public static final class TEMPLET_FILE_PATH {
		//Excel模板路径
		public static final String PATH = "uploadFiles/templet/";
	}
	
	/**
	 * Excel模板名称
	 */
	public static final class TEMPLET_FILE_NM {
		//运营商线路Excel模板路径
		public static final String LINE = "Line.xls";
		//分机号管理Excel模板路径
		public static final String NUMBER = "Number.xls";
	}
	
	/**
	 * 有效区间30分钟
	 */
	public static final int VALID_MINUTE = 30;
	
	/**
	 * 超时时间
	 */
	public static final int TIME_OUT =30000;
}