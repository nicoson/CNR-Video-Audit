$(function(){
	$(".no_select_month").click(function(){
		$("#divMonth").find(".no_select_month").removeClass("select_month");
		$(this).addClass("select_month");
		$("#months").html($(this).html());
		//更新支付
		changePay();
	});
	
	//减数量事件
	$("#btnSub").click(function(){
		var amount = parseFloat($("#amount").val());
		if (amount>1){
			amount = amount -1;
		}else{
			return;
		}
		$("#amount").val(amount);
		//更新数量
		changeAmount();
	});
	
	//减数量事件
	$("#btnAdd").click(function(){
		var amount = parseFloat($("#amount").val());
		amount = amount + 1;
		$("#amount").val(amount);
		//更新数量
		changeAmount();
	});
	//支付
	$("#btnConfirm").click(function(){
		if($("#btnConfirm").hasClass("login_button_disabled")){
			return false;
		}
		//支付
		payMoney();
	});
	
	//取消
	$("#btnCancel").click(function(){
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	});
	
	changePay();
});

/**
 * 处理小数点
 */
function fomatFloat(src,pos){
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);
} 

/**
 * 更新数量
 */
function changeAmount(){
	$("#amount").val($("#amount").val().replace(/\D/g,''));
	var amount = $("#amount").val();
	if (amount ==""){
		$("#amount").val("1");
	}
	$("#quantity").html($("#amount").val());
	//更新支付
	changePay();
}

/**
 * 计算折扣系数
 */
function getDiscount(quantity){
	var ret =1.00;
	$("li[name='discountInfo']").each(function() {
		var lowLimit = parseFloat($(this).find("[name='lowLimit']").html());
		//var upperLimit = parseFloat($(this).find("[name='upperLimit']").html());
		var discount = parseFloat($(this).find("[name='discount']").html());
		if (quantity>=lowLimit){
			ret = discount;
		}
	});
	return ret;
}


/**
 * 更新支付
 */
function changePay(){
	//数量
	var quantity = parseFloat($("#quantity").html());
	//月数
	var months = parseFloat($("#months").html());
	//单价
	var price = parseFloat($("#price").html());
	//折扣系数
	var discount = getDiscount(quantity);
	
	//价格计算
	//原始金额
	var pay= quantity * months * price;
	//原价
	var oldPay = fomatFloat(pay,2) ;
	$("#oldPay").html(oldPay);
	//折扣
	var disPay = fomatFloat((pay * (1-discount)),2);
	$("#disPay").html(disPay);
	//本次支付
	var newPay = fomatFloat((pay * discount),2);
	$("#newPay").html(newPay);
}

/**
 * 支付
 */
function payMoney(){
	var flag = false;
	var param = $.param({"productId":$("#productId").val()});
	$.ajax({
		type:'POST',
		url:$.basepath + "/index/coProductOrganizeCheck.do",
		data:param,
		async:false,
		dataType:"json",
		timeout:$.time_out,
		success:function(result, status) {
			if (result.success == true) {
				//业务正确
				flag = true;
			}else{
				//业务错误
				$(".c_modal").toast(result.errorMsg,"error");
			}
		},
		error:function(){
			//异常错误
			$(".c_modal").toast("请求失败！","error");
		},
		complete:function(result) {
			//最终执行
		}
	});
	//是否未开通
	if (flag == false){
		return false;
	}
	
	if (checkInput() ==false){
		return false;
	}

	$("#btnConfirm").addClass("login_button_disabled");
	
	layer.confirm('确定购买？', {
		  btn: ['确定','取消']
		}, function(e){
			$("#btnConfirm").addClass("login_button_disabled");
			//产品id
			var productId = $("#productId").val();
			//月数
			var months = $("#months").html();
			//数量
			var quantity = $("#quantity").html();
			//最终支付
			var payMoney = $("#newPay").html();
			
			var param = $.param({"productId":productId,"months":months,"quantity":quantity,"payMoney":payMoney});
			$.ajax({
				type:'POST',
				url:$.basepath + "/index/coProductOrganizeAdd.do",
				data:param,
				dataType:"json",
				timeout:$.time_out,
				success:function(result, status) {
					if (result.success == true) {
				    	var index = parent.layer.getFrameIndex(window.name);
				        parent.layer.close(index);
					}else{
						//业务错误
						$(".c_modal").toast(result.errorMsg,"error");
					}
				},
				error:function(){
					//异常错误
					$(".c_modal").toast("请求失败！","error");
				},
				complete:function(result) {
					//最终执行
					$("#btnConfirm").removeClass("login_button_disabled");
				}
			});
		layer.close(e);
		}, function(){
			$("#btnConfirm").removeClass("login_button_disabled");
		});
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
function checkInput(){
	var months = $("#months").html();
	var quantity = $("#quantity").html();
	var price = $("#price").html();
	var newPay = $("#newPay").html();
	var balance = $("#balance").html();
	
	//月份
	if (!/^\d+$/.test(quantity) && parseFloat(quantity) < 1){	
		$(".c_modal").toast("至少购买1个月","error");
		return false;
	}
	//数量
	if (!/^\d+$/.test(months) && parseFloat(months) < 1){
		$(".c_modal").toast("至少购买1个","error");
		return false;
	}
	//条款
	if ($("#check:checked").length==0){
		$(".c_modal").toast("请勾选条款","error");
		return false;
	}
	//余额和支付信息
	if (parseFloat(balance)<0 || parseFloat(balance) < parseFloat(newPay)){
		$(".c_modal").toast("余额不足请确充值","error");
		return false;
	}
	return true;
}
