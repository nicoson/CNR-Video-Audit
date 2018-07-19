
var imgCode;

$(function(){
	
	$(".dd-submit-phone-error").html("").addClass("dd-hide");
    //初始校验
	if ($ && $.validator) {
		$.validator.setDefaults({
			errorElement:"img",
			errorClass:"error_validate"
		});
	}
	checkInput();
	createCode();
	//手机号码
	var phone='';
	//第一步提交
    $('#submitPhoneBtn').click(function(){
    	//加载下拉选
    	$("#questionOne").initDicItems({
    		dicCode:"question_one",
    	  	blank:{need:true}
    	});
    	$("#questionTwo").initDicItems({
    		dicCode:"question_two",
    	  	blank:{need:true}
    	});
    	$("#questionThree").initDicItems({
    		dicCode:"question_three",
    	  	blank:{need:true}
    	});
    	
    	phone =$('#adminMobile').val();
    	var adminCheckCode =$('#adminCheckCode').val();
    	var businessType = register;
    	// 画面必须输入校验
    	var validateResult = $("#phoneForm").validate({ignore:""}).form();
    	if (validateResult==false) return ;
    	
    	$('#submitPhoneBtn').attr("disabled",true);
    	//查询数据
    	var param = $.param({"phone":phone,"code":adminCheckCode,"businessType":businessType});
    	$.ajax({
    		type:'POST',
    		url:$.basepath + "/index/checkCode.do",
    		data:param,
    		dataType:"json",
    		timeout:$.time_out,
    		success:function(result, status) {
    			if (result.success == true) {
    				//手机画面 隐藏
        	        $('#phoneContainer').hide();
        	        //完善详细画面 显示
        	        $('#fromContainer').show();
        	    	$(".city-picker-span").css("width","302px");
    			}else{
    				//业务错误
    				$(".dd-submit-phone-error").html(result.errorMsg).removeClass("dd-hide");
    			}
    		},
    		error:function(){
    			//异常错误
    			$(".dd-submit-phone-error").html("请重新获取短信校验码").removeClass("dd-hide");
    		},
    		complete:function(result) {
    			//最终执行
    			$("#submitPhoneBtn").attr("disabled",false);
    		}
    	});
    });

    //第二部提交
    $('#submitBtn').click(function(){

    	if (checkInputTwo()==false){
    		return;
    	}
    	
    	var question1Id =$('#questionOne').val();
    	var question2Id =$('#questionTwo').val();
    	var question3Id =$('#questionThree').val();
    	var answer1 =$('#answer1').val();
    	var answer2 =$('#answer2').val();
    	var answer3 =$('#answer3').val();
    	var param = $.param({"question1Id":question1Id,"answer1":answer1
    						,"question2Id":question2Id,"answer2":answer2
    						,"question3Id":question3Id,"answer3":answer3
    						,"phone":phone
    						});
    	
    	$('#submitBtn').attr("disabled",true);
    	$.ajax({
    		type:'POST',
    		url:$.basepath + "/index/peUserSecurityAdd.do",
    		data:param,
    		dataType:"json",
    		timeout:$.time_out,
    		success:function(result, status) {
    			if (result.success == true) {
    		        $('#fromContainer').hide();
    		        $('#doneContainer').show();	
    			}else{
    				//业务错误
    				$("#isv-alert-2").html(result.errorMsg).removeClass("dd-hide");
    			}
    		},
    		error:function(){
    			//异常错误
				$("#isv-alert-2").html("请求失败！").removeClass("dd-hide");
    		},
    		complete:function(result) {
    			//最终执行
    			$("#submitBtn").attr("disabled",false);
    		}
    	});

    });

    //跳转到首页
    $('#doneBtn').click(function(){
        window.parent.location.href = $.basepath + "/index/welcome.do";
    });
    
    //发送短信校验码
    $('#getCheckCode').click(function(){
    	// 画面必须输入校验
    	var validateResult = $("#phoneForm").validate({ignore:"#adminCheckCode"}).form();
    	if (validateResult==false) return ;
    	
		countDown($.chkcd_validity);
		//获取短信校验码
		getIndentifyingCode();
    	
    });
});

/**
 * 获取短信倒计时
 * @param time
 */
function countDown(time){
    var Timer = "";
    $("#getCheckCode").attr("disabled",true);
    _countDown();
    function _countDown(){
        clearTimeout(Timer);
        if(time<=0){
        	 $("#getCheckCode").attr("disabled",false);
        	 $("#adminCheckTime").addClass("dd-hide");
        }else{
        	$("#adminCheckTime").removeClass("dd-hide");
           $("#adminCheckTime").html(time+" s");
           time --;
           Timer = setTimeout(function(){
               _countDown();
           },1000);
        }
    }
}

/**
 * 短信校验码
 */
