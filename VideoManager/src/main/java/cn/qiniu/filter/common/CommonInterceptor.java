package cn.qiniu.filter.common;

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
        return true;  
    }  

}
