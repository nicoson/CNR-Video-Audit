$(document).ready(function(){
		//获取视频数据
		getVideoList();
		//加载页面【危险等级】下拉选数据
		$("#dangerLevel").initDicItems({
			dicCode:"review_level",//danger_level
//		  	blank:{need:true},
			callback:selectCss
		});
		
		//【刷新】按钮点击事件
		$("#refreshVideoListBtn").click(function(){
			//将遮罩层放出
			refreshPage_open();
			//下拉框复位
			$("ul li",".dangerLevel").removeClass("selected");
			$(".filter-option",".selectCon").html("危险等级");
			$(".filter-option",".selectCon").text("危险等级");
			$("button[data-id='dangerLevel']",".dangerLevel").attr("title","危险等级");
			$("#dangerLevel").val("");
			//触发重新加载页面数据事件
			$("#__hidBtnSearch").trigger("click.pager");
			getVideoList();
		
		});
	
		//危险等级下拉选change事件
		$("#dangerLevel").change(function(){
			//当前页数重置为第一页
			$("#hidPageNum").text(1);
			getVideoList();
			//将遮罩层放出
			refreshPage_open();
		});
		$("#pager_pre").addClass("disableClass");
		//点击【首页】
		$("#pager_first").click(function(){
			//先移除下一页按钮的禁用样式
			$("#pager_next").removeClass("disableClass");
			if($("#hidPageNum").text() == '1'){
				return;
			}
			//将遮罩层放出
			refreshPage_open();
			//设置分页参数
			$("#hidPageNum").text(1);
			getVideoList();
			
		});
		//点击【尾页】
		$("#pager_last").click(function(){
			if($("#hidPageNum").text() == $("#hidPageMaxNum").val()){
				return;
			}
			//先移除下一页按钮的禁用样式
//			$("#pager_next").removeClass("disableClass");
			//将遮罩层放出
			refreshPage_open();
			//设置分页参数
			$("#hidPageNum").text($("#hidPageMaxNum").val());
			getVideoList();
			
		});
		//点击【跳转】
		$("#page_jump").click(function(){
			if(eval($("#jump_pageNumber").val()) < 1){
				top.layer.alert("请正确输入页数");
				return;
			}
			if(eval($("#jump_pageNumber").val()) > eval($("#hidPageMaxNum").val())){
				top.layer.alert("超出数据总页数");
				return;
			}
			
			//先移除下一页按钮的禁用样式
//			$("#pager_next").removeClass("disableClass");
			//将遮罩层放出
			refreshPage_open();
			//设置分页参数
			$("#hidPageNum").text($("#jump_pageNumber").val());
			getVideoList();
			
		});
		//点击【上一页】
		$("#pager_pre").click(function(){
			//先移除下一页按钮的禁用样式
			$("#pager_next").removeClass("disableClass");
			if($("#hidPageNum").text() == '1'){
				return;
			}
			//将遮罩层放出
			refreshPage_open();
			//设置分页参数
			$("#hidPageNum").text(+$("#hidPageNum").text()-1);
			getVideoList();
			
		});
		//点击【下一页】
		$("#pager_next").click(function(){
			if($("#hidPageNum").text() == $("#hidPageMaxNum").val()){//当前页  为尾页
//				$("#pager_next").addClass("disableClass");
				return ;
			}
			//将页面加载中遮罩层放出
			refreshPage_open();
			//设置分页参数
			$("#hidPageNum").text(+$("#hidPageNum").text()*1+1);
			
			getVideoList();
			
		});
		
});

