<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>专题</title>
	<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
	<%@include file="../snippets/meta.jsp"%>
	<link type="text/css"
	href="${pageContext.request.contextPath}/css/subject/subject.css"
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
		                    <th width="15%">专题序列</th>
		                    <th width="25%">专题名</th>
		                    <th width="25%">专题标题</th>
		                    <th width="5%">专题类型</th>
		                    <th width="15%">专题标签</th>
		                    <th width="15%">操作</th>
		                </tr>
		            </thead>
		            <tbody>
		                <c:forEach items="${subjects}" var="subject" varStatus="status">
		                	<tr>
			                	<td >${subject.id}</td>
			                	<td >${subject.name}</td>
			                	<td >${subject.title}</td>
			                	<td >${subject.sort.name}</td>
			                	<td ><a target="_blank" href="${subject.tagurl}">${subject.tagurl}</td>
			                	<td >
			                		<a class="link2" href="javascript:;"  bind="editSubject" editId="${subject.id}">编辑</a>	|	
			                		<a class="link2" href="javascript:;"  bind="delSubject" delId="${subject.id}">删除</a>
			                		<!-- <a class="link2" href="javascript:;"  bind="deploy"><c:if test="${subject.deploy == true}">停用</c:if><c:if test="${subject.deploy == false}">发布</c:if></a> -->
			                	</td>	                	
		                	</tr>
		                </c:forEach>
		            </tbody>
		        </table>
		        <winxuan:page pagination="${pagination}" bodyStyle="html"/>
				</div>
				<center ><button class="add_subject" name="addSubject">添加专题</button></center>
			</div>
 		</div>
 		</div>
 	</div>

 	<div class="subject_box" style="display:none" >
		<div>
			<p class="text_space">
				<div>专题名：</div><input class="input_txt" type="text" name="sname" value=""/>  
				<div>专题标题：</div><input class="input_txt" type="text" name="stitle" value=""/> 
				<div>专题标签：</div><input class="input_txt" type="text" name="surl" value=""/> 
				<div>专题类型：</div>
				<select  id="select" name="select" >
					<c:forEach items="${codes}" var="code" varStatus="status">
						<option value="${code.id}" <c:if test="${code.id==Code.PRODUCT_SORT_BOOK}">selected="selected"</c:if>>${code.name}</option>
					</c:forEach>
			      </select> 
				<button class="manageSubject" name="manageSubject" >保存</button>
			</p>
		</div>
	</div>

 	<div class="floor_box" style="display:none" >
		<div>
			<p class="text_space">
				<div>楼层数（不包括横幅）：</div><input class="input_txt" type="text" name="floor" value=""/>  
				<input class="input_txt" type='hidden' name="editIdHidden" value="" />  
				<button class="manageSubject" name="floorSet" >保存</button>
			</p>
		</div>
	</div>

	<%@include file="../snippets/scripts.jsp"%>
	<script src="${contextPath}/js/subject/list.js"></script>
</body>
</html>