$(function(){
	$(".no_select_month").click(function(){
		$("#divMonth").find(".no_select_month").removeClass("select_month");
		$(this).addClass("select_month");
		$("#addMonths").val($(this).html());
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
 * 更新数量
 */
function changeAmount(){
	$("#amount").val($("#amount").val().replace(/\D/g,''));
	var amount = $("#amount").val();
	if (amount ==""){
		$("#amount").val("1");
	}
	$("#newQuantity").html($("#amount").val());
	var quantity = parseFloat($("#quantity").html());
	var newQuantity = parseFloat($("#newQuantity").html());
	var addQuantity = newQuantity - quantity;
	//调整数量
	$("#addQuantity").val(addQuantity);
	//更新支付
	changePay();
}

/**
 * 处理小数点
 */
function fomatFloat(src,pos){
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);
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
 * 更新截止日期
 */
function changeMonths(){
	var endAt = $("#endAt").html();
	var addMonths = parseInt($("#addMonths").val());
	var endAtList = endAt.split("-");
	//取得当前截止日期
	if (endAtList !=null && endAtList.length==3){
		var years = parseInt(endAtList[0]);
		var months = parseInt(endAtList[1]);
		var days = parseInt(endAtList[2]);
		var newDate= new Date(years,months- 1 + addMonths,days);
		var newEndAt = newDate.getFullYear() + "-" + (newDate.getMonth() + 1) +"-" + newDate.getDate();
		$("#newEndAt").html(newEndAt);
	}
	
}

/**
 * 更新支付
 */
function changePay(){
	//新的 数量
	var quantity = parseFloat($("#newQuantity").html());
	//增加的数量
	var addQuantity = parseFloat($("#addQuantity").val());
	//月数
	var months = parseFloat(getMonths());
	//单价
	var price = parseFloat($("#price").html());
	//折扣系数
	var discount = getDiscount(quantity);
	
	//价格计算
	if (addQuantity >0){
		
	}else{
		addQuantity = 0;
	}
	
	//原始金额
	var pay= addQuantity * months * price;
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

function getMonths(){
	//当期日期
	var myDate = new Date();
	var endAt = $("#endAt").html();
	var endAtList = endAt.split("-");
	//取得调整后截止日期
	if (endAtList !=null && endAtList.length==3){
		var years = parseInt(endAtList[0]);
		var months = parseInt(endAtList[1]);
		var days = parseInt(endAtList[2]);
		var endAtDate= new Date(years,months- 1,days,23,59,59);
		//截止日期大于当期日期
		if (endAtDate>myDate){
			var time= endAtDate.getTime() - myDate.getTime(); 
			var days = parseInt(time / (1000 * 60 * 60 * 24));
			return (days/30);
		}else{
			return 0;
		}
	}
}

/**
 * 支付
 */
function payMoney(){
	if (checkInput() ==false){
		return false;
	}
	
	
	layer.confirm('确定购买？', {
		  btn: ['确定','取消']
		}, function(e){
			var newQuantity = $("#newQuantity").html();
			var quantity = $("#quantity").html();
			
			if (newQuantity == quantity){
				//业务正确
		    	var index = parent.layer.getFrameIndex(window.name);
		        parent.layer.close(index);
		        return;
			}
			$("#btnConfirm").addClass("login_button_disabled");
			//产品id
			var coFunctionId = $("#coFunctionId").val();
			//调整数量
			var addQuantity = $("#addQuantity").val();
			//最终支付
			var payMoney = $("#newPay").html();
			var param = $.param({"coFunctionId":coFunctionId,"addQuantity":addQuantity,"payMoney":payMoney});
			$.ajax({
				type:'POST',
				url:$.basepath + "/corpApp/payAddQuantity.do",
				data:param,
				dataType:"json",
				timeout:$.time_out,
				success:function(result, status) {
					if (result.success == true) {
						//业务正确
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
	var newQuantity = $("#newQuantity").html();
	var newPay = $("#newPay").html();
	var balance = $("#balance").html();
	
	//数量
	if (!/^\d+$/.test(newQuantity) && parseFloat(newQuantity) < 1){
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
