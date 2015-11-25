<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_70006">
<div class="right_box margin10" fragment="${fragment.id}" cachekey="${cacheKey}">
	<h1 class="join_title">部门列表</h1>
  	<ul class="depart_list" >
  	<c:forEach items="${contentList}" var="content" varStatus="status">
  		<li><a href="${content.href }" title="${content.title }"><img src="${content.src}" alt="${content.title }" /></a></li>
  	</c:forEach>
  	</ul>
</div>
</cache:fragmentPageCache>