/*
 * jQuery showDialog
 *提示框：top.showInfo('删除失败!');
 *确认框：top.showConfirm('确认删除吗?', function(){点击确认执行的内容});
 *异步加载内容：top.showWindow('弹窗标题','${base }/admin/adminPageAdd.action',900);
*/

function detectMacXFF() {
	var userAgent = navigator.userAgent.toLowerCase();
	if(userAgent.indexOf("mac") != -1 && userAgent.indexOf("firefox") != -1) {
		return true;
	}
}

function in_array(needle, haystack) {
	if(typeof needle == "string" || typeof needle == "number") {
		for(var i in haystack) {
			if(haystack[i] == needle) {
				return true;
			}
		}
	}
	return false;
}

function sd_load(sd_width) {
	if(sd_width) {
		$("#SD_window").css("width", sd_width + "px");
	}
	var sd_top = ($(window).height() - $("#SD_window").height()) / 2 + $(document).scrollTop();
	if(sd_top < 0) {
		sd_top = 0;
	}
	var sd_left = ($(window).width() - $("#SD_window").width()) / 2;
	if(sd_left < 0) {
		sd_left = 0;
	}
	$("#SD_window").css("top", sd_top);
	$("#SD_window").css("left", sd_left);
}

function sd_remove() {
	$("#SD_close,#SD_cancel,#SD_confirm").unbind("click");
	$("#SD_window,#SD_overlay,#SD_HideSelect").remove();
	if(typeof document.body.style.maxHeight == "undefined") {
		$("body","html").css({height: "auto", width: "auto"});
	}
}

