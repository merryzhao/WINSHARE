<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!-- 广告模块(ad-base-1) -->
<div class="mod mod-ad-row" fragment="${fragment.id}" cachekey="${param.cacheKey }">
<jsp:include page="../null.jsp"/>
	<div class="ad ad-base-1">
		<c:forEach var="content" items="${contentList }" varStatus="i">
			<c:if test="${i.index < 1 }">
				<a href="${content.url }" target="_blank" title="${content.title }"><img src="${content.src }" alt="${content.name }"></a>
			</c:if>
		</c:forEach>
	</div>
</div>
<!-- 广告模块(ad-base-1) end -->
