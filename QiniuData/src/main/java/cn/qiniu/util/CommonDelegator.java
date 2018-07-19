package cn.qiniu.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * Project:CommonDelegator.java statement: FastJson解析类
 * 
 * @author:lvwj
 * @Date:
 */
@Component
public class CommonDelegator {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T parseObject(String jsonString, Class cls) {
		T t = null;
		try {
			t = (T) JSON.parseObject(jsonString, cls);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T parseModel(String jsonString, Class cls) {
		List list = new ArrayList();
		T t = null;
		try {
			list = JSON.parseArray(jsonString, cls);
			if (list != null && list.size() > 0) {
				t = (T) list.get(0);
			}
		} catch (Exception e) {
		}
		return t;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List parseArray(String jsonString, Class cls) {
		List list = new ArrayList();
		try {
			list = JSON.parseArray(jsonString, cls);
		} catch (Exception e) {
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List<T> parseObjectList(String jsonString, Class cls) {
		List<T> t = null;
		try {
			t = JSON.parseArray(jsonString, cls);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}
}
