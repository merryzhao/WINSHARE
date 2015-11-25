<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>渠道管理</title>
</head>
<body>
<div class="frame">
	<%@include file="../snippets/frame-top.jsp"%>
	<div class="frame-side">
		<div class="frame-side-inner">
			<div class="menu-nav">
				<h3>渠道管理</h3>
				<ul class="sub">
					<li class="selected">
						<label><a href="${contextPath}/channel/all" target="contentWrap">${allChannel.name}</a></label>
						<c:set var="channels" value="${allChannel.children}"></c:set>
						<c:if test="${!empty channels}">
							<ul class="nav">
							<c:forEach var="channel" items="${channels}">
								<li><a href="${contextPath}/channel/${channel.id}/list" target="contentWrap">${channel.name}</a></li>
							</c:forEach>
							</ul>
						</c:if>
					</li>
					<li><a href="${contextPath}/channel/new" target="contentWrap">新建渠道</a></li>
					<li>所有上下架规则</li>
					<li>新建上下架规则</li>
					<li>同步规则</li>
					<li>商品销售申请</li>
				</ul>
			</div>
			<div class="menu-nav">
				<h3>相关内容</h3>
				<ul class="sub">
					<li>[暂无相关内容]</li>
				</ul>
			</div>
			<div class="menu-nav">
				<h3>我的快捷入口</h3>
				<ul class="sub">
					<li>[暂无快捷入口]</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="frame-main">
		<div class="frame-main-inner" id="content">
			<iframe src="${contextPath}/channel/all" name="contentWrap" class="content-iframe" frameborder="0"></iframe>
		</div>
	</div>
	<div class="loading">数据处理中...　请稍候</div>
</div>
<%@include file="../snippets/scripts.jsp" %>
</body>
</html>