<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<body>
	<c:if test="${ empty list }">
		<script>
			parent.noProduct();
		</script>
	</c:if>
	<c:if test="${! empty list }">
	<c:forEach items="${list}" var="attr" varStatus="status">
		<script>
			var jsonobject=
			{
			        productSaleId:${attr.productSale.id},
			        productOuterId:'${attr.productSale.outerId}',
			        productName:'${attr.productSale.sellName}',
			        shopName:'${attr.productSale.shop.shopName}',
			        statusName:'${attr.productSale.saleStatus.name}',
			        statusId:${attr.productSale.saleStatus.id},
			        discount:'${attr.discount}',
			        stockQuantity:'${attr.stocks}',//库存在dc中的组成
			        canSaleQuantity:'${attr.canSaleQuantity}',//可用量
			        listPrice:${attr.productSale.product.listPrice},//商品码洋
			        salePrice:${attr.productSale.salePrice},//销售价格
			        purchaseQuantity:${attr.num},
			        xiaoji:${attr.productSale.salePrice*attr.num},
			        supplyType:'${attr.supplyType}'
			};
			parent.addProduct(jsonobject);
		</script>
	</c:forEach>
	</c:if>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>