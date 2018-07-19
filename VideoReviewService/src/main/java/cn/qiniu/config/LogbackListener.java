package cn.qiniu.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import ch.qos.logback.ext.spring.web.WebLogbackConfigurer;

public class LogbackListener implements ServletContextListener  {
	
	private static String logbackAppName = "CNR-VIDEO"; 
	private static String logbackServerIP = ""; 
	private static String logbackServerHost = ""; 
	
	@Override
    public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
        //添加系统属性示例代码
        if (org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS) {
           System.setProperty("logPath:-/tmp", "${CATALINA_HOME}" + SystemUtils.FILE_SEPARATOR + "logs");
        } else {//linux
           System.setProperty("logPath:-/tmp", "/logs");
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

    @Override
    public void contextDestroyed(ServletContextEvent event){
        WebLogbackConfigurer.shutdownLogging(event.getServletContext());
    }

}
