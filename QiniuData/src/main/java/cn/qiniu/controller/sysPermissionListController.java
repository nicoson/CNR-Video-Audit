package cn.qiniu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.qiniu.form.SysResourceForm;
import cn.qiniu.service.sysPermissionListService;

@RestController
public class sysPermissionListController {
    @Autowired
    private sysPermissionListService service;
    /**
     *  statement：列表
     *
     * @return
     */
    @RequestMapping(value = "/getUserResList", method = RequestMethod.POST)
    public Object tbList(@RequestBody SysResourceForm form) {
        return service.tbList(form);
    }
}
