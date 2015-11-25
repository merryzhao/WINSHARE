<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<li fragment="${fragment.id}">
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content" varStatus="status" end="0" >
	<h3><a     href="http://list.winxuan.com/${content.product.category.id }" title="${fragment.name }">${fragment.name }</a></h3>
    <div class="have_pro">
        <p><c:if test="${content.hasPromotion }"><img src="images/ads/pro_icon1.png"></c:if></p>
        <a title="${content.name}"     href="${content.url}"><img class="book_img" alt="${content.name}" src="${content.imageUrl}" ></a></div>
    <h4><a title="${content.name}"     href="${content.url}">${content.name}</a></h4>
    <b class="red fb">￥${content.effectivePrice}</b> <b class="l_gray">(<fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0"></fmt:formatNumber>)折</b>
	</c:forEach>
    <p class="margin_t20 txt_left">
    <c:forEach items="${contentList}" var="content" varStatus="status" begin="1" >
    <a title="${content.name }"   href="${content.url }"><b class="red"><fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0"></fmt:formatNumber>折</b>${content.name}</a>
        </c:forEach></p>
</li>