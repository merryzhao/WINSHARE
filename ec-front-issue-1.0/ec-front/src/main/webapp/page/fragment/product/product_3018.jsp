<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="tab cf" fragment="${fragment.id}" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp" />
	<c:forEach items="${contentList}" var="content" varStatus="status">
		<div class="tab-item <c:if test="${status.index==0}"> current </c:if>"><b></b> ${content.name}</div>
	</c:forEach>
</div>
