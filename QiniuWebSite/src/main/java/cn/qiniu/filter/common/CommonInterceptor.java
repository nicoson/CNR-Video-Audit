package cn.qiniu.filter.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CommonInterceptor implements HandlerInterceptor{
	  
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object obj, Exception err)
            throws Exception {
    }  
  
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object obj, ModelAndView mav) throws Exception {
    }  
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object obj) throws Exception {
//    	System.out.println("Authentication:" + request.getHeader("Authentication"));
//		// 获取请求类型
//		String method = request.getMethod();
//    	System.out.println("请求类型:" + method);
//		//获取请求接口路径
//		String path = request.getRequestURI();
//    	System.out.println("请求接口路径:" + path);
//		//获取请求参数
//		String rawQuery = request.getQueryString();
//    	System.out.println("请求参数:" + rawQuery);
//		//获取请求主机
//		StringBuffer url = request.getRequestURL();  
//		String host = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();     	System.out.println("请求主机:" + host);
//		//获取请求内容类型
//		String contentType = request.getContentType();
//    	System.out.println("请求内容类型:" + contentType);
//		//获取请求Body
//		String bodyStr = this.getBodyStr(request);
//    	System.out.println("请求Body:" + bodyStr);
//    	HMACSHA1Util.HmacSHA1Encrypt(encryptText, encryptKey);
        return true;  
    }  
  
    /**
     * 取得POST请求参数
     *
     */
	private String getBodyStr(HttpServletRequest request) {
		String body = "";
		try {
			StringBuffer sb = new StringBuffer() ; 
			InputStream is = request.getInputStream(); 
			InputStreamReader isr = new InputStreamReader(is, "utf-8");   
			BufferedReader br = new BufferedReader(isr); 
			String s = "" ; 
			while((s=br.readLine())!=null){ 
				sb.append(s) ; 
			} 
			body =sb.toString();

			is.close();
			isr.close();
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return body;
	}
}
