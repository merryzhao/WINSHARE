<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>开票成功</title>


<style type="text/css">
#invoice-body {
	margin-top: 100px;
	width: 600px;
	height: 500px;
}

#success {
	margin-top: 50px;
	width: 500px;
	height: 250px;
	border: 2px solid #DFDFDF;
}

.slable {
	margin-top: 100px;
}
</style>
</head>
<script type="text/javascript" src="${contextPath}/js/jQuery.js"></script>
<script type="text/javascript" src="${contextPath}/js/orderDetail.js"></script>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<center>
					<div class="step">
				<img src="${contextPath}/css/images/invoice_bk_step_3.jpg">
			</div>
					<div id="invoice-body">
						<div id="success">
							<div class="slable">
							${orderId}订单开票成功，发票号${orderInvoiceId}
 							</div>
							</div>
						</div>
				</center>
			</div>
		</div>
	</div>
</body>
</html>