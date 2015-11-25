<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>商品信息查询</title>
<style type="text/css">
input.codingContent {
	margin-left: 50px;
}

input.sellerName {
	margin-left: 5px;
}

input.productAuthor {
	margin-left: 5px;
}

label.moretrem {
	margin-left: 25px;
	color: #5CACEE;
}

div.moreterm {
	display: none;
}
.error {
    border: 0px;
    padding: 0px;
    margin: 0px;
}
textarea {
	width: 300px;
	height: 80px;
}
</style>
<!-- 引入JS -->
</head>
<body
	<c:if test="${productQueryForm.ismore==true}">
onload="inorvisible('moreterm');"
</c:if>>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>礼品卡商品信息</h4>
			
				<!-- 查询结果展示div -->
				<div>
					<c:if test="${pagination!=null&&pagination.pageCount!=0}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<form action="">
						<table class="list-table">
							<c:if test="${pagination!=null}">
								<tr>
									<th>商品编码</th>
									<th>商品名称</th>
									<th>码洋</th>
									<th>实洋</th>
									<th>EC库存量</th>
									<th>EC库存可用量</th>
									<th>状态</th>
								</tr>
							</c:if>
							<c:forEach var="productSale" items="${productSales}"
								varStatus="i">
								<tr>
									<td><a
										href="/product/${productSale.id}/detail">${productSale.id}</a>
									</td>
									<td>${productSale.product.name}</td>
									<td>${productSale.product.listPrice}</td>
									<td>${productSale.salePrice}</td>
									<td class="stockQuantityCss">${productSale.stockQuantity}</td>
									<td>${productSale.stockQuantity-productSale.saleQuantity}</td>
									<td class="statuscss">${productSale.saleStatus.name}</td>
								</tr>
							</c:forEach>
							<c:if test="${pagination!=null}">
								<tr>
									<th>商品编码</th>
									<th>商品名称</th>
									<th>码洋</th>
									<th>实洋</th>
									<th>EC库存量</th>
									<th>EC库存可用量</th>
									<th>状态</th>
								</tr>
							</c:if>
						</table>
					</form>
					<c:if test="${pagination!=null&&pagination.pageCount!=0}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
				</div>

			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>