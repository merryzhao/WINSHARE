<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<h3 class="pro_title margin10" fragment="${fragment.id}">
<jsp:include page="../null.jsp"/>
	<span class="fr">
		<c:forEach var="content" items="${contentList}"  varStatus="status">
			<c:if test="${status.index > 0}"> | </c:if>
			<a href="${content.href}" title="">${content.name}</a>
		</c:forEach>
	</span>
	<a href="javascript:;" title="">${fragment.name}</a>
</h3>