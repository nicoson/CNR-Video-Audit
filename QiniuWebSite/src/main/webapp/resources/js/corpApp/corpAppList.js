$(document).ready(function(){
});

/**
 * 显示修改数量画面
 * @param coFunctionId
 */
function showAddQuantity(coFunctionId){
	layer.open({
		  type: 2,
		  title: '调整数量',
		  shadeClose: true,
		  shade: 0.4,
		  area: ['800px', '550px'],
		  content: $.basepath + "/corpApp/showAddQuantity.do?coFunctionId="+coFunctionId,
	      end:function(index, layero){
	      }
		}); 
}

/**
 * 显示增加月数画面
 * @param coFunctionId
 */
function showAddMonths(coFunctionId){
	layer.open({
		  type: 2,
		  title: '续费',
		  shadeClose: true,
		  shade: 0.4,
		  area: ['800px', '550px'],
		  content: $.basepath + "/corpApp/showAddMonths.do?coFunctionId="+coFunctionId,
	      end:function(index, layero){
	      }
		}); 
}
