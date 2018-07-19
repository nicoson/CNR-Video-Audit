package cn.qiniu.filter.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qiniu.delegator.SysResourceDelegator;
import cn.qiniu.delegator.SysUserDelegator;
import cn.qiniu.entity.SysResource;
import cn.qiniu.entity.SysResourceSearch;
import cn.qiniu.entity.SysUser;
import cn.qiniu.entity.SysUserSearch;
import cn.qiniu.form.SysResourceForm;
import cn.qiniu.form.SysUserForm;
import cn.qiniu.util.common.Constant;

public class MyRealm extends AuthorizingRealm {
	@Autowired
	private SysUserDelegator userDelegator;
	@Autowired
	private SysResourceDelegator resourceDelegator;
	
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		SysUser user = (SysUser) SecurityUtils.getSubject().getSession().getAttribute("user");
		Set<String> permSet = new HashSet<String>();
		permSet.addAll(user.getPermissionList());
		authorizationInfo.setStringPermissions(permSet);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		if (SecurityUtils.getSubject().isAuthenticated()) {
			SecurityUtils.getSubject().logout();
		}
		/* 这里编写认证代码 */
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		/**用户id 组织id**/
		SysUser user = getUserInfo(token.getUsername(), new String(token.getPassword()));
		if (user != null) {
			SecurityUtils.getSubject().getSession().setAttribute("user", user);
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
		return info;
	}
	
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}
	
	/**
     * 验证用户登录信息
     * 用户登录验证通过后获取用户权限信息
     * @param loginNm 登录名
     * @param password 登录密码
     * @return OpUser 用户信息（包括用户权限信息）
     */
    public SysUser getUserInfo(String loginNm, String password) {
		SysUserForm userForm = new SysUserForm();
		SysUserSearch userSearch = new SysUserSearch();
		userSearch.setLoginNm(loginNm);
		userSearch.setPassword(password);
		userForm.setSearch(userSearch);
		
        //取得用户信息
		SysUser user = userDelegator.select(userForm);
		
        //获取功能权限集合
        SysResourceSearch resoureSearch = new SysResourceSearch();
        resoureSearch.setDelFlg(Constant.DEL_FLG.DEL_FLG_0);
        SysResourceForm resourceForm = new SysResourceForm();
        if (user != null && Constant.STAFF_ADMIN_ID.equals(user.getUserId())) {
            //如果是管理员登录，则取得全部菜单
        	resourceForm.setSearch(resoureSearch);
            List<SysResource> resourceList = resourceDelegator.tbList(resourceForm);
            user.setMenuList(resourceList);
        } else if (user != null) {
            //获取功能权限集合
        	resoureSearch.setResourceId(user.getUserId());
        	resourceForm.setSearch(resoureSearch);
            List<SysResource> userRes = resourceDelegator.getUserMenuList(resourceForm);
            user.setMenuList(userRes);
        }
        
        if (user != null && user.getMenuList() != null && user.getMenuList().size() > 0) {
            //按父节点及sortno排序
            Collections.sort(user.getMenuList(), new Comparator<SysResource>(){
                public int compare(SysResource p1, SysResource p2) {
                    if (p1 == null) {
                        return 1;
                    }
                    if (p2 == null) {
                        return -1;
                    }
                    if (StringUtils.isEmpty(p1.getParentResourceId()) && StringUtils.isEmpty(p2.getParentResourceId()) ) {
                        return p1.getSortNo().compareTo(p2.getSortNo());
                    }
                    if (StringUtils.isEmpty(p1.getParentResourceId()) ) {
                        return -1;
                    }
                    if (StringUtils.isEmpty(p2.getParentResourceId()) ) {
                        return 1;
                    }
                    return p1.getSortNo().compareTo(p2.getSortNo());
                }
            });
            
            //设置权限集合
            List<String> permissionList = new ArrayList<String>();
            for (int i = 0; i < user.getMenuList().size(); i++) {
                if (!permissionList.contains(user.getMenuList().get(i).getResourceIdent())) {
                    permissionList.add(user.getMenuList().get(i).getResourceIdent());
                }
            }
            user.setPermissionList(permissionList);
        }
        
        return user;
    }
}
