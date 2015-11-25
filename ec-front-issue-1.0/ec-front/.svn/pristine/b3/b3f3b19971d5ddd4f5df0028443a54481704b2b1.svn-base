<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<div fragment= "${fragment.id}" cachekey="${cacheKey}">
 	<h3 class="subject_title" >
		 <jsp:include page="../null.jsp">
			<jsp:param value="产品列表-横幅" name="tip"/>
		</jsp:include>
 		<c:forEach var="content" items="${contentList}">
	 			<a target="_blank" href="${content.href}">
	        		<img data-lazy="false" src="${content.src }" alt="${content.name }"/>
	        	</a>
        </c:forEach>
	</h3>
</div>