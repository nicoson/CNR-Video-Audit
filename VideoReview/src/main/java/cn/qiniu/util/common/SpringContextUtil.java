package cn.qiniu.util.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 
 * 〈SPRING初始化上下文〉
 * 〈功能详细描述〉
 * @author    FELIX
 * @version   V1.00 2015-10-10 [版本号, YYYY-MM-DD]
 * @see       [相关类/方法]
 * @since     kindMed V1.0R001 [金域文控管理系统/模块版本]
 */
public class SpringContextUtil implements ApplicationContextAware
{
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException
    {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     *〈获取对象〉
     * @param name 对象名称
     * @return Object bean的实例
     */
    public static Object getBean(String name)
        throws BeansException
    {
        return applicationContext.getBean(name);
    }

    /**
     *〈获取对象实例〉
     * @param name 注册Bean的名称
     * @param requiedType 返回对象类型
     * @return
     * @throws BeansException Object
     */
    public static Object getBean(String name, Class<?> requiedType)
        throws BeansException
    {
        return applicationContext.getBean(name, requiedType);
    }

}
