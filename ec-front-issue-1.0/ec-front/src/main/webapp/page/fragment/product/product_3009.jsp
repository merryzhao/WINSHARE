<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<div class="user-hot" fragment="${fragment.id}" cachekey="${param.cacheKey}">
            	<h6>热门活动</h6>
            	<c:if test="${fn:length(contentList) <= 0}">
            	暂无内容
            	</c:if>
            	 <ul class="user-hot-list">
            	 	<c:forEach var="content" items="${contentList}" varStatus="status">
                    <li><a href="${content.url}" target="_blank"><b>${content.name}</b></a></li>
                    </c:forEach>            
                </ul>
        	</div>
        	
        	
        	

                                
                             