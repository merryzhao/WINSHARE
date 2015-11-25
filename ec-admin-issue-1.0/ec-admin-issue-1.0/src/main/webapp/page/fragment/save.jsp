<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<c:if test="${!empty url}">
<c:import url="${url}/fragment/${fragment.id}"/>
</c:if>
<c:if test="${empty url}">
<c:import url="http://www.winxuan.com/fragment/${fragment.id}"/>
</c:if>