<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<div class="unit slave-nav" fragment="${fragment.id}" cachekey="${param.cacheKey }">
            	<c:if test="${fn:length(contentList) <= 0}">
            	暂无内容
            	</c:if>
            	 <ul class="list slave-nav-list cf">
            	 	<c:forEach var="content" items="${contentList}" varStatus="status">
                    <li><a href="${content.url}" target="_blank">${content.name}</a></li>
                    </c:forEach>            
                </ul>
</div>