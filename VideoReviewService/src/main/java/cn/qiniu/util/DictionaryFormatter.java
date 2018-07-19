package cn.qiniu.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
*  Project：DictionaryFormatter
*  statement：数据字典注解信息类
*  @Author ：lvweijun
*  @Date ：2016-11-03
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DictionaryFormatter {
	String[] dicDefFields();
}