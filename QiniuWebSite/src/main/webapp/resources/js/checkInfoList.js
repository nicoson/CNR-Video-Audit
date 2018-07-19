$(function(){
	//获取分页数据
	getPageDate();
	//点击选择 显示的模式
/*	$(".selecteShowNum").click(function(){
		//判断 $(".videoNumCon") 是否有 hidden样式
		if($(".videoNumCon").hasClass("hidden")){
			$(".videoNumCon").removeClass("hidden");
		}else{
			$(".videoNumCon").addClass("hidden");
		}
	});*/
	//选择 show的模式
	$(".videoNum").click(function(){
		$(this).css("background","#4F9ADC");
		$(this).siblings().css("background","");
	});
	
	//【刷新】按钮点击事件
	$("#refresh").click(function(){
		//获取分页数据
		getPageDate();
	});
	
	//向后台发起请求  每隔5s向后台发起请求
//	 var timeReq = setInterval(requestVideoCheck,5000);
	 
});//end
/**分页数据**/
function getPageDate(){
	var dangerLevel = $("#dangerLevel").val();
	$.ajax({
		url: $.basepath + "/video/showCheckInfoPageListDate.do?dangerLevel="+dangerLevel,
		method:"post",
		dataType:"json",
		success:function(result, status) {
			if (result !=null && result.success) {
				//绑定页面数据
				var htmlContent = $("#checkVideoList").render(result.body.checkVideoList);
				$("#auditVideoListConID").html(htmlContent);
				var checkVideoListStr = JSON.stringify(result.body.checkVideoList);
				var checkVideoListArray = JSON.parse(checkVideoListStr);
				$.each(checkVideoListArray,function(i,checkVideo){
					var videoId = checkVideo.id;
					//var videoUri = checkVideo.videoUri;
					
					var videoUri = encodeURI(checkVideo.videoUri); 
					//实例化 Qiniu视频播放器
			   	     var options = {
			   	    			controls : true,
			   					url : videoUri,
			   					type : 'mp4',
			   					preload : true,
			   					autoplay : true
			   	         };
			   	         var player = new QiniuPlayer(videoId, options);
			   	       //页面的  错误视频连接  弹出蒙板 
			   	     /* $(".auditVideoList").each(function(i,el){
			   		    var error = 	$(el).find(".video-damage-note").length;
			   			if(error>0){
			   				$(".errorDialogMask",el).removeClass("hidden");
			   			}
			   			
			   	      });*/
			   	         
			   		  
			   	 
				});
				
				
					
			}else{
				
			}
		}
	});
	
	
	
}
/**
 * 获取视频的错误
 */
function findVideoError(){
	$(".auditVideoList").each(function(index,el){
		var length = 	$(el).find("video").length;
	    var error = 	$(el).find(".video-damage-note").length;
		console.log("video:"+length);
		console.log("error:"+error)
		})
}

/**关闭审核视频**/
function videoDel(obj){
	$(obj).next().removeClass("hidden");
	//点击 否
	$(obj).next().find("#no").click(function(){
		$(obj).next().addClass("hidden");
	});
	//点击  是
	$(obj).next().find("#yes").click(function(){
		//视频数量-1
		$(".menuSelected",window.parent.document).find(".numberCon").text($(".menuSelected",window.parent.document).find(".numberCon").text()-1);
		//移除要结束审核的vide
		$(obj).next().parent().parent().remove();
		var videoId = $(this).parent().parent().parent().parent().find(".videoId").val();
		//发送请求更新数据
		$.ajax({
			async:false,
			url: $.basepath + "/check/videoManualFinishByPage.do?videoId="+videoId,
			method:"post",
			success:function(result, status) {
				if (result !=null && result.success) {
					
					top.layer.alert("视频审核成功!");
				}else{
					top.layer.alert("视频审核失败!");
				}
			}
		});
		
		
	});
}


