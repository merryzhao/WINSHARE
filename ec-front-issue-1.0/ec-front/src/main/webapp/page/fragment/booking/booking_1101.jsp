<%@page pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div class="mod-heavy-recommend" fragment="${fragment.id}" cachekey="${param.cacheKey }">
<!-- Tab栏目(col-tab-3) -->
<div class="col col-base-1 col-tab-3 J-tab">
	<!-- 栏目标题(title) -->
	<div class="title">
		<h3 class="title-h"><i class="iconfont">&#xe60b;</i>${fragment.name}</h3>
	</div>

	<!-- 栏目标题(title) end -->
	<!-- 栏目主体(cont) -->
		<div class="cont">
		 	<c:forEach var="content" items="${contentList}"  varStatus="i">
				<div class="tab-cont J-tab-cont" <c:if test="${i.index==1}">style="display: block;"</c:if> <c:if test="${i.index==1}">style="display: none;"</c:if> >
					<!-- 商品模块(cell-important-1) -->
				<div class="cell cell-important-1">
					<div class="img"><a href="${content.url }" target="_blank"><img src="${content.product.imageUrlFor350px }" alt="${content.name }"></a></div>		
					<div class="name"><a href="${content.url }" target="_blank" title="">${content.name }</a></div>
					<div class="info">${content.subheading}</div>
					<div class="price"><span class="price-n">￥${content.effectivePrice }</span><span class="price-o">￥${content.listPrice}</span></div>
					<div class="active"><a class="btn btn-buystrong" href="${content.url}" data-id="${content.id}" id="addtocart" bind="addToCart" target="_blank" title="加入购物车"><i class="iconfont">&#xe60e;</i>加入购物车</a></div>
				</div>
				<!-- 商品模块(cell-important-1) end -->
				</div>
			</c:forEach>
	   </div>
	<!-- 栏目主体(cont) end -->

	<!-- Tab切换触发器1(tab) -->
	<div class="tab tab-1">
		<c:forEach var="content" items="${contentList}"  varStatus="i">
			<c:if test="${i.index < 6 }">
				<div class="tab-item J-tab-trigger <c:if test="${i.index==0}">current</c:if>">
					<!-- 商品模块(cell-base-1) -->
					<div class="cell cell-base-1">
						<div class="img"><a href="${content.url }" target="_blank"><img src="${content.product.imageUrlFor160px }" alt="${content.name }"></a></div>
						<div class="name"><a href="${content.url }" target="_blank" title="">${content.name }</a></div>
						<%-- <div class="info">${content.subheading}</div> --%>
						<div class="price"><span class="price-n">￥${content.effectivePrice }</span><span class="price-o">￥${content.listPrice}</span></div>		
					</div>
					<!-- 商品模块(cell-base-1) end -->
				</div>
			</c:if>
		</c:forEach>
	</div>
	<!-- Tab切换触发器1(tab) end -->
	</div>
<!-- Tab栏目(col-tab) end -->
</div>

