package cn.qiniu.delegator;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.entity.SysDict;
import cn.qiniu.entity.basic.SysDictSearch;
import cn.qiniu.form.SysDictForm;
import cn.qiniu.framework.httpclient.HttpClientTemplate;

import com.google.common.collect.Maps;

@Component
public class SysDictDelegator {

	@Autowired
	private HttpClientTemplate httpClientTemplate;
	
	@Autowired
	private CommonDelegator commonDelegator;
	
	@Autowired
	private String qiniuDataUrl;
	
	private static final String TB_QUERYBYDICCODE = "/selectItemsByDictionaryCode";
	
	private static final String GET_DICT_LABEL_BY_VALUE = "/getDictLabelByValue";
	
	
	/**
     * 拼接https地址
     *
     * @return
     */
	private String getQiniuData(String url) {
		return qiniuDataUrl + url;
	}
	
	/**
	 *	查询字典子项列表
	 */
	public List<SysDict> selectByDictionaryCode(SysDictForm form) {	
		List<SysDict> result = null;
		Map<String,Object> params = Maps.newHashMap();
		//设置参数:条件
		params.put("search", form.getSearch());
		try {
			//取得字典信息
			String response = httpClientTemplate.doPost(getQiniuData(TB_QUERYBYDICCODE), params);
			result = commonDelegator.parseObjectList(response, SysDict.class);
		} catch (Exception e) {
			return result;
		}
		return result;
	}
	
	/**
	 *	查询字典子项列表
	 */
	public String getDictLabelByValue(String value,String type) {
		String response = "";
		Map<String,Object> params = Maps.newHashMap();
		SysDictSearch search = new SysDictSearch();
		search.setValue(value);
		search.setType(type);
		//设置参数:条件
		params.put("search", search);
		try {
			//取得字典信息
			response = httpClientTemplate.doPost(getQiniuData(GET_DICT_LABEL_BY_VALUE), params);
//			result = commonDelegator.parseObjectList(response, SysDict.class);
		} catch (Exception e) {
			return "";
		}
		return response;
	}
	
	
	
}
