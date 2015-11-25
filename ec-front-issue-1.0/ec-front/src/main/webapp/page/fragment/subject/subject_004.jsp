<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<div class="other_subjects" fragment= "${fragment.id}" cachekey="${cacheKey}">
 	<ul>
		<jsp:include page="../null.jsp">
			<jsp:param value="其他类别专题链接" name="tip"/>
		</jsp:include>
 		<c:forEach var="content" items="${contentList}">
	 			<li> 			
					<a target="_blank" href="${content.href}">
			      		<img data-lazy="false" src="${content.src }" alt="${content.name }"/>
			      	</a>
	 			</li>
        </c:forEach>
	</ul>
</div>