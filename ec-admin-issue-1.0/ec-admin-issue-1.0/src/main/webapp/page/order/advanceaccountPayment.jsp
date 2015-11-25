<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
.text {
	width: 240px;
	height: 20px;
	line-height: 20px;
	padding: 0 10px;
	color: #6699cc;
	font-size: 18px;
	font-weight: bold;
	font-family: Microsoft YaHei;
}

.cl-table {
	width: 100%;
	border: 1px solid #DFDFDF
}

.fontcl {
	font-size: 20px;
	color: #6699cc;
	font-weight: bold;
	font-family: Microsoft YaHei;
}

.table tr {
	height: 35px;
	margin-top: 20px
}

.table tr td {
	width: 300px;
	margin-top: 20px
}

.cl1 {
	width: 100px;
}

.cl2 {
	width: 600px;
}

#cltextarea {
	float: left;
	width: 190px;
	height: 48px;
	font-size: 13px;
}

label.error {
	padding: 0.1em;
	color: red;
	font-weight: normal;
}

#form input.error {
	padding: 0px;
	border: 1px solid red;
}
</style>
<style type="text/css">
.error {
	padding: 0em;
	margin-bottom: 0em;
	border: 1px solid #ddd;
}
</style>
<title>暂存款支付</title>
</head>
<body>
	<!-- 引入JS -->
	<%@include file="../snippets/scripts.jsp"%>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>暂存款支付</h4>
				<div id="content-result">
					<div>
						<form:form action="/order/confirmAdvanceaccountPayment" onsubmit="return check();" method="post"
							id="form" commandName="advanceaccountPaymentFrom">
							<token:token></token:token>
							<table>
								<tr>
									<td class="cl1"><label class="fontcl">订单号:</label>
									</td>
									<td class="cl2"><form:input class="text" path=""
											id="paymentOrderId" name="orderId"
											style="padding:10px;color:#6699cc;" /><font color="red"><form:errors
												path="orderId" />
									</font><label id="error" class="fontcl"></label>
									</td>
								</tr>
								<tr>
									<td id="mytd" colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2"><input type="submit" id="advanceaccountSubmit" value="提交" disabled="disabled" />	</td>
								</tr>
							</table>

						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.methods.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/customer/advanceAccountPayment.js"></script>
</body>
</html>