<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>

<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>商品标签信息查询</title>
<%-- <link type="text/css"
	href="${pageContext.request.contextPath}/css/product/productList.css"
	rel="stylesheet" /> --%>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle3_5/zTreeStyle.css"
	rel="stylesheet" />
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>
<!-- <style>
button.move {
	background: url("/css/zTreeStyle/img/move.png") no-repeat scroll 1px 1px
		transparent;
}
button.add { background:url("/css/zTreeStyle/img/add.jpg")  no-repeat scroll 1px 1px transparent;}
button.info { background:url("/css/zTreeStyle/img/info.png") no-repeat scroll 1px 1px transparent;}
button.update { background:url("/css/zTreeStyle/img/sim/10.png") no-repeat scroll 1px 1px transparent;}
div.updatesort{
position:fixed;left:450px;float: left;border: 0px;background-color:#D9D9D9;display: none;
}
button.move { background:url("/css/zTreeStyle/img/move.png") no-repeat scroll 1px 1px transparent;}
</style> -->
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
			<div id="errMsg" style="display: none;">${errMsg}</div>
			<div id="labels" style="display: none;">${labels}</div>
			<div id="search">
				<form action="/product/label/showproductsalelabels" method="post"
						name="queryForm" id="queryItemForm">
				<table class="list-table" style="width: 1180px">
					<tr>
						<td align="right">商品EC编码：</td>
						<td><input style="height: 25px;" class="input_width" type="text" name="productSaleId"
							id="productSaleId" value="${queryForm.productSaleId}" /></td>
						<td align="right">商品名称：</td>
						<td><input style="height: 25px;" class="input_width" type="text"
							name="sellName" id="sellName"
							value="${queryForm.sellName}" /></td>
						<td align="right">ISBN：</td>
						<td><input style="height: 25px;" class="input_width" type="text"
							name="barcode" id="barcode"
							value="${queryForm.barcode}" /></td>
						
					</tr>
					<tr>
					<td align="right">标签：</td>
						<td>
						 <div>
						 	<ul id="treeDemo" class="ztree"></ul>
						 </div>
						</td>
						<td>
						<input style="height: 25px;" class="input_width" type="hidden"
							name="labelId" id=labelId
							value="${queryForm.labelId}" />
						<input style="height: 25px;" class="input_width" type="text"
							name="labelName" id=labelName
							value="${queryForm.labelName}" />
						</td>
					</tr>
					<tr>
						<td colspan="6" align="right"><input type="submit" value="检索" class="ui-button" /></td>
					</tr>
			   </table>
			   </form>
			</div>
			<div>
				<table class="list-table" style="width: 1180px">
					<tr>
						<td colspan="3" align="left">
							<input type="button" value="添加" id="add_label_button" class="ui-button" />
							<!-- <input type="button" value="修改" id="updateSupply" class="ui-button" />
							<input type="button" value="删除" id="deleteSupply" class="ui-button" /> -->
						</td>
					</tr>
			   </table>
			</div>
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div>
					<form action="">
						<table class="list-table" style="width: 1180;">
							<c:if test="${pagination!=null}">
								<tr>
									<th width="2%"><input type="checkbox" id="selectAll"
										name="selectAll"></th>
									<th width="50px">商品编码</th>
									<th width="100px">商品名称</th>
									<th width="50px">ISBN编码</th>
									<th width="50px">码洋</th>
									<th width="50px">实洋</th>
									<th width="50px">SAP编码</th>
									<th width="50px">MC分类</th>
									<th width="50px">网站可用量</th>
									<th width="50px">状态</th>
									<th width="50px">作者</th>
									<th width="50px">出版社</th>
									<th  width="50px">出版时间</th>
									<th  width="50px">商品供应类型</th>
									<th  width="50px">商品标签</th>
									<!-- <th  width="100px">操作</th> -->
								</tr>
							</c:if>
							<c:forEach var="productSaleLable" items="${productSaleLabels}"
								varStatus="i">
								<tr>
									<td><input type="checkbox" name="export"
										value="${productSaleLable.id}"></td>
									<td><a href="/product/${productSaleLable.productSale.product.id}/detail">${productSaleLable.productSale.id}</a>
									</td>
									<td width="150px;">${productSaleLable.productSale.sellName}</td>
									<td>${productSaleLable.productSale.product.barcode}</td>
									<td>${productSaleLable.productSale.product.listPrice}</td>
									<td>${productSaleLable.productSale.salePrice}</td>
									<td>${productSaleLable.productSale.outerId}</td>
									<td>${productSaleLable.productSale.product.mcCategory}</td>
									<td class="stockQuantityCss"><a href="javascript:void(0);"
										title="<c:if test="${productSaleLable.productSale.supplyType.id==13101 }">实物库存(<c:forEach var="productSaleStock" items="${productSaleLable.productSale.productSaleStockVos}">${productSaleStock.dcdetail.name}:${productSaleStock.stock}；&nbsp;</c:forEach>)</c:if><c:if test="${productSaleLable.productSale.supplyType.id==13102 }">预售库存</c:if>">${productSaleLable.productSale.stockQuantity-productSaleLable.productSale.saleQuantity}</a>
									</td>
									<td class="saleStatuscss">${productSaleLable.productSale.saleStatus.name}</td>
									<td>${productSaleLable.productSale.product.author}</td>
									<td>${productSaleLable.productSale.product.manufacturer}</td>
									<td><fmt:formatDate
											value="${productSaleLable.productSale.product.productionDate }" type="date" />
									</td>
									<td class="statuscss">${productSaleLable.productSale.supplyType.name}</td>
									<td>
									${productSaleLable.label.name}
									</td>
									<%-- <td id="operateTd${i.index}">
									<a target="_blank" href="/product/label/${productSaleLable.id}/edit">编辑</a>
									</td> --%>
								</tr>
							</c:forEach>
							<c:if test="${pagination!=null&&pagination.pageCount!=0}">
								<tr>
									<th width="2%"><input type="checkbox" name="selectAll2">
									</th>
									<th>商品编码</th>
									<th>商品名称</th>
									<th>ISBN编码</th>
									<th>码洋</th>
									<th>实洋</th>
									<th>SAP编码</th>
									<th>MC分类</th>
									<th>网站可用量</th>
									<th>状态</th>
									<th>作者</th>
									<th>出版社</th>
									<th>出版时间</th>
									<th>商品供应类型</th>
									<th>商品类型</th>
									<!-- <th>操作</th> -->
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
		<div class="loading">数据处理中... 请稍候</div>
	</div>
	<!-- 新增和修改界面start -->
	<div id="add_label" style="display: none;height: 100%;width: 100%;">
		<form id="form" action="/product/label/addlabel" method="post">
			<table  style="width: 100%;height: 100%;border-collapse: collapse;">
			<tr height="30">
				<td colspan="2" height="30" align="center">
					<span style="color: red;">商品编码和标签ID按照Tab键分割</span>
				</td>
			</tr>
				<tr height="30">
					<td align="left"><strong style="font: 16px;">商品编码/标签ID：</strong></td>
					<td align="left">
						<textarea id="productSaleLabelStr" name="productSaleLabelStr" style="width:150px;height: 250px;" rows="" cols=""></textarea>
					</td>
				</tr>
				<tr height="30">
					<td colspan="2" height="30" align="center">
						<input type="hidden" id="id" name="id" />
						<input style="width: 50px" type="submit" value="完成" id="saveButton" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ztree.all-3.5.min.js"></script>
<%-- <%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script> --%>
<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath}/js/tree/category_tree.js"></script> --%>
<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.bgiframe.min.js"></script> --%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/product/product_label_list.js"></script>
<%-- <script type="text/javascript"
	src="${contextPath}/js/category/category_move.js"></script> --%>
</html>