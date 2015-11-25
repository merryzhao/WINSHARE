<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="margin10" fragment="${fragment.id}">
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content" varStatus="status">
		<a title="<c:out value="${content.title }"/>"
			    href="${content.url }"><img alt="${content.title }"
			src="${content.src }">
		</a>
	</c:forEach>
</div>
