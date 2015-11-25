<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<ul class="list list-dazzle" fragment="${fragment.id}" cachekey="${param.cacheKey }">
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content" varStatus="i">
		<li><a href="${content.href}" target="_blank" class="link link-classify" title="${fn:escapeXml(content.title)}"><strong>${content.name}</strong>${content.title}</a></li>
	</c:forEach>
</ul>