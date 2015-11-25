<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<%@page import="java.net.URLDecoder" %>
<html>
<head>
<title>团购权重配置</title>
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
label.red {
	color: red;
}

input.long {
	width: 98%;
}

label.error {
	border: none;
	padding: 0.1em;
	color: red;
	font-weight: normal;
	background: none;
	padding-left: 16px;
}

td label.error {
	display: none !important;
}
#uploadPanel{
	margin:20px 0;
}
#uploadbtn{
	padding: 2px 10px;
}

</style>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<!-- 主体start -->
		<div class="frame-main">
			<!-- 核心内容部分div start-->
			<div class="frame-main-inner" id="content">
				<h4>团购权重配置</h4>
				<br />
				<div style="color: red">
					<p id="message">
						<c:if test="${msg == 'success'}">
							操作成功！<br />
						</c:if>
						<c:if test="${msg == 'error'}">
							操作失败！<strong>${errMsg}</strong><br/>
						</c:if>
					</p>
				</div>
				<div>
					<table class="list-table" style="width:45%">
						<tr>
							<td colspan="3" align="left">
								<input type="button" value="添加" id="addSupply" class="ui-button" />
								<input type="button" value="修改" id="updateSupply" class="ui-button" />
								<input type="button" value="删除" id="deleteSupply" class="ui-button" />
							</td>
						</tr>
						<tr>
							<th>选择</th>
							<th>dc</th>
							<th>销售级别</th>
							<th>权重数值</th>
						</tr>
						<c:forEach var="supply" items="${mrSupplys}">
							<tr id="tr_list${supply.id}"> 
								<td><input type="checkbox" id="checkbox_${supply.id}" class="checkbox_list" /></td>
								<td>${supply.dc.name}</td>
								<td>${supply.grade}</td>
								<td><fmt:formatNumber type="number" value="${(supply.weights)*100}" maxFractionDigits="2" />%</td>
							</tr>	
						</c:forEach>
					</table>
				</div>
			</div>
			<!-- 核心内容部分div end -->
		</div>
		<!-- 主体end -->
		<!-- 新增和修改界面start -->
		<div id="add_supply" style="display: none;height: 100%;width: 100%;">
			<form id="form" action="/replenishment/saveOrUpdateSupply" method="post">
				<table  style="width: 100%;height: 100%;border-collapse: collapse;">
					<tr height="30">
						<td align="right"><strong style="font: 16px;">dc：</strong></td>
						<td>
							<select id="dc.id" name="dc.id" class="dc" style="width: 120px; height: 25px;">
								<c:forEach var="code" items="${codes}">
									<option value="${code.id}">${code.name}</option>
								</c:forEach>
							</select>
						</td>
						<td align="right"><strong style="font: 16px;">销售级别：</strong></td>
						<td><input type="text" name="grade" id="grade" style="height: 25px;" /></td>
					</tr>
					<tr height="30">
						<td align="right"><strong style="font: 16px;">权重数值：</strong></td>
						<td><input type="text" name="weights" id="weights" style="height: 25px;" /></td>
					</tr>
					<tr height="30">
						<td colspan="4" height="30" align="center">
							<input type="hidden" id="id" name="id" />
							<input style="width: 50px" type="submit" value="完成" id="saveButton" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.methods.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/replenishment/supply.js"></script>
</body>
</html>