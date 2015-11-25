<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
 <h3 class="pro_title" fragment= "${fragment.id}">
      <span class="fr">
      <jsp:include page="../null.jsp"/>
       <c:forEach items="${contentList}" var="content"
		varStatus="status">
		<a class="khaki" href="${content.href}">${content.name}</a>
		<c:if test="${!status.last}">|</c:if>
	</c:forEach>
       </span>
        
        <a href="#" title="">${fragment.name}</a>
        </h3>