<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>资源查询</title>
	<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
	<%@include file="../../snippets/meta.jsp"%>
	<link type="text/css"
	href="${pageContext.request.contextPath}/css/authority/authority.css"
	rel="stylesheet" />
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
						<a class="fr link3" href="javascript:;" id="edit_group">资源组管理</a>
			        	资源组：
			        	<c:choose>
		        			<c:when test="${empty param.groupId}">
				        		<a class="all" href="resource" >全部</a> 
				        	</c:when>
		        			<c:otherwise>
				        		<a class="link1" href="resource" >全部</a> 
				        	</c:otherwise>
			        	</c:choose>
			        	<c:forEach var="resourceGroup" items="${resourceGroups}" varStatus="status">
			        		<c:choose>
					        	<c:when test="${param.groupId == resourceGroup.id}">
					        		<a class="all" 
					        		href="resource?groupId=${resourceGroup.id}" >${resourceGroup.value}</a> 
					        	</c:when>
					        	<c:otherwise>
					        		<a class="link1" 
					        		href="resource?groupId=${resourceGroup.id}" >${resourceGroup.value}</a> 
					        	</c:otherwise>
					        </c:choose>
			        	</c:forEach>
			       	</p>
				</div>
				<div>
					<table width="100%" class="list-table view" cellspacing="0" cellpadding="0">
		            <thead>
		                <tr>
		                    <th width="15%">资源编码</th>
		                    <th width="37%">资源描述</th>
		                    <th width="41%">资源值</th>
		                    <th width="7%">操作</th>
		                </tr>
		            </thead>
		            <tbody>
		                <c:forEach items="${resources}" var="resource" varStatus="status">
		                	<tr resourceId="${resource.id}">
			                	<td >${resource.code}</td>
			                	<td class="txt_left">
									<p>${resource.description}</p>
			                		<p>资源组：<span name="resource_groups">
							                    <c:forEach items="${resource.resourceGroups}" var="rg"><font style="color:#454545;" rgId="${rg.id}" bind="${resource.id}"><b>${rg.value}</b></font> </c:forEach></span>
							                    [<a class="link2" href="javascript:;" resourceId="${resource.id}" bind="manageRelation">维护</a>]
							        </p>
								</td>
			                	<td class="txt_left">${resource.value}</td>
			                	<td >
			                		<a class="link2" href="javascript:;" bind="editResource" editResource="${resource.id}">编辑</a>	|	<a class="link2" href="javascript:;" bind="removeResource" removeResource="${resource.id}">删除</a>
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
 	
 	<div class="group_box" style="display:none" >
		<div>
			<p class="text_space">
				<span>资源组编码：</span><input class="input_txt" type="text" name="groupCode"> <span>资源组值：</span><input class="input_txt" type="text" name="groupValue" > 
			</p>
			<p class="text_space">
				<span>资源组描述：</span><input class="input_txt" type="text" name="groupDes"> <button class="addGroup" name="add_group_but_submit">提交</button>
			</p>
		</div>
		<c:if test="${!empty resourceGroups }">
			<div class="groups">
				<table class="list-table view" cellspacing="0" border="0" width="100%">
					<thead>
						<tr class="table_title">
							<th width="25%">资源组编码</th>
							<th width="30%">资源组值</th>
							<th width="32%">资源组描述</th>
							<th width="13%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${resourceGroups}" var="resourceGroup">
							<tr groupId="${resourceGroup.id}">
								<td>${resourceGroup.code}</td>
								<td >${resourceGroup.value}</td>
								<td>${resourceGroup.description}</td>
								<td>
									<a delGroupId="${resourceGroup.id}" href="javascript:;" class="gray" bind="removeGroup">删除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
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
	
 	<div class="edit_resource_box" style="display:none" ></div>
 	<%@include file="../../snippets/scripts.jsp"%>
	<script src="${contextPath}/js/authority/resource.js"></script>
</body>
</html>