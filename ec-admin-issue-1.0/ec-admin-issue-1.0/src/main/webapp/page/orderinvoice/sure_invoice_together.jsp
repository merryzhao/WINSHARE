<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>确认开票</title>


<style type="text/css">
#invoice-body {
	margin-top: 50px;
	width: 600px;
	height: 500px;
	display: none;
}

#ninvoice-body {
	margin-top: 50px;
	width: 600px;
	height: 500px;
}

.iinfo-lable {
	padding: 0 0 0 15px;
}

select.select-size {
	width: 135px;
}

select.area-size {
	width: 80px;
}

.cbstyle {
	width: 70px;
	height: 25px;
	margin-right: 30px;
}

.cnstyle {
	width: 70px;
	height: 25px;
	margin-left: 30px;
}

.list-table1 {
	width: 100%;
	border: 1px solid #DFDFDF
}

table.list-table1 tr {
	height: 30px;
	line-height: 30px;
	border: 1px solid #DFDFDF;
}

table.list-table1 tr.hover {
	background: #ffffe1
}

table.list-table1 th {
	background: #6293BB;
	color: #fff;
	font-weight: bolder;
}

table.list-table1 .select,table.list-table .id {
	width: 100px
}

table.list-table1.state {
	width: 10px
}

table.list-table1 .date-time {
	width: 140px
}

table.list-table1 .operate {
	with: 60px;
}

table.list-table1 a.operate-link {
	padding: 0 10px;
}

table.list-table1 th input,table.list-table td input {
	margin: 0px;
}
</style>
</head>
<script type="text/javascript" src="${contextPath}/js/jQuery.js"></script>
<script type="text/javascript" src="${contextPath}/js/orderDetail.js"></script>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<center>
					<div class="step">
				<img src="${contextPath}/css/images/invoice_together_step_2.jpg">
			</div>
					<div id="ninvoice-body">
						<div>订单号:</div>
							<label id="ids" >${orderIds}</label>
						<table class="list-table1">	
							<tr>
								<td align="right">抬头类型</td>
								<td><label class="iinfo-lable" id="ninvoiceTpye">${titleType}</label>
								</td>
								<td align="right">发票抬头</td>
								<td><label class="iinfo-lable" id="ninvoiceTitle">${orderInvoiceCreateForm.title}</label>
								</td>
							</tr>
							<tr>
								<td align="right">发票金额</td>
								<td colspan="3"><label class="iinfo-lable" id="nmoney">${orderInvoiceCreateForm.money}</label>
								</td>
							</tr>
							<tr>
								<td align="right">发票内容</td>
								<td><label class="iinfo-lable">图书/百货</label></td>
								<td align="right">商品数量</td>
								<td><label class="iinfo-lable">${orderInvoiceCreateForm.quantity}</label>
								</td>
							</tr>
							<tr>
								<td align="right">收货人</td>
								<td><label class="iinfo-lable" id="nconsignee">${orderInvoiceCreateForm.consignee}</label>
								</td>
								<td align="right">电话</td>
								<td><label class="iinfo-lable" id="nmobile">${orderInvoiceCreateForm.mobile}</label>
								</td>
							</tr>
							<tr>
								<td align="right">电子邮件</td>
								<td><label class="iinfo-lable" id="nemail">${orderInvoiceCreateForm.email}</label>
								</td>
								<td align="right">邮编</td>
								<td><label class="iinfo-lable" id="nzipCode">${orderInvoiceCreateForm.zipCode}</label>
								</td>
							</tr>
							<tr>
								<td align="right">区域</td>
								<td colspan="3"><label class="iinfo-lable" id="narea">${area}</label>
								</td>
							</tr>
							<tr>
								<td align="right">详细地址</td>
								<td colspan="3"><label class="iinfo-lable" id="naddress">${orderInvoiceCreateForm.address}</label>
								</td>
							</tr>
							<tr>
								<td colspan="4" align="center"><input type="button" id="prestep"
									class="cbstyle"  value="上一步" />
									<input type="button" class="cnstyle" id="submit"
									value="提交" /></td>
							</tr>

						</table>
					</div>

					<div id="invoice-body">
						<form:form commandName="orderInvoiceCreateForm" name="invoiceForm"
							action="${contextPath}/orderinvoice/save_together" method="post">
							<token:token></token:token>
							<table class="list-table1">
								<tr>
									<td align="right">订单号</td>
									<td colspan="3"><label class="iinfo-lable" id="orderId">${orderIds}</label>
										<form:hidden path="orderIds" /></td>
								</tr>
								<tr>
									<td align="right">抬头类型</td>
									<td><label class="iinfo-lable"> <form:input
												path="titleType" /> </label></td>
									<td align="right">发票抬头</td>
									<td><label class="iinfo-lable"><form:input
												path="title"></form:input> </label></td>
								</tr>
								<tr>
									<td align="right">发票金额</td>
									<td colspan="3"><label class="iinfo-lable"> <form:input
												path="money" /> </label></td>
								</tr>
								<tr>
									<td align="right">发票内容</td>
									<td><label class="iinfo-lable">图书/百货</label> <form:hidden
											path="content" /></td>
									<td align="right">商品数量</td>
									<td><label class="iinfo-lable"> <form:input
												path="quantity" /> </label></td>
								</tr>
								<tr>
									<td align="right">收货人</td>
									<td><label class="iinfo-lable"> <form:input
												path="consignee" /> </label>
									</td>
									<td align="right">电话</td>
									<td><label class="iinfo-lable"> <form:input
												path="mobile" /> </label></td>
								</tr>
								<tr>
									<td align="right">电子邮件</td>
									<td><label class="iinfo-lable"> <form:input
												path="email" /> </label>
									</td>
									<td align="right">邮编</td>
									<td><label class="iinfo-lable"> <form:input
												path="zipCode" /> </label>
									</td>
								</tr>
								<tr>
									<td align="right">区域</td>
									<td colspan="3"><label class="iinfo-lable"> <!-- 国家 -->
											<form:input path="country" /> <!-- 省 --> <form:input
												path="province" /> <!-- 城市 --> <form:input path="city" />
											<!-- 区县 --> <form:input path="district" /> </label>
									</td>
								</tr>
								<tr>
									<td align="right">详细地址</td>
									<td colspan="3"><label class="iinfo-lable"> <form:input
												size="83" path="address" /> </label>
									</td>
								</tr>
							</table>							
						</form:form>
					</div>
				</center>
			</div>
		</div>
	</div>
</body>
<input type="hidden" id="path" value="${contextPath}"></input>
<script type="text/javascript" 
		src="${contextPath}/js/orderinvoice/sure_orderinvoice.js"></script> 
</html>