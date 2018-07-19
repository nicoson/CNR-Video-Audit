$(function(){
		
	/*点击'申请发票'事件*/
	$('#btnApply').click(function () {
		top.layer.confirm('为选中交易记录申请发票？', {
			btn: ['确定','取消'] //按钮
		}, function(e) {
			var rechargeIdList = '';
			var accountNo = '';
			var custType='';
			var custName='';
			var amount=0;
			var invoiceStatus='';
			//获取候选项数据
			$("input[name='check']:checked:visible").each(function(){
				//获取数据
				rechargeIdList += $(this).val()+",";
				custType = $(this).closest("tr").attr("custType");
				custName = $(this).closest("tr").attr("custName");
				//总金额
				amount += parseInt($(this).closest("tr").find("#amount").text());
				accountNo = $(this).closest("tr").attr("accountNo");
				invoiceStatus = $(this).closest("tr").attr("invoiceStatus");
			});
			
			//判断是否有数据
			if(rechargeIdList != ''){
				rechargeIdList = rechargeIdList.substring(0, rechargeIdList.length - 1);
			}
			$.ajax({
				url:$.basepath +"/valAddedSer/puInvoiceInfoAddData.do",
				method:"post",
				data:{"rechargeIdList":rechargeIdList,"accountNo":accountNo,"custType":custType,"custName":custName,"amount":amount,"invoiceStatus":invoiceStatus},
				dataType:"json",
				async:false,
				success:function(result, status) {
					if(result != 0) {
						top.layer.alert("发票申请成功！", {
							icon: 1,
							skin: 'layer-ext-moon'
						}, function(e){
							top.layer.close(e);
							$.ajax({
								type: "POST",
								url : $.basepath+"/valAddedSer/rechargeRecord/tabChange.do?type=3",
								success : function(res)
								{
									if(res!="")
									{ 
										$(".microApp-right").html(res);
								        $('.pay_list_type').removeClass("pay_list_type_active");
								        $('#payTypeClear').addClass("pay_list_type_active");
								        $('.table-pay').hide();
								        $('#tablePayClear').show();
									    $('#btnApply').hide();   
									}
								}
							});
						});
					}else{
						top.layer.alert("发票申请失败", {
							icon: 2,
							skin: 'layer-ext-moon'
						});
					}
				}
			});
			top.layer.close(e);
		}, function(){
		});
	});
	
	/*单选click事件*/
	$("input[name='check']").click(function(){
		var allchecked = $("input[name='check']:checked").length;
		var allcheck = $("input[name='check']:visible").length;
		switch(allchecked) {
			case 0:
				$("#checkAll").prop("checked", false);
				$("#btnApply").prop("disabled",true);
				break;
			case allcheck:
				$("#checkAll").prop("checked", true); 
				$("#btnApply").prop("disabled",false);
				break;
			default:
				$("#checkAll").prop("checked", false); 
				$("#btnApply").prop("disabled",false);
				break;
		}
	});
	
	/*全选事件*/
	$("#checkAll").click(function(){
		$("input[name='check']:visible").prop("checked", $(this).prop("checked"));
		$("#btnApply").prop("disabled",!$(this).prop("checked"));
	});

	
    $('#payTypeAll').click(function () {
    	// tab切换
		$.ajax({
			type: "POST",
			url : $.basepath+"/valAddedSer/rechargeRecord/tabChange.do?type=0",
			success : function(res)
			{
				if(res!="")
				{ 
					$(".microApp-right").html(res);
					$('.pay_list_type').removeClass("pay_list_type_active");
					$('#payTypeAll').addClass("pay_list_type_active");
					$('.table-pay').hide();
					$('#tablePayAll').show();
					$('#btnApply').hide(); 
				}
			}
		});
    });
    
    $('#payTypeWait').click(function () {
    	// tab切换
		$.ajax({
			type: "POST",
			url : $.basepath+"/valAddedSer/rechargeRecord/tabChange.do?type=1",
			success : function(res)
			{
				if(res!="")
				{ 
					$(".microApp-right").html(res);
			        $('.pay_list_type').removeClass("pay_list_type_active");
			        $('#payTypeWait').addClass("pay_list_type_active");
			        $('.table-pay').hide();
			        $('#tablePayWait').show();
			        $('#btnApply').hide(); 
				}
			}
		});
    });

    $('#payTypeClose').click(function () {
    	// tab切换
		$.ajax({
			type: "POST",
			url : $.basepath+"/valAddedSer/rechargeRecord/tabChange.do?type=2",
			success : function(res)
			{
				if(res!="")
				{ 
					$(".microApp-right").html(res);
			        $('.pay_list_type').removeClass("pay_list_type_active");
			        $('#payTypeClose').addClass("pay_list_type_active");
			        $('.table-pay').hide();
			        $('#tablePayClose').show();
			        $('#btnApply').hide();   
				}
			}
		});
    });

    $('#payTypeClear').click(function () {
    	// tab切换
		$.ajax({
			type: "POST",
			url : $.basepath+"/valAddedSer/rechargeRecord/tabChange.do?type=3",
			success : function(res)
			{
				if(res!="")
				{ 
					$(".microApp-right").html(res);
			        $('.pay_list_type').removeClass("pay_list_type_active");
			        $('#payTypeClear').addClass("pay_list_type_active");
			        $('.table-pay').hide();
			        $('#tablePayClear').show();
				    $('#btnApply').hide();   
				}
			}
		});
    });
    
    /*发票申请*/
    $('#invoiceApply').click(function () {
    	// tab切换
		$.ajax({
			type: "POST",
			url : $.basepath+"/valAddedSer/rechargeRecord/tabChange.do?type=4",
			success : function(res)
			{
				if(res!="")
				{ 
					$(".microApp-right").html(res);
			        $('.pay_list_type').removeClass("pay_list_type_active");
			        $('#invoiceApply').addClass("pay_list_type_active");
			        $('.table-pay').hide();
			        $('#tableInvoiceApply').show();
				    $('#btnApply').show();   
				}
			}
		});
    });
});

//交易关闭
function closeOrder(rechargeId){
	$.ajax({
		type: "POST",
		url : $.basepath+"/valAddedSer/closeOrder.do?rechargeId="+rechargeId,
		dataType:"json",
		success : function(res)
		{
			if(res.data != null && res.success==true)
			{ 
				alert("取消订单成功");
			}else {
				alert(res);
			}
		}
	});
}

