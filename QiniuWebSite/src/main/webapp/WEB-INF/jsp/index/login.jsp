<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="cn.qiniu.util.common.Constant"%>
<%@page import="cn.qiniu.util.common.CommonUtil"%>
<html>
<%
	request.setAttribute("base", request.getContextPath());
	request.setAttribute("cssPath", request.getContextPath()
			+ "/resources/css");
	request.setAttribute("bootCssPath", request.getContextPath()
			+ "/resources/bootstrap/css");
	request.setAttribute("imgPath", request.getContextPath()
			+ "/resources/images");
	request.setAttribute("jsPath", request.getContextPath()
			+ "/resources/js");
	request.setAttribute("jqueryJsPath", request.getContextPath()
			+ "/resources/jquery");
	request.setAttribute("bootJsPath", request.getContextPath()
			+ "/resources/bootstrap/js");

	//文件服务器路径
	String fileServicePath = CommonUtil.getVcFileMgrUrl();
	//文件路径
	String filePath = fileServicePath + Constant.UPLOAD_FILE_PATH.PIC;
	int time_out = Constant.TIME_OUT;
	/* int chkcd_validity = Constant.CHKCD_VALIDITY; */
%>
<head>
<link rel="icon" href="${base}/resources/images/title.jpg"
	mce_href="${base}/resources/images/title.jpg" type="image/x-icon" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap -->
<link href="${bootCssPath}/bootstrap.css" rel="stylesheet">
<link href="${cssPath}/login.css" rel="stylesheet">
</head>
<nav class="navbar navbar-default navbar-fixed-top"
	style="height: 62px;">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<a class="navbar-brand" href="#"> 
			<%-- <img alt="Brand" src="${imgPath}/logoImg.png" style="    position: relative; top: -4px;"> --%>
			</a> <a class="navbar-brand" href="#" style="font-size: 22px; font-weight: bolder; color: #333; 
					margin-top: 6px;">视频审核系统</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
	<!-- 	<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">

			<ul class="nav navbar-nav navbar-right">
				<li><button type="submit" onclick="toRegister()"
						class="btn btn-default navbar-btn"
						style="padding:0px 22px;border-radius:4px;width:128px;height:32px;line-height:32px;font-size: 16px;color: White; background-color:#f0ad4e;border-color: #eea236;; margin-top: 14px;">企业注册</button></li>
			</ul>
		</div> -->
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>
<div class="container dd-login-content">
	<div class="col-sm-7"></div>
		<div class="col-sm-4">
			<div class="new_login_content ng-scope">
				<div class="login_content">
				    <div class="login_header">
				        <div class="login_type login_type_active">账号登录</div>
				    </div>
				    <div class="login_body">
				        <div class="login_phone">
				            <form id="formId">
				               <div class="login_input_content">
				                  <input id="phone" class="login_input" type="text" placeholder="请输入登录账号">
				               </div>
				               <div class="login_input_content">
				                  <input id="pwd" class="login_input" type="password" placeholder="请输入登录密码" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
				               </div>
				               <div class="login_input_content hide" id="sms">
				                  <input id="code" class="login_input login_input_code" type="text" placeholder="短信验证码">
				                  <div class="login_input_code_btn_content">
				                      <div class="login_button reGetCode">重新获取</div>
				                  </div>
				               </div>
				               <div class="login_input_content hide" id="imgCodeContent">
				                  <input id="imgCode" class="login_input login_input_code" style="width:50%" type="text" placeholder="图片验证码">
				                  <div class="login_input_code_btn_content" style="padding-left:10px;width:50%">
				                      <img class="img_code" src=""/>
				                      <span class="iconfont img_code_reflash">&#xe600;</span>
				                  </div>
				               </div>
				               <div class="login_input_content">
				                  <div class="login_button" id="loginBtn">登录</div>
				               </div>
				            </form>
				        </div>
				    </div>
	  			</div>
			</div>
		</div>
	<%-- <div style="margin: 0px auto;text-align: center;font-size: 12px;color: #a8a8a8;width: 100%;
				position: absolute;bottom: 0px;left:0px;background-color: #e7e8eb;padding: 10px 0px">
		<iframe name="content_frame" marginwidth=0 marginheight=0 width=100%
			height=50px src="${base}/footer.jsp" frameborder=0></iframe>
	</div> --%>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${jqueryJsPath}/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resources/jquery/jquery.tips.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${bootJsPath}/bootstrap.min.js"></script>
<script type="text/javascript" src="${jsPath}/login.js"></script>
<script type="text/javascript" src="${jsPath}/toast.js"></script>
<script type="text/javascript">
	$.basepath = "${base}";
	$.filePath = "<%=filePath%>";
	$.time_out = "<%=time_out%>";
	
</script>
</body>
</html>
