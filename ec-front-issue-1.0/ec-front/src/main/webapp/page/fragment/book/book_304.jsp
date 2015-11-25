<%@page pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div fragment="${fragment.id}">
<jsp:include page="../null.jsp"/>
 <h3 class="fb box_title">${fragment.name}</h3>
        <ul class="press">
         	<c:forEach items="${contentList}" var="content" varStatus="status">
	          <li><a href="${content.href}">${content.name}</a></li>
           </c:forEach>     
       </ul> 
</div>