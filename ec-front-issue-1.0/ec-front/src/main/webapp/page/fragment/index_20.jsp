<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="title_list" fragment="20">
<jsp:include page="null.jsp"/>
	<c:forEach items="${contentList}" var="content">
	<li><a href="${content.url}" target="_blank" title="${content.name}">${content.name}</a>
	</li>
	</c:forEach>
</ul>