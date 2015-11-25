<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="title" fragment="${fragment.id }" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp"/>
	<c:forEach var="content" items="${contentList }" varStatus="i">
		${content.content}
	</c:forEach>
</div>
	<%-- <h3 class="title-h">
		<c:if test="${fragment.name==''}">暂无内容</c:if>
		${fragment.name }
	</h3>
	<div class="title-s">倾情推荐时下最热门图书</div> --%>