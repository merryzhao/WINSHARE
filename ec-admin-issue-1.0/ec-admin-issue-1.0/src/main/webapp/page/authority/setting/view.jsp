<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../../snippets/tags.jsp"%>
<%@include file="../../snippets/scripts.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>权限设置</title>
	<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
	<%@include file="../../snippets/meta.jsp"%>
	<link type="text/css"
	href="${pageContext.request.contextPath}/css/authority/authority.css"
	rel="stylesheet" />
	<link rel="stylesheet" href="${contextPath}/css/authority/jquery.autocomplete.css" />
    <script type='text/javascript' src='${contextPath}/js/authority/jquery.autocomplete.js'></script>
    <script type='text/javascript' src='${contextPath}/js/authority/query.js'></script>
</head>
<body>
	<div class="frame" >
		<!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-system.jsp"%>
		<div class="frame-main">
			<div class="frame-main-inner" id="content">
			<div>
				<div>
					<p class="mylabel">
			        	资源组：
			        	<c:choose>
		        			<c:when test="${empty param.groupId}">
				        		<a class="all" href="authority" >全部</a> 
				        	</c:when>
		        			<c:otherwise>
				        		<a class="link1" href="authority" >全部</a> 
				        	</c:otherwise>
			        	</c:choose>
			        	<c:forEach var="resourceGroup" items="${resourceGroups}" varStatus="status">
			        		<c:choose>
					        	<c:when test="${param.groupId == resourceGroup.id}">
					        		<a class="all" 
					        		href="authority?groupId=${resourceGroup.id}" >${resourceGroup.value}</a> 
					        	</c:when>
					        	<c:otherwise>
					        		<a class="link1" 
					        		href="authority?groupId=${resourceGroup.id}" >${resourceGroup.value}</a> 
					        	</c:otherwise>
					        </c:choose>
			        	</c:forEach>
			       	</p>
				</div>
				<div>
					 <form:form action="" method="post" >
					<input id="keyword" value="请输入用户账户" style="color:grey" onfocus="javascript:clearnText();" onblur="javascript:addText();"/><input id="getValue" value="查询" type="button" />
					</form:form>
				</div>
				<div>
					<table width="100%" class="list-table view" cellspacing="0" cellpadding="0">
		            <thead>
		                <tr>
		                    <th width="15%">用户账户</th>
		                    <th width="15%">姓名</th>
		                    <th width="15%">电子邮箱</th>
		                    <th width="10%">座机电话</th>
		                    <th width="10%">手机</th>
		                    <th width="25%">所属资源组</th>
		                    <th width="10%">操作</th>
		                </tr>
		            </thead>
		            <tbody>
		                <c:forEach items="${employees}" var="employee" varStatus="status">
		                	<tr>
			                	<td >${employee.name}</td>
			                	<td >${employee.realName}</td>
			                	<td >${employee.email}</td>
			                	<td >${employee.phone}</td>
			                	<td >${employee.mobile}</td>
			                	<td class="txt_left"><c:forEach items="${employee.resourceGroups}" var="rg">${rg.value} </c:forEach></td>
			                	<td >
			                		<a class="link2" href="javascript:;" available="${employee.available}" availableId="${employee.id}" bind="available"><c:if test="${employee.available == true}">禁用</c:if><c:if test="${employee.available == false}">启用</c:if></a>	|	
			                		<a class="link2" href="javascript:;" employeeId="${employee.id}" bind="manageRelation">设置权限</a>
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
	
	<c:if test="${!empty resourceGroups }">
	 	<div class="manageRelation_box" style="display:none" >
			<div>
				<table class="list-table view" cellspacing="0" border="0" width="100%">
					<thead>
						<tr >
							<th width="5%"><input type="checkbox" bind="selectAll"/></th>
							<th width="27%">资源组编码</th>
							<th width="33%">资源组值</th>
							<th width="35%">资源组描述</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${resourceGroups}" var="resourceGroup">
							<tr>
								<td><input type="checkbox" value="${resourceGroup.id}" bind="item"/></td>
								<td>${resourceGroup.code}</td>
								<td >${resourceGroup.value}</td>
								<td>${resourceGroup.description}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<center ><button class="manage_relation" name="manageRelationAction">保		存</button></center>
		</div>
	</c:if>
	<script src="${contextPath}/js/authority/setting.js"></script>
</body>
</html>