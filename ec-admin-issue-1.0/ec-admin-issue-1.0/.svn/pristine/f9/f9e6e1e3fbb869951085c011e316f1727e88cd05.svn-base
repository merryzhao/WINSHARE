<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
   <%@include file="../snippets/tags.jsp"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
[<c:forEach var="cat" items="${area.children }" varStatus="s"  >
	{id:${cat.id},name:'${cat.name }',pid:${cat.parent.id},isParent:<c:if test="${not empty cat.children}">true</c:if><c:if test="${empty cat.children}">false</c:if>}<c:if test="${not s.last }">,</c:if>
</c:forEach>] 

