<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="cont" fragment="${fragment.id }" cachekey="${param.cacheKey }">
	<ul class="list list-whitebg-border">
		<jsp:include page="../null.jsp"/>	
		<c:forEach items="${contentList}" var="content">
			<li>
				<div class="cell cell-m-book-top-p">
					<div class="img"><a href="${content.url}" target="_blank" title="${fn:escapeXml(content.sellName)}"><img ${size.value} src="${content.product.imageUrlFor160px}" alt="${fn:escapeXml(content.sellName)}"></a></div>
					<div class="name"><a href="${content.url}" target="_blank" title="${fn:escapeXml(content.sellName)}">${content.sellName}</a></div>
					<div class="price"><span class="price-n">￥${content.effectivePrice}</span><span class="price-o">￥${content.product.listPrice}</span></div>
				</div>
			</li>
		</c:forEach>
	</ul>
</div>