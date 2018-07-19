package cn.qiniu.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
*  Project：Id2NmFormatter
*  statement：code转名称注解类
*  @Author ：lvweijun
*  @Date ：2016-11-03
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Id2NmFormatter {
	String[] id2NmFields();
}