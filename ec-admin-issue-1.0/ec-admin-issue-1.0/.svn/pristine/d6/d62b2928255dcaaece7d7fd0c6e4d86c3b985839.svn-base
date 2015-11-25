<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>词典列表</title>
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
			
				<center ><button class="add_new_word" name="addNewWord">词典管理</button></center>
				<div>
					<div class='query'><input class="input_txt" type="text" name="queryWord" value="<c:if test="${!empty queryWord}">${queryWord}</c:if>"/><button class="query_dic" name="query">查询</button></div>
					<table width="100%" class="list-table view" cellspacing="0" cellpadding="0">
		            <thead>
		                <tr>
		                    <th width="20%">词语</th>
		                    <th width="15%">同义词</th>
		                    <th width="15%">相关推荐词</th>
		                    <th width="10%">词语来源</th>
		                    <th width="10%">创建时间</th>
		                    <th width="10%">审核人</th>
		                    <th width="10%">审核时间</th>
		                    <th width="10%">操作</th>
		                </tr>
		            </thead>
		            <tbody>
		                <c:forEach items="${words}" var="word" varStatus="status">
		                	<tr wordId="${word.id}">
			                	<td >${word.word}</td>
			                	<td ><c:forEach items="${word.synonym2s}" var="synonym" varStatus="status">${synonym.word} </c:forEach></td>
			                	<td ><c:forEach items="${word.children}" var="child" varStatus="status">${child.word} </c:forEach></td>
			                	<td >${word.source.name}</td>
			                	<td ><fmt:formatDate type="date" value="${word.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                	<td >${word.auditBy.name}</td>
			                	<td ><fmt:formatDate type="date" value="${word.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                	<td >
			                		<a class="link2" href="javascript:;" bind="removeWord" removeWord="${word.id}">删除</a>
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

 	<div class="word" style="display:none" >
		<div>
			<p class="text_space">
				<span>词语：</span><input class="input_txt" type="text" name="wordItem" value=""/>  
				<span>同义词：（,分割）</span><input class="input_txt" type="text" name="synonymItem" value=""/> 
				<span>推荐词：（,分割）</span><input class="input_txt" type="text" name="recommendItem" value=""/> 
				<button class="manageWord" name="manage_word_but_submit" >提交</button>
			</p>
		</div>
	</div>

 	<%@include file="../../snippets/scripts.jsp"%>
 	<script src="${contextPath}/js/search/dic.js"></script>
</body>
</html>