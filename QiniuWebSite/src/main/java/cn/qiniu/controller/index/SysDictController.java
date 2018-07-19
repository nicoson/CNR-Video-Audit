package cn.qiniu.controller.index;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.qiniu.controller.common.BaseController;
import cn.qiniu.entity.SysDict;
import cn.qiniu.entity.basic.SysDictSearch;
import cn.qiniu.service.SysDictService;


@Controller
public class SysDictController extends BaseController {
	@Autowired
	private SysDictService sysDictService;
	/**
	 * 根据字典CODE,取得所有字典子项
	 * @param request
	 * @param response
	 * @param search
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/index/getDictionaryItemsByCode")
	public String getDictionaryItemsByCode(HttpServletRequest request, HttpServletResponse response, SysDictSearch search) {
		String result = null; 
		try{
			List<SysDict> dicItemList = sysDictService.queryBasDictionaryItemByCode(search);
			result = getJson(true, null, dicItemList, false);
		} catch (Exception e) {
			log.error("[查询字典记录报错]", e);
			result = getJson(false, "查询失败", null, false);
		}
		
		return result;
	}
}
