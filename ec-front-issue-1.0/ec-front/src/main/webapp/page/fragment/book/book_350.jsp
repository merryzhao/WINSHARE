<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div content="hotpro" table="bookshop" cn="now_sort" >
<div fragment="350">
<jsp:include page="../null.jsp"/>
	<ul class="more_sort">
		<c:forEach items="${contentList}" var="content" varStatus="status">
			<li label="bookshop"
				<c:if test="${status.index==0 }">class="now_sort"</c:if>>
				<a	href="${content.url}">${content.name }</a>
			</li>
		</c:forEach>
	</ul>
</div>	
	<c:import url="/fragment/351"></c:import> <%--文轩书店 --%>
	<c:import url="/fragment/357"></c:import> <%--计算机书店 --%>
	<c:import url="/fragment/352"></c:import> <%--生活书店 --%>
	<c:import url="/fragment/353"></c:import> <%--教育书店 --%>
	<c:import url="/fragment/354"></c:import> <%--少儿书店 --%>
	<c:import url="/fragment/355"></c:import> <%--社科书店 --%>
	<c:import url="/fragment/356"></c:import> <%--考试书店 --%>
</div>