//点击【审核】 --点播/直播
function videoCheckDialog(obj){
	
	//获取 indexLook 页面遮罩层的内容
	var parentDialog = 	$('#dialogMask',window.parent.document);
	parentDialog.removeClass("hidden");
	var singleVideoAuditCon = $(".singleVideoAuditCon",parentDialog);
	
	var videoCon = $(singleVideoAuditCon).find("#videoCon");
	//重置复选框
	 $('.auditConten',window.parent.document).find('input[type=checkbox]').attr('checked',false);
	//先清空
	$(videoCon).html('');
	//动态设置  父级页面的 审核视频的video
	videoCon.html($("#checkInfoScrVideoId").html());
	//"video" + new Date().getTime();
//	var videoBigId = $(obj).parent().find(".videoId").val();
	var videoBigId = "video" + new Date().getTime();
	var videoBig = $(videoCon).find(".videoBig").attr("id",videoBigId);
	
	//父级页面上的审核视频的video id
//	var videoBigId  = singleVideoAuditCon.find("#videoBig").attr("id");
	
	singleVideoAuditCon.removeClass("hidden");
	//现将直播流的nav  隐藏
	$("#playing_video",singleVideoAuditCon).addClass("hidden");
	//获取视频id
	var videoId = $(obj).parent().find("#hidId").val();
	
	$.ajax({
		async:false,
		url: $.basepath + "/check/checkVideo.do?videoId="+videoId,
		method:"post",
//        data: videoId,
		dataType:"json",
		success:function(result, status) {
			if (result !=null && result.success) {
				//json转数组
				var videoSegements=eval(result.body.info.videoSegements); 
				//加载截帧时间段数据
				var videoTimeList = $("#videoTimeList").render(videoSegements);
				$('.videoTimeCon',window.parent.document).html(videoTimeList);
				
			}else{
			}
		}
	});
	//获取videoUrl
	var videoUrl = $(obj).parent().find(".videoUri").val();
	
	
	//判断是点播还是直播  playing==直播  2017-10-11 凌琼芳 add   start
	//获取video类型，点播还是直播
	var videoType = $(obj).parent().find("#videoType").val();
	if("2"==videoType){//直播
		//显示直播流nav
		$("#playing_video",singleVideoAuditCon).removeClass("hidden");
		//点击直播流 nav
		$("#playing_video",singleVideoAuditCon).click(function(){
			//设置  直播流的  url
			//先清空
			$(videoCon).html('');
			//动态设置  父级页面的 审核视频的video
			videoCon.html($("#checkInfoScrVideoId").html());
			var videoLiveBigId = "video" + new Date().getTime();
			var videoLiveBig = $(videoCon).find(".videoBig").attr("id",videoLiveBigId);
			var  videoLiveurl = videoUrl;
			//设置 直播流url
			parent.newQiniuVideo(videoLiveBigId,videoLiveurl,"auditShow");
			$(this).addClass("selected_nav");
			$(this).prev().removeClass("selected_nav");
			//隐藏右侧的时间截帧
			$("#auditOperateCon",singleVideoAuditCon).addClass("hidden");
			//视频宽度
			$("#videoCon",singleVideoAuditCon).addClass("videoBig");
			
		});
		//点击人工审核 nav
		 $("#manual_check",singleVideoAuditCon).click(function(){
			//默认第一个  时间帧被选中
			 $(".videoTimeLine",singleVideoAuditCon).eq(0).click(); 
			 $(this).addClass("selected_nav");
			 $(this).next().removeClass("selected_nav");
			//隐藏右侧的时间截帧
				$("#auditOperateCon",singleVideoAuditCon).removeClass("hidden");
				$("#videoCon",singleVideoAuditCon).removeClass("videoBig");
				//设置视频的宽度
				/*$(".videoConDefault",singleVideoAuditCon).css("width","70%");*/
		 });
	}
	// 2017-10-11 凌琼芳 add  end
	//设置审核详情页的视频地址
	// $(".videoBig",singleVideoAuditCon).find("video").attr("src",videoUrl);
	 parent.newQiniuVideo(videoBigId,videoUrl,"auditShow");
	 //点击  x 
	 $(".closeaAudit",singleVideoAuditCon).click(function(){
		 parentDialog.addClass("hidden");
		 singleVideoAuditCon.addClass("hidden");
		 //把视频的url  清空
		 $(".videoBig",singleVideoAuditCon).find("video").attr("src","");
		 //复位
		 $("#videoCon",singleVideoAuditCon).removeClass("videoBig");
		 $("#videoCon",singleVideoAuditCon).removeClass("videoCon").addClass("videoConDefault");
		 $("#auditOperateCon",singleVideoAuditCon).removeClass("auditOperateCon").addClass("auditOperateConDefault");
		 $("#auditOperateCon",singleVideoAuditCon).removeClass("hidden");
		 $("#videoTxtCon",singleVideoAuditCon).removeClass("videoTxtCon").addClass("videoTxtConDefault");
		 //将隐藏的审核结果复选框隐藏
		// $(".auditConten",singleVideoAuditCon).addClass("hidden");
		// 
		 $("#playing_video",singleVideoAuditCon).removeClass("selected_nav");
		 $("#manual_check",singleVideoAuditCon).addClass("selected_nav");
	 });
	 
	 // TODO
	//点击 取消
	  $("#cancelCheckBtn",window.parent.document).unbind("click").click(function(){
		  $("#videoCon",singleVideoAuditCon).removeClass("videoCon").addClass("videoConDefault");
			 $("#auditOperateCon",singleVideoAuditCon).removeClass("auditOperateCon").addClass("auditOperateConDefault");
			 
			 $("#videoTxtCon",singleVideoAuditCon).removeClass("videoTxtConDefault").addClass("videoTxtCon");
			 //将隐藏的审核结果复选框
			 $(".auditConten",singleVideoAuditCon).addClass("hidden");
			 $(".videoTimeSelected",singleVideoAuditCon).removeClass("videoTimeSelected");
			 
			//重置复选框
		 $('.auditConten',window.parent.document).find('input[type=checkbox]').attr('checked',false);
	 });
	 // 点击  确定  
	 $("#sureCheckBtn",window.parent.document).unbind("click").click(function(){
		 
		 //选中项数据
		 var checkedVal="";
		 //根据勾选项更改时间帧绑定数据
		 $.each($('input:checkbox:checked',window.parent.document),function(){
			 checkedVal = $(this).val();
		 });
		 
		 //获取选中的时间帧
		 var timeChecked="";
		 jQuery(".videoTimeLine",window.parent.document).each(function(){
			 var size = $(this).attr("class").indexOf("videoTimeSelected");
			 if(size != (-1)){
				 timeChecked = $(this);
			}
			
		 }); 
		//页面勾选项，设置op和label
		 switch(checkedVal){
		 	//危险等级-无(通过)
		 	case "":
		 		//根据op设置label(正常)
		 		if("pulp_beta" == timeChecked.find(".segmentsOp").text()){
		 			//设置人审等级
			 		timeChecked.find(".segmentsManualLevel").text(2);
			 		//设置最终审核登记
			 		timeChecked.find(".segmentsLevel").text(2);
		 		}
		 		if("terror" == timeChecked.find(".segmentsOp").text()){
		 			//设置人审等级
			 		timeChecked.find(".segmentsManualLevel").text(0);
			 		//设置最终审核登记
			 		timeChecked.find(".segmentsLevel").text(0);
		 		}
		 		if("face_group_search" == timeChecked.find(".segmentsOp").text()){
		 			//设置人审等级
			 		timeChecked.find(".segmentsManualLevel").text(0);
			 		//设置最终审核登记
			 		timeChecked.find(".segmentsLevel").text(0);
		 		}
		 		
		 		break;
		 	//危险等级-无(通过)	
		 	case undefined:
		 		//根据op设置label(正常)
		 		if("pulp_beta" == timeChecked.find(".segmentsOp").text()){
		 			//设置人审等级
			 		timeChecked.find(".segmentsManualLevel").text(2);
			 		//设置最终审核登记
			 		timeChecked.find(".segmentsLevel").text(2);
		 		}
		 		if("terror" == timeChecked.find(".segmentsOp").text()){
		 			//设置人审等级
			 		timeChecked.find(".segmentsManualLevel").text(0);
			 		//设置最终审核登记
			 		timeChecked.find(".segmentsLevel").text(0);
		 		}
		 		if("face_group_search" == timeChecked.find(".segmentsOp").text()){
		 			//设置人审等级
			 		timeChecked.find(".segmentsManualLevel").text(0);
			 		//设置最终审核登记
			 		timeChecked.find(".segmentsLevel").text(0);
		 		}
		 		break;
		 	//暴恐
		 	case "terror_1":
		 		//设置审核项目
		 		timeChecked.find(".segmentsOp").text("terror");
		 		//设置人审等级
		 		timeChecked.find(".segmentsManualLevel").text(1);
		 		//设置最终审核登记
		 		timeChecked.find(".segmentsLevel").text(1);
		 		break;
	 		//涉政事件
		 	case "politician_1":
		 		//设置审核项目
		 		timeChecked.find(".segmentsOp").text("face_group_search");
		 		//设置人审等级
		 		timeChecked.find(".segmentsManualLevel").text(1);
		 		//设置最终审核登记
		 		timeChecked.find(".segmentsLevel").text(1);
		 		break;
		 	//涉政人物
			case "politician_0":
				//设置审核项目
		 		timeChecked.find(".segmentsOp").text("face_group_search");
		 		//设置人审等级
		 		timeChecked.find(".segmentsManualLevel").text(0);
		 		//设置最终审核登记
		 		timeChecked.find(".segmentsLevel").text(0);
		 		break;
		 	//色情
			case "pulp_0":
				//设置审核项目
		 		timeChecked.find(".segmentsOp").text("pulp_beta");
		 		//设置人审等级
		 		timeChecked.find(".segmentsManualLevel").text(0);
		 		//设置最终审核登记
		 		timeChecked.find(".segmentsLevel").text(0);
		 		break;
		 	//性感
			case "pulp_1":
				//设置审核项目
		 		timeChecked.find(".segmentsOp").text("pulp_beta");
		 		//设置人审等级
		 		timeChecked.find(".segmentsManualLevel").text(1);
		 		//设置最终审核登记
		 		timeChecked.find(".segmentsLevel").text(1);
		 		break;	
		 }
		 
		 //获取选中截帧时刻数据
		 var checkedData = JSON.stringify(getVideoTimeCheckedData());
		 //发送请求，修改此条数据
		 $.ajax({
				url: $.basepath + "/check/checkVideoInReview.do",
				data:{
					checkedData : checkedData
					},
				method:"post",
				dataType:"json",
				success:function(result, status) {
					if (result !=null && result.success) {
						top.layer.alert("截帧时间段审核成功!");
					}else{
						top.layer.alert("截帧时间段审核失败!");
					}
				}
			});
		 
		 $("#videoCon",singleVideoAuditCon).removeClass("videoCon").addClass("videoConDefault");
		 $("#auditOperateCon",singleVideoAuditCon).removeClass("auditOperateCon").addClass("auditOperateConDefault");
		 $("#videoTxtCon",singleVideoAuditCon).removeClass("videoTxtConDefault").addClass("videoTxtCon");
		 //将隐藏的审核结果复选框隐藏
		 $(".auditConten",singleVideoAuditCon).addClass("hidden");
		 //是否人审  的图片  换成  勾
		 $(".videoTimeSelected",singleVideoAuditCon).removeClass("videoTimeSelected").find(".opreateTip").attr("src",$.imgPath+"/ok2.png");
		 
	 });
	//审核完成 
	 $("#auditFinished",window.parent.document).unbind("click").click(function(){
		 var jsonData = getVideoTimeConData();
		 //审核的视频id
		 var videoId = jsonData[0].videoId;
		 //获取页面所有数据
		 var videoTimeData = JSON.stringify(jsonData);
		 $.ajax({
				url: $.basepath + "/check/videoManualFinish.do",
				data:{
					videoTimeData : videoTimeData
					},
				method:"post",
				dataType:"json",
				success:function(result, status) {
					if (result !=null && result.success) {
						
						 parentDialog.addClass("hidden");
						 singleVideoAuditCon.addClass("hidden");
						 //把视频的url  清空
						 $(".videoBig",singleVideoAuditCon).attr("src","");
						 
						 top.layer.alert("视频审核成功!");
						 //去除一览页面当前视频
						 $("input[value='"+videoId+"']").parent().parent().remove();
						 //视频数量-1
						 $(".menuSelected",window.parent.document).find(".numberCon").text($(".menuSelected",window.parent.document).find(".numberCon").text()-1);
						 
					}else{
						 top.layer.alert("视频审核失败!");
					}
				}
			});
		 
		
	 });
	 
	
	 
	 //点击时间帧
	 $(".videoTimeLine",singleVideoAuditCon).click(function(){
		 
		 $("#videoTxtCon",singleVideoAuditCon).removeClass("videoTxtCon").addClass("videoTxtConDefault");
		 //将隐藏的审核结果复选框隐藏
		 $(".auditConten",singleVideoAuditCon).removeClass("hidden");
		 var videoTypeFlag = $("#playing_video",singleVideoAuditCon).hasClass("hidden");
		 //获取 时间帧端 字符串
		 var timeStart = +$(".offsetBegin",this).text();
		 // TODO 暂时注释
//		 video[0].addEventListener("timeupdate", timeupdate);
//		 //调到指定的时间点播放
//		 playMedia(timeStart,null);
		 if(videoTypeFlag){//点播
			 //点播  就是从指定的时间开始播放
			 parent.setCurrentTime(timeStart/1000);
		 }else{//直播
			//先清空
			$(videoCon).html('');
			//动态设置  父级页面的 审核视频的video
			videoCon.html($("#checkInfoScrVideoId").html());
			var videoBigId = "video" + new Date().getTime();
			var videoBig = $(videoCon).find(".videoBig").attr("id",videoBigId);
			var  url = $(this).find(".segmentsUri").text();
			//设置  截帧地址
			parent.newQiniuVideo(videoBigId,url,"auditShow");
		 }
		 
		 //重新布局
//		 $("#videoCon",singleVideoAuditCon).removeClass("videoConDefault").addClass("videoCon");
//		 $("#auditOperateCon",singleVideoAuditCon).removeClass("auditOperateConDefault").addClass("auditOperateCon");
//		 $("#videoTxtCon",singleVideoAuditCon).removeClass("videoTxtCon").addClass("videoTxtConDefault");
		 //将隐藏的审核结果复选框发出
//		 $(".auditConten",singleVideoAuditCon).removeClass("hidden");
		 //重置复选框
		 $('.auditConten',window.parent.document).find('input[type=checkbox]').attr('checked',false);
		 
		 $(this).addClass("videoTimeSelected");
		 $(this).siblings().removeClass("videoTimeSelected");
		 
		 //获取此行op
		 var segmentsOp = jQuery(this).find(".segmentsOp").text();
		 //获取此行危险等级
		 var segmentsLevel = jQuery(this).find(".segmentsLevel").text();
		 
		 //根据审核项目和危险等级，控制页面勾选项
		 switch(segmentsOp){
		 	
		 	//鉴黄
		 	case "pulp_beta":
		 		//根据label设置勾选项
		 		if(segmentsLevel == '0'){
		 			//色情
		 			$('#pornographic',window.parent.document).attr('checked',true);
		 			$('#pornographic',window.parent.document).prop('checked',true);
		 		}else if(segmentsLevel == '1'){
		 			//性感
		 			$('#sex',window.parent.document).attr('checked',true);
		 			$('#sex',window.parent.document).prop('checked',true);
		 		}
		 		break;
		 	//鉴暴
		 	case "terror":
		 		//根据危险等级设置勾选项
		 		if(segmentsLevel == '1'){
		 			//暴恐
		 			$('#fearViolence',window.parent.document).attr('checked',true);
		 			$('#fearViolence',window.parent.document).prop('checked',true);
		 		}
		 		break;
		 	//政治人物
		 	case "face_group_search":
		 		//根据危险等级设置勾选项
		 		if(segmentsLevel == '1'){
		 			//涉政事件
		 			$('#politiciansInvolved',window.parent.document).attr('checked',true);
		 			$('#politiciansInvolved',window.parent.document).prop('checked',true);
		 		}else if(segmentsLevel == '0'){
		 			//涉政人物
		 			$('#politicalAffairs',window.parent.document).attr('checked',true);
		 			$('#politicalAffairs',window.parent.document).prop('checked',true);
		 		}
		 		break;
		 }
	 });   
	 
	 //默认第一个时间帧 是默认选中状态
	 //同时默认显示第一个时间帧的审核结果
	// $(".videoTimeLine",window.parent.document).eq(0).click();
	 //判断每端时间帧是否已经人审  已经人审的 打勾，未人审的  打x 标志
	 $(".videoTimeLine",window.parent.document).each(function(index,el){
		var segmentsManualLevel = $(".segmentsManualLevel",this).text();
		if(segmentsManualLevel !=""&& segmentsManualLevel!=null){//表示有人工审核
			//审核标志ok2.png
			$(".opreateTip",this).attr("src",$.imgPath+"/ok2.png");
		}else{
			//审核标志 wrong.png
			$(".opreateTip",this).attr("src",$.imgPath+"/wrong.png");
		}
	 });
	 
	 //默认第一个  时间帧被选中
	 $(".videoTimeLine",singleVideoAuditCon).eq(0).click(); 
}



