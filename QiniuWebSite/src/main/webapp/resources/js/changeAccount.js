/**
*方法说明：画面初始化
*返回值:
*    无
*参数：
*    无
*/
$(function(){
	//【获取原主管理员】验证码按钮事件
    $('#getMasterChkCd').click(function () {
    	//获取原主管理员验证码
    	getOldMasterChkCd();
    });
    
	//【获取新主管理员】验证码按钮事件
    $('#getNewMasterChkCd').click(function () {
    	//获取新管理员验证码
    	getNewMasterChkCd();
    });
    
	//【确认】按钮事件
    $('#btnConfirm').click(function () {
    	//获取旧管理员ID
    	var oldMasterId = $('#oldMasterId').val() || "";
    	//获取新管理员ID
    	var newMasterId = $('#newMasterId').val() || "";
    	//取得验证码内容
    	var _checkCode = $('#newCheckCode').val() || "";
    	//获取手机号
    	var _phone = $('#newMasterPhone').val();
    	//获取业务场景：修改管理员密码
    	var _businessType = co_change_password;

    	//如果未选择新管理员，提示错误信息
    	if(newMasterId == "") {
    		//获取新管理员ID
        	layer.tips('请选择新管理员', $(".chosen-container"), {
  			  tips: [1, '#fa5b5b'],
  			  time: 4000
  			});
        	return;
    	}
    	//如果新旧管理员为同一个人，提示错误信息
    	if (oldMasterId == newMasterId) {
    		$(".secondMsgDiv").toast("新旧管理员不能是同一个账号，请重新选择","error");
    		return;
    	}
    	//如果验证码不为空
    	if(_checkCode != null && _checkCode != "") {
    		//校验验证码的有效性
    		$.post($.basepath + "/index/checkCode.do", $.param({"phone":_phone,"businessType":_businessType,"code":_checkCode}),function(result) {
    			if (result.success == true) {
    				//变更管理员
    				changeAdmin();
    			}else{
    				layer.tips(result.errorMsg, $("#newCheckCode"), {
    	    			  tips: [1, '#fa5b5b'],
    	    			  time: 4000
    	    			});
    			}
    		}, "json");
    	}
    	else {
    		layer.tips('验证码不能为空', $("#newCheckCode"), {
    			  tips: [1, '#fa5b5b'],
    			  time: 4000
    			});
    	}
    });
	
	//【下一步】按钮事件
    $('#nextStep').click(function () {
    	//取得验证码内容
    	var _checkCode = $('#checkCode').val() || "";
    	//获取手机号
    	var _phone = $('#masterPhone').val();
    	//获取业务场景：修改管理员密码
    	var _businessType = co_change_password;
    	
    	if(_checkCode != null && _checkCode != "") {
    		//校验验证码的有效性
    		$.post($.basepath + "/index/checkCode.do", $.param({"phone":_phone,"businessType":_businessType,"code":_checkCode}),function(result) {
    			if (result.success == true) {
    	    		$('#firstStep').hide();
    	            $('#secondStep').show();
    	        	$.post($.basepath +"/contacts/getMemberByOrgId.do", function(data) {
    	      		  $("#orgMember").initTbItems({
    	      			   tbData:data.data,
    	      			   value:"userId",
    	      			   text:"userNm",
    	      			   value2:"phone",
    	      			   blank:{need:true}
    	      		  });
    	      		  $("#orgMember").chosen({search_contains:true,allow_single_deselect:true,no_results_text:"没有找到"});
    	      		//【选择新主管理员】下拉框change事件
    	      	    $('#orgMember').change(function () {
    	      	    	//取得电话号码
    	      	    	var phone = $("#orgMember").find("option:selected").attr("phone");
    	      	    	//取得用户ID
    	      	    	var memberId = $("#orgMember").find("option:selected").val();
    	      	    	$('#newMasterId').val(memberId);
    	      	    	$('#newMasterPhone').val(phone);
    	      	    	$('#hidNewMasterPhone').val(phone);
    	      	    });
    	      	  }, "json");
    			}else{
    				layer.tips(result.errorMsg, $("#checkCode"), {
    	    			  tips: [1, '#fa5b5b'],
    	    			  time: 4000
    	    			});
    			}
    		}, "json");

    	}
    	else {
    		layer.tips('验证码不能为空', $("#checkCode"), {
    			  tips: [1, '#fa5b5b'],
    			  time: 4000
    			});
    	}
    });
});

/**
*方法说明：获取原主管理员验证码
*返回值:
*    无
*参数：
*    无
*/
function getOldMasterChkCd(){
	//获取手机号
	var _phone = $('#masterPhone').val() || "";
	//获取业务场景：修改管理员密码
	var _businessType = co_change_password;
	//如果管理员手机号为空,提示错误信息
	if(_phone == "") {
		layer.tips('手机号不能为空', $("#masterPhone"), {
			  tips: [1, '#fa5b5b'],
			  time: 4000
			});
		return;
	}
	//查询数据
	$.post($.basepath + "/index/getCheckCode.do", $.param({"phone":_phone,"businessType":_businessType}),function(result) {
		if (result.success == true) {
			$(".firstMsgDiv").toast("获取验证码成功","success");
	    	//显示验证码计时器
	    	showOldTimer($.chkcd_validity);
		}else{
			$(".firstMsgDiv").toast(result.errorMsg,"error");
		}
	}, "json");
}

