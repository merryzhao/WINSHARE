<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>代理商家信息管理</title>
<style type="text/css">
input.codingContent {
	margin-left: 50px;
}

input.sellerName {
	margin-left: 50px;
}

input.productAuthor {
	margin-left: 25px;
}

label.moretrem {
	margin-left: 25px;
	color: #5CACEE;
}

div.moreterm {
	display: none;
}

.log{
	width:100%;
}
</style>
<!-- 引入JS -->
</head>
<body>
	<div class="frame">
 	<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
	<%@include file="../snippets/frame-left-channel.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>代理商信息管理:</h4>
				<!-- 查询表单div -->
				<div id="queryForm">
					<form class="inline" action="/agent/findagent" method="post" >
					<table>
					<tr>
					<td>查询条件：
					<span>用户账号 ：<input type="text" id="userName" name="userName"/></span>
					</td>
					<td>
						<span style="padding-left: 10px;"><input type="submit"  id="cx" value="检索" style="width: 60px;height: 30px;"></span>
					</td>
					</tr>
					<tr>
					<td colspan="2">代理商类型：<input type="checkbox" checked="checked" name="isAgent" id="isAgent"  value="agent"/> 代理商</td>
					</tr>
					</table>
 					</form>
				</div>
				<!-- 查询结果展示div -->
				<div>
				<br/>
				<c:if test="${pagination!=null}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<form action="">
						<table class="list-table" id="datatable">
 								<tr>
									<th>用户账号</th>
									<th>用户注册邮箱</th>
									<th>代理商类型</th>
									<th>商品实洋比例</th>
									<th>操作</th>
								</tr>
						 	<c:if test="${fn:length(customers)!=0}">
 						 	<c:forEach var="customer" items="${customers}">
								<tr>
									<td class="usernametd">${customer.name}</td>
									<td>${customer.email}</td>
 									<td class="isagentcss"><c:choose><c:when test="${customer.channel.id==6}">代理商</c:when>
 									<c:otherwise>非代理商</c:otherwise></c:choose>
 									</td>
									<td class="discountcss">
									<c:choose><c:when test="${customer.channel.id==6}">${customer.discount}</c:when>
 										<c:otherwise></c:otherwise></c:choose>
 									</td>
									<td><span style="display: none;" class="cid">${customer.id}</span><c:choose>
									<c:when test="${customer.channel.id==6}"><a href="javascript:void(0)" class="cancelAgent">取消代理商资格</a></c:when>
 										<c:otherwise><a href="javascript:void(0)" class="setAgent">设置为代理商</a></c:otherwise></c:choose>
 										<a href="javascript:void(0)" class="findLog">用户日志</a>
 										</td>
								</tr>
							</c:forEach> 
							</c:if>
						  <c:if test="${fn:length(customers)==0}">
						  <tr>
						  	<td align="center" colspan="12">暂无数据</td>
						  </tr>
						  </c:if>
							 
 								<tr>
									<th>用户账号</th>
									<th>用户注册邮箱</th>
									<th>代理商类型</th>
									<th>商品实洋比例</th>
									<th>操作</th>
								</tr>
						</table>
					</form>
					<c:if test="${pagination!=null&&pagination.pageCount!=0}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
				</div>

			</div>
		</div>
		<div class="loading">数据处理中... 请稍候</div>
	</div>
	<div id="setAgent">
	<center>
	 <table>
	 <tr>
	 	<td>用户账号：<span id="uName"></span></td>
	</tr>
	<tr>
	 	<td>实洋比例：<input type="text" id="discount" name="discount"/> </td>
	</tr>
	<tr>
	 	<td align="center"><input style="width: 80px;" type="button" id="sbm" name="sbm" value="提交"/> </td>
	 </tr>
	 </table>
	 </center>
	</div>
	<div id="statusLog">
		<table class="log">
			<thead>
				<tr style="font-weight: bold;">
				<td>变更渠道</td>
				<td>折扣</td>
				<td>操作人</td>
				<td>日期</td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<input type="hidden" name="cusid" id="cusid">
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript" src="${contextPath}/js/agent/agent.js"></script>
 </body>
</html>