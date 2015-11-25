<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan-page"%>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>DC配送规则编辑</title>
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
			<div class="frame-main-inner ui-widget-content" id="content">
				<div id="ui-widget-content">
					<div class="ui-widget-content">
					<form id="edit">
						<input type="hidden" name="id" value="${dcRule.id }"/>
						<table class="list-table">
							<tr class="hover">
								<td>DC代号：</td>
								<td><c:if test="${dcRule.location.id == 110001 }">D803</c:if> <c:if test="${dcRule.location.id == 110002 }">D801</c:if> <c:if
										test="${dcRule.location.id == 110003 }">8A17</c:if> <c:if
										test="${dcRule.location.id == 110004 }">D818</c:if><c:if
										test="${dcRule.location.id == 110007 }">D819</c:if> <c:if
										test="${dcRule.location.id == 110005 }">8A19</c:if></td>
								
							</tr>
							<tr class="hover">
								<td>优先级：</td>
								<td>
									<select name="priority">
										<option value="1" <c:if test="${dcRule.priority == 1 }">selected</c:if>>1</option>
										<option value="2" <c:if test="${dcRule.priority == 2 }">selected</c:if>>2</option>
										<option value="3" <c:if test="${dcRule.priority == 3 }">selected</c:if>>3</option>
										<option value="4" <c:if test="${dcRule.priority == 4 }">selected</c:if>>4</option>
										<option value="5" <c:if test="${dcRule.priority == 5 }">selected</c:if>>5</option>
									</select>
								</td>
							</tr>
							<tr class="hover">
								<td>是否具备全国发货能力：</td>
								<td>
									<select name="countrywide">
										<option value="1" <c:if test="${dcRule.countrywide == true }">selected</c:if>>是</option>
										<option value="0" <c:if test="${dcRule.countrywide == false }">selected</c:if>>否</option>
									</select>
								</td>
							</tr>
							<tr class="hover">
								<td>是否可用：</td>
								<td>
									<select name="available">
										<option value="1" <c:if test="${dcRule.available == true }">selected</c:if>>是</option>
										<option value="0" <c:if test="${dcRule.available == false }">selected</c:if>>否</option>
									</select>
								</td>
							</tr>
							<tr class="hover">
								<td>退货地址：</td>
								<td><textarea rows="5" cols="20" style="width: 459px; height: 67px;" name="address">${dcRule.address}</textarea></td>
							</tr>
							<tr class="hover">
								<td>描述：</td>
								<td><textarea rows="5" cols="20" style="width: 459px; height: 67px;" name="description">${dcRule.description}</textarea></td>
							</tr>
							<tr>
								<td><input type="button" value="确认" onclick="dc.edit();"/></td>
							</tr>
						</table>
					</form>
					</div>
					<br />
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${contextPath }/js/dc/dcrule.js"></script>
</body>
</html>