function getVideoList(){
	//定义默认危险等级 
	var reviewLevel = "0,1,2,3,99";
	var dangerLevel = $("#dangerLevel").val();
	//分页信息
	var pageNum = $("#hidPageNum").text();
	var pageSize = $("#hidPageSize").val();
	
	//判断危险等级下拉选是否有数据
	if(dangerLevel != "" && dangerLevel != null){
		reviewLevel = dangerLevel.join();
	}
	
	$.post($.basepath + "/video/videoPageListData.do",{
		pageNum : pageNum,
		pageSize :	pageSize,
//		reviewStage : "",
		reviewLevel :reviewLevel,  
	},function(result){
		
		$("#hidPageMaxNum").val(result.body.pageMaxNum);
		$("#hidBucketDomain").val(result.body.bucketDomain);
		$("#page_show").text($("#hidPageNum").text()+"/"+$("#hidPageMaxNum").val());
		
		if($("#hidPageNum").text() < $("#hidPageMaxNum").val()){//当前页  为尾页
			$("#pager_next").removeClass("disableClass");
			$("#pager_last").removeClass("disableClass");
//			return ;
		}
		
		if($("#hidPageNum").text() == $("#hidPageMaxNum").val()){//当前页  为尾页
			$("#pager_next").addClass("disableClass");
			$("#pager_last").addClass("disableClass");
//			return ;
		}
		
		//判断是否查到数据
		if(JSON.stringify(result.body.data) == '' || JSON.stringify(result.body.data).length < 3){
			//尾页
			if(pageNum > 1){
				$("#hidPageNum").text($("#hidPageNum").text()-1);
				getVideoList();
//				top.layer.alert("当前已是尾页");
//				refreshPage_close();
//				$("#hidPageNum").text(+$("#hidPageNum").text()-1);
//				if($("#hidPageNum").text() == 1){
//					$("#pager_pre").addClass("disableClass");
//				}
				return;
			}
		}
		if(pageNum == 1){
			$("#pager_pre").addClass("disableClass");
			$("#pager_first").addClass("disableClass");
		}else{
			$("#pager_pre").removeClass("disableClass");
			$("#pager_first").removeClass("disableClass");
		}
		
		//设置分页数据
		$("#hidPageNum").text(result.body.pageNum);
		$("#hidPageSize").val(result.body.pageSize);
		
		//绑定页面数据
		var htmlContent = $("#engineeringTr").render(result.body.data);
		$("#VideoPageListBody").html(htmlContent);
		//遍历tr 
		$("#VideoPageListBody","#main").find("tr").each(function(){
//			var videoHidden = $(".videoHidden",this);
//			var videoDurtion =$(".videoDurtion",this);
			//当前行的 视频帧封面 地址
			var videoImgUrl = $(".videoImg",this).attr("src");
			if(videoImgUrl==""||videoImgUrl==null){
				$(".videoImg",this).attr("src",$.imgPath+"/logoImg.png");
			}
			//获取当前视频时长  显示视频时长
			/*videoHidden[0].oncanplay=function(){
				//durationFormat(this.duration);
				//videoDurtion.text(durationFormat(this.duration));
			}*/
			//机审状态
			var roboticState=$(".roboticState",this).text();
			//机审状态
			if(roboticState=="处理中"){
			//	.addClass("roboticState_inProcess");
				$(".roboticState",this).addClass("roboticState_inProcess");
			}else{
				//.addClass("roboticState_other");
				$(".roboticState",this).addClass("roboticState_other");
			}
			//机审危险等级
			var roboticLevel = $(".roboticLevel",this).find("span").text();
			switch(roboticLevel){
			case "高":
				$(".roboticLevel",this).find("span").addClass("dangerLevel_height");
				$(".roboticLevel",this).find("img").attr("src",$.imgPath+"/wrong.png");
				break;
			case "中":
				$(".roboticLevel",this).find("span").addClass("dangerLevel_middle");
				$(".roboticLevel",this).find("img").attr("src",$.imgPath+"/warning-o.png");
				break;
			case "低":
				$(".roboticLevel",this).find("span").addClass("dangerLevel_low");
				$(".roboticLevel",this).find("img").attr("src",$.imgPath+"/infor-o.png");
				break;
			case "无":
				$(".roboticLevel",this).find("span").addClass("dangerLevel_none");
				$(".roboticLevel",this).find("img").attr("src",$.imgPath+"/ok.png");
				break;
			case "未知":
				$(".roboticLevel",this).find("span").addClass("dangerLevel_unknown");
				$(".roboticLevel",this).find("img").attr("src",$.imgPath+"/unkown.png");
				break;
			}
			
			//人审状态
			var manualState = $(".manualState",this).find("span").text();
			//根据人审状态显示样式
			switch(manualState)
			{
			case "未开始"://未开始
				$(".manualState",this).find("span").addClass("manualState_unStart");
				break;
			case "处理中"://处理中
				$(".manualState",this).find("span").addClass("manualState_inProcess");
				$(".manualState",this).find("img").attr("src",$.imgPath+"/dealing.png");
				break;
			case "通过"://通过
				$(".manualState",this).find("span").addClass("manualState_pass");
				$(".manualState",this).find("img").attr("src",$.imgPath+"/ok2.png");
				break;
			case "不通过"://不通过
				$(".manualState",this).find("span").addClass("manualState_closure");
				$(".manualState",this).find("img").attr("src",$.imgPath+"/wrong.png");
				break;
			}
		});
		//将页面加载关闭
		refreshPage_close();
	});
}

