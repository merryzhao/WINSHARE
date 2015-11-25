<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="${contextPath}/css/jquery.treeTable.css" rel="stylesheet"/>
<title>联盟管理</title>
<style type="text/css">
.clcheckbox{ margin:0px;padding:0px;width:14px;}
input.availableyes{ margin-left: 22px}
input.availableno{margin-left: 8px}
input.usingapiyes{margin-left: 5px}
input.usingapino{margin-left: 8px}
label.error {
	padding:0.05em;
	color: red;
	font-weight: normal;
}
input.error {
	padding:0px;
	border: 1px solid red;
}
</style>
</head>
<body>
<div class="frame">
	<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-channel.jsp"%>
	
	<div class="frame-main">
<div id="content-result">
<h4>联盟列表        <a href="#" class="operate-link" onclick="add()">添加联盟</a></h4>
<c:choose>
	<c:when test="${!empty unionList}">
	<table class="list-table" id="tree-table">
		<colgroup>
			<col class="id"/>
			<col class="name"/>
			<col class="url"/>
			<col class="rate"/>
			<col class="createtime"/>
			<col class="userName"/>
			<col class="phone"/>
			<col class="email"/>
			<col class="available"/>
			<col class="operate"/>
		</colgroup>
		<tr><th>标识</th><th>名称</th><th>网址</th><th>佣金比例</th><th>创建时间</th><th>联系人</th><th>联系电话</th><th>联系邮箱</th><th>是否启用</th><th>操作</th></tr>
			<c:forEach var="union" items="${unionList}" varStatus="status">
			<c:set var="rowStatus" value="odd"/>
			<c:if test="${status.index%2==1}">
				<c:set var="rowStatus" value="even"/>
			</c:if>
			
			<tr class=" ${rowStatus}">			    
			    <td >${union.id}</td>
				<td >${union.name}</td>
				<td >${union.url}</td>
				<td >${union.rate}</td>
				<td ><fmt:formatDate value="${union.createTime}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
				<td >${union.userName}</td>
				<td >${union.phone}</td>
				<td >${union.email}</td>
				<td>
				<c:choose>
					<c:when test="${union.available}">	
					<span id="checkspan${status.index}">已启用</span></c:when>
					<c:otherwise>
					<span id="checkspan${status.index}">未启用</span></c:otherwise>
				</c:choose>
				</td>
				<td><a href="#" onclick="edit(${union.id},'${union.name}','${union.url}',${union.rate},'${union.createTime}','${union.userName}','${union.phone}','${union.email}',${union.available})" class="operate-link">修改</a>
				    |<a href="#" onclick="deleteUnion(${union.id})" class="operate-link" >删除</a>
				</td>
				
			</tr>
			</c:forEach>
			<tr><th>标识</th><th>名称</th><th>网址</th><th>佣金比例</th><th>创建时间</th><th>联系人</th><th>联系电话</th><th>联系邮箱</th><th>是否启用</th><th>操作</th></tr>   
	</table>
	</c:when>
</c:choose>
</div>
<div id="new">
	<form action="${contextPath}/union/create" method="POST" id="unionNew">
		<fieldset>
			<p><input type="hidden" name="show" value="true"/></p>
			<p><label>名		称：</label><input type="text" name="name" id="name"/></p>
			<p><label>网		址：</label><input type="text" name="url" id="url"/></p>
			<p><label>佣金比例：</label><input type="text" name="rate" id="rate"/></p>
			<p><label>创建时间：</label><input type="text" name="createTime" id="createTime" bind="datepicker" /></p>
			<p><label>联  系  人：</label><input type="text" name="userName" id="userName"/></p>
			<p><label>联系电话：</label><input type="text" name="phone" id="phone"/></p>
			<p><label>联系邮件：</label><input type="text" name="email" id="email"/></p>
			<p>
			是否启用：
			<input class="availableyes" type="radio" name="available" value="1" checked="checked">是
			<input class="availableno" type="radio" name="available" value="0" >否
			</p>
			<br>
			<div class="center"><button type="submit">保存</button></div>
		</fieldset>
	</form>
</div>
<div id="edit">
	<form action="" method="POST" id="unionEdit">
		<fieldset>
		    <input type="hidden" name="id" id="id"/>
		    <p><input type="hidden" name="show" value="true"/></p>
			<p><label>名		称：</label><input type="text" name="name" id="name1"/></p>
			<p><label>网		址：</label><input type="text" name="url" id="url1"/></p>
			<p><label>佣金比例：</label><input type="text" name="rate" id="rate1"/></p>
			<p><label>创建时间：</label><input type="text" name="createTime" id="createTime1" bind="datepicker" /></p>
			<p><label>联  系  人：</label><input type="text" name="userName" id="userName1"/></p>
			<p><label>联系电话：</label><input type="text" name="phone" id="phone1"/></p>
			<p><label>联系邮件：</label><input type="text" name="email" id="email1"/></p>
			<p>
			是否启用：
			<input class="availableyes" type="radio" name="available" value="1" checked="checked">是
			<input class="availableno" type="radio" name="available" value="0" >否
			</p>
			<br>
			<div class="center"><button type="submit">保存</button></div>
		</fieldset>
	</form>
</div>
<%@include file="../snippets/scripts.jsp" %>
<script src="${contextPath}/js/jquery.treeTable.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/union/list.js"></script>
</div>
</div>
</body>
</html>