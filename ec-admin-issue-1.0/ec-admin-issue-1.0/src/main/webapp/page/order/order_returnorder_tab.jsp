<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div>
	<h4>退换货信息</h4>
	<button type="button"
		onclick="window.location.href='${contextPath}/returnorder/new?id=${orderId}'">新建退换货申请</button>
	<br> <br>
	<table class="list-table">
		<tr>
			<th>退换货订单</th>
			<th>退换货订单状态</th>
			<th>创建时间</th>
			<th>创建人</th>
			<th>状态</th>
			<th>书款退款</th>
			<th>退换货数量</th>
			<th>付退金额</th>
			<th>应退运费合计</th>
			<th>礼券支付</th>
			<th>礼品卡支付</th>
		</tr>
		<c:forEach var="returnOrder" items="${returnOrders}">
			<tr>
				<td><a href="/returnorder/${returnOrder.id }/detail" target="_blank">${returnOrder.id}</a>
				</td>
				<td>${returnOrder.status.name }</td>
				<td><fmt:formatDate value="${returnOrder.createTime }"
						type="date" />
				</td>
				<td>${returnOrder.creator.realName }</td>
				<td>${returnOrder.type.name}</td>
				<td>${returnOrder.type.id==24001?returnOrder.refundGoodsValue:returnOrder.desireTotalSalePrice} 元</td>
				<td><c:set value="0" var="count"></c:set> <c:forEach
						var="itemList" items="${returnOrder.itemList}">
						<c:set value="${itemList.appQuantity + count }" var="count"></c:set>
					</c:forEach> ${count }</td>
				<td>${returnOrder.refundCompensating}</td>
				<td>${returnOrder.refundDeliveryFee}元</td>
				<td></td>
				<td></td>
			</tr>
		</c:forEach>
	</table>
</div>