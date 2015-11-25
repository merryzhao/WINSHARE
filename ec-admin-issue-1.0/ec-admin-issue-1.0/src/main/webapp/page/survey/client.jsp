<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>调查表问卷结果</title>
	<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
	<%@include file="../snippets/meta.jsp"%>
	<link type="text/css"
	href="${pageContext.request.contextPath}/css/survey/survey.css"
	rel="stylesheet" />
</head>
<body>
	<div class="frame" >
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<div class="frame-main-inner" id="content">
			<div>
				<div>
					<table width="100%" class="list-table view" cellspacing="0" cellpadding="0">
		            <thead>
		                <tr>
		                    <th width="30%">开始时间</th>
		                    <th width="30%">结束时间</th>
		                    <th width="20%">ip地址</th>
		                    <th width="20%">操作</th>
		                </tr>
		            </thead>
		            <tbody>
		                <c:forEach items="${clients}" var="client" varStatus="status">
		                	<tr>
			                	<td ><fmt:formatDate value="${client.start}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                	<td ><fmt:formatDate value="${client.end}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                	<td >${client.ip}</td>
			                	<td >
			                		<a class="link2" href="/survey/client/detail/${client.id}" target="_blank">详细</a>
			                	</td>	                	
		                	</tr>
		                </c:forEach>
		            </tbody>
		        </table>
		        <winxuan:page pagination="${pagination}" bodyStyle="html"/>
				</div>
			</div>
 		</div>
 		</div>
 	</div>
</body>
</html>