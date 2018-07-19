	var timer1;
    var timer2;
$(function(){
	//查询树
    searchTree();
    //初始校验
	if ($ && $.validator) {
		$.validator.setDefaults({
			errorElement:"img",
			errorClass:"error_validate"
		});
	}
    checkInput();
    //点击空白处隐藏悬浮页面
    $(".bg").click(function() {
    	timer2 = setInterval(slowHide, 5);
    });
    
    //显示 新增子部门信息
    $("#btnDepAdd").click(function(){
    	depResit();
    	//上级部门ID
    	var departmentId = $("#departmentId_header").val();
    	//上级部门名称
    	var departmentNm = $("#departmentNm_header").html();
    	
    	$('#btnSaveDep').removeClass("c_ding_btn_disabled");
    	$('#btnDelDep').removeClass("c_ding_btn_disabled");
    	
    	//设置 头=添加部门
    	$("#divDep span[name='divDephead']").html("添加部门");
    	//隐藏 部门ID
    	$("#divDep span[name='departmentIdShow']").addClass("hide");
    	//隐藏 删除按钮
    	$('#btnDelDep').addClass("hide");
    	
    	//清空 部门ID
    	$("#departmentId").val("");
    	//清空 部门名称
    	$("#departmentNm").val("");
    	
    	//设置 上级部门名称
    	$("#parentName").text(departmentNm);
    	//设置 上级部门ID
    	$("#parentId").val(departmentId);

        $('#divDep').removeClass("hide");
        
        $('.c_side_modal_box .c_side_modal').css('top', '-600px');
        timer1 = setInterval(slowShow, 5);
    });

    //显示 修改部门信息
    $('#btnDepUpdate').click(function(){
    	depResit();
    	//本部门ID
    	var departmentId = $("#departmentId_header").val();
    	//本部门名称
    	var departmentNm = $("#departmentNm_header").html();
    	//设置 父级部门ID
    	var parentId = $("#parentId_header").val();
    	//设置 父级部门名称
    	var parentName = $("#parentName_header").val();
    	var departmentLevel = $("#departmentLevel_header").val();
    	//3级部门才能修改上级部门
    	if (departmentLevel =="3"){
    		$("#divParent").removeClass("hide");
    	}
    	$('#btnSaveDep').removeClass("c_ding_btn_disabled");
    	$('#btnDelDep').removeClass("c_ding_btn_disabled");
    	
    	//设置 头=添加部门
    	$("#divDep span[name='divDephead']").html("编辑部门");
    	//显示 部门ID
    	$("#divDep span[name='departmentIdShow']").removeClass("hide");
    	//显示 删除按钮
    	$('#btnDelDep').removeClass("hide");
    	
    	//设置 部门ID
    	$("#departmentId").val(departmentId);
    	$("#divDep span[name='departmentIdShow']").html("(部门ID："+ departmentId +")");
    	//设置 部门名称
    	$("#departmentNm").val(departmentNm);
    	//设置 上级部门ID
    	$("#parentId").val(parentId);
		//设置 上级部门名称
    	$("#parentName").html(parentName);

        $('#divDep').removeClass("hide");
        $('.c_side_modal_box .c_side_modal').css('top', '-600px');
        timer1 = setInterval(slowShow, 5);
    });

    //保存 部门设置
    $('#btnSaveDep').click(function(){
        if($(this).hasClass("c_ding_btn_disabled")){
            return false;
          }
        
    	// 画面必须输入校验
    	var validateResult = $("#depForm").validate().form();
    	if (validateResult==false) return ;
        
        var url ="";
    	var urlAdd="/contacts/departmentAdd.do";
    	var urlUpdate="/contacts/departmentUpdate.do";
    	var param =$("#depForm").serialize();

        var departmentId = $("#departmentId").val();
        
        if (departmentId !=""){
        	url = urlUpdate;
        }else{
        	url = urlAdd;
        }
        
        $(this).addClass("c_ding_btn_disabled");
    	$.post($.basepath + url, param,function(result) {
    		if (result.success == true) {
    			$('#btnSaveDep').removeClass("c_ding_btn_disabled");
    			timer2 = setInterval(slowHide, 5);
    			//更新树信息
    			searchTree();
    		}else{
    			$("#depForm .c_modal_head").toast(result.errorMsg,"error");
    			$('#btnSaveDep').removeClass("c_ding_btn_disabled");
    		}
    	}, "json");
    });
    
    // 删除 部门
    $('#btnDelDep').click(function(){
    	layer.confirm('确定删除？', {
  		  btn: ['确定','取消']
  		}, function(e){
  			var departmentId = $("#departmentId").val();
  			// 请求删除用户
	    	$.post($.basepath + "/contacts/departmentDelete.do", $.param({"departmentId":departmentId}),function(result) {
	    		if (result.success == true) {
	    			timer2 = setInterval(slowHide, 5);
	    			//更新树信息
	    			searchTree();
	    		}else{
	    			$("#depForm .c_modal_head").toast(result.errorMsg,"error");
	    		}
	    	}, "json");
		layer.close(e);
		}, function(){
		});
    });

    //关闭 部门设置
    $('#btnCancelDep').click(function(){
        timer2 = setInterval(slowHide, 5);
    });
    
    //新增 部门员工
    $('#btnAddEmp').click(function(){
    	//员工信息重置
    	memResit();
        $('#btnDelEmp').addClass("hide");
    	$("#memForm input[name='phone']").removeAttr("disabled");
    	//本部门ID
    	var departmentId = $("#departmentId_header").val();
    	//本部门名称
    	var departmentNm = $("#departmentNm_header").html();
		$("#memForm input[name='departmentId']").val(departmentId);
		$("#memForm span[name='departmentNm']").html(departmentNm);
    	
        $('#divEmp').removeClass("hide");
        $('.c_side_modal_box .c_side_modal').css('top', '-600px');
        timer1 = setInterval(slowShow, 5);
    });
    
    //保存 员工信息
    $('#btnSaveEmp').click(function(){
        if($(this).hasClass("c_ding_btn_disabled")){
            return false;
          }
        
    	// 画面必须输入校验
    	var validateResult = $("#memForm").validate().form();
    	if (validateResult==false) return ;
    	
        
        var url ="";
    	var urlAdd="/contacts/memberAdd.do";
    	var urlUpdate="/contacts/memberUpdate.do";
    	var param =$("#memForm").serialize();

        var memberId = $("#memberId_mem").val();
        
        var flag = false;
        
        
        
        if (memberId !=""){
        	url = urlUpdate;
        	flag = true;
        }else{
        	url = urlAdd;
        	
        	//校验手机号
            $.ajax({
    			type:'POST',
    			url:$.basepath + "/contacts/checkMemberByPhone.do",
    			async:false,
    			data:param,
    			dataType:"json",
    			timeout:$.time_out,
    			success:function(result, status) {
    				if (result.success == true) {
    					flag = true;
    				}else{
    					//业务错误
    					flag = false;
    					$("#memForm .c_modal_head").toast(result.errorMsg,"error");
    				}
    			},
    			error:function(){
    				//异常错误
    				flag = false;
    				$("#memForm .c_modal_head").toast("请求失败！","error");
    			},
    			complete:function(result) {
    				//最终执行
    			}
    		});
        }
        
        
        if (flag ==false){
        	return;
        }
        
        $(this).addClass("c_ding_btn_disabled");
		$.ajax({
			type:'POST',
			url:$.basepath + url,
			data:param,
			dataType:"json",
			timeout:$.time_out,
			success:function(result, status) {
				if (result.success == true) {
					//业务正确
					timer2 = setInterval(slowHide, 5);
					// 更新树信息
					searchTree();
				}else{
					//业务错误
					$("#memForm .c_modal_head").toast(result.errorMsg,"error");
				}
			},
			error:function(){
				//异常错误
				$("#memForm .c_modal_head").toast("请求失败！","error");
			},
			complete:function(result) {
				//最终执行
				$('#btnSaveEmp').removeClass("c_ding_btn_disabled");
			}
		});
    });
    
    //删除 员工信息
    $('#btnDelEmp').click(function(){
    	layer.confirm('确定删除？', {
    		  btn: ['确定','取消']
    		}, function(e){
    			
			//请求删除用户
    		var memberId = $("#memForm input[name='memberId']").val();
			var departmentId = $("#memForm input[name='departmentId']").val();
	    	$.post($.basepath + "/contacts/memberDelete.do", $.param({"memberId":memberId,"departmentId":departmentId}),function(result) {
	    		if (result.success == true) {
	    			timer2 = setInterval(slowHide, 5);
	    			//更新树信息
	    			searchTree();
	    		}else{
	    			$("#memForm .c_modal_head").toast(result.errorMsg,"error");
	    		}
	    	}, "json");
	    	
		layer.close(e);
    		}, function(){
    		});
    });
    
    //批量删除 员工信息
    $('#btnDelEmpAll').click(function(){
		var memberId = "";
		$("input[name='check']:checked").each(function() {
			memberId += $(this).val() + ",";
		});
		if (memberId != "") {
			memberId = memberId.substring(0, memberId.length - 1);
		}
		
		if (memberId ==""){
			$(".c_right_box").toast("请选择要删除的员工","error");
			return false;
		}
    	
    	layer.confirm('确定删除？', {
  		  btn: ['确定','取消']
  		}, function(e){
  			
			var departmentId = $("#departmentId_header").val();
			//请求删除用户
	    	$.post($.basepath + "/contacts/memberDelete.do", $.param({"memberId":memberId,"departmentId":departmentId}),function(result) {
	    		if (result.success == true) {
	    			$(".c_right_box").toast("删除员工成功","success");
	    			//更新树信息
	    			searchTree();
	    		}else{
	    			$(".c_right_box").toast(result.errorMsg,"error");
	    		}
	    	}, "json");
	    	
		layer.close(e);
		}, function(){
		});
		
    });

    //关闭 员工信息
    $('#btnCancelEmp').click(function(){
        timer2 = setInterval(slowHide, 5);
    });
    
    //【部门】选择框点击事件(部门)
    $('.selectDepSpan').click(function(){
    	//取得当前已选中的部门ID和名称
    	//设置 上级部门ID
    	var currentDepId = $("#depForm input[name='parentId']").val();
		//设置 上级部门名称
    	var currentDepNm = $("#depForm span[name='parentName']").html();
    	selectDep(currentDepId,currentDepNm,"dep");

    });
    
    //【部门】选择框点击事件(员工)
    $('.selectMemSpan').click(function(){
    	//取得当前已选中的部门ID和名称
    	var currentDepId = $("#memForm input[name='departmentId']").val();
		//设置 部门名称
    	var currentDepNm = $("#memForm span[name='departmentNm']").html();
    	selectDep(currentDepId,currentDepNm,"mem");
    });
    
	/*全选事件*/
	$("#checkAll").click(function(){
		$("input[name='check']:visible").prop("checked", $(this).prop("checked"));
	});
});

