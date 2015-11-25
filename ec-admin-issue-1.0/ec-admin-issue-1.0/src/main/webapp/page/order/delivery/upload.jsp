<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>订单查询</title>
<%@include file="../../snippets/meta.jsp"%>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<form action="/orderDelivery/batchDelivery/upload" method="post" enctype="multipart/form-data">
				<label>Excel文件(版本2003):</label><input name="file" id="file" type="file" />
				<input type="submit" value="批量发货" onclick="return document.getElementById('file').value != '';"/>
			</form>
		</div>
	</div>
</body>
</html>