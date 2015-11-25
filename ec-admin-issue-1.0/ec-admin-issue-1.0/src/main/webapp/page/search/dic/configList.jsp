<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>抓取配置信息</title>
	<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
	<%@include file="../../snippets/meta.jsp"%>
	<link type="text/css" href="${pageContext.request.contextPath}/css/search/dic.css" rel="stylesheet" />
</head>
<body>
	<div class="frame" >
		<!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-search.jsp"%>
		<div class="frame-main">
			<div class="frame-main-inner" id="content">
				<center ><button class="add_new_config" name="addNewConfig">添加抓取信息</button></center>
				<div>
					<table width="100%" class="list-table view" cellspacing="0" cellpadding="0">
		            <thead>
		                <tr>
		                    <th width="30%">抓取地址</th>
		                    <th width="10%">抓取来源</th>
		                    <th width="10%">解析脚本</th>
		                    <th width="10%">编码格式</th>
		                    <th width="10%">操作</th>
		                </tr>
		            </thead>
		            <tbody>
		                <c:forEach items="${configs}" var="config" varStatus="status">
		                	<tr>
			                	<td >${config.url}</td>
			                	<td >${config.source.name}</td>
			                	<td >${config.script}</td>
			                	<td >${config.charsetName}</td>
			                	<td >
			                		<a class="link2" href="javascript:;" bind="removeConfig" removeConfig="${config.id}">删除</a>
			                	</td>		                	
		                	</tr>
		                </c:forEach>
		            </tbody>
		        </table>
		        <winxuan:page pagination="${pagination}" bodyStyle="html"></winxuan:page>
				</div>
 		</div>
 		</div>
 	</div>

 	<div class="config" style="display:none" >
		<div>
			<p class="text_space">
				<span>抓取地址：</span><input class="input_txt" type="text" name="address" value=""/>  
				<span>解析脚本：</span><input class="input_txt" type="text" name="analyseScript" value=""/> 
				<span>编码格式：</span><input class="input_txt" type="text" name="charsetFormat" value=""/> 
				<button class="manageWord" name="addConfig" >提交</button>
			</p>
		</div>
	</div>

 	<%@include file="../../snippets/scripts.jsp"%>
 	<script src="${contextPath}/js/search/config.js"></script>
</body>
</html>