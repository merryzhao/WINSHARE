<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="title">
	<h3 class="title-h">
		<c:if test="${fragment.name==''}">暂无内容</c:if>
		${fragment.name}
	</h3>
</div>
<div class="cont" fragment="${fragment.id }" cachekey="${param.cacheKey }">
	<ul class="list list-m-dotted">
		<jsp:include page="../null.jsp"/>
		<c:forEach var="content" items="${contentList }">
			<li>
				<div class="cell cell-s-book-left">
					<div class="img"><a href="${content.url }" target="_blank" title="${fn:escapeXml(content.sellName)}"><img ${size.value} src="${content.product.imageUrlFor55px}" alt="${fn:escapeXml(content.sellName)}"></a></div>	
					<div class="name"><a href="${content.url }" target="_blank" title="${fn:escapeXml(content.sellName)}">${content.sellName}</a></div>
					<div class="price"><span class="price-n">￥${content.effectivePrice}</span><span class="price-o">￥${content.product.listPrice}</span></div>	
				</div>
			</li>
		</c:forEach>
	</ul>
</div>
