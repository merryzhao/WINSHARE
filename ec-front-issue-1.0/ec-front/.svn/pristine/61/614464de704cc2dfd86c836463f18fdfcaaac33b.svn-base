<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<c:if test="${fn:length(contentList) <= 0}">
	<c:choose>
	    <c:when test="${empty param.tip}">        
	       <span>暂无内容!</span>      		
	    </c:when>
		<c:otherwise>
			<span>${param.tip}</span>
		</c:otherwise>
	</c:choose>
</c:if>