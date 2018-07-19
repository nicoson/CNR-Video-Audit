package cn.qiniu.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import cn.qiniu.mapper.SysDictMapper;

public class EhCacheInitBean implements
		ApplicationListener<ContextRefreshedEvent> {
	
	//字典主表
    @Autowired
    private SysDictMapper basDictionary;
    
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			//加载数据字典数据
			EhcacheUtil.initStartupDictionaryData(basDictionary);
//			//加载部门数据
//			EhcacheUtil.initStartupDictionaryData(departmentImportDao);
//			//加载用户数据
//			EhcacheUtil.initStartupStaffData(staffDao);
		}

	}

}
