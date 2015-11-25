<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- 附加内容区域(attach) -->
<div class="attach" fragment="${fragment.id}" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp" />
	<c:forEach var="content" items="${contentList }" >
			<a href="${content.href}" title="${fn:escapeXml(content.title)}" target="_blank">更多 &gt;</a>
	</c:forEach>
</div>
<!-- 附加内容区域(attach) end -->
