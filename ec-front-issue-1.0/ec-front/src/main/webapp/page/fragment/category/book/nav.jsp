<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<c:forEach var="category" items="${items}" varStatus="status">
	<dd data-index="${status.index}">
		<h2><a href="${category.categoryHref}">${category.alias}</a>
		</h2>
		<p><c:forEach var="child" items="${category.children}" varStatus="status"><c:if test="${status.index<4&&child.available}"><a href="${child.categoryHref }">${child.alias}</a></c:if></c:forEach></p>
	</dd>
</c:forEach>
<dd data-index="8">
	<h2>
		<a href="http://www.winxuan.com/book/">更多图书</a>
	</h2>
	<p>
	<c:forEach var="category" items="${morebook}" varStatus="status">
			<c:if test="${category.available}">
				<a href="${category.categoryHref}">${category.alias}</a>
			</c:if>
		</c:forEach>
	</p>
</dd>