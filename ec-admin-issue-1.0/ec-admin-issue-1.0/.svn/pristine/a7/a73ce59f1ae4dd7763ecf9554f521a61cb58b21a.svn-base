<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div>
	<h4>订单号:${orderId }</h4>
	<p align="left">
		<input type="button" onclick="javascript:$('input[name=itemIds]').attr('checked',true);changeOrderItem();" value="全选"/>
		<input type="button" onclick="javascript:$('input[name=itemIds]').each(function(i,e){ $(e).attr('checked',!$(e).attr('checked')); });changeOrderItem();" value="反选"/>
	</p>
	<form id="order_item_form" action="/ordersettle/addBillItems" method="post">
		<input type="hidden" name="billId" value="${billId }"/>
		<table class="list-table" style="width: 1000px;" id="order_item_table">
			<tr>
				<th></th>
				<th>商品ID</th>
				<th>商品名</th>
				<th>码洋</th>
				<th>实洋</th>
				<th>本次可结算数量</th>
				<th>本次结算金额</th>
			</tr>
			<c:forEach var="orderItem" items="${orderItemList}">
				<tr>
					<td align="center">
						<input type="checkbox" name="itemIds" value="${orderItem.id }" onclick="changeOrderItem();"> 
					</td>
					<td>${orderItem.productSale.id }</td>
					<td>${orderItem.productSale.sellName }</td>
					<td>${orderItem.listPrice }</td>
					<td>${orderItem.salePrice }</td>
					<td>${orderItem.availableQuantity }</td>
					<td id="orderitemprice_${orderItem.id }">${orderItem.availablePrice }</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="8" align="right">总计：<span id="orderitem_total">0</span>元</td>
			</tr> 
		</table>
		<p align="center">
			<button type="button" onclick="submitOrderItemForm();return false;">确定</button>
			<button type="button" onclick="$('#orderItemInfo').dialog('close');return false;">关闭</button>
		</p>
	</form>
</div>
<script
		src="${pageContext.request.contextPath}/js/ordersettle/orderitem.js"></script>

