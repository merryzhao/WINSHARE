<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="title_list" fragment="${fragment.id }">
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content">
	<li>
	<a   target="_blank"  href="${content.url}" target="_blank" title="<c:out value="${content.name}" /> "><c:out value="${content.name}" /> </a>
	</li>
	</c:forEach>
</ul>