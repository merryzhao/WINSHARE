<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>批量修改商品价格</title>
</head>
<body onload="chackValue();">
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>批量修改商品价格:</h4>
				<!-- 查询表单div -->
				<div id="queryForm">
					<form action="/product/productPriceUpdateUpload" method="post"
						id="excelForm" enctype="multipart/form-data">
						<table>
							<tr>
								<td>导入待修改的商品:<a href="javascript:void(0);"
									onclick="$('#exForm').submit();">模板下载</a> <input type="file"
									name="fileName" id="fileName"> <input type="button"
									value="上传" id="sbm"><br/><span
									style="color: red; font-size: 10px">(目前仅支持电子书修改，自动过滤非电子书)</span>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<form action="/excel/exportProductPriceUpdateModel" target="_blank"
					method="post" name="exForm" id="exForm">
					<input type='hidden' name='format' value='xls' />
				</form>
				<!-- 查询结果展示div -->
				<div>
					<br /> <input type="button" id="productPriceUpdateButton"
						value="批量修改商品价格" />
					<form action="/product/productPriceUpdateing" id="priceUpdateing"
						name="priceUpdateing" method="post">
						<table class="list-table" id="datatable">
							<tr>
								<th>商品编码</th>
								<th>商品名称</th>
								<th>ISBN编码</th>
								<th>码洋</th>
								<th>实洋</th>
								<th>折扣</th>
								<th>操作</th>
							</tr>
							<c:if test="${fn:length(productSales)!=0}">
								<c:forEach var="productSale" items="${productSales}">
									<tr
										<c:if test="${productSale.isWarning}">style="color:red;"</c:if>>
										<td><input type="hidden" name="checkValue"
											value="${productSale.isWarning}"> <a
											href="/product/${productSale.id}/detail">${productSale.id}</a><input
											type="hidden" name="productSaleId" value="${productSale.id}">
										</td>
										<td>${productSale.name}</td>
										<td>${productSale.barcode}</td>
										<td>${productSale.listPrice}</td>
										<td>${productSale.saleprice} <input type="hidden"
											name="saleprice" value="${productSale.saleprice}">
										</td>
										<td>${productSale.discount}</td>
										<td><a href="javascript:void(0)" class="delectpro">删除该条记录</a></td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${fn:length(productSales)==0}">
								<tr>
									<td align="center" colspan="7">暂无数据</td>
								</tr>
							</c:if>
							<tr>
								<th>商品编码</th>
								<th>商品名称</th>
								<th>ISBN编码</th>
								<th>码洋</th>
								<th>实洋</th>
								<th>折扣</th>
								<th>操作</th>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div class="loading">数据处理中... 请稍候</div>
	</div>
	<!-- 引入JS -->
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/product/productPriceUpdate.js"></script>
</body>
</html>