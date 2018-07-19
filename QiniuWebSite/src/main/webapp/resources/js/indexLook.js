$(function(){
	//根据用户角色设置admin权限
	var roleType = $("#roleType").val();
	if('0' != roleType){
		$(".menuNav").eq(4).addClass("hidden");
	}
	//实现复选框单选效果
	$('input[type=checkbox]').click(function(e){
		if($(this).is(':checked')){
				$(this).attr('checked',true).parent().siblings().find('input[type=checkbox]').attr('checked',false);
		}else{
			$(this).attr('checked',false).parent().siblings().find('input[type=checkbox]').attr('checked',false);
		}
	});
		
//	//获取用户id
//	var userId = $("#userId").text();
//	//登录名
//	var loginNm = $("#loginNm").text();

	//获取危险等级对应的审核视频数量
	setSelectData();
	//页面已加载默认显示视频列表
	$("#selectedIframe").attr("src",$.basePath+"/video/videoPageList.do");
	$("#selectedIframe").prop("src",$.basePath+"/video/videoPageList.do");
	//视频列表
$(".menuNav").eq(0).click(function(){
	//页面已加载默认显示视频列表
	$("#selectedIframe").attr("src",$.basePath+"/video/videoPageList.do");
	$("#selectedIframe").prop("src",$.basePath+"/video/videoPageList.do");
	setUnderLine(this);
});

$(".menuNav").eq(1).click(function(){
	//危险等级-高
	var dangerLevel = 3;
	//Iframe发送请求跳转页面
	$("#selectedIframe").attr("src",$.basePath+"/showCheckInfoList.do?dangerLevel="+dangerLevel);
	$("#selectedIframe").prop("src",$.basePath+"/showCheckInfoList.do?dangerLevel="+dangerLevel);
	//nav设置下划线
	setUnderLine(this);
});


$(".menuNav").eq(2).click(function(){
	//危险等级-中
	var dangerLevel = 2;
	//Iframe发送请求跳转页面
	$("#selectedIframe").attr("src",$.basePath+"/showCheckInfoList.do?dangerLevel="+dangerLevel);
	$("#selectedIframe").prop("src",$.basePath+"/showCheckInfoList.do?dangerLevel="+dangerLevel);
	//nav设置下划线
	setUnderLine(this);
	});
$(".menuNav").eq(3).click(function(){
	//危险等级-低 
	var dangerLevel = 1;
	//Iframe发送请求跳转页面
	$("#selectedIframe").attr("src",$.basePath+"/showCheckInfoList.do?dangerLevel="+dangerLevel);
	$("#selectedIframe").prop("src",$.basePath+"/showCheckInfoList.do?dangerLevel="+dangerLevel);
	//nav设置下划线
	setUnderLine(this);
	});



$(".menuNav").eq(4).click(function(){
	$("#selectedIframe").attr("src",$.basePath+"/showSetLevel.do");
	$("#selectedIframe").prop("src",$.basePath+"/showSetLevel.do");
	//nav设置下划线
	setUnderLine(this);
	});
	/*iframe加载完成后*/
	var iframeName=$("#selectedIframe");
	//获取遮罩层
//	var dialogMask=$("#dialogMask");
	iframeName.load(function(){
		//点击上传视频列表(iframe 页面嵌着 视频列表页面)
		//获取域名
	//	var bucketDomain = $(this).contents().find("#hidBucketDomain").val();
		$(this).contents().find("#uploadVideoBtn").click(function(){
			//重置【请输入直播名称】输入框
			$(".user_inputVideoName").val("");
			//重置【请输入直播地址】输入框
			$(".user_input").val("");
			
			var bucketDomain ="http://"+ $(this).parent().find("#hidBucketDomain").val()+"/";
			//将遮罩层放出
			$('#dialogMask', parent.document).removeClass("hidden");
			$(".uploadCon", parent.document).removeClass("hidden");
			//点击取消按钮
			$('#cancel', parent.document).click(function(){
				//清空点播信息
            	$("#uploadFileListCon").html("");
				$('#dialogMask', parent.document).addClass("hidden");
				$(".uploadCon", parent.document).addClass("hidden");
				$("#pickfiles").nextAll().remove();  
			});
			
			//点击选择文件上传
		
			   //引入Plupload 、qiniu.js后
            var uploader = Qiniu.uploader({
                runtimes: 'html5,flash,html4',    //上传模式,依次退化
                browse_button: 'pickfiles',       //上传选择的点选按钮，**必需**
              //  uptoken : 'HK1vOltLkw0HQ7pqCYuoqGwm7XTgcaxhQ7qejE1Y:fSEeowLN4zp3T9V08PkpOKJMez4=:eyJzY29wZSI6InZpZGVvLXJldmlldyIsImRlYWRsaW5lIjoxNTA4NDk1Mjk5fQ==',
                uptoken_url:	$.basePath+'/getUploadToken.do',            //Ajax请求upToken的Url，**强烈建议设置**（服务端提供）
                // uptoken : '', //若未指定uptoken_url,则必须指定 uptoken ,uptoken由其他程序生成
                unique_names: true, // 默认 false，key为文件名。若开启该选项，SDK为自动生成上传成功后的key（文件名）。
                // save_key: true,   // 默认 false。若在服务端生成uptoken的上传策略中指定了 `sava_key`，则开启，SDK会忽略对key的处理
                domain: bucketDomain,   //bucket 域名，下载资源时用到，**必需**'http://ow8zztdmn.bkt.clouddn.com/'
                get_new_uptoken: false,  //设置上传文件的时候是否每次都重新获取新的token
                container: 'uploadFileBtn',           //上传区域DOM ID，默认是browser_button的父元素，
                max_file_size: '1GB',           //最大文件体积限制
                flash_swf_url: $.jsPath+'/plupload/Moxie.swf',  //引入flash,相对路径
                max_retries: 3,                   //上传失败最大重试次数
                dragdrop: true,                   //开启可拖曳上传
                drop_element: 'uploadFileBtn',        //拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
                chunk_size: '4mb',                //分块上传时，每片的体积
                auto_start: true,                 //选择文件后自动上传，若关闭需要自己绑定事件触发上传
                filters : {
                    mime_types: [
                        {title : "Video files", extensions : "flv,mpg,mpeg,avi,wmv,mov,asf,rm,rmvb,mkv,m4v,mp4"} // 限定flv,mpg,mpeg,avi,wmv,mov,asf,rm,rmvb,mkv,m4v,mp4后缀格式上传
                    ]
                },
                
                init: {
                    'FilesAdded': function(up, files) {
                    	
                        plupload.each(files, function(file) {
                            // 文件添加进队列后,处理相关的事情
                        	$("#uploadFileListCon").append($("#uploadFileSrcId").html());
                        });
                    },
                    'BeforeUpload': function(up, file) {
                           // 每个文件上传前,处理相关的事情
                    },
                    'UploadProgress': function(up, file) {
                           // 每个文件上传时,处理相关的事情
                    	//$("#uploadFileListCon").append($("#uploadFileSrcId").html());
                    			
                         		$(".fileName",".fileLine").last().text(file.name);
                         		$(".size",".fileLine").last().text(plupload.formatSize(file.size).toUpperCase());
                         		$(".fileprogress",".fileLine").last().text(file.percent + "%");
                         		/*$(".progressPrengent","#uploadFileConDesc").text(file.percent + "%");*/
                             //  $("#uploadFileConDesc").show();
                            //   var raised = 200 * file.percent/100 +'';
                            /*   $('#jqmeter-container').jQMeter({
	                       			goal:'200',
	                       			raised:raised,
	                       			orientation:'horizontal',
	                       			width:'200px',
                       				bgColor	: "#EDEFF3",
                       				barColor:"#A3DCF3",
                       				height	:'20px'
                       			});*/
                    },
                    'FileUploaded': function(up, file, info) {
                    	 // 每个文件上传成功后,处理相关的事情
                    	//清除 自动 生成的 input 标签
                    	//$("#pickfiles").nextAll().remove();  
                    	
                    	console.log("response:", info);
                    	var infoRes = JSON.parse(info);
//                    	var fileHash = infoRes.hash;
//                    	var fileName = infoRes.key;
                    	
                    	 var domain = up.getOption('domain');
                  	   var urlLink = domain + encodeURI(infoRes.key);
                  	    console.log("url:"+urlLink);
                    	console.log(infoRes.key);
                          console.log("上传成功");
                          //上传  成功以后  关闭上传 弹出层
                        /*  setTimeout(function(){
                        	  //清空页面文件名
                                $(".fileName","#uploadFileConDesc").text('');
                                //清空页面大小
                                $(".size","#uploadFileConDesc").text('');
                                //上传文信息con  隐藏
                                $("#uploadFileConDesc").hide();
                                //隐藏  上传页面弹出框
                                $(".uploadCon", parent.document).addClass("hidden");
                                //隐藏  大的弹出框s
                                $('#dialogMask', parent.document).addClass("hidden");
                          }, 3000 );*/
                        
                        // 视频类型 0:直播，1:点播
          				var videoType = "1"; 
          				//视频上传以后的地址
          				var uri = domain + encodeURI(infoRes.key);
          				//视频唯一标识
          				//var id = infoRes.hash;
          				var  data ={};
          				var attribute = {"desc":file.name};
          				//attribute = {"id":id};
          				data = {"uri":uri,"attribute":attribute};
          				
          				var params ={};
          				var ops = {"pulp":{},"terror":{},"politician":{}};
          				params = {"videoType":videoType,"reviewType":"2","ops":ops,"save":uri};
          				
          				var  VideoSubmitJson = {"data":data,"params":params};
          				var VideoSubmit =JSON.stringify(VideoSubmitJson);
                          //上传成功以后向后台发起请求
                          $.ajax({
          					url:  $.basepath + "/submitVideo.do",
          					method:"post",
          					data:{ VideoSubmit: VideoSubmit},
          					success:function(result) {
          					},
          					error:function(a, b, c) {
          					}
          				});
                          
                          
                      	
                           // 其中 info.response 是文件上传成功后，服务端返回的json，形式如
                           // {
                           //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
                           //    "key": "gogopher.jpg"
                           //  }
                           // 参考http://developer.qiniu.com/docs/v6/api/overview/up/response/simple-response.html

                           // var domain = up.getOption('domain');
                           // var res = parseJSON(info.response);
                           // var sourceLink = domain + res.key; 获取上传成功后的文件的Url
                    },
                    'Error': function(up, err, errTip) {
                    	if('419' == err.status){
                    		top.layer.alert("上传失败，请确认账户是否正常!", {
        						icon: 2,
        						skin: 'layer-ext-moon'
        					});
                    	}else{
                    		top.layer.alert("上传失败!", {
        						icon: 2,
        						skin: 'layer-ext-moon'
        					});
                    	}
                    },
                    'UploadComplete': function() {
                    	
                           //队列文件处理完毕后,处理相关的事情
                    	/* $('#success').show();*/
                    }
                }
            });
		
          
            
            	//点击 确定按钮-上传视频
				$('#sure', parent.document).unbind("click").click(function(){
					
					//清除 自动 生成的 input 标签
		        	$("#pickfiles").nextAll().remove();  
					//获取输入的uri
					var  uploadviodeUri = $(".user_input", parent.document).val();
					//获取输入 视频名称
					var uploadviodeName =  $(".user_inputVideoName", parent.document).val();
					//判断是否  输入 直播地址  直播视频名称
					if(uploadviodeUri == ""){
						top.layer.alert("请输入直播视频地址");
						return ;
					}
					if(uploadviodeName == ""){
						top.layer.alert("请输入直播视频名称");
						return ;
					}
				
		            	$.ajax({
							url : $.basepath + "/video/submitVideo.do",
							data : {
								uri : uploadviodeUri,
								videoName : uploadviodeName
							},
							method:"post",
							dataType:"json",
							async:false,
							success:function(result, status) {
								if (result !=null && result.success) {
								//	alert("提交成功")
									top.layer.alert("提交成功");
								}
							}
						});
		            	//清空点播信息
		            	$("#uploadFileListCon").html("");
		            	$('#dialogMask', parent.document).addClass("hidden");
						$(".uploadCon", parent.document).addClass("hidden");
					});
          
		
		});
	
		
	});//iframe  加载完成  end
	
	//请求审核视频数目
	var timeInit = setInterval(requestVideoCheck,5000);
	
	//点击退出按钮
	$("#btnExit").click(function(){
		$.post($.basepath +"/index/loginOut.do", function(json) {
			//alert(json);
			window.top.location.href='/QiniuWebSite/';
		});
	});

});//readyEnd
var player = null;
//实例化  父级页面的  七牛播放器
function newQiniuVideo(targetVideoId,url,showType){//targetId,url
	/*if (player) {
		player.dispose();
	}*/
	//实例化 Qiniu视频播放器
    var options = {
   			controls : true,//  http://pili-live-hls.ps.qiniucdn.com/NIU7PS/1508383911083736890_196m2lhl7MTtskc79YGAUA.m3u8   
				url : url,
				preload : true,
				autoplay : false
        };
    player =  new QiniuPlayer(targetVideoId, options);
    
    
	player.ready(function() {
		// console.log( "error2:"+player.error());
   	  	//错误信息：
			/*var errorMessage = player.error();
			//表示出现错误信息
			if(errorMessage!=null){
				//显示蒙板 提示错误信息
				if(showType.indexOf("previewShow")>-1){//预览页面上的播放 出错
					$(".prevideoError").removeClass("hidden");
				}else if (showType.indexOf("auditShow")>-1){//审核页面上的播放出错
					$(".aduitShowError").removeClass("hidden");
				}
			}*/
			
		})
    var errorLength = $(".video-damage-note").length;
    if(errorLength>0){
    	//显示蒙板 提示错误信息
		if(showType.indexOf("previewShow")>-1){//预览页面上的播放 出错
			$(".prevideoError").removeClass("hidden");
		}else if (showType.indexOf("auditShow")>-1){//审核页面上的播放出错
			$(".aduitShowError").removeClass("hidden");
		}
    }
	return ;
	
}
//设置当前播放时间
function setCurrentTime(startTime){
	  player.ready(function() {
	    	 player.currentTime(startTime);
	    });
	
}
//设置iframe 页面跳转
function setIframeUrl(obj,url){
	$("#selectedIframe").attr("src",url);
	$(obj).parent().siblings().find(".menuNav ").removeClass("menuSelected");
	$(obj).addClass("menuSelected");
}

