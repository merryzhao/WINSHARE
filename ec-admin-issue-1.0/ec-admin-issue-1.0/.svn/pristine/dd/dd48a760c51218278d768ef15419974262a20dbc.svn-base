<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/winxuan-string" prefix="s" %>
<%@include file="../snippets/tags.jsp"%>

<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>套装书查询</title>
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
select{
	margin:0.5em 0 0 0;
}
table.page{
	width:1150px;
}
</style>
<!-- 引入JS -->
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
</head>
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

				<h4>套装书查询</h4>
				<!-- 查询表单div -->
				<div id="queryForm">
						<form:form class="inline" action="/product/complexlist" method="post" commandName="complexQueryForm" id="complexQueryForm">
 						<table>
 						<tr>
 						<td><label>商品编号：</label></td>
 						<td><form:textarea  style="width: 150px;height: 60px;"  path="codingContent"/>	</td>
 						<td><label>子商品编号：</label></td>
 						<td><form:textarea  style="width: 150px;height: 60px;"  path="items"/>	</td>
 						<td><label>商品名称：</label></td>
 						<td><form:input path="productName"/></td>
 						<td> <input type="submit" value="检索"/></td>
 						</tr>
 						</table>
						 <div> 
						 <label>商品创建时间：</label> <input id="startDate" class="long" readonly="readonly"
									type="text" name="startDate" bind="datepicker"
									value="${startDate}" /> <label>~</label>
								<input id="endDate" class="long" type="text" name="endDate" readonly="readonly"
									bind="datepicker" value="${endDate}"/>
								  <label>
								上下架状态：</label> 
								<form:select id="status" path="status" multiple="">
								    <form:option value="0" label="请选择"></form:option>
									<c:forEach items="${saleStatus}" var="s">
									    <form:option value="${s.id}" label="${s.name}"></form:option>
									</c:forEach>
							</form:select>
 						</div>
						</form:form>
				</div>
				<!-- 查询结果展示div -->
				<div>
					<c:if test="${pagination!=null}">
 						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<form action="">
						<table style="width:1150px;" class="list-table">
							<c:if test="${pagination!=null}">
								<tr>
									<th>&nbsp;&nbsp;商品编码&nbsp;&nbsp;</th>
 									<th>商品名称</th>
									<th>商品状态</th>
 									<th>码洋</th>
									<th>实洋</th>
									<th>商品库存</th>
									<th>操作</th>
								</tr>
							</c:if>
							
   							<c:forEach var="productSale" items="${productSales}" varStatus="i">
								<tr>
 									<td>
										<a href="/product/${productSale.id}/editcomplex">${productSale.id}</a>
									</td>
									<td align="left">
										<img id="detailimg" height="80" width="80" src="${productSale.product.smallImageUrl}">
										<s:substr length="8" content="${productSale.product.name}"/>
										<c:if test="${fn:trim(productSale.product.complex)==fn:trim(1)}">
										套装
										</c:if>
										<c:forEach var="ex" items="${productSale.product.extendList}">
											<c:if test="${fn:trim(ex.productMeta.id)==fn:trim(4)}">
												${ex.value}
											</c:if>
										</c:forEach>
									</td>
 									<td class="saleStatuscss">${productSale.saleStatus.name}</td>
 									<td>${productSale.product.listPrice}</td>
									<td>${productSale.salePrice}</td>
  									<td class="stockQuantityCss">
  									<c:forEach items="${productSale.productSaleStockVos}" var="productStock">
  									   ${productStock.dcdetail.description}(${productStock.dcdetail.name}):${productStock.availableQuantity}</br>
  									</c:forEach>
  									
  									</td>
 									<td id="operateTd${i.index }">
		 									<a href="/product/${productSale.id}/editcomplex">[修改]</a>
		 									<select id="statusChange" name="statusSelect" psid="${productSale.id}" psStatus="${productSale.saleStatus.id}">
		 										<option value="-1">更改状态</option>
		 										<option value="${Code.PRODUCT_SALE_STATUS_OFFSHELF}">下架</option>
		 										<option value="${Code.PRODUCT_SALE_STATUS_ONSHELF}">上架</option>
		 										<option value="${Code.PRODUCT_SALE_STATUS_DELETED}">删除</option>
		 									</select>
										</td>
								</tr>
							</c:forEach>
 							<c:if test="${pagination!=null}">
 								<c:if test="${empty productSales&&pagination!=null}"><tr><td colspan="7">暂无数据</td></tr></c:if>
								<tr>
									<th>&nbsp;&nbsp;商品编码&nbsp;&nbsp;</th>
 									<th>商品名称</th>
									<th>商品状态</th>
 									<th>码洋</th>
									<th>实洋</th>
									<th>商品库存</th>
									<th>操作</th>
								</tr>
							</c:if>
						</table>
						<input id="productSaleOnShelfStatus" type="hidden" value="${Code.PRODUCT_SALE_STATUS_ONSHELF}"/>
						<input id="productSaleOffShelfStatus" type="hidden" value="${Code.PRODUCT_SALE_STATUS_OFFSHELF}"/>
						<input id="productSaleDeleteStatus" type="hidden" value="${Code.PRODUCT_SALE_STATUS_DELETED}"/>
					</form>
					<c:if test="${pagination!=null}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
				</div>

			</div>
		</div>
		<div class="loading">数据处理中... 请稍候</div>
	</div>
	 <%@include file="../snippets/scripts.jsp"%>
 	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/product/querycomplex.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/tree/category_tree.js"></script>
</body>
</html>