<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<%--已使用index_162片段 --%>
<div content="2f" style="display:none;">
<ul class="book_list"  fragment="${fragment.id }" >
<jsp:include page="../null.jsp"/>
		<c:forEach items="${contentList}" var="content" varStatus="status">
            <li>
                <div class="have_pro">
                    <p><c:if test="${content.hasPromotion}"><img src="images/ads/pro_icon1.png"></c:if></p>				
                    <a title="${content.name}"   target="_blank"  href="${content.url}"><img class="book_img" alt="${content.name}" src="${content.imageUrl}" ></a></div>
                <h4><a title="${content.name}"   target="_blank"  href="${content.url}"><winxuan-string:substr length="20" content="${content.name}"/></a></h4>
                <b class="red fb">￥${content.effectivePrice}</b> <del>￥${content.product.listPrice}</del> </li>
        </c:forEach>
</ul>
</div>
