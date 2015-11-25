<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="title" fragment="${fragment.id }" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content">
		<a href="${content.href}"  target="_blank" title="${fn:escapeXml(content.title)}">
			<img src="${content.src}" alt="${fn:escapeXml(content.name)}">
		</a>
	</c:forEach>
</div>
