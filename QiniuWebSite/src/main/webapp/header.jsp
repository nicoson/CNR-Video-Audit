<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title></title>

<style type="text/css">
    .navbarWaiceng {
        width: 100%;
        background-color: #fcfcfc;
        box-shadow: 0 2px 4px 0 rgba(0,0,0,.04);
    }
    .navbar-container {
        width: 1080px!important;
        display: block;
        margin: 0 auto;
    }
    .navbar-container .topLevel {
        height: 62px;
        width: 100%;
        background-color: #fcfcfc;
    }
    .navbar-container .navbar-p1 {
        display: inline-flex;
        height: 100%;
        font-size: 22px;
        color: #333;
        letter-spacing: 0;
        text-align: left;
        margin-right: 76px;
    }
    .navbar-container .navbar-p2 {
        display: inline-flex;
    }
    .navbar-container .navbar-p3 {
        display: inline-flex;
        float: right;
        line-height: 62px;
        height: 62px;
    }
    .navbar-p2 .navitem {
        font-size: 18px;
        color: #333;
        letter-spacing: 0;
        text-align: center;
        line-height: 62px;
        cursor: pointer;
        height: 62px;
    }
    .navitem+.navitem {
        margin-left: 59px;
    }
    .navbar-p1 span {
        font-size: 22px;
        line-height: 62px;
        color: #333;
        letter-spacing: 0;
        text-align: left;
        padding-left: 10px;
        font-size: 22px; font-weight: bolder; color: #333;
    }
    .navbar-p2 .navitem.selected {
        color: #38adff;
        border-bottom: 3px solid #38adff;
    }
    .navbar-p3 .navsitem {
        font-size: 12px;
        color: #333;
        letter-spacing: 0;
        text-align: center;
        padding: 0 12px;
        height: 12px;
        cursor: pointer;
    }
    </style>
</head>
<body style="background-color: #e7e8eb;">
    <nav class="navbar navbar-default navbar-static-top ng-scope" >
    <div  class="ng-isolate-scope">
    	<div class="navbarWaiceng">
    		<div class="navbar-container">
    			<div class="topLevel">
    				<div class="navbar-p1">
    					<a href="#/welcome">
    						<img src="${base}/resources/images/VCCLogo.png" style="height: 34px; width: 34px;"><span>央广视讯播控系统</span>
    					</a>
    				</div>
	    			<div class="navbar-p3" >
	    				<div class="navsitem" id="navitem7" onclick="javascript:document.location.href = '${base}/index/loginOut.do'">退出</div>
	    			</div>
    			</div><div></div>
    		</div>
    	</div>
    </div>
    </nav>
    <script type="text/javascript">
        $(function () {
			// 首页
            $('#navitem1').click(function () {
            	$.ajax({
            		type: "POST",
            		url : "${base}/index/indexMsg.do",
            		success : function(res)
            		{
            			if(res!="")
            			{ 
            				$('.navitem').removeClass("selected");
            				$('#navitem1').addClass("selected");
            				$(".c_box").html(res);
            			}
            		}
            	});
            });
        });
    </script>
</body>
</html>
