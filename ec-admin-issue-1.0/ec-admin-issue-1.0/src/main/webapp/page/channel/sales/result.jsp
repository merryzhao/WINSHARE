<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>渠道销售</title>
<%@include file="../../snippets/meta.jsp"%>
<body>
    <!-- 引入JS -->
	<div class="frame">
	     <!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		 <!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<H2>${message }</H2>
		</div>
	</div>
</body>
</html>