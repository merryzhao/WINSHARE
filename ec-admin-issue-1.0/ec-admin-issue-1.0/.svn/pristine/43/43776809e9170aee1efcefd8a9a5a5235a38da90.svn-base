<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div id="content-result">
	<h4>发货信息</h4>

	<table class="list-table">


		<tr>
			<th>配送方式</th>
			<th>承运商</th>
			<th>交寄单号</th>
			<th>发货时间</th>
			<th>发货重量</th>
		</tr>
		<tr>
			<td>${order.deliveryType.name}</td>
			<td>${order.deliveryCompany.company}</td>
			<td>${order.deliveryCode}</td>
			<td><fmt:formatDate type="both" value="${order.deliveryTime}" />
			</td>
			<td>${order.orderWeight}  克</td>
		</tr>
		
	<c:if test="${!empty splits }">
		<tr>
			<td><font color="#7799ff"><b>分包信息</b></font></td>
			<td colspan="3"></td>
		</tr>
			<c:forEach var="split" items="${splits}">
				<tr>
					<td>${order.deliveryType.name}</td>
					<td>${split.company.company}</td>
					<td>${split.code}</td>
					<td><fmt:formatDate type="both" value="${split.deliveryTime}" />
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
		
		<c:if test="${fn:length(listInvoice)>0}">
<h4>发票信息</h4>
	<table class="list-table">
			<tr>
				<th>发票订单号</th>
				<th>开票日期</th>
				<th>开票金额</th>
				<th>抬头类型</th>
				<th>发票抬头</th>
			</tr>
		<c:forEach var="invoice" items="${listInvoice}" varStatus="index">
			<tr>
				<td>${invoice.id}</td>
				<td><fmt:formatDate type="both" value="${invoice.createTime}" />
				</td>
				<td>${invoice.money}</td>
				<td>${invoice.titleType.name}</td>
				<td>${invoice.title}</td>
			</tr>
		</c:forEach>
	</table>
		</c:if>
	
<h4>商品信息</h4>
	<table class="list-table">
		<tr>
			<th>商品编号</th>
			<th>卖家</th>
			<th>商品名</th>
			<th>发货码洋</th>
			<th>发货实洋</th>
			<th>购买数量</th>
			<th>发货数量</th>
		</tr>
		<c:forEach var="orderItem" items="${order.itemList}" varStatus="index">
			<tr>
				<td>${orderItem.productSale.id}</td>
				<td>${orderItem.productSale.shop.shopName}</td>
				<td>${orderItem.productSale.product.name}</td>
				<td><c:if test="${orderItem.deliveryQuantity==0}">0.00</c:if>
					<c:if test="${orderItem.deliveryQuantity!=0}">${orderItem.listPrice}</c:if>
				</td>
				<td><c:if test="${orderItem.deliveryQuantity==0}">0.00</c:if>
					<c:if test="${orderItem.deliveryQuantity!=0}">${orderItem.salePrice}</c:if>
				</td>
				<td>${orderItem.purchaseQuantity}</td>
				<td>${orderItem.deliveryQuantity}</td>
			</tr>
		</c:forEach>

		<tr>
			<td></td>
			<td></td>
			<td></td>

			<td>${totalListPrice}</td>
			<td>${totalSalePrice}</td>
			<td>${order.purchaseQuantity}</td>
			<td>${order.deliveryQuantity}</td>
			<td></td>
		</tr>
	</table>
</div>

