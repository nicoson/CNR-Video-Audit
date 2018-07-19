//分页验证
function paginate(forward,totalPages){
	if(forward)	{
		document.getElementById("currentPage").value=forward;
	}else{
		var temp=document.getElementById("forwardPage").value;		
		if(temp=="")
		{
			alert("输入的页数为空!");
			return false;
		}
    	var reg = /^[1-9]\d*$/;
    	if(!reg.test(temp))    	
    	{
    		alert("你输入的不是一个正整数！");
    		return false;
    	}
    	if(temp>totalPages)
    	{
    		alert("输入的页数超过了显示的范围!");
    		return false;
    	}
		document.getElementById("currentPage").value=document.getElementById("forwardPage").value;
	}
	$("#queryListForm").submit();
}

