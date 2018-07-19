package cn.qiniu.util.common;

/**
 * 常量类
 * 
 * @author lvweijun
 * @version V1.00 2016-10-18
 */
public interface Constant {
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
	 * 资费类型
	 */
	public static final class CHARGES_TYPE {
		//电话
		public static final String CALL = "call";
		//短信
		public static final String SMS = "sms";
		//预付费
		public static final String LICENCE = "licence";
	}	
	
	/**
	 * 资源子表名称
	 */
	public static final class CHILD_TABLE_NAME {
		//电话资费表
		public static final String OP_CHARGES_CALL = "op_charges_call";
		//短信资费表
		public static final String OP_CHARGES_SMS = "op_charges_sms";
		//预付费资费表
		public static final String OP_CHARGES_PRODUCT = "op_charges_product";
	}	
	
	/**
	 * 广告状态
	 */
	public static final class STATUS_FLG {
		//启用
		public static final String STATUS_FLG_START = "0";
		//未启用
		public static final String STATUS_FLG_STOP = "1";
	}
	
	/**
	 * 字典项验证标识
	 */
	public static final class DIC_EXIST_CHECK {
		public static final String DIC_NAME = "1";
		public static final String DIC_VALUE = "2";
	}
	
	/**
	 * 第三方标识
	 */
	public static final class THIRD_PARTY_FLG {
		//非第三方
		public static final String THIRD_PARTY_FLG_NO = "0";
		//第三方
		public static final String THIRD_PARTY_FLG_YES = "1";
	}
	
	/**
	 * 折扣标识
	 */
	public static final class DISCOUNT_FLG {
		//非折扣
		public static final String DISCOUNT_FLG_NO = "0";
		//折扣
		public static final String DISCOUNT_FLG_YES = "1";
	}
	
	/**
	 * 赠送标识
	 */
	public static final class PRESENT_FLG {
		//非赠送
		public static final String PRESENT_FLG_NO = "0";
		//赠送
		public static final String PRESENT_FLG_YES = "1";
	}
		
	/**
	 * 认证状态
	 */
	public static final class APPROVE_STATUS{
		//未认证
		public static final String APPROVE_STATUS_0 = "0";
		//已认证
		public static final String APPROVE_STATUS_1 = "1";
	}
	
	/**
	 * 认证状态
	 */
	public static final class CHECK_STATUS{
		//未申请
		public static final String CHECK_STATUS_0 = "0";
		//待审核
		public static final String CHECK_STATUS_1 = "1";
		//未通过
		public static final String CHECK_STATUS_2 = "2";
		//已通过
		public static final String CHECK_STATUS_3 = "3";
	}
	
	/**
	 * 管理员ID
	 */
	public static final String STAFF_ADMIN_ID = "1";
	
    /**
     * 员工权限模块
     */
    public static final String STAFF_PERMISSION_MODE = "staff";
	
    /**
     * 角色权限模块
     */
    public static final String ROLE_PERMISSION_MODE = "role";
    
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
	 * 组织图片类型
	 */
	public static final class ORGANIZE_PIC_TYPE {
		//营业执照
		public static final String LICENSE = "license";
		//图片2
		public static final String PIC2 = "pic2";
		//图片3
		public static final String PIC3 = "pic3";
	}
	
	/**
	 * 充值流水表 充值状态
	 */
	public static final class PAY_STATUS {
		//已支付
		public static final String PAY_STATUS_PAY = "0";
		//未支付
		public static final String PAY_STATUS_NOPAY = "1";
		//取消
		public static final String PAY_STATUS_CLOSE = "2";
	}
	
	/**
	 * 充值类型
	 */
	public static final class RECHARGE_TYPE {
		//客户充值
		public static final String RECHARGE_TYPE_CUS = "0";
		//运维充值
		public static final String RECHARGE_TYPE_OP = "1";
		//返现充值
		public static final String RECHARGE_TYPE_RETURN = "2";
	}
	
	/**
	 * 支付方式
	 */
	public static final class PAY_TYPE {
		/**支付宝**/
		public static final String PAY_TYPE_ALIPAY = "alipay";
		/**其他**/
		public static final String PAY_TYPE_OTHER = "other";
	}
	
	/**
	 * 公司固定充值账号
	 */
	public static final String MY_PAY_NO = "声通公司返现账号";
	
	/**
	 * 客户类别
	 */
	public static final class CUST_TYPE{
		/**组织**/
		public static final String CUST_TYPE_CO = "co";
		/**个人**/
		public static final String CUST_TYPE_PE = "pe";
		
	}
	
