<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="title">
	<h3 class="title-h">
		<c:if test="${fragment.name==''}">暂无内容</c:if>
		${fragment.name }
	</h3>
</div>
<div class="cont">
	<ul class="list list-many-fixed" fragment="${fragment.id}" cachekey="${param.cacheKey }">
		<jsp:include page="../null.jsp"/>
		<c:forEach var="content" items="${contentList }" varStatus="i">
			<li><a class="link link-normal" href="${content.href}" target="_blank" title="${fn:escapeXml(content.title)}">${content.name }</a></li>
		</c:forEach>
	</ul>
</div>