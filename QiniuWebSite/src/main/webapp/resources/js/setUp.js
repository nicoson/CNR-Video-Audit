$(function(){
    $('#btnSave').click(function(){
    	var rmrk = $("#rmrk").val();
    	$.ajaxFileUpload({
			type:'POST',
			url:$.basepath +"/coCloud/coOrganizeUpdateData.do",
			data:{"rmrk":rmrk},
			fileElementId:'logoUpload',
			success:function(msg){
				if (msg.responseText != "0") {
					$("#myform .c_modal_head").toast("保存成功","success");
				} else {
					$("#myform .c_modal_head").toast("保存失败","error");
				}
			}
		});
    });
});

//上传图片校验
function setImagePreview(picUrl) {
	//验证上传的图片格式
	checkPicType(picUrl);
	var docObj = $(picUrl);
	var parent = $(picUrl).parent().parent();
	parent.find(".app-logo-text").hide();
	parent.find(".ng-scope").attr('src',window.URL.createObjectURL(docObj[0].files.item(0)));
}

//验证上传的文件是否为图片格式
function checkPicType(picUrl) {
	var filePath = $(picUrl).val();
	//获得文件后缀名
	var fileType = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
	if(fileType != '.gif' && fileType != '.png' && fileType != '.jpg' && fileType != '.jpeg'){
		$("#myform .c_modal_head").toast("请上传图片格式文件","error");
	}
}