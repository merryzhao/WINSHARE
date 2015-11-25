<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="mallextend" fragment="${fragment.id}">
<jsp:include page="../null.jsp"/>
<c:forEach items="${contentList}" var="content" varStatus="status">
	<a href="${content.href}" title="${content.title}">
	<img data-lazy="false" src="${content.src}" alt="${content.title}">
		</a>
</c:forEach>
	</div>