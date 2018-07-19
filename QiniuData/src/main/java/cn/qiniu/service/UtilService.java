package cn.qiniu.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.qiniu.config.Global;
import cn.qiniu.config.Global.DATE_TIME;
import cn.qiniu.util.AnnotationInfoVo;
import cn.qiniu.util.DictUtil;
import cn.qiniu.util.DictionaryFormatter;
import cn.qiniu.util.EhcacheUtil;
import cn.qiniu.util.Id2NmFormatter;
import cn.qiniu.util.Id2NmFormatterVo;
import cn.qiniu.util.PagerForm;
import cn.qiniu.util.StringUtil;
import cn.qiniu.util.VoAnnotationInfo;
import cn.qiniu.vo.PagerData;
import cn.qiniu.vo.VoConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by roderickWang on 6/24/16.
 */
@Service
public class UtilService {
	
	private static Logger logger = Logger.getLogger(EhcacheUtil.class);

    /**
     * 封装查询条件
     * 根据domin的值做筛选,exceptProp不筛选(时间等字段)
     * @param example:查询条件
     * @param domain:查询实体
     * @param exceptProp:无需转换的条件选项
     * @param <T>
     * @return
     */
    public <T, K> void convertExample(T example, K domain, List<String> exceptProp) {
        try {
        	if (domain==null) return ;
            Method[] declaredMethods = domain.getClass().getMethods();
            for (Method method : declaredMethods) {
                String name = method.getName();
                String midFix = name.substring(3);

                if (exceptProp.contains(midFix.substring(0, 1).toLowerCase() + midFix.substring(1))) {
                    continue;
                }

                if (name.startsWith("get")) {
                    Object val = method.invoke(domain);
                    if (val instanceof String && StringUtils.isNotEmpty((CharSequence) val)) {
                        Class<?> returnType = method.getReturnType();

                        if (name.endsWith("From")) {
                            midFix = midFix.replace("From", "");
                            Method exampleMethod = example.getClass().getMethod("and" + midFix + "GreaterThanOrEqualTo", Date.class);
                            Object invokeVal = covertType((String) val,DATE_TIME.START, Date.class);
                            example = (T) exampleMethod.invoke(example, invokeVal);
                        } else if (name.endsWith("To")) {
                            midFix = midFix.replace("To", "");
                            Method exampleMethod = example.getClass().getMethod("and" + midFix + "LessThanOrEqualTo", Date.class);
                            Object invokeVal = covertType((String) val,DATE_TIME.END, Date.class);
                            example = (T) exampleMethod.invoke(example, invokeVal);
                        } else {
                            Method exampleMethod = example.getClass().getMethod("and" + midFix + "Like", returnType);
                            String invokeVal ="%" + val + "%";
                            example = (T) exampleMethod.invoke(example, invokeVal);
                        }
                    }
                }
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
        	logger.error(e.getMessage());
        }
    }

    /**
     * 查询条件中的日期格式转换
     * @param val:日期内容
     * @param type:日期格式
     * @param <T>
     * @return
     */
    private Object covertType(String val,String str,Class<?> type) {
        try {
            if (type == Date.class) {
                SimpleDateFormat sf=null;
                sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(val.contains(":")){
                	return sf.parse(val);
                }else{
                	return sf.parse(val + str);
                }
            } else if (type == Integer.class || type == int.class) {
                return Integer.valueOf(val);
            } else {
                return val;
            }
        } catch (Exception e) {
        	logger.error(e.getMessage());
        	return null;
        }
    }

    /**
     * 封装返回结果集，包括返回数据、分页信息
     * @param data:返回数据结果集
     * @param form:分页信息
     * @param total:数据总行数
     * @param <T>
     * @return
     */
    public <T> PagerData buildVoWithPage(List<T> data, PagerForm pagerForm, int total) {
        return new PagerData(formatVo(data), pagerForm, total);
    }
    
    /**
     * 封装返回结果集，不含分页信息
     * @param data:返回数据结果集
     * @param <T>
     * @return
     */
    public <T> List<Map> buildVoWithoutPage(List<T> data) {
        return formatVo(data);
    }

    /**
     * 转换结果集数据类型、并根据指定数据字典类型自动加载字典名称
     * 目前自动转换日期格式，其他数据类型统一转换为文字类型
     * @param <T>
     * @param data
     * @return
     */
    private <T> List<Map> formatVo(List<T> data) {
    	try {
    		//如果数据集为空，返回空
            if (data.size() == 0) {
                return new LinkedList<>();
            }

            Map<String, VoConverter> covertMap = new HashMap();
            //----------------1、取得数据集中日期类型的字段-----------------------------
            Field[] fields = data.get(0).getClass().getDeclaredFields();
            for (Field field : fields) {
            	//判断当前字段类型是否为日期类型
                if (field.getGenericType() == Date.class) {
                	//取得转换后的日期格式,默认为：yyyy-MM-dd HH:mm:ss格式
                    covertMap.put(field.getName(), getConverter("dateConverter"));
                }
            }
            
            //----------------2、解析Domain中的注解信息----------------------------
            //----------------2-1、取得数据集中定义的字典项---------------------------
            VoAnnotationInfo annoInfo = analysiser(data.get(0).getClass());
            //----------------2-2、取得数据集中定义的code转名称注解信息-----------------
            annoInfo = getId2NmFormatter(annoInfo, data.get(0).getClass());

            //----------------3、循环数据集取得字典项目名称，并转换结果集项目为字符类型---------
            //定义返回结果Map
            List<Map> vo = new LinkedList<>();
            //定义Map转换器
            ObjectMapper objectMapper = new ObjectMapper();
            //循环处理数据集
            for (T item : data) {
            	//将单行数据转换为Map格式
                Map newItem = objectMapper.convertValue(item, Map.class);
                
                //-------------3.1 循环数据集中的日期类型字段，并将其转换为默认日期格式（yyyy-MM-dd HH:mm:ss）-----------
                for (Map.Entry<String, VoConverter> covert : covertMap.entrySet()) {
                    String prop = covert.getKey();
                    if (newItem.get(prop) == null) {
                        continue;
                    }
                    //保存日期格式转换后的字段内容
                    newItem.put(prop, covert.getValue().convert(newItem.get(prop)));
                }
                
                //-------------3.2 取得字典名称-----------
        		// 如果当前数据集存在字典项注解信息，则从缓存中取得字典名称
        		if (annoInfo != null && annoInfo.getAnnotationInfoVo() != null && annoInfo.getAnnotationInfoVo().size() > 0) {
        			//取得字典项目
        			List<AnnotationInfoVo> dictInfoList = annoInfo.getAnnotationInfoVo();
        			//循环字典项，取得字典名称
                    for (AnnotationInfoVo dictItem : dictInfoList) {
                    	//取得需要字典转换的字典Code
                        String dictCode = dictItem.getDictionaryCode();
                    	//取得需要字典转换的项目名称
                        String name = dictItem.getName();
                        if (newItem.get(name) == null) {
                            continue;
                        }
                        //从缓存中取得字典名称
                        String dictNm = DictUtil.getDictLabel(dictCode, String.valueOf(newItem.get(name)), "");
                        //向结果集Map中增加字典名称项目（默认名称字段名：字典Code名称+"DicItemVal"）
                        newItem.put(name + Global.DIC_ITEM_VALUE, dictNm);
                    }
        		}
        		
        		//添加处理后的返回结果
                vo.add(newItem);
            }
            return vo;
		} catch (Exception e) {
        	logger.error(e.getMessage());
        	return null;
		}
    }

    /**
	*  取得数据转换格式
	*  @param： name:转换格式名称
	*  @return： VoConverter 转换格式
	*/
    private VoConverter getConverter(String name) {
    	//从系统中加载指定转换格式类
        WebApplicationContext webApplication = ContextLoader.getCurrentWebApplicationContext();
        //返回转换格式
        return (VoConverter) webApplication.getBean(name);
    }
    
	/**
	*  解析数据字典注解信息
	*  @param： voClass:目标数据集
	*  @return： VoAnnotationInfo  解析后的注解信息
	*/
	private VoAnnotationInfo analysiser(Class voClass) {
		try {
			// 定义注解信息对象集合
			VoAnnotationInfo info = new VoAnnotationInfo();
			// 定义注解信息集合变量
			List<AnnotationInfoVo> annoList = new ArrayList();
			// 定义注解信息VO变量
			AnnotationInfoVo annoVo;
			// 取得目标数据集中的注解信息
			DictionaryFormatter formatter = (DictionaryFormatter) voClass.getAnnotation(DictionaryFormatter.class);
			//如果未定义注解信息，终止处理
			if (formatter == null) {
				return info;
			}
			// 保存注解信息
			String[] fieldsList = formatter.dicDefFields();
			// 如果注解信息为空，终止处理
			if (fieldsList == null || fieldsList.length == 0) {
				return info;
			}
			// 解析注解信息
			for (String field : fieldsList) {
					if (!StringUtil.isNullOrEmpty(field)) {
						if(field.split(",").length != 2) {
							continue;
						}
						// 注解信息VO变量
						annoVo = new AnnotationInfoVo();
						// 字典Code
						annoVo.setDictionaryCode(field.split(",")[0]);
						// 需转义的项目
						annoVo.setName(field.split(",")[1]);
						// 将注解信息添加到注解信息集合
						annoList.add(annoVo);
					}
				}
			info.setAnnotationInfoVo(annoList);
			// 返回注解信息集合
			return info;
		} catch (Exception e) {
        	logger.error(e.getMessage());
        	return null;
		}
	}
	
	/**
	*  数据集中定义的code转名称注解信息
	*  @param： voClass:目标数据集
	*  @return： VoAnnotationInfo  解析后的注解信息
	*/
	private VoAnnotationInfo getId2NmFormatter(VoAnnotationInfo annoInfo, Class voClass) {
		try {
			// 定义注解信息集合变量
			List<Id2NmFormatterVo> annoList = new ArrayList();
			// 定义注解信息VO变量
			Id2NmFormatterVo annoVo;
			// 取得目标数据集中的注解信息
			Id2NmFormatter formatter = (Id2NmFormatter) voClass.getAnnotation(Id2NmFormatter.class);
			//如果未定义注解信息，终止处理
			if (formatter == null) {
				return annoInfo;
			}
			// 保存注解信息
			String[] fieldsList = formatter.id2NmFields();
			// 如果注解信息为空，终止处理
			if (fieldsList == null || fieldsList.length == 0) {
				return annoInfo;
			}
			// 解析注解信息
			for (String field : fieldsList) {
					if (!StringUtil.isNullOrEmpty(field)) {
						if(field.split(",").length == 6) {
							// 注解信息VO变量
							annoVo = new Id2NmFormatterVo();
							// 数据库名称
							annoVo.setDatabase(field.split(",")[0]);
							// 表名称
							annoVo.setTbl(field.split(",")[1]);
							// key值
							annoVo.setKey(field.split(",")[2]);
							// key值对应名称
							annoVo.setNm(field.split(",")[3]);
							// 查询条件key
							annoVo.setSchKey(field.split(",")[4]);
							// 名称别名
							annoVo.setTransNm(field.split(",")[5]);
							// 附件条件
							annoVo.setAndStr(null);
							// 将注解信息添加到注解信息集合
							annoList.add(annoVo);
						}
						if(field.split(",").length == 7) {
							// 注解信息VO变量
							annoVo = new Id2NmFormatterVo();
							// 数据库名称
							annoVo.setDatabase(field.split(",")[0]);
							// 表名称
							annoVo.setTbl(field.split(",")[1]);
							// key值
							annoVo.setKey(field.split(",")[2]);
							// key值对应名称
							annoVo.setNm(field.split(",")[3]);
							// 查询条件key
							annoVo.setSchKey(field.split(",")[4]);
							// 名称别名
							annoVo.setTransNm(field.split(",")[5]);
							// 附件条件
							annoVo.setAndStr(field.split(",")[6]);
							// 将注解信息添加到注解信息集合
							annoList.add(annoVo);
						}
						
					}
				}
			annoInfo.setId2NmFormatterVo(annoList);
			// 返回注解信息集合
			return annoInfo;
		} catch (Exception e) {
        	logger.error(e.getMessage());
        	return annoInfo;
		}
	}
	
	
	public static void main(String[] args) {
		String abc = "aaa,bbb,ccc";
	    String[] arr = abc.split(",");
	    List<String> list = java.util.Arrays.asList(arr);
	    System.out.println(list.get(0));
	    System.out.println(list.get(1));
	    System.out.println(list.get(2));
	}
}
