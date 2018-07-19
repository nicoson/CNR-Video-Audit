package cn.qiniu.util;

public class DictUtil {
	
	/**
	 * 获取字典项lable值
	 * @param type    字典类型
	 * @param value   字典code
	 * @return
	 */
	public static String getDictLabel(String type, String value, String defaultValue){
		//如果字典类型和字典code不为空，返回字典label
		if(!StringUtil.isNullOrEmpty(type) && !StringUtil.isNullOrEmpty(value)) {
			return EhcacheUtil.getDictLabels(type, value);
		}
		return defaultValue;
	}
	
	/**
	 * 获取字典项value值
	 * @param type    字典类型
	 * @param label   字典label
	 * @return
	 */
	public static String getDictValue(String type, String label, String defaultValue){
		//如果字典类型和字典label不为空，返回字典value
		if(!StringUtil.isNullOrEmpty(type) && !StringUtil.isNullOrEmpty(label)) {
			return EhcacheUtil.getDictValue(type, label);
		}
		return defaultValue;
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
	 * 直播停止标识
	 */
	public static final String TYPE_LIVE_END_FLG = "live_end_flg";
	public static final class LIVE_END_FLG {
        /**直播中**/
		public static final String LIVE_END_FLG_0 = "0";
        /**直播停止**/
		public static final String LIVE_END_FLG_1 = "1";
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
}
