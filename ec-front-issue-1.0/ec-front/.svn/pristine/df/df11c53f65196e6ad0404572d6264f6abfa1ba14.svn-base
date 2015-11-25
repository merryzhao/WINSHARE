<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="shopads_box" fragment="302">
<jsp:include page="../null.jsp"/>
	<ul class="rotation2">
		<c:forEach items="${contentList}" var="content" varStatus="status">
			 <li>${status.count}</li>
		</c:forEach>
	</ul>
	<ul class="shop_ads">
		<c:forEach items="${contentList}" var="content" varStatus="status">
		<li>
			<a target="_blank"  href="${content.url }" title="${content.title }">
				<img data-lazy="false" src="${content.src }" alt="${content.name }">
			</a>
		</li>
		</c:forEach>
	</ul>
</div>
<script>
seajs.use("slider",function(slider){slider();});
</script>