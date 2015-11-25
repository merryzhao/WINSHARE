<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div fragment="${fragment.id}" class="sort_title_fix">
	<h3>${fragment.name}</h3>
	<h4 class="fr">
		<c:forEach items="${contentList}" var="content">
			<a href="${content.href}">${content.name}</a>
		</c:forEach>
	</h4>
</div>
