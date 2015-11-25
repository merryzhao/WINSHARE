<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="sg-download"	 fragment="${fragment.id }" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp"/>
	<c:forEach var="content" items="${contentList }" varStatus="i">
		<c:if test="${i.index==0}">
			<img class="sg-download-code" src="${content.src}" alt="${content.title}">
		</c:if>
		<c:if test="${i.index!=0}">
			<c:if test="${i.index==1}">
				<a href="${content.href}" target="_blank" title="${content.title}" class="sg-download-a1">${content.title}</a>
			</c:if>
			<c:if test="${i.index==2||i.index==3}">
				<a href="${content.href}" target="_blank" title="${content.title}" class="sg-download-a2">${content.title}</a>
			</c:if>
			<c:if test="${i.index==4}">
				<a href="${content.href}" target="_blank" title="${content.title}" class="sg-download-a4">${content.title}</a>
			</c:if>
		</c:if>
	</c:forEach>
</div>
 