package cn.qiniu.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理的共通方法
 */
public class DateUtil {
	/**
	 * statement:获取当天日期时间
	 * 
	 * @param pattern
	 *            日期格式
	 * @return 当天日期时间
	 */
	public static String getNow(String pattern) {
		return dateToString(new Date(), pattern);
	}

	/**
	 * 日期转为字符串,默认转换后的字符串格式为yyyy-MM-dd
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            转换格式
	 * @return 转换后的字符串
	 */
	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		if (StringUtil.isNullOrWhiteSpace(pattern))
			pattern = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 字符串转为日期,默认字符串格式为yyyy-MM-dd
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param pattern
	 *            转换格式
	 * @return 转换后的日期或null(转换失败)
	 */
	public static Date stringToDate(String dateStr, String pattern) {
		if (StringUtil.isNullOrWhiteSpace(dateStr)) {
			return null;
		}
		if (StringUtil.isNullOrWhiteSpace(pattern))
			pattern = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获得前一天的日期
	 * 
	 * @return 前一天的日期
	 */
	public static Date getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * 根据当天日期，取得指定天数之前（之后）的日期
	 * 
	 * @param diff
	 *            距离当天的天数（正数为当天之后，负数为当天之前）
	 * @return 指定的日期
	 */
	public static Date getSpecifiedDate(int diff) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, diff);
		return cal.getTime();
	}

	/**
	 * 根据指定日期，取得指定天数之前（之后）的日期
	 * 
	 * @param diff
	 *            距离指定日期的天数（正数为指定日期之后，负数为指定日期之前）
	 * @return 指定的日期
	 */
	public static Date getSpecifiedDate(Date date, int diff) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, diff);
		return cal.getTime();
	}

	/**
	 * 获得两个日期之间的天数差
	 * 
	 * @param from
	 *            开始日期
	 * @param to
	 *            结束日期
	 * @return 相关天数，0：相同 >0：to比from晚 <0：to比from早
	 */
	public static int getDateDiff(Date from, Date to) {
		long fromLong = from.getTime();
		long toLong = to.getTime();
		int diff = (int) (toLong - fromLong) / (24 * 60 * 60 * 1000);
		return diff;
	}

	public static Date addDate(Date d, long day) throws ParseException {

		long time = d.getTime();
		day = day * 24 * 60 * 60 * 1000;
		time += day;
		return new Date(time);

	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	
	/**
	 * 取得开始日期(YYYY-MM)
	 * @param dateStr
	 * @return
	 */
	public static String getForm(String dateStr){
		if (StringUtil.isNullOrWhiteSpace(dateStr)){
			return null;
		}else{
			return dateStr+"-01";
		}
	}
	
	/**
	 * 取得结束日期(YYYY-MM)
	 * @param dateStr
	 * @return
	 */
	public static String getTo(String dateStr){
		if (StringUtil.isNullOrWhiteSpace(dateStr)){
			return null;
		}else{
			String newStr= dateStr+"-01";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(format.parse(newStr));
				//增加一个月
				cal.add(Calendar.MONTH, 1);
				//减少一天
				cal.add(Calendar.DATE, -1);
				return dateToString(cal.getTime(),"yyyy-MM-dd");
			} catch (ParseException e) {
				return null;
			}
		} 
	}
	
    /**
     * 把毫秒转化成日期
     *
     * @param pattern(日期格式，例如：MM/ dd/yyyy HH:mm:ss)
     * @param millSec(毫秒数)
     * @return
     */
    public static String longToDate(Long millSec, String pattern) {
        if (millSec == null) {
            return null;
        }
        if (StringUtil.isNullOrWhiteSpace(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date(millSec);
        return format.format(date);
    }
}
