package cn.qiniu.config;


/**
 * 常量类
 * 
 * @author lvweijun
 * @version V1.00 2016-11-03
 */
public interface Global
{
	/**
	 * 审核科目
	 */
	public static final String REVIEW_OP_PULP = "pulp_beta";
	public static final String REVIEW_OP_TERROR = "terror";
	public static final String REVIEW_OP_POLITICIAN = "face_group_search";
	
	/**
	 * 字符0
	 */
	public static final String STRING_ZERO = "0";
	
    /**
     * 缓存名称
     */
    public static final String CACHE_NAME = "qiniuCache";
    
	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	
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
	 * 删除标识
	 */
	public static final class DEL_FLG {
		public static final String DEL_FLG_0 = "0";
		public static final String DEL_FLG_1 = "1";
	}
	
	/**
	 * 时间后缀
	 */
	public static class DATE_TIME {
		public final static String START = " 00:00:00";
		public final static String END = " 23:59:59";
	}
	
	/**
	 * 返回Map中增加的字典项目名称
	 */
	public static final String DIC_ITEM_VALUE = "DicItemVal";
	
	/**
	 * 字典项是否启用标志
	 */
	public static final String DIC_ENABLE_Y = "Y";
	
	/**
	 * 临时文件存放路径
	 */
	public static final class TEMP_FILE_PATH {
		//默认文件保存路径
		public static final String DEFAULT = "temp";
	}
}