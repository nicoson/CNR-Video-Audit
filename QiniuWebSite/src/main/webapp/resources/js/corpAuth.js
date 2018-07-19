
$(function(){
	// 默认提示标签不显示
    $('.dd-glyphicon').hide();
    // 设置选择地址控件长度
    $(".city-picker-span").css("width","359px");
    $('.dd-half-width').focus(function(){
       console.log('focus');
       $(this).parent().children('.dd-glyphicon').hide();
    });

    $('.dd-half-width').blur(function(){
       console.log('blur');
       if ($(this).val()){
         $(this).parent().children('.dd-input-true').show();
       } else {
         $(this).parent().children('.dd-input-error').show();
       }
    });   
    // 提交审核事件
    $('.btn-success').click(function(){
    	if ($('#checkBoxCheck').prop("checked")) {
	    	// 数据验证
	    	if (updateCheck()) {
	    		// 设置更新参数
	    		var regNum = $('#regNum').val();
	    		var trade =$('#trade option:selected').text();
	    		var boss = $('#boss').val();
	    		var bossId = $('#bossId').val();
	    		var capital = $('#capital').val();
	    		var address = $(".title").text();
				var arr = address.split("/");
				var province = arr[0];
				var area = arr[1];
	    		var params = {"trade":trade,"regNum":regNum,
	    				"boss":boss,"bossId":bossId,"capital":capital,
	    				"province":province,"area":area};
	    		$.ajaxFileUpload({
	    			method:"post",
	    			url : $.basepath+"/corpAuth/updateCorpAuth.do",
	    			data: params,
	    			dataType : "json",
	    			fileElementId:["licenseUpdate","pic2"],
	    			success:function(msg){
	    				data= JSON.parse(msg.data);
	    				if(data.success == true)
	    				{ 
	    					$(".checkbox").toast(data.errorMsg,"success");
	    					window.location.href=$.basepath +'/index/index.do';
	    				}else {
	    					$(".checkbox").toast(data.errorMsg,"error");
	    				}
	    				
	    			}
	    		});
			}
    	}else {
			$(".checkbox").toast("请先阅读且同意XXX条约！","error");
		}
    });
});

/**
* 数据验证
*/
function updateCheck(){
	var licenseUpdate = $('#licenseUpdate').parent().parent().parent().find(".dd-img").attr("src");
	if (!licenseUpdate) {
		$('#licenseUpdate').parent().parent().children('.dd-input-error').show();
		return false;
	}else{
		$('#licenseUpdate').parent().parent().children('.dd-input-error').hide();
	}
	var regNum = $('#regNum').val();
	if (!regNum) {
		$('#regNum').parent().children('.dd-input-error').show();
		return false;
	}
	var trade = $('#trade').val();
	if (!trade) {
		$('#trade').parent().children('.dd-input-error').show();
		return false;
	}
	var boss = $('#boss').val();
	if (!boss) {
		$('#boss').parent().children('.dd-input-error').show();
		return false;
	}
	var bossId = $('#bossId').val();
	if (!bossId) {
		$('#bossId').parent().children('.dd-input-error').show();
		return false;
	}
	var capital = $('#capital').val();
	if (!capital) {
		$('#capital').parent().children('.dd-input-error').show();
		return false;
	}
	var address = $(".title").text();
	if(address != null && address != "") {
		var arr = address.split("/");
		if (arr[1] == undefined) {
			$('.areaStrError').show();
		}else {
			$('.areaStrError').hide();
		}
	}
	var pic2 = $('#pic2').parent().parent().parent().find(".dd-img").attr("src");
	if (!pic2) {
		$('#province').parent().parent().children('.dd-input-error').show();
		return false;
	}else{
		$('#province').parent().parent().children('.dd-input-error').hide();
	}
	return true;
};


//上传图片校验
function setImagePreview(picUrl) {
	//验证上传的图片格式
	if (checkPicType(picUrl)) {
		var docObj = $(picUrl);
		var parent = $(picUrl).parent().parent().parent();
		parent.find(".dd-img").attr('src',window.URL.createObjectURL(docObj[0].files.item(0)));
	}
}

//验证上传的文件是否为图片格式
function checkPicType(picUrl) {
	var filePath = $(picUrl).val();
	//获得文件后缀名
	var fileType = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
	if(fileType != '.gif' && fileType != '.png' && fileType != '.jpg' && fileType != '.jpeg'){
		$(".checkbox").toast("请上传图片格式文件！","error");
		return false;
	}
	return true;
}