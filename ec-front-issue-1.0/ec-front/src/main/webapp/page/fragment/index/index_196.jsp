<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<div fragment="${fragment.id }" content="4f">
<jsp:include page="../null.jsp"/>
<ul class="book_list"  >
		<c:forEach items="${contentList}" var="content" varStatus="status">
            <li>
                <div class="have_pro">
                    <p><c:if test="${content.hasPromotion}"><img src="images/ads/pro_icon1.png"></c:if></p>				
                    <a title="${content.name}"   target="_blank"  href="${content.url}"><img class="book_img" alt="${content.name}" src="${content.imageUrl}" ></a></div>
                <h4><a title="${content.name}"   target="_blank"  href="${content.url}"><winxuan-string:substr length="20" content="${content.name}"/></a></h4>
               <b class="red fb">￥${content.effectivePrice}</b> <b class="l_gray">(<fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0"></fmt:formatNumber>折)</b> 
               </li>
        </c:forEach>
</ul>
</div>