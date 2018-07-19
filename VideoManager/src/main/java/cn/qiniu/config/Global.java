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
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	
	/**
	 * 消息
	 */
	public static class MESSAGE_CODE {
		// 不能为空
		public static final int EMPTY = 1001;
		// 必须是数值
		public static final int NOT_NUMERIC = 1002;
		
	}
    

}