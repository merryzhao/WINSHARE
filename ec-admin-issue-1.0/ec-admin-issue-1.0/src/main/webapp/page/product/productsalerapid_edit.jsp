<%@page import="java.util.ArrayList"%>
<%@page import="com.winxuan.ec.model.category.Category"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<html>
<head>
<title>采购快速分拨商品</title>

<style type="text/css">

#add-body {
	margin-top: 30px;
	margin-left: 30px;
	width: 650px;
	height: 250px;
	border: 1px solid #DFDFDF;
}
</style>
<%@include file="../snippets/meta.jsp"%>
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript" src="../../js/product/productsalerapid.js"></script>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<div id="add-body">
					<h4>采购快速分拨商品</h4>
					<form action="/productsalerapid/update" method="post" id="update">
						<input type="hidden" value="${productSaleRapid.productSale.id }" name="productSaleId">
						<table class="list-table1">
							<tr>
								<td>商品ID：</td>
								<td>${productSaleRapid.productSale.id}</td>
							</tr>
							<tr>
								<td>商品名称：</td>
								<td>${productSaleRapid.productSale.product.name}</td>
							</tr>
							<tr>
								<td>码洋：</td>
								<td>${productSaleRapid.productSale.product.listPrice}</td>
							</tr>
							<tr>
								<td>库存占用量:</td>
								<td>${productSaleStock.sales }</td>
							</tr>
							<tr>
								<td>采购量:</td>
								<td><input type="text" name="amount" /></td>
							</tr>
							<tr>
								<td><input type="submit" value="确认" onclick="return rapid.submit()"/></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
 </body>
</html>