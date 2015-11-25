<%@page pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div table="1f" cn="now_sort" class="left_box12 fl">
	<ul class="more_sort" fragment="${fragment.id}">
	<jsp:include page="../null.jsp"/>
		<c:forEach items="${contentList}" var="content" varStatus="status">
			<li label="1f" <c:if test="${status.index==0}">class="now_sort"</c:if>><a
				href="${content.url }"><winxuan-string:substr length="20" content="${content.name}"/></a>
			</li>
		</c:forEach>
	</ul>
	<c:import url="/fragment/141" />
	<c:import url="/fragment/142" />
	<c:import url="/fragment/143" />
	<c:import url="/fragment/144" />
	<c:import url="/fragment/145" />
	<c:import url="/fragment/146" />
	<c:import url="/fragment/147" />
	<c:import url="/fragment/150" />
</div>