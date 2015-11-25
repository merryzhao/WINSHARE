<%@page pageEncoding="UTF-8" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="leftbox_menu" fragment="${fragment.id}" >
    <h3 class="fb box_title">${fragment.name}</h3>
      <ul class="press">
     <c:forEach var="content" items="${contentList}"  varStatus="status">	     
	          <li><a target="_blank" href="${content.href}">${content.name}</a></li>
         </c:forEach>
      </ul>
    </div>