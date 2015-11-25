<%@page pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp" %>
<div class="clearfloat"></div>
<footer class="foot"  fragment="${fragment.id}" cachekey="${cacheKey}">
<jsp:include page="null.jsp"/>
	<c:forEach items="${contentList}" var="content" varStatus="status">
		${content.content }
	</c:forEach>
   </footer>
