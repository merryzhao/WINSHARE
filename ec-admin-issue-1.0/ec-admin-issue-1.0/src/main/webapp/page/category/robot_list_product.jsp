
<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
<title>修改商品分类</title>
<style>
button.add { background:url("/css/zTreeStyle/img/add.jpg")  no-repeat scroll 1px 1px transparent;}
button.info { background:url("/css/zTreeStyle/img/info.png") no-repeat scroll 1px 1px transparent;}
button.move { background:url("/css/zTreeStyle/img/move.png") no-repeat scroll 1px 1px transparent;}
button.update { background:url("/css/zTreeStyle/img/sim/10.png") no-repeat scroll 1px 1px transparent;}
div.updatesort{
position:fixed;left:450px;float: left;border: 0px;background-color:#D9D9D9;display: none;
}
</style>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${contextPath}/js/jquery.ztree-2.6.min.js"></script>
	<script type="text/javascript" src="${contextPath}/js/category/robot_product.js"></script>
</head>
<body>
	<!-- 引入JS -->
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		
		<div class="frame-main">
			<div >
				<ul>
				  <li>注意：</li>
				  <li><font color='red'>在修改前，请先打开全部默认选中的节点</font></li>
				</ul>
			</div>
			<hr />
			
		<div id="updatesort" class="updatesort" >
		<input type="hidden" id = "productId" value="${productId}"/>
		<input type="hidden" id = "isUpdate" value="0"/>
		</div>
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content" style="float: left;">
			    <form:form commandName="proCaEditForm" name="proCaEditForm" id="proCaEditForm"
				action="${contextPath}/product/updateproductcagegory?pid=${productId}&sid=${productSaleId}" method="post">
				<token:token></token:token>
				<div id="categorysDiv"></div>
				<input type="button" onclick="updateCategoryA();" value="修改分类"/>
				</form:form><br/>
				所有分类:
				<ul id="category_tree" class="tree"></ul>
			</div>
			<div style="position:fixed;bottom:5px;right:10px;float: right;border: 0px;">
				<iframe id="category_description" name="category_description" frameborder=0 style="border-width:0px;border:0px;width: 600px;height: 500px;" src="" ></iframe> 
			</div>
			<div id="categoryMoveDiv">
				<ul id="categoryMoveDiv_tree" class="tree"></ul>
				<br />
				<input name="selectTargetId" type="hidden"/>
			</div>
	   </div>
	</div>
</body>
</html>