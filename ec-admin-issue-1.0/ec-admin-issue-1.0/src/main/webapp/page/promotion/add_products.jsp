<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<html>
<head>
</head>
<body>
	<c:forEach items="${list}" var="attr" varStatus="status">
		<script>
			var jsonobject=
			{
					productId:${attr.productSale.id},
					productName:'${attr.productSale.product.name}',
			        productType:'${attr.productSale.product.sort.name}',
			        shopName:'${attr.productSale.shop.name}',
			        storageType:'${attr.productSale.storageType.name}',
			        price:'${attr.productSale.product.listPrice}',
			        dbSalePrice:${attr.productSale.salePrice},
			        saleprice:'${attr.discount==null?attr.productSale.salePrice:attr.discount}'
			};
			parent.addProduct(jsonobject);
		</script>
	</c:forEach>
</body>
</html>