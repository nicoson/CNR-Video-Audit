<%@page import="cn.qiniu.util.common.Constant"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<%@include file="../../Header.inc"%>
<%@include file="../../private.jsp"%>
<!DOCTYPE html>
<html>
  <head>
 
  
    <title>央广视讯播控系统</title>
   	 <link rel="stylesheet" type="text/css" href="${base}/resources/css/indexLook.css">
   	 <link href="https://player.qiniucc.com/sdk/latest/qiniuplayer.min.css" rel="stylesheet">
   	 <%-- <link rel="stylesheet" type="text/css" href="${bootCssPath}/bootstrap.min.css">
   	 <script type="text/javascript" src="${bootJsPath}/bootstrap.min.js"></script> --%>
   	 <script src="https://player.qiniucc.com/sdk/latest/qiniuplayer.min.js"></script>
   	 <script type="text/javascript" src="${jsPath}/indexLook.js"></script>
   	 <script src="https://player.qiniucc.com/sdk/latest/qiniuplayer.min.js"></script>
   	 

	<%-- <script type="text/javascript" src="${jsPath}/plupload/plupload.dev.js"></script>
	<script type="text/javascript" src="${jsPath}/plupload/moxie.min.js"></script> --%>
	
	<script type="text/javascript" src="${jsPath}/plupload/plupload.min.js"></script>
	<script type="text/javascript" src="${jsPath}/plupload/plupload.full.min.js"></script>
	<script type="text/javascript" src="${jsPath}/plupload/jqmeter.min.js"></script>
	
 	<script src="https://cdn.staticfile.org/qiniu-js-sdk/1.0.14-beta/qiniu.min.js"></script>
	
   	 <!-- <script>
		   	var DispClose = true; 
		   	function CloseEvent() 
		   	{ 
		   	if (DispClose) 
		   	{ 
		   	return "是否离开当前页面?"; 
		   	}  
		   	} 
		   	function UnLoadEvent() 
		   	{ 
		   	DispClose = false; 
		   	//在这里处理关闭页面前的动作 
		   	} 
   	 </script> -->
   	 <script>
   	 $(function(){
   	 	//实例化 Qiniu视频播放器
   	   /*   var options = {
   	    			controls : true,//  http://pili-live-hls.ps.qiniucdn.com/NIU7PS/1508383911083736890_196m2lhl7MTtskc79YGAUA.m3u8   
   					url : "http://pili-live-hls.ps.qiniucdn.com/NIU7PS/1508560243635011182_Gw4uwjqiZWZ6XylaAhQVeA.m3u8",
   					type : 'mp4',
   					preload : true,
   					autoplay : false
   	         };
   	         var player = new QiniuPlayer('previewVideoId', options); */
   	         
   	     /*     var checkInfoOptions = {
   	        		controls : true,
   					url : "http://ow8zztdmn.bkt.clouddn.com/6f1a530f5407475ea95b85a43416c681",
   					type : 'mp4',
   					preload : true,
   					autoplay : false,
   	         }
   	      var checkInfoOptionsPlay = new QiniuPlayer('videoBig', checkInfoOptions); */
   	 })
		
   	 </script>
   	 <style type="text/css">
   	 	/*七牛播放视频样式*/
		.previewVideo {
			width: 70% !important;
			height: 500px !important;
		}

		.previewVideo video {
			/* object-fit: fill; */
			height: 500px;
		}
		/*审核详情页出现播放错误**/
		#videoCon .video-damage-note{
			    color: #7EBBF7;
		}
   	 </style>
   	 <script  id="uploadFileSrcId" type="text/x-jsrender">
   		<div class="fileLine">
			<label class="fileName"></label>
			<label class="size"></label>
			<label class="fileprogress"></label>
		</div>
   	 </script>
  </head>

