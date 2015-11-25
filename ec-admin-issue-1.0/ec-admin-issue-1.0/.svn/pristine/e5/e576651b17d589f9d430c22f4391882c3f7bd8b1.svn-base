<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<html>
<head>
<title>限制补货</title>
<%@include file="../snippets/meta.jsp"%>
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
				<h4>限制补货</h4>
				<div style="color: red" id="message">
					<c:if test="${msg == 'success'}">
							操作成功！<br />
					</c:if>
					
					<c:if test="${msg == 'error'}">
							操作失败！${errMsg}
						<br />
					</c:if>
				</div>
				<div>
					<div>
						<p>说明:</p>
						<ul>
							<li>1. 格式只能使用excel2003兼容格式, 即后缀是xls的excel文件</li>
							<li>2. 样例模板<a
								href="http://console.winxuan.com/excel/replenishment/productfreeze.xls">下载</a></li>
							<li></li>
							<li></li>
						</ul>
					</div>
					<div id="uploadPanel">
						<form action="/replenishment/productFreezeExcelImport"
							method="post" enctype="multipart/form-data" id="uploadForm">
							<table>
								<tr>
									<td>文件:<input type="file" name="file" class="ui-button"></td>
									<td><input type="submit" value="提交" id="uploadbtn"
										class="ui-button" /></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
				<!-- 列表start -->
				<div style="width: 60%;">

					<table id="tablesorter" class="tablesorter list-table"
						style="width: 60%;">
						<tr>
							<td colspan="2" >
								<form action="/replenishment/showMrProductFreeze" method="post"
									name="queryForm" id="queryItemForm">
									<input class="input_width" type="hidden"
										value="${pagination.currentPage}" id="currentPage" /> <input
										class="input_width" type="hidden"
										value="${pagination.pageSize}" id="pageSize" />
									<table style="width: 100%">
										<tr>
											<td align="right">EC编码：</td>
											<td align="left"><input class="input_width" type="text"
												name="searchProductSaleId" id="searchProductSaleId" value="${searchProductSaleId}" /></td>
											<td colspan="2" align="center"><input type="submit"
												value="检索" class="ui-button" /></td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
						<tr>
							<td charoff="2" align="left">
								<input type="button" value="添加" id="addLimit" class="ui-button" />
								<input type="button" value="删除" id="deleteLimit" class="ui-button" />
							</td>
						</tr>
						<tr> 
							<th>
								全选<input type="checkbox" id="select_checkbox" style="margin-right: 2px;" />
								反选<input type="checkbox" id="invert_select_checkbox" style="margin-right: 2px;"/>
							</th>
							<th>EC编码</th>
						</tr>
						<tbody>
							<c:forEach var="freeze" items="${freezes}">
								<tr id="tr_list_${freeze.id}" class="js-tr-list">
									<td><input type="checkbox" id="checkbox_${freeze.id}"
										class="checkbox_list" /></td>
									<td>${freeze.productSale.id}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<!-- 列表end -->
				<div style="width: 45%;">
					<c:if test="${not empty pagination}">
						<winxuan-page:page bodyStyle="javascript"
							pagination="${pagination}"></winxuan-page:page>
					</c:if>
				</div>
			</div>
			<!-- 核心内容部分div end -->
		</div>
		<!-- 主体end -->
		<!-- 新增和修改界面start -->
		<div id="add_limit" style="display: none; height: 100%; width: 100%;">
			<form id="form" action="/replenishment/saveOrUpdateFreeze"
				method="post">
				<table style="width: 100%; height: 100%; border-collapse: collapse;">
					<tr height="30">
						<td align="right"><strong style="font: 16px;">EC编码：</strong></td>
						<td><input type="text" name="productSaleId" id="productSaleId"
							style="height: 25px;" /></td>
					</tr>
					
					<tr height="30">
						<td colspan="2" height="30" align="center"><input
							type="hidden" id="id" name="id" /> <input style="width: 50px"
							type="submit" value="完成" id="saveButton" /></td>
					</tr>
				</table>
			</form>
		</div>

	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.validate.methods.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/replenishment/limit.js"></script>
</body>
</html>