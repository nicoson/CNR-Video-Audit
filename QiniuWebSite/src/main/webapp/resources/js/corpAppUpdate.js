$(function(){
	// 更新校验 
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
	
	// 更新按钮
	$("#btnUpdate").click(function(){
		if ($("#logo").val() == null || $("#logo").val() == "") {
			alert("企业logo不能为空!");
			return;
		}
		// 画面必须输入校验
		var validateResult = $("#microAppUpdateForm").validate().form();
		if (validateResult==false) return ;
		
		$.ajax({
			type: "POST",
			url : $.basepath+"/corpApp/addCorpApp.do",
			data:$("#microAppUpdateForm").serialize(),
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