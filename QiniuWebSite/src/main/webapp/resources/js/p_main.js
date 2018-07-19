/*
 * p_main.js
 * version 1.0
 * Copyright (c) 2009 Developped By OPP
 */
var publicObject = new Array();

publicObject["currentMenuId"] = "default";

var opp = new Object();

opp.registerObject = function(objId, obj) {
	publicObject[objId] = obj;
	return true;
};

opp.removeObject = function(objId) {
	if (publicObject[objId] == null) {
		return false;
	}
	publicObject[objId] = null;
};

function regObject(objId, obj) {
	// 当窗体加载时，对公共对象进行注册��
	if (typeof window.onload !== "function") {
		window.onload = function() {
			top.opp.registerObject(objId, obj);
		}
	} else {
		window.attachEvent("onload", opp.registerObject(objId, obj));
	}
	;

	// 当窗体关闭时，对公共对象进行释放�ʱ���Թ�����������ͷ�
	if (typeof window.onunload !== "function") {
		window.onunload = function() {
			top.opp.removeObject(objId);
		}
	} else {
		window.attachEvent("onunload", opp.removeObject(objId));
	}
};

function toggle(imgDiv, o) {
	$("#" + imgDiv).toggle(function() {
		$(this).attr('src', imgPath_session + '/title_arrow_top.gif');
		$("#" + o).slideUp('slow');
	}, function() {
		$(this).attr('src', imgPath_session + '/title_arrow_down.gif');
		$("#" + o).slideDown('show');
	});
};

/* 自适应高度 */
function adapt() {
	if (window.frameElement != null) {
		// alert(window.frameElement.height);
		window.frameElement.height = document.body.scrollHeight + 25;
	}
};

/* 获取中间高度 */
function getMiddleHeight() {
	return $(top.document).height() - 130;// 顶部50px；底部30px;TAP页24px
};

/* 获取中间宽度 */
function getMiddleWidth() {
	return $(top.document.body).find("#rightFrame_DIV").width();
};

/**
 * 判断是否为空
 * 
 * @param str
 * @returns {Boolean}
 */
function isEmpty(str) {
	if (str != "" && str != null && str.length != 0) {
		return false;
	} else {
		return true;
	}
};

//限制文本框只能输入整数和浮点数
function CheckInputIntFloat(oInput) {
	
	if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/,'')) {
		oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\.{0,1}\d{0,}/); 
	} 
}
//限制文本框只能输入整数
function CheckInputInt(oInput) {
	
	if('' != oInput.value.replace(/^[1-9]\d*$/,'')) {
		oInput.value = oInput.value.match(/^[1-9]\d*$/) == null ? '' :oInput.value.match(/^[1-9]\d*$/); 
	} 
}
/**
 * jquery扩展
 */
$.extend($,{
	OK : "0",
	ERROR : "-1"
});