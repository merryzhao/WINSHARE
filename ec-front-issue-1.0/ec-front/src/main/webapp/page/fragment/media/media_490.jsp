<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="sort_title cl">
	<h3>${fragment.name}</h3>
</div>
<div class="left_box12 fl" table="media" cn="now_sort">
<ul class="more_sort" fragment="${fragment.id}">
<%-- 顶部title --%>
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content" varStatus="status">
		<li label="media"
			<c:if test="${status.index==0 }">class="now_sort"</c:if>><a
			href="${content.url}">${content.name }</a>
		</li>
	</c:forEach>
</ul>
	<c:import url="/fragment/491"></c:import>
	<c:import url="/fragment/492"></c:import>
	<c:import url="/fragment/493"></c:import>
	<c:import url="/fragment/494"></c:import>
	<c:import url="/fragment/495"></c:import>
	<c:import url="/fragment/496"></c:import>
	<c:import url="/fragment/497"></c:import>
	<c:import url="/fragment/498"></c:import>
</div>

<div class="right_box3 fr">
	<c:import url="/fragment/499"></c:import>
	<c:import url="/fragment/500"></c:import>
</div>