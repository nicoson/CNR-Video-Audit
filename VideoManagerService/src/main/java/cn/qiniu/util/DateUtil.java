package cn.qiniu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.qiniu.config.Global;
import cn.qiniu.config.Global.DATE_FORMAT;

/**
 * 日期处理的共通方法
 */
public class DateUtil {
    /**
     * statement:获取当天日期时间
     *
     * @param pattern 日期格式
     * @return 当天日期时间
     */
    public static String getNow(String pattern) {
        return dateToString(new Date(), pattern);
    }

    /**
     * 日期转为字符串,默认转换后的字符串格式为yyyy-MM-dd
     *
     * @param date    日期
     * @param pattern 转换格式
     * @return 转换后的字符串
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        if (StringUtil.isNullOrWhiteSpace(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 字符串转为日期,默认字符串格式为yyyy-MM-dd
     *
     * @param dateStr 日期字符串
     * @param pattern 转换格式
     * @return 转换后的日期或null(转换失败)
     */
    public static Date stringToDate(String dateStr, String pattern) {
        if (StringUtil.isNullOrWhiteSpace(dateStr)) {
            return null;
        }
        if (StringUtil.isNullOrWhiteSpace(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            return null;
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
     * @param diff 距离当天的天数（正数为当天之后，负数为当天之前）
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
     * @param diff 距离指定日期的天数（正数为指定日期之后，负数为指定日期之前）
     * @return 指定的日期
     */
    public static Date getSpecifiedDate(Date date, int diff) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, diff);
        return cal.getTime();
    }

    /**
     * 根据指定日期，取得指定天数之前（之后）的日期（字符串）
     *
     * @param date yyyy-mm-dd
     * @param diff 距离指定日期的天数（正数为指定日期之后，负数为指定日期之前）
     * @return 指定的日期(yyyy-mm-dd)
     */
    public static String getSpecifiedDateString(String date, int diff) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(stringToDate(date, ""));
        cal.add(Calendar.DATE, diff);
        return dateToString(cal.getTime(), "");
    }

    /**
     * 获得两个日期之间的天数差
     *
     * @param from 开始日期
     * @param to   结束日期
     * @return 相关天数，0：相同		>0：to比from晚		<0：to比from早
     */
    public static int getDateDiff(Date from, Date to) {
        long fromLong = from.getTime();
        long toLong = to.getTime();
        long diffLong = (toLong - fromLong) / (24 * 60 * 60 * 1000);
        int diff = Integer.valueOf(String.valueOf(diffLong)) ;
        return diff;
    }
    
    /**
     * 根据当天日期，取得指定分钟之前（之后）的日期
     *
     * @param diff 距离当天的天数（正数为分钟之后，负数为分钟之前）
     * @return 指定的日期
     */
    public static Date getMinuteDate(int diff) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, diff);
        return cal.getTime();
    }
    
	/**
	 * 根据指定日期，取得指定月数之前（之后）的日期
	 * 
	 * @param diff
	 *            距离指定日期的月数之（正数为指定日期之后，负数为指定日期之前）
	 * @return 指定的日期
	 */
	public static Date getMonthDate(Date date, int diff) {
		SimpleDateFormat formatMonth = new SimpleDateFormat(DATE_FORMAT.PATTERN1);
		SimpleDateFormat formatAll = new SimpleDateFormat(DATE_FORMAT.PATTERN2);
        String  month =  formatMonth.format(date);
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(formatAll.parse(month + Global.DATE_TIME.END));
			//增加一个月
			cal.add(Calendar.MONTH, diff);
			return  cal.getTime();
		} catch (ParseException e) {
			return null;
		}
	}
}
