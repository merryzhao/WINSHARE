<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<html>
<head>
</head>
<body>
		<c:if test="${flag}">
			<script>
				parent.fail("${errorMessage}");
			</script>
			
		</c:if>
		<c:if test="${!flag}">
			<script>
				parent.success();
			</script>
			<br />【<a href="/promotion/list">去查询促销</a>】
		</c:if>
</body>
</html>