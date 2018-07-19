<%@page contentType="text/html;charset=utf-8"%>
<%@include file="../../Header.inc"%>
<jsp:include page="/header.jsp"></jsp:include>
<script src="${resources}/js/index.js"></script>
</head>
<body>
	<div class="container ng-scope">
		<div class="ng-scope ng-isolate-scope">
			<div class="c_box"
				style="display:flex;overflow:visible;position:relative;">
			</div>
		</div>
	</div>
	<iframe name="content_frame" marginwidth=0 marginheight=0 width=100%
		height=50 src="${base}/footer.jsp" frameborder=0></iframe>

		
</html>