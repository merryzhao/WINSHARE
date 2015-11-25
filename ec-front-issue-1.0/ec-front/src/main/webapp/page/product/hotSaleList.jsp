<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<c:if test="${!empty hotSaleList }">
	<div class="left_container" cache="${cacheKey}">
		<h4 class="fb">同类热销产品</h4>
		<c:forEach var="hotSale" items="${hotSaleList}">
			<dl class="also_viewed">
				<dt>
					<a class="hotSaleList" href="${hotSale.product.url}" title="${hotSale.effectiveName}"
						target="_blank"><img alt="${hotSale.effectiveName }"
						src="${hotSale.product.imageUrlFor55px }">
					</a><a class="link4" href="${hotSale.product.url }"
						title="${hotSale.effectiveName}" target="_blank">
							<c:if test="${hotSale.storageType.id==6004}">
             										<div class="ebook_mini_ico"></div>
										</c:if>
						<winxuan-string:substr
							length="17" content="${hotSale.sellName }"></winxuan-string:substr>
					</a><br> 文轩价：<span class="fb red">￥${hotSale.salePrice}</span>
				</dt>
			</dl>
		</c:forEach>
	</div>
</c:if>