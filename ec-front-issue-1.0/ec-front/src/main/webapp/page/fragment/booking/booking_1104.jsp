<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!-- 列表(list) -->
<div fragment="${fragment.id}" cachekey="${param.cacheKey }">
<jsp:include page="../null.jsp"/>
<div class="title">
	<h3 class="title-h"><i class="iconfont">&#xe606;</i>${fragment.name}</h3>
</div>
<ul class="list list-base-4">
	<!-- 栏目标题(title) -->
	<!-- 栏目标题(title) end -->
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
</div>
<!-- 列表(list) end -->