function showDialog(mode, msg, t, sd_width) {
	var sd_width = sd_width ? sd_width : 400;
	var mode = in_array(mode, ['confirm', 'window', 'info', 'loading']) ? mode : 'alert';
	var t = t ? t : "提示信息";
	var msg = msg ? msg : "";
	var confirmtxt = confirmtxt ? confirmtxt : "确定";
	var canceltxt = canceltxt ? canceltxt : "取消";
	sd_remove();
	try {
		if(typeof document.body.style.maxHeight === "undefined") {
			$("body","html").css({height: "100%", width: "100%"});
			if(document.getElementById("SD_HideSelect") === null) {
				$("body").append("<iframe id='SD_HideSelect'></iframe><div id='SD_overlay'></div>");
			}
		} else {
			if(document.getElementById("SD_overlay") === null) {
				$("body").append("<div id='SD_overlay'></div>");
			}
		}
		if(mode == "alert") {
			if(detectMacXFF()) {
				$("#SD_overlay").addClass("SD_overlayMacFFBGHack");
			} else {
				$("#SD_overlay").addClass("SD_overlayBG");
			}
		} else {
			if(detectMacXFF()) {
				$("#SD_overlay").addClass("SD_overlayMacFFBGHack2");
			} else {
				$("#SD_overlay").addClass("SD_overlayBG2");
			}
		}
		$("body").append("<div id='SD_window'></div>");
		var SD_html;
		SD_html = "";
		SD_html += "<table cellspacing='0' cellpadding='0'><tbody><tr><td class='SD_bg'></td><td class='SD_bg'></td><td class='SD_bg'></td></tr>";
		SD_html += "<tr><td class='SD_bg'></td>";
		SD_html += "<td id='SD_container'>";
		SD_html += "<h3 id='SD_title' style='margin-top:-1px;text-align: left;'>" + t + "</h3>";
		SD_html += "<div id='SD_body'><div id='SD_content'>" + msg + "</div></div>";
		SD_html += "<div id='SD_button'><div class='SD_button'>";
		SD_html += "<a id='SD_confirm'>" + confirmtxt + "</a>";
		SD_html += "<a id='SD_cancel'>" + canceltxt + "</a>";
		SD_html += "</div></div>";
		SD_html += "<a href='javascript:;' id='SD_close' title='关闭'></a>";
		SD_html += "</td>";
		SD_html += "<td class='SD_bg'></td></tr>";
		SD_html += "<tr><td class='SD_bg'></td><td class='SD_bg'></td><td class='SD_bg'></td></tr></tbody></table>";
		$("#SD_window").append(SD_html);
		$("#SD_confirm,#SD_cancel,#SD_close").bind("click", function(){
			var leftpx = window.pageXOffset ? window.pageXOffset: document.documentElement.scrollLeft;
			var toppx = window.pageYOffset ? window.pageYOffset: document.documentElement.scrollTop;
			var ___forfocus = $("<input type='text' style='position:absolute; top:" + toppx + "px; left:" + leftpx + "px;' class='___forfocus'/>");
			$(top.document.body).append(___forfocus);
			___forfocus.focus();
			___forfocus.remove();
			sd_remove();
		});
		if(mode == "info" || mode == "alert") {
			$("#SD_cancel").hide();
			$("#SD_button").show();
		}
		if(mode == "window") {
			$("#SD_close").show();
		}
		if(mode == "confirm") {
			$("#SD_button").show();
		}
		var sd_move = false;
		var sd_x, sd_y;
		$("#SD_container > h3").click(function(){}).mousedown(function(e){
			sd_move = true;
			sd_x = e.pageX - parseInt($("#SD_window").css("left"));
			sd_y = e.pageY - parseInt($("#SD_window").css("top"));
		});
		$(document).mousemove(function(e){
			if(sd_move){
				var x = e.pageX - sd_x;
				var y = e.pageY - sd_y;
				$("#SD_window").css({left:x, top:y});
			}
		}).mouseup(function(){
			sd_move = false;
		});
		$("#SD_body").width(sd_width - 50);
		sd_load(sd_width);
		$("#SD_window").show();
		$("#SD_window").focus();
	} catch(e) {
		alert("System Error !");
	}
}
//消息显示
function showInfo(msg, fn, timeout) {
	showDialog("info", msg);
	$("#SD_confirm").unbind("click");
	if(fn && timeout) {
		st = setTimeout(function(){
			var leftpx = window.pageXOffset ? window.pageXOffset: document.documentElement.scrollLeft;
			var toppx = window.pageYOffset ? window.pageYOffset: document.documentElement.scrollTop;
			var ___forfocus = $("<input type='text' style='position:absolute; top:" + toppx + "px; left:" + leftpx + "px;' class='___forfocus'/>");
			$(top.document.body).append(___forfocus);
			___forfocus.focus();
			___forfocus.remove();
			sd_remove();
			fn();
		}, timeout * 1000);
	}
	$("#SD_confirm").bind("click", function(){
		if(timeout) {
			clearTimeout(st);
		}
		var leftpx = window.pageXOffset ? window.pageXOffset: document.documentElement.scrollLeft;
		var toppx = window.pageYOffset ? window.pageYOffset: document.documentElement.scrollTop;
		var ___forfocus = $("<input type='text' style='position:absolute; top:" + toppx + "px; left:" + leftpx + "px;' class='___forfocus'/>");
		$(top.document.body).append(___forfocus);
		___forfocus.focus();
		___forfocus.remove();
		sd_remove();
		if(fn) {
			fn();
		}
	});
}

var globalFn = null;
function sd_removeAndFN(data){
	var leftpx = window.pageXOffset ? window.pageXOffset: document.documentElement.scrollLeft;
	var toppx = window.pageYOffset ? window.pageYOffset: document.documentElement.scrollTop;
	var ___forfocus = $("<input type='text' style='position:absolute; top:" + toppx + "px; left:" + leftpx + "px;' class='___forfocus'/>");
	$(top.document.body).append(___forfocus);
	___forfocus.focus();
	___forfocus.remove();
	if(globalFn){
		globalFn.call('callback',data);
	}
	sd_remove();
}

function sd_removeAndFNNew(data1,data2){
	var leftpx = window.pageXOffset ? window.pageXOffset: document.documentElement.scrollLeft;
	var toppx = window.pageYOffset ? window.pageYOffset: document.documentElement.scrollTop;
	var ___forfocus = $("<input type='text' style='position:absolute; top:" + toppx + "px; left:" + leftpx + "px;' class='___forfocus'/>");
	$(top.document.body).append(___forfocus);
	___forfocus.focus();
	___forfocus.remove();
	if(globalFn){
		globalFn.call('callback',data1,data2);
	}
	sd_remove();
}


