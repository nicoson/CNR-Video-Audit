package cn.qiniu.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.MemoryUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.qiniu.config.Global;
import cn.qiniu.entity.SysDict;
import cn.qiniu.entity.SysDictExample;
import cn.qiniu.mapper.SysDictMapper;

/**
 * ehcache 缓存工具类
 * 
 * cacheName在ehcache.xml中配置
 */
@Component
public class EhcacheUtil {

	//字典主表
    private static SysDictMapper basDictionary;
    
	public static CacheManager manager = null;
	static {
		manager = CacheManager.getInstance();
		manager.addCacheIfAbsent(Global.CACHE_NAME);
	}

	private static Logger logger = Logger.getLogger(EhcacheUtil.class);
	
	/**
	 * 启动spring加载字典数据到缓存
	 * @param dicDao
	 */
	public static void initStartupDictionaryData(SysDictMapper basDictionary){
		logger.info("starting load dictionay...");
		//设置当前类的字典mapper实例
		EhcacheUtil.basDictionary = basDictionary;
		
		//检索字典主表
		List<String> dictTypeList = basDictionary.selectDictTypeList();
		
		//缓存字典信息
		for (String type : dictTypeList) {
			//设置数据字典主表检索条件
			SysDictExample example = new SysDictExample();
			//只抽取删除标志="0"的数据
			example.createCriteria().andDelFlgEqualTo(Global.DEL_FLG.DEL_FLG_0)
			.andTypeEqualTo(type);
			//检索字典主表
			List<SysDict> dictList = basDictionary.selectByExample(example);
			//以字典类型为单位缓存字典信息
			EhcacheUtil.put(type, dictList);
		}
		logger.info("load dictionay success...");
	}
	
	
	/**
	 * 获取字典项lable值
	 * @param type    字典类型
	 * @param value   字典code
	 * @return
	 */
	public static String getDictLabels(String type, String value) {
		return getDictByValue(type, value).getLabel();
	}
	
	/**
	 * 从Ehcache获取字典项value值
	 * @param type    字典类型
	 * @param label   字典内容
	 * @return
	 */
	public static String getDictValue(String type, String label) {
		return getDictByLabel(type, label).getValue();
	}
	
	/**
	 * 获取字典项
	 * @param type    字典类型
	 * @param value   字典code
	 * @return 
	 */
	public static SysDict getDictByValue(String type, String value) {
		SysDict returnVal = new SysDict();
		List<SysDict> items = (List<SysDict>) get(type);
		//如果在缓存找不到的话，重新加载对应字典数据
		if(items==null){
			//设置数据字典主表检索条件
			SysDictExample example = new SysDictExample();
			//只抽取删除标志="0"的数据
			example.createCriteria().andDelFlgEqualTo(Global.DEL_FLG.DEL_FLG_0)
			.andTypeEqualTo(type);
			//检索字典主表
			List<SysDict> dictList = basDictionary.selectByExample(example);
			
			//如果表中存在字典项
			if(dictList!=null){
				EhcacheUtil.put(type, dictList);
			}else{
				items = new ArrayList<SysDict>();
			}
		}
		for (SysDict dict : items) {
			if(!StringUtils.isEmpty(dict.getType()) &&
					value.equals(dict.getValue())) {
				returnVal = dict;
				break;
			}
		}
		return returnVal;
	}
	
	/**
	 * 获取字典项
	 * @param type    字典类型
	 * @param label   字典内容
	 * @return 
	 */
	public static SysDict getDictByLabel(String type, String label) {
		SysDict returnVal = new SysDict();
		List<SysDict> items = (List<SysDict>) get(type);
		//如果在缓存找不到的话，重新加载对应字典数据
		if(items==null){
			//设置数据字典主表检索条件
			SysDictExample example = new SysDictExample();
			//只抽取删除标志="0"的数据
			example.createCriteria().andDelFlgEqualTo(Global.DEL_FLG.DEL_FLG_0)
			.andTypeEqualTo(type);
			//检索字典主表
			List<SysDict> dictList = basDictionary.selectByExample(example);
			
			//如果表中存在字典项
			if(dictList!=null){
				EhcacheUtil.put(type, dictList);
			}else{
				items = new ArrayList<SysDict>();
			}
		}
		for (SysDict dict : items) {
			if(!StringUtils.isEmpty(dict.getType()) &&
					label.equals(dict.getLabel())) {
				returnVal = dict;
				break;
			}
		}
		return returnVal;
	}
	
	/**
	 * 从Ehcahe获取项值
	 * @param key
	 * @return
	 */
	public static Object get(Object key) {
		Cache cache = manager.getCache(Global.CACHE_NAME);
		if (cache != null) {
			Element element = cache.get(key);
			if (element != null) {
				return element.getObjectValue();
			}
		}
		return null;
	}

	/**
	 * 保存Ehcahe键值对
	 * @param key
	 * @param value
	 */
	public static void put(Object key, Object value) {
		Cache cache = manager.getCache(Global.CACHE_NAME);
		if (cache != null) {
			cache.put(new Element(key, value));
		}
	}

	/**
	 * 删除Ehcahe键值对
	 * @param key
	 * @param value
	 */
	public static boolean remove(Object key) {
		Cache cache = manager.getCache(Global.CACHE_NAME);
		if (cache != null) {
			return cache.remove(key);
		}else{
			
		}
		return false;
	}

	public static void main(String[] args) {
		String key = "key";
		String value = "hello";
		EhcacheUtil.put("key1", "hello1");
		EhcacheUtil.put("key2", "hello2");
		System.out.println(EhcacheUtil.get("key2"));
		manager.shutdown();
		CacheManager cacheManager = CacheManager.create();  
		CacheConfiguration cacheConfig = new CacheConfiguration();  
		cacheConfig.name("cache1").maxBytesLocalHeap(100, MemoryUnit.MEGABYTES);  
	}
	
}