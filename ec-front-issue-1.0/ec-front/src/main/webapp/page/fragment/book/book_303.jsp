<%@page pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<dl class="book_news" fragment="303">
	<dt>热门书讯</dt>
	<c:forEach items="${contentList}" var="content" varStatus="status">
	<dd><a target="_blank"  href="${content.href}" title="${content.title}"><winxuan-string:substr length="14" content="${content.name}"/></a></dd>
	</c:forEach>
</dl>