function selectDep(currentDepId,currentDepNm,depOrMem){
	layer.open({
		  type: 2,
		  title: '选择部门',
		  shadeClose: true,
		  shade: 0.4,
		  area: ['680px', '530px'],
		  content: $.basepath + "/contacts/selectDepOrMember.do?depOrMem=" + depOrMem + "&currentDepId=" + currentDepId + "&currentDepNm=" + currentDepNm,
	      end:function(index, layero){
	      	var hidSelectedDep = $("#hidSelectedDep").val();
	  	    var obj = $.parseJSON(hidSelectedDep);
	  	    if (obj !=null){
	  	    	if (depOrMem =="dep"){
	  	    		$("#depForm input[name='parentId']").val(obj.selectedDepId);
	  	    		$("#depForm span[name='parentName']").html(obj.selectedDepNm);
	  	    	}
	  	    	if (depOrMem =="mem"){
	  	    		$("#memForm input[name='departmentId']").val(obj.selectedDepId);
	  	    		$("#memForm span[name='departmentNm']").html(obj.selectedDepNm);
		  	    }
	  	    }
	      }
		}); 
}


//显示效果
function slowShow() {
    if ($('.c_side_modal_box .c_side_modal').css('top') == '0px') {
        clearInterval(timer1);
    }
    else {
        $('.c_side_modal_box .c_side_modal').css('top', parseInt($('.c_side_modal_box .c_side_modal').css('top').replace('px', '')) + 10 + 'px');
    }
}

