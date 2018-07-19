package cn.qiniu.delegator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.qiniu.util.common.Pager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * Project:CommonDelegator.java statement: FastJson解析类
 * 
 * @author:lvwj
 * @Date:
 */
@Component
public class CommonDelegator {
	// AccessKey
	@Autowired
	private String accessKey;
	
	// SecretKey
	@Autowired
	private String secretKey;
	
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> Pager<T> parsePager(String jsonString, Class cls) {
		Pager<T> pager = null;
		try {
			JSONObject object = JSON.parseObject(jsonString);
			if (object != null) {
				pager = new Pager<>();
				//当前行
				pager.setCurrent(object.get("current") == null ? 0
						: Integer.parseInt(object.get("current").toString()));
				//数据集
				pager.setData(JSON.parseArray(object.get("data").toString(), cls));
				//每页显示行数
				pager.setRowCount(object.get("rowCount") == null ? 0 : Integer
						.parseInt(object.get("rowCount").toString()));
				//总页数
				pager.setTotalPageCount(object.get("totalPageCount") == null ? 0
						: Integer.parseInt(object.get("totalPageCount").toString()));
				//检索结果总行数
				pager.setTotalRowCount(object.get("totalRowCount") == null ? 0
						: Integer.parseInt(object.get("totalRowCount").toString()));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return pager;
	}
	
	/**
	 * 取得Head部（包含鉴权）
	 * @param url			访问地址
	 * @param method		请求方式
	 * @param body			请求内容
	 * @param contentType	请求类型
	 * @return Head部信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String> getHeader(String url, String method, byte[] body, String contentType){
		Auth me = Auth.create(accessKey, secretKey);
		StringMap authorization = me.authorizationV2(url, method, body, contentType);
		return (Map) authorization.map();
	}
	
	/**
	 * 签名
	 * @param url
	 * @param method
	 * @param body
	 * @param contentType
	 * @return
	 */
	public StringMap authorizationV2(String url, String method, byte[] body, String contentType){
		Auth me = Auth.create(accessKey, secretKey);
		StringMap authorization = me.authorizationV2(url, method, body, contentType);
		return authorization;
	}
}
