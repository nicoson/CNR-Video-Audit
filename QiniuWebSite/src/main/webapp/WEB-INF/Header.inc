<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="cn.qiniu.util.common.Constant"%>
<%@page import="cn.qiniu.util.common.CommonUtil"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<%
		request.setAttribute("base", request.getContextPath());
		request.setAttribute("resources", request.getContextPath()+"/resources");
		request.setAttribute("cssPath", request.getContextPath()+"/resources/css");
		request.setAttribute("bootCssPath", request.getContextPath()+"/resources/bootstrap/css");
		request.setAttribute("imgPath",request.getContextPath() + "/resources/images");
		request.setAttribute("jsPath", request.getContextPath() + "/resources/js");	
		request.setAttribute("jqueryJsPath", request.getContextPath() + "/resources/jquery");	
		request.setAttribute("bootJsPath", request.getContextPath() + "/resources/bootstrap/js");
		request.setAttribute("setUpjsPath", request.getContextPath() + "/resources/js/setUp");
		request.setAttribute("imgPath", request.getContextPath() + "/resources/images");
		//文件服务器路径
		String fileServicePath = CommonUtil.getVcFileMgrUrl();
		//文件路径
		String filePath = fileServicePath + Constant.UPLOAD_FILE_PATH.PIC;
		int time_out = Constant.TIME_OUT;
		//支付服务器路径
		String payServicePath = CommonUtil.getVcComService();
	%>
	<head>
	<link rel="icon" href="${base}/resources/images/title.jpg" mce_href="${base}/resources/images/title.jpg" type="image/x-icon" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>央广视讯播控系统</title>

		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		
	<!--	<link rel="stylesheet" href="${bootCssPath}/bootstrap.min.css" />-->
		<!--<link rel="stylesheet" href="${cssPath}/app.css" />-->
		<link rel="stylesheet" href="${cssPath}/validation.css" >
		<link rel="stylesheet" href="${cssPath}/toast.css" />
		<link rel="stylesheet" href="${cssPath}/font-awesome.min.css" />
	  
	
	  	
		<link rel="stylesheet" href="${cssPath}/commonStyle.css"/>
		<script src="${jqueryJsPath}/jquery.min.js"></script>
		<script src="${jqueryJsPath}/jquery.validate.js"></script>
		<script src="${jqueryJsPath}/jquery.metadata.js"></script>
		<script src="${jqueryJsPath}/jquery.validate.additional.js"></script>
		<script src="${jqueryJsPath}/jquery.validate.messages.js"></script>
		<script src="${jqueryJsPath}/jquery.tips.js"></script>
		<script src="${jqueryJsPath}/jsrender.min.js"></script>
		<!--<script src="${bootJsPath}/bootstrap.min.js"></script>-->
		<script src="${jsPath}/layer/layer.js"></script>
		<script src="${jsPath}/ajaxfileupload/ajaxfileupload.js"></script>
		<script src="${jqueryJsPath}/jquery.tmpl.min.js"></script>
		<script src="${jsPath}/toast.js"></script>
	
	

		<script src="${jsPath}/yunjie/yunjie.js"></script>
		
		<script type="text/javascript">
			$.basepath = "${base}";
			$.filePath = "<%= filePath%>";
			$.time_out = "<%= time_out%>";
			$.imgPath = "${imgPath}";
			$.jsPath ="jsPath";
		</script>
		
		<script type="text/javascript">
			$.qiNiu.basepath = "${base}";
			$.qiNiu.sortOpt = {
				sortColumn:null,	//排序字段
				sortVal:null,		//排序顺序
				addSortable:function() {//添加排序事件
					$(".sortable").click(function() {
						$.qiNiu.sortOpt.changeOrder(this, ".pager_div");
					}).append('<img class="_sorticon" src="${imgPath }/sortable.gif"/>');
				},
				changeOrder:function(obj, parentObj) {//改变排序
					parentObj = parentObj || ".pager_div";
					var oldSortCol = $.qiNiu.sortOpt.sortColumn;
					var oldSortVal = $.qiNiu.sortOpt.sortVal;
					var newSortCol = $(obj).attr("id");
					var newSortVal = "asc";
					var newSortImg = '${imgPath}/sortAsc.gif';
					if (newSortCol == oldSortCol) {
						newSortVal = (oldSortVal == "asc" ? "desc" : "asc");
						newSortImg = (oldSortVal == "asc" ? '${imgPath}/sortDesc.gif' : '${imgPath}/sortAsc.gif');
					}
					$.qiNiu.sortOpt.sortColumn = newSortCol;
					$.qiNiu.sortOpt.sortVal = newSortVal;
					$(obj).closest("tr").find("th img._sorticon").attr("src", '${imgPath }/sortable.gif');
					$(obj).find("img._sorticon").attr("src", newSortImg);
					$(parentObj).find(".pager_first").trigger("clickresort");
				}
			};
			
			//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
			function forbidBackSpace(e) {
				var ev = e || window.event; //获取event对象 
				var obj = ev.target || ev.srcElement; //获取事件源 
				var t = obj.type || obj.getAttribute('type'); //获取事件源类型 
				//获取作为判断条件的事件类型 
				var vReadOnly = obj.readOnly;
				var vDisabled = obj.disabled;
				//处理undefined值情况 
				vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
				vDisabled = (vDisabled == undefined) ? true : vDisabled;
				//当敲Backspace键时，事件源类型为密码或单行、多行文本的， 
				//并且readOnly属性为true或disabled属性为true的，则退格键失效 
				var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
				//当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效 
				var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";
				//判断 
				if (flag2 || flag1) return false;
	
			}
			//禁止后退键 作用于Firefox、Opera
			document.onkeypress = forbidBackSpace;
			//禁止后退键  作用于IE、Chrome
			document.onkeydown = forbidBackSpace;
		</script>
