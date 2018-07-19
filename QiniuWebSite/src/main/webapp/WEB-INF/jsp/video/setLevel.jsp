<%@page import="cn.qiniu.util.common.Constant"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@include file="../../Header.inc"%>
<!DOCTYPE html>
<html>
<head>
<title>等级设定页面</title>
<link rel="stylesheet" type="text/css"
	href="${base}/resources/css/setLevel.css">
<script type="text/javascript" src="${jsPath}/setLevel.js"></script>

	  <script id="setLevel" type="text/x-jsrender">
				<div class="lineCon  levelLine">
					<div class="lineTxt1">
						<select class="op_contents" id="op_con"  onchange ="selectProLabel(this)">
							<option value="politician_1" class="selected" selected>政治人物-涉政</option>
							<option value="politician_0">政治人物-正常</option>
							<option value="terror_1">暴恐-暴恐</option>
							<option value="terror_0">暴恐-正常</option>
							<option value="pulp_0" >涉黄-黄色</option>
							<option value="pulp_1">涉黄-性感</option>
							<option value="pulp_2">涉黄-正常</option>
						</select>
					</div>
					<div class="lineDangerLevel">
						<select class="levelTxt" onchange ="selectDangerLevel(this)">
							<option value="0" class="selected" selected>无</option>
							<option value="1">低</option>
							<option value="2">中</option>
							<option value="3">高</option>
						</select>
						<div class="levelColor"></div>
						<div class="removeLevel">
							<!-- <div class="remove"> -->
							<img class="remove" src="${imgPath}/remove.png" onclick="deleteLineCon(this)"/>
							<!--  </div> -->
							<div class="del" onclick="deleteLineCon(this)">移除</div>
						</div>

					</div>
				</div>
	</script>
	

</head>

<body>
	<div class="levelContainer">
		<div class="levelTop">
			<div class="top_leftTxt">等级设定</div>
			<div class="top_rightCon">
				<div class="lineCon">
					<div class="lineTxt">机审结果分类</div>
					<div class="lineDangerTitleLevel">危机等级</div>
				</div>
				<div id="levelList"   class="levelListCon">
				
					<%-- <div class="lineCon">
						<div class="lineTxt1">
							<select class="op_contents">
								<!-- class="lineContent" -->
								<option value="{{:opsOpLabel}}">政治人物-涉政</option>
								<option value="2">政治人物-正常</option>
								<option value="1">暴恐-暴恐</option>
								<option value="2">暴恐-正常</option>
								<option value="1" selected>涉黄-黄色</option>
								<option value="2">涉黄-性感</option>
								<option value="3">涉黄-正常</option>
							</select>
						</div>
						<div class="lineDangerLevel">
							<select class="levelTxt">
								<option value="0" selected>高</option>
								<option value="1">中</option>
								<option value="2">低</option>
								<option value="3">无</option>
							</select>
							<div class="levelColor height"></div>
							<div class="removeLevel">
								<!-- <div class="remove"> -->
								<img class="remove" src="${imgPath}/remove.png" />
								<!--  </div> -->
								<div class="del">移除</div>
							</div>
	
						</div>
					</div>
					<div class="lineCon">
						<div class="lineTxt1">
							<select class="op_contents">
								<!-- class="lineContent" -->
								<option value="1">政治人物-涉政</option>
								<option value="2">政治人物-正常</option>
								<option value="1">暴恐-暴恐</option>
								<option value="2">暴恐-正常</option>
								<option value="1">涉黄-黄色</option>
								<option value="2" selected>涉黄-性感</option>
								<option value="3">涉黄-正常</option>
							</select>
						</div>
						<div class="lineDangerLevel">
							<select class="levelTxt">
								<option value="0">高</option>
								<option value="1">中</option>
								<option value="2" selected>低</option>
								<option value="3">无</option>
							</select>
							<div class="levelColor low"></div>
							<div class="removeLevel">
								<!-- <div class="remove"> -->
								<img class="remove" src="${imgPath}/remove.png" />
								<!--  </div> -->
								<div class="del">移除</div>
							</div>
	
						</div>
					</div>
					<div class="lineCon">
						<div class="lineTxt1">
							<select class="op_contents">
								<!-- class="lineContent" -->
								<option value="1">政治人物-涉政</option>
								<option value="2">政治人物-正常</option>
								<option value="1">暴恐-暴恐</option>
								<option value="2">暴恐-正常</option>
								<option value="1">涉黄-黄色</option>
								<option value="2">涉黄-性感</option>
								<option value="3" selected>涉黄-正常</option>
							</select>
						</div>
						<div class="lineDangerLevel">
							<select class="levelTxt">
								<option value="0">高</option>
								<option value="1">中</option>
								<option value="2">低</option>
								<option value="3" selected>无</option>
							</select>
							<div class="levelColor none"></div>
							<div class="removeLevel">
								<!-- <div class="remove"> -->
								<img class="remove" src="${imgPath}/remove.png" />
								<!--  </div> -->
								<div class="del">移除</div>
							</div>
	
						</div>
					</div> --%>
				</div>
				<div class="addLevel">
					<img class="add" src="${imgPath}/add.png" /> <label class="ad">添加</label>
				</div>

			</div>
		</div>
		<div class="levelBottom">
			<div class="bottom_leftTxt">
				最高危险等级<br />直接通知片方
			</div>
			<div class="bottom_rightCon">
				<p>当机审结果属于最高危险等级时，立即返回机审结果给片方。</p>
				<div class="off">
					<img class="closeCon" src="${imgPath}/close.png" /> <label
						class="close">关闭</label>
				</div>
				<div class="on">
					<img class="openCon" src="${imgPath}/open.png" /> <label
						class="open">开启</label>
				</div>
			</div>
		</div>
		<div class="linesave">
			<input id="cancel" type="button" onclick="cancel()"  value="取消" /> 
			<input id="save"   type="submit" onclick="save()"  value="保存" />
		</div>
	</div>
</body>
</html>
