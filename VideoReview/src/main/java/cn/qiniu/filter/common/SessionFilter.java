package cn.qiniu.filter.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 〈登录过滤〉
 * 〈功能详细描述〉
 * @author	FELIX
 * @version   V1.00 2015-11-5 [版本号, YYYY-MM-DD]
 * @see	   [相关类/方法]
 * @since	 kindMed V1.0R001 [金域文控管理系统/COMMON模块]
 */
public class SessionFilter extends OncePerRequestFilter {  
	@Override  
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 不过滤的uri
		String[] notFilter = new String[] {
				"/index/loginOut.do",
				"/check/checkVideo.do",
				"/check/checkVideoInReview",
				"/video/submitVideo.do",
				"/video/getCountByDangerLevel.do",
				"/video/videoClosure.do",
				"/video/videoDelete.do",
				"/checkInfoList.do",
				"/video/showCheckInfoPageListDate.do",
				"/video/videoPageList.do",
				"showCheckInfoList.do",
				"video/videoPageListData.do",
				"checkUser.do",
				"registerPe.do",
				"login.do", 
				"doLogin.do", 
				"unauthorized.do",
				"/v1/jobs.do",
				"/job/{jobId}/result.do",
				"getCheckCode.do",
				"checkCode.do","coPasswordReset.do",
				"/check/videoManualFinish.do",
				"/check/videoManualFinishByPage.do",
				"/index/getDictionaryItemsByCode.do"};
		// 请求的uri
		String uri = request.getRequestURI();
		// 是否过滤
		boolean doFilter = true;
		for (String s : notFilter) {
			if (uri.indexOf(s) != -1) {
				// 如果uri中包含不过滤的uri，则不进行过滤
				doFilter = false;
				break;
			}
		}
		String contextPath = request.getContextPath();
		if (doFilter && !uri.equals(contextPath) && !uri.equals(contextPath + "/")) {
			// 执行过滤
			// 从session中获取登录者实体
			Object obj = null;
			if (SecurityUtils.getSubject().isAuthenticated()) {
				obj = SecurityUtils.getSubject().getSession().getAttribute("user");
			}
			if (null == obj) {
				// 如果session中不存在登录者实体，转向loginOut.do
				String loginPage = request.getContextPath();
				if (!loginPage.endsWith("/")) {
					loginPage += "/";
				}
				
				// 设置request和response的字符集，防止乱码  
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				StringBuilder builder = new StringBuilder();
				builder.append("<script type=\"text/javascript\">");
				//builder.append("alert('网页过期，请重新登录！');");
				builder.append("window.top.location.href='");
				builder.append(loginPage);
				builder.append("';");
				builder.append("</script>");
				out.print(builder.toString());
				
			} else {
				// 如果session中存在登录者实体，则继续
				filterChain.doFilter(request, response);
			}
		} else {
			// 如果不执行过滤，则继续
			filterChain.doFilter(request, response);
		}
	}
}