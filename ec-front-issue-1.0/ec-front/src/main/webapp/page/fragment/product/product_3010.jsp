<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<div class="slave-banner cf" fragment="${fragment.id}" cachekey="${param.cacheKey }">
            	<c:if test="${fn:length(contentList) <= 0}">
            	暂无内容
            	</c:if>
            	 	<c:forEach var="content" items="${contentList}" varStatus="status">
                    <div class="s1of3"><a href="${content.url}" target="_blank"><img src="${content.src}" alt="${content.name}"></a></div>
                    </c:forEach>            
                
        	</div>
        	
 