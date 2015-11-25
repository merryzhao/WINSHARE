<%@page pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<!-- 列表(list) -->
<ul class="list list-base-2" fragment="${fragment.id}" cachekey="${param.cacheKey }" style="display:none;">
<c:if test="${empty contentList}">
	<li>暂无内容.</li>
</c:if>
<c:forEach var="content" items="${contentList}" varStatus="i">
	<li>
		<!-- 商品模块(cell-base-2) -->
		<div class="cell cell-base-2">
			<div class="img"><a href="${content.url}" target="_blank"><img src="${content.product.imageUrlFor200px}" alt="${content.name}"></a></div>
			<div class="name"><a href="${content.url}" target="_blank" title="">${content.name}</a></div>
			<div class="price"><span class="price-n">￥${content.effectivePrice }</span><span class="price-o">￥${content.listPrice}</span></div>	
		</div>
		<!-- 商品模块(cell-base-2) end -->
	</li>
</c:forEach>
</ul>
<!-- 列表(list) end -->