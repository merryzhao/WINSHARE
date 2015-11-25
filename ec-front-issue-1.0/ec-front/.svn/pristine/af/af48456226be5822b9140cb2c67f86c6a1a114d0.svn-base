<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div class="special_offer" fragment="401">
	<jsp:include page="../null.jsp"/>
	<ul class="gallery_mode">
		<c:forEach items="${contentList}" var="content" varStatus="status"
			begin="0" end="2">
			<li>
				<p class="book_pic">
					<a title="<c:out value="${content.product.name }"/>"	target="_blank" href="${content.url }">
						<img src="${content.imageUrl }" alt="${content.product.name }">
					</a>
				</p> <strong><a	title="<c:out value="${content.product.name }"/>" target="_blank"	href="${content.url }" class="link4"><winxuan-string:substr length="16" content="${content.product.name}"/></a>
			</strong>
				<p class="s_price">
					<del>定价：￥${content.listPrice }</del>
					<br />套装价：<b class="red fb">￥${content.effectivePrice}</b>
				</p></li>
		</c:forEach>
	</ul>
	<dl class="list_mode">
		<c:forEach items="${contentList}" var="content" varStatus="status">
			<c:if test="${status.index==3 }">
				<dt class="dt">
					<div><a class="suitList" title="<c:out value="${content.product.name }"/>"	target="_blank" href="${content.url }">
					<img src="${content.imageUrl }" alt="${content.product.name }">
						</a>
						<p>
							<a target="_blank" href="${content.url }" class="link4"><winxuan-string:substr length="14" content="${content.product.name}"/></a>
						</p>
						<b class="fb red">￥${content.effectivePrice }</b>
						<del>￥${content.product.listPrice }</del>
					</div>
				</dt>
			</c:if>
			<c:if test="${status.index>3 }">
				<dd>
					<div><b class="red fb">￥${content.effectivePrice }</b> <a target="_blank"
						href="${content.url }" class="link4"><winxuan-string:substr length="14" content="${content.product.name }"/></a></div>
				</dd>
			</c:if>
		</c:forEach>
	</dl>
</div>