
<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
<title>分类管理</title>
<style>
button.add { background:url("/css/zTreeStyle/img/add.jpg")  no-repeat scroll 1px 1px transparent;}
</style>
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
				<div>
					<ul>
						<li>1、分类前方的复选框代表启用状态，钩中为启用，未钩中为不启用。</li>
						<li>2、修改分类名称时如果名称跟别名相同则只填写名称即可，如果不同则如下格式修改：<font color=red >名称*别名</font>。</li>
						<li>3、<font color=red >双击分类名称</font>可以查看该分类的数据描述。</li>
					</ul>
				</div>
				
				<hr/>所有分类
				<ul id="category_tree" class="tree"></ul>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/tree/category_tree_manage.js"></script>
</body>
</html>