<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<html>
<head>
<title>MC分类导入</title>
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
#uploadPanel{
	margin:20px 0;
}
#uploadbtn{
	padding: 2px 10px;
}
</style>
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
				<h4>分类导入</h4>
				<div>
					<div>
						<p>说明:</p>
						<ul>
							<li>1. 格式只能使用excel2003兼容格式, 即后缀是xls的excel文件</li>
							<li>2. 从第一行默认开始统计</li>
							<li>3. 样例模板<a href="http://console.winxuan.com/excel/replenishment/mrmctype.xls">下载</a>。</li>
							<li></li>
							<li></li>
						</ul>
					</div>
					<div id="uploadPanel">
						<form action="/replenishment/mcTypeExcelImport" method="post" enctype="multipart/form-data" id="uploadForm">
							<table>
								<tr>
									<td>文件:<input type="file" name="file"></td>
									<td><input type="submit" value="提交" id="uploadbtn"/></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
				<div style="color: red">
					<p>
						<c:if test="${msg == 'success'}">
							导入成功!
						</c:if>
						<c:if test="${msg == 'error'}">
							操作失败!
						</c:if>
					</p>
				</div>
				<div>
					<table class="list-table" style="width:60%">
						<tr>
							<th>MC分类</th>
							<th>类型</th>
							<th>基础数量(定位表)</th>
							<th>价格上界(定位表)</th>
							<th>价格下界(定位表)</th>
							<th>比率</th>
							<th>默认分配(定位表)</th>
						</tr>
						<c:forEach var="typeItem" items="${mrMcTypes }">
							<tr>
								<td>${typeItem.mcCategory.id }</td>
								<td>${typeItem.type.name }</td>
								<td>${typeItem.quantity }</td>
								<td>${typeItem.boundTop }</td>
								<td>${typeItem.boundBottom }</td>
								<td>${typeItem.ratio }</td>
								<td>${typeItem.defaultQuantity }</td>
							</tr>	
						</c:forEach>
					</table>
					<c:if test="${pagination.count>0}">
						<div style="width:60%">
							<winxuan:page bodyStyle="javascript" pagination="${pagination}"></winxuan:page>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.methods.min.js"></script>
</body>
</html>