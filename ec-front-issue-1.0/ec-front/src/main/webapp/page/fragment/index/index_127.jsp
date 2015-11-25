<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div fragment="${fragment.id}">
<p class="margin10" >
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content">
		<a title="${content.title}" href="${content.href}" target="_blank">
			<img alt="${content.title}" src="${content.src}">
		</a>
	</c:forEach>
</p>
</div>