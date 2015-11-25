<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan-page"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>批量订单登记到款</title>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<form action="/order/gotoBatchPayList" target="batchPay" method="post" enctype="multipart/form-data">
				<label>文件:</label>
				<input name="file" id="file" type="file" />
				<input type="submit" value="数据导入" onclick="return document.getElementById('file').value != '';"/>
			</form>
			<iframe name="batchPay" style="border:0px;width: 100%;height: 500px;"></iframe>
		</div>
	</div>
</body>
</html>