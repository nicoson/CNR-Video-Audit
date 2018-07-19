package cn.qiniu.util;

import java.util.List;

/** 
*  Project：VoAnnotationInfo
*  statement：Annotation信息类
*  @Author ：lvweijun
*  @Date ：2016-11-03
*/
public class VoAnnotationInfo {

	/* 数据字典注解信息数据对象集合*/
	private List<AnnotationInfoVo>  annotationInfoVo;
	
	/* code转名称注解信息数据对象集合*/
	private List<Id2NmFormatterVo>  id2NmFormatterVo;

	public List<AnnotationInfoVo> getAnnotationInfoVo() {
		return annotationInfoVo;
	}

	public void setAnnotationInfoVo(List<AnnotationInfoVo> annotationInfoVo) {
		this.annotationInfoVo = annotationInfoVo;
	}

	public List<Id2NmFormatterVo> getId2NmFormatterVo() {
		return id2NmFormatterVo;
	}

	public void setId2NmFormatterVo(List<Id2NmFormatterVo> id2NmFormatterVo) {
		this.id2NmFormatterVo = id2NmFormatterVo;
	}
	
	
}