package cn.qiniu.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * 字符串处理的共通方法
 */
public class StringUtil {
    /**
     * 将空字符串转为null
     *
     * @param value 要转换的字符串
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
     * @param value 要转换的字符串
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
     * @param value 要验证的字符串
     * @return 验证结果，字符串为null或由空格组成，返回true，否则返回false
     */
    public static boolean isNullOrWhiteSpace(String value) {
        if (value == null) {
            return true;
        }
        if ("".equals(value.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 验证字符串是否为null或空字符串
     *
     * @param value 要验证的字符串
     * @return 验证结果，字符串为null或空字符串，返回true，否则返回false
     */
    public static boolean isNullOrEmpty(String value) {
        if (value == null) {
            return true;
        }
        if ("".equals(value)) {
            return true;
        }
        return false;
    }

    /**
     * 把String转为double
     *
     * @param value 要验证的字符串
     * @return 验证结果，字符串为null或空字符串，返回true，否则返回false
     */
    public static double strToDouble(String value) {
        if (value == null) {
            return 0;
        }
        return Double.parseDouble(value);
    }

    /**
     * 把String转为数值,并保留2位小数
     *
     * @param value 要验证的字符串
     * @return 验证结果，字符串为null或空字符串，返回true，否则返回false
     */
    public static String strToDecimal(String value, int divisor) {
        if (isNullOrEmpty(value)) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(Double.parseDouble(value) / divisor);
    }

    /**
     * 去除小数位后多余的0
     *
     * @return
     */
    public static String removeZero(String val) {
        if (StringUtil.isNullOrEmpty(val)) {
            return "";
        }
        if (val.indexOf(".") > 0) {
            //去掉后面无用的零
            val = val.replaceAll("0+?$", "");
            //如小数点后面全是零则去掉小数点
            val = val.replaceAll("[.]$", "");
        }
        return val;
    }

    /**
     * ip地址转换为Long型数值
     *
     * @return
     */
    public static long ipToLong(String ipAddress) {
        long result = 0;
        String[] ipAddressInArray = ipAddress.split("\\.");
        for (int i = 3; i >= 0; i--) {
            long ip = Long.parseLong(ipAddressInArray[3 - i]);
            //1. 192 << 24
            //1. 168 << 16
            //1. 1   << 8
            //1. 2   << 0
            result |= ip << (i * 8);
        }
        return result;
    }
    
    /**
     * 转换字符串为驼峰格式
     *
     * @param value 要转换的字符串
     * @return 如果为空字符串，返回null；否则返回驼峰格式字符串
     */
    public static String strToHump(String value) {
        if (isNullOrEmpty(value)) {
            return null;
        }
        String[] str = value.split("_");
        if (str.length == 1) {
        	return value;
        } else {
        	int i=0;
        	String result = "";
        	for (String item : str) {
				if(i==0) {
					result = item;
					i++;
					continue;
				}
				result = result + item.substring(0,1).toUpperCase() + item.substring(1);
			}
        	return result;
        }
    }
    
    /**
     * 把String转为数值
     *
     * @param value 要验证的字符串
     * @return 验证结果，字符串为null或空字符串，返回true，否则返回false
     */
    public static BigDecimal strToDecimal(String value) {
        if (isNullOrEmpty(value)) {
            BigDecimal zero=new BigDecimal("999.99");
            return zero;
        }
         BigDecimal newBig=new BigDecimal(value); 
         return newBig;
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
    
    public static boolean isNumeric(String str){
    	for (int i = str.length();--i>=0;){  
    		if (!Character.isDigit(str.charAt(i))){
    			return false;
    		}
    	}
    	return true;
	}
}
