var orgList = null;
/**
*方法说明：画面初始化
*返回值:
*    无
*参数：
*    无
*/
$(function(){
	
	//用户登录
    $("#loginBtn").click(function(){
    	doLogin();
    });
});
/*
 * 回车
 * 
 */
function EnterPress(e){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
		doLogin();
	} 
}

/**
 * 用户登录
 */
function doLogin(){
    var _phone = $('#phone').val();
    var _pwd = $('#pwd').val();
    if(!_phone){
        $(".login_phone").toast("登录账号不能为空","error");
       return;
    }
    if(!_pwd){
      $(".login_phone").toast("密码不能为空","error");
      return;
    }
	
	//用户成功登录后跳转到主页
	$.ajax({
		async:false,
		url: $.basepath + "/index/doLogin.do",
		method:"post",
        data: {"loginNm":_phone,"password":_pwd},
		dataType:"json",
		success:function(result, status) {
			if (result !=null && result.success) {
				window.location.href = $.basepath + "/index/index.do"; 
						//$.basepath + "/indexLook.jsp"
			}else{
				$(".login_phone").toast(result.msg,"error");
			}
		}
	});
}