//隐藏效果
function slowHide(){
//	$(document).not($("#divEmp")).unbind();
    if ($('.c_side_modal_box .c_side_modal').css('top') == '-600px') {
        clearInterval(timer2);
        $('.c_add_dept').addClass("hide");
    }
    else {
        $('.c_side_modal_box .c_side_modal').css('top', parseInt($('.c_side_modal_box .c_side_modal').css('top').replace('px', '')) - 10 + 'px');
    }
}
var leftStr="";

//查询树数据
function searchTree(){
	//重置信息
	$("#departmentId_header").val("");
	$("#departmentLevel_header").val("");
	$("#parentId_header").val("");
	$("#parentName_header").val("");
	$("#departmentNm_header").html("");
	$("#btnDepUpdate").addClass("hide");
	$("#btnDepAdd").addClass("hide");
	
	$.post($.basepath + "/contacts/departmentTree.do",function(result) {
		if (result.success == true) {
			var json= result.data;
			 // 加载数据
		    bulidDepTree(json);
		    //注册事件
		    treeClick();
		    $("a[name='showDepOne']").trigger("click");
		    //清空部门人员
		    $(".dragDropStaff").html("");
		}else{

		}
	}, "json");
}


//创建树
function bulidDepTree(jsonObj){

    var department = jsonObj[0];
    leftStr ="";
    setLeft(department,"");
    $("#depTree").html(leftStr);
}

