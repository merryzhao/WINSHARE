<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- 栏目主体(cont) -->
<div class="cont" fragment="${fragment.id}" cachekey="${param.cacheKey }">
	<div class="sg-selective">
		<ul class="sg-selective-imglist-ul">
			<jsp:include page="../null.jsp"/>
			<c:forEach var="content" items="${contentList}">
				<li>
					<a href="${content.href}" target="_blank" title="${fn:escapeXml(content.title)}">
						<img class="A-alpha-outin-2s A-hover-alpha-60" src="${content.src }" alt="${fn:escapeXml(content.name)}">
						<span>${content.name }</span>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<!-- 栏目主体(cont) end -->