//打开iframe加载页面
function showIframeWindowWithData(title, the_url, sd_width, sd_height, data, fn)
{
	globalFn = fn;
	var sd_width = sd_width ? sd_width : 400;
	sd_remove();
	try {
		if(typeof document.body.style.maxHeight === "undefined") {
			$("body","html").css({height: "100%", width: "100%"});
			if(document.getElementById("SD_HideSelect") === null) {
				$("body").append("<iframe id='SD_HideSelect'></iframe><div id='SD_overlay'></div>");
			}
		} else {
			if(document.getElementById("SD_overlay") === null) {
				$("body").append("<div id='SD_overlay'></div>");
			}
		}
		if(detectMacXFF()) {
			$("#SD_overlay").addClass("SD_overlayMacFFBGHack2");
		} else {
			$("#SD_overlay").addClass("SD_overlayBG2");
		}
		$("body").append("<div id='SD_window'></div>");
		var SD_html;
		SD_html = "";
		SD_html += "<table cellspacing='0' cellpadding='0'><tbody><tr><td class='SD_bg'></td><td class='SD_bg'></td><td class='SD_bg'></td></tr>";
		SD_html += "<tr><td class='SD_bg'></td>";
		SD_html += "<td id='SD_container'>";
		SD_html += "<h3 id='SD_title' style='margin-top:-1px;text-align:left'>" + title + "</h3>";
		SD_html += "<div id='SD_body'><div id='SD_content'><iframe predata1=\"" + data + "\" predata='" + data + "' style='border:0px;width:100%;height:"+sd_height+"px' src='" + the_url + "' id='_framewithdata'></iframe></div></div>";
		SD_html += "<a href='javascript:;' id='SD_close' title='关闭'></a>";
		SD_html += "</td>";
		SD_html += "<td class='SD_bg'></td></tr>";
		SD_html += "<tr><td class='SD_bg'></td><td class='SD_bg'></td><td class='SD_bg'></td></tr></tbody></table>";
		$("#SD_window").append(SD_html);
		$("#SD_close").bind("click", function(a){
			var leftpx = window.pageXOffset ? window.pageXOffset: document.documentElement.scrollLeft;
			var toppx = window.pageYOffset ? window.pageYOffset: document.documentElement.scrollTop;
			var ___forfocus = $("<input type='text' style='position:absolute; top:" + toppx + "px; left:" + leftpx + "px;' class='___forfocus'/>");
			$(top.document.body).append(___forfocus);
			___forfocus.focus();
			___forfocus.remove();
			sd_remove();
		});
		$("#SD_close").show();

		var sd_move = false;
		var sd_x, sd_y;
		$("#SD_container > h3").click(function(){}).mousedown(function(e){
			sd_move = true;
			sd_x = e.pageX - parseInt($("#SD_window").css("left"));
			sd_y = e.pageY - parseInt($("#SD_window").css("top"));
		});
		$(document).mousemove(function(e){
			if(sd_move){
				var x = e.pageX - sd_x;
				var y = e.pageY - sd_y;
				$("#SD_window").css({left:x, top:y});
			}
		}).mouseup(function(){
			sd_move = false;
		});
		$("#SD_body").width(sd_width - 50);
		sd_load(sd_width);
		$("#SD_window").show();
		$("#SD_window").focus();
	} catch(e) {
		alert("System Error !");
	}
}

