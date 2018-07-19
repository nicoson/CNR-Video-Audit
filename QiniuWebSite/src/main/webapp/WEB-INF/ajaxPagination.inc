<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
	.am-pagination {
  padding-left: 0;
  margin: 0.5rem 0;
  list-style: none;
  color: #999999;
  text-align: left;
}
.am-pagination:before,
.am-pagination:after {
  content: " ";
  display: table;
}
.am-pagination:after {
  clear: both;
}
.am-pagination > li {
  display: inline-block;
}
.am-pagination > li > a,
.am-pagination > li > span {
  position: relative;
  display: block;
  padding: 0.5em 1em;
  text-decoration: none;
  line-height: 1.2;
  background-color: #ffffff;
  border: 1px solid #dddddd;
  border-radius: 0;
  margin-bottom: 5px;
  margin-right: 5px;
}
.am-pagination > li:last-child > a,
.am-pagination > li:last-child > span {
  margin-right: 0;
}
.am-pagination > li > a:hover,
.am-pagination > li > span:hover,
.am-pagination > li > a:focus,
.am-pagination > li > span:focus {
  background-color: #eeeeee;
}
.am-pagination > .am-active > a,
.am-pagination > .am-active > span,
.am-pagination > .am-active > a:hover,
.am-pagination > .am-active > span:hover,
.am-pagination > .am-active > a:focus,
.am-pagination > .am-active > span:focus {
  z-index: 2;
  color: #ffffff;
  background-color: #0e90d2;
  border-color: #0e90d2;
  cursor: default;
}
.am-pagination > .am-disabled > span,
.am-pagination > .am-disabled > span:hover,
.am-pagination > .am-disabled > span:focus,
.am-pagination > .am-disabled > a,
.am-pagination > .am-disabled > a:hover,
.am-pagination > .am-disabled > a:focus {
  color: #999999;
  background-color: #ffffff;
  border-color: #dddddd;
  cursor: not-allowed;
  pointer-events: none;
}
.am-pagination .am-pagination-prev {
  float: left;
}
.am-pagination .am-pagination-prev a {
  border-radius: 0;
}
.am-pagination .am-pagination-next {
  float: right;
}
.am-pagination .am-pagination-next a {
  border-radius: 0;
}
.am-pagination-centered {
  text-align: center;
}
.am-pagination-right {
  text-align: right;
}
/*lqf添加，去除按钮的下划线*/
li{
border:none;
}
.am-pagination > li > a,.am-pagination > li > span{
    border-radius: 6px;
}
</style>
<div class="am-fr pager_div" style="padding: 0px;margin: 0px;float:right;margin-right:13px;">
	<ul class="am-pagination" style="margin-right: 18px;">
		<li>查询到<font color="#f00;" class="pager_totalrow">0</font>条记录　</li>
		<li><a href="javascript:void(0);" class="pager_first">首页</a></li>
		<!-- 显示上一页 -->
		<li><a href="javascript:void(0);" class="pager_pre">上一页</a></li>
		<!--当前页 -->
		<li class="am-disabled"><span class="pager_current"></span></li>
		<!-- 显示下一页 -->
		<li><a href="javascript:void(0);" class="pager_next">下一页</a></li>
		<!-- 最后一页 -->
		<li><a href="javascript:void(0);" class="pager_last">尾页</a></li>
	</ul>
</div>
