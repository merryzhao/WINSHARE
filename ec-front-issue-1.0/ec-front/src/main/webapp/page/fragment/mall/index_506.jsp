<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="right_box2 fl" fragment="506">
<jsp:include page="../null.jsp"/>
    <div class="shopads_box">
        <ul class="rotation2">
        <c:forEach items="${contentList}" var="content" varStatus="status">
        	<c:if test="${status.first }"> <li class="current_ad">${status.index+1 }</li></c:if>
        	<c:if test="${!status.first }"> <li>${status.index+1}</li></c:if>
        </c:forEach>
        </ul>
        <ul class="shop_ads">
        <c:forEach items="${contentList}" var="content" varStatus="status">
        	<li><a href="${content.href}" title="<c:out value="${content.title }"/>"><img data-lazy="false" src="${content.src }" alt="${content.title }"></a></li>
        </c:forEach>
        </ul>
    </div>
    <div class="ads_sort">
        <dl>
        <c:forEach items="${contentList}" var="content" varStatus="status">
        <c:if test="${status.first }"><dd class="now_theme"><a href="${content.href }" title="<c:out value="${content.title }"/>">${content.name }</a></dd></c:if>
        <c:if test="${!status.first }"><dd><a href="${content.href }" title="<c:out value="${content.title }"/>">${content.name }</a></dd></c:if>    
        </c:forEach>
        </dl>
        <a href="javascript:;" class="left_arrow"></a> <a href="javascript:;" class="right_arrow"></a>
    </div>
</div>