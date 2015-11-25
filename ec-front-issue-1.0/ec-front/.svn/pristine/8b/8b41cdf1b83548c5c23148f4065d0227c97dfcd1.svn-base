<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<jsp:include page="../null.jsp"/>
<div class="tab" fragment="${fragment.id }" cachekey="${param.cacheKey }">
<c:forEach var="content" items="${contentList }" varStatus="i">
	<div class="tab-item J-tab-trigger <c:if test="${i.index==0 }">current</c:if>"><i></i>${content.name}</div>
</c:forEach>
</div>
