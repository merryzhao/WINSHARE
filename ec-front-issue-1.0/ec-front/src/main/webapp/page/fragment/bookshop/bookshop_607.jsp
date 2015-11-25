<%@page pageEncoding="UTF-8" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <div class="book_topics" fragment="${fragment.id}">
      <h2>${fragment.name}</h2>
      <ul>
        <c:forEach var="content" items="${contentList}"  varStatus="status">
        <li><a target="_blank" href="${content.href}" title="${content.title}">${content.name}</a></li>
        </c:forEach>
      </ul>
      <div class="hei1"></div>
    </div>
 