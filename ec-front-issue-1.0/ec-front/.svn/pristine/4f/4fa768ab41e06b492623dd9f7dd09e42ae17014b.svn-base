<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div fragment="${fragment.id}">
<p class="margin10">
<jsp:include page="../null.jsp"/>
<c:forEach var="content" items="${contentList}" varStatus="status">
	<a href="${content.href}" title="${content.title}">
		<img src="${content.src}" alt="${content.title}">
	</a>
</c:forEach>
</p>
</div>