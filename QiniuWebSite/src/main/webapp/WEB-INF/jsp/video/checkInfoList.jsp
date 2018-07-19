<!doctype html>
<%@page import="cn.qiniu.util.common.Constant"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../Header.inc"%>
<html>
  <head>
  <meta charset="utf-8">
		<title>人工审核视频列表</title>
      <link rel="stylesheet" type="text/css" href="${base}/resources/css/checkInfoList.css">
	  <link rel="stylesheet" href="${base}/resources/css/font-awesome.min.css" type="text/css"></link>
	  <link href="https://player.qiniucc.com/sdk/latest/qiniuplayer.min.css" rel="stylesheet">
   	  <script src="https://player.qiniucc.com/sdk/latest/qiniuplayer.min.js"></script>
	  <script type="text/javascript" src="${jsPath}/checkInfoList.js"></script>
	 <%--  <script type="text/javascript" src="${jsPath}/video/videoControls.js"></script> --%>
	 
	 <script id="checkInfoScrVideoId" type="text/x-jsrender">
			<video id=""  class="videoBig video-js vjs-big-play-centered"  src="" >
			</video>
			<!--<div class="aduitShowError hidden">视频播放错误，请稍后...</div>-->
		</script>
	 
	  <script id="checkVideoList" type="text/x-jsrender">
			<div class="auditVideoList">
					<div class="videoSub">
		   					<video id="{{:id}}" class="video-js vjs-big-play-centered videoStyle" style="    height: 200px!important;" >
							</video>
							<input type="hidden"  id="videoType" value="{{:videoType}}"/>
							<input type="hidden" class="videoId" id="hidId" value="{{:id}}"/>
							<input type="hidden" class="videoUri" id="hidId" value="{{:videoUri}}"/>
							<div class="auditBtn" onclick="videoCheckDialog(this)" >审核</div>	
							<div class="remove" onclick="videoDel(this)">×</div>		
			   				<div class="videoDialogMask hidden">
			   					<div class="cancelAuditTip ">
			   						<div class="tipsTxt">审核结束？</div>
			   						<div class="btnCon">
			   							<div class="btn" id="no">否</div>
			   							<div class="btn" id="yes">是</div>
			   						</div>
			   					</div>
			   				</div>
							
							<div class="errorDialogMask hidden">
								<!--视频播放错误提示  start-->
								<div class="errorTip">
			   							视频播放错误，请稍后...
			   					</div>
								<!--视频播放错误提示  end-->
			   				</div>
							
		   			</div>
			</div>	
	</script>
	
	 <script id="videoTimeList" type="text/x-jsrender">
			<div class="videoTimeLine">
				<span id="" name="segmentsId" propkey="id" class="segmentsId" style="display: none;">{{:id}}</span>
				<span id="" name="segmentsOp" propkey="op" class="segmentsOp" style="display: none;">{{:op}}</span>
				<span id="" name="segmentsUri" propkey="uri" class="segmentsUri" style="display: none;">{{:uri}}</span>
				<span id="" name="segmentsLevel" propkey="level" class="segmentsLevel" style="display: none;">{{:level}}</span>
				<span id="" name="segmentsManualLevel" propkey="manualLevel" class="segmentsManualLevel" style="display: none;">{{:manualLevel}}</span>
				<span id="" name="offsetBegin" propkey="offsetBegin" class="offsetBegin" style="display: none;">{{:offsetBegin}}</span>
				<span id="" name="offsetEnd" propkey="offsetEnd" class="offsetEnd" style="display: none;">{{:offsetEnd}}</span>
				<span id="" name="videoId" propkey="videoId" class="videoId" style="display: none;">{{:videoId}}</span>
				<label class="videoTime">{{:offsetBeginStr}}~{{:offsetEndStr}}</label>
				<img class="opreateTip" src="${imgPath}/wrong.png" />
			</div>

	</script>
  </head>
  
  <body>
 
  <form action="" method="post" id="queryListForm" onsubmit="return false" style="height:525px;margin-bottom: 5px;">
  <input id="dangerLevel" name="dangerLevel" type="hidden" value="${search.dangerLevel}"/>
  	<div class="manualAuditCon">
   		<div class="videoListOpreateCon">
   			<div class="videoNumCon hidden">
   				<span class="videoNum minNum">2*3</span>
   				<span class="videoNum">3*3</span>
   				<span class="videoNum maxNum">4*3</span>
   				<span class=" dot-right"></span>
   			</div>
   			<!--  -->
   			<!-- <div class="refresh" id="refresh">刷新</div> -->
   			<!-- <i class="fa fa-bars fa-2x selecteShowNum" aria-hidden="true"></i> -->
   		</div>
   		
   		<div class="auditVideoListCon" id="auditVideoListConID" >
   			
   		</div>
   		</div>
   	 </form>
   		<!-- 分页代码 -->
   		<%-- <div class="page-content">
			<%@include file="../../ajaxPagination.inc"%>
		</div> --%>
   
  </body>
</html>
