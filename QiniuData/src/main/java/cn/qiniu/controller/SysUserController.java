package cn.qiniu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.qiniu.form.SysUserForm;
import cn.qiniu.service.SysUserService;

@RestController
public class SysUserController {
    @Autowired
    private SysUserService service;
    
    /**
     *  statement：新增
     *
     * @return
     */
    @RequestMapping(value = "/sysUserAdd", method = RequestMethod.POST)
    public Object tbAdd(@RequestBody SysUserForm form) {
        return service.tbAdd(form);
    }
    
    /**
     *  statement：修改
     *
     * @return
     */
    @RequestMapping(value = "/sysUserUpdate", method = RequestMethod.POST)
    public Object tbUpdate(@RequestBody SysUserForm form) {
        return service.tbUpdate(form);
    }
    
    /**
     *  statement：修改ByPk
     *
     * @return
     */
    @RequestMapping(value = "/sysUserUpdateByPk", method = RequestMethod.POST)
    public Object tbUpdateByPk(@RequestBody SysUserForm form) {
        return service.tbUpdateByPk(form);
    }
    
    /**
     *  statement：数量
     *
     * @return
     */
    @RequestMapping(value = "/sysUserCount", method = RequestMethod.POST)
    public Object tbCount(@RequestBody SysUserForm form) {
        return service.tbCount(form);
    }
    
    /**
     *  statement：列表
     *
     * @return
     */
    @RequestMapping(value = "/sysUserList", method = RequestMethod.POST)
    public Object tbList(@RequestBody SysUserForm form) {
        return service.tbList(form);
    }
    
    /**
     *  statement：分页
     *
     * @return
     */
    @RequestMapping(value = "/sysUserPageList", method = RequestMethod.POST)
    public Object tbPageList(@RequestBody SysUserForm form) {
        return service.tbPageList(form);
    }
}
