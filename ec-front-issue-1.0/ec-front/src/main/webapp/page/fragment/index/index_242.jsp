<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <h4 class="list_title margin10">入驻商家</h4>
<ul class="business" fragment="${fragment.id}">
<jsp:include page="../null.jsp"/>
 <c:forEach items="${contentList}" var="content" varStatus="status">
	<li><a   target="_blank"  href="${content.url }" title="${content.title }"><img class="book_img"
			src="${content.src }" alt="${content.name }">
	</a>
	</li>
	</c:forEach>
</ul>