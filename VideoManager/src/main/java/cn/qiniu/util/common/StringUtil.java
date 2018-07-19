package cn.qiniu.util.common;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理的共通方法
 */
public class StringUtil {
	/**
	 * 将空字符串转为null
	 * 
	 * @param value
	 *            要转换的字符串
	 * @return 如果为空字符串，返回null；否则返回原字符串
	 */
	public static String emptyStringToNull(String value) {
		if (isNullOrEmpty(value)) {
			return null;
		}
		return value;
	}

	/**
	 * 将nulll转为空字符串
	 * 
	 * @param value
	 *            要转换的字符串
	 * @return 如果为null，返回空字符串；否则返回原字符串
	 */
	public static String nullToEmptyString(String value) {
		if (isNullOrEmpty(value)) {
			return "";
		}
		return value;
	}

	/**
	 * 验证字符串是否为null或全部由空格组成
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 验证结果，字符串为null或由空格组成，返回true，否则返回false
	 */
	public static boolean isNullOrWhiteSpace(String value) {
		if (value == null)
			return true;
		if ("".equals(value.trim()))
			return true;
		return false;
	}

	/**
	 * 验证字符串是否为null或空字符串
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 验证结果，字符串为null或空字符串，返回true，否则返回false
	 */
	public static boolean isNullOrEmpty(String value) {
		if (value == null)
			return true;
		if ("".equals(value))
			return true;
		return false;
	}
	
    /**
     * 生成随机数
     * @param size 多少位随机数
     * @return
     */
    public static String randomStr(int size){
    	int i=1;//i在此程序中只作为判断是否是将随机数添加在首位，防止首位出现0；
    	  Random r=new Random();
    	  int tag[]={0,0,0,0,0,0,0,0,0,0};
    	  String str="";
    	  int temp=0;

    	  while(str.length()<size)
    	  { temp=r.nextInt(10);//取出0(含)~10(不含)之间任意数
    	    if(i==1&&temp==0)
    	      { continue;
    	      }
    	      else{
    	       i=2;
    	    if(tag[temp]==0)
    	     {
    	     str=str+temp;
    	     tag[temp]=1;
    	     }
    	      }
    	   }
    	return str;
    }
    
    /**
     * 转换长ESB
     * @param value
     * @return
     */
    public static String changStrToEsb(String value){
    	String str = "";
    	str = value + "." + value.replace("/", "") + "HttpEndpoint";
    	return str;
    }
    
    /**
     * 秒转成时分秒(hh:mm:ss)
     * @param time
     * @return
     */
    public static String secToTime(int time) {  
        String timeStr = null;  
        int hour = 0;  
        int minute = 0;  
        int second = 0;  
        if (time <= 0)  
            return "00:00";  
        else {  
            minute = time / 60;  
            if (minute < 60) {  
                second = time % 60;  
                timeStr = unitFormat(minute) + ":" + unitFormat(second);  
            } else {  
                hour = minute / 60;  
                if (hour > 99)  
                    return "99:59:59";  
                minute = minute % 60;  
                second = time - hour * 3600 - minute * 60;  
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);  
            }  
        }  
        return timeStr;  
    }  
  
    public static String unitFormat(int i) {  
        String retStr = null;  
        if (i >= 0 && i < 10)  
            retStr = "0" + Integer.toString(i);  
        else  
            retStr = "" + i;  
        return retStr;  
    }  
    
	/**
	 * 验证字符串是否为数值
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 验证结果，字符串为数值，返回true，否则返回false
	 */
	public static boolean isNotNumeric(String value) {
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(value);
		   if( !isNum.matches() ){
		       return true; 
		   } 
		   return false; 
	}
	
}
