<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col col-mainslider J-tab" fragment="${fragment.id}" cachekey="${param.cacheKey }">
   <c:forEach items="${contentList}" var="content" varStatus="i">
			<c:choose>
				<c:when test="${empty content.content}">
					暂无内容!
				</c:when>
				<c:otherwise>
					${content.content}
				</c:otherwise>
			</c:choose>
	</c:forEach>
</div>