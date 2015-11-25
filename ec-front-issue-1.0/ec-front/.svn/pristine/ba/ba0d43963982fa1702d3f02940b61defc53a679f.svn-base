<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="book_topics" fragment="360" >
	<h2>专题推荐</h2>
	<ul>
		<c:forEach items="${contentList}" var="content" varStatus="status">
		<li><a   target="_blank"  href="${content.url }" title="<c:out value="${content.title }"/>">${content.name }</a>
		</li>
		</c:forEach>
	</ul>
	<div class="hei1"></div>
</div>