<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../snippets/tags.jsp"%>

[
<c:forEach var="c" items="${categorys }" varStatus="s">
	{
	id:${c.id}, name:<c:if test="${c.name==c.alias}">"${c.name}"</c:if><c:if test="${c.name!=c.alias}">"${c.name}*${c.alias}"</c:if>, checked : ${c.defaultChecked}
	,isParent:
	<c:if test="${not empty c.children}">true</c:if>
	<c:if test="${empty c.children}">false</c:if>
	,index:${c.index}
	}
	<c:if test="${not s.last }">,</c:if>
</c:forEach>
]