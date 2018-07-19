$.qiNiu = $.qiNiu || {};
;(function($) {
/**************************DATE方法扩展**********************************/
/**
 * 字符串转日期
 * @param dateStr 要转换的字符串，字符串格式要求：yyyy-MM-dd HH:mm:ss.SSS、yyyy/MM/dd HH:mm:ss.SSS
 * 				不一定年月日时分秒都有，但是各个位数、顺序、分隔符等要跟格式中保持一致，例如yyyy-MM也可以
 * @returns 转换后的日期
 */
Date.parseDate = function(dateStr) {
	return new Date(Date.parse(dateStr.replace(/-/g,"/")));
};

/*********************  jQuery Validator   ***************************/
if ($ && $.validator) {
	$.validator.setDefaults({
		errorElement:"img",
		errorClass:"error_validate"
	});
}

/**
 * 字符串转日期
 * @param dateStr 要转换的字符串
 * @param pattern 格式，默认yyyy-MM-dd
 * @returns 转换后的日期
 */
Date.parseDateByPattern = function(dateStr, pattern) {
	pattern = pattern || "yyyy-MM-dd";
	var yearStart = pattern.indexOf("y");
	var yearEnd = pattern.lastIndexOf("y");
	var monthStart = pattern.indexOf("M");
	var monthEnd = pattern.lastIndexOf("M");
	var dateStart = pattern.indexOf("d");
	var dateEnd = pattern.lastIndexOf("d");
	var hourStart = pattern.indexOf("H");
	var hourEnd = pattern.lastIndexOf("H");
	var minuteStart = pattern.indexOf("m");
	var minuteEnd = pattern.lastIndexOf("m");
	var secondStart = pattern.indexOf("s");
	var secondEnd = pattern.lastIndexOf("s");
//	var miliSecondStart = pattern.indexOf("S");
//	var miliSecondEnd = pattern.lastIndexOf("S");
	
	var yearStr = dateStr.substring(yearStart, yearEnd + 1);
	var monthStr = dateStr.substring(monthStart, monthEnd + 1);
	var dayStr = dateStr.substring(dateStart, dateEnd + 1);
	var hourStr = dateStr.substring(hourStart, hourEnd + 1);
	var minuteStr = dateStr.substring(minuteStart, minuteEnd + 1);
	var secondStr = dateStr.substring(secondStart, secondEnd + 1);
//	var miliSecondStr = dateStr.substring(miliSecondStart, miliSecondEnd);
	
	var result = (yearStr==""?"2000":yearStr) + "/" + (monthStr==""?"01":monthStr)
				+ "/" + (dayStr==""?"01":dayStr) + " " + (hourStr==""?"00":hourStr)
				+ ":" + (minuteStr==""?"00":minuteStr) + ":" + (secondStr==""?"00":secondStr);
//				+ "." + (miliSecondStr==""?"000":miliSecondStr);
	
	return Date.parseDate(result);
};

/**
 * 日期格式化：yyyy-MM-dd HH:mm:ss.SSS
 * @pattern 格式
 * @return 格式化后的字符串
 */
Date.prototype.format = function(pattern) {
	pattern = pattern || "";
	if (pattern == "") {
		return this.toString();
	}
	var o = {
		"M+" : this.getMonth()+1,					//月份   
		"d+" : this.getDate(),						//日   
		"H+" : this.getHours(),				   		//小时   
		"m+" : this.getMinutes(),				 	//分   
		"s+" : this.getSeconds(),				 	//秒   
		"q+" : Math.floor((this.getMonth()+3)/3), 	//季度
		"S"  : this.getMilliseconds()				//毫秒   
	};
	if(/(y+)/.test(pattern)) {
		pattern = pattern.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	} 
	for(var k in o) {
		if(new RegExp("("+ k +")").test(pattern)) {
			pattern = pattern.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		} 
	}
	return pattern; 
};
/**
 * 日期加减
 * @param diff 加减的值，正数为加， 负数为减
 * @returns 计算后的结果Date
 */
Date.prototype.addDays = function(diff) {
	if (!$.isNumeric(diff)) {
		return this;
	}
	return new Date(this.getTime() + 86400000 * diff);
};
/***********************   AJAX   ***************************/
	$(document).bind("ajaxStart.showloading", function () {
		//ajax请求执行之前调用
		//显示等待Loading
		if (top && top.showLoadingWin) {
			top.showLoadingWin();
		}
	}).ajaxStop(function() {
		//ajax请求执行完成后调用
		//隐藏等待Loading
		if (top && top.hideLoadingWin) {
			top.hideLoadingWin();
		}
	});
/******************页面元素对象方法扩展*******************/
	$.fn.extend({
		/**
		 * 用数据填充下拉框（清除原有选项）
		 * 调用示例：
		 * $("#selectId").initTbItems({
		 		tbData:tbData
		 * 		value:"testvalue",
		 * 		text:"testtext",
		 * 		blank:{need:true, text:"----请选择----", value:""},
		 * 		callback:function(){xxxxx}
		 * });
		 * options如下：
		 * 	tbData(必选):数据
		 * 	value(必选):下拉框的value字段
		 * 	text(必选):下拉框显示的内容字段
		 * 	blank(可选):空选项相关
		 * 		need(可选)：是否需要，默认为false
		 * 		text(可选)：空选项显示的内容，默认为"请选择"
		 * 		value(可选):空选项的值，默认为""
		 *	callback(可选):可选，在下拉框数据加载完成后执行
		 */
		initTbItems:function(options) {
			var $this = $(this);
			$this.empty();
			options = options || {};
			options.blank = options.blank || {};
			if (options.blank.need) {
				var option = $("<option value='" + (options.blank.value || "") + "'>" + (options.blank.text || "请选择") + "</option>");
				$this.append(option);
			}
			if (!options.value || !options.text) {
				return;
			}
			options.callback = options.callback || function(){};
			$this.fillSelectOptions(options.tbData, options.value, options.text, options.attr);
			options.callback($this);
		},
		/**
		 * 用字典项填充下拉框（清除原有选项）
		 * 调用示例：
		 * $("#selectId").initDicItems({
		 * 		dicCode:"test",
		 * 		value:"testvalue",
		 * 		text:"testtext",
		 * 		attr:{"test1":"testid1", "test2":"testid2"},
		 * 		blank:{need:true, text:"----请选择----", value:""},
		 * 		callback:function(){xxxxx}
		 * });
		 * options如下：
		 * 	dicCode(必选):字典CODE
		 * 	value(可选):下拉框的value字段，默认为：dictionaryItemCode
		 * 	text(可选):下拉框显示的内容字段，默认为：dictionaryItemName
		 * 	attr(可选):下拉框option的自定义属性, 形式为：{"demo1":"xxx", "demo2":"xxxxx",......},demo为属性名 ，xxx为字段名
		 *	callback(可选):可选，在下拉框数据加载完成后执行
		 * 	blank(可选):空选项相关
		 * 		need(可选)：是否需要，默认为false
		 * 		text(可选)：空选项显示的内容，默认为"请选择"
		 * 		value(可选):空选项的值，默认为""
		 */
		initDicItems:function(options) {
			var $this = $(this);
			$this.empty();
			options = options || {};
			options.blank = options.blank || {};
			if (options.blank.need) {
				var option = $("<option value='" + (options.blank.value || "") + "'>" + (options.blank.text || "危险等级") + "</option>");
				$this.append(option);
			}
			if (!options.dicCode) {
				return;
			}
			options.callback = options.callback || function(){};
			$.post($.basepath + "/index/getDictionaryItemsByCode.do", {"type":options.dicCode}, function(result, status) {
				if (result.success) {
					$this.fillSelectOptions(result.data, options.value || "value", options.text || "label", options.attr);
				}
				options.callback($this);
			}, "json");
		},
		/**
		 * 将Form里的元素序列化，返回JSON类型的数据，key为name
		 * @return JSON 序列化后的JSON数据
		 */
		serializeJson:function() {
			var jsonResult = {};
			$.each($(this).serializeArray(), function(i, field) {
				var value = field.value;
				if (jsonResult[field.name] != undefined) {
					var _oldValue = jsonResult[field.name];
					if (_oldValue instanceof Array) {
						_oldValue.push(value);
						value = _oldValue;
					} else {
						value = [_oldValue, value];
					}
				}
				jsonResult[field.name] = value;
			});
			return jsonResult;
		},
		/**
		 * data:数据
		 * value：data中的字段名，用于填充在value中
		 * name：data中的字段名，用于显示
		 * attr:option自定义的属性
		 */
		fillSelectOptions:function(data, value, name, attr) {
			if ($.isArray(data)) {
				for (var i = 0; i < data.length; i++) {
					var option = $("<option></option>");
					option.val(data[i][value]).text(data[i][name]).appendTo($(this));
					if (attr) {
						for (var key in attr) {
							option.attr(key, data[i][attr[key]] || "");
						}
					}
				}
				$(this).initSelectedOption();
			}
		},
		/**
		 * 根据下拉框的dval，初始化下拉框的值
		 */
		initSelectedOption:function() {
			var defaultValue = $(this).attr("dval");
			if (defaultValue && defaultValue != "") {
				$(this).find("option").prop("selected", false);
				$(this).find("option[value='" + defaultValue + "']").prop("selected", true);
			} else {
				$(this).find("option:first").prop("selected", false);
			}
		},
		/**
		 * 添加日历控件
		 * @param options 参数，常用如下：
		 * dateFmt:日期格式，默认:yyyy-MM-dd
		 * defaultDate:默认日期，不写则无默认值，0为当天，负数为向前，正数为向后; 字符则直接显示
		 * enabledDays:只启用的所指定的日期（(1至7 分别代表 周一至周日），数组，例如[1,6]代表只启用周一周六，其他禁用
		 * enabledDates:只启用指定的日期，数组，如['05','15','25'],只有每月5号，15号，25号有效
		 * minDate_val:最小日期(值，如：2014-09-01)
		 * maxDate_val:最大日期(值，如：2014-09-01)
		 * minDate_diff:最小日期（0为当天，负数为向前，正数为向后）
		 * maxDate_diff:最大日期（0为当天，负数为向前，正数为向后）
		 * minDate_dyn:最小日期(页面元素的ID)，参照其他页面元素的值
		 * minDate_dyn_diff: minDate_dyn的补充，取得元素的值之后，再偏移minDate_dyn_diff天，为最小日期（0为当天，负数为向前，正数为向后）
		 * minDate_dyn_val:当minDate_dyn为空时，以minDate_dyn_val为最小日期
		 * maxDate_dyn:最大日期(页面元素的ID)，参照其他页面元素的值
		 * maxDate_dyn_diff: maxDate_dyn的补充，取得元素的值之后，再偏移maxDate_dyn_diff天，为最大日期（0为当天，负数为向前，正数为向后）
		 * maxDate_dyn_val:当maxDate_dyn为空时，以maxDate_dyn_val为最大日期
		 * 说明：三个最大/最小日期，只有一个生效，生效优先级：dyn > diff > val
		 * onpicked:日期选择后，触发的事件（function）
		 */
		my97DatePicker:function(options) {
			options = options || {};
			//有效星期几
			var enabledDays = options.enabledDays || [];
			if ($.isArray(enabledDays) && enabledDays.length > 0) {
				var disabledDays = [];
				for (var i = 1; i <= 7; i += 1) {
					if ($.inArray(i, enabledDays) == -1 && $.inArray(i + "", enabledDays) == -1) {
						disabledDays.push(i % 7);
					}
				}
				//禁用
				options.disabledDays = disabledDays;
				//高亮
				options.specialDays = enabledDays;
			}
			//有效日期
			if (options.enabledDates != undefined) {
				var enabledDates = [];
				for (var i = 0; i < options.enabledDates.length; i += 1) {
					var value = options.enabledDates[i];
					if ($.isNumeric(value)) {
						value = parseInt(value, 10);
						value = value > 9 ? value : "0" + value;
						enabledDates.push(value + "$");
					}
				}
				if (enabledDates.length > 0) {
					//启用
					options.disabledDates = enabledDates;
					//高亮
					options.specialDates = enabledDates;
					options.opposite = true;
				}
			}
			//最小日期
			var minDate_diff = options.minDate_diff;
			if (minDate_diff != undefined && $.trim(minDate_diff) != '' && $.isNumeric(minDate_diff)) {
				minDate_diff = parseInt(minDate_diff, 10);
				if (minDate_diff >= 0) {
					minDate_diff = " + " + minDate_diff;
				}
				minDate_diff = "%y-%M-{%d" + minDate_diff + "}";
			}
			var minDate_dyn = options.minDate_dyn;
			if (minDate_dyn != undefined && $.trim(minDate_dyn) != '') {
				minDate_dyn = "#F{$dp.$D('" + minDate_dyn + "', {d:" + (options.minDate_dyn_diff || 0) + "}) || '" + ___getRealData(options.minDate_dyn_val, "") + "'}";
			}
			options.minDate = ___getRealData(minDate_dyn || minDate_diff || options.minDate_val, "");
			//最大日期
			var maxDate_diff = options.maxDate_diff;
			if (maxDate_diff != undefined && $.trim(maxDate_diff) != '' && $.isNumeric(maxDate_diff)) {
				maxDate_diff = parseInt(maxDate_diff, 10);
				if (maxDate_diff >= 0) {
					maxDate_diff = " + " + maxDate_diff;
				}
				maxDate_diff = "%y-%M-{%d" + maxDate_diff + "}";
			}
			var maxDate_dyn = options.maxDate_dyn;
			if (maxDate_dyn != undefined && $.trim(maxDate_dyn) != '') {
				maxDate_dyn = "#F{$dp.$D('" + maxDate_dyn + "', {d:" + (options.maxDate_dyn_diff || 0) + "}) || '" + ___getRealData(options.maxDate_dyn_val, "") + "'}";
			}
			options.maxDate = maxDate_dyn || maxDate_diff || options.maxDate_val || "";
			//默认日期
			options.defaultDate = options.defaultDate || "";
			//日期格式
			options.dateFmt = options.dateFmt || "yyyy-MM-dd";
			options.enableInputMask = options.enableInputMask || false;
			if ($.isNumeric(options.defaultDate) && options.defaultDate != "") {
				var diff = parseInt(options.defaultDate);
				var date = new Date();
				date = date.addDays(diff);
				$(this).val(date.format(options.dateFmt) + "");
			} else {
				$(this).val(options.defaultDate + "");
			}
			$(this).attr("readonly", "readonly");
			$(this).prop("readonly", "readonly");
			$(this).bind("click.my97", function() {
				WdatePicker(options);
			});
		},
		/**
		 * 移除日历控件
		 */
		removeMy97DatePicker:function() {
			$(this).removeAttr("readonly");
			$(this).removeProp("readonly");
			$(this).unbind("click.my97");
		},
		/**
		 * 填充列表数据
		 * @param data JSONArray数据；页面table属性如下：
		 * 标题行的tr中要用th标签，th标签中要有propkey与dval属性，其中操作列的propkey="actopts"，其他行用td标签
		 * @param options 参数如下：
		 * JSONArray 除操作列以外的链接，具体如下：
		 * options.trDataBoundCallBack 行数据绑定完成后，调用的回调函数
		 * options.tableDataBoundCallBack table数据绑定完成后，调用的回调函数
		 * options.saveRowData 保存行数据，点击显示行详细时用，默认false
		 * options.rowCntPerPage 列表每页行数，默认为0，即不分页
		 * options.showAllRows:是否显示所有行，空行显示空白；默认为true
		 * options.{&propkey}：用于判断该列是否有链接，比如单位名称一列有链接，该列tr的propkey为"custName"，
		 * 											则options里面加一个参数custName；因此propkey不可重复。
		 * 		{&propkey}_propClass:结果集中带出来的样式，一般用于区分不同类型的数据
		 * 		displayContent：显示的名称，比如：多个联系人时，显示【查看联系人】
		 * 		name_EnableHtml:名称是否支持HTML,默认为false
		 * 		className: 链接样式
		 * 		condition: 显示链接的判断条件，可以为语句，可以为方法，只要结果为true或false就行,默认为true（显示）
		 * 		showLinkWithoutEvent:无事件（即condition判断不通过）时，是否显示链接，默认false（不显示）
		 * 		optActions:JSONArray 操作事件：
		 * 			actionName：事件名称
		 * 			actionFunc：事件方法
		 * 			actionParams：Array事件参数的key，用于从data中取值(如不存在，则取"")，回调时参数以JSON格式传递;如果无参数，默认传data
		 * 			paramFormData：是否从data中取得参数，默认为true
		 * 			如：actionFunc=test, actionParams=[a, b], 回调时：test({a:xxx, b:xxxx})
		 * options.opts JSONArray操作列，数组中每个元素具体如下：
		 * 		displayContent:显示的名称
		 *		name_EnableHtml:名称是否支持HTML,默认为false
		 * 		className: 链接样式
		 * 		condition: 显示链接的判断条件，可以为语句，可以为方法，只要结果为true或false就行,默认为true（显示）
		 * 		showLinkWithoutEvent:无事件（即condition判断不通过）时，是否显示链接，默认false（不显示）
		 * 		optActions:JSONArray 操作事件：
		 * 			actionName：事件名称
		 * 			actionFunc：事件方法
		 * 			actionParams：Array事件参数的key，用于从data中取值(如不存在，则取"")，回调时参数以JSON格式传递;如果无参数，默认传data
		 * 			paramFormData：是否从data中取得参数，默认为true
		 * 			如：actionFunc=test, actionParams=[a, b], 回调时：test({a:xxx, b:xxxx})
		 */
		fillListData:function(data, options) {
			data = data || [];
			options = options || {};
			options.saveRowData = options.saveRowData || false;
			options.showAllRows = ___getRealData(options.showAllRows, true);
			options.rowCntPerPage = options.rowCntPerPage || 0;
			options.trDataBoundCallBack = options.trDataBoundCallBack || function() {};
			options.tableDataBoundCallBack = options.tableDataBoundCallBack || function() {};
			
			var index = 0;
			//清空TD里面的内容
			//$(this).find("tr:gt(1)").remove();
			var trindex = 1;
			var $templateArr = $(this).find("tr#template");
			$templateArr.each(function() {
				if (this.rowIndex > trindex) {
					trindex = this.rowIndex;
				}
			});
			$(this).find("tr:gt(" + trindex + ")").remove();
			
			//页面上的行数与data里数据的行数，取较小的值为循环的基数
			var rowCnt = data.length;
			if (options.rowCntPerPage != 0 && data.length > options.rowCntPerPage) {
				rowCnt = options.rowCntPerPage;
			}
			var $template = $(this).find("tr#template");
			var $table = $template.closest("table");
			$table.data("rowCntPerPage", options.rowCntPerPage);
			$template.hide();
			//循环写数据
			for (var rowIndex = 0; rowIndex < rowCnt; rowIndex += 1) {
				//添加行
				var $tr = $template.clone(true);//获取table模板
				$table.append($tr);//添加新行
				$tr.removeAttr("id");
				$tr.show();
				
				//保存行数据
				if (options.saveRowData) {
					$tr.data("rowData", data[rowIndex]);
				}
				//设置行数据
				_setRowData($tr, options, data[rowIndex], (rowIndex * 100 + index), rowIndex);
				//调用行数据绑定回调函数
				options.trDataBoundCallBack.call($tr, data[rowIndex]);
			}
			if (options.showAllRows) {
				for (var rowIndex = rowCnt; rowIndex < options.rowCntPerPage; rowIndex += 1) {
					//添加行
					var $tr = $template.clone(true);//获取table模板
					$table.append($tr);//添加新行
					$tr.removeAttr("id");
					$tr.find("input:checkbox").remove();;
					$tr.find(".hidewithoutdata:not(td)").remove();
					$tr.show();
				}
			}
			//调用table数据绑定回调函数
			options.tableDataBoundCallBack.call($table, data);
		},
		/**
		 * 取得行数据
		 * @return 行数据，前提是写列表数据的时候，保存了行数据，saveRowData:true
		 */
		getTableRowData:function() {
			return $(this).data("rowData");
		},
		/**
		 * 填充表单数据
		 * @param data JSON数据；支持元素类型：input,textarea,select,label,div,span,td；元素属性如下：
		 * 		propkey Json数据的key，用于从Json中取值，绑定到页面元素
		 * 		dval 默认值，Json中取值为空的情况下，填充dval
		 * @param filePath 有文件上传的话，需要传此参数，指明文件保存的路径，用于查看文件时使用
		 */
		fillFormData:function(data, filePath) {
			data = data || {};
			var idExist = true;
			var index = 0;
			if (!$(this).hasAttr("id")) {
				idExist = false;
				$(this).attr("id", "_myTempId_" + (index++));
			}
			var $form = $(this);
			var $thisId = "#" + $(this).attr("id");
			//移除JS自动为File添加的隐藏字段
			$($thisId + " .hidfile").remove();
			$($thisId + " input, " + $thisId + " textarea, " + $thisId + " select").each(function() {
				var eleDval = _getDval($(this));
				if ($(this).hasAttr("type") && ($(this).attr("type") == "text" || $(this).attr("type") == "hidden" 
					|| $(this).attr("type") == "password") || !$(this).is("input")) {
					//输入框、textarea或select
					if ($(this).hasAttr("propkey")) {
						$(this).val("");
						var propkey = $(this).attr("propkey");
						if (data[propkey] != undefined) {
							$(this).val(data[propkey] + "");
						} else if ($(this).hasAttr("dval")) {
							$(this).val(eleDval + "");
						}
					} else if ($(this).hasAttr("dval")) {
						$(this).val("");
						$(this).val(eleDval + "");
					}
					if($(this).is("select")) {
						if ($(this).hasAttr("propkey")) {
							var propkey = $(this).attr("propkey");
							if (data[propkey] != undefined) {
								$(this).attr("dval", data[propkey] + "");
							}
						}
						$(this).initSelectedOption();
					}
				} else if ($(this).hasAttr("type") && ($(this).attr("type") == "radio" || $(this).attr("type") == "checkbox")) {
//					$(this).removeAttr("checked");
//					$(this).removeProp("checked");
					$(this).prop("checked", false);
					//radio或checkbox
					if ($(this).hasAttr("propkey")) {
						var propkey = $(this).attr("propkey");
						if (data[propkey] != undefined && _containsValue(data[propkey], $(this).val())) {
//							$(this).attr("checked", true);
							$(this).prop("checked", true);
						} else if (data[propkey] == undefined && $(this).hasAttr("dval") && _containsValue($(this).attr("dval"), $(this).val())) {
//							$(this).attr("checked", true);
							$(this).prop("checked", true);
						}
					} else if ($(this).hasAttr("dval") && _containsValue($(this).attr("dval"), $(this).val())) {
//						$(this).attr("checked", true);
						$(this).prop("checked", true);
					}
				} else if ($(this).hasAttr("type") && ($(this).attr("type") == "file")) {
					//如果是文件上传，则添加隐藏的原文件，并判断是否有值。有值的情况下，显示“查看”“删除”按钮
					var _fileEleName = $(this).attr("name");
					var _hidOldFile = $("<input type='hidden' name='" + _fileEleName + "_OldFile' class='hidfile'/>");
					_hidOldFile.appendTo($form);
					var _hidOldFileDelFlg = $("<input type='hidden' name='" + _fileEleName + "_DelFlg' class='hidfile' value='0'/>");
					_hidOldFileDelFlg.appendTo($form);
					if ($(this).hasAttr("propkey")) {
						$(this).val("");
						_hidOldFile.val("");
						var propkey = $(this).attr("propkey");
						if (data[propkey] != undefined && data[propkey] != "") {
							var $thisfile = $(this);
							_hidOldFile.val(data[propkey] + "");
							var $view = $('<div class="button1 hidfile" style="padding:0px; margin:0 1px; cursor:pointer;" title="查看">查</div>');//"<a href='" + filePath + "/" + data[propkey] + "' class='hidfile' style='margin-left:5px;' target='_blank'>查看</a>");
							var $del = $('<div class="button1 hidfile" style="padding:0px; margin:0 1px; cursor:pointer;" title="删除">删</div>');
							$view.bind("click.delfile", function(){
								$.tabs.addTab({
									id: "file_" + data[propkey],
									title: "文件查看",
									url:filePath + "/" + data[propkey] + "?1=1"
								});
							});
							$del.bind("click.delfile", function() {$view.remove(); $del.remove(); _hidOldFileDelFlg.val("1"); $thisfile.removeData("validateExclude_File");});
							var $btnCon = $("<div style='float:left; margin-top:4px;'></div>");
							$btnCon.append($view);
							$btnCon.append($del);
							$(this).after($btnCon);
							$(this).bind("change.chgfile", function(){$view.remove(); $del.remove(); _hidOldFileDelFlg.val("1"); $thisfile.removeData("validateExclude_File");});
							if (filePath == undefined) {
								$view.hide();
							}
							$(this).data("validateExclude_File", "required");
						} else if ($(this).hasAttr("dval")) {
							_hidOldFile.val(eleDval + "");
						}
					} else if ($(this).hasAttr("dval")) {
						$(this).val("");
						$(this).val(eleDval + "");
					}
				}
			});
			
			$($thisId + " label, " + $thisId + " div, " + $thisId + " span, " + $thisId + " td").each(function() {
				var eleDval = _getDval($(this));
				if (eleDval == "filelink") {
					if ($(this).hasAttr("propkey")) {
						$(this).html("");
						var title = $(this).attr("tabtitle");
						var propkey = $(this).attr("propkey");
						if (data[propkey] != undefined) {
							var link = $("<div style='cursor:pointer; color:#2B63F3;'>点击查看</div>");
							link.bind("click", function() {
								$.tabs.addTab({
									id: "file_" + data[propkey],
									title: title || "文件查看_" + data[propkey],
									url:filePath + "/" + data[propkey] + "?1=1"
								});
							});
							link.appendTo($(this));
						}
					}
				} else {
					if ($(this).hasAttr("propkey")) {
						$(this).text("");
						var propkey = $(this).attr("propkey");
						if (data[propkey] != undefined) {
							$(this).text(data[propkey]);
						} else if ($(this).hasAttr("dval")) {
							$(this).text(eleDval);
						}
					} else if ($(this).hasAttr("dval")) {
						$(this).text("");
						$(this).text(eleDval);
					}
				}
			});
			$($thisId + " img, " + $thisId + " image").each(function() {
				if (filePath != undefined) {
					var eleDval = _getDval($(this));
					if ($(this).hasAttr("propkey")) {
						var propkey = $(this).attr("propkey");
						if (data[propkey] != undefined) {
							$(this).attr("src", filePath + "/" + data[propkey]);
						} else if ($(this).hasAttr("dval")) {
							$(this).attr("src", filePath + "/" + eleDval);
						}
					} else if ($(this).hasAttr("dval")) {
						$(this).attr("src", filePath + "/" + eleDval);
					}
					
				}
			});
			if (!idExist) {
				$(this).removeAttr("id");
			}
		},
		/**
		 * 验证元素是否有指定属性
		 * @param attrName 属性名称
		 * @return 有:true   没有:false
		 */
		hasAttr:function(attrName) {
			if ($(this).attr(attrName) == undefined) {
				return false;
			}
			return true;
		},
		/**
		 * 分页，以下为class
		 *			pager_totalrow：显示总行数
		 * 			pager_current：显示当前页
		 * 			pager_next：下一页
		 * 			pager_first：首页
		 * 			pager_last：末页
		 * 			pager_pre：上一页
		 * 			pager_turnto：跳转到指定页面的按钮
		 * 			pager_turnToPage：跳转到指定页面的输入框
		 * @param options 参数，具体如下：
		 * 			currentPage：页码，默认为1
		 * 			pageSize：每页显示的行数，默认为10
		 * 			url：请求URL
		 * 			paramFormId：查询参数所在Form的Id
		 * 			target：显示数据的Table的Id；查询成功的情况下，如果success为空，则默认显示数据到这个target
		 * 			searchbtn：查询按钮的ID
		 * 			success:成功执行的方法
		 * 			error：失败执行的方法
		 * 			searchOnLoad: 加载时执行查询，默认为false不加载
		 * 			loadBlankLines: 加载空白行，默认为true
		 */
		pager:function(options) {
			options = options || {};
			options.currentPage = options.currentPage || 1;
			options.pageSize = options.pageSize || 10;
			options.url = options.url || "";
			options.paramFormId = options.paramFormId || "";
			options.target = options.target || "";
			options.success = options.success || null;
			options.error = options.error || null;
			options.searchbtn = options.searchbtn || "";
			options.searchOnLoad = options.searchOnLoad || false;
			options.loadBlankLines = ___getRealData(options.loadBlankLines, true);
			if (options.url == "" || options.success == null && options.target == "") {
				$.setErrorMsg("分页控件初始化失败！");
				return;
			}
			$("<input type='hidden' class='_hid_pager_current' value='1'/>").appendTo(this);
			$("<input type='hidden' class='_hid_pager_totalpage' value='1'/>").appendTo(this);
			
			$this = $(this);
			$this.find(".pager_current").text("1 / 1");
			//下一页
			$this.find(".pager_next").bind("click.pager", function() {
				//计算要跳转的页码
				var currentPage = $this.find("._hid_pager_current").val();
				var totalPage = $this.find("._hid_pager_totalpage").val();
				if (!$.isNumeric(currentPage) || !$.isNumeric(totalPage)) {
					currentPage = 1;
					totalPage = 1;
				}
				currentPage = parseInt(currentPage, 10) + 1;
				//已经是最后一页,没有下一页,不查询
				if (currentPage > parseInt(totalPage, 10)) {
					currentPage = totalPage;
					return;
				}
				//设置要跳转的页码
				options.currentPage = currentPage;
				_turnToPage(options, $this);
			});
			//重新排序时，查询用
			$this.find(".pager_first").bind("clickresort", function() {
				//设置要跳转的页码
				options.currentPage = 1;
				_turnToPage(options, $this);
			});
			//首页
			$this.find(".pager_first").bind("click.pager", function() {
				var currentPage = $this.find("._hid_pager_current").val();
				//已经是第一页,则不查询
				if (currentPage == "1") {
					return;
				}
				//设置要跳转的页码
				options.currentPage = 1;
				_turnToPage(options, $this);
			});
			
			//如果查询按钮ID不为空，处理查询按钮的点击事件
			if (options.searchbtn != "") {
				//移除查询按钮的click事件，绑定分页查询事件
//				$("#" + options.searchbtn).removeAttr("onclick").unbind("click").bind("click.pager", function() {
				$("#" + options.searchbtn).change(function() {
					
					//设置查询参数
					options.param = "";
					if (options.paramFormId != "") {
						options.param = $("#" + options.paramFormId).serialize();
					}
					//设置要跳转的页码
					options.currentPage = 1;
					_turnToPage(options, $this);
				});
				//移除查询按钮子元素的click事件，防止click事件不是绑定在查询按钮上
				$("#" + options.searchbtn).children().removeAttr("onclick").unbind("click");
			}
			
			//定义一个隐藏的按钮在页面上，用于在任何情况下重新加载table数据
			var hidBtnSearch = $("<input type='button' id='__hidBtnSearch' style='display:none'/>");
			hidBtnSearch.bind("click.pager", function() {
				//设置查询参数
				options.param = "";
//				if (options.paramFormId != "") {
//					options.param = $("#" + options.paramFormId).serialize();
//				}
				//设置要跳转的页码
				options.currentPage = 1;
				_turnToPage(options, $this);
			});
			hidBtnSearch.appendTo($this);
			
			//末页
			$this.find(".pager_last").bind("click.pager", function() {
				//计算要跳转的页码
				var currentPage = $this.find("._hid_pager_current").val();
				var totalPage = $this.find("._hid_pager_totalpage").val();
				if (!$.isNumeric(totalPage)) {
					totalPage = 1;
				}
				//已经是最后一页，则不查询
				if (currentPage == totalPage) {
					return;
				}
				//设置要跳转的页码
				options.currentPage = totalPage;
				_turnToPage(options, $this);
			});
			//上一页
			$this.find(".pager_pre").bind("click.pager", function() {
				//计算要跳转的页码
				var currentPage = $this.find("._hid_pager_current").val();
				if (!$.isNumeric(currentPage)) {
					currentPage = 1;
				}
				currentPage = parseInt(currentPage, 10) - 1;
				//已经是第一页，没有上一页，不查询
				if (currentPage < 1) {
					currentPage = 1;
					return;
				}
				//设置要跳转的页码
				options.currentPage = currentPage;
				_turnToPage(options, $this);
			});
			//跳转-回车事件
			$this.find(".pager_turnToPage").bind("keypress.pager", function(event) {
				//回车事件
				if(event.keyCode == "13") {
					_turnToPageClick(options, $this);
				}
			});
			//跳转-按钮点击
			$this.find(".pager_turnto").bind("click.pager", function() {_turnToPageClick(options, $this);});
			if (options.loadBlankLines) {
				$("#" + options.target).fillListData(null, {"rowCntPerPage":options.pageSize});
			}
			if (options.searchOnLoad) {
				//设置要跳转的页码
//				options.currentPage = 1;
//				_turnToPage(options, $this);
				hidBtnSearch.trigger("click.pager");
			}
		},
		/**
		 * 重新加载分页对应的Table的数据
		 */
		reloadTableData:function() {
			$(this).find("#__hidBtnSearch").trigger("click.pager");
		}
	});
	
/*******************jQuery方法扩展****************************/
	$.extend({
		basePath : function () {
			//获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
			var curWwwPath = window.document.location.href;
			//获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
			var pathName = window.document.location.pathname;
			var pos = curWwwPath.indexOf(pathName);
			//获取主机地址，如： http://localhost:8080
			var localhostPath = curWwwPath.substring(0, pos);
			//获取带"/"的项目名，如：/ems
			var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
			//获取项目的basePath   http://localhost:8080/ems/
			var basePath = localhostPath + projectName;
			return basePath;
		},
		/**
		 * ajax执行完成后，执行。仅一次有效
		 * @param fun    ajax执行完成后执行的后续操作
		 */
		ajaxStopOnce:function(fun) {
			$(document).one("ajaxStop.once", function() {
				fun();
			});
		},
		//HTML标签字符转换成转意符
		html2Escape : function (str) {
			return str.replace(/[<>&"]/g, function (c) { return { '<': '&lt;', '>': '&gt;', '&': '&amp;', '"': '&quot;'}[c]; });
		},
		//转意符换成HTML标签
		escape2Html : function (str) {
			var arrEntities = { 'lt': '<', 'gt': '>', 'nbsp': ' ', 'amp': '&', 'quot': '"' };
			return str.replace(/&(lt|gt|nbsp|amp|quot);/ig, function (all, t) { return arrEntities[t]; });
		}
	});
/*************************私有方法,文件内部使用*******************************/
	/**
	 * 分页，转到指定页码
	 */
	function _turnToPage(options, $this) {
		options.param = ___getRealData(options.param, "");
		var pagerParam = {};
		pagerParam["current"] = options.currentPage;
		pagerParam["rowCount"] = options.pageSize;
		pagerParam["propertyName"] = $.qiNiu.sortOpt.sortColumn;
		pagerParam["ascending"] = $.qiNiu.sortOpt.sortVal;
		var pagerParamStr = $.param(pagerParam);
		if (options.param != "") {
			pagerParamStr = options.param + "&" + pagerParamStr;
		}
		$.ajax({
			url: options.url,
			method:"post",
			dataType:"json",
			data:pagerParamStr,
			success:function(result, status) {
				if (result.success == true) {
					var pageData = JSON.parse(result.body.data);
					
					$this.find("._hid_pager_totalpage").val(pageData.totalPageCount + "");
					$this.find("._hid_pager_current").val(pageData.current + "");
					$this.find(".pager_current").text(pageData.current + " / " + pageData.totalPageCount);
					$this.find(".pager_totalrow").text(pageData.totalRowCount + "");
					
					//查询成功
					if (options.success != null) {
						options.success(pageData);
					} else if (options.target != "") {
						$("#" + options.target).fillListData(pageData, {"rowCntPerPage": options.pageSize});
					}
				} else {
					$this.find("._hid_pager_totalpage").val("1");
					$this.find("._hid_pager_current").val("1");
					$this.find(".pager_current").val("1 / 1");
					$this.find(".pager_totalrow").val("0");
					
					//查询失败
					if (options.error != null) {
						options.error(pageData);
					}
				}
			},
			error:function(result, status) {
				alert("网络异常，提交失败！");
			}
		});
	}
	
	function _turnToPageClick(options, $this) {
		//计算要跳转的页码
		var currentPage = $.trim($this.find(".pager_turnToPage").val());
		var totalPage = $this.find("._hid_pager_totalpage").val();
		if (!$.isNumeric(currentPage) || !$.isNumeric(totalPage)) {
			currentPage = 1;
			totalPage = 1;
		}
		if (parseInt(currentPage, 10) > parseInt(totalPage, 10)) {
			currentPage = totalPage;
		}
		
		if (parseInt(currentPage, 10) < 1) {
			currentPage = 1;
		}
		//设置要跳转的页码
		$this.find(".pager_turnToPage").val("");
		//页码没有变化，不查询
		if (currentPage  == $this.find("._hid_pager_current").val()) {
			return;
		}
		options.currentPage = currentPage;
		_turnToPage(options, $this);
	}
	
	function _containsValue(target, value) {
		while (target.indexOf(", ") != -1) {
			target = target.replace(/, /g, ",");
		}
		target = "," + target + ",";
		var result = (target.indexOf("," + value + ",") != -1);
		return result;
	}
	
	function ___getRealData(data, replace) {
		if (data === 0 || data === false) {
			return data;
		}
		return data || replace;
	}
	
	function _setRowData(tableRow, options, data, index, rowIndex) {
		opts = options.opts || [];
		//如果有checkbox，则先绑定checkbox的值
		if (tableRow.find("input:checkbox").length > 0) {
			var rowcheck = $(tableRow.find("input:checkbox")[0]);
			if (rowcheck.hasAttr("propkey")) {
				rowcheck.val(data[rowcheck.attr("propkey")] + "");
			} else if (rowcheck.hasAttr("dval")) {
				rowcheck.val(data[rowcheck.attr("dval")] + "");
			}
		}
		var cells = tableRow.find("td");
		//写列数据
		for (var colIndex = 0; colIndex < cells.length; colIndex += 1) {
			if ($(cells[colIndex]).hasAttr("propkey") || $(cells[colIndex]).hasAttr("dval")) {
				if ($(cells[colIndex]).hasAttr("propkey") && $(cells[colIndex]).attr("propkey") == "_autorownumber") {
					$(cells[colIndex]).text((rowIndex + 1) + "");
				}
				if ($(cells[colIndex]).hasAttr("propkey") && $(cells[colIndex]).attr("propkey") != "actopts") {
					var propkey = $(cells[colIndex]).attr("propkey");
					if (options[propkey] != undefined) {
						var opt = options[propkey];
						var _oldDisplayContent = opt.displayContent;
						opt.displayContent = ___getRealData(opt.displayContent || (data[propkey]) || (data[propkey.replace("___text", "")]),  "");
						var _linkId = "_myLinkId_" + (index++);
						_addLinkEvent(opt, _linkId, cells[colIndex], data, ___getRealData(data[propkey] || data[propkey.replace("___text", "")], ""));
						opt.displayContent = _oldDisplayContent;
					} else {
						if (data[propkey] != undefined) {
							if ($(cells[colIndex]).attr("enablehtml") == 'enabled') {
								$(cells[colIndex]).html(data[propkey] + "");
								$(cells[colIndex]).attr("title", (data[propkey] + "").replace(/<br\/>/g, '\r\n'));
							} else {
								$(cells[colIndex]).text(data[propkey] + "");
								$(cells[colIndex]).attr("title", data[propkey] + "");
							}
							if (data[options[propkey + "_propClass"]]) {
								$(cells[colIndex]).addClass(data[options[propkey + "_propClass"]]);
							}
						}else if (propkey.indexOf("___text") != -1 && data[propkey.replace("___text", "")] != undefined) {
							if ($(cells[colIndex]).attr("enablehtml") == 'enabled') {
								$(cells[colIndex]).html(data[propkey.replace("___text", "")]);
								$(cells[colIndex]).attr("title", data[propkey.replace("___text", "")].replace(/<br\/>/g, '\r\n'));
							} else {
								$(cells[colIndex]).text(data[propkey.replace("___text", "")]);
								$(cells[colIndex]).attr("title", data[propkey.replace("___text", "")]);
							}
							if (data[options[propkey + "_propClass"]]) {
								$(cells[colIndex]).addClass(data[options[propkey + "_propClass"]]);
							}
						} else if ($(cells[colIndex]).hasAttr("dval")) {
							if ($(cells[colIndex]).attr("enablehtml") == 'enabled') {
								$(cells[colIndex]).html($(cells[colIndex]).attr("dval"));
								$(cells[colIndex]).attr("title", $(cells[colIndex]).attr("dval").replace(/<br\/>/g, '\r\n'));
							} else {
								$(cells[colIndex]).text($(cells[colIndex]).attr("dval"));
								$(cells[colIndex]).attr("title", $(cells[colIndex]).attr("dval"));
							}
							if (data[options[propkey + "_propClass"]]) {
								$(cells[colIndex]).addClass(data[options[propkey + "_propClass"]]);
							}
						}
					}
				} else if ($(cells[colIndex]).hasAttr("propkey") && $(cells[colIndex]).attr("propkey") == "actopts") {
					$(cells[colIndex]).css("white-space", "nowrap").html("");
					for (var optIndex = 0; optIndex < opts.length; optIndex += 1) {
						var opt = opts[optIndex];
						var _linkId = "_myLinkId_" + (index++);
						_addLinkEvent(opt, _linkId, cells[colIndex], data);
					}
				} else if ($(cells[colIndex]).hasAttr("dval")) {
					if ($(cells[colIndex]).attr("enablehtml") == 'enabled') {
						$(cells[colIndex]).html($(cells[colIndex]).attr("dval"));
						$(cells[colIndex]).attr("title", $(cells[colIndex]).attr("dval").replace(/<br\/>/g, '\r\n'));
					} else {
						$(cells[colIndex]).text($(cells[colIndex]).attr("dval"));
						$(cells[colIndex]).attr("title", $(cells[colIndex]).attr("dval"));
					}
				}
			} else if ($(cells[colIndex]).find("input:text").length > 0) {
				var tdInput = $($(cells[colIndex]).find("input:text")[0]);
				if (tdInput.hasAttr("propkey")) {
					var propkey = tdInput.attr("propkey");
					if (data[propkey] != undefined) {
						tdInput.val(data[propkey] + "");
					} else if (tdInput.hasAttr("dval")) {
						tdInput.val(tdInput.attr("dval") + "");
					}
				} else if (tdInput.hasAttr("dval")) {
					tdInput.val(tdInput.attr("dval") + "");
				}
				if (data[options[propkey + "_propClass"]]) {
					$(cells[colIndex]).find("input:text").addClass(data[options[propkey + "_propClass"]]);
				}
			} else if ($(cells[colIndex]).find("span").hasAttr("propkey") || $(cells[colIndex]).find("span").hasAttr("dval")) {
				var propkey = $(cells[colIndex]).find("span").attr("propkey");
				if ($(cells[colIndex]).find("span").attr("enablehtml") == 'enabled') {
					$(cells[colIndex]).find("span").text(data[propkey] + "");
					$(cells[colIndex]).find("span").attr("title", (data[propkey] + "").replace(/<br\/>/g, '\r\n'));
				} else {
					$(cells[colIndex]).find("span").text(data[propkey] + "");
					$(cells[colIndex]).find("span").attr("title", data[propkey] + "");
				}
				if (data[options[propkey + "_propClass"]]) {
					$(cells[colIndex]).find("span").addClass(data[options[propkey + "_propClass"]]);
				}
			}
		}
		
		return index;
	}
	
	function _addLinkEvent(opt, _linkId, target, data, content) {
		opt.displayContent = ___getRealData(opt.displayContent, '');
		opt.name_EnableHtml = opt.name_EnableHtml || false;
		opt.className = opt.className || "";
		opt.optActions = opt.optActions || [];
		opt.showLinkWithoutEvent = opt.showLinkWithoutEvent || false;
		opt.condition = ___getRealData(opt.condition, true);
//		if (opt.condition == undefined) {
//			opt.condition = true;
//		}
		opt.paramFormData = ___getRealData(opt.paramFormData, true);
//		if (opt.paramFormData == undefined) {
//			opt.paramFormData = true;
//		}
		//添加操作元素
		var optHtml = "<div id='" + _linkId + "' class='" + opt.className + "' style='cursor:pointer'></div>";
		$(target).append(optHtml);
		//添加操作元素的名称
		if (opt.name_EnableHtml) {
			$(target).find("#" + _linkId).html(opt.displayContent);
		} else {
			$(target).find("#" + _linkId).html("<a href='###'></a>");
			$(target).find("#" + _linkId + " a").text(opt.displayContent + "");
		}
		//判断链接是否有事件及是否显示链接
		if (typeof(opt.condition) == "function" && opt.condition(data) || typeof(opt.condition) != "function" && opt.condition) {
			//添加操作元素的事件
			for (var actionIndex = 0; actionIndex < opt.optActions.length; actionIndex += 1) {
				var action = opt.optActions[actionIndex];
				action.actionName = action.actionName || "";
				action.actionFunc = action.actionFunc || "";
				action.actionParams = action.actionParams || [];
				if (action.actionName != "" && action.actionFunc != "") {
					$(target).find("#" + _linkId).data("actionIndex", actionIndex);
					$(target).find("#" + _linkId).bind(action.actionName + ".autobind", function() {
						var _itemActionIndex = $(this).data("actionIndex");
						_itemOpt = opt;
						var _itemAction = _itemOpt.optActions[_itemActionIndex];
						var _itemParam = {};
						for (var paramIndex = 0; paramIndex < _itemAction.actionParams.length; paramIndex += 1) {
							if (_itemOpt.paramFormData) {
								_itemParam[_itemAction.actionParams[paramIndex]] = ___getRealData(data[_itemAction.actionParams[paramIndex]], "");
							} else {
								_itemParam[_itemAction.actionParams[paramIndex]] = _itemAction.actionParams[paramIndex];
							}
						}
						if (action.actionParams.length == 0) {
							_itemParam = data;
						}
						_itemOpt.optActions[_itemActionIndex].actionFunc(_itemParam, this);
					});
				}
			}
		} else if (!opt.showLinkWithoutEvent) {
			$(target).find("#" + _linkId).hide();
//		} else if (opt.showLinkWithoutEvent && !opt.name_EnableHtml) {
		} else {
			$(target).find("#" + _linkId).css("cursor", "auto");
			$(target).find("#" + _linkId).html("");
			$(target).find("#" + _linkId).text(content);
		}
	}
	
	function _getDval($this) {
		var eleDval = "";
		//针对日期的默认值，特殊处理
		if ($this.hasAttr("dval")) {
			eleDval = $this.attr("dval");
			if (eleDval.indexOf("date:") == 0) {
				eleDval = $.trim(eleDval.replace("date:", ""));
				eleDval = eval("(" + eleDval + ")") || {};
				if (eleDval.date != undefined && $.isNumeric(eleDval.date) && eleDval.date != "") {
					var diff = parseInt(eleDval.date);
					var date = new Date();
					date = date.addDays(diff);
					eleDval = date.format(eleDval.format || "yyyy-MM-dd");
				} else if (eleDval.date != undefined) {
					eleDval = eleDval.date;
				}
			}
		}
		return eleDval;
	}
})(jQuery);

//下拉框值改变时 保存值
var selected = "";
//重置选择值（下拉框点X提醒时 原值丢失问题）
function reSetSelectValue(){
	$('#workflowSel').val(selected);
	$("#workflowSel_chosen").find('a').find("span").html('');
	$("#workflowSel_chosen").find('a').find("span").append($('#workflowSel').find("option[value='"+selected+"']").html());
	$("#workflowSel_chosen").find('a').attr("class","chosen-single chosen-single-with-deselect");
	$("#workflowSel_chosen").find('a').append("<abbr class='search-choice-close'></abbr>");
}
//清除选择值（下拉框点X提醒时确认时删除原值）
function setNullSelectValue(){
	selected = "";
	$('#workflowSel').val('');
	$("#workflowSel_chosen").find('a').find("span").html('');
	$("#workflowSel_chosen").find('a').find("span").append('请选择流程');
	$("#workflowSel_chosen").find('a').attr("class","chosen-single chosen-default");
}
//文件选择控件，检查后缀名
function checkFile(sender) {
	var validExts = sender.accept.split(",");
	for(var x in validExts){
		validExts[x] = validExts[x].replace(" ", "");
	}
    var fileExt = sender.value;
    fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
    if (validExts.indexOf(fileExt) < 0 && fileExt != "") {
        alert("只允许选取 " + validExts.toString() + "这些后缀名的文件。");
//        top.layer.alert("只允许选取 " + validExts.toString() + "这些后缀名的文件。", {
//  			icon: 1,
//			skin: 'layer-ext-moon'
//	 	});
//        $(sender).val("");
        return false;
    }
    
	var files = sender.files;
	var unit = 1048576; //1MB单位
	var maxUnit = 50; //单位数
	var maxSize = unit * maxUnit; //最大上传限制
    for(var i = 0; i < files.length; i++) {
        var file = files[i];
		if( file.size > maxSize ) {
			alert("上传的单个文件不能超过" + maxUnit + "MB。");
			return false;
		}
    }
    return true;
}

//不更新前台的情况下更新小版本号
function addVersion(obj){
	var ver = $('#'+obj).html();
	var strs = ver.split('.');
	var value = '';
	if(strs.length == 1){
		value = strs[0]+".1";
	}else{
		value = strs[0]+'.'+(parseInt(strs[1])+1);
	}
	$('#'+obj).html(value);
}

/**
 * 获取选中的数据集合：JsonArray
 * @return 数据集合：JsonArray
 */
function getCheckedDetailData() {
	var checkedData = [];
//	$(this).find("tr input:checked").each(function() {
		var jsonResult = {};
		$(this).closest("div").find('span').each(function () {   
			 //获取propkey
			 var propkey = $(this).attr("propkey") || "";
			 //获取value
			 var value = $(this).text() || "";
			 //保存值
			 jsonResult[propkey] = value;
		});
		if (!$.isEmptyObject(jsonResult)) {
			checkedData.push(jsonResult);
		}
//	});
	return checkedData;
}
	

