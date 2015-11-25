<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<ul class="list sg-listebook" fragment="${fragment.id }" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp"/>
	<c:forEach var="content" items="${contentList}" varStatus="i">
		<li>
			<div class="cell sg-cellmiddle">
				<div class="img"><a href="${content.url}" target="_blank" title="${fn:escapeXml(content.sellName)}"><img ${size.value} src="${content.product.imageUrlFor160px}" alt="${fn:escapeXml(content.sellName)}"></a></div>
				<div class="name"><a href="${content.url}" target="_blank" title="${fn:escapeXml(content.sellName)}">${fn:escapeXml(content.sellName)}</a></div>
				<div class="price-n">￥${content.effectivePrice}<span>(${content.discount}折)</span></div>
			</div>
		</li>
	</c:forEach>
</ul>
 