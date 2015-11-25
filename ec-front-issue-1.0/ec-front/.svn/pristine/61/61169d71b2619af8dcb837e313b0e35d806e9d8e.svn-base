<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div table="hot" cn="now_sort" >
<ul class="more_sort" fragment="117" >
<jsp:include page="../null.jsp"/>
            <c:forEach items="${contentList}" var="content" varStatus="status">
            <li label="hot" <c:if test="${status.index==0}">class="now_sort"</c:if>><a href="${content.url }" >${content.name}</a></li>
            </c:forEach>
</ul>
    <c:import url="/fragment/118" /> <%-- 热销-精选 --%>
	<c:import url="/fragment/119" /> 
	<c:import url="/fragment/120" />
	<c:import url="/fragment/121" />
	<c:import url="/fragment/122" />
	<c:import url="/fragment/123" />
	<c:import url="/fragment/124" />
	<c:import url="/fragment/125" />
</div>