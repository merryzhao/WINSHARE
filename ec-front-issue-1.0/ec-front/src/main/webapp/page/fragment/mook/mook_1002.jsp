<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <p class="margin10" fragment="${fragment.id}">
 <jsp:include page="../null.jsp"/>
 <c:forEach var="content" items="${contentList}">
 <a href="${content.href}" title="${content.title}"><img src="${content.src}" alt="${content.title}"></a>
 </c:forEach>
 </p>