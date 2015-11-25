<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<dl class="sort_tab" fragment="${fragment.id}">
	<dt></dt>
	<jsp:include page="../null.jsp" />
	<c:forEach items="${contentList}" var="content" varStatus="status">
		<c:if test="${status.first}">
			<dd class="current_sort" label="hotpro">
		</c:if>
		<c:if test="${!status.first}">
			<dd label="hotpro">
		</c:if>
		<a href="${content.href}">${content.name}</a>
		</dd>
	</c:forEach>
</dl>