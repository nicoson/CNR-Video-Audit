package cn.qiniu.util.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * 通用工具类
 * 
 * @author lvweijun
 * @version V1.00 2016-10-18
 */
public class CommonUtil
{
	private static Properties prop = null;
	static {
		try {
			if (prop == null) {
				prop = new Properties();
				prop.load(CommonUtil.class.getResourceAsStream("/spring-config.properties"));
			}
		} catch (IOException e) {
			System.err.println("加载spring-config.properties失败！");
		}
	}
	
    /**
     * 生成32位UUID 并去掉"-"
     */
    public static String getUUID()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 填充字符串
     * 
     * @param code value
     * @param length value
     * @param fillCode value
     * @return String
     */
    public static String getStatusCode(String code, int length, String fillCode)
    {
        String statusCode;
        for (statusCode = code; statusCode.length() < length; statusCode =
            (new StringBuilder()).append(fillCode).append(statusCode).toString())
        {
            
        }
        return statusCode;
    }
    
    /**
     * 〈直接向用户返回指定信息〉
     * 
     * @param response response
     * @param msg void
     */
    public static void returnMsg(HttpServletResponse response, String msg)
    {
        if (msg == null || "".equals(msg))
        {
            return;
        }
        Writer out = null;
        try
        {
            response.setCharacterEncoding("utf-8");
            out = response.getWriter();
            out.write(msg);
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 〈正则匹配〉
     * 
     * @param reg reg
     * @param str str
     * @return boolean
     */
    public static boolean test(String reg, String str)
    {
        Matcher matcher = Pattern.compile(reg).matcher(str);
        return matcher.find();
    }
    
    // *分割字符串后，将字符串数组转换成整型。
    public static String[] convertStringToIntArray(String stringSplit)
    {
        String s[] = stringSplit.split(",");
        String s3[] = new String[s.length];
        for (int i = 0; i < s.length; i++)
        {
            s3[i] = s[i];/* 将字符串数组转换成整型,结果出现异常。后面事件处理时，调用sort,出现错误。 */
        }
        return s3;
    }
    
    /**
     * 判断字符串是否不为空
     * 
     * @param originalString 原始字符串
     * @return 不为空返回true,否则返回false
     */
    public static boolean isNotEmptyString(String originalString)
    {
        return originalString != null && !"".equals(originalString);
    }
    
    /**
     * 判断字符串是否为空
     * 
     * @param originalString 原始字符串
     * @return 不为空返回true,否则返回false
     */
    public static boolean isEmptyString(String originalString)
    {
        return originalString == null || "".equals(originalString);
    }
    /**
     * 判断字符串是否为空 为空则转变为0
     * 
     * @param originalString 原始字符串
     * @return 不为空返回true,否则返回false
     */
    public static String convertNullToZero(String originalString)
    {
    	if(originalString == null || "".equals(originalString)){
    		return Constant.STRING_ZERO;
    	}
    	return originalString;
    }
    /**
     * 判断list不为空
     * 
     * @param list list
     * @return 不为空返回true,否则返回false
     */
    public static boolean isNotEmptyList(List<?> list)
    {
        return list != null && !list.isEmpty();
    }
    
    /**
     * 计算list记录数
     * 
     * @param list
     * @return
     */
    public static int totalNum(List<?> list)
    {
        if (!list.isEmpty())
        {
            int num = Integer.parseInt(list.toArray()[0].toString());
            list.clear();
            return num;
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * 获得当前日期字符串格式（精确到秒)
     * 
     * @return String
     */
    public static String getCurrentDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        return sdf.format(new Date(System.currentTimeMillis()));
    }
    
    /**
     * 转换日期，按照给定的格式
     * 
     * @return String
     */
    public static String convertDateToString(String mode, Date date)
    {
    	if (date == null) {
    		return null;
    	}
    	
        SimpleDateFormat sdf = new SimpleDateFormat(mode);
        
        return sdf.format(date);
    }
    
    /**
     * 转换日期，按照给定的格式
     * 
     * @return String
     */
    public static Date parseDate(String mode, String dateStr) {
		try {
			if (dateStr == null || "".equals(dateStr.trim())) {
				return null;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat(mode);
			
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
    }
    
    public static Date formDate(String mode, Date date)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(mode);
            Date strtodate = formatter.parse(formatter.format(date));
            return strtodate;
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 根据所给日期返回两日期相差的秒数
     * 
     * @param d1 开始时间
     * @param d2 结束时间
     * @return
     */
    public static long getSecond(Date d1, Date d2)
    {
        long a1 = d1.getTime();
        long a2 = d2.getTime();
        long a3 = (a2 - a1) / 1000;
        
        return a3;
    }
    
	/**
	 * statement: 取得文件服务工程路径
	 * @param 
	 * @return 文件服务工程路径
	 */
	public static String getVcFileMgrUrl() {
		//取得文件服务路径
		String fileServerUrl = getProperties("url.vcFileMgr");
		return fileServerUrl;
	}
	
	/**
	 * 从ApplicationResources.properties中取值
	 * 
	 * @param key 取值的key
	 * @return value 取得的值
	 */
	public static String getProperties(String key) {
		if (prop != null && prop.containsKey(key)) {
			return prop.getProperty(key);
		}
		return null;
	}
	
	/**
	 * statement: 取得文件服务工程路径
	 * @param 
	 * @return 文件服务工程路径
	 */
	public static String getVcComService() {
		//取得文件服务路径
		String fileServerUrl = getProperties("url.vcComService");
		return fileServerUrl;
	}

	public static String getPostRequestParam(HttpServletRequest request) {
		String str = "";
//		JSONObject json = new JSONObject();
		try {
			StringBuffer sb = new StringBuffer() ; 
			InputStream is = request.getInputStream(); 
			InputStreamReader isr = new InputStreamReader(is, "utf-8");   
			BufferedReader br = new BufferedReader(isr); 
			String s = "" ; 
			while((s=br.readLine())!=null){ 
				sb.append(s) ; 
			} 
			str =sb.toString();

			//防止用get传递参数 
			if(str.equals("")) { 
				if(request.getQueryString() != null) { 
					str = request.getRequestURL()+"?"+request.getQueryString(); 
				} else { 
					str = request.getRequestURL().toString(); 
				} 
			} 
//			json = JSONObject.parseObject(str);
			is.close();
			isr.close();
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return str;
	}
}