//top.showIframeWindow('新增管理员','${base }/admin/adminPageAdd.action',900,350);
function showIframeWindow(title, the_url, sd_width, sd_height,fn)
{
	globalFn = fn;
	var sd_width = sd_width ? sd_width : 400;
	sd_remove();
	try {
		if(typeof document.body.style.maxHeight === "undefined") {
			$("body","html").css({height: "100%", width: "100%"});
			if(document.getElementById("SD_HideSelect") === null) {
				$("body").append("<iframe id='SD_HideSelect'></iframe><div id='SD_overlay'></div>");
			}
		} else {
			if(document.getElementById("SD_overlay") === null) {
				$("body").append("<div id='SD_overlay'></div>");
			}
		}
		if(detectMacXFF()) {
			$("#SD_overlay").addClass("SD_overlayMacFFBGHack2");
		} else {
			$("#SD_overlay").addClass("SD_overlayBG2");
		}
		$("body").append("<div id='SD_window'></div>");
		var SD_html;
		SD_html = "";
		SD_html += "<table cellspacing='0' cellpadding='0'><tbody><tr><td class='SD_bg'></td><td class='SD_bg'></td><td class='SD_bg'></td></tr>";
		SD_html += "<tr><td class='SD_bg'></td>";
		SD_html += "<td id='SD_container'>";
		SD_html += "<h3 id='SD_title' style='margin-top:-1px;text-align:left'>" + title + "</h3>";
		SD_html += "<div id='SD_body'><div id='SD_content'><iframe style='border:0px;width:100%;height:"+sd_height+"px' src='" + the_url + "'></iframe></div></div>";
		SD_html += "<a href='javascript:;' id='SD_close' title='关闭'></a>";
		SD_html += "</td>";
		SD_html += "<td class='SD_bg'></td></tr>";
		SD_html += "<tr><td class='SD_bg'></td><td class='SD_bg'></td><td class='SD_bg'></td></tr></tbody></table>";
		$("#SD_window").append(SD_html);
		$("#SD_close").bind("click", function(a){
			var leftpx = window.pageXOffset ? window.pageXOffset: document.documentElement.scrollLeft;
			var toppx = window.pageYOffset ? window.pageYOffset: document.documentElement.scrollTop;
			var ___forfocus = $("<input type='text' style='position:absolute; top:" + toppx + "px; left:" + leftpx + "px;' class='___forfocus'/>");
			$(top.document.body).append(___forfocus);
			___forfocus.focus();
			___forfocus.remove();
			sd_remove();
		});
		$("#SD_close").show();

		var sd_move = false;
		var sd_x, sd_y;
		$("#SD_container > h3").click(function(){}).mousedown(function(e){
			sd_move = true;
			sd_x = e.pageX - parseInt($("#SD_window").css("left"));
			sd_y = e.pageY - parseInt($("#SD_window").css("top"));
		});
		$(document).mousemove(function(e){
			if(sd_move){
				var x = e.pageX - sd_x;
				var y = e.pageY - sd_y;
				$("#SD_window").css({left:x, top:y});
			}
		}).mouseup(function(){
			sd_move = false;
		});
		$("#SD_body").width(sd_width - 50);
		sd_load(sd_width);
		$("#SD_window").show();
		$("#SD_window").focus();
	} catch(e) {
		alert("System Error !");
	}
}

//打开iframe加载页面(关闭时可触发事件)
//top.showIframeWindow('新增管理员','${base }/admin/adminPageAdd.action',900,350);
function showIframeForClose(title, the_url, sd_width, sd_height,fn, closeFn)
{
	globalFn = fn;
	var sd_width = sd_width ? sd_width : 400;
	sd_remove();
	try {
		if(typeof document.body.style.maxHeight === "undefined") {
			$("body","html").css({height: "100%", width: "100%"});
			if(document.getElementById("SD_HideSelect") === null) {
				$("body").append("<iframe id='SD_HideSelect'></iframe><div id='SD_overlay'></div>");
			}
		} else {
			if(document.getElementById("SD_overlay") === null) {
				$("body").append("<div id='SD_overlay'></div>");
			}
		}
		if(detectMacXFF()) {
			$("#SD_overlay").addClass("SD_overlayMacFFBGHack2");
		} else {
			$("#SD_overlay").addClass("SD_overlayBG2");
		}
		$("body").append("<div id='SD_window'></div>");
		var SD_html;
		SD_html = "";
		SD_html += "<table cellspacing='0' cellpadding='0'><tbody><tr><td class='SD_bg'></td><td class='SD_bg'></td><td class='SD_bg'></td></tr>";
		SD_html += "<tr><td class='SD_bg'></td>";
		SD_html += "<td id='SD_container'>";
		SD_html += "<h3 id='SD_title' style='margin-top:-1px;text-align: left;'>" + title + "</h3>";
		SD_html += "<div id='SD_body'><div id='SD_content'><iframe style='border:0px;width:100%;height:"+sd_height+"px' src='" + the_url + "'></iframe></div></div>";
		SD_html += "<a href='javascript:;' id='SD_close' title='关闭'></a>";
		SD_html += "</td>";
		SD_html += "<td class='SD_bg'></td></tr>";
		SD_html += "<tr><td class='SD_bg'></td><td class='SD_bg'></td><td class='SD_bg'></td></tr></tbody></table>";
		$("#SD_window").append(SD_html);
		$("#SD_close").bind("click", function(a){
			var leftpx = window.pageXOffset ? window.pageXOffset: document.documentElement.scrollLeft;
			var toppx = window.pageYOffset ? window.pageYOffset: document.documentElement.scrollTop;
			var ___forfocus = $("<input type='text' style='position:absolute; top:" + toppx + "px; left:" + leftpx + "px;' class='___forfocus'/>");
			$(top.document.body).append(___forfocus);
			___forfocus.focus();
			___forfocus.remove();
			globalFn.call('callback','closeWin');
			sd_remove();
		});
		$("#SD_close").show();

		var sd_move = false;
		var sd_x, sd_y;
		$("#SD_container > h3").click(function(){}).mousedown(function(e){
			sd_move = true;
			sd_x = e.pageX - parseInt($("#SD_window").css("left"));
			sd_y = e.pageY - parseInt($("#SD_window").css("top"));
		});
		$(document).mousemove(function(e){
			if(sd_move){
				var x = e.pageX - sd_x;
				var y = e.pageY - sd_y;
				$("#SD_window").css({left:x, top:y});
			}
		}).mouseup(function(){
			sd_move = false;
		});
		$("#SD_body").width(sd_width - 50);
		sd_load(sd_width);
		$("#SD_window").show();
		$("#SD_window").focus();
	} catch(e) {
		alert("System Error !");
	}
}

