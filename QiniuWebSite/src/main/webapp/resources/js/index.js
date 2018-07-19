
$(function(){
	$.ajax({
		type: "POST",
		url : $.basepath+"/index/indexMsg.do",
		success : function(res)
		{
			if(res!="")
			{ 
				$(".c_box").html(res);
			}
		}
	});
});