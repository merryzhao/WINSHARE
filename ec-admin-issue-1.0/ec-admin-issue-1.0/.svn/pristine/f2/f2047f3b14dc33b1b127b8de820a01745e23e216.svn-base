<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>调查表</title>
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
		                    <th width="20%">序号</th>
		                    <th width="60%">标题</th>
		                    <th width="20%">操作</th>
		                </tr>
		            </thead>
		            <tbody>
		                <c:forEach items="${surveys}" var="survey" varStatus="status">
		                	<tr>
			                	<td >${survey.id}</td>
			                	<td >${survey.title}</td>
			                	<td >
			                		<a class="link2" href="javascript:;"  bind="editSurvey" editId="${survey.id}">编辑</a>	|	
			                		<a class="link2" href="javascript:;"  bind="delSurvey" delId="${survey.id}">删除</a>
			                	</td>	                	
		                	</tr>
		                </c:forEach>
		            </tbody>
		        </table>
		        <winxuan:page pagination="${pagination}" bodyStyle="html"/>
				</div>
				<center ><button class="add_survey" name="addSurvey">添加调查表</button></center>
			</div>
 		</div>
 		</div>
 	</div>

 	<div class="survey_box" style="display:none" >
		<div>
			<p class="text_space">
				<div>调查名：</div><input class="input_txt" type="text" name="sname" value=""/>
				<button class="manageSurvey" name="manageSurvey" >保存</button>
			</p>
		</div>
	</div>


	<%@include file="../snippets/scripts.jsp"%>
	<script src="${contextPath}/js/survey/list.js"></script>
</body>
</html>