	/**
	 * 问题反馈问题状态
	 */
	public static final class PROBLEM_TYPE{
		/**公共池**/
		public static final String PROBLEM_PUBLIC = "0";
		/**个人**/
		public static final String PROBLEM_PRIVATE = "1";
		/**完成**/
		public static final String PROBLEM_COMPLETE = "2";
	}
	
	/**
	 * 发票状态
	 */
	public static final class INVOICE_STATUS{
		/**未开**/
		public static final String INVOICE_STATUS_NO = "0";
		/**申请**/
		public static final String INVOICE_STATUS_APPLY = "1";
		/**已开**/
		public static final String INVOICE_STATUS_YES = "2";
		/**已寄出**/
		public static final String INVOICE_STATUS_SEND = "3";
	}
	
	/**
	 * 业务场景
	 */
	public static final class BUSINESS_TYPE{
		/**用户注册**/
		public static final String BUSINESS_TYPE_REGISTER ="1";
		/**用户充值**/
		public static final String BUSINESS_TYPE_RECHARGE ="2";
		/**账户扣款**/
		public static final String BUSINESS_TYPE_CHARGE ="3";
		/**余额不足**/
		public static final String BUSINESS_TYPE_BALANCE ="4";
		/**个人密码重置**/
		public static final String BUSINESS_TYPE_PE_PASSWORD_RESET ="5";
		/**用户登入**/
		public static final String BUSINESS_TYPE_CO_ENTER ="6";
		/**组织密码重置**/
		public static final String BUSINESS_TYPE_CO_PASSWORD_RESET="7";
		/**修改管理员密码**/
		public static final String BUSINESS_TYPE_CO_CHANGE_PASSWORD ="8";
		/**修改手机号**/
		public static final String BUSINESS_TYPE_PE_CHANGE_PHONE ="9";
		/**邀请**/
		public static final String BUSINESS_TYPE_INVITE ="a";
	}
	
	/**
	 * 有效区间30分钟
	 */
	public static final int VALID_MINUTE = 30;
	
	/**
	 * 随机数位数
	 */
	public static final int RANDOM_SIZE = 4;
	
	/**
	 * 是否激活
	 */
	public static final class IS_ACTIVATION{
		/**未激活**/
		public static final String IS_ACTIVATION_NO ="0";
		/**已激活**/
		public static final String IS_ACTIVATION_OK ="1";
	}
	
	/**
	 * 超时时间
	 */
	public static final int TIME_OUT =30000;
	
	/**
	 * 获取验证码倒计时
	 */
	public static final int CHKCD_VALIDITY =60;
	
	/**
	 * 管理员级别
	 */
	public static final class ADMIN_LEVEL {
		/**管理员**/
		public static final String ADMIN_LEVEL_0 ="0";
		/**高级用户**/
		public static final String ADMIN_LEVEL_1 ="1";
	}
	
	/**
	 * 协议类型
	 */
	public static final class PROTOCOL_TYPE{
		/**充值协议**/
		public static final String PROTOCOL_TYPE_RECHARGE ="recharge";
		/**声通公约**/
		public static final String PROTOCOL_TYPE_CONVENTION ="convention";
		/**注册协议说明**/
		public static final String PROTOCOL_TYPE_SIGN_IN ="sign_in";
		/**帮助中心**/
		public static final String PROTOCOL_TYPE_HELP_CENTER ="help_center";
		/**声通UC支付条款**/
		public static final String PROTOCOL_TYPE_BUY ="buy";
	}
	
	/**
	 * 密保问题
	 */
	public static final class USER_SECURITY_QUESTION{
		/**密保问题1**/
		public static final String QUESTION_ONE ="question_one";
		/**密保问题2**/
		public static final String QUESTION_TWO ="question_two";
		/**密保问题3**/
		public static final String QUESTION_THREE = "question_three";
	}
	
	/**
	 * 证件类型
	 */
	public static final class DOC_TYPE{
		/**身份证**/
		public static final String DOC_TYPE_IDCARD ="1";
		/**护照**/
		public static final String DOC_TYPE_PASSPORT ="2";
		/**驾照**/
		public static final String DOC_TYPE_DRIVING_LICENSE = "3";
		/**台胞证**/
		public static final String DOC_TYPE_TAIWAN_IDCARD = "4";
		/**香港身份证**/
		public static final String DOC_TYPE_HANGKANG_IDCARD = "5";
		/**其他**/
		public static final String DOC_TYPE_OTHER = "6";
	}
}