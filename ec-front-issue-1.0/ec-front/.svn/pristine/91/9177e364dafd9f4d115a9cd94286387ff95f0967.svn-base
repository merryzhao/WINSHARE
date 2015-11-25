<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<%--已使用index_231片段 --%>
<li fragment="${fragment.id }">
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content" varStatus="status" end="0" >
	<h3><a   target="_blank"  href="${content.product.category.id }" title="${content.product.category.name }">${fragment.name }</a></h3>
    <div class="have_pro">
        <p><c:if test="${content.hasPromotion }"><img src="images/ads/pro_icon1.png"></c:if></p>
        <a title="${content.name}"   target="_blank"  href="${content.url}"><img class="book_img" alt="${content.name}" src="${content.imageUrl}" ></a></div>
    <h4><a title="${content.name}"   target="_blank"  href="${content.url}">${content.name}</a></h4>
      <b class="red fb">￥${content.effectivePrice}</b> <b class="l_gray">(<fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0"></fmt:formatNumber>折)</b></b>
	</c:forEach>
    <p class="margin_t20 txt_left">
    <c:forEach items="${contentList}" var="content" varStatus="status" begin="1" >
    <a title="${content.name }"   target="_blank"  href="${content.url }"><winxuan-string:substr length="10" content="${content.name}"></winxuan-string:substr></a>
        </c:forEach></p>
</li>