/**删除审核视频**/
function videoDel(obj){
	//获取选中的视频id
	var videoId = $(obj).parent().parent().find(".videoId").text();
	
	var liveEnd = $(obj).parent().parent().find(".liveEnd").text();
	if(liveEnd != "" && liveEnd == 0){
		top.layer.alert("视频正在直播，无法删除");
		return;
	}
	
	top.layer.confirm('确认删除该视频吗？', {
		btn: ['确定','取消'] //按钮
	}, function(e) {
		$.ajax({
			async:false,
			url: $.basepath + "/video/videoDelete.do",
			method:"post",
	        data: {"videoId":videoId},
	        complete: function(result){
				if (result.success) {
					top.layer.alert("删除成功", {
						icon: 1,
						skin: 'layer-ext-moon'
					}, function(e){
						top.layer.close(e);
						top.sd_removeAndFN("success");
					});
				} else {
					top.layer.alert("删除失败", {
						icon: 2,
						skin: 'layer-ext-moon'
					});
				}
				//刷新页面数据
				getVideoList();
			}
		});
		top.layer.close(e);
	}, function(){
	});
}

/**封禁审核视频**/
function videoClosure(obj){
	//获取选中的视频id
//	var videoId = $(obj).parent().parent().find(".hidId").text();
	
	top.layer.confirm('确认封禁该视频吗？', {
		btn: ['确定','取消'] //按钮
	}, function(e) {
		// TODO 待完善
		top.layer.alert("封禁成功", {
			icon: 1,
			skin: 'layer-ext-moon'
		});
//		$.ajax({
//			async:false,
//			url: $.basepath + "/video/videoClosure.do",
//			method:"post",
//	        data: "videoId=" + videoId,
//	        complete: function(msg){
//				if (msg.responseText > 0) {
//					top.layer.alert("封禁成功", {
//						icon: 1,
//						skin: 'layer-ext-moon'
//					}, function(e){
//						top.layer.close(e);
//						top.sd_removeAndFN("success");
//					});
//				} else {
//					top.layer.alert("封禁失败", {
//						icon: 2,
//						skin: 'layer-ext-moon'
//					});
//				}
//				//刷新页面数据
//				getVideoList();
//			}
//		});
		top.layer.close(e);
		
	}, function(){
	});
}

/**加载下拉选样式**/
function selectCss(){
	$('#dangerLevel').selectpicker({
        'selectedText': 'cat',
		'style': 'btn-info',
		'size': 8
    });
}

