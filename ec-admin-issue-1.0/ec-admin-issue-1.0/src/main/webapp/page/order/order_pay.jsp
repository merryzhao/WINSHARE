<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan-page"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
.cl1{width:100px}

.cl2{width:360px}

.fontcl {
	font-size: 20px;
	color: #6699cc;
	font-weight: bold;
	font-family: Microsoft YaHei;
	margin-right:20px;
}

.table tr {
	height: 35px;
	margin-top: 20px
}

.table tr td {
	width: 300px;
	margin-top: 20px
}

.mydiv {
	height: 25px;
	margin-top: 15px;
}

.label {
	margin: 0 30px 15px 0px;
}

#cltextarea{float : left;
			width : 190px;
			height : 48px;
			font-size: 13px;}
label.error {
	padding:0em;
	margin: 0;
}
#form input.error {
	margin:0px;
	padding:0px;
	border: 1px solid red;
}

</style>
<title>普通订单登记到款</title>
</head>
<body>
	<div class="frame">
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<div id="content">
				<form:form id="form" action="" method="post"
					commandName="orderPayForm" onsubmit="return confirm('确认登记到款?');" >
				<token:token></token:token>
				<h4>普通订单登记到款</h4>
				<hr>
				<div>
					<label class="fontcl">订单号 : </label> <input type="text" id="orderId"
						class="text" style="padding:10px;color: #6699cc;" name="orderId" /><label
						class="fontcl" id="error"></label>
				</div>
				<div id="information" class="mydiv">
					<label class="label">登记类型:普通订单登记到款</label> 订单号:<label
						id="orderIdLabel" class="label"></label> 注册名:<label
						id="nameLabel" class="label"></label> 账户余额:<label
						id="balanceLabel" class="label"></label>
						还需支付金额:<label id="requidPayMoney" class="label"></label>
				</div>
					<table >
						<tr>
							<td class="cl1">实际到款金额:<font color="red">*</font></td><td class="cl2">
							<form:input path="money"  id="money" class="money"/>
							</td>
						</tr>
						<tr>
							<td>外部单号:<font color="red">*</font></td><td>
							<form:input path="outerId" id="outerId" /><br>
							<font color="red"><form:errors path="outerId" />
							</font>
							</td>
						</tr>
						<tr>
							<td>到款日期:<font color="red">*</font></td><td>
							<form:input path="payTime" bind="datepicker" id="payTime" readonly="true" value="${today }" /><br>
							</td>
						</tr>
						<tr>
							<td>支付方式: </td><td><form:select path="payment">
									<c:forEach items="${listPayment}" var="payment">
										<option id="pay_${payment.id }" value="${payment.id }">${payment.name}</option>
									</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
							<td>支 付 人:</td><td> <form:input path="payer" />
							</td>
						</tr>
						<tr>
							<td><label style="margin-right: 11px;"> 备 注 :</label></td><td> <form:textarea id="cltextarea"
									path="remark" />
							</td>
						</tr>
						<tr>
							<td colspan="2"><input type="submit" id="tijiao" value="提交 " /><input type="submit" id="tijiao2" style="display:none;" disabled="disabled" value="提交 " /></td>
						</tr>
					</table>
					<form:hidden path="customer" id="customer" />
				</form:form>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.methods.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/order/order_pay.js"></script>
</body>
</html>