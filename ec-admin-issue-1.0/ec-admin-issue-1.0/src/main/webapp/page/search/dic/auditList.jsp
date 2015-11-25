<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>待审核词典列表</title>
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
					<div style="font-weight: bolder;">审核规则：
						<p>1、如果该词为非中文或者为中文与英文、数字的组合词，比如：java，暗黑3等。审核不通过</p>
						<p>2、词长超过4或者小于2，审核不通过</p>
						<p>3、如果词语中包含副词或助词等，需要通过到百度、亚马逊等搜索确认是否是新词或者书名等，否则审核不通过</p>
						<p>4、如果词语是人名或者书名，审核通过</p>
						<p>5、其他情况，审核通过</p>
						<!-- <p>6、如果词语是复合词，比如：“基础教程”，显然该词是由“基础”和“教程”组成的，审核时需要确认“基础”和“教程”是否已经入库，如果没有入库，手动添加，同时“基础教程”审核通过</p> -->
						
					</div>
					<div class='query'><input class="input_txt" type="text" name="queryWord" value="<c:if test="${!empty queryWord}">${queryWord}</c:if>"/><button class="query_dic" name="query">查询</button></div>
					<table width="100%" class="list-table view" cellspacing="0" cellpadding="0">
		            <thead>
		                <tr>
		                    <th width="10%"><input type="checkbox" bind="selectAll"/><a style="color: #FFFFFF;" href="javascript:;" bind="auditBatch"">通过</a>	|	<a style="color: #FFFFFF;" href="javascript:;" bind="unauditedBatch"">不通过</a></th>
		                    <th width="25%">中文词语</th>
		                    <th width="20%">来源</th>
		                    <th width="20%">创建时间</th>
		                    <th width="20%">审核</th>
		                </tr>
		            </thead>
		            <tbody>
		                <c:forEach items="${words}" var="word" varStatus="status">
		                	<tr wordId="${word.id}">
		                		<td><input type="checkbox" value="${word.id}" bind="item"/></td>
			                	<td >${word.word}</td>
			                	<td >${word.source.name}</td>
			                	<td ><fmt:formatDate type="date" value="${word.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                	<td >
			                		<a class="link2" href="javascript:;" bind="audit" audit="${word.id}">通过</a>	|	<a class="link2" href="javascript:;" bind="unaudited" unaudited="${word.id}">不通过</a>
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

 	<%@include file="../../snippets/scripts.jsp"%>
 	<script src="${contextPath}/js/search/dicAudit.js"></script>
</body>
</html>