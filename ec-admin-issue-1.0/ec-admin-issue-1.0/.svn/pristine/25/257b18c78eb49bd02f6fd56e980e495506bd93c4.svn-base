<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
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
			<c:forEach items="${productSales }" var="giftProductSale"
				varStatus="status">
				<tr id="giftTr${status.index+oldIndex}">
					<td>${giftProductSale.id}</td>
					<td>${giftProductSale.product.name}</td>
					<td><input type="text" name="giftQuantity" value="1"
						id="giftQuantity" /></td>
					<td><a href="javascript:void(0);"
						onclick="deleteTr(${status.index+oldIndex},1);">删除</a></td>
					<input type="hidden" name="giftIds" value="${giftProductSale.id }" />
				</tr>
			</c:forEach>

		</c:if>
	</table>
</body>
</html>