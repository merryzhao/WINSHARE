<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<dl class="book_news" fragment="${fragment.id}">
                <dt>${fragment.name}</dt>
                <jsp:include page="../null.jsp"/>
                <c:forEach var="content" items="${contentList}">
                <dd><a href="${content.href}" title="${content.title }">${content.name}</a></dd>
                </c:forEach>
                
 </dl>