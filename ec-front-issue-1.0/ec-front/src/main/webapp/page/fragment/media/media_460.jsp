<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="more_sort" fragment="460">
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content" varStatus="status">
		<li label="media"
			<c:if test="${status.index==0 }">class="now_sort"</c:if>><a
			href="${content.url}">${content.name }</a>
		</li>
	</c:forEach>
</ul>
