package cn.qiniu.util.common;


import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * serlvet 初始化代理
 * @author Administrator
 *
 */
public class DelegatingServletProxy extends HttpServlet {  
    private static final long serialVersionUID = 1L;  
    private String targetServletBean;  
    private Servlet proxy;  
      
    public void init() throws ServletException {  
        this.targetServletBean = this.getInitParameter("targetServletBean");  
        this.getServletBean();  
        this.proxy.init(this.getServletConfig());  
    }  
  
    protected void service(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        proxy.service(request,response);   
    }  
  
    private void getServletBean(){  
        ServletContext servletContext = this.getServletContext();  
        WebApplicationContext wac = null;   
        wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);  
        this.proxy = (Servlet) wac.getBean(targetServletBean);  
    }  
}  