//生成 部门树
function setLeft(department,parentName){
	var picNo = "1";
	var depName = 'name = "showDepOne"';
	//部门级别 1,2,3
	if (department.departmentLevel !="1"){
		picNo = "2";
		depName = "";
	}
	var picNm ="open_selected";
	var picCol="#ffffff";
	var ulDisplay="display: block";
	if (department.departmentLevel =="2" || department.departmentLevel =="3"){
		picNm = "close";
		picCol= "#c5c5c5";
		ulDisplay="display: none;";
	}
	var depId = department.departmentId;
	var depLevel = department.departmentLevel;
	var depNm = department.departmentNm;
	var memCount = department.members;
	var departments = department.departmentList;
	var parentId = department.parentId;
	leftStr = leftStr + '<li class="angular-ui-tree-node">';
	leftStr = leftStr + '<div style="position:relative;width:100%;" class="area">';
	leftStr = leftStr + '<a style="color:;" class="dep-name" href="javascript:void(0)" '+depName+'>';
	leftStr = leftStr + '<i class="iconfont icon-dept"><img src="../resources/images/contacts/'+ picNo +'.png" style="width: 18px; height: 18px;"></i>';
	leftStr = leftStr + '<input type="hidden" class="depId" value="'+ depId +'"/>';
	leftStr = leftStr + '<input type="hidden" class="depLevel" value="'+ depLevel +'"/>';
	leftStr = leftStr + '<input type="hidden" class="depParentId" value="'+ parentId +'"/>';
	leftStr = leftStr + '<input type="hidden" class="depParentName" value="'+ parentName +'"/>';
	leftStr = leftStr + '<span>&nbsp;</span><span class="depNm">'+ depNm +'</span>';
	leftStr = leftStr + '<span class="count"><span> (</span><span class="depCount">'+ memCount +'</span><span>人)</span></span></a>';
	if (departments !=null && departments.length > 0){
		leftStr = leftStr + '<a class="dep-tree-pos-btn" href="javascript:void(0)"><span class="iconfont icon-collapsed" style="color:#'+ picCol +';"><img class="icon_img" src="../resources/images/contacts/'+ picNm +'.png" style="width: 14px; height: 14px;"></span></a>';
		leftStr = leftStr + '</div>';
		leftStr = leftStr + '<ul style="margin-left:15px; '+ulDisplay+'">';
		for (var i =0;i < departments.length;i++){
			setLeft(departments[i],depNm);
		}
	leftStr = leftStr + '</ul>';
	}else{
		leftStr = leftStr + '</div>';
	}
leftStr = leftStr + '</li>';
}

