<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<%--已使用index_142片段 --%>
<div content="1f" style="display:none;">
 <ul class="book_list" fragment="${fragment.id }" >
 <jsp:include page="../null.jsp"/>
		<c:forEach items="${contentList}" var="content" varStatus="status">
            <li>
                <div class="have_pro">
                    <p><c:if test="${content.hasPromotion}"><img src="images/ads/pro_icon1.png"></c:if></p>				
                    <a title="<c:out value="${content.name}" /> "   target="_blank"  href="${content.url}"><img class="book_img" alt="<c:out value="${content.name}" /> " src="${content.imageUrl}" ></a></div>
                <h4><a title="<c:out value="${content.name}" /> "   target="_blank"  href="${content.url}"><winxuan-string:substr length="20" content="${content.name}"/></a></h4>
                <b class="red fb">￥${content.effectivePrice}</b> <b class="l_gray">(<fmt:formatNumber value="${content.discount*10}" pattern="##.#" minFractionDigits="1"></fmt:formatNumber>折)</b> </li>
        </c:forEach>
</ul>
</div>
