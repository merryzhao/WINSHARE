<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_70000">
<div class="right_box margin10" fragment="${fragment.id}" cachekey="${cacheKey}">
	<h1 class="join_title">校园招聘</h1>
	<c:forEach items="${contentList}" var="content" varStatus="status">
		${content.content }
	</c:forEach>
</div>
</cache:fragmentPageCache>