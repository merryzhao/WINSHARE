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
<title>批量订单支付信息</title>
</head>
<body>
	<c:choose>
		<c:when test="${message == null }">
		<form action="/order/batchPay" method="POST">
			<label>支付方式:</label>
			<select name="paymentTypeId">
				<c:forEach items="${listPayment}" var="payment">
						<option value="${payment.id }">${payment.name}</option>
				</c:forEach>
			</select>
			<table class="list-table">
				<thead>
					<tr>
						<th>订单号</th>
						<th>支付平台</th>
						<th>外联凭证号</th>
						<th>汇款金额</th>
						<th>汇款账号</th>
						<th>汇款人</th>
						<th>地址</th>
						<th>到款日期</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paylist }" var="pay" varStatus="s">
						<tr>
							<td><input readonly="readonly" name="list[${s.index}].orderId" value="${pay.orderId}" /></td>
							<td><input readonly="readonly" name="list[${s.index}].platform" value="${pay.platform}" /></td>
							<td><input readonly="readonly" name="list[${s.index}].credential" value="${pay.credential}" /></td>
							<td><input readonly="readonly" name="list[${s.index}].money" value="${pay.money}" /></td>
							<td><input readonly="readonly" name="list[${s.index}].account" value="${pay.account}" /></td>
							<td><input readonly="readonly" name="list[${s.index}].payer" value="${pay.payer}" /></td>
							<td><input readonly="readonly" name="list[${s.index}].address" value="${pay.address}" /></td>
							<td><input readonly="readonly" name="list[${s.index}].payTime" value="${pay.payTime}" /></td>
							<td><input readonly="readonly" name="list[${s.index}].remark}" value="${pay.remark}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<input type="submit" value="确认提交" />
		</form>
		</c:when>
		<c:otherwise>
			批量导入出错:${message }
		</c:otherwise>
	</c:choose>
</body>
</html>