$(function(){
    var timer1;

    $('#btnAddApp').click(function(){
        if($('#divAddApp').css('display') == 'block') {
            timer2 = setInterval(slowHide, 5);
        } else {
            $('#divAddApp').show();
            $('.modal .modal-dialog').css('top', '-600px');
            
            timer1 = setInterval(slowShow, 5);
        }
    });

    function slowShow() {
        if ($('.modal .modal-dialog').css('top') == '0px') {
            clearInterval(timer1);
        }
        else {
            $('.modal .modal-dialog').css('top', parseInt($('.modal .modal-dialog').css('top').replace('px', '')) + 10 + 'px');
        }
    }

    var timer2;

    $('#btnAddAppCloase').click(function(){
        timer2 = setInterval(slowHide, 5);
    });

    function slowHide(){
        if ($('.modal .modal-dialog').css('top') == '-600px') {
            clearInterval(timer2);
            $('.modal').hide();
        }
        else {
            $('.modal .modal-dialog').css('top', parseInt($('.modal .modal-dialog').css('top').replace('px', '')) - 10 + 'px');
        }
    }
    // 第三方设置
    $('.btnSetApp').click(function(){
    	// 使用的一个设置div，清除上一次readonly事件
		$('#logoUpdate').removeAttr("disabled");
		$('#appNmUpdate').removeAttr("readonly");
		$('#contentUpdate').removeAttr("readonly");
		$('#urlUpdate').removeAttr("readonly");
		$('#productId').removeAttr("readonly");
    	$(this).attr("disabled","disabled");
        //判断哪个app点击
        var appId = $(this).attr('data-id');
        var that = $(this);
        $.ajax({
			type:'POST',
			url:$.basepath + "/corpApp/updateCorpDetail.do",
			async:false,
			data:{"id":appId},
			dataType:"json",
			timeout:$.time_out,
			success:function(result, status) {
				if (result.success == true) {
					var dataRst = result.data.dataRst;
					// 设置更新页面数据
					$('#logoUpdate').parent().find("#localImag").attr('src',$.filePath+dataRst.logo);
					$('#appNmUpdate').val(dataRst.appNm);
					$('#contentUpdate').val(dataRst.content);
					$('#urlUpdate').val(dataRst.url);
					$('#productId').text(dataRst.productId);
					$('#updateId').val(dataRst.id);
					$('#updateType').val(result.data.updateType);
					// 设置可见范围信息 
					var jsonObj = JSON.parse(result.data.rtnStr);
					if(jsonObj != ""){
						setDepOrMemberInfo(jsonObj);
					}else {
						$(".selectDepOrMemberSpanUpdate").html('<span class="text-primary">选择部门、人员;不选时，默认为所有人员可见</span>');
					}
					if($('#divSetApp').css('display') == 'block') {
						timer2 = setInterval(slowHide, 5);
					} else {
						$('#divSetApp').show();
						$('.modal .modal-dialog').css('top', '-600px');
						
						timer1 = setInterval(slowShow, 5);
					}
				}else{
					//业务错误
					$(".c_modal_head").toast(result.errorMsg,"error");
				}
			},
			error:function(){
				//异常错误
				$(".c_modal_head").toast("请求失败！","error");
			},
			complete:function(result) {
				//最终执行
				that.removeAttr("disabled");
			}
		});
    });
    // fc设置
    $('.fcbtnSetApp').click(function(){
    	$(this).attr("disabled","disabled");
    	var that = $(this);
        //判断哪个app点击
        var appId = $(this).attr('data-id');
        $.ajax({
			type:'POST',
			url:$.basepath + "/corpApp/fcUpdateDetail.do",
			async:false,
			data:{"id":appId},
			dataType:"json",
			timeout:$.time_out,
			success:function(result, status) {
				if (result.success == true) {
					var dataRst = result.data.dataRst[0];
					// 设置更新页面数据
					$('#logoUpdate').parent().find("#localImag").attr('src',$.filePath+dataRst.functionPicture);
					$('#logoUpdate').attr("disabled","disabled");
					$('#appNmUpdate').val(dataRst.functionName);
					$('#appNmUpdate').attr("readonly","readonly");
					$('#contentUpdate').val(dataRst.functionName);
					$('#contentUpdate').attr("readonly","readonly");
					$('#urlUpdate').val("无");
					$('#urlUpdate').attr("readonly","readonly");
					$('#productId').text("");
					$('#productId').attr("readonly","readonly");
					$('#updateId').val(dataRst.functionId);
					$('#updateType').val(result.data.updateType);
					// 设置可见范围信息 
					var jsonObj = JSON.parse(result.data.rtnStr);
					if(jsonObj != ""){
						setDepOrMemberInfo(jsonObj);
					}else {
						$(".selectDepOrMemberSpanUpdate").html('<span class="text-primary">选择部门、人员;不选时，默认为所有人员可见</span>');
					}
					if($('#divSetApp').css('display') == 'block') {
						timer2 = setInterval(slowHide, 5);
					} else {
						$('#divSetApp').show();
						$('.modal .modal-dialog').css('top', '-600px');
						
						timer1 = setInterval(slowShow, 5);
					}
				}else{
					//业务错误
					$(".c_modal_head").toast(result.errorMsg,"error");
				}
			},
			error:function(){
				//异常错误
				$(".c_modal_head").toast("请求失败！","error");
			},
			complete:function(result) {
				//最终执行
				that.removeAttr("disabled");
			}
		});
    });

    $('#btnSetAppCloase').click(function(){
        timer2 = setInterval(slowHide, 5);
    });

    $('.appItem').mouseover(function(){
        $(this).children('img').hide();
        $(this).children('div').show();
    });

    $('.appItem').mouseout(function(){
        $(this).children('img').show();
        $(this).children('div').hide();
    });
	
	//【可见范围】选择框点击事件
	$('.selectDepOrMemberSpanAdd').click(function(){
//		//取得当前已选中的部门ID和名称
//		//设置 上级部门ID
//		var currentDepId = $("#depForm input[name='parentId']").val();
//		//设置 上级部门名称
//		var currentDepNm = $("#depForm span[name='parentName']").html();
//		selectDep(currentDepId,currentDepNm,"dep");
		selectDep(1);
	});
	
	//【可见范围】选择框点击事件
	$('.selectDepOrMemberSpanUpdate').click(function(){
		selectDep(2);
	});
	
	// 保存校验 
	var appNmAdd="应用名称";
	$("#appNmAdd").addClass("{required:['"+appNmAdd+"']" +
			",maxlength:[10,'"+appNmAdd+"']" +
			"}"); 
	
	var contentAdd="应用内容";
	$("#contentAdd").addClass("{required:['"+contentAdd+"']" +
			",maxlength:[20,'"+contentAdd+"']" +
			"}"); 
	
	var urlAdd="入口地址";
	$("#urlAdd").addClass("{required:['"+urlAdd+"']" +
			",url:['"+urlAdd+"']" +
			"}"); 
	
	// 新增保存按钮
	$("#btnAdd").click(function(){
		if ($("#logoAdd").val() == null || $("#logoAdd").val() == "") {
			$(".c_modal_head").toast("企业logo不能为空！","error");
			return;
		}
		// 画面必须输入校验
		var validateResult = $("#microAppAddForm").validate().form();
		if (validateResult==false) return ;
		
		// 设置新增参数
		var appNm = $('#appNmAdd').val();
		var content = $('#contentAdd').val();
		var url = $('#urlAdd').val();
		var appPeopleMem = $('#appPeopleMemAdd').val();
		var appPeopleDep = $('#appPeopleDepAdd').val();
		var params = {"appNm":appNm,"content":content,"url":url,"appPeopleDep":appPeopleDep,"appPeopleMem":appPeopleMem};
		
		$.ajaxFileUpload({
			method:"post",
			url : $.basepath+"/corpApp/addCorpApp.do",
			data: params,
			dataType:'json',
			fileElementId:"logoAdd",
			success:function(msg){
				if(msg.success == true && msg.data > 0)
				{ 
					$('#navitem3').trigger("click");
				}else {
					$(".c_modal_head").toast("保存失败！","error");
				}
			}
		});
	});
	
	// 更新校验 
	var appNmUpdate="应用名称";
	$("#appNmUpdate").addClass("{required:['"+appNmUpdate+"']" +
			",maxlength:[10,'"+appNmUpdate+"']" +
			"}"); 
	
	var contentUpdate="应用内容";
	$("#contentUpdate").addClass("{required:['"+contentUpdate+"']" +
			",maxlength:[20,'"+contentUpdate+"']" +
			"}"); 
	
	var urlUpdate="入口地址";
	$("#urlUpdate").addClass("{required:['"+urlUpdate+"']" +
			",url:['"+urlUpdate+"']" +
			"}"); 
	
	// 更新按钮
	$("#btnUpdate").click(function(){
		var updateType = $("#updateType").val();
		if (updateType == 0) {
			// 画面必须输入校验
			var validateResult = $("#microAppUpdateForm").validate().form();
			if (validateResult==false) return ;
			
			// 设置更新参数
			var appNm = $('#appNmUpdate').val();
			var content = $('#contentUpdate').val();
			var url = $('#urlUpdate').val();
			var id = $('#updateId').val();
			var productId = $('#productId').text();
			var appPeopleMem = $('#appPeopleMemUpdate').val();
			var appPeopleDep = $('#appPeopleDepUpdate').val();
			var params = {"id":id,"appNm":appNm,"content":content,"productId":productId,
					"url":url,"appPeopleMem":appPeopleMem,"appPeopleDep":appPeopleDep};
			$.ajaxFileUpload({
				method:"post",
				url : $.basepath+"/corpApp/updateCorpApp.do",
				data: params,
				dataType : "json",
				fileElementId:"logoUpdate",
				success:function(msg){
					if(msg.success == true && msg.data > 0)
					{ 
						$('#navitem3').trigger("click");
					}else {
						$(".c_modal_head").toast("修改失败！","error");
					}
				}
			});
		}
		if (updateType == 1) {
			var id = $('#updateId').val();
			var appPeopleMem = $('#appPeopleMemUpdate').val();
			var appPeopleDep = $('#appPeopleDepUpdate').val();
	        $.ajax({
				type:'POST',
				url:$.basepath + "/corpApp/updatefcCorpApp.do",
				data:{"functionId":id,"appPeopleMem":appPeopleMem,"appPeopleDep":appPeopleDep},
				dataType:"json",
				timeout:$.time_out,
				success:function(result, status) {
					if (result.success == true) {
						$('#navitem3').trigger("click");
					}else{
						//业务错误
						$(".c_modal_head").toast(result.errorMsg,"error");
					}
				},
				error:function(){
					//异常错误
					$(".c_modal_head").toast("请求失败！","error");
				},
				complete:function(result) {
					//最终执行
				}
			});
		}
	});
});


