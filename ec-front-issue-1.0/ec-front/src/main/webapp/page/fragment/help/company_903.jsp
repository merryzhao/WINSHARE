<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_903">
<div class="right_box margin10" fragment="${fragment.id}" cachekey="${cacheKey}">
<jsp:include page="../null.jsp"/>	
<c:forEach items="${contentList}" var="content" varStatus="status">
		${content.content }
</c:forEach>
			
</div>
</cache:fragmentPageCache>