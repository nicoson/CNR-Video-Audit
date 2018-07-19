 
<%@page import="cn.qiniu.util.common.Constant"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../Header.inc"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
    <title>视频列表</title>
	<link rel="stylesheet" type="text/css" href="${base}/resources/css/videoList.css">
	<link rel="stylesheet" href="${bootCssPath}/bootstrap.min.css" />
	<link rel="stylesheet" href="${bootCssPath}/bootstrap-select.min.css"/>
	<link href="https://player.qiniucc.com/sdk/latest/qiniuplayer.min.css" rel="stylesheet">
    <script src="https://player.qiniucc.com/sdk/latest/qiniuplayer.min.js"></script>
    <script src="${bootJsPath}/bootstrap.min.js"></script>
	<script src="${bootJsPath}/bootstrap-select.min.js"></script>
	<script src="${bootJsPath}/defaults-zh_CN.min.js"></script>
	<script type="text/javascript" src="${jsPath}/video/videoList.js"></script>
	<style type="text/css">
			.glyphicon-ok:before {
		    content: '\2714';
			}
			.bootstrap-select.btn-group.show-tick .dropdown-menu li.selected a span.check-mark{
				margin-top: 0;
			}
			li{
			    border-bottom: 1px solid #999;
			}
			  
			.bootstrap-select:not([class*=col-]):not([class*=form-control]):not(.input-group-btn) {
			     width: 126px;
			}
			
			.pull-left{
				color:white;
				
			}
			.btn-default {
				background: #018DF8;
				border:none;
			}
			.btn-default.active, .btn-default:active, .btn-default:focus, .btn-default:hover, .open>.dropdown-toggle.btn-default{
			background: #018DF8;
			border:none;
			}
		</style>
		
		<script id="previewScrVideoId" type="text/x-jsrender">
			<div class="closePreview">×</div>
			<video class="previewVideo video-js vjs-big-play-centered" ></video>
		</script>
		
		<script id="engineeringTr" type="text/x-jsrender">
		<tr class="">
			<!-- 隐藏id -->
			<td class="txtColor ellipsis videoId" style="display: none;" >{{:id}}</td>
			<!-- 直播停止标识符 -->
			<td class="txtColor ellipsis liveEnd" style="display: none;" >{{:liveEnd}}</td>
			<td class="txtColor ellipsis" propkey='videoUri' style="display: none;">{{:video.uri}}</td>
			
			<!-- 视频封面图 -->
			<td class="txtColor ellipsis">
				<img class='videoImg' alt='' src="{{:video.cover}}">
			</td>
			<!-- 时长 -->
			<td class="txtColor ellipsis videoTime">{{:videoTime}}</td>
			<!-- 视频名称 -->
			<td class="txtColor ellipsis videoName">
				<div style="width:300px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;margin:auto;">
					{{:video.videoName}}
				</div>
			</td>
			<!-- 视频类型-->
			<td class="txtColor ellipsis videoType">{{:videoType}}</td>
			<!-- 机审状态 -->
			<td class="txtColor ellipsis roboticState">{{:review.roboticState}}</td>
			<!-- 机审危险等级 -->
			<td class="txtColor ellipsis roboticLevel"><img class="roboticLevelImg" src=''/><span style="padding-left: 5px;">{{:review.roboticLevel}}</span></td>
			<!-- 人审状态 -->
			<td class="txtColor ellipsis manualState"><img class="manualStateImg" src=''/><span style="padding-left: 5px;">{{:review.manualState}}</span></td>
			<!-- 创建时间 -->
			<td class="txtColor ellipsis">{{:video.createAt}}</td>
			<!-- 操作 -->
			<td class="txtColor ellipsis">
                <div class="opreateBtn txtColor" onclick="videoClosure(this)">封禁</div>
                <div class="opreateBtn txtColor" onclick="videoDel(this)">删除</div>
				<div class="opreateBtn txtColor" onclick="previewVideo(this)">预览</div>
				<div class="opreateBtn txtColor" ><a style="text-decoration:none; color: #81848C;" href="{{:video.uri}}" onclick="getMessage(this)" download="video">下载</a></div>
			</td>
		</tr>
	</script>
	
	
	</head>
	<body>
<form action= "" method="post" id="queryListForm" onsubmit="return false"  style="height: 520px;">
  	<div class="opreateBtnCon">
  			<div class="uploadBtn" id="uploadVideoBtn">上传视频</div>
  			<div class="refresh" id="refreshVideoListBtn">刷新</div>
  			<input type="hidden" id="hidPageMaxNum" name="" value=""/>
  			<input type="hidden" id="hidBucketDomain" name="" value=""/>
  			
  			<!-- <input type="hidden" id="hidPageNum"  oninput="setCurrentPage(event)" onpropertychange ="setCurrentPage(event)" name="" value="1"/> -->
  			<input type="hidden" id="hidPageSize" name="" value="5"/>
  			
  			<div class="dangerLevel">
  				<select id="dangerLevel" name="dangerLevel" class="selectCon"  multiple><!-- selectpicker -->
  				</select><!-- dangerLevel -->
  			</div>
  		</div>
  		<div class="videoTab">
  			<table id="main" cellpadding="0" cellspacing="0">
  				<tr>
  					<th>视频信息</th>
  					<th>时长</th>
  					<th>视频名称</th>
  					<th>视频类型</th>
  					<th>机审状态</th>
  					<th>机审危险等级</th>
  					<th>人审状态</th>
  					<th>创建时间</th>
  					<th>操作</th>
  				</tr>
  				<tbody id="VideoPageListBody">
  				
  				</tbody>
  			</table>
  		</div>
  		</form>
	<%-- 	<div class="page-content">
			<%@include file="../../ajaxPagination.inc"%>
		</div> --%>
		
 		<div class="page-content">
 			<div id="pager_first" class="pager_first">首页</div>
			<div id="pager_pre">上一页</div>
			<div id="page_show">&nbsp;</div><!-- 当前页/总页数 -->
			<div style="display: none;" class=""><span>第</span><span id="hidPageNum">1</span><span>页</span></div>
			<div id="pager_next" class="pager_next">下一页</div>
			<div id="pager_last" class="pager_last">尾页</div>
			<label>到第</label><input type="text" class="" id="jump_pageNumber" value="1"  /><!-- 跳转页 --><label>页</label>
			<div id="page_jump" class="">跳转</div>
		</div>
	</body>
</html>