//待删除
/*//向后台发起请求
function requestVideoCheck(){
	var higher = 0;
	var medium = 0;
	var low = 0;
	var loginName  = $('#loginNm',window.parent.document).text();
	var hightCount = +$('#hightCount',window.parent.document).text();
	var middleCount = +$('#middleCount',window.parent.document).text();
	var lowerCount = +$('#lowerCount',window.parent.document).text();
	//危险等级的 传过去 0 表示不请求分配，传过去1 表示 请求分配
	if(hightCount<6){
		higher = 1;
	}
	if(middleCount<6){
		medium = 1;
	}
	if(lowerCount<6){
		low = 1;
	}
	if(hightCount < 6|| middleCount < 6|| lowerCount < 6){
			// 发送请求
		$.ajax({
			url:$.basepath +"/assignReview.do",
			data :{
					loginName : loginName,
					low : low,
					medium : medium,
					higher: higher
					},
				method:"post",
				dataType:"json",
				async:false,
				success:function(result) {
					var checkVideo = result.body.checkVideo;
					if(checkVideo == null){
						return;
					}
					
//					var checkVideo = JSON.parse(result.body.checkVideo);
					//判断  当前选择的危险等级是 高    中  低 然后和后台取出的数据比较  将是对应的危险等级 的视频显示在  当前页面上
					//	当前页面选中的 危险等级
					var currentDangerLevel = $(".menuSelected",window.parent.document).find(".numberCon").attr("name");
						//从后台取出的 危险等级的数据
						var  review_level = checkVideo.reviewLevel;
						if(review_level=="3"&&review_level!=currentDangerLevel){
							 blingFlagHight = true;//高
						}
						if(review_level=="2"&&review_level!=currentDangerLevel){
							blingFlagMid= true;//中			
											}
						if(review_level=="1"&&review_level!=currentDangerLevel){
							blingFlagLow= true;//低
						}
						
						//判断 什么时候可以闪烁  危险等级：高   如果有返回值时，返回值  >6对应的闪烁标志 比如blingFlagHight设置为true
						 if(blingFlagHight){
							 timeHight.mySetInterval(showTaskNum,1000,$("#hightCount",window.parent.document));
							 $("#hightCount",window.parent.document).parent().click(function(){                
						    		  timeHight.myClearInterval();
							       blingFlagHight = false;
							    });
						    	  $("#hightCount",window.parent.document).parent().siblings().click(function(){
						    		  timeHight.mySetInterval(showTaskNum,1000,$("#hightCount",window.parent.document));
						    	  });
						    }
						// 危险等级：中 
						 if(blingFlagMid){
							 	//设置定时器
						    	 timeMid.mySetInterval(showTaskNum,1000,$("#middleCount",window.parent.document));
						    	 $("#middleCount",window.parent.document).parent().click(function(){   
						    		  //点击自身以后定时器 停止
						    		  timeMid.myClearInterval();
							       blingFlagMid = false;
							    });
						    	  $("#middleCount",window.parent.document).parent().siblings().click(function(){
						    		  timeMid.mySetInterval(showTaskNum,1000,$("#middleCount",window.parent.document));
						    	  });
						    }
						// 危险等级：低
						 if(blingFlagLow){
						    	 timeLow.mySetInterval(showTaskNum,1000,$("#lowerCount",window.parent.document));
						    	 $("#lowerCount",window.parent.document).parent().click(function(){  
						    		  //关闭定时器
						    		  timeLow.myClearInterval();
							       blingFlagLow = false;
							    });
						    	 $("#lowerCount",window.parent.document).parent().siblings().click(function(){
						    		timeLow.mySetInterval(showTaskNum,1000,$("#lowerCount",window.parent.document));
						    	  });
						    } 
						 
						//根据后台返回的  危险等级将页面上相应危险等级的数据+1
						var num = +$("lable[name='"+review_level+"']",window.parent.document).text() + 1;
						
						$("lable[name='"+review_level+"']",window.parent.document).text(num);
						//从后台取出的 审核人id
						var reviewerId = checkVideo.reviewerId;
						//从后台取出的  审核阶段
						var reviewStage = checkVideo.reviewStage;
					//返回的数据，根据危险等级划分
					//判断页面上对应危险等级的  视频个数，当视频个数小宇6时，将返回的数据加载到页面上
					if( result.body.checkVideo !=null && result.body.checkVideo !="" && review_level==currentDangerLevel){
						 //当前页面的视频的个数=6的时候，关闭请求
						var currentCheckVideoNum = $(".auditVideoList").length;
						if(currentCheckVideoNum<6){
							appendVideoCon(checkVideo);
							
						}
					}else{
						
						
					}
					
			}
		});
		}
	
//	alert($(".auditVideoList").length);
	$(".auditVideoList").each(function(i,el){
	    var error = 	$(el).find(".video-damage-note").length;
		if(error>0){
			$(".errorDialogMask",el).removeClass("hidden");
		}
		
	});
}*/