/**
*方法说明：获取新管理员验证码
*返回值:
*    无
*参数：
*    无
*/
function getNewMasterChkCd(){
	//获取手机号
	var _phone = $('#newMasterPhone').val();
	//获取业务场景：修改管理员密码
	var _businessType = co_change_password;
	//如果管理员手机号为空,提示错误信息
	if(_phone == "") {
		layer.tips('手机号不能为空，请先选择新管理员', $("#newMasterPhone"), {
			  tips: [1, '#fa5b5b'],
			  time: 4000
			});
		return;
	}
	//查询数据
	$.post($.basepath + "/index/getCheckCode.do", $.param({"phone":_phone,"businessType":_businessType}),function(result) {
		if (result.success == true) {
			$(".secondMsgDiv").toast("获取验证码成功","success");
	    	//显示验证码计时器
	    	showNewTimer($.chkcd_validity);
		}else{
			$(".secondMsgDiv").toast(result.errorMsg,"error");
		}
	}, "json");
}

/**
*方法说明：获取短信倒计时
*返回值:
*    无
*参数：
*    无
*/
function showOldTimer(time){
    var Timer = "";
    $("#getMasterChkCd").addClass("hide");
    $("#timeChange").removeClass("hide");
    _countDown();
    function _countDown(){
        clearTimeout(Timer);
        if(time<=0){
        	$("#getMasterChkCd").removeClass("hide");
        	$("#timeChange").addClass("hide");
        }else{
           $("#timeChange").html(time+" 秒");
           time --;
           Timer = setTimeout(function(){
               _countDown();
           },1000);
        }
    }
}

/**
*方法说明：获取短信倒计时
*返回值:
*    无
*参数：
*    无
*/
function showNewTimer(time){
    var Timer = "";
    $("#getNewMasterChkCd").addClass("hide");
    $("#newTimeChange").removeClass("hide");
    _countDown();
    function _countDown(){
        clearTimeout(Timer);
        if(time<=0){
        	$("#getNewMasterChkCd").removeClass("hide");
        	$("#newTimeChange").addClass("hide");
        }else{
           $("#newTimeChange").html(time+" 秒");
           time --;
           Timer = setTimeout(function(){
               _countDown();
           },1000);
        }
    }
}

/**
*方法说明：管理员变更
*返回值:
*    无
*参数：
*    无
*/
function changeAdmin(){
	$.post($.basepath + "/setup/changeAdmin.do", $("#newAdminForm").serialize(),function(result) {
		if (result.success == true) {
			alert("管理员变更成功，请以新管理员身份重新登录。");
	    	window.location.href = $.basepath + '/index/loginOut.do';
		}else{
			$(".secondMsgDiv").toast("管理员变更失败。","error");
		}
	}, "json");
}

$.fn.extend({
	/**
	 * 用数据填充下拉框（清除原有选项）
	 * 调用示例：
	 * $("#selectId").initTbItems({
	 		tbData:tbData
	 * 		value:"testvalue",
	 * 		text:"testtext",
	 * 		blank:{need:true, text:"----请选择----", value:""},
	 * 		callback:function(){xxxxx}
	 * });
	 * options如下：
	 * 	tbData(必选):数据
	 * 	value(必选):下拉框的value字段
	 * 	text(必选):下拉框显示的内容字段
	 * 	blank(可选):空选项相关
	 * 		need(可选)：是否需要，默认为false
	 * 		text(可选)：空选项显示的内容，默认为"请选择"
	 * 		value(可选):空选项的值，默认为""
	 *	callback(可选):可选，在下拉框数据加载完成后执行
	 */
	initTbItems:function(options) {
		var $this = $(this);
		$this.empty();
		options = options || {};
		options.blank = options.blank || {};
		if (options.blank.need) {
			var option = $("<option value='" + (options.blank.value || "") + "'>" + (options.blank.text || "请选择") + "</option>");
			$this.append(option);
		}
		if (!options.value || !options.text) {
			return;
		}
		options.callback = options.callback || function(){};
		$this.fillSelectOptions(options.tbData, options.value, options.text, options.attr, options.value2);
		options.callback($this);
	},
	/**
	 * data:数据
	 * value：data中的字段名，用于填充在value中
	 * name：data中的字段名，用于显示
	 * attr:option自定义的属性
	 */
	fillSelectOptions:function(data, value, name, attr, value2) {
		if ($.isArray(data)) {
			for (var i = 0; i < data.length; i++) {
				var option = $("<option></option>");
				option.val(data[i][value]).text(data[i][name] + " (" + data[i][value2] +  ")").appendTo($(this));
				option.attr(value2, data[i][value2] || "");
				if (attr) {
					for (var key in attr) {
						option.attr(key, data[i][attr[key]] || "");
					}
				}
			}
			$(this).initSelectedOption();
		}
	},
	/**
	 * 根据下拉框的dval，初始化下拉框的值
	 */
	initSelectedOption:function() {
		var defaultValue = $(this).attr("dval");
		if (defaultValue && defaultValue != "") {
			$(this).find("option").prop("selected", false);
			$(this).find("option[value='" + defaultValue + "']").prop("selected", true);
		} else {
			$(this).find("option:first").prop("selected", true);
		}
	}
});