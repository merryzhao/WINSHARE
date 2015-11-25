<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h2 class="sort_tit txt_center margin10">${fragment.category.name}</h2>
    <div class="menu_border">
    
    <c:forEach items="${fragment.category.children}" var="category">
    <c:if test="${category.available}">
      <dl class="sort_list unfold">
        <dt>
          <h2><a title="" href="http://list.winxuan.com/${category.id}">${category.name}</a></h2>
        </dt>
        <c:forEach items="${category.children}" var="cate">
        	<c:if test="${category.available}">
	        	<dd><a title="" href="http://list.winxuan.com/${cate.id}">${cate.name}</a></dd>
	        </c:if>
        </c:forEach>
      </dl>
    </c:if>
    </c:forEach>
    </div>