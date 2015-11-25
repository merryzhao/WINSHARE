<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan-page"%>
<html>
<head>
<%@include file="../../snippets/meta.jsp"%>
<title>DC配送区域维护</title>
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
					<form action="/dc/area/add" method="post" id="area-form">
						<input type="hidden" value="${dcRule.id }" name="dcRuleId" />
						<table class="list-table">
							<tr>
								<th colspan="3">DC代号</th>
								<th colspan="3">${dcRule.location.name }</th>
								<th><input type="button" value="编辑可添加的配送区域" id="add"/></th>
								<th><input type="button" value="编辑当前可配送的区域" id="delete"/></th>
							</tr>
							<tr></tr>
							<tr class="used-area-title">
								<th colspan="8">当前可配送区域</th>
							</tr>
							<tr class="used-area-content">
							<c:set value="1" var = "i"/>
							<c:forEach var="area" items="${dcRule.dcAreaList }">
								<td><input type="checkbox" value="${area.id }" name="deleteAreaId"><label onclick="dc.checked(${area.id})">${area.area.name }</label></td>
								<c:if test="${i % 8 == 0 }"><c:out value="</tr><tr class='used-area-content'>" escapeXml="false"/></c:if>
								<c:set value="${i+1}" var = "i"/>
							</c:forEach>
							</tr>
							<tr class="unused-area-title">
								<th colspan="8">可添加的配送区域</th>
							</tr>
							<tr class="unused-area-content">
							<c:set value="1" var = "i"/>
							<c:forEach var="area" items="${unusedProvinces }">
								<td><input type="checkbox" value="${area.id }" name="addAreaId"><label onclick="dc.checked(${area.id})">${area.name }</label></td>
								<c:if test="${i % 8 == 0 }"><c:out value="</tr><tr class='unused-area-content'>" escapeXml="false"/></c:if>
								<c:set value="${i+1}" var = "i"/>
							</c:forEach>
							</tr>
							<tr>
								<td>
									<input type="submit" value="确认添加" id="add_confirm"/>
									<input type="submit" value="确认删除" id="delete_confirm"/>
								</td>
							</tr>
						</table>
						</form>
					</div>
					<br />
					<c:set value="false" var="canSubmit"></c:set>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${contextPath }/js/dc/dcrule.js"></script>
</body>
</html>