//上传图片校验
function setImagePreview(picUrl) {
	//验证上传的图片格式
	checkPicType(picUrl);
	var docObj = $(picUrl);
	var parent = $(picUrl).parent().parent();
	parent.find(".app-logo-text").hide();
	parent.find("#localImag").attr('src',window.URL.createObjectURL(docObj[0].files.item(0)));
}

//验证上传的文件是否为图片格式
function checkPicType(picUrl) {
	var filePath = $(picUrl).val();
	//获得文件后缀名
	var fileType = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
	if(fileType != '.gif' && fileType != '.png' && fileType != '.jpg' && fileType != '.jpeg'){
		$(".c_modal_head").toast("请上传图片格式文件！","error");
	}
}

// 更改启用/停用状态
$(".btnChangeStatus").click(function(){
	var appId = $(this).attr('data-id');
	var type = $(this).attr('data-type');
	var that = this;

	$.ajax({
		type: "POST",
		url : $.basepath+"/corpApp/changeStatus.do",
		data:{"id":appId,"type":type},
		success : function(res)
		{
			res = JSON.parse(res);
			if(res.success == true && res.data > 0)
			{ 
				// 改变启用,进入状态
				if (type == 1) {
					$(that).text("启用");
					$(that).attr('data-type',0);
					$(that).parent().find("a").attr("disabled","true");
				}else{
					$(that).text("停用");
					$(that).attr('data-type',1);
					$(that).parent().find("a").removeAttr("disabled");
				}
				$(".c_modal_head").toast("修改成功！","success");
			}else{
				$(".c_modal_head").toast("修改失败！","error");
			}
		}
	});
});

