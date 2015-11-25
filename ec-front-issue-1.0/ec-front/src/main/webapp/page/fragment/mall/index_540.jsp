<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="sort_title cl" fragment="${fragment.id}">
<jsp:include page="../null.jsp"/>
<span class="fr"> <c:forEach items="${contentList}" var="content"
		varStatus="status">
		<a class="khaki" href="${content.href}">${content.name}</a>
		<c:if test="${!status.last}">|</c:if>
	</c:forEach>
	 </span>
	<h3>${fragment.name}</h3>
</div>