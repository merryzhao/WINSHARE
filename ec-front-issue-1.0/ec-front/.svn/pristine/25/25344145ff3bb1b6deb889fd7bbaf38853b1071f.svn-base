<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="ebook_img_in" fragment="${fragment.id}" cachekey="${cacheKey}">
	 <c:forEach items="${contentList}" var="content" varStatus="i">
		 <c:if test="${empty content.content||content.content == ''}">
		 	暂无内容
		 </c:if>
		 ${content.content}
	</c:forEach>
 </div>