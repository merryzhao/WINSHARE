<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<div>
	<!-- 选择卖家 -->
	<div class="general">
		<label>选择卖家</label> <select id="seller_select">
			<c:forEach items="${sellerList }" var="seller">
				<option value="${seller.id }">${seller.name }</option>
			</c:forEach>
		</select>
		<button type="button" onclick="orderPriceAddSeller();">添加</button>
	</div>
	<!-- 添加的卖家 -->
	<div id="seller_list" class="sellers"></div>
	<!-- 促销方式 -->
	<div class="promotion_type">
		<div>
			<label>促销方式：</label> <input type="radio" checked="checked"
				name="orderPricemanner" value="0"><label>普通优惠</label> <input
				type="radio" name="orderPricemanner" value="1"><label>梯度优惠</label>
		</div>
		<div class="promotion_type_price">
			<div id="general_price_promotion" class="general"></div>
			<div id="grads_price_promotion" class="general"></div>
		</div>
	</div>
</div>

