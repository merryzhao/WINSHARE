<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_3003">
<div class="temp_ads_index" id="temp_ads"  fragment="${fragment.id}" cachekey="${cacheKey}" available="${config.available}" style="display:none;">
<c:if test="${fn:length(contentList) <= 0}">
            	暂无内容
</c:if>
<c:forEach var="content" items="${contentList}" varStatus="status">
<a class="ads_image" href="${content.href}"  target="_blank">
	  <img	alt="${content.name}" src="${content.src}">
	</a>
</c:forEach>	
</div>
</cache:fragmentPageCache>