<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- 栏目附加链接(attach) -->
<div class="attach" fragment="${fragment.id}" cachekey="${param.cacheKey }">
	<div class="sg-classifyrecommend-links">
	    <jsp:include page="../null.jsp" />
		<c:forEach var="content" items="${contentList }">
			<span><a href="${content.href}" title="${fn:escapeXml(content.title)}" target="_blank">${content.name}&gt;</a></span>
		</c:forEach>
	</div>
</div>
<!-- 栏目附加链接(attach) end -->