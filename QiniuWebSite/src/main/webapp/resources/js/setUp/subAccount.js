$(function () {
	// 加载数据
	$.ajax({
		type: "POST",
		url : $.basepath+"/setup/selectResource.do",
		dataType:"json",
		success : function(res)
		{
			if(res.success == true && res.data != 0)
			{ 
				var htmlContent=$("#memtml").tmpl({"infoList":res.data});
				$("#tbData").html(htmlContent);
			}else{
				$(".c_modal_head").toast("加载失败","error");
			}
		}
	});
	// 加载权限列表
	resourceList();
	 
    $('#btnAddSubAccount').click(function () {
    	$("input[type='checkbox']").attr("checked", false);
        $('#divSubAccount').hide();
        $('#divAddSubAccount').show();
        // 新增事件
        $('#typeControl').val(1);
        // 新增页面添加click事件
        $('#membersSelection').bind("click",selectDep);
        $('#membersSelection').html('<span class="placeholder ng-scope">请选择成员</span>');
        $('#detailHead').html('添加高级管理员');
    });
    $('#goBack').click(function () {
        $('#divAddSubAccount').hide();
        $('#divSubAccount').show();
    });
    // 保存事件
    $('.btn-primary').click(function () {
    	var typeControl = $('#typeControl').val();
    	if (typeControl == 0) {
    		// 设置更新参数
    		var params = "";
    		var resourceList = [];
    		var userId = $('#userId').val();
    		$('input[type="checkbox"]:checked').each(function(){
    			if($(this).attr("data-id")){
    				resourceList.push({"resourceId":$(this).attr("data-id"),"resourceType":$(this).attr("data-type")});
    			}
    		});
    		params = {"roleList":resourceList,"userId":userId};
    		updatePermission(params);
    	}
    	if (typeControl == 1) {
    		var params = [];
    		var resourceList = [];
    		var memberIds = $("#selectedMemberId").val().split(",");
    		$('input[type="checkbox"]:checked').each(function(){
    			if($(this).attr("data-id")){
    				resourceList.push({"resourceId":$(this).attr("data-id"),"resourceType":$(this).attr("data-type")});
    			}
    		});
    		for(var i = 0;i < memberIds.length;i++){
        		params.push({"roleList":resourceList,"memberId":memberIds[i]});
    		}
    		savePermission(params);
		}
    });
    // 点击全部选择事件
    $('#checkAll').click(function () {
    	$("input[type='checkbox']").prop("checked", $(this).prop("checked"));
    });
});

/**
* 删除事件
**/
function delPermission(userId){
	if (confirm("真的要删除吗？")) {
		var userIdStr = $(userId).attr("data-id");
		$.ajax({
			type: "POST",
			url : $.basepath+"/setup/delResource.do",
			data:{"userId":userIdStr},
			dataType:"json",
			success : function(res)
			{
				if(res.success == true && res.data > 0)
				{ 
					$(".c_modal_head").toast("删除成功","success");
				}else{
					$(".c_modal_head").toast("删除失败","error");
				}
			}
		});
	}
}

/**
* 编辑管理员权限
**/
function editPermission(userId){
	var userIdStr = $(userId).attr("data-id");
	$.ajax({
		type: "POST",
		url : $.basepath+"/setup/editResource.do",
		data:{"userId":userIdStr},
		dataType:"json",
		success : function(res)
		{
			if(res.success == true && res.data != null)
			{ 
				$('#typeControl').val(0);
				$('#divSubAccount').hide();
				$('#divAddSubAccount').show();
				// 移除绑定事件
				$('#membersSelection').unbind("click");
				$("input[type='checkbox']").removeAttr("checked");
				// 设置默认勾选
				for(var i = 0;i < res.data[0].roleList.length;i++){
					var oneInfo = res.data[0].roleList[i];
					$("input[data-id='"+oneInfo.resourceId+"']").prop("checked",true);
				}
				$('#membersSelection').html('<span class="placeholder ng-scope">'+res.data[0].userNm+
						'</span><input id="userId" type="hidden" value="'+res.data[0].userId+'" />');
				$('#detailHead').html('编辑高级管理员权限');
			}else{
				$(".c_modal_head").toast(res.errorMsg,"error");
			}
		}
	});
}

/**
 * 保存权限
 */
function savePermission(params){
	
	$.ajax({
		type: "POST",
		url : $.basepath+"/setup/saveResource.do",
		data:{"params":JSON.stringify(params)},
		dataType:"json",
		success : function(res)
		{
			if(res.success == true && res.data > 0)
			{ 
				$(".c_modal_head").toast("保存成功","success");
			}else{
				$(".c_modal_head").toast("保存失败","error");
			}
		}
	});
}

/**
 * 更新权限
 */
function updatePermission(params){
	$.ajax({
		type: "POST",
		url : $.basepath+"/setup/updateResource.do",
		data:{"params":JSON.stringify(params)},
		dataType:"json",
		success : function(res)
		{
			if(res.success == true && res.data > 0)
			{ 
				$(".c_modal_head").toast("修改成功","success");
			}else{
				$(".c_modal_head").toast("修改失败","error");
			}
		}
	});
}

/**
*方法说明：打开【部门/成员】选择页面
*返回值:
*    无
*参数：
*    无
*/
function selectDep(){
	layer.open({
		  type: 2,
		  title: '选择成员范围',
		  shadeClose: true,
		  shade: 0.4,
		  area: ['900px', '530px'],
		  content: $.basepath + "/setup/setUserRight.do",
	      end:function(index, layero){
	      }
	}); 
}

/**
* 加载权限列表
**/
function resourceList(){
	$.ajax({
		type: "POST",
		url : $.basepath+"/setup/queryResource.do",
		dataType:"json",
		success : function(res)
		{
			if(res.success == true && res.data != 0)
			{ 
				var htmlContent=$("#sliTempl").tmpl({"searchList":res.data.searchList});
				$("#sLi").html(htmlContent);
				var htmlContent=$("#fliTempl").tmpl({"searchList":res.data.searchList});
				$("#fLi").html(htmlContent);
			}else{
				$(".c_modal_head").toast("加载失败","error");
			}
		}
	});
}