/*
 * p_tab.js 
 * depend on:$ 1.2.6
 * version 1.0
 * Copyright (c) 2009 Developped By OPP
 */
 
//查看对象或方法
function opp(obj)
{
	var msg="";
	for (i in obj){
	  if(typeof obj[i] == "object")
	  {
	 	 msg += i+"=" + obj[i].outerHTML + "\n";
	  }else
	  {
	   msg += i+"=" + obj[i] + "\n";
	  }
	}
	alert(msg);
}

var floatContextMenuTip;
function openContextMenu()
{
	var oEvent = window.event;
	var obj  = oEvent.srcElement;
	floatContextMenuTip = window.createPopup();
	var d = floatContextMenuTip.document;
	var oPopBody = d.body;
	var ht ="<table id='myRecentOpenTable' cellpadding='0'style='border:1px solid #2c5c7d;width: 100%;height: 100%;text-align: center;overflow: hidden;'>";
	ht +="<tr><td><a class='contextM' href='javascript:void(0)' onclick=\"parent.deleteCurrentTab('"+obj.id+"');\">关闭</a></td></tr>";
	ht +="<tr><td style='border-top:1px dashed #2c5c7d;'><a class='contextM'  href='javascript:void(0)' onclick=\"parent.deleteOtherTab('"+obj.id+"');\">关闭其它</a></td></tr>";
	ht +="<tr><td style='border-top:1px dashed #2c5c7d;'><a class='contextM'  href='javascript:void(0)' onclick=\"parent.deleteAllTab();\">关闭所有</a></td></tr>";
	ht +="</table>";
	oPopBody.innerHTML = ht;
	var oStyleSheet = d.createStyleSheet();
	oStyleSheet.addImport(cssPath_session+'/p_tab.css');
	floatContextMenuTip.show(oEvent.clientX,oEvent.clientY+8, 120, 80, document.body);
	return false;
}

/*关闭其它TAB*/
function deleteOtherTab(id)
{	
	floatContextMenuTip.hide();
	var tabDesc = id.slice(0,id.lastIndexOf('_'));
	$("#container div ul").children().not('#'+tabDesc+'_l').remove();
	$("#maintab").children().not('#'+tabDesc).remove(); 
	$("#container div ul li a").css({color: "#999", "background-image": "url("+imgPath_session+"/tab_topover.gif)"});
	$("#"+tabDesc+"_a").css({color: "#000", "background-image":"url("+imgPath_session+"/tab_top.gif)"});	
	$("#maintab div").hide();
	$("#"+tabDesc).fadeIn(400);	
}

/*关闭当前TAB*/
function deleteCurrentTab(id)
{	
	var tabDesc = id.slice(0,id.lastIndexOf('_'));
	floatContextMenuTip.hide();
	document.getElementById(tabDesc+"_s").click();
}

/*删除所有TAB页*/
function deleteAllTab()
{	
		floatContextMenuTip.hide();
		$("#container div ul").children().remove();
		$("#maintab").children().remove();
		//top.openMyIndex();//回首页
		$("#maintabIframe").show();
}
var globalCurrentTab='';
function TabZone(id)
{
	this.mainDiv = $("#"+id);
}

