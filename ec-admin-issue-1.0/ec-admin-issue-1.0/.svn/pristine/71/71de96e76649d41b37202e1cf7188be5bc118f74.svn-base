<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
   <%@include file="../snippets/tags.jsp"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
[<c:forEach var="cat" items="${category.children }" varStatus="s"  >
	{id:${cat.id},name:"${cat.name }"<c:if test="${cat.name!=cat.alias&&not empty cat.alias  }">+'*${cat.alias }'</c:if>,pid:${cat.parent.id},checked:${cat.available },isParent:<c:if test="${not empty cat.children}">true</c:if><c:if test="${empty cat.children}">false</c:if>}<c:if test="${not s.last }">,</c:if>
</c:forEach>]

