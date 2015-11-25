<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="list sg-listtext" fragment="${fragment.id }" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp"/>
	<c:forEach var="content" items="${contentList }" varStatus="i">
		<li>
			<a href="${content.href}" title="${content.title}" target="_blank">${content.title}</a>
		</li>
	</c:forEach>
</ul>
 