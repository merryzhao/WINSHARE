<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>客户端版本管理</title>
<style type="text/css">
textarea {
	width: 120px;
	height: 50px;
	margin-bottom: -5px;
}
input{vertical-align:middle;margin:0px;}
</style>
<!-- 引入JS -->
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="tabs">
					<div id="result">
						<c:if test="${pagination!=null}">
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
						</c:if>
						<c:if test="${versionList!=null}">
							<table class="list-table">
								<tr>
									<th>版本号</th>
									<th>操作系统</th>
									<th>更新地址</th>
									<th>更新内容</th>
									<th>创建者</th>
									<th>创建时间</th>
									<th>是否是最新版本</th>
									<th>操作</th>
								</tr>
								<c:if test="${fn:length(versionList)!=0}">
									<tbody>
									<c:forEach items="${versionList}" var="version">
										<tr class="data">
											<td>${version.version}</td>
											<td>
												<c:if test="${version.system == 1 }">andriod</c:if>
												<c:if test="${version.system == 2 }">ios</c:if>
											</td>
											<td>${version.updateAddress}</td>
											<td width="200px"><c:if test="${fn:length(version.updateInfo)>40}">${fn:substring(version.updateInfo, 0, 40)}...</c:if><c:if test="${fn:length(version.updateInfo)<=40}">${version.updateInfo}</c:if></td>
											<td>${version.creator.name }</td>
											<td><fmt:formatDate value="${version.createTime}" type="both"/></td>
											<td><c:if test="${version.latest}">是</c:if><c:if test="${!version.latest}">否</c:if></td>
											<td><a href="/version/goUpdate?id=${version.id }" >修改</a></td>
										</tr>
									</c:forEach>
									</tbody>
								</c:if>
								<c:if test="${fn:length(versionList)==0}">
									<tr>
										<td colspan="8">暂无数据</td>
									</tr>
								</c:if>
								<tr>
									<th>版本号</th>
									<th>操作系统</th>
									<th>更新地址</th>
									<th>更新内容</th>
									<th>创建者</th>
									<th>创建时间</th>
									<th>是否是最新版本</th>
									<th>操作</th>
								</tr>
							</table>
						</c:if>
						<c:if test="${pagination!=null}">
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>