<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="sg-classifyrecommend-keywords" fragment="${fragment.id}" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp" />
	<c:forEach items="${contentList}" var="content" varStatus="i">
		<a href="${content.href }" title="${fn:escapeXml(content.title)}" target="_blank">${content.name }</a>
		<c:if test="${fn:length(contentList)!=i.index+1}">
			<b></b>
		</c:if>
	</c:forEach>
</div>