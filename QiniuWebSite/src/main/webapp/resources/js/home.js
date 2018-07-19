$.vccloud.home = {
	getUserMenu : function() {//取得菜单数据，这个菜单数据必须是已经排序好的
		$.post($.vccloud.basepath + "/index/initMenu.do", $.vccloud.home.initHomePage, "json");
	},
	initHomePage : function(menuList) {//初始化菜单
		$("#uesrmenu").empty();
		var picPath = $("#picPath").val();
		if (menuList && menuList.data && menuList.data.length > 0) {
			var rowModuleCnt = 0;
			var rowMenuCnt = {};
			var targetContainer = $("#alltemplate .contentContainerDiv").clone(true);
			targetContainer.appendTo("#page_root");
			for (var index in menuList.data) {
				var menuItem = menuList.data[index];
				//如果当前菜单项有父菜单，则给父菜单添加相应的样式；并判断是否有子菜单容器
				if (menuItem.parentResourceId != null && menuItem.parentResourceId != "") {
					if (!rowMenuCnt[menuItem.parentResourceId]) {
						rowMenuCnt[menuItem.parentResourceId] = 0;
					}
					if (rowMenuCnt[menuItem.parentResourceId] >= 2) {
						rowMenuCnt[menuItem.parentResourceId] = 0;
						var frontRow = $("#menuContainer[key='" + menuItem.parentResourceId + "']");
						frontRow.removeAttr("key");
						frontRow.removeAttr("id");
						var menuContainer = $("#alltemplate #menuContainer").clone(true);
						menuContainer.attr("key", menuItem.parentResourceId);
						menuContainer.appendTo(frontRow.parent());
					}
					rowMenuCnt[menuItem.parentResourceId]++;
					var menu = $("#alltemplate #menuItem").clone(true);
					menu.text(menuItem.resourceNm);
					menu.data("url", menuItem.resourceUrl);
					menu.click(function() {
						if (menuItem.resourceUrl && menuItem.resourceUrl.length > 1 && menuItem.resourceUrl.indexOf("/") == "0") {
							window.location.href = $.vccloud.basepath + "/" + $(this).data("url");
						}
					});
					$("#menuContainer[key='" + menuItem.parentResourceId + "']").append(menu);
				} else {
					if (rowModuleCnt >= 3) {
						rowModuleCnt = 0;
						targetContainer = $("#alltemplate .contentContainerDiv").clone(true);
						targetContainer.appendTo("#page_root");
					}
					var contentDiv = $("#alltemplate #contentDiv").clone(true);
					contentDiv.appendTo(targetContainer);
					contentDiv.find("#menuName").text(menuItem.resourceNm);
					contentDiv.find("#menuContainer").attr("key", menuItem.resourceId);
					var menuIcon = contentDiv.find("#menuBigIcon");
					menuIcon.prop("src",picPath +"/home" + index + ".png");
					rowModuleCnt++;
				}
			}
			$("#menuContainer").removeAttr("key");
			$("#menuContainer").removeAttr("id");
			var height = 0;
			$(".contentContainerDiv").each(function() {
				var rowHeight = $(this).height();
				if (rowHeight > height) {
					height = rowHeight;
				}
			});
			$(".contentContainerDiv").height(height);
		}
	}
};
$(function() {
	$.vccloud.home.getUserMenu();
});
