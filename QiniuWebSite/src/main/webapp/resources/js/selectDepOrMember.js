$(function(){
	//生成组织信息树
    searchTree();
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
		    	showSelectedDep(hidCurrentDepId, hidCurrentDepNm);
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
        //
    	showSelectedDep(departmentId, departmentName);
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
    	//获取选中的部门ID
    	var selectedDepId = $('#hidDepartmentId').val();
    	//获取选中的部门名称
    	var selectedDepNm = $('#hidDepartmentNm').val();
    	if (selectedDepId ==undefined ||selectedDepId ==""){
    		$(".c_selector_panel_box").toast("请选择部门","error");
    		return false;
    	}
    	//生成返回JSON串
    	var rtnStr = '{ "selectedDepId": "' + selectedDepId + '", "selectedDepNm": "' + selectedDepNm + '" }';
    	//设置返回值
    	parent.$("#hidSelectedDep").val(rtnStr);
        parent.layer.close(index);
    });
}

//删除选中的部门
function delSelectedDep() {
	$("#selectedDep").html("");
}

//显示选中的部门信息
function showSelectedDep(departmentId, departmentName) {
	var selecedDep = "";
	//显示选中的部门
    $("#selectedDep").html("");
    selecedDep = leftStr + '<li class="angular-ui-tree-node">';
    selecedDep = "<li class='c_list_item' style='padding-right:30px;position:relative;' title='" + departmentName + "' >" + 
        			 "<i class='iconfont'><img src='../resources/images/contacts/2.png' style='width: 18px; height: 18px;'></i><span>&nbsp;</span><span>" + departmentName + "</span>" + 
        			 "<input type='hidden' id='hidDepartmentId' value='" + departmentId + "'/>" + 
        			 "<input type='hidden' id='hidDepartmentNm' value='" + departmentName + "'/>" + 
        			 "<i class='iconfont close' style='position:absolute;right:10px;' onclick='delSelectedDep();'><img src='../resources/images/contacts/close2.png' style='width: 18px; height: 18px;'></i>" + 
    			 "</li>";
    $("#selectedDep").html(selecedDep);
}

