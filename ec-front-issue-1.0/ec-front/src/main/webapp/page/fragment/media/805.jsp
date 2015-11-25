<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<dl class="book_news" fragment="${fragment.id}">
	<dt>
		<a class="fr more_news" href="javasript:;" style="display: none;">更多>></a><b>${fragment.name}</b>
	</dt>
	<c:forEach var="content" items="${contentList}" varStatus="status">
		<dd>
			<a href="${content.href}" title="${content.name}"><winxuan-string:substr length="14" content="${content.name}"/></a>
		</dd>
	</c:forEach>
</dl>
