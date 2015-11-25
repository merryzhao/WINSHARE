<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!-- 其他书籍(attach) -->
<div class="attach" fragment="${fragment.id}" cachekey="${param.cacheKey }">
<jsp:include page="../null.jsp"/>
	<!-- 列表(list) -->
	<ul class="list list-base-3">
		<c:forEach var="content" items="${contentList }" varStatus="i">
			<li>
				<!-- 商品模块(cell-base-2) -->
				<div class="cell cell-base-2">
					<div class="img"><a href="${content.url }" target="_blank"><img src="${content.product.imageUrlFor200px }" alt="${content.name }"></a></div>
					<div class="name"><a href="${content.url }" target="_blank" title="">${content.name }</a></div>
					<div class="price"><span class="price-n">￥${content.effectivePrice }</span><span class="price-o">￥${content.listPrice }</span></div>	
				</div>
				<!-- 商品模块(cell-base-2) end -->
			</li>
		</c:forEach>
	</ul>
	<!-- 列表(list) end -->
</div>
<!-- 其他书籍(attach) end -->