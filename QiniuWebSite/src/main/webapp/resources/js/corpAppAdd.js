/**
*方法说明：画面初始化
*返回值:
*    无
*参数：
*    无
*/
$(function(){
	//【可见范围】选择框点击事件
	$('.selectDepOrMemberSpan').click(function(){
//		//取得当前已选中的部门ID和名称
//		//设置 上级部门ID
//		var currentDepId = $("#depForm input[name='parentId']").val();
//		//设置 上级部门名称
//		var currentDepNm = $("#depForm span[name='parentName']").html();
//		selectDep(currentDepId,currentDepNm,"dep");
		selectDep();
	});
	
	// 保存校验 
	var appName="应用名称";
	$("#appNm").addClass("{required:['"+appName+"']" +
			",maxlength:[10,'"+appName+"']" +
			"}"); 
	
	var content="应用内容";
	$("#content").addClass("{required:['"+content+"']" +
			",maxlength:[20,'"+content+"']" +
			"}"); 
	
	var url="入口地址";
	$("#url").addClass("{required:['"+url+"']" +
			",url:['"+url+"']" +
			"}"); 
	
	// 新增保存按钮
	$("#btnAdd").click(function(){
		if ($("#logo").val() == null || $("#logo").val() == "") {
			alert("企业logo不能为空!");
			return;
		}
		// 画面必须输入校验
		var validateResult = $("#microAppAddForm").validate().form();
		if (validateResult==false) return ;
		
		$.ajax({
			type: "POST",
			url : $.basepath+"/corpApp/addCorpApp.do",
			data:$("#microAppAddForm").serialize(),
			success : function(res)
			{
				res = JSON.parse(res);
				if(res.success == true && res.data == 0)
				{ 
					layer.closeAll('page');
				}else{
					alert("提交失败");
				}
			}
		});
	});
});

// 上传图片校验
function setImagePreview(picUrl) {
	//验证上传的图片格式
	checkPicType(picUrl);
	var docObj = $(picUrl);
	var parent = $(picUrl).parent().parent();
	parent.find(".app-logo-text").hide();
	parent.find("#localImag").attr('src',window.URL.createObjectURL(docObj[0].files.item(0)));
}

// 验证上传的文件是否为图片格式
function checkPicType(picUrl) {
	var filePath = $(picUrl).val();
	//获得文件后缀名
	var fileType = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
	if(fileType != '.gif' && fileType != '.png' && fileType != '.jpg' && fileType != '.jpeg'){
		alert("请上传图片格式文件");
	}
}

/**
*方法说明：打开【部门/成员】选择页面
*返回值:
*    无
*参数：
*    无
*/
function selectDep(){
	$.post($.basepath + "/corpApp/setUserRight.do?" , {}, function(str){
	layer.open({
		  type: 2,
		  title: '选择可见范围',
		  shadeClose: true,
		  shade: 0.4,
		  area: ['680px', '530px'],
//		  content: $.basepath + "/contacts/selectDepOrMember.do?depOrMem=" + depOrMem + "&currentDepId=" + currentDepId + "&currentDepNm=" + currentDepNm,
		  content: str,
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
	}); 
}