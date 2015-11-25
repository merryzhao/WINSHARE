<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="spe-services" fragment="${fragment.id}" cachekey="${cacheKey}" >
<jsp:include page="../null.jsp"/>
	<p class="spe_services">
		<c:forEach items="${contentList}" var="content" varStatus="status">
			<a   target="_blank"  href="${content.url }" title="${content.title}">${content.name }</a>
		</c:forEach>
	</p>
</div>