//待删除
/*//追加视频
function appendVideoCon(checkVideo){
	//将数据动态添加到 新生成的视频框
	//视频地址
		var videoUrl = checkVideo.videoUri;
		var auditVideoList = $("<div class='auditVideoList'></div>");
			var videoSub = $("<div class='videoSub'></div>").appendTo(auditVideoList);
			$("<video  class='videoStyle' controls src='"+videoUrl+"'></video>").appendTo(videoSub);
			$("<input type='hidden' class='videoId' id='hidId' value='{{:id}}'/>").appendTo(videoSub);
			$("<div class='auditBtn' onclick='videoCheckDialog(this)'>审核</div>").appendTo(videoSub);
			$("<div class='remove' onclick='videoDel(this)'>×</div>").appendTo(videoSub);
			var videoDialogMask = $("<div class='videoDialogMask hidden'></div>").appendTo(videoSub);
			$("<div class='cancelAuditTip'>"+
			"<div class='tipsTxt'>审核结束？</div>"+
			"<div class='btnCon'>"+
				"<div class='btn' id='no'>否</div>"+
				"<div class='btn' id='yes'>是</div></div></div>").appendTo(videoDialogMask);
			$("#auditVideoListConID").append(auditVideoList);
}*/


//待删除
/*//设置3个定时器
var blingFlagHight = false;//高
var blingFlagMid= false;//中
var blingFlagLow= false;//低
var _intervalHight;
var _intervalMid;
var _intervalLow;
//div闪烁
function showTaskNum(obj)
{
	$(obj).fadeOut(400);
	$(obj).fadeIn(400); 
}
//设置带参数的定时器
var timeHight ={
	mySetInterval :function(f,time,param){
		_intervalHight= setInterval(function(){f(param);},time); 
	},
	myClearInterval : function(){
		clearInterval(_intervalHight);
	}
};
var timeMid ={
	mySetInterval :function(f,time,param){
		_intervalMid= setInterval(function(){f(param);},time); 
	},
	myClearInterval : function(){
		clearInterval(_intervalMid);
	}
};
var timeLow ={
		mySetInterval :function(f,time,param){
			 _intervalLow= setInterval(function(){f(param);},time); 
		},
		myClearInterval : function(){
			clearInterval(_intervalLow);
		}
	};*/



