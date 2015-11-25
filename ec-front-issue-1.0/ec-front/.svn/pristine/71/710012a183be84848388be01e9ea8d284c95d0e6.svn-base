<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="tab" fragment="${fragment.id}" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp" />
	<div class="tab-currentline J-tab-currentline"><b></b></div>
	<c:forEach items="${contentList}" var="content" varStatus="i">
		<div class="tab-item <c:if test="${i.index==0}"> current </c:if> J-tab-trigger">
			<a href="${content.href }" title="${fn:escapeXml(content.title)}" target="_blank">${content.name }</a>
		</div>
	</c:forEach>
</div>