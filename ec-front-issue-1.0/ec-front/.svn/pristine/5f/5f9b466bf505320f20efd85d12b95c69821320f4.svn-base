<%@page pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div class="subject_box" fragment="${fragment.id}" cachekey="${cacheKey}">
<h3 class="subject_title" >
<jsp:include page="../null.jsp"> 
	<jsp:param value="专题横幅" name="tip"/>
</jsp:include>
 </h3>
	
	<ul class="subject_banner">
		<c:forEach items="${contentList}" var="content" varStatus="status">
		<li>
			<img data-lazy="false" src="${content.src }" alt="${content.name }">
		</li>
		</c:forEach>
	</ul>
</div>
