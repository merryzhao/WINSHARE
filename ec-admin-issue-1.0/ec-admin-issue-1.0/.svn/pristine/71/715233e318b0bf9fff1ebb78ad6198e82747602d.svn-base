<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
<title>促销-运费减免</title>
<style type="text/css">
.idTextArea {
	width: 100px;
	height: 60px;
}
</style>
</head>
<body>
	<div id="content-result">
		<!-- 选择卖家 -->
		<div class="general">
			<label>选择卖家</label> <select id="seller_select">
				<c:forEach items="${sellerList }" var="seller">
					<option value="${seller.id }">${seller.name }</option>
				</c:forEach>
			</select>
			<button type="button" onclick="addSellers();">添加</button>
		</div>
		<!-- 添加的卖家 -->
		<div id="seller_list" class="sellers"></div>
		<div>
			<label>订单基准金额：</label><input type="text" name="minAmount" id="minAmount" value="0"/>元
		</div>
		<div>
			<label>减免金额：</label><input type="text" name="deliveryfee" value="0" />元 <input
				type="checkbox" id="" name="remitdeliveryfee"/>减免所有运费
		</div>
		<div>
			<ul id=area_tree class=tree></ul>
		</div>
		<div id="areaInput"></div>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/promotion/deliveryfee_promotion.js"></script>
</body>
</html>