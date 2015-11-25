<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<dl class="book_news bot_dot" fragment="${fragment.id }">
	<dt>
		<h3>${fragment.name }</h3>
	</dt>
	<c:forEach items="${contentList}" var="content" varStatus="status">
	<dd>
		<a     href="${content.url }" title="<c:out value="${content.title }"/>">${content.name }</a>
	</dd>
	</c:forEach>
</dl>