//将时分秒转化为 秒
/*function timeFormatToSecond(timeFormat){
var timeArray = timeFormat.split(":");
	//时
	var hour = +timeArray[0];
	//分
	var minute =  +timeArray[1];
	// 秒
	var second = +timeArray[2];
	
	var secondFormat = second + minute*60 + hour*60*60;
	return secondFormat;
}*/


var video = $(".videoBig",window.parent.document).find("video");
var _endTime;
// 视频 切入
function playMedia(startTime, endTime) {
	// 设置结束时间
	_endTime = endTime;
	video[0].currentTime = startTime;
	//将切入的时间点 暂停
	video[0].play();
	
}
function timeupdate() {
	var time = video[0].currentTime + "";
	if (time == _endTime) {
		video[0].pause();
	}
}

/**
 * 获取截帧列表的所有数据
 * @returns {Array}
 */
function getVideoTimeConData() {
	var videoData = [];
	$(".videoTimeCon",window.parent.document).find(".videoTimeLine").each(function() {
		var jsonResult = {};
		$(this).find("span").each(function () {
			 //获取propkey
			 var propkey = $(this).attr("propkey") || "";
			 //获取value
			 var value = $(this).text() || "";
			 //保存值
			 jsonResult[propkey] = value;
			
		});
		if (!$.isEmptyObject(jsonResult)) {
			videoData.push(jsonResult);
		}
	});
	return videoData;
}

/**
 * 获取选中的时间帧的数据
 * @returns {Array}
 */
function getVideoTimeCheckedData() {
	var videoData;
	$(".videoTimeCon",window.parent.document).find(".videoTimeLine").each(function() {
		var size = $(this).attr("class").indexOf("videoTimeSelected");
		//判断当前元素是否选中
		if(size != (-1)){
			var jsonResult = {};
			$(this).find("span").each(function () {
				 //获取propkey
				 var propkey = $(this).attr("propkey") || "";
				 //获取value
				 var value = $(this).text() || "";
				 //保存值
				 jsonResult[propkey] = value;
				
			});
//			if (!$.isEmptyObject(jsonResult)) {
				videoData=jsonResult;
//			}
			//获取到一条选中数据，跳出所有循环
			return false;
		}
	});
	return videoData;
}