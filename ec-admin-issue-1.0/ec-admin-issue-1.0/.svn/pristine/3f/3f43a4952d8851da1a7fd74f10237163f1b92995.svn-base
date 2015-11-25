<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
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
		
		<c:if test="${! empty productSales }">
		<tr>
			<th>商品编号</th>
			<th>商品名称</th>
			<th>商品类别</th>
			<th>卖家</th>
			<th>储配方式</th>
			<th>码洋</th>
			<th>实洋</th>
			<th>数量限制(最小)</th>
			<th>操作</th>
		</tr>
			<c:forEach items="${productSales }" var="productSale"
				varStatus="status">
				<tr id="productTr${status.index }">
					<td>${productSale.id }</td>
					<td>${productSale.product.name }</td>
					<td>${productSale.product.sort.name }</td>
					<td>${productSale.shop.name }</td>
					<td>${productSale.storageType.name }</td>
					<td>${productSale.product.listPrice }</td>
					<td>${productSale.salePrice }</td>
					<td><input type="text" name="productQuantity" value="1" id="productQuantity"/>
					</td>
					<td><a  href="javascript:void(0);"
						onclick="deleteTr(${status.index},0);">删除</a>
					</td>					
					<input type="hidden" name="productSaleIds" value="${productSale.id }" />
				</tr>
				
			</c:forEach>
		</c:if>
	</table>
	<script type="text/javascript"
		src="${contextPath }/js/promotion/product_promotion.js"></script>
</body>
</html>