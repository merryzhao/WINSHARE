<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/winxuan-string" prefix="s" %>
<%@include file="../snippets/tags.jsp"%>

<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>搭配推荐管理</title>
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
}
</style>
<!-- 引入JS -->
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
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

				<h4>搭配推荐管理</h4>
				<!-- 查询表单div -->
				<div id="queryForm">
						<form:form class="inline" action="/bundle/list" method="post" commandName="form" id="bundleForm">
 						<table>
 						<tr>
 						<td>
 						<form:select path="codingType">
 							<form:option value="productId">商品编号</form:option>
 							<form:option value="outerId">自编码</form:option>
 						</form:select></td>
 						<td><form:textarea  style="width: 150px;height: 60px;"  path="codingContent"/>	</td>
 						<td> <input type="submit" value="检索"/></td>
 						</tr>
 						</table>
						</form:form>
				</div>
				<!-- 查询结果展示div -->
				<div>
					<c:if test="${pagination!=null}">
 						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<form action="">
						<table style="width:100%;" class="list-table">
							<c:if test="${pagination!=null}">
								<tr>
									<th>&nbsp;&nbsp;商品编码&nbsp;&nbsp;</th>
 									<th>商品名称</th>
 									<th>操作</th>
								</tr>
							</c:if>
							
   							<c:forEach var="productSale" items="${productSales}" varStatus="i">
								<tr>
 									<td>
										<a href="/bundle/${productSale.id}/edit">${productSale.id}</a>
									</td>
									<td align="left">
										<s:substr length="8" content="${productSale.product.name}"/>
									</td>
									<td><a href="/bundle/${productSale.id}/log">显示日志</a></td>
								</tr>
							</c:forEach>
 							<c:if test="${pagination!=null}">
 								<c:if test="${empty productSales&&pagination!=null}"><tr><td colspan="7">暂无数据</td></tr></c:if>
								<tr>
									<th>&nbsp;&nbsp;商品编码&nbsp;&nbsp;</th>
 									<th>商品名称</th>
 									<th>操作</th>
								</tr>
							</c:if>
						</table>
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