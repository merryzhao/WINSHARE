<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
#cltextarea{
float : left;
width : 190px;
height : 48px;
font-size: 13px;}
</style>
<%@include file="../snippets/meta.jsp"%>
<title>开票历史记录</title>
</head>
<body>
	<!-- 引入JS -->
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<h4>开票历史记录</h4><br/>
				
				<div>
						<form action="" id="form" method="get">
							<table class="list-table">
								<tr>
									<th>发票订单号</th>
									<th>开票日期</th>
									<th>抬头类型</th>
									<th>发票抬头</th>
									<th>承运商</th>
									<th>寄单号</th>
									<th>发货日期</th>
								</tr>
								<c:forEach items="${listInvoice}" var="dto">
								<c:if test="${not empty dto.orderInvoice.id }">
									<tr>
										<td>${dto.orderInvoice.id}
										</td>
										<td><fmt:formatDate value="${dto.orderInvoice.createTime}" type="both"></fmt:formatDate></td>
										<td>${dto.orderInvoice.titleType.name }</td>
										<td>${dto.orderInvoice.title }</td>
										<td>${dto.orderInvoice.deliveryCompany.company }</td>
										<td>${dto.order.deliveryCode }</td>
										<td><fmt:formatDate value="${dto.orderInvoice.deliveryTime }" type="both" ></fmt:formatDate></td>
									</tr>
								</c:if>
									<c:if test="${empty dto.orderInvoice.id }"><tr><td colspan="7"><h3>没有数据</h3></td></tr></c:if>
								</c:forEach>
								<tr>
									<th>发票订单号</th>
									<th>开票日期</th>
									<th>抬头类型</th>
									<th>发票抬头</th>
									<th>承运商</th>
									<th>运单号</th>
									<th>发货日期</th>
								</tr>
							</table>
						</form>
				</div>

			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>