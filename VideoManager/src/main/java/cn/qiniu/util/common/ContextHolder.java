package cn.qiniu.util.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 〈spring上下文对象〉
 * 〈功能详细描述〉
 * @author    FELIX
 * @version   V1.00 2015-10-10 [版本号, YYYY-MM-DD]
 * @see       [相关类/方法]
 * @since     kindMed V1.0R001 [金域文控管理系统/通用模块]
 */
public class ContextHolder
{
    private static ApplicationContext ctx;

    public ContextHolder()
    {
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getCtx()
    {
        HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();    
        ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        return ctx;
    }

    /**
     *@param beanName value
     * @return Object
     */
    public static Object getBean(String beanName)
    {
        if (getCtx() != null)
        {
            return getCtx().getBean(beanName);
        }
        else
        {
            return null;
        }
    }
}
