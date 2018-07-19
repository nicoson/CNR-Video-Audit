package cn.qiniu.listener.common;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.SystemUtils;

import ch.qos.logback.ext.spring.web.WebLogbackConfigurer;

/**
 * Logback日志监听器
 * 
 * @author Hong Yingjie
 * @version 2017-09-26
 */
public class LogbackListener implements ServletContextListener  {
	// APP 名称
	private static String logbackAppName = "VIDEO-REVIEW-SERVICE"; 
	
	/**
	 * 初始化处理
	 * 
	 * @param event
	 */
	@Override
    public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
        //添加系统属性示例代码
        if (org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS) {
           System.setProperty("logPath", "${CATALINA_HOME}" + SystemUtils.FILE_SEPARATOR + "logs");
        } else {//linux
           System.setProperty("logPath", "/logs");
        }
        
        System.setProperty("APP_NAME", logbackAppName);

        String env =System.getProperty("env"); 
        
        if ("product".equals(env)) {
           System.setProperty("log.root.level", "WARN");
        } else {//非生产环境
           System.setProperty("log.root.level", "DEBUG");
        } 
        WebLogbackConfigurer.initLogging(sc);
    }

	/**
	 * 销毁处理
	 * 
	 * @param event
	 */
    @Override
    public void contextDestroyed(ServletContextEvent event){
        WebLogbackConfigurer.shutdownLogging(event.getServletContext());
    }
}