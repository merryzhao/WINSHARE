<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<link href="${contextPath}/css/jquery.treeTable.css" rel="stylesheet"/>
<title>渠道管理</title>
</head>
<body>
<div class="frame">
	<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-channel.jsp"%>
	
	<div class="frame-main">
		<div class="frame-main-inner" id="content">
			<iframe src="${contextPath}/channel/list" name="contentWrap" class="content-iframe" frameborder="0"></iframe>
		</div>
		<div id="channel-editor" style="display:none;">
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
								<c:forEach var="code" items="${channelType.children}">
								<option value="${code.id}">${code.name}</option>	
								</c:forEach>
							</select>
						</p>
						<div class="center"><button binding="saveChannelForm">保存</button></div>
					</fieldset>
				</form>
			</div>
	</div>
	<div class="loading">数据处理中...　请稍候</div>
</div>
<%@include file="../snippets/scripts.jsp" %>
<script src="${contextPath}/js/jquery.treeTable.min.js"></script>
<script src="${contextPath}/js/channel/index.js"></script>
</body>
</html>