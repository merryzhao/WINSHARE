<%@page pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div class="left_box12 fl"  cn="now_sort" table="4f">
	<ul class="more_sort" fragment="195">
	<jsp:include page="../null.jsp"/>
		<c:forEach items="${contentList}" var="content" varStatus="status">
			<li label="4f"
				<c:if test="${status.index==0}">class="now_sort"</c:if>><a
				href="${content.url }"><winxuan-string:substr length="20" content="${content.name}"/></a>
			</li>
		</c:forEach>
	</ul>
	<c:import url="/fragment/196" />
	<c:import url="/fragment/197" />
	<c:import url="/fragment/198" />
	<c:import url="/fragment/199" />
	<c:import url="/fragment/203" />
</div>