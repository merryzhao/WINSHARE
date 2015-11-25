<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<span class="grid-1210w" fragment="${fragment.id}" cachekey="${param.cacheKey }">
	<c:forEach var="content" items="${contentList }" varStatus="i">
		<img class="imgtitle" style="display:block;" src="${content.src }">
	</c:forEach>
</span>