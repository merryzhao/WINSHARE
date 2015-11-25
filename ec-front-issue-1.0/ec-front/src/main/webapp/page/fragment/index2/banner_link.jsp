<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="tab" fragment="${fragment.id}" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp"/>
	<c:forEach var="content" items="${contentList}">
		<div class="tab-item J-tab-trigger">
			<a href="${content.href }" target="_blank">
			<img src="${content.src }" alt=""><span></span></a>
		</div>
	</c:forEach>
</div>