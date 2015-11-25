<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<dl class="list list-dl-keywords" fragment="${fragment.id}" cachekey="${param.cacheKey }">
<jsp:include page="../null.jsp"/>
	<dt>${fragment.name}</dt>
	<c:forEach items="${contentList}" var="content">
		<dd><a href="${content.href}" title="${fn:escapeXml(content.title)}" target="_blank">${content.name}</a></dd>
	</c:forEach>
</dl>