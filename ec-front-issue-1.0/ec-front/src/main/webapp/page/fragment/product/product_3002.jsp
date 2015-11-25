<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<c:if test="${config.available}">
	<c:set var="available" value="block" />
</c:if>
<c:if test="${!config.available}">
	<c:set var="available" value="none" />
</c:if>

<div class="productbanner" fragment="${fragment.id}" style="display:${available};" cachekey="${cacheKey}">
	<c:forEach var="content" items="${contentList}">
		<a href="${content.href}" title="${content.title}" target="_blank"><img
			alt="${content.name}" src="${content.src}"></a>
	</c:forEach>
</div>