function getIndentifyingCode(){
	$(".dd-submit-phone-error").html("").addClass("dd-hide");
	var _phone = $('#adminMobile').val();
	var _businessType = register;
	var param = $.param({"phone":_phone,"businessType":_businessType});
	//查询数据
	$.ajax({
		type:'POST',
		url:$.basepath + "/index/getCheckCode.do",
		data:param,
		dataType:"json",
		timeout:$.time_out,
		success:function(result, status) {
			if (result.success == true) {
			}else{
				//业务错误
				$(".dd-submit-phone-error").html(result.errorMsg).removeClass("dd-hide");
			}
		},
		error:function(){
			//异常错误
			$(".dd-submit-phone-error").html("请求失败！").removeClass("dd-hide");
		},
		complete:function(result) {
			//最终执行
			$("#submitPhoneBtn").removeAttr("disabled");
		}
	});
}

/**
 * 创建code
 */
function createCode(){       
	imgCode = "";      
    var codeLength = 4;//验证码的长度
    var selectChar = new Array(1,2,3,4,5,6,7,8,9,'a','b','c','d','e','f','g','h','j','k','l','m','n','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');      
          
    for(var i=0;i<codeLength;i++) {
       var charIndex = Math.floor(Math.random()*60);      
       imgCode +=selectChar[charIndex];
    }      
    if(imgCode.length != codeLength){      
      createCode();      
    }
    showCheck(imgCode);
}

/**
 * 显示图片验证码
 * @param a
 */
function showCheck(a){/* 显示验证码图片 */
	$("#codeImg").val(a);
	var c = document.getElementById('myCanvas');
  var ctx = c.getContext("2d");
	ctx.clearRect(0,0,1000,1000);
	ctx.font = "80px Arial";
	ctx.fillText(a,0,100);
}

/**
 * 校验初始化
 */
function checkInput(){
	//校验初始化
	//员工校验
	var adminMobile="手机号码";
	$("#adminMobile").addClass("{required:['"+adminMobile+"']" +
			",mobileOrTel:['"+adminMobile+"']" +
			",maxlength:[36,'"+adminMobile+"']" +
			"}"); 
	
	var picCode="图片验证码";
	$("#picCode").addClass("{required:['"+picCode+"']" +
			",equalToUpperCase:['#codeImg','"+picCode+"','图片']" +
			",maxlength:[36,'"+picCode+"']" +
			"}"); 
	
	var adminCheckCode="短信校验码";
	$("#adminCheckCode").addClass("{required:['"+adminCheckCode+"']" +
			",maxlength:[36,'"+adminCheckCode+"']" +
			"}"); 
}

/**
 * 显示协议
 * @param protocolType 协议类型
 */
function showProtocolPdf(protocolType){
	//取得文件流
	var encodeUrl = encodeURIComponent($.basepath + "/index/showProtocolPdfByProtocolType.do?protocolType=" + protocolType);
	//使用pdfjs插件打开文件
	var urlSrc=$.basepath + "/resources/pdfjs/web/viewer.html?file="+encodeUrl+"&pdfTitle="+"协议文件阅读";
	parent.window.open(urlSrc,"协议");
}

/**
 * 初始化校验
 */
function checkInputTwo(){
	$("#isv-alert-2").html("").addClass("dd-hide");
	
	var question1 =$('#questionOne').val();
	var question2 =$('#questionTwo').val();
	var question3 =$('#questionThree').val();
	var answer1 =$('#answer1').val();
	var answer2 =$('#answer2').val();
	var answer3 =$('#answer3').val();
	
	//
	if (question1 ==""){	
		$("#isv-alert-2").html("请选择密保问题1").removeClass("dd-hide");
		return false;
	}
	if (answer1 ==""){	
		$("#isv-alert-2").html("问题1的答案不能为空").removeClass("dd-hide");
		return false;
	}
	if (question2 ==""){	
		$("#isv-alert-2").html("请选择密保问题2").removeClass("dd-hide");
		return false;
	}
	if (answer2 ==""){	
		$("#isv-alert-2").html("问题2的答案不能为空").removeClass("dd-hide");
		return false;
	}
	if (question3 ==""){	
		$("#isv-alert-2").html("请选择密保问题3").removeClass("dd-hide");
		return false;
	}
	if (answer3 ==""){	
		$("#isv-alert-2").html("问题3的答案不能为空").removeClass("dd-hide");
		return false;
	}
	
//	var reg = /(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}/;
//	if (!reg.exec(password)){	
//		$("#isv-alert-2").html("密码必须包含数字、大小写字母、长度不低于6位").removeClass("dd-hide");
//		return false;
//	}
	//条款
	if ($("#check:checked").length==0){
		$("#isv-alert-2").html("请勾选条款").removeClass("dd-hide");
		return false;
	}
	return true;
}