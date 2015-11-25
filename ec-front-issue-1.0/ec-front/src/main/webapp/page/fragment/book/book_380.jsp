<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h3 class="pro_title">
	<a href="javascript:;" title="">作家专栏</a>
</h3>
<div table="writer" cn="now_sort" >
<div fragment="380">
<jsp:include page="../null.jsp"/>
	<ul class="more_sort">
		<c:forEach items="${contentList}" var="content" varStatus="status">
			<li label="writer"	<c:if test="${status.index==0 }">class="now_sort"</c:if>><a	href="${content.url}">${content.name }</a></li>
		</c:forEach>
	</ul>
	</div>
	<dl class="authors" content="writer">
		<c:import url="/fragment/381"></c:import>
		<dd>
			<c:import url="/fragment/382"></c:import>
			<c:import url="/fragment/383"></c:import>
		</dd>
	</dl>
	<dl class="authors" content="writer" style="display:none;">
		<c:import url="/fragment/384"></c:import>
		<dd>
		<c:import url="/fragment/385"></c:import>
			<c:import url="/fragment/386"></c:import>
		</dd>
	</dl>
	<dl class="authors" content="writer" style="display:none;">
		<c:import url="/fragment/387"></c:import>
		<dd>
		<c:import url="/fragment/388"></c:import>
			<c:import url="/fragment/389"></c:import>
		</dd>
	</dl>
	<div class="hei10"></div>
</div>