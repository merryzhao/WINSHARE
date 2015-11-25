<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_70005">
<div fragment="${fragment.id}" cachekey="${cacheKey}">
<h2>加入我们</h2>
<c:forEach items="${contentList}" var="content" varStatus="status">
	${content.content }
</c:forEach>
</div>
</cache:fragmentPageCache>