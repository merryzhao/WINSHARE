<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<c:forEach var="category" items="${items}" varStatus="status">
	 <div class="item"  data-index="${status.index}">
	 <div class="item-title">
		<h3><a href="${category.categoryHref}">${category.alias}</a>
		</h3>
		<div class="item-title-class">
		<c:forEach var="child" items="${category.children}" varStatus="status"><c:if test="${status.index<4&&child.available}"><a href="${child.categoryHref }">${child.alias}</a></c:if></c:forEach>
		</div>
		  <b></b>
        </div>
        <span class="overlayout"></span>
	</div>
</c:forEach>
<div class="item" data-index="8">
    <div class="item-title">
    <h3>
        <a href="http://www.winxuan.com/book/">更多图书</a>
    </h3>
    <div class="item-title-class">
    <c:forEach var="category" items="${morebook}" varStatus="status">
            <c:if test="${category.available}">
                <a href="${category.categoryHref}">${category.alias}</a>
            </c:if>
    </c:forEach>
    </div>
    <b></b>
    </div>
    <span class="overlayout"></span>
</div>

