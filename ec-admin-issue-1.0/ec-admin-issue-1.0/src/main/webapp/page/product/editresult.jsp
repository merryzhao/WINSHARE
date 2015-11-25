<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.cl-table{width:100%;border:1px solid #DFDFDF}
table.cl-table tr{height:30px;line-height:30px;border:1px solid #DFDFDF;}
table.cl-table tr.hover{background:#ffffe1}
table.cl-table .odd{background:#FCFCFC}
table.cl-table .even{background:#F9F9F9}
</style>
<%@include file="../snippets/meta.jsp"%>
<title>套装书修改结果</title>
</head>
<body>
	<!-- 引入JS -->
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="content-result">
				<div align="center">
					<h4><c:if test="${empty message }">套装书修改成功!</c:if>
					<br /> <a href="/product/${complexId}/editcomplex">点击修改</a>
					<br /> <a href="/product/complex">新建套装书</a>
					<c:if test="${not empty message }"><span style="color: red;">套装书修改失败</span><input type="button" onclick="window.history.go(-1)" value="返回修改"/></c:if></h4>
				</div>
			</div>
		</div>
		</div>
	</div>
		<%@include file="../snippets/scripts.jsp"%>
</body>
</html>