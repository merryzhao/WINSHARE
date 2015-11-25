<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan-page"%>
<html>
<head>
<%@include file="../../snippets/meta.jsp"%>
<title>DC配送区域</title>
<link rel="stylesheet"
	href="${contextPath}/css/slidingtabs-horizontal.css">
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner ui-widget-content" id="content">
				<div id="ui-widget-content">
					<div class="ui-widget-content">
						<table class="list-table">
							<tr>
								<th>DC代号</th>
								<th colspan="7">配送区域</th>
							</tr>
							<c:forEach var="dcRule" items="${dcRules }">
							<tr class="hover">
								<td>
									<a href="/dc/area/edit?dcRuleId=${dcRule.id }" >${dcRule.location.name}</a>
								</td>
								<c:set value="1" var = "i"/>
								<c:forEach var="area" items="${dcRule.dcAreaList }" varStatus="status">
									<td>${area.area.name }</td>
									<c:if test="${i % 7 == 0 }"><c:out value="</tr><tr><td></td>" escapeXml="false"/></c:if>
									<c:set value="${i+1}" var = "i"/>
								</c:forEach>
							</tr>
							</c:forEach>
						</table>
					</div>
					<br />
					<div class="ui-widget-content" id="area">
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${contextPath }/js/dc/dcrule.js"></script>
</body>
</html>
