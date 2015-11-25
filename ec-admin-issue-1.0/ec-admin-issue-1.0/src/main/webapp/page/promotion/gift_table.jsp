<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<body>
	<table class="list-table" id="gift_table">
		<colgroup>
			<col class="id" />
			<col class="product_name" />
			<col class="number" />
			<col class="operate" />
		</colgroup>
		<c:if test="${! empty productSales }">
		<tr>
			<th>赠品编号</th>
			<th>赠品名称</th>
			<th>赠送数量</th>
			<th>操作</th>
		</tr>
			<c:forEach items="${productSales }" var="giftProductSale"
				varStatus="status">
				<tr id="giftTr${status.index}">
					<td>${giftProductSale.id}</td>
					<td>${giftProductSale.product.name}</td>
					<td><input type="text" name="giftQuantity" value="1" id="giftQuantity"/></td>
					<td><a  href="javascript:void(0);"
						onclick="deleteTr(${status.index},1);">删除</a></td>		
						<input type="hidden" name="giftIds" value="${giftProductSale.id }" />				
				</tr>
				
			</c:forEach>
		</c:if>
	</table>
	<script type="text/javascript"
		src="${contextPath }/js/promotion/product_promotion.js"></script>
</body>
</html>