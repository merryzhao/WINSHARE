<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>未审核通过词典列表</title>
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
				<div>
					<div class='query'><input class="input_txt" type="text" name="queryWord" value="<c:if test="${!empty queryWord}">${queryWord}</c:if>"/><button class="query_dic" name="query">查询</button></div>
					<table width="100%" class="list-table view" cellspacing="0" cellpadding="0">
		            <thead>
		                <tr>
		                    <th width="20%">中文词语</th>
		                    <th width="20%">来源</th>
		                    <th width="20%">创建时间</th>
		                    <th width="20%">审核人</th>
		                    <th width="20%">审核时间</th>
		                </tr>
		            </thead>
		            <tbody>
		                <c:forEach items="${words}" var="word" varStatus="status">
		                	<tr>
			                	<td >${word.word}</td>
			                	<td >${word.source.name}</td>
			                	<td ><fmt:formatDate type="date" value="${word.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>		                	
			                	<td >${word.auditBy.name}</td>		                	
			                	<td ><fmt:formatDate type="date" value="${word.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>		                	
		                	</tr>
		                </c:forEach>
		            </tbody>
		        </table>
		        <winxuan:page pagination="${pagination}" bodyStyle="html"></winxuan:page>
				</div>
 		</div>
 		</div>
 	</div>

 	<%@include file="../../snippets/scripts.jsp"%>
 	<script src="${contextPath}/js/search/dicUnaudit.js"></script>
</body>
</html>