<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="cont" fragment="${fragment.id}" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp"/>
	<c:forEach var="content" items="${contentList}">
		<div class="tab-cont J-tab-cont" style="display:block;">
			<a href="${content.href }" target="_blank" style="background-image:url(${content.src})"></a>
		</div>
	</c:forEach>
</div>


