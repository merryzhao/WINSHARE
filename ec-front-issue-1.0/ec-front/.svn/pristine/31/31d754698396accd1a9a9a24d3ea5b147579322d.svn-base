<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<ul fragment="${fragment.id}" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content" >	
		<li>
			<div class="cell cell-m-book-top-n">
				<div class="img"><a href="${content.url}" target="_blank" title="${fn:escapeXml(content.sellName)}"><img ${size.value} src="${content.product.imageUrlFor200px}" alt="${fn:escapeXml(content.sellName)}"></a></div>		
				<div class="name"><a href="${content.url}" target="_blank" title="${fn:escapeXml(content.sellName)}">${content.name}</a></div>
			</div>
		</li>
	</c:forEach>
</ul>
