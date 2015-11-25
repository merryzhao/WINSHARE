<%@page pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
#cltextarea {
	float: left;
	width: 190px;
	height: 48px;
	font-size: 13px;
}
</style>
<%@include file="../snippets/meta.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/tablesorter/themes/blue/style.css"
	type="text/css" media="print, projection, screen" />
<title>发票金额修改</title>
</head>
<body>
	<!-- 引入JS -->
	<%@include file="../snippets/scripts.jsp"%>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
			
				<h4>发票金额修改</h4>
				<!-- 查询表单div -->
				<div id="queryForm">
					<form class="inline" action="/orderinvoice/updateOrderInvoiceList" method="post"
						name="queryForm" id="queryInvoiceForm">
						<table style="border: none;">
							<tr>
								<td align="center" class="text-weight" align="right">发票订单号:</td>
								<td><textarea id="cltextarea" name="information"></textarea></td>
								<td><button type="submit">查询</button></td>
							</tr>
						</table>
					</form>
				</div>
				<div style="color: red">
					<p id="messsage">
					  	<strong id="msg" style="display: none;">${msg}</strong>
					</p>
				</div>
				<div>
					<c:if test="${pagination!=null}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<c:if test="${listInvoice!=null}">
						<form action="" id="form" method="get">
							<table class="list-table">
							<thead>
								<tr>
									<th>发票订单号</th>
									<th>订单号</th>
									<th>运费</th>
									<th>发票金额</th>
									<th>开票日期</th>
								</tr>
							</thead>
							
							<tbody id="tbody">
								<c:if test="${fn:length(listInvoice)!=0}">
									<c:forEach items="${listInvoice}" var="dto">
										<tr>
											<td>${dto.orderInvoice.id}</td>
											<td><a href="/order/${dto.order.id}" target="content">${dto.order.id}</a></td>
											<td>${dto.order.deliveryFee}</td>
											<td id="td_${dto.orderInvoice.id}" class="td_click"
												onclick="Update.tdClickEvent"><a
												href="javascript:void(0);">${dto.orderInvoice.money}</a></td>
											<td><fmt:formatDate
													value="${dto.orderInvoice.createTime }"/></td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${fn:length(listInvoice)==0}">
									<tr>
										<td colspan="12">没有对应的发票数据</td>
									</tr>
								</c:if>
							</tbody>
							</table>
						</form>
					</c:if>
					<c:if test="${pagination!=null}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
				</div>
			</div>
		</div>
	<script type="text/javascript"
		src="${contextPath}/js/order/orderInvoice.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/orderinvoice/update_orderinvoice.js"></script>
	</div>
</body>
</html>