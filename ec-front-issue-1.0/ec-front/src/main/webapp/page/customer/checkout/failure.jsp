<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>成功提交订单--文轩网 </title>
<!--[if lte IE 6]>
<link rel="stylesheet" type="text/css" href="${serverPrefix}images/png_fix.css" />
<![endif]-->
<link href="${serverPrefix}images/general.css" rel="stylesheet" type="text/css">
<script src="${serverPrefix}javascript/jquery1.5.1.js"></script>
<script src="${serverPrefix}javascript/common.js"></script>
<script src="${serverPrefix}javascript/public.js"></script>
</head>
<body>
	<div class="header other_header">
		<div class="logo_box">
			<a title="文轩网,新华书店" href="http://www.winxuan.com/"><img
				src="${serverPrefix}images/logo.gif" alt="文轩网" width="188" height="46">
			</a>
		</div>
	</div>
	<div class="layout">
		<div class="shop_progress">
			<span id="progress3"></span>
		</div>
		<div class="order_border">
			<div class="succ_orders">
				<p>
					<span class="hook"></span><b class="red">订单提交失败！</b>
				</p>
				<c:if test="message">
					${messge}
				</c:if>
			</div>
			<p class="order_tips">
				* 您可以在<a href="../order">我的订单</a>中查看或取消您的订单。<br /> *
				我们会在72小时内为您保留未支付的订单。请及时去<a href="../order">我的订单</a>完成支付
			</p>
		</div>
	</div>
	<%@include file="../../snippets/footer.jsp"%>
</body>
</html>
