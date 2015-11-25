<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<%@page import="java.net.URLDecoder"%>
<html>
<head>
<title>补货审核</title>
<%@include file="../snippets/meta.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/tablesorter/themes/blue/style.css"
	type="text/css" media="print, projection, screen" />
<style type="text/css">
label.red {
	color: red;
}

input.long {
	width: 98%;
}

label.error {
	border: none;
	padding: 0.1em;
	color: red;
	font-weight: normal;
	background: none;
	padding-left: 16px;
}

td label.error {
	display: none !important;
}

#uploadPanel {
	margin: 20px 0;
}

#uploadbtn {
	padding: 2px 10px;
}

.input_width {
	width: 100px;
}

table#tablesorter th {
	color: #333;
}
</style>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<!-- 主体start -->
		<div class="frame-main">

			<!-- 核心内容部分div start-->
			<div class="frame-main-inner" id="content">
				<h4>补货审核</h4>
				<div>
					<div>
						<p>说明:</p>
						<ul>
							<li>1. 格式只能使用excel2003兼容格式, 即后缀是xls的excel文件</li>
							<li>2. 样例模板<a
								href="http://console.winxuan.com/excel/replenishment/template.xls">下载</a></li>
							<li></li>
							<li></li>
						</ul>
					</div>
					<div id="uploadPanel">
						<form action="/replenishment/productItemExcelImport" method="post"
							enctype="multipart/form-data" id="uploadForm">
							<table>
								<tr>
									<td>文件:<input type="file" name="file"></td>
									<td><input type="submit" value="提交" id="uploadbtn" /></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
				<br />
				<div style="color: red">
					<c:if test="${msg == 'success'}">
							操作成功！<br />
					</c:if>
					
					<c:if test="${msg == 'error'}">
							操作失败！${errMsg}
						<br />
					</c:if>
					<p id="messsage">
						<strong id="msg" style="display: none;">${msg}</strong>
					</p>
				</div>
				<!-- 查询表单div start-->
				<div id="queryForm">

					<form action="/replenishment/productItemList" method="post"
						name="queryForm" id="queryItemForm">
						<input type="hidden" name="msg" id="check_msg"/>
						<input class="input_width" type="hidden"
							value="${pagination.currentPage}" id="currentPage" /> <input
							class="input_width" type="hidden" value="${pagination.pageSize}"
							id="pageSize" />
						<table style="width: 100%">
							<tr>
								<td align="right">商品自编码：</td>
								<td><input class="input_width" type="text" name="outerId"
									id="outerId" value="${queryForm.outerId}" /></td>
								<td align="right">商品名称：</td>
								<td><input class="input_width" type="text"
									name="productName" id="productName"
									value="${queryForm.productName}" /></td>
								<td align="right">MC二次分类：</td>
								<td><input type="hidden" id="mcCategory"
									value="${queryForm.subMcCategory}"> <select
									id="subMcCategory" name="subMcCategory"
									style="width: 100px;">
										<option value="">--请选择--</option>
										<c:forEach var="submccategory" items="${submccategorys}">
											<option value="${submccategory}">${submccategory}</option>
										</c:forEach>
								</select></td>
								<td align="right">销售分级：</td>
								<td><input class="input_width" type="text" name="grades"
									id="grades" value="${queryForm.grades}" /></td>
							</tr>
							<tr>
								<td align="right">码洋区间：</td>
								<td><input class="input_width" type="text"
									name="basicPriceMin" id="basicPriceMin"
									value="${queryForm.basicPriceMin}" />~ <input
									class="input_width" type="text" name="basicPriceMax"
									id="basicPriceMax" value="${queryForm.basicPriceMax}" /></td>
								<td align="right">可用量：</td>
								<td><input class="input_width" type="text"
									name="availableQuantityMin" id="availableQuantityMin"
									value="${queryForm.availableQuantityMin}" />~ <input
									class="input_width" type="text" name="availableQuantityMax"
									id="availableQuantityMax"
									value="${queryForm.availableQuantityMax}" /></td>
								<td align="right">补货数量：</td>
								<td><input class="input_width" type="text"
									name="repQuantityMin" id="repQuantityMin"
									value="${queryForm.repQuantityMin}" />~ <input
									class="input_width" type="text" name="repQuantityMax"
									id="repQuantityMax" value="${queryForm.repQuantityMax}" /></td>
								<td align="right">发货仓库：</td>
								<td><input class="input_width" type="text" name="dc"
									id="dc" value="${queryForm.dc}" /></td>

							</tr>
							<tr>
								<td align="right">供应商：</td>
								<td><input class="input_width" type="text"
									name="productVendor" id="productVendor"
									value="${queryForm.productVendor}" /></td>
								<td align="right">创建时间：</td>
								<td><input class="input_width" type="text"
									name="createStartTime" id="createStartTime"
									value="${queryForm.createStartTime}" />~ <input
									class="input_width" type="text" name="createEndTime"
									id="createEndTime" value="${queryForm.createEndTime}" /></td>
								<td colspan="2" align="center"><input type="submit"
									value="检索" class="ui-button" /></td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 查询表单div end-->
				<!-- 列表start -->
				<div>
					<p>
						<input type="button" value="审核" id="examineItem" class="ui-button" />
						<input type="button" value="删除" id="deleteItem" class="ui-button" />
						<input type="button" value="导出报表" id="exportItem"
							class="ui-button" />
					</p>
					<table id="tablesorter" class="tablesorter list-table"
						style="width: 100%">
						<thead>
							<tr>
								<th width="11%" class="{sorter: false}">全选<input
									type="checkbox" id="select_checkbox" style="margin-right: 2px;" />
									反选<input type="checkbox" id="invert_select_checkbox"
									style="margin-right: 2px;" />
								</th>
								<th width="8%" style="color: #333;">商品自编码</th>
								<th width="15%" class="{sorter: false}">商品名称</th>
								<th width="7%">ISBN编码</th>
								<th class="{sorter: false}" width="5%">模型</th>
								<th width="3%">MC分类</th>
								<th width="5%">码洋</th>
								<th class="{sorter: false}" width="8%">出版社</th>
								<th width="8%">发货仓库</th>
								<th width="8%">销售分级</th>
								<th width="8%">预测销售</th>
								<th width="6%">可用量</th>
								<th width="8%">补货数量</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<c:forEach var="item" items="${items}">
								<tr id="tr_list_${item.id}" class="tr_list">
									<td><input type="checkbox" id="checkbox_${item.id}"
										class="checkbox_list" /></td>
									<td>${item.productSale.outerId}</td>
									<td>${item.productSale.product.name}</td>
									<td>${item.productSale.product.barcode}</td>
									<td>${item.model.name}</td>
									<td>${item.productSale.product.mcCategory}</td>
									<td>${item.productSale.product.listPrice}</td>
									<td>${item.productSale.product.manufacturer}</td>
									<td>${item.dc.name}</td>
									<td>${item.grade}</td>
									<td>${item.forecastQuantity}</td>
									<td>${item.availableQuantity}</td>
									<td id="td_${item.id}" class="td_click"
										onclick="Examine.tdClickEvent"><a
										href="javascript:void(0);">${item.replenishmentQuantity}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<!-- 列表end -->
				<c:if test="${not empty pagination}">
					<winxuan-page:page bodyStyle="javascript"
						pagination="${pagination}"></winxuan-page:page>
				</c:if>
			</div>
			<!-- 核心内容部分div end -->
		</div>
		<!-- 主体end -->

	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.validate.methods.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.tablesorter.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.tablesorter.pager.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/replenishment/examine.js"></script>
</body>
</html>