<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div table="book" cn="now_sort" >
<ul class="more_sort" fragment="106" >
<jsp:include page="../null.jsp"/>
            <c:forEach items="${contentList}" var="content" varStatus="status">
            <li label="book" <c:if test="${status.index==0}">class="now_sort"</c:if>><a href="${content.url }" >${content.name}</a></li>
            </c:forEach>
</ul>
<div>
<c:import url="/fragment/107" /> <%-- 新书-精选 --%>
	<c:import url="/fragment/108" /> 
	<c:import url="/fragment/109" /> 
	<c:import url="/fragment/110" /> 
	<c:import url="/fragment/111" /> 
	<c:import url="/fragment/112" /> 
	<c:import url="/fragment/113" /> 
	<c:import url="/fragment/114" /> 
</div>
</div>