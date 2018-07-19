
$(function(){
	$('#carousel-example-generic').carousel();
	
    // 页面跳转
    $('#kjrk1').click(function(){
    	$.ajax({
    		type: "POST",
    		url : $.basepath+"/index/contacts.do",
    		success : function(res)
    		{
    			if(res!="")
    			{ 
    				$('.navitem').removeClass("selected");
    				$('#navitem2').addClass("selected");
    				$(".c_box").css("style","flex");
    				$(".c_box").addClass("home");
    				$(".c_box").html(res);
    			}
    		}
    	});
    });
    
    showList();
	
});
// 认证事件
function organizeApprove(){
	$.ajax({
		type: "POST",
		url : $.basepath+"/index/corpAuth.do",
		success : function(res)
		{
			if(res!="")
			{ 
				$(".c_box").html(res);
			}else {
				alert("加载失败！");
			}
		}
	});
}
/**
 * 请求产品购买信息
 */
function showList(){
	$.post($.basepath + "/index/coProductOrganize.do",function(result) {
		if (result.success == true) {
			//员工信息
			var htmlContent=$("#protml").tmpl({"infoList":result.data});
			$(".proList").html(htmlContent);
		}else{
			$(".proList").html("");
		}
	}, "json");
}

//开通产品
function openProduct(productId){
	layer.open({
		  type: 2,
		  title: '开通产品',
		  shadeClose: true,
		  shade: 0.4,
		  area: ['800px', '550px'],
		  content: $.basepath + "/index/openProduct.do?productId="+productId,
	      end:function(index, layero){
	    	  showList();
	      }
		}); 
}