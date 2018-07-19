package cn.qiniu.controller.index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.qiniu.controller.common.BaseController;
import cn.qiniu.entity.SysUser;
import cn.qiniu.entity.basic.AjaxJson;
import cn.qiniu.service.video.VideoListService;
import cn.qiniu.util.common.MD5Util;
import cn.qiniu.util.common.StringUtil;

/**
 * 〈后台核心类〉 〈功能详细描述〉
 * 
 * @author lvweijun
 * @version V1.00 2016-10-18
 */
@Controller
public class IndexController extends BaseController {
	
	@Autowired
    private VideoListService videoListService;
    /**
     * 显示登录页面
     */
	@RequestMapping(value = "/index/welcome", method = RequestMethod.GET)
	public ModelAndView welcomePage(HttpServletRequest requeset, HttpServletResponse response, ModelMap modelMap) {
		return new ModelAndView("index/login", modelMap);
	}
	
    /**
     * 显示登录页面
     */
	@RequestMapping(value = "/index/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest requeseoot, HttpServletResponse response, ModelMap modelMap) throws Exception {
		/*return new ModelAndView("index/index", modelMap);*/
		//获取当前登录人
		SysUser user = getLoginUser();
		//获取当前登录人权限
		String roleType = videoListService.getRoleType(user);
		modelMap.put("roleType", roleType);
		return new ModelAndView("video/indexLook", modelMap);
	}
	
    /**
     * 用户登录
     * 生成用户session信息（包括用户权限信息）
     */
	@ResponseBody
	@RequestMapping(value = "/index/doLogin")
	public AjaxJson login(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		//获取登录用户名
		String loginNm = request.getParameter("loginNm");
		//获取登录密码
//		String password = request.getParameter("password");
		String password = MD5Util.MD5(request.getParameter("password"));
		//shiro登录
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(loginNm, password.toCharArray());
		subject.login(token);
		//获取用户信息（包括用户权限信息）
		SysUser user = getLoginUser();
		if(user == null || StringUtil.isNullOrEmpty(user.getUserId())) {
			//跳转到登录页面
			j.setMsg("用户名或密码错误，请重新输入");
			j.setSuccess(false);
			return j;
		} else {
			//将登录成功的用户信息存在session
			// 获取session
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			return j;
		}
	}
	
    /**
     * 用户退出
     */
	@RequestMapping(value = "/index/loginOut")
	public ModelAndView loginOut(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		if (SecurityUtils.getSubject().isAuthenticated()) {
			SecurityUtils.getSubject().logout();
		}
		return new ModelAndView("redirect:welcome.do");
	}
	
	/**
     * 用户无权限
     */
	@RequestMapping(value = "/index/unauthorized")
	public ModelAndView unauthorized(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return new ModelAndView("common/unauthorized", modelMap);
	}
}
