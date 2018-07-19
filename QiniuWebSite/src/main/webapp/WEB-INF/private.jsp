<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"%>
<%@ page language="java" import="cn.qiniu.entity.SysUser" pageEncoding="UTF-8"%>
<%
	String userId = "";
	String loginNm ="";
	SysUser user = (SysUser) session.getAttribute("user");

	if (user != null) {
		userId = user.getUserId();
		loginNm = user.getLoginNm();
	}
%>