//异步加载一个URL,将内容填到内容
function showWindow(title, the_url, sd_width) {
	var sd_width = sd_width ? sd_width : 400;
	$.ajax({
		type		: "GET",
		dataType	: "html",
		cache		: false,
		timeout		: 10000,
		url			: the_url,
		data		: "isajax=1",
		success		: function(data){
			showDialog("window", data, title, sd_width);
		},
		error		: function(data){
			showDialog("alert", "读取数据失败");
		},
		beforeSend	: function(data){
			showDialog("loading", "正在读取数据...");
		}
	});
}

function showWindow(title, the_url, sd_width,datas) {
	var sd_width = sd_width ? sd_width : 400;
	$.ajax({
		type		: "POST",
		dataType	: "html",
		cache		: false,
		async: false,  
		timeout		: 10000,
		url			: the_url,
		data		: datas,
		success		: function(data){
			showDialog("window", data, title, sd_width);
		},
		error		: function(data){
			showDialog("alert", "读取数据失败");
		},
		beforeSend	: function(data){
			showDialog("loading", "正在读取数据...");
		}
	});
}


function showWindow(title, the_url, sd_width,datas,fn) {
	var sd_width = sd_width ? sd_width : 400;
	$.ajax({
		type		: "POST",
		dataType	: "html",
		cache		: false,
		async: false,  
		timeout		: 10000,
		url			: the_url,
		data		: datas,
		success		: function(data){
			showDialog("window", data, title, sd_width,fn);
		},
		error		: function(data){
			showDialog("alert", "读取数据失败");
		},
		beforeSend	: function(data){
			showDialog("loading", "正在读取数据...");
		}
	});
}

//确认框
function showConfirm(msg, fn) {
	showDialog("confirm", msg);
	$("#SD_confirm").unbind("click");
	$("#SD_confirm").bind("click", function(){
		if(fn) {
			var leftpx = window.pageXOffset ? window.pageXOffset: document.documentElement.scrollLeft;
			var toppx = window.pageYOffset ? window.pageYOffset: document.documentElement.scrollTop;
			var ___forfocus = $("<input type='text' style='position:absolute; top:" + toppx + "px; left:" + leftpx + "px;' class='___forfocus'/>");
			$(top.document.body).append(___forfocus);
			___forfocus.focus();
			___forfocus.remove();
			fn();
			sd_remove();
		}
	});
}


