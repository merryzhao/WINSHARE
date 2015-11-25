<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan-page"%>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>DC配送规则列表</title>
<link rel="stylesheet"
	href="${contextPath}/css/slidingtabs-horizontal.css">
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div>
					<table class="list-table">
						<tr>
							<th>dc代号</th>
							<th>优先级</th>
							<th>是否具备全国发货能力</th>
							<th>退货地址</th>
							<th>描述</th>
							<th>是否可用</th>
							<th>操作</th>
						</tr>
						<c:forEach var="dcRule" items="${dcRules}">
							<tr>
								<td><c:if test="${dcRule.location.id == 110001 }">D803</c:if>
									<c:if test="${dcRule.location.id == 110002 }">D801</c:if> <c:if
										test="${dcRule.location.id == 110003 }">8A17</c:if> <c:if
										test="${dcRule.location.id == 110004 }">D818</c:if> <c:if
										test="${dcRule.location.id == 110007 }">D819</c:if> <c:if
										test="${dcRule.location.id == 110005 }">8A19</c:if></td>
								<td>${dcRule.priority }</td>
								<td><c:choose>
										<c:when test="${dcRule.countrywide }">是</c:when>
										<c:otherwise>否</c:otherwise>
									</c:choose></td>
								<td>${dcRule.address }</td>
								<td>${dcRule.description }</td>
								<td><c:choose>
										<c:when test="${dcRule.available }">是</c:when>
										<c:otherwise>否</c:otherwise>
									</c:choose></td>
								<td>
									<a href="/dc/${dcRule.id }">编辑</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>
