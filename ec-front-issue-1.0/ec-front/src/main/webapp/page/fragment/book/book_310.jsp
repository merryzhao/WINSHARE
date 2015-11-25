<%@page pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div  content="hotpro" table="new_onsale" cn="now_sort" >
<ul class="more_sort" fragment="310" >
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content" varStatus="status">
	<li label="new_onsale" <c:if test="${status.index==0 }">class="now_sort"</c:if>><a href="${content.url}">${content.name }</a>
	</li>
	</c:forEach>
</ul>
<c:import url="/fragment/311"></c:import> <%--精选 --%>
<c:import url="/fragment/318"></c:import>  <%--传记 --%>
<c:import url="/fragment/312"></c:import> <%--美妆 --%>
<c:import url="/fragment/313"></c:import> <%--家教 --%>
<c:import url="/fragment/314"></c:import> <%--辅导 --%>
<c:import url="/fragment/315"></c:import> <%--少儿 --%>
<c:import url="/fragment/316"></c:import> <%--历史 --%>
<c:import url="/fragment/317"></c:import> <%--励志 --%>

</div>