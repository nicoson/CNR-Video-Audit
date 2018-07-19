$(function() {
	// 初始化 页面数据
	initDangerLevelData();

	// 添加按钮
	$(".ad,.add").click(
			function() {
				$("#levelList").append($("#setLevel").html());// 需要添加
				$(".levelLine").last().find(".levelTxt").next().removeAttr(
						"class").addClass("none");

			});
});

// 移除按钮
function deleteLineCon(obj) {
	$(obj).parent().parent().parent().remove();
	// alert(1)
}

// 保存按钮
function save() {

	// 页面危险等级设置的数据
	var danderLeveData = getlineInfo();
	// $.ajax({
	// async : false,
	// url : $.basepath + "/v1/config/Postlevel.do",
	// method : "post",
	// data : {
	// "danderLeveData" :danderLeveData
	// },
	// success : function(result) {
	//			
	// }
	//		
	// });
	top.layer.confirm('确定保存吗？', {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function(e) {
		//alert("432");
		$.ajax({
			async : false,
			url : $.basepath + "/v1/config/Postlevel.do",
			method : "post",
			data : {
				"danderLeveData" : JSON.stringify(danderLeveData)
			},
			complete : function(result) {
				if (result.success) {
					top.layer.alert("保存成功", {
						icon : 1,
						skin : 'layer-ext-moon'
					}, function(e) {
						top.layer.close(e);
						// top.sd_removeAndFN("success");
					});
				} else {
					top.layer.alert("保存失败", {
						icon : 2,
						skin : 'layer-ext-moon'
					});
				}
			}
		});
		top.layer.close(e);
	}, function() {
	});
}

// 取消按钮
function cancel() {
	top.layer.confirm('确定取消吗？', {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function(e) {
		$("#levelList").html('');
		// 初始化 页面数据
		initDangerLevelData();
		top.layer.close(e);
	}, function() {
	});
}

// 页面危险 等级的数据
function getlineInfo() {
	// 页面危险 等级的数据
	var danderLeveData = [];
	$("#levelList").find(".levelLine").each(function() {
		var jsonResult = {};
		var op_labelStr = $(".op_contents option[selected]", this).val();

		var op_labelArray = op_labelStr.split('_');

		// op
		var op = op_labelArray[0];
		// label
		var label = op_labelArray[1];
		// 危险等级
		var dangerLevel = $(".levelTxt option[selected]", this).val();

		jsonResult = {
			"opsOp" : op,
			"opsOplabel" : label,
			"opsOplevel" : dangerLevel
		};

		// 保存值
		danderLeveData.push(jsonResult);
	});
	return danderLeveData;
}
// 动态监听 设置项目和label
function selectProLabel(obj) {
	// 去除默认的selected
	//移出selected
	$(obj).find(".initCls").removeAttr("selected").removeClass("initCls");
	// 设置点击的项目为selected
	var value = $(obj).find("option:selected").val();
	$(obj).find("option[value='"+value+"']").siblings().removeAttr("selected");
	$(obj).find("option[value='"+value+"']").siblings().removeAttr("selected");
	//将value值为value的设为selected
	$(obj).find("option[value='"+value+"']").attr("selected",true);  
	$(obj).find("option[value='"+value+"']").prop("selected","selected");  
	
}
// 动态监听 设置危险等级 高中低
function selectDangerLevel(obj) {
	
	// 去除默认的selected
	//移出selected
	$(obj).find(".initCls").removeAttr("selected").removeClass("initCls");
	// 这就是selected的值
	var selectedVal = $(obj).children('option:selected').val();
	$(obj).find("option[value='"+selectedVal+"']").siblings().removeAttr("selected");
	$(obj).find("option[value='"+selectedVal+"']").siblings().removeAttr("selected");
	
	//将value值为value的设为selected
	$(obj).find("option[value='"+selectedVal+"']").prop("selected","selected");  
	$(obj).find("option[value='"+selectedVal+"']").attr("selected",true);  
	var className = "";
	switch (selectedVal) {
	case "3":// height
		className = "height";
		break;
	case "2":// middel
		className = "middel";
		break;
	case "1":// low
		className = "low";
		break;
	case "0":// none
		className = "none";
		break;
	}
	// 根据选中的值
	$(obj).next().removeAttr("class");
	$(obj).next().addClass(className);
}
/**
 * 下拉框改变时,移除原始默认值
 */
function selectChange(obj) {
	// $(obj).change(function(){
	// 清空默认选中
	$(obj).find(".selected").attr("selected", false);
	$(obj).find(".selected").removeAttr("selected");
	// });
	// 监听下拉框发生变化
	/*
	 * $(obj).change(function(){ $(this).find("option").removeAttr("selected");
	 * });
	 */

}

// 初始化 页面数据
function initDangerLevelData() {
	// 获取数据库里的危险等级设置
	$.ajax({
		async : false,
		url : $.basepath + "/v1/config/level.do",
		method : "get",
		success : function(result) {
			// 将数据显示到页面
			// 遍历
			$.each(result,
					function(i, obj) {
						// var htmlContent = $("#setLevel");
						// 现将数据绑定到页面
						$("#levelList").append($("#setLevel").html());
						
						// 行 box
						var levelLine = $(".levelLine").eq(i);
						// 设置op下拉框默认选项select
						var op_contents = $(".op_contents", levelLine);
						// 设置label下拉框默认选项select
						var levelTxt = $(".levelTxt", levelLine);
						// 移除下拉框默认选项
						selectChange($(op_contents));
						// 移除下拉框默认选项
						selectChange($(levelTxt));
						// 判断 op label level
						switch (obj.opsOp) {
						case "terror": // 暴恐
							// 判断label
							if (obj.opsOpLabel == "0") {// 0==正常
								//页面初始化的时候,添加 initCls  为了 下拉框 change  使用
								$("option[value='terror_0']", op_contents).addClass("initCls");
								$("option[value='terror_0']", op_contents)
										.attr("selected", true);
							} else if (obj.opsOpLabel == "1") {// 暴恐
								$("option[value='terror_1']", op_contents).addClass("initCls");
								$("option[value='terror_1']", op_contents)
										.attr("selected", true);
							}
							// 设置危险等级
							if (obj.opsOpLevel == "0") {// 无
								$("option[value='0']", levelTxt).addClass("initCls");
								$("option[value='0']", levelTxt).attr("selected", true);
								// 设置背景颜色
								$(levelTxt).next().addClass("none");

							} else if (obj.opsOpLevel == "1") {// 低
								$("option[value='1']", levelTxt).addClass("initCls");
								$("option[value='1']", levelTxt).attr(
										"selected", true);
								// 设置背景颜色
								$(levelTxt).next().addClass("low");

							} else if (obj.opsOpLevel == "2") {// 中
								$("option[value='2']", levelTxt).addClass("initCls");
								$("option[value='2']", levelTxt).attr("selected", true);
								// 设置背景颜色
								$(levelTxt).next().addClass("middel");
							} else if (obj.opsOpLevel == "3") {// 高
								$("option[value='3']", levelTxt).addClass("initCls");
								$("option[value='3']", levelTxt).attr(
										"selected", true);
								// 设置背景颜色
								$(levelTxt).next().addClass("height");
							}

							break;
						case "face_group_search":// 政治
							// 判断label level
							if (obj.opsOpLabel == "0") {// 0==正常
								$("option[value='politician_0']", op_contents).addClass("initCls");
								$("option[value='politician_0']", op_contents)
										.attr("selected", true);
							} else if (obj.opsOpLabel == "1") {// 涉政
								$("option[value='politician_1']", op_contents).addClass("initCls");
								$("option[value='politician_1']", op_contents)
										.attr("selected", true);
							}

							// 设置危险等级
							if (obj.opsOpLevel == "0") {// 无
								$("option[value='0']", levelTxt).addClass("initCls");
								$("option[value='0']", levelTxt).attr("selected", true);
								// 设置背景颜色
								$(levelTxt).next().addClass("none");
							} else if (obj.opsOpLevel == "1") {// 低
								$("option[value='1']", levelTxt).addClass("initCls");
								$("option[value='1']", levelTxt).attr(
										"selected", true);
								// 设置背景颜色
								$(levelTxt).next().addClass("low");
							} else if (obj.opsOpLevel == "2") {// 中
								$("option[value='2']", levelTxt).addClass("initCls");
								$("option[value='2']", levelTxt).attr("selected", true);
								// 设置背景颜色
								$(levelTxt).next().addClass("middel");
							} else if (obj.opsOpLevel == "3") {// 高
								$("option[value='3']", levelTxt).addClass("initCls");
								$("option[value='3']", levelTxt).attr("selected", true);
								// 设置背景颜色
								$(levelTxt).next().addClass("height");
							}
							// $("#levelList").html(htmlContent);
							break;
						case "pulp_beta": // 鉴黄
							// 判断label level
							if (obj.opsOpLabel == "0") {// 0==黄色
								$("option[value='pulp_0']", op_contents).addClass("initCls");
								$("option[value='pulp_0']", op_contents).attr("selected", true);
							} else if (obj.opsOpLabel == "1") {// 性感
								$("option[value='pulp_1']", op_contents).addClass("initCls");
								$("option[value='pulp_1']", op_contents).attr(
										"selected", true);
							} else if (obj.opsOpLabel == "2") {// 正常
								$("option[value='pulp_2']", op_contents).addClass("initCls");
								$("option[value='pulp_2']", op_contents).attr(
										"selected", true);
							}
							// 设置危险等级
							if (obj.opsOpLevel == "0") {// 无
								$("option[value='0']", levelTxt).addClass("initCls");
								$("option[value='0']", levelTxt).attr(
										"selected", true);
								// 设置背景颜色
								$(levelTxt).next().addClass("none");
							} else if (obj.opsOpLevel == "1") {// 低
								$("option[value='1']", levelTxt).addClass("initCls");
								$("option[value='1']", levelTxt).attr(
										"selected", true);
								// 设置背景颜色
								$(levelTxt).next().addClass("low");
							} else if (obj.opsOpLevel == "2") {// 中
								$("option[value='2']", levelTxt).addClass("initCls");
								$("option[value='2']", levelTxt).attr(
										"selected", true);
								// 设置背景颜色
								$(levelTxt).next().addClass("middel");
							} else if (obj.opsOpLevel == "3") {// 高
								$("option[value='3']", levelTxt).addClass("initCls");
								$("option[value='3']", levelTxt).attr(
										"selected", true);
								// 设置背景颜色
								$(levelTxt).next().addClass("height");
							}
							// $("#levelList").html(htmlContent);
							break;
						}
				    });
		}

	});
}
