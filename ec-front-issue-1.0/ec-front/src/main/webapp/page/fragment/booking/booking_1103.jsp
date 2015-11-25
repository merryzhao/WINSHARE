<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!-- 栏目标题(title) -->
<!-- Tab栏目(col-tab-2) -->
<div class="col col-base-1 col-slider-1 J-slider" fragment="${fragment.id}" cachekey="${param.cacheKey }">
<jsp:include page="../null.jsp"/>
<div class="title">
	<h3 class="title-h"><i class="iconfont">&#xe608;</i>${fragment.name }</h3>
</div>
<div class="cont">

	<!-- 栏目标题(title) end -->
			<c:forEach var="content" items="${contentList }" varStatus="i">
				<c:if test="${i.index%5==0||i.index==0}">
				 <div class="tab-cont J-slider-cont" <c:if test="${i.index!=0}">style="display: none;"</c:if>>
					<ul class="list list-base-3">
				</c:if>
					<!-- 列表(list) -->
							<li>
								<!-- 商品模块(cell-base-2) -->
								<div class="cell cell-base-2">
									<div class="img"><a href="${content.url }" target="_blank"><img src="${content.product.imageUrlFor160px }" alt=""></a></div>
									<div class="name"><a href="${content.url }" target="_blank" title="">${content.name }</a></div>
									<div class="price"><span class="price-n">￥${content.effectivePrice }</span><span class="price-o">￥${content.listPrice }</span></div>	
								</div>
								<!-- 商品模块(cell-base-2) end -->
							</li>
					<!-- 列表(list) end -->
				<c:if test="${(i.index+1)%5==0||fn:length(contentList)==i.index+1}">
					</ul>
				 </div>
				</c:if>
			</c:forEach>
</div>
<!-- 附加内容区域(attach) -->
	<div class="attach">
		<a href="javascript:;" class="slider-prev iconfont J-slider-prev">&#xe602;</a>
		<a href="javascript:;" class="slider-next iconfont J-slider-next">&#xe601;</a>
	</div>
	<!-- 附加内容区域(attach) end -->

</div>
<!-- Tab栏目(col-tab) end -->
