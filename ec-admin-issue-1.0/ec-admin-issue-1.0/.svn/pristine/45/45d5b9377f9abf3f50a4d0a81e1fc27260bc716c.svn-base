<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>分发礼券</title>
</head>
<body>
<c:forEach items="${list }" var="string">
</c:forEach>
<%@include file="../snippets/scripts.jsp"%>
<script>
	var jsonobject=
	{
			textareaString:'${string}',
	};
		parent.addTxt(jsonobject);
	</script>
</body>
</html>