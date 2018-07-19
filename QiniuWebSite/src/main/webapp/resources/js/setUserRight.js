$(function(){
	//生成组织信息树
    searchTree();
	if ($("#controlType").val() == 1) {
	    $("#selectedDepAndMember").append(parent.$("#appPeopleAdd").val());
	}
	if ($("#controlType").val() == 2) {
	    $("#selectedDepAndMember").append(parent.$("#appPeopleUpdate").val());
	}
});
var leftStr="";

//查询树数据
function searchTree(){
	$.post($.basepath + "/contacts/departmentTree.do",function(result) {
		if (result.success == true) {
			var json= result.data;
			 // 加载数据
		    bulidDepTree(json);
		    //注册事件
		    treeClick();
		    //显示父页面传过来的部门
		    var hidCurrentDepId = $('#hidCurrentDepId').val() || "";
		    var hidCurrentDepNm = $('#hidCurrentDepNm').val() || "";
		    if( hidCurrentDepId != "" && hidCurrentDepNm != "") {
		    	showSelectedDep(hidCurrentDepId, hidCurrentDepNm, "");
		    }
		}else{

		}
	}, "json");
}

//创建树
function bulidDepTree(jsonObj){

    var department = jsonObj[0];
    leftStr ="";
    setLeft(department);
    $("#depTree").html(leftStr);
}

//生成 部门树
function setLeft(department){
	var depOrMem = $("#hidDepOrMem").val();
	var picNo = "1";
	//部门级别 1,2,3
	if (department.departmentLevel !="1"){
		picNo = "2";
	}
	var picNm ="open_selected";
	var picCol="#ffffff";
	if (department.departmentLevel =="3"){
		picNm = "close";
		picCol= "#c5c5c5";
	}
	var depId = department.departmentId;
	var depLevel = department.departmentLevel;
	var depNm = department.departmentNm;
	var memCount = department.members;
	var departments = department.departmentList;
	leftStr = leftStr + '<li class="angular-ui-tree-node">';
	leftStr = leftStr + '<div style="position:relative;width:100%;" class="area">';
	leftStr = leftStr + '<a style="color:;" class="dep-name" href="javascript:void(0)">';
	leftStr = leftStr + '<i class="iconfont icon-dept"><img src="../resources/images/contacts/'+ picNo +'.png" style="width: 18px; height: 18px;"></i>';
	leftStr = leftStr + '<input type="hidden" class="depId" value="'+ depId +'"/>';
	leftStr = leftStr + '<input type="hidden" class="depLevel" value="'+ depLevel +'"/>';
	leftStr = leftStr + '<span>&nbsp;</span><span class="depNm">'+ depNm +'</span>';
	leftStr = leftStr + '<span class="count"><span> (</span><span class="depCount">'+ memCount +'</span><span>人)</span></span></a>';
	if (depOrMem =="dep" && depLevel=="2"){
		leftStr = leftStr + '</div>';
	}else{
		if (departments !=null && departments.length > 0){
			leftStr = leftStr + '<a class="dep-tree-pos-btn" href="javascript:void(0)"><span class="iconfont icon-collapsed" style="color:#'+ picCol +';"><img class="icon_img" src="../resources/images/contacts/'+ picNm +'.png" style="width: 14px; height: 14px;"></span></a>';
			leftStr = leftStr + '</div>';
			leftStr = leftStr + '<ul style="margin-left:15px;">';
			for (var i =0;i < departments.length;i++){
				setLeft(departments[i]);
			}
		leftStr = leftStr + '</ul>';
		}else{
			leftStr = leftStr + '</div>';
		}
	}
leftStr = leftStr + '</li>';
}

