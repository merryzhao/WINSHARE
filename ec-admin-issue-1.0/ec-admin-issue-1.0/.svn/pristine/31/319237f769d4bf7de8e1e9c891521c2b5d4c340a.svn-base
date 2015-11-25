<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/meta_payment.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>成功</title>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<div class="step">
				<img src="${contextPath}/css/images/payment_success.jpg">
			</div>
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="content-result" class="step">
					<div>您的支付信息已经添加成功!</div><br>
					<div>
						<a href="${contextPath}/payment">点击跳转到管理支付方式页</a>
					</div>
					<br>
					<div id="ShowDiv"></div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/payment/payment.js"></script>
	<script type="text/javascript">
		Load("/payment");
	</script>
</body>
</html>