//function 更改启用/停用状态
$(".fcbtnChangeStatus").click(function(){
	var appId = $(this).attr('data-id');
	var type = $(this).attr('data-type');
	var that = this;

	$.ajax({
		type: "POST",
		url : $.basepath+"/corpApp/changefcStatus.do",
		data:{"id":appId,"type":type},
		success : function(res)
		{
			res = JSON.parse(res);
			if(res.success == true && res.data > 0)
			{ 
				// 改变启用,进入状态
				if (type == 1) {
					$(that).text("启用");
					$(that).attr('data-type',0);
					$(that).parent().find("a").attr("disabled","true");
				}else{
					$(that).text("停用");
					$(that).attr('data-type',1);
					$(that).parent().find("a").removeAttr("disabled");
				}
				$(".c_modal_head").toast("修改成功！","success");
			}else{
				$(".c_modal_head").toast("修改失败！","error");
			}
		}
	});
});

/**
*方法说明：打开【部门/成员】选择页面
*返回值:
*    无
*参数：
*    无
*/
function selectDep(number){
	layer.open({
		  type: 2,
		  title: '选择可见范围',
		  shadeClose: true,
		  shade: 0.4,
		  area: ['900px', '530px'],
//		  content: $.basepath + "/contacts/selectDepOrMember.do?depOrMem=" + depOrMem + "&currentDepId=" + currentDepId + "&currentDepNm=" + currentDepNm,
		  content: $.basepath + "/corpApp/setUserRight.do?type="+number,
	      end:function(index, layero){
//	      	var hidSelectedDep = $("#hidSelectedDep").val();
//	  	    var obj = $.parseJSON(hidSelectedDep);
//	  	    if (obj !=null){
//	  	    	if (depOrMem =="dep"){
//	  	    		$("#depForm input[name='parentId']").val(obj.selectedDepId);
//	  	    		$("#depForm span[name='parentName']").html(obj.selectedDepNm);
//	  	    	}
//	  	    	if (depOrMem =="mem"){
//	  	    		$("#memForm input[name='departmentId']").val(obj.selectedDepId);
//	  	    		$("#memForm span[name='departmentNm']").html(obj.selectedDepNm);
//		  	    }
//	  	    }
	      }
	}); 
}

/**
* 设置可见范围信息
*/
function setDepOrMemberInfo(jsonObj){
	var selectedObj = "";
	for(var i = 0;i < jsonObj.length;i++){
		selectedObj = selectedObj + "<div class='col-sm-4 result-box ng-scope' ng-repeat='current in dingSelector.resultList'>" +
			"<div class='result-text'>" +
				"<small class='ng-binding'>" + jsonObj[i].name +"</small>" + 
			 "<input type='hidden' class='hidId' value='" + jsonObj[i].id + "'/>" + 
			 "<input type='hidden' class='hidNm' value='" + jsonObj[i].name + "'/>" + 
			 "<input type='hidden' class='hidType' value='" + jsonObj[i].type + "'/>" + 
			"</div>" +
			"<div class='result-close' onclick='removeObj(this)'>×</div>" + 
		"</div>";
		$(".selectDepOrMemberSpanUpdate").html(selectedObj);
	}
}