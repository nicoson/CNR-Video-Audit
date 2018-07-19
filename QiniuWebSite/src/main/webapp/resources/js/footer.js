/**
*方法说明：画面初始化
*返回值:
*    无
*参数：
*    无
*/
$(function(){

});

/**
 * 显示协议
 * @param protocolType 协议类型
 */
function showProtocolPdf(protocolType){
	//取得文件流
	var encodeUrl = encodeURIComponent($.basepath + "/index/showProtocolPdfByProtocolType.do?protocolType=" + protocolType);
	//使用pdfjs插件打开文件
	var urlSrc=$.basepath + "/resources/pdfjs/web/viewer.html?file="+encodeUrl+"&pdfTitle="+"协议文件阅读";
	parent.window.open(urlSrc,"协议");
}