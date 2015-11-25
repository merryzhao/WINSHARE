<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<dd fragment="${fragment.id}" cachekey="${param.cacheKey}">
	<c:forEach items="${contentList}" var="content">
		<c:if test="${empty content.content}">
	  		暂无内容
	  	</c:if>
		${content.content}
	</c:forEach>
</dd>