//设置树事件
function treeClick(){
    //部门点击事件
    $('.dep-name').click(function(){
    	$("#depTree").find(".dep-name").removeClass("dep-name-hl");
        $(this).addClass("dep-name-hl");
        //获取选中的部门名称
        var departmentName = $(this).parent("div").find(".depNm").html();
        //获取选中的部门ID
    	var departmentId = $(this).parent("div").find(".depId").val();
    	
    	//请求部门人员信息
    	$.post($.basepath + "/contacts/memberList.do", $.param({"departmentId":departmentId}),function(result) {
    		if (result.success == true) {
    	        //显示部门员工
    	    	showSelectedDep(departmentId, departmentName, result.data);
    		}else{
    			staffInfo = "";
    		}
    	}, "json");
    	

    });
    
    // 树展开 树合拢
    $('.dep-tree-pos-btn').click(function(){
        if($(this).parent('div').next('ul').css('display') == 'block') {
            $(this).parent('div').next('ul').hide();
            $(this).parent('div').find("icon-collapsed").css("color","#c5c5c5");
            $(this).parent('div').find('.icon_img').prop("src","../resources/images/contacts/close.png");
        } else {
            $(this).parent('div').next('ul').show();
            $(this).parent('div').find("icon-collapsed").css("color","#ffffff");
            $(this).parent('div').find('.icon_img').prop("src","../resources/images/contacts/open_selected.png");
        }
    });
    
    //【取消】按钮点击事件
    $('#btnCancel').click(function(){
    	var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });
    
    //【确定】按钮点击事件
    $('#btnConfirm').click(function(){
    	var index = parent.layer.getFrameIndex(window.name);
    	// 打印动态组件
    	var rtnDepStr = "";
    	var rtnMemStr = "";
    	var selectedObj = "";
    	var depfalg = false;
    	// TODO:
    	$("#selectedDepAndMember").find(".result-text").each(function(index,element){
    		var id = $(this).find(".hidId").val();
    		var nm = $(this).find(".hidNm").val();
    		var type = $(this).find(".hidType").val();
    		if (type == 1) {
    			depfalg = true;
    			rtnDepStr = rtnDepStr +","+id;
			}
    		if (type == 2) {
    			rtnMemStr = rtnMemStr+","+id;
			}
    		selectedObj = selectedObj +
	  		"<div class='col-sm-4 result-box ng-scope' ng-repeat='current in dingSelector.resultList'>" +
	  			"<div class='result-text'>" +
	  				"<small class='ng-binding'>" + nm +"</small>" + 
	    			 "<input type='hidden' class='hidId' value='" + id + "'/>" + 
	    			 "<input type='hidden' class='hidNm' value='" + nm + "'/>" + 
	    			 "<input type='hidden' class='hidType' value='" + type + "'/>" + 
	  			"</div>" +
	  			"<div class='result-close' onclick='removeObj(this)'>×</div>" + 
	  		"</div>";
    		if ($("#controlType").val() == 1) {
    			parent.$(".selectDepOrMemberSpanAdd").html(selectedObj);
			}
    		if ($("#controlType").val() == 2) {
    			parent.$(".selectDepOrMemberSpanUpdate").html(selectedObj);
			}
    	});
    	if (!depfalg) {
    		$(".c_selector_panel_box").toast("请选择部门","error");
			return;
		}
		if ($("#controlType").val() == 1) {
	    	parent.$("#appPeopleAdd").val(selectedObj);
	    	parent.$("#appPeopleDepAdd").val(rtnDepStr.substring(1, rtnDepStr.length));
	    	parent.$("#appPeopleMemAdd").val(rtnMemStr.substring(1, rtnMemStr.length));
		}
		if ($("#controlType").val() == 2) {
	    	parent.$("#appPeopleUpdate").val(selectedObj);
	    	parent.$("#appPeopleDepUpdate").val(rtnDepStr.substring(1, rtnDepStr.length));
	    	parent.$("#appPeopleMemUpdate").val(rtnMemStr.substring(1, rtnMemStr.length));
		}
    	parent.layer.close(index);
    });
}

//删除选中的部门
function delSelectedDep() {
	$("#selectedDep").html("");
}

