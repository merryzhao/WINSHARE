<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>创建搭配推荐</title>
<style type="text/css">
input.codingContent {
	margin-left: 50px;
}

input.sellerName {
	margin-left: 5px;
}

input.productAuthor {
	margin-left: 15px;
}

label.moretrem {
	margin-left: 25px;
	color: #5CACEE;
}
table.tr{
	margin-top: 5px;
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
.saveMoney{width:45px}
#btnpanel{text-align: center;padding: 20px 0px}
#btnpanel input{width:60px;height:25px}
</style>
<!-- 引入JS -->
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
<body
	<c:if test="${productQueryForm.ismore==true}">
onload="inorvisible('moreterm');"
</c:if>>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
			<div style="color: red;">${msg}</div>
				<h4>创建搭配推荐</h4>
				<div>
					<div>
					<b>主商品</b>
					<hr>
					<div>
						<table class="list-table">
							<thead>
								<tr>
									<th>商品编码</th>
									<th>商品名称</th>
									<th>商品类别</th>
									<th>卖家</th>
									<th>储配方式</th>
									<th>码洋</th>
									<th>实洋</th>
								</tr>
							</thead>
							<tbody id="masterBody">
								<tr>
									<td>${productSale.id}</td>
									<td>${productSale.name}</td>
									<td>${productSale.product.sort.name}</td>
									<td>${productSale.shop.name}</td>
									<td>${productSale.storageType.name}</td>
									<td>${productSale.product.listPrice}</td>
									<td>${productSale.salePrice}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<hr>
					<div>
						<table class="list-table">
							<thead>
								<tr>
									<th>ID</th>
									<th>搭配商品名称</th>
									<th>操作类型</th>
									<th>优惠金额</th>
									<th>操作人</th>
									<th>操作时间</th>
								</tr>
							</thead>
							<tbody id="productBody">
							<c:forEach items="${logs}" var="log">
								<tr>
									<td>${log.id}</td>
									<td>${log.productSale.product.name}</td>
									<td>${log.operate.name}</td>
									<td>${log.saveMoney}</td>
									<td>${log.operateUser.name}</td>
									<td>${log.operateDate}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				</div>
				</div>
				</div>
				</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/productbundle/productbundle.js"></script>
</body>
</html>