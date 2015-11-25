<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp" %>
<dl>
<c:forEach var="second" items="${category.children}" varStatus="status">
<c:if test="${second.logicDisplay == true}">
   
       <c:if test="${empty onlyEBookQueryString}">
       <dt><a href="${second.categoryHref}" title="${second.name}">${second.name}</a></dt>
       </c:if>
       <c:if test="${!empty onlyEBookQueryString}">
       <dt><a href="http://list.winxuan.com/${second.id}${onlyEBookQueryString}" title="${second.name}">${second.name}</a></dt>
       </c:if>
       <dd >
       <c:forEach var="three" items="${second.children}">
       <c:if test="${three.logicDisplay == true}">
           <a href="${three.categoryHref}${onlyEBookQueryString}" title="${three.name}">${three.name}</a>
       </c:if>
       </c:forEach>
       </dd>
</c:if>
</c:forEach>		
</dl>