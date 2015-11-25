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
			<div style="color: red;">${msg==null ? (param.msg==null?"":param.msg) : msg }</div>
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
					<div>
					<select id="productType">
						<option value="productId">商品编号</option>
						<option value="outerId">自编码</option>
					</select>
					<textarea id="productValue"></textarea>
					<a href='javascript:;' bind="addProduct">添加商品</a><span id="productmsg" style="color: red;"></span></div>
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
									<th>组合优惠金额(元)</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="productBody">
							<c:forEach items="${productSale.bundles}" var="bundle">
								<tr>
									<td>${bundle.productSale.id}</td>
									<td>${bundle.productSale.name}</td>
									<td>${bundle.productSale.product.sort.name}</td>
									<td>${bundle.productSale.shop.name}</td>
									<td>${bundle.productSale.storageType.name}</td>
									<td>${bundle.productSale.product.listPrice}</td>
									<td>${bundle.productSale.salePrice}</td>
									<td><input type='text' class='saveMoney' data-id='${bundle.productSale.id}' value='${bundle.saveMoney}'></td>
									<td><a href='javascript:;' bind='bundleProductDel' data-id='${bundle.id}'>删除</a></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div id="btnpanel">
					<input type="button" value="提交" bind="savebundle">
					<form id="bundleform" method="post" action="/bundle/${productSale.id}/edit"></form>
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