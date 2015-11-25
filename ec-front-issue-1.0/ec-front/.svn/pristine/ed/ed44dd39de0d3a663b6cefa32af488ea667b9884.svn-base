<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div content="1f">
<ul class="book_list" fragment="${fragment.id }" >
<jsp:include page="../null.jsp"/>
		<c:forEach items="${contentList}" var="content" varStatus="status">
            <li>
                <div class="have_pro">
                	<img class="ebook_ico2" alt="" src="${serverPrefix}css2/images/ads/tag_ebook.png" data-lazyimg="false"/>
                    <p><c:if test="${content.hasPromotion}"><img src="images/ads/pro_icon1.png"></c:if></p>				
                    <a title="<c:out value="${content.name}" /> "   target="_blank"  href="${content.url}"><img class="book_img" alt="<c:out value="${content.name}" /> " src="${content.imageUrl}" ></a></div>
                <h4><a title="<c:out value="${content.name}" /> "   target="_blank"  href="${content.url}"><winxuan-string:substr length="20" content="${content.name}"/></a></h4>
                <b class="red fb">￥${content.effectivePrice}</b> <b class="l_gray">(<fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0"></fmt:formatNumber>折)</b> </li>
        </c:forEach>
</ul>
</div>
