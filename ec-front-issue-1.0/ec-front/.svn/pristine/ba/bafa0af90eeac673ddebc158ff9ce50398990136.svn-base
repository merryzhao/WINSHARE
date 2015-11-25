<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/winxuan-string" prefix="s" %>
<div fragment="${fragment.id}" content="bookshop">
<ul class="pro_bookstore" >
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content" varStatus="status">
		<li><a class="pro_pic"   target="_blank"  href="${content.url }"><img src="${content.imageUrl }" alt="${content.product.name }">
		</a>
			<p>
				<a   target="_blank"  href="${content.url }" title="<c:out value="${content.name }"/>"><s:substr length="15" content="${content.name}"></s:substr> </a>
			</p> 
				<b class="fb red f14">￥${content.effectivePrice}</b>
				<b class="l_gray">(<fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0"></fmt:formatNumber>折)</b>
		    </li>
	</c:forEach>
</ul>
<br class="hei1" />
<div class="hei10"></div>
</div>