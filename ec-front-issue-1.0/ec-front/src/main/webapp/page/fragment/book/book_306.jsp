<%@page pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div fragment="${fragment.id}">
<jsp:include page="../null.jsp"/>
  <c:forEach var="content" items="${contentList}"  varStatus="status">
     <%-- <p class="left_ads"><a title="${content.title}" href="${content.href}"><img src="${content.src}" width="200" alt="${content.title}" /></a></p> --%>
    </c:forEach>
   </div> 