<!--    <body  onbeforeunload="return CloseEvent();" onunload="UnLoadEvent()">  -->
<body>
  <div id = "loginNm" class="logNu  hidden"><%=loginNm %></div>
  <div id = "userId" class="userId hidden"><%=userId%></div>
  <input type="hidden" id="roleType" value="${roleType}"/>
   	<div id="container">
   		<div id="topMenu">
   			<div class="logoImgCon ">
   					<img src="${imgPath}/logoImg.png"/>
   			</div>
   			<%-- <form action="${base}/" method="post"> --%>
   			<div class="menuNav menuSelected ">
   					<lable class="videoList" >视频列表</lable>
   			</div>
   			<!-- </form> -->
   			
	   			<div class="menuNav dangerNav">
	   				<lable class="hight">高</lable>
	   					<lable readonly  name="3" class="numberCon" id="hightCount" style="background: #E10127;" value=""></lable>
	   			</div>
   			
   			<div class="menuNav dangerNav">
   					<lable class="middle">中</lable>
   					<lable readonly   name="2" class="numberCon" id="middleCount" style="background: #FD8124;" value=""></lable>
   			</div>
   			
   		
   			<div class="menuNav dangerNav">
   					<lable class="lower">低</lable>
   					<lable readonly   name="1" class="numberCon" id="lowerCount" style="background: #D29C23;" value=""></lable>
   			</div>
   		
   		
   			<div class="menuNav ">
   					<lable class="userName">Admin</lable>
   			</div>
   			<div id="btnExit">退出</div>
   		</div>
   		<div id="menuIframeCon">
   			<!-- <div class="opreateBtnCon">
   				<div class="uploadBtn">上传视频</div>
  			<div class="refresh">刷新</div>
   			</div> -->
   			<iframe id="selectedIframe" src="" frameborder="no" scrolling="no">
			</iframe>
   		</div>
   	</div>
   	
   	
   	
   	<div id="dialogMask" class="hidden">
   		<!-- 加载 显示 -->
   		<div class="loadingCon hidden">
   			<img alt="" src="${imgPath}/loading.gif"/>
   			<span class="loadingTxt">页面加载中...</span>
   		</div>
  		<!--遮罩层里的东西动态生成  -->
   		<div class="uploadCon hidden">
   			<div class="uploadListTxt">上传视频</div>
   		<!-- 	<div class="" id="uploadFileContainer" style="position: relative;"> -->
   				<!-- <a href="#"  id="pickfiles" style="width: 40%;display: inline-block;height:120px;">
   					<i class="fa fa-cloud-upload fa-4x uploadPic" aria-hidden="true"></i>
   					<label class="tipTxt">点击选择文件上传</label>
   				</a> -->
   					<!-- <i class="fa fa-cloud-upload fa-4x uploadPic" aria-hidden="true"></i>
   					<div class="uploadWayCon" ><label class="tipTxt">点击选择文件上传</label><input class="fileInput" id="pickfiles" value="" type="file" name=""></div>
   					<div class="uploadOtherWay">或者将音视频拖拽到此处上传</div> -->
   				<div class="uploadBtn"  style="position: relative;"><!-- 添加文件按钮 -->
   					<div id="uploadFileListCon" class="uploadFileListCon">
   						<!-- <div class="fileLine">
   							<label class="fileName">1.mp4</label>
   							<label class="size">100kb</label>
   							<label class="fileprogress">0%</label>
   						</div> -->
   						
   						
   					</div>
   					<div id="uploadFileBtn">
   						<a href="#"  id="pickfiles"style="display: inline-block;"><label class="addFileTxt">添加文件</label></a>
   					</div>
   				</div>
   			<!-- </div> -->
   			<!-- <div id="uploadFileConDesc" style="display:none">
   				<span>文件名：</span><span class="fileName"></span>
   				<span>文件大小：</span><span class="size"></span>
   				<span>上传进度：</span><span class="progressPrengent"></span>
   				<div id="jqmeter-container" style="    display: inline-block; height: 38px;">
   				</div>
   			</div> -->
   			<div class="inputCon">
   					<input class="user_inputVideoName" maxlength="50" placeholder="请输入直播视频名称" type="text"/>
   					<br/>
   					<br/>
   					<input class="user_input" placeholder="请输入直播链接" type="text"/>
   					
   					
   			</div>
   			<div class="btnCon">
   				<div class="btn" id="cancel">关闭</div>
   				<div class="btn" id="sure">完成</div>
   			</div>
   			
   		</div>
   		
   		<!-- 点击审核按钮 -->
   		<div class="singleVideoAuditCon hidden" name=""><!-- playing 直播标志 -->
   				<!--导航栏 start  -->
   					<div class="nav_video">
   						<div id ="manual_check" class="video_navTitle selected_nav">人工审核</div>
   						<div id="playing_video" class="video_navTitle hidden">直播流</div>
   						<div class="closeaAudit">×</div>
   						<div class="bottom_underLine"></div>
   					</div>
   					<!-- 导航栏  end -->
   				<div id="videoCon" class="videoConDefault "><!-- selectedTimeCutStyle -->
   					
   						<!-- <video id="videoBig"  class="videoBig video-js vjs-big-play-centered"  src="" >
						 
						</video> -->
						
   				</div>
   				<div id="auditOperateCon" class="auditOperateConDefault "><!-- selectedTimeCutStyle -->
   					<div class="auditConten">
   						<!-- <div class="contenLine">
   							<input class="multipleBox" id="politicalAffairs" type="checkbox" value="politician_0" />
   							<label class="contentTxt" id="testtext" for="politicalAffairs" >涉政事件</label>
   						</div> -->
   						<div class="contenLine">
   							<input class="multipleBox" id="politiciansInvolved" type="checkbox" value="politician_1" />
   							<label class="contentTxt" for="politiciansInvolved" >涉政人物</label>
   						</div>
   						<div class="contenLine">
   							<input class="multipleBox" id="fearViolence" type="checkbox" value="terror_1" />
   							<label class="contentTxt" for="fearViolence" >暴恐</label>
   						</div>
   						<div class="contenLine" >
   							<input class="multipleBox" id="pornographic" type="checkbox" value="pulp_0" />
   							<label class="contentTxt" for="pornographic" >色情</label>
   						</div>
   						<div class="contenLine">
   							<input class="multipleBox" id="sex" type="checkbox" value="pulp_1" />
   							<label class="contentTxt" for="sex">性感</label>
   						</div>
   						<div class="auditBtnCon">
   								<div id="cancelCheckBtn" class="auditBtn">取消</div>
   								<div id= "sureCheckBtn" class="auditBtn">确定</div>	
   						</div>
   					</div>
   					<div id="videoTxtCon" class="videoTxtConDefault">
   						<div class="videoTxtLine">
   								<div class="videoTxtTitle">机审不合格截帧列表</div>
   							<!-- 	<div class="closeaAudit">×</div> -->
   						</div>
   						<div class="videoTxtLine">
   								<div class="videoTimeTxt">截帧时刻</div>
   								<div class="peopleAudits">是否已人审</div>
   						</div>
   						<div class="videoTimeCon">
   							<%-- <div class="videoTimeLine">
   								<span class="videoTime">00:02:56</span>
   								<img class="opreateTip" src="${imgPath}/wrong.png" />
   							</div>
   							<div class="videoTimeLine">
   								<span class="videoTime">00:34:56</span>
   								<img class="opreateTip" src="${imgPath}/wrong.png" />
   							</div>
   							<div class="videoTimeLine">
   								<span class="videoTime">01:50:56</span>
   								<img class="opreateTip" src="${imgPath}/finished.png" />
   							</div>
   							<div class="videoTimeLine">
   								<span class="videoTime">00:12:56</span>
   								<img class="opreateTip" src="${imgPath}/wrong.png" />
   							</div>
   							<div class="videoTimeLine">
   								<span class="videoTime">01:34:56</span>
   								<img class="opreateTip" src="${imgPath}/finished.png" />
   							</div> --%>
   						</div>
   						<div class="closure_video btn_offset" onclick="videoClosure(this)">封禁</div>
   						<div id="auditFinished" class="auditFinished btn_offset">审核完成</div>
   					</div>
   				</div>
   		</div>
   		
   	
   		<!-- 点击预览视频 -->
   		<div id="previewVideoConId" class="previewVideoCon hidden ">
   			<!-- <div class="closePreview">×</div>
   			<video id="" class="previewVideo video-js vjs-big-play-centered" ></video> -->
   		</div>
   	</div>
   	
   	
   	<script type="text/javascript">
   	$.basePath="${base}";
   	$.imgPath="${imgPath}";
   	</script>
  </body>
</html>
