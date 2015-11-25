<%@page pageEncoding="UTF-8"%> <%@include file="../../snippets/tags.jsp"%>
<c:if test="${config.available}">
	<c:set var="available" value="block" />
</c:if>
<c:if test="${!config.available}">
	<c:set var="available" value="none" />
</c:if>
<div class="site_notice" fragment="${fragment.id}" style="display:${available};" cachekey="${cacheKey}">
	<c:forEach var="content" items="${contentList}">
		${content.content}
	</c:forEach>
</div>
<!-- <div class="site_notice"><span>通知：</span>今天放假！</div> -->
