<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>

<div fragment="${fragment.id }">
<jsp:include page="../null.jsp"/>
<p class="left_ads" >
   <c:forEach items="${contentList}" var="content" varStatus="status">
		<a title="${content.name }"   target="_blank"  href="${content.url }">
		<img data-lazy="false" 	src="${content.src}" width="200" alt="${content.name }" />
	   </a>
		</c:forEach>
</p>
</div>