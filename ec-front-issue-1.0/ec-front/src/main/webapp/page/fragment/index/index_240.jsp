<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div class="left_box12 fl nopadding" fragment="240">
<jsp:include page="../null.jsp"/>
        <ul class="merchandise">
        <c:forEach items="${contentList}" var="content" varStatus="status">
            <li>
                <div class="have_pro">
                    <p><c:if test="${content.hasPromotion}"><img src="images/ads/pro_icon1.png"></c:if></p>
                    <a title="${content.name}" target="_blank"  href="${content.url}"><img class="book_img" alt="${content.name }" src="${content.imageUrl }" ></a></div>
                <h4><a title="${content.name}" target="_blank"  href="${content.url}"><winxuan-string:substr length="20" content="${content.name}"/> <b class="orange">${content.subheading }</b></a></h4>
                <p class="txt_center">文轩价：<b class="red fb f14">￥${content.effectivePrice}</b></p></li>
                </c:forEach>
        </ul>
    </div>