//显示选中的部门员工列表
function showSelectedDep(departmentId, departmentName, staffList) {
	var selecedDep = "";
	//显示选中的部门
    $("#selectedDep").html("");
    selecedDep = selecedDep + '<li class="angular-ui-tree-node">';
    selecedDep = "<li class='c_list_item' style='padding-right:30px;position:relative;' title='" + departmentName + "' >" + 
        			 "<i class='iconfont'><img src='../resources/images/contacts/2.png' style='width: 18px; height: 18px;'></i><span>&nbsp;</span><span>" + departmentName + "</span>" + 
        			 "<input type='hidden' class='hidId' value='" + departmentId + "'/>" + 
        			 "<input type='hidden' class='hidNm' value='" + departmentName + "'/>" + 
        			 "<input type='hidden' class='hidType' value='1'/>" + 
        			 "<input type='checkbox' class='iconfont close' style='position:absolute;right:10px;' onclick='getSelectedObj(this);' />" + 
        		 "</li>";
    //如果选中部门的员工信息不为空,则显示员工列表
    if (staffList !=null && staffList.length > 0) {
    	selecedDep = selecedDep + '<ul style="margin-left:15px;">';
		for (var i =0;i < staffList.length;i++){
			selecedDep = selecedDep + '<li class="angular-ui-tree-node">';
			selecedDep = selecedDep + '<div style="position:relative;width:100%;margin-top:5px;" class="area">';
			selecedDep = selecedDep + '<i class="iconfont icon-dept"><img src="../resources/images/myposition2.jpg" style="width: 18px; height: 18px; margin-right: 5px;">'+staffList[i].userNm+'</i>';
			selecedDep = selecedDep + "<input type='checkbox' class='iconfont close' style='position:absolute;right:10px;' onclick='getSelectedObj(this);' />";
			selecedDep = selecedDep + "<input type='hidden' class='hidId' value='" + staffList[i].memberId + "'/>";
			selecedDep = selecedDep + "<input type='hidden' class='hidNm' value='" + staffList[i].userNm + "'/>";
			selecedDep = selecedDep + "<input type='hidden' class='hidType' value='2'/>";
			selecedDep = selecedDep + '</div>';
			selecedDep = selecedDep + '</li>';
		}
		selecedDep = selecedDep + '</ul>';
	}
    
    $("#selectedDep").html(selecedDep);
}

//获取选中的部门/员工信息
function getSelectedObj(obj) {
	//定义空html字符串
	var selectedObj = "";
	//判断当前选中的对象是否为已选对象标志
	var isSelectedFlag = false;
	//获取选中对象的ID
	var objId = $(obj).parent().find(".hidId").val();
	//获取选中对象的名称
	var objNm = $(obj).parent().find(".hidNm").val();
	//获取选中对象的类型（1：部门    2：员工）
	var objType = $(obj).parent().find(".hidType").val();
	//移除对应的元素
	var hidObjIdList = $("#selectedDepAndMember").find('.hidId');
	
	//如果当前checkbox为非选中状态，则添加元素；否则，删除元素
	if($(obj).is(':checked')) {
		$(hidObjIdList).each(function(){
	       if(objId == $(this).val()) {
	    	   isSelectedFlag = true;
	    	   return;
	       }
		}); 
		
		//如果当前选中的对象非已选对象
		if (!isSelectedFlag) {
			selectedObj = selectedObj +
			  "<div class='col-sm-4 result-box ng-scope' ng-repeat='current in dingSelector.resultList'>" +
				"<div class='result-text'>" +
					"<small class='ng-binding'>" + objNm +"</small>" + 
	  			 "<input type='hidden' class='hidId' value='" + objId + "'/>" + 
	  			 "<input type='hidden' class='hidNm' value='" + objNm + "'/>" + 
	  			 "<input type='hidden' class='hidType' value='" + objType + "'/>" + 
				"</div>" +
				"<div class='result-close' onclick='removeObj(this)'>×</div>" + 
			  "</div>";
			$("#selectedDepAndMember").append(selectedObj);
		}
	} else {
		$(hidObjIdList).each(function(){
	       if(objId == $(this).val()) {
	    	   $(this).parent().parent().remove();
	       }
	    }); 
	}
}

//移除选中的部门/员工信息
function removeObj(obj) {
	//定位需移除的对象div
	$(obj).parent().remove();
	//取得当前移除元素的部门/员工ID
	var objId = $(obj).parent().find('.hidId').val();
	//取得所有的checkbox集合
	var arrChk = $("#selectedDep input[type='checkbox']");
	//定位当前移除的部门/员工信息，将对应的checkbox设置为不选中状态
    $(arrChk).each(function(){
       //取得当前checkbox相邻的部门/员工元素ID
    	var neighbor = $(this).parent().find('.hidId').val() || "";
    	if(neighbor != "" && objId == neighbor) {
    		$(this).removeAttr("checked");
    		return;
    	}
    }); 
}