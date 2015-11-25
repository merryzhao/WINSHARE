<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--已使用index_205片段 --%>
<div fragment="220" class="index_right_fixheight">
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content">
	<p class="margin10"><a   target="_blank"  href="${content.url}" title="<c:out value="${content.title}" /> "><img width="200" alt="<c:out value="${content.title}" /> " src="${content.src}"></a></p>
	</c:forEach>
</div>