<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<div class="temp_ads_index" id="temp_ads"  fragment="${fragment.id}" cachekey="${param.cacheKey}" available="${config.available}" style="display:none;">
	<c:if test="${fn:length(contentList) <= 0}">
	            	暂无内容
	</c:if>
	<c:forEach var="content" items="${contentList}" varStatus="status">
		<a class="ads_image" title="${content.name}" href="${content.href}" style="background-image: url(${content.src})"  target="_blank"></a>
	</c:forEach>	
</div>
