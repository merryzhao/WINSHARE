<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>删除缓存</title>
<style type="text/css">
textarea {
	width: 120px;
	height: 50px;
	margin-bottom: -5px;
}
input{vertical-align:middle;margin:0px;}
</style>
<!-- 引入JS -->
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-system.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="tabs">
					<div id="result">
						<div>
	<form action="/cache/deleteObjectCache" method="post">
		<table>
			<tr>
				<td><label>删除对象缓存：</label></td>
				<td>对象包路径<input name="objectCacheKeyPrefix" type="text" alt="com.winxuan.ec.model.Product"></td>
				<td>对象数据库Id区间：<input name="minId" type="text">~<input name="maxId" type="text"></td>
				<td><input name="删除" value="删除" type="submit"></td>
			</tr>
		</table>
		</form>
	</div>
	<div>
	<form action="/cache/deleteFragmentCache" method="post">
		<table>
			<tr>
				<td><label>删除片段缓存：</label></td>
				<td>片段缓存key<input name="fragmentKey" type="text"></td>
				<td><input name="删除" value="删除" type="submit"></td>
			</tr>
		</table>
		</form>
	</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>