/**点击预览按钮**/
function previewVideo(obj){
	//获取 indexLook 页面遮罩层
	var parentDialog = 	$('#dialogMask',window.parent.document);
	parentDialog.removeClass("hidden");
	var previewVideoCon = $(".previewVideoCon",parentDialog);
	
	//先清空 previewVideoCon
	$(previewVideoCon).html("");
	
	//向父级页面  动态添加预览视频框的  元素
//	var targetVideoId = "video" + new Date().getTime();
	var targetVideoId = "video" + new Date().getTime();
	var elem1 = $('<div class="closePreview">×</div>');
	var elem2 = $('<video class="previewVideo video-js vjs-big-play-centered" ></video>');
//	var elem3 = $('<div class="prevideoError hidden">	视频播放错误，请稍后...</div>');
	elem2.attr("id", targetVideoId);
	$(previewVideoCon).append(elem1);
	$(previewVideoCon).append(elem2);
//	$(previewVideoCon).append(elem3);
//	var html = $("#previewScrVideoId").html();
//	$(html).find("video").attr("id",targetVideoId);
	
	//获取预览 video
//	var previewVideo = $(".previewVideo",previewVideoCon);
//		$(obj).parent().find(".videoId").val();
		//动态设置  videoId  为预览视频框的id
	//	previewVideo.attr("id",$(obj).parent().parent().find(".videoId").text());
		
	//获取 预览视频id
//	var previewVideoId = $("#previewVideoId",previewVideoCon);
	
	previewVideoCon.removeClass("hidden");
	var currentLine=$(obj).parent().parent();
	//获取当前行的视频地址
	var url = $("td[propkey='videoUri']",currentLine).text();
	//实例化七牛视频播放器
	parent.newQiniuVideo(targetVideoId,url,"previewShow");
/*	var id = previewVideoId.attr("id");
	//实例化 Qiniu视频播放器
	     var options = {
	    			controls : true,//  http://pili-live-hls.ps.qiniucdn.com/NIU7PS/1508383911083736890_196m2lhl7MTtskc79YGAUA.m3u8   
					url : videoUri,
					type : 'mp4',
					preload : true,
					autoplay : false
	         };
	      var player = new QiniuPlayer(id, options);*/
	
	
	
	var len =  $(".video-damage-note",parentDialog).length;
	
	if($(".closePreview",parentDialog).length<1){//表示 实例化没有成功
		alert("erroe")
		
	}
	//点击关闭按钮
	$(".closePreview",parentDialog).click(function(){
		parentDialog.addClass("hidden");
		previewVideoCon.addClass("hidden");
		//移除预览的视频播放器
		$("#previewVideoConId",parentDialog).html('');
	});
	
}
//时长转化格式为 10:23:09
function durationFormat(duration){
	// 获取时长 时
	// 获取时
	hour = Math.floor(duration / 60 / 60);
	// 获取分
	minute = Math.floor(duration / 60 % 60);
	// 获取秒
	second = Math.floor(duration % 60);

	(hour >= 10) ? h1 = parseInt(hour / 10) : h1 = "0";
	h2 = hour % 10;
	(minute >= 10) ? m1 = parseInt(minute / 10) : m1 = "0";
	m2 = minute % 10;
	(second >= 10) ? s1 = parseInt(second / 10) : s1 = "0";
	s2 = second % 10;
	var durationStr = h1+h2 +":"+m1+m2+":"+s1+s2;
	
	return durationStr;
	
}
/**
 * 弹出提示页面加载中
 */
function refreshPage_open(){
	//将遮罩层放出
	$('#dialogMask', parent.document).removeClass("hidden");
	$(".loadingCon", parent.document).removeClass("hidden");
}
//关闭页面加载提示
function refreshPage_close(){
	$('#dialogMask', parent.document).addClass("hidden");
	$(".loadingCon", parent.document).addClass("hidden");
}
function getMessage(obj){
	var liveEnd = $(obj).parent().parent().parent().find(".liveEnd").text();
	if(liveEnd != "" && liveEnd == 0){
		$(obj).removeAttr("href");
		top.layer.alert("视频正在直播，无法下载");
		return;
	}
}