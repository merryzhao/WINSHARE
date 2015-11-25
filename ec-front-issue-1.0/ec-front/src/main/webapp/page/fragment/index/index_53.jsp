<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.winxuan.com/tag/winxuan-string" prefix="winxuan"%>
<dl fragment="53">
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content" varStatus="status">
		<dd>
			<a   target="_blank"  href="${content.url}" title="${content.title}"><winxuan:substr length="14" content="${content.name}"></winxuan:substr></a>
		</dd>
	</c:forEach>
</dl>
