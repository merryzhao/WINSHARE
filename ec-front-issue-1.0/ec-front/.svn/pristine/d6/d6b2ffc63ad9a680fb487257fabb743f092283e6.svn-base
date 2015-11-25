<%@page pageEncoding="UTF-8" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<dl class="book_news"  fragment="${fragment.id}">
        <dt>热门书讯</dt>
           <c:forEach var="content" items="${contentList}"  varStatus="status">
            <dd><a target="_blank" href="http://www.winxuan.com/product/${content.id}" title="${content.name}">${content.name}</a></dd>
           </c:forEach>
      </dl>