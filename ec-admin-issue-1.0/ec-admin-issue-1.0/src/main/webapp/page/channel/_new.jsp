<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp" %>
<html>
<head>
<%@include file="../snippets/meta.jsp" %>
<title>渠道管理</title>
</head>
<body>
<div id="content-result">
<div>
	<form action="${contextPath}/channel/create" method="POST" name="channelForm">
		<h5>新建渠道</h5>
		<fieldset>
			<p><label>渠道名称</label><input type="text" name="name" class="text"/></p>
			<p><label>所属分支</label>
				<select name="parent">
					<option value="${rootChannel.id}">${rootChannel.name}</option>
					<c:forEach var="channel" items="${rootChannel.children}">
					<option value="${channel.id}">${channel.name}</option>
					</c:forEach>
				</select>
				<a href="#">选择其它分支</a>
			</p>
			<p><label>渠道类别</label>
				<select name="type">
					<option>请选择类别</option>
					<c:forEach var="code" items="${codes}">
					<option value="${code.id}">${code.name}</option>	
					</c:forEach>
				</select>
			</p>
			<div class="center"><button>保存</button></div>
		</fieldset>
		<div class="mini-editor-close">X</div>
	</form>
</div>
</div>
<%@include file="../snippets/scripts.jsp" %>
</body>
</html>