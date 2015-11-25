<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_${fragment.id}">
<div fragment="${fragment.id}" cachekey="${cacheKey}">
<h2>${contentList[0].title}</h2>
<ul>
    <c:forEach items="${contentList}" var="content" begin="0" varStatus="status">
     	<li <c:if test="${status.index % 2 == 1}">class="nmr"</c:if>><a href="###"><img src="${content.src}" alt="${content.title}" /></a></li>
    </c:forEach>
</ul>
</div>
</cache:fragmentPageCache>