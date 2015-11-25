<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="sg-imglink" fragment="${fragment.id }" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp"/>
	<c:forEach var="content" items="${contentList }" varStatus="i">
		<a href="${content.href}" target="_blank">
			<img src="${content.src}" alt="${content.title}">
		</a>
	</c:forEach>
</div>
 