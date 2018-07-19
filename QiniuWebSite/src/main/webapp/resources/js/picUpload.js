/**
*方法说明：画面初始化
*返回值:
*    无
*参数：
*    无
*/
$(function(){
	//初始化文件上传组件
	$('.selectedPicPath').ace_file_input({
		no_file:'请选择图片 ...',
		btn_choose:'选择',
		btn_change:'更改',
		droppable:false,
		onchange:null,
		thumbnail:false, //| true | large
		whitelist:'gif|png|jpg|jpeg',
	});
	
});

/**
*方法说明：图片显示
*返回值:
*    无
*参数：
*    无
*/
function setImagePreview(picUrl) {
	//验证上传的图片格式
	checkPicType(picUrl);
	var docObj = $(picUrl);
	var parent = $(picUrl).parent().parent();
	parent.find(".preview").show();
	parent.find(".localImag").show();
	parent.find(".preview").css({ display: "#block",width: "210px",height:"150px"});
	parent.find(".preview").attr('src',window.URL.createObjectURL(docObj[0].files.item(0)));
}

/**
*方法说明：验证上传的文件是否为图片格式
*返回值:
*    无
*参数：
*    无
*/
function checkPicType(picUrl) {
	var filePath = $(picUrl).val();
	//获得文件后缀名
	var fileType = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
	if(fileType != '.gif' && fileType != '.png' && fileType != '.jpg' && fileType != '.jpeg'){
		$(picUrl).tips({
			side:3,
	        msg:'请上传图片格式的文件',
	        bg:'#AE81FF',
	        time:3
	    });
	}
}

/**
*方法说明：删除当前选中的文件
*返回值:
*    无
*参数：
*    无
*/
function delSelectedPic(delPic) {
	var selectedPicParent = $(delPic).parent().parent();
	selectedPicParent.find(".preview").attr('src',"");
	selectedPicParent.find(".preview").hide();
	selectedPicParent.find(".localImag").hide();
}