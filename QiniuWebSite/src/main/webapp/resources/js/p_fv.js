//验证框架
//vr="0&&1" lth="10"  
//vr: 0不为空验证
//    1字符长度，长度为lth属性
////////////////////
function ysbFormValidate(formObj)
{
	var f = 0;
   	for(var k=0;k<formObj.length;k++){
	   	var inptObj = formObj[k];
	   	if(typeof $(inptObj).attr('vr')=="string" && ($(inptObj).attr('type')=='text' || $(inptObj).attr('type')=='textarea')&& inptObj.style.display !='none'){//存在规则验证
		     inptObj.value=vTrim(inptObj.value);
		     if(!inptObj.id){
		         alert("INPUT没有设置ID!");
		     }
	     	 if(!ysbCheck(inptObj)) f++;
		 }
	}
	return f>0 ? false:true;
}

function vTrim(str){
    if(str==null){
        return "";
    }
    str =str.replace(/ +$/,"");
    str =str.replace(/^ +/,"");
    return str;
}
/**
 * 为空验证,为空返回false
 */
function checkEmpty(obj){
     var str=obj.value;
     str=str.replace(/^\s/,"");
     str=str.replace(/\s+$/,"");
     obj.value=str;
	 if(str == ""){
	  	 return false;
	 }else{
	 	return true;
	 }
}

/**
 * 验证字符个数
 */
function checkCharLTH(obj){
    if(obj.value==""){
        return true;
    }
    return obj.value.length<=getlth(obj);
}

function getlth(obj){
	
    if(typeof $(obj).attr('lth')=="string"){
        return $(obj).attr('lth');
    }else{
        return 20;//默认
    }
}


function tsf(vldRst,obj,msg){
	if(vldRst){
		if(document.getElementById("div_"+ obj.id))
		{
			$(obj).attr('style',$(obj).attr('style')+';border:1px solid #ccc');
			 $("#div_"+ obj.id).remove();
			
		}
		
    }else{//显示
    	if(!document.getElementById("div_"+ obj.id))
    	{
    		var $htmlDIV = $("<label id='div_"+ obj.id +"' style='width:120px;height:26px;line-height:26px;text-align:left;color:red'>"+msg+"</label>");  //创建DOM对象
	        $(obj).parent().append($htmlDIV);
	        $(obj).attr('style',$(obj).attr('style')+';border:1px solid #f00');
    	}
    }
}

/*基本验证规则[循环，有一次失败false，就当失败]*/
function ysbCheck(obj){
    //alert('规则验证:'+obj.outerHTML);
    var arr=$(obj).attr('vr').split("&&");
    for(j=0;j<arr.length;j++){
	    //验证结果
	    var flag=false;
	    switch (parseInt(arr[j])) {
	           case 0: //为空验证
					flag=checkEmpty(obj);
	                tsf(flag,obj,"必填");//为空false，显示提示；不为空true，隐藏提示
	                if(flag==false){return false;}
	                break;
	           case 1: //字符长度验证
	               flag=checkCharLTH(obj);
	               //alert(flag);
	               tsf(flag,obj,"不超过"+getlth(obj)+"个字符");
	               if(flag==false){return false;}
	               break;
	           case 2: // 只能为数字
	               flag=checkMustNum(obj);
	               //alert(flag);
	               tsf(flag,obj,"只能为数字");
	               if(flag==false){return false;}
	               break;
	         }
	}
	return true;
}

/**
 * 只能为数字
 */
function checkMustNum(obj){
    if(obj.value==""){
        return true;
    }
    return /^\d+$/.test(obj.value);
}
/**
 * 弹出窗验证 add By cc
 */
function validateTheForm(formObj)
{
   	for(var k=0;k<formObj.length;k++)
   	{
	   	var inptObj = formObj[k];
	   	if(typeof $(inptObj).attr('vr')=="string" && ($(inptObj).attr('type')=='text' || $(inptObj).attr('type')=='textarea')&& inptObj.style.display !='none')
	   	{//存在规则验证
		     if(vTrim(inptObj.value)=="")
		     {
		     	var msg = $(inptObj).attr('vr')+"不能为空!";
	     	 	top.layer.alert(msg, {
			        icon: 2,
    				skin: 'layer-ext-moon'
			    });
		     	return false;
		     }
		}
		
		if(typeof $(inptObj).attr('maxlt')=="string")
	     {
	     	var maxLength = $(inptObj).attr('maxlt');
	     	var textLength = vTrim(inptObj.value).length;
	     	if(textLength > maxLength)
	     	{
	     		var msg = $(inptObj).attr('lbName')+"长度不能超过"+maxLength;
	     		top.layer.alert(msg, {
			        icon: 2,
    				skin: 'layer-ext-moon'
			    });
	     		return false;
	     	}
	     }
	}
	return true;
}
