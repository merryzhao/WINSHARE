<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<div class="master-search-keywords fl" fragment="${fragment.id}" cachekey="${param.cacheKey }">
            	热门搜索：
            	<c:if test="${fn:length(contentList) <= 0}">
            	暂无内容
            	</c:if>
            	
            	 	<c:forEach var="content" items="${contentList}" varStatus="status">
                    <a href="${content.url}" target="_blank">${content.name}</a>
                    </c:forEach>            
                
 </div>
 