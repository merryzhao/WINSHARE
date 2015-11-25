<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<cache:fragmentPageCache idleSeconds="86400" key="${fragment.id}">
<div fragment="${fragment.id}" cachekey="${cacheKey}">
  	<c:forEach items="${contentList}" var="content" varStatus="status">
  		${content.content}
  	</c:forEach>
</div>
</cache:fragmentPageCache>