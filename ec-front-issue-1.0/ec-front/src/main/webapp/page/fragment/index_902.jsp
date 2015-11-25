<%@page pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp" %>
<cache:fragmentPageCache idleSeconds="86400"	key="FRAGMENT_ID_902">
<div class="footer ebook_footer" fragment="${fragment.id}" cachekey="${cacheKey}">
<jsp:include page="null.jsp"/>
	<c:forEach items="${contentList}" var="content" varStatus="status">
		${content.content }
	</c:forEach>
  </div>
</cache:fragmentPageCache>