function setUnderLine(obj){
	$(obj).siblings().removeClass("menuSelected");
	$(obj).addClass("menuSelected");
}

/**
 * 加载视频等级【高】【中】【低】审核视频数量
 */
function setSelectData(){
	// TODO
	$.ajax({
		url:$.basepath + "/video/getCountByDangerLevel.do",
		method:"post",
		dataType:"json",
		async:false,
		success:function(result, status) {
			if (result !=null && result.success) {
				//设置危险等级对应的审核视频数量
				$("#hightCount").text(result.body.videoCountByLevel.hightCount);
				$("#middleCount").text(result.body.videoCountByLevel.middleCount);
				$("#lowerCount").text(result.body.videoCountByLevel.lowerCount);
			}else{
				
			}
			
		}
	});
}



//向后台发起请求
function requestVideoCheck(){
	var higher = 0;
	var medium = 0;
	var low = 0;
	var loginName  = $('#loginNm').text();
	var hightCount = +$('#hightCount').text();
	var middleCount = +$('#middleCount').text();
	var lowerCount = +$('#lowerCount').text();
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
						
						
						//判断  当前选择的危险等级是 高    中  低 然后和后台取出的数据比较  将是对应的危险等级 的视频显示在  当前页面上
						//	当前页面选中的 危险等级
//						var currentDangerLevel = $(".menuSelected").find(".numberCon").attr("name");
						//判断危险等级 是否被选中
						var currentDangerLevel = "";
						$(".dangerNav").each(function(i,dangerNav){
							if($(dangerNav).hasClass("menuSelected")){//判断危险等级 是否被选中
								currentDangerLevel =	$(dangerNav).find(".numberCon").attr("name");
							}
							
						})
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
						
							//根据后台返回的  危险等级将页面上相应危险等级的数据+1
							var num = +$("lable[name='"+review_level+"']").text() + 1;
							
							$("lable[name='"+review_level+"']").text(num);
							
							//判断 什么时候可以闪烁  危险等级：高   如果有返回值时，返回值  >6对应的闪烁标志 比如blingFlagHight设置为true
							 if(blingFlagHight){
								 //设置闪烁灯
								 timeHight.mySetInterval(showTaskNum,500,$("#hightCount"));
							    }
							 //点击高
							 $("#hightCount").parent().click(function(){    
								 //清空  闪烁灯
					    		  timeHight.myClearInterval();
					    		  //高等级的闪烁灯设置 为false
					    		  blingFlagHight = false;
						      
						    });
							 //点击  非高的nav，让高的 闪烁灯  闪
							 $("#hightCount").parent().siblings().click(function(){
								if(blingFlagHight){
									 timeHight.mySetInterval(showTaskNum,500,$("#hightCount"));
								}
					    		 
					    	  });
							 
							// 危险等级：中 
							 if(blingFlagMid){
								 	//设置定时器
							    	 timeMid.mySetInterval(showTaskNum,500,$("#middleCount"));
							    }
							 $("#middleCount").parent().click(function(){   
					    		  //点击自身以后定时器 停止
					    		  timeMid.myClearInterval();
					    			  blingFlagMid = false;
						      
						    });
							 $("#middleCount").parent().siblings().click(function(){
								 if(blingFlagMid){
									 timeMid.mySetInterval(showTaskNum,500,$("#middleCount"));
								 }
					    		  
					    	  });
							// 危险等级：低
							 if(blingFlagLow){
							    	 timeLow.mySetInterval(showTaskNum,500,$("#lowerCount"));
							    } 
							 $("#lowerCount").parent().click(function(){  
					    		  //关闭定时器
					    		  timeLow.myClearInterval();
						    	 blingFlagLow = false;
						    });
							 $("#lowerCount").parent().siblings().click(function(){
								 if(blingFlagLow){
									 timeLow.mySetInterval(showTaskNum,500,$("#lowerCount"));
								 }
						    		
						    	  });
						
							//从后台取出的 审核人id
							var reviewerId = checkVideo.reviewerId;
							//从后台取出的  审核阶段
							var reviewStage = checkVideo.reviewStage;
							//返回的数据，根据危险等级划分
							//判断页面上对应危险等级的  视频个数，当视频个数小宇6时，将返回的数据加载到页面上
							if( result.body.checkVideo !=null && result.body.checkVideo !="" && review_level==currentDangerLevel){
								 //当前页面的视频的个数=6的时候，关闭请求
								var currentCheckVideoNum = $("#selectedIframe").contents().find(".auditVideoList").length;
								if(currentCheckVideoNum<6){
									appendVideoCon(checkVideo);
									
								}
							}
					
					}
		});
	}
	
}


//追加视频
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
			$("#selectedIframe").contents().find("#auditVideoListConID").append(auditVideoList);
}
//设置3个定时器
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
	};

/**封禁审核视频**/
function videoClosure(obj){
	top.layer.confirm('确认封禁该视频吗？', {
		btn: ['确定','取消'] //按钮
	}, function(e) {
		// TODO 待完善
		top.layer.alert("封禁成功", {
			icon: 1,
			skin: 'layer-ext-moon'
		});

		top.layer.close(e);
		
	}, function(){
	});
}