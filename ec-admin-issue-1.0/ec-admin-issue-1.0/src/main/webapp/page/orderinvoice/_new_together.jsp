<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>合并开票</title>

<script type="text/javascript">
	
</script>
<style type="text/css">
#invoice-body {
	margin-top: 50px;
	width: 600px;
	height: 500px;
}

#ninvoice-body {
	margin-top: 50px;
	width: 600px;
	height: 500px;
	display: none;
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

.errors {
	background: none;
	border: 0px;
	color: #FF0000;
}

#ids {width =600px;
}

label.error {
	padding: 0.1em;
}

#orderInvoiceCreateForm input.error {
	padding: 0px;
	border: 2px solid red;
}
</style>
<%@include file="../snippets/meta.jsp"%>
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
				<center>
					<div class="step">
						<img src="${contextPath}/css/images/invoice_together_step_1.jpg">
					</div>
					<div id="invoice-body">
						<form:form commandName="orderInvoiceCreateForm"
							action="${contextPath}/orderinvoice/goSure_together"
							method="post">
							<div>订单号:</div>
							<label id="ids">${orderIds}</label>
							<form:hidden path="orderIds" />
							<table class="list-table1">
								<tr>
									<td align="right">抬头类型</td>
									<td><label class="iinfo-lable"> <form:select
												path="titleType" cssClass="select-size"
												items="${titleTypes}" id="" multiple="" itemLabel="name"
												itemValue="id" /> </label></td>
									<td align="right">发票抬头</td>
									<td><label class="iinfo-lable"><form:input
												path="title"></form:input> </label></td>
								</tr>
								<tr style="height: 10px">
									<td></td>
									<td><label class="iinfo-lable"><font
											class="errors"><form:errors path="titleType"></form:errors>
										</font>
									</label></td>
									<td></td>
									<td><label class="iinfo-lable"><font
											class="errors"> <form:errors path="title"></form:errors>
										</font>
									</label></td>
								</tr>
								<tr style="height: 10px">
									<td align="right">发票金额</td>
									<td colspan="3"><label class="iinfo-lable"> <form:input
												path="money" /><span>发货总实洋：${totalSalePrice}</span> </label></td>
								</tr>
								<tr>
									<td></td>
									<td colspan="3"><label class="iinfo-lable"><font
											class="errors"> <form:errors path="money"></form:errors>
												${bigError}</font>
									</label></td>
								</tr>
								<tr>
									<td align="right">发票内容</td>
									<td><label class="iinfo-lable">图书/百货</label></td>
									<td align="right">商品数量</td>
									<td><label class="iinfo-lable"> <form:input
												path="quantity" /> </label></td>
								</tr>
								<tr style="height: 10px">
									<td></td>
									<td><label class="iinfo-lable"><font
											class="errors"><form:errors path="content"></form:errors>
										</font>
									</label></td>
									<td></td>
									<td><label class="iinfo-lable"><font
											class="errors"> <form:errors path="quantity"></form:errors>
										</font>
									</label></td>
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
								<tr style="height: 10px">
									<td></td>
									<td><label class="iinfo-lable"><font
											class="errors"><form:errors path="consignee"></form:errors>
										</font>
									</label></td>
									<td></td>
									<td><label class="iinfo-lable"><font
											class="errors"> <form:errors path="mobile"></form:errors>
										</font>
									</label></td>
								</tr>
								<tr>
									<td align="right">电子邮件</td>
									<td><label class="iinfo-lable"> <form:input
												path="email" /> </label>
									</td>
									<td align="right">邮编</td>
									<td><label class="iinfo-lable"> <form:input
												path="zipCode" />
									</label>
									</td>
								</tr>
								<tr style="height: 10px">
									<td></td>
									<td><label class="iinfo-lable"><font
											class="errors"><form:errors path="email"></form:errors>
										</font>
									</label></td>
									<td></td>
									<td><label class="iinfo-lable"><font
											class="errors"> <form:errors path="zipCode"></form:errors>
										</font>
									</label></td>
								</tr>
								<tr>
									<td align="right">区域</td>
									<td colspan="3"><label class="iinfo-lable"> </label> <!-- 国家 -->
										<form:select areaLevel="country" path="country"
											cssClass="area-size" id="country" multiple="">
										</form:select> <!-- 省 --> <form:select areaLevel="province" path="province"
											cssClass="area-size" id="province" multiple="">
											<form:option value="-1">请选择省份</form:option>
										</form:select> <!-- 城市 --> <form:select path="city" areaLevel="city"
											multiple="" cssClass="area-size" id="city">
											<form:option value="-1">请选择城市</form:option>
										</form:select> <!-- 区县 --> <form:select path="district" areaLevel="district"
											multiple="" cssClass="area-size" id="district">
											<form:option value="-1">请选择区县</form:option>
										</form:select></td>
								</tr>
								<tr style="height: 10px">
									<td></td>
									<td colspan="3"><label class="iinfo-lable"><font
											class="errors"> <form:errors path="country"></form:errors>
												<form:errors path="province"></form:errors> <form:errors
													path="city"></form:errors> <form:errors path="district"></form:errors>
										</font>
									</label></td>
								</tr>
								<tr>
									<td align="right">详细地址</td>
									<td colspan="3"><label class="iinfo-lable"> <form:input
												size="70" path="address" /> </label>
									</td>
								</tr>
								<tr style="height: 10px">
									<td></td>
									<td colspan="3"><label class="iinfo-lable"><font
											class="errors"> <form:errors path="address"></form:errors>
										</font>
									</label></td>
								</tr>
								<tr>
									<td colspan="4" align="center"><input id="cancle"
										type="button" class="cbstyle" value="取消" /> <input
										type="submit" class="cnstyle" value="下一步" /></td>
								</tr>

							</table>
						</form:form>
					</div>
				</center>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/order/jquery.mousewheel.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/order/jquery.easing.1.3.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/order/jquery.slidingtabs.pack.js"></script>
	<script type="text/javascript" src="${contextPath}/js/area/areadata.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/area/areaevent.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/orderinvoice/orderinvoice.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.min.js"></script>

	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.methods.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/orderinvoice/orderinvoice_validate.js"></script>
	<input type="hidden" id="icountry"
		value="${orderInvoiceCreateForm.country}"></input>
	<input type="hidden" id="iprovince"
		value="${orderInvoiceCreateForm.province}"></input>
	<input type="hidden" id="icity" value="${orderInvoiceCreateForm.city}"></input>
	<input type="hidden" id="idistrict"
		value="${orderInvoiceCreateForm.district}"></input>
	<input type="hidden" id="path" value="${contextPath}"></input>

</body>

</html>