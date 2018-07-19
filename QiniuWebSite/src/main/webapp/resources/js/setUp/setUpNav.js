$(function(){
	// 默认加载
	$.ajax({
		type: "POST",
		url : $.basepath+"/setup/tabChange.do?type=0",
		success : function(res)
		{
			if(res!="")
			{ 
				$(".microApp-right").html(res);
			}
		}
	});
	// tab标签切换
	$("ul li").click(function (){
		var tabIndex = 0;
		$("ul li").each(function(){
			if ($(this).hasClass("active")) {
				tabIndex = $(this).val();
			}
		});
		$("ul li").removeClass("active");
		$(this).addClass("active");
		// 获取当前选择tab
		var type = $(this).val();
		if (type==tabIndex) {
			return;
		}else{
			// tab切换
			$.ajax({
				type: "POST",
				url : $.basepath+"/setup/tabChange.do?type="+type,
				success : function(res)
				{
					if(res!="")
					{ 
						$(".microApp-right").html(res);
					}
				}
			});
		}
	});
});