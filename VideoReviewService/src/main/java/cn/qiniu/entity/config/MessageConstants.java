package cn.qiniu.entity.config;

public class MessageConstants {
    // 获取支持的数据源列表失败
	public static final int EMPTY_CODE = 1001;
	public static final String EMPTY_MESSAGE = "[%s]不能为空";

	public static final int NOT_NUMERIC_CODE = 1002;
	public static final String NOT_NUMERIC_MESSAGE = "[%s]必须是数值";

	public static final int NOT_FOUND_CODE = 1003;
	public static final String NOT_FOUND_MESSAGE = "%s不存在";

	public static final int EXIST_CODE = 1004;
	public static final String EXIST_MESSAGE = "%s已存在";

	public static final int IDENTIF_SEXY_FAILED_CODE = 2001;
	public static final String IDENTIF_SEXY_FAILED_MESSAGE = "图片鉴黄发生错误：%s";

}
