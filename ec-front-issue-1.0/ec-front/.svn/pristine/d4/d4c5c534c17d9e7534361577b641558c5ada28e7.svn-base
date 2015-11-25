<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h3 class="fb box_title" fragment="${fragment.id }">${fragment.name }</h3>
<jsp:include page="../null.jsp"/>
            <ul class="press" >
            <c:forEach var="content" items="${contentList}">
                <li><a href="${content.href}" title="${content.title}">${content.name}</a></li>
         </c:forEach>
            </ul>