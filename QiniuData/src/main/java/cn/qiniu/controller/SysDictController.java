package cn.qiniu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.qiniu.form.SysDictForm;
import cn.qiniu.service.SysDictService;

@RestController
public class SysDictController {

    @Autowired
    private SysDictService service;
    
    /**
     *  statement：数量
     *
     * @return
     */
    @RequestMapping(value = "/basDictionaryCount", method = RequestMethod.POST)
    public Object tbCount(@RequestBody SysDictForm form) {
        return service.tbCount(form);
    }
    
    /**
     *  statement：列表
     *
     * @return
     */
    @RequestMapping(value = "/basDictionaryList", method = RequestMethod.POST)
    public Object tbList(@RequestBody SysDictForm form) {
        return service.tbList(form);
    }
    
    /**
     *  statement：根据字典code查询字典项列表
     *
     * @return 
     */
    @RequestMapping(value = "/selectItemsByDictionaryCode", method = RequestMethod.POST)
    public Object selectByDictionaryCode(@RequestBody SysDictForm form) {
    	return service.selectByDictionaryCode(form);
    }
    
    /**
     *  statement：根据字典code查询字典项列表
     *
     * @return 
     */
    @RequestMapping(value = "/getDictLabelByValue", method = RequestMethod.POST)
    public String getDictLabelByValue(@RequestBody SysDictForm form) {
    	return service.getDictLabelByValue(form.getSearch().getValue(),form.getSearch().getType());
    }
    
    
}
