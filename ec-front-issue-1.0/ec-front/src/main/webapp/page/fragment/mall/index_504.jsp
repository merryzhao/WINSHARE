<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<ul class="business" fragment="504">
<jsp:include page="../null.jsp"/>
<c:forEach items="${contentList}" var="content" varStatus="status">

		<li>
		<a href="${content.href}" title="${content.title}"><img data-lazy="false"
				class="book_img" src="${content.src}" alt="${content.title}">
		</a>
		</li>
	</c:forEach>
	</ul>