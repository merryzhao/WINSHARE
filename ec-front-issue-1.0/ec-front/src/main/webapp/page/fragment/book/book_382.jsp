<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<div class="new_works" fragment="${fragment.id}">
<jsp:include page="../null.jsp"/>
<c:forEach items="${contentList}" var="content" varStatus="status">
	<a class="book_img" title="<c:out value='${content.product.name}'/>" target="_blank" href="${content.url}"><img src="${content.imageUrl}" alt="${content.product.name}"></a>
	<h3>
		<a class="fb f14 link1" target="_blank"  href="${content.url}" title="<c:out value="${content.product.name }"/>">${content.product.name }</a>
	</h3>
	<div class="margin10">
		<div class="com_star fl">
			<b style="width:${content.productTransient.avgRank*20}%;"></b>
		</div>
		（<a class="link4"   target="_blank"  href="javascript:;">${content.productTransient.rankCount }人评价</a>）
	</div>
	<p>
	<b class="fb red f14">￥${content.effectivePrice}</b>
		<b class="l_gray">(<fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0"></fmt:formatNumber>折)</b>
	</p>
	<div class="description">
		<c:forEach items="${content.product.descriptionList}" var="description" varStatus="status" end="0">
		${description.content }
		</c:forEach>
	</div>
</c:forEach>
</div>