<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<ul class="list list-base-3 cf" fragment="${fragment.id}" cachekey="${param.cacheKey }" style="display: none;"> 
            	<c:if test="${fn:length(contentList) <= 0}">
            	<li>
	            		暂无内容
	            	</li>
            	</c:if>
            	<c:forEach var="content" items="${contentList}" varStatus="status">
            	 <li>
                                <!-- cell-style-3 -->
                                <div class="cell cell-style-3">
                                    <div class="img"><a href="${content.url}" target="_blank"><img src="${content.imageUrl}" alt="${content.name}"></a></div>
                                    <div class="name"><a href="${content.url}" target="_blank">${content.name}</a></div>
                                    <div class="price-n"><b>￥${content.effectivePrice}</b><s>￥${content.product.listPrice}</s></div>
                                </div>
                                <!-- cell-style-3 end -->
                  </li>
                 </c:forEach>
</ul>
