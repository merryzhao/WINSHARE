<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>今日促销维护</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="index" name="type" />
</jsp:include>
</head>
<body>
<jsp:include page="/page/snippets/version2/header.jsp">
	<jsp:param value="home" name="label" />
	<jsp:param value="0" name="expandable" />
</jsp:include>
<div class="layout">
	<div class="left_box"></div>
	<div class="right_box">
		<div class="td_recom" fragment="${fragment.id}">
			<c:if test="${fn:length(contentList) <= 0}">
		            	暂无内容
			</c:if>
			<c:forEach items="${contentList}" var="content" varStatus="status">
				<a href="${content.url }" title="<c:out value="${content.title }"/>"><img src="${content.src }" alt="${content.title }" data-lazy="false"></a>
			</c:forEach>
		</div>
	</div>
</div>
</body>
</html>


