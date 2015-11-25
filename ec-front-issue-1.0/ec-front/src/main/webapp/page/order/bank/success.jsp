<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--[if lte IE 6]>
<link rel="stylesheet" type="text/css" href="${serverPrefix}images/png_fix.css" />
<![endif]-->
<link href="${serverPrefix}images/general.css" rel="stylesheet"
	type="text/css">
<style>
.layout {
	width: 990px;
	height: 318px;
	border: #CCC solid 1px;
	padding-top: 100px;
}

.pro_mid {
	background-color: #FFFFFF;
	border-color: -moz-use-text-color #B5B5B5;
	border: 0px solid #B5B5B5;
	padding: 40px 14px;
	margin-left: auto;
	margin-right: auto;
	text-align: left;
	width: 600px;
}
.order_su{
	width:300px;
	margin: 0 auto;
}
div,td {
	font-size: 14px;
	line-height: 20px;
}

.black {
	padding-top: 10px;
	border-top: 3px #CCC solid;
	height: 20px;
	width: 990px;
	margin: 0 auto;
}
</style>
<title>支付成功</title>
</head>
<body>
	<div class="header other_header">
		<div class="logo_box">
			<a title="文轩网,新华书店" href="http://www.winxuan.com/"><img
				src="${serverPrefix}images/logo.gif" alt="文轩网" width="188"
				height="46"> </a>
		</div>
	</div>
	<div class="black">&nbsp;</div>
	<div class="layout">
		<div class="pro_mid">
			<div class="order_su pay_su">
		        <span class="hook"></span>
		        <b>恭喜您，订单支付成功！</b>
		        <p>您现在可以：</p>
		        <p>1、查看订单详情&nbsp;&nbsp;<c:forEach var="order" items="${orderList}"><a href="http://www.winxuan.com/customer/order/${order.id}">${order.id}</a>&nbsp;&nbsp;</c:forEach></p>
				<p>2、<a href="javascript:window.close();">关闭窗口</a></p>
		    </div>
		</div>
	</div>
	<%@include file="../../snippets/footer.jsp"%>




</body>
</html>

