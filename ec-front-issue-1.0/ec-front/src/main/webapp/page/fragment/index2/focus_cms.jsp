<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<ul class="title_list" fragment="${fragment.id }" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content">
		<li><a target="_blank" href="${content.href}" target="_blank" title="${fn:escapeXml(content.title)}">${content.name}</a>
	</li>
	</c:forEach>
</ul>