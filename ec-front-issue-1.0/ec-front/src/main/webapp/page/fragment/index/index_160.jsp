<%@page pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div table="2f" cn="now_sort" class="left_box12 fl">
	<ul class="more_sort" fragment="160">
	<jsp:include page="../null.jsp"/>
		<c:forEach items="${contentList}" var="content" varStatus="status">
			<li label="2f"
				<c:if test="${status.index==0}">class="now_sort"</c:if>><a
				href="${content.url }"><winxuan-string:substr length="20" content="${content.name}"/></a>
			</li>
		</c:forEach>
	</ul>
	<c:import url="/fragment/161" />
	<c:import url="/fragment/162" />
	<c:import url="/fragment/163" />
	<c:import url="/fragment/164" />
	<c:import url="/fragment/165" />
	<c:import url="/fragment/169" />
	<c:import url="/fragment/170" />
	<c:import url="/fragment/171" />
</div>