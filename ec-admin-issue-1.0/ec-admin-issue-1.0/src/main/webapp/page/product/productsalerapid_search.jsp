<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/scripts.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>查询快速分拨商品</title>

<link type="text/css" href="../../css/redmond/jquery-ui-1.8.14.custom.css" rel="stylesheet" />
<link type="text/css" href="../../css/global.css" rel="stylesheet" />
<script type="text/javascript" src="../../js/product/productsalerapid.js"></script>
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
		<h3>根据商品ID查询分拨采购单</h3>
		<div><font color="red">${message }</font></div>
		<form action="/productsalerapid/search" method="POST" id="search">
			<div align="left">
				商品ID:<input type="text" name="productSaleId"/>
			</div>
			<input type="submit" value="提交" onclick="return rapid.searchSubmit()" />
		</form>
	</div>
</div>
</div>
</body>
</html>