//设置树事件
function treeClick(){
    //部门点击事件
    $('.dep-name').click(function(){
    	$("#depTree").find(".dep-name").removeClass("dep-name-hl");
        $(this).addClass("dep-name-hl");
    	
    	var departmentNm = $(this).parent("div").find(".depNm").html();
    	var departmentId = $(this).parent("div").find(".depId").val();
    	var departmentLevel = $(this).parent("div").find(".depLevel").val();
    	var parentId = $(this).parent("div").find(".depParentId").val();
    	var parentName = $(this).parent("div").find(".depParentName").val();
    	//设置 部门名称
    	$("#departmentNm_header").html(departmentNm);
    	//设置 部门ID
    	$("#departmentId_header").val(departmentId);
    	//设置 部门等级
    	$("#departmentLevel_header").val(departmentLevel);
    	//设置 父级部门ID
    	$("#parentId_header").val(parentId);
    	//设置 父级部门ID
    	$("#parentName_header").val(parentName);
    	//是否显示 部门设置
    	if (departmentLevel =="1"){
    		// 隐藏 部门设置 按钮 
    		$("#btnDepUpdate").addClass("hide");
    	}else
    		{	//显示 部门设置按钮
    			$("#btnDepUpdate").removeClass("hide");
    		}
    	if (departmentLevel =="3"){
    		// 隐藏 添加下级 按钮 
    		$("#btnDepAdd").addClass("hide");
    	}else
    		{	//显示 添加下级按钮
    			$("#btnDepAdd").removeClass("hide");
    		}
    	
    	//请求部门人员信息
    	$.post($.basepath + "/contacts/memberList.do", $.param({"departmentId":departmentId}),function(result) {
    		if (result.success == true) {
    			//员工信息
    			var htmlContent=$("#memtml").tmpl({"infoList":result.data});
    			$(".dragDropStaff").html(htmlContent);
    			$("#checkAll").prop("checked", false);
    			memCheckClick();
    		}else{
    			$(".dragDropStaff").html("");
    			$("#checkAll").prop("checked", false);
    			$(".c_right_box").toast(result.errorMsg,"error");
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
}

/**
 * 激活员工
 */
function activateMember(customerId,phone,customerName){
	$.ajax({
		type:'POST',
		url:$.basepath + "/contacts/activateMember.do",
		data:$.param({"customerId":customerId,"phone":phone,"customerName":customerName}),
		dataType:"json",
		timeout:$.time_out,
		success:function(result, status) {
			if (result.success == true) {
				//业务正确
				$(".c_right_box").toast("发送邀请成功！","success");	
			}else{
				//业务错误
				$(".c_right_box").toast(result.errorMsg,"error");
			}
		},
		error:function(){
			//异常错误
			$(".c_right_box").toast("请求失败！","error");
		},
		complete:function(result) {
			//最终执行
		}
	});
}

/**
 * 显示员工详细信息
 */
function showMemberDetail(memberId,updatedAt){
	//重置员工信息
	memResit();
	
	//请求部门人员信息
	$.post($.basepath + "/contacts/memberDetailData.do", $.param({"memberId":memberId}),function(result) {
		if (result.success == true) {
			info = result.data;
			$("#memForm input[name='userNm']").val(info.userNm);
			$("#memForm .userNm").html(info.userNm);
			$("#memForm input[name='memberId']").val(info.memberId);
			$("#memForm span[name='memberIdShow']").html("(员工UserID："+ info.memberId+")");
			$("#memForm span[name='memberIdShow']").removeClass("hide");
			$("#memForm input[name='phone']").val(info.phone);
			$("#memForm input[name='departmentId']").val(info.departmentId);
			$("#memForm span[name='departmentNm']").html(info.departmentNm);
			$("#memForm input[name='positions']").val(info.positions);
			$("#memForm input[name='email']").val(info.email);
		}else{
		}
	}, "json");

    $("#divEmp").removeClass("hide");
    $('#btnDelEmp').removeClass("hide");
    $('.c_side_modal_box .c_side_modal').css('top', '-600px');
    timer1 = setInterval(slowShow, 5);
}

//员工信息重置
function memResit(){
	$('#btnDelEmp').addClass("hide");
	//员工名称
	$("#memForm input[name='userNm']").val("");
	$("#memForm .userNm").html("");
	//员工编号
	$("#memForm input[name='memberId']").val("");
	$("#memForm span[name='memberIdShow']").html("");
	$("#memForm span[name='memberIdShow']").addClass("hide");
	//员工手机
	$("#memForm input[name='phone']").val("");
	$("#memForm input[name='phone']").attr("disabled","disabled");
	//部门编号
	$("#memForm input[name='departmentId']").val("");
	//部门名称
	$("#memForm span[name='departmentNm']").html("");
	//职务
	$("#memForm input[name='positions']").val("");
	//邮箱
	$("#memForm input[name='email']").val("");
}

//部门信息重置
function depResit(){
	$("#btnDelDep").addClass("hide");
	$("#divParent").addClass("hide");
	//部门名称
	$("#depForm input[name='departmentNm']").val("");
	$("#depForm span[name='divDephead']").html("");
	//部门编号
	$("#depForm input[name='departmentId']").val("");
	$("#depForm span[name='departmentIdShow']").html("");
	$("#depForm span[name='departmentIdShow']").addClass("hide");
	//上级部门编号
	$("#depForm input[name='parentId']").val("");
	//上级部门名称
	$("#depForm span[name='parentName']").html("");
}

//单选事件
function memCheckClick(){
	/*单选click事件*/
	$("input[name='check']").click(function(){
		var allchecked = $("input[name='check']:checked").length;
		var allcheck = $("input[name='check']:visible").length;
		switch(allchecked) {
			case 0:
				$("#checkAll").prop("checked", false);
				break;
			case allcheck:
				$("#checkAll").prop("checked", true); 
				break;
			default:
				$("#checkAll").prop("checked", false); 
				break;
		}
	});
}

/**
 * 初始化校验
 */
function checkInput(){
	//校验初始化
	//员工校验
	var userNm_mem="姓名";
	$("#userNm_mem").addClass("{required:['"+userNm_mem+"']" +
			",maxlength:[36,'"+userNm_mem+"']" +
			"}"); 
	
	var phone_mem="手机号";
	$("#phone_mem").addClass("{required:['"+phone_mem+"']" +
			",mobileOrTel:['"+phone_mem+"']" +
			",maxlength:[36,'"+phone_mem+"']" +
			"}"); 
	
	var positions_mem="职位";
	$("#positions_mem").addClass("{" +
			"maxlength:[36,'"+positions_mem+"']}");

	var email_mem="邮箱";
	$("#email_mem").addClass("{" +
			"email:['"+email_mem+"']" +
			",maxlength:[50,'"+email_mem+"']}");
	
	//部门校验
	var departmentNm="部门名称";
	$("#departmentNm").addClass("{required:['"+departmentNm+"']" +
			",maxlength:[36,'"+departmentNm+"']" +
			"}"); 
}