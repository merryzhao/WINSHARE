<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="sort_title cl">
	<h3>${fragment.name}</h3>
</div>
<ul class="more_sort" fragment="${fragment.id}">
<jsp:include page="../null.jsp"/>
	<c:forEach var="content" items="${contentList}" varStatus="status">
		<li label="hotsale" <c:if test="${status.index == 0}"> class="now_sort"</c:if>><a
			href="${content.url}">${content.name}</a>
		</li>
	</c:forEach>
</ul>