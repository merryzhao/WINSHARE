<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<div class="special_offer"  content="special" fragment="415" style="display:none;">
<jsp:include page="../null.jsp"/>
	<ul class="gallery_mode">
		<c:forEach items="${contentList}" var="content" varStatus="status"
			begin="0" end="2">
			<li>
				<p class="book_pic">
					<a title="<c:out value="${content.product.name }"/>" target="_blank"  href="${content.url }">
					<img src="${content.imageUrl }"　alt="${content.product.name }"> </a>
				</p> <strong>
						<a　title="<c:out value="${content.product.name }"/>"　target="_blank"  href="${content.url }" class="link4">${content.product.name }</a> 
					</strong>
				<p class="s_price">
					<del>定价：￥${content.listPrice }</del>
					<br/>特价：<b class="red fb">￥${content.effectivePrice}</b>(<fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0" ></fmt:formatNumber>折)
				</p></li>
		</c:forEach>
	</ul>
	<dl class="list_mode">
		<c:forEach items="${contentList}" var="content" varStatus="status">
			<c:if test="${status.index==3 }">
				<dt>
					<a class="specialList" title="<c:out value="${content.product.name }"/>"　target="_blank"  href="${content.url }"><img src="${content.imageUrl }"　alt="${content.product.name }"> </a>
					<p>
						<b class="red fb"><fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0" ></fmt:formatNumber>折</b><a target="_blank" href="${content.url }" class="link4">${content.product.name}</a>
					</p>
				</dt>
			</c:if>
			<c:if test="${status.index>3 }">
				<dd>
					<b class="red fb"><fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0" ></fmt:formatNumber>折</b><a   target="_blank"  href="${content.url }" class="link4">${content.product.name}</a>
				</dd>
			</c:if>
		</c:forEach>
	</dl>

</div>