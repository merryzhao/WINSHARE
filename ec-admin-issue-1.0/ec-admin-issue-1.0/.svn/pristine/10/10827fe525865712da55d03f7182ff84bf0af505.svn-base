<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>批量上传补开发票</title>
</head>
<body onload="chackValue();">
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>批量补开发票:</h4>
				<!-- 查询表单div -->
				<div>
					<form action="" method="post" id="excelForm"
						enctype="multipart/form-data">
						<table>
							<tr>
								<td>请下载模板：<a href="/excel/orderinvoice/orderInvoiceTemplate.xls">模板下载</a>
									<input type="file" name="file"> <input type="submit"
									value="上传" id="sbm"><br /> <span
									style="color: red; font-size: 15px">${msg}</span></td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 查询结果展示div -->
				<div>
					<br />
					<table class="list-table" id="datatable">
						<tr>
							<th>发票订单号</th>
							<th>订单号</th>
							<th>外部订单号</th>
							<th>发票抬头</th>
							<th>注册名</th>
							<th>下单日期</th>
							<th>运费</th>
							<th>商品数量</th>
							<th>发票金额</th>
							<th>收件人</th>
							<th>操作人</th>
							<th>开票日期</th>
							<th>开票次数</th>
							<th>操作</th>
							<th>详细</th>
						</tr>
						<c:if test="${!empty orderInvoices}">
							<c:forEach var="dto" items="${orderInvoices}">
								<tr>
									<td>${dto.id}</td>
									<td><a href="/order/${dto.order.id}" target="content">${dto.order.id}</a>
									</td>
									<td>${dto.order.outerId }</td>
									<td>${dto.title}</td>
									<td>${dto.order.customer.name }</td>
									<td><fmt:formatDate value="${dto.order.createTime}" /></td>
									<td>${dto.order.deliveryFee }</td>
									<td>${dto.quantity}</td>
									<td>${dto.money }</td>
									<td>${dto.consignee}</td>
									<td>${dto.operator.name }</td>
									<td><fmt:formatDate
											value="${dto.createTime }" /></td>
									<td>${fn:length(dto.order.invoiceList) }</td>
									<td> <c:if test="${dto.order.processStatus.id==8004}">
													<a href="/orderinvoice/invoice/${dto.order.id}">补开发票</a>
												</c:if> <c:if test="${dto.order.processStatus.id==8005}">
													<a href="/orderinvoice/invoice/${dto.order.id}">补开发票</a>
												</c:if> <c:if test="${dto.order.processStatus.id==8011}">
													<a href="/orderinvoice/invoice/${dto.order.id}">补开发票</a>
												</c:if>
											</td>
											<td><a
												href="/orderinvoice/orderinvoice_list?orderid=${dto.order.id}">详细</a>
											</td>
								</tr>
							</c:forEach>
						</c:if>
						<tr>
							<th>发票订单号</th>
							<th>订单号</th>
							<th>外部订单号</th>
							<th>发票抬头</th>
							<th>注册名</th>
							<th>下单日期</th>
							<th>运费</th>
							<th>商品数量</th>
							<th>发票金额</th>
							<th>收件人</th>
							<th>操作人</th>
							<th>开票日期</th>
							<th>开票次数</th>
							<th>操作</th>
							<th>详细</th>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- 引入JS -->
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>