/*打开新的tab页*/
/*id唯一,tabName标题名称,tabsrc路径,isRefresh是否涮新*/
TabZone.prototype.addTab = function (id, tabName, tabsrc, isRefresh) {
    if (isRefresh == undefined) isRefresh = 'N';
    $("#maintabIframe").hide();
    //如果已打开，聚焦
    if (this.isOpen(id)) {

        if (isRefresh == 'N') {
            this.focusTab(id);
            return;
        } else {
            //刷新打开tab页
            $("#container div ul li a").css({ color: "#999", "background-image": "url(" + imgPath_session + "/tab_topover.gif)" });
            $("#maintab div").hide();
            $("#" + id + "").fadeIn(400);
            $("#" + id + "_a").css({ color: "#000", "background-image": "url(" + imgPath_session + "/tab_top.gif)" });
            $("#" + id + " iframe").attr("src", tabsrc);
            globalCurrentTab = id;
            return;
        }
    }

    if ($("#container div ul").children().length >= parseInt(getMiddleWidth() / 100)) {
        //top.showInfo('您打开的TAB页面过多,将会影响系统的稳定性,系统已经自动关闭了第一个TAB页.');
        $("#container div ul li:first-child").remove(); $("#maintab div:first").remove();
    }

    //创新新的tab页
    this.hiddenTab(id);
    $("#container div ul:last-child").append("<li id='" + id + "_l'><a id='" + id +
	"_a' href='javascript:void(0);'><label id='" + id + "_n' style='width:78px;float:left;' class='ellipsis' title='" + tabName + "'>" + tabName + "</label><label id='" + id +
	"_s' style='width:11px;height:11px;float:right;margin:4px 2px 0px 0px;'><img id='" + id + "_img' src='" + imgPath_session + "/tab_close.png'></label></a></li>");
    $("#maintab:last").append("<div id='" + id + "'><iframe frameBorder='0' style='width:100%;height:" + getMiddleHeight() + "px' src='" + tabsrc + "'></iframe></div>");
    //绑定焦点事件
    $("#" + id + "_a").bind('click', function (event) {
        $("#maintab div").hide();      
        if (isRefresh == 'R')/*R 点击tab页，会重新加载*/
        {
            $("#container div ul li a").css({ color: "#999", "background-image": "url(" + imgPath_session + "/tab_topover.gif)" });
            $("#maintab div").hide();
            $("#" + id + "").fadeIn(400);
            $("#" + id + "_a").css({ color: "#000", "background-image": "url(" + imgPath_session + "/tab_top.gif)" });
            $("#" + id + " iframe").attr("src", tabsrc);
            globalCurrentTab = id;
            return;

        } else if (globalCurrentTab == id) {
            $("#" + id).show();
        } else {
            $("#" + id).fadeIn(400);
        }

        //将所有tab的样式变蓝
        $("#container div ul li a").css({ color: "#999", "background-image": "url(" + imgPath_session + "/tab_topover.gif)" });
        $("#" + id + "_a").css({ color: "#000", "background-image": "url(" + imgPath_session + "/tab_top.gif)" });
        globalCurrentTab = id;
    });
    //绑定删除事件
    $("#" + id + "_s").bind('click', function (event) {
        $("#" + id).remove();
        $("#" + id + "_l").remove();

        //重置所有tab页
        $("#container div ul li a").css({ color: "#999", "background-image": "url(" + imgPath_session + "/tab_topover.gif)" });
        $("#maintab div").hide();

        //焦点定位上一个tab页
        $("#maintab div:last").fadeIn(400);
        $("#container div ul li a:last").css({ color: "#000", "background-image": "url(" + imgPath_session + "/tab_top.gif)" });
        //无TAB,回我的首页
        if ($("#container div ul li a:last").text() == '') {
            //top.openMyIndex();
            $("#maintabIframe").show();
        }
    });

    //绑定关闭按钮TOGGGLE效果
    $("#" + id + "_img").hover(
	  function () {
	      $(this).attr('src', imgPath_session + '/tab_close_on.png');
	  },
	  function () {
	      $(this).attr('src', imgPath_session + '/tab_close.png');
	  });

    globalCurrentTab = id;
    document.getElementById(id + '_l').oncontextmenu = openContextMenu;
    //alert($("#container").html());
}

/*删除tab页，焦点定位上个tab页*/
TabZone.prototype.deleteTab = function(id)
{
	if(this.isOpen(id))
	{
		$("#"+id).remove();$("#"+id +"_l").remove();
		$("#container div ul li a").css({color: "#999", "background-image": "url("+imgPath_session+"/tab_topover.gif)"}); 
		$("#maintab div").hide();
		$("#maintab div:last").fadeIn(400);
		$("#container div ul li a:last").css({color: "#000", "background-image": "url("+imgPath_session+"/tab_top.gif)"});
	}
}
TabZone.prototype.deleteCurrentTabNoValue = function()
{
	this.deleteTab(globalCurrentTab);
}

/*删除oldId的tab页,并涮新id的tab页,并定位id为焦点*/
TabZone.prototype.delTabAndRefreshOtherTab = function(oldId,id)
{
	if(this.isOpen(oldId) && this.isOpen(id))
	{
		$("#"+oldId).remove();$("#"+oldId +"_l").remove();
		$("#container div ul li a").css({color: "#999", "background-image": "url("+imgPath_session+"/tab_topover.gif)"}); 
		$("#maintab div").hide();
		$("#"+id+"").fadeIn(400);
		$("#"+id+"_a").css({color: "#000", "background-image": "url("+imgPath_session+"/tab_top.gif)"});
		$("#"+id+" iframe").attr("src",$("#"+id+" iframe").attr("src"));
	}
}

//将隐藏所有tab内容
TabZone.prototype.hiddenTab = function(id)
{	
	//将所有tab的样式变蓝
	$("#container div ul li a").css({color: "#999", "background-image": "url("+imgPath_session+"/tab_topover.gif)"}); 
	$("#maintab div").hide();
}

//判断当前tab页是否打开
TabZone.prototype.isOpen = function(id)
{	
	return $("#"+id+"_a").is("a");
}

//将当前tab设置为焦点
TabZone.prototype.focusTab = function(id)
{
	//将所有tab的样式变蓝
	$("#container div ul li a").css({color: "#999", "background-image": "url("+imgPath_session+"/tab_topover.gif)"});
	$("#"+id+"_a").css({color: "#000", "background-image":"url("+imgPath_session+"/tab_top.gif)"});	
	$("#maintab div").hide();
	$("#"+id).fadeIn(400);
	globalCurrentTab = id;
}

TabZone.prototype.rename = function(id,name)
{
	if(this.isOpen(id))
	{
		$("#"+id+"_n").html(name);
	}
}