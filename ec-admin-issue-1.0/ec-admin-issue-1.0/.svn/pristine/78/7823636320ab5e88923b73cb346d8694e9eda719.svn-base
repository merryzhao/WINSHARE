<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<body>
	<table class="list-table" id="product_table">
		<colgroup>
			<col class="id" />
			<col class="product_name" />
			<col class="type" />
			<col class="type" />
			<col class="type" />
			<col class="number" />
			<col class="number" />
			<col class="address" />
			<col class="operate" />
		</colgroup>
		<c:if test="${not empty productSales }">
			<c:forEach items="${productSales }" var="productSale"
				varStatus="status">
				<tr id="productTr${status.index+oldIndex }">
					<td>${productSale.id }</td>
					<td>${productSale.product.name }</td>
					<td>${productSale.product.sort.name }</td>
					<td>${productSale.shop.name }</td>
					<td>${productSale.storageType.name }</td>
					<td>${productSale.product.listPrice }</td>
					<td>${productSale.salePrice }</td>
					<td><input type="text" name="productQuantity" value="1"
						id="productQuantity" />
					</td>
					<td><a href="javascript:void(0);"
						onclick="deleteTr(${status.index+oldIndex},0);">删除</a>
					</td>
					<input type="hidden" name="productSaleIds"
						value="${productSale.id }" />
				</tr>
			</c:forEach>
		</c:if>
	</table>
</body>
</html>