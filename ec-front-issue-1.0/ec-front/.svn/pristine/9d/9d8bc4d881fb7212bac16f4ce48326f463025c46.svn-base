<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="more_sort" fragment="470">
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content" varStatus="status">
		<c:if test="${status.first }">
			<li label="hotsale" class="now_sort"><a href="${content.url }">${content.name
					}</a>
		</c:if>
		<c:if test="${!status.first }">
			<li label="hotsale"><a href="${content.url }">${content.name }</a>
		</c:if>
	</c:forEach>
</ul>