function showDialog(mode, msg, t, sd_width,fn) {
	globalFn = fn;
	var sd_width = sd_width ? sd_width : 400;
	var mode = in_array(mode, ['confirm', 'window', 'info', 'loading', 'inputwindow']) ? mode : 'alert';
	var t = t ? t : "提示信息";
	msg = msg ? msg : "";
	var confirmtxt = confirmtxt ? confirmtxt : "确定";
	var canceltxt = canceltxt ? canceltxt : "取消";
	sd_remove();
	try {
		if(typeof document.body.style.maxHeight === "undefined") {
			$("body","html").css({height: "100%", width: "100%"});
			if(document.getElementById("SD_HideSelect") === null) {
				$("body").append("<iframe id='SD_HideSelect'></iframe><div id='SD_overlay'></div>");
			}
		} else {
			if(document.getElementById("SD_overlay") === null) {
				$("body").append("<div id='SD_overlay'></div>");
			}
		}
		if(mode == "alert") {
			if(detectMacXFF()) {
				$("#SD_overlay").addClass("SD_overlayMacFFBGHack");
			} else {
				$("#SD_overlay").addClass("SD_overlayBG");
			}
		} else {
			if(detectMacXFF()) {
				$("#SD_overlay").addClass("SD_overlayMacFFBGHack2");
			} else {
				$("#SD_overlay").addClass("SD_overlayBG2");
			}
		}
		$("body").append("<div id='SD_window'></div>");
		var SD_html;
		SD_html = "";
		SD_html += "<table cellspacing='0' cellpadding='0'><tbody><tr><td class='SD_bg'></td><td class='SD_bg'></td><td class='SD_bg'></td></tr>";
		SD_html += "<tr><td class='SD_bg'></td>";
		SD_html += "<td id='SD_container'>";
		SD_html += "<h3 id='SD_title' style='margin-top:-1px;text-align: left;'>" + t + "</h3>";
		SD_html += "<div id='SD_body'><div id='SD_content'>" + msg + "</div></div>";
		SD_html += "<div id='SD_button'><div class='SD_button'>";
		SD_html += "<a id='SD_confirm' opt='OK'>" + confirmtxt + "</a>";
		SD_html += "<a id='SD_cancel'>" + canceltxt + "</a>";
		SD_html += "</div></div>";
		SD_html += "<a href='javascript:;' id='SD_close' title='关闭'></a>";
		SD_html += "</td>";
		SD_html += "<td class='SD_bg'></td></tr>";
		SD_html += "<tr><td class='SD_bg'></td><td class='SD_bg'></td><td class='SD_bg'></td></tr></tbody></table>";
		$("#SD_window").append(SD_html);
		$("#SD_confirm,#SD_cancel,#SD_close").bind("click", function(){
			var leftpx = window.pageXOffset ? window.pageXOffset: document.documentElement.scrollLeft;
			var toppx = window.pageYOffset ? window.pageYOffset: document.documentElement.scrollTop;
			var ___forfocus = $("<input type='text' style='position:absolute; top:" + toppx + "px; left:" + leftpx + "px;' class='___forfocus'/>");
			$(top.document.body).append(___forfocus);
			___forfocus.focus();
			___forfocus.remove();
			var opt = $(this).attr("opt") || 'closeWin';
			var param = $(this).closest("table").find(":text:first").val() || $(this).closest("table").find("textarea:first").val() || "";
			globalFn.call('callback', opt, param);
			sd_remove();
		});
		if(mode == "info" || mode == "alert") {
			$("#SD_cancel").hide();
			$("#SD_button").show();
		}
		if(mode == "window") {
			$("#SD_close").show();
		}
		if(mode == "inputwindow") {
			$("#SD_close").show();
			$("#SD_button").show();
		}
		if(mode == "confirm") {
			$("#SD_button").show();
		}
		var sd_move = false;
		var sd_x, sd_y;
		$("#SD_container > h3").click(function(){}).mousedown(function(e){
			sd_move = true;
			sd_x = e.pageX - parseInt($("#SD_window").css("left"));
			sd_y = e.pageY - parseInt($("#SD_window").css("top"));
		});
		$(document).mousemove(function(e){
			if(sd_move){
				var x = e.pageX - sd_x;
				var y = e.pageY - sd_y;
				$("#SD_window").css({left:x, top:y});
			}
		}).mouseup(function(){
			sd_move = false;
		});
		$("#SD_body").width(sd_width - 50);
		sd_load(sd_width);
		$("#SD_window").show();
		$("#SD_window").focus();
	} catch(e) {
		alert("System Error !");
	}
}