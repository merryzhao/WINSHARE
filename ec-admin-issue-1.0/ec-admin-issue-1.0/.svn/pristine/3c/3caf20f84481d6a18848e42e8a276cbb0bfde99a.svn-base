<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/meta_product.jsp"%>
<%@include file="../snippets/meta.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
div.showContent a:link {
	color: #FF5500;
}
div.ui-widget-content{
	width:1150px;
}
</style>
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript" src="../../js/product/productsalerapid.js"></script>
<title>商品详细信息</title>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner ui-widget-content" id="content">
				<div id="ui-widget-content">
				<p><input type="button" value="采购快速分拨商品" onclick="rapid.edit()"></p>
				<br/>
  					<div class="ui-widget-content">
						<h4>商品信息</h4>
						<table class="list-table" >
							<tr>
								<td>商品ID：</td>
								<td>${productSaleRapid.productSale.id}</td>
								<td>商品名称：</td>
								<td>${productSaleRapid.productSale.product.name}</td>
								<td>码洋：</td>
								<td>${productSaleRapid.productSale.product.listPrice}</td>
							</tr>
							<tr>
								<td>类型：</td>
								<td>${productSaleRapid.productSale.product.sort.name}</td>
								<td>是否套装商品：</td>
								<td><c:if test="${productSaleRapid.productSale.product.complex==1}">是</c:if> <c:if
										test="${productSaleRapid.productSale.product.complex==0}">否</c:if></td>
								<td>是否虚拟商品(礼品卡)：</td>
								<td><c:if test="${productSaleRapid.productSale.product.virtual}">是</c:if> <c:if
										test="${!productSaleRapid.productSale.product.virtual}">否</c:if></td>
							</tr>
							<tr>
								<td>库存占用量</td>
								<td>${productSaleStock.sales }</td>
								<td>采购量</td>
								<td>${productSaleRapid.amount }</td>
								<td>操作人</td>
								<td>${productSaleRapid.creator.name }</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="pid" value="${productSaleRapid.productSale.id}">
</body>
	   	
</html>