<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <html>
<head>
<title>补开发票</title>

<script type="text/javascript">
	function nextStep() {

	}

	function preStep() {
		document.getElementById("invoice-body").style.display = "block";
		document.getElementById("ninvoice-body").style.display = "none";
	}
</script>
<style type="text/css">
#invoice-body {
	margin-top: 10px;
	width: 600px;
	height: 500px;
	margin-left: -150px;
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
label.error {
	padding:0.1em;
}
#orderInvoiceCreateForm input.error {
	padding:0px;
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
				<img src="${contextPath}/css/images/invoice_bk_step_1.jpg">
			</div>
					<div id="invoice-body">
						<form:form commandName="orderInvoiceCreateForm"
							action="${contextPath}/orderinvoice/goSure" method="post">
 							<table class="list-table1">
								<tr>
									<td align="right">订单号</td>
									<td colspan="3"><label class="iinfo-lable">${orderId}</label>
										<form:hidden path="orderId" />
									</td>
								</tr>

								<tr>
									<td align="right">抬头类型</td>
									<%-- <td><label class="iinfo-lable"> <select name="titleType"  style="width:100px">					
												<c:forEach var="titleType" items="${titleTypes}">	
				   										<option value="${titleType.id }" <c:if test="${titleType.id == titleTypeId}">selected="selected"</c:if>>${titleType.name}</option>
				   								</c:forEach>
												</select>  												
									   </label>
									</td>  --%>
									 <td><label class="iinfo-lable"> <form:select
												path="titleType" cssClass="select-size"
												items="${titleTypes}" id="" multiple="" itemLabel="name"
												itemValue="id" />  </label>
									</td>
									<td align="right">发票抬头</td>
									<td><label class="iinfo-lable"><form:input
												path="title" value ="${orderInvoiceCreateForm.title }"></form:input>  </label>
									</td>
								</tr>
								<tr style="height:10px">
								<td></td>
								<td>
								<label class="iinfo-lable"><font color="red"><form:errors path="titleType"></form:errors> </font></label>
								</td>
								<td></td>
								<td>
								<label class="iinfo-lable"><font color="red"> <form:errors path="title"></form:errors> </font></label>
								</td>
								</tr>
								<tr style="height:10px">
									<td align="right">发票金额</td>
									<td colspan="3">
									<label class="iinfo-lable">
									<form:input path="money" value ="${orderInvoiceCreateForm.money }"></form:input>(代理商订单可变动金额，其他订单按照发货金额计算)
									</label>
									</td>
								</tr>
							    <tr style="height:10px">
								<td></td>
								<td colspan="3">
								<label class="iinfo-lable"><font color="red"><font color="red"> <form:errors path="money"></form:errors></font></font></label>
								</td>
								</tr>
								<tr>
									<td align="right">发票内容</td>
									<td><label class="iinfo-lable">${orderInvoiceCreateForm.content}</label>
									</td>
									<td align="right">商品数量</td>
									<td><label class="iinfo-lable"> <form:input path="quantity"/>  </label>
									</td>
								</tr>
								<tr style="height:10px">
								<td></td>
								<td>
								<label class="iinfo-lable"><font color="red"><form:errors path="content"></form:errors> </font></label>
								</td>
								<td></td>
								<td>
								<label class="iinfo-lable"><font color="red"> <form:errors path="quantity"></form:errors> </font></label>
								</td>
								</tr>
								<tr>
									<td align="right">收货人</td>
									<td><label class="iinfo-lable"> <form:input
												path="consignee" /> </label></td>
									<td align="right">电话</td>
									<td><label class="iinfo-lable"> <form:input
												path="mobile" />  </label>
									</td>
								</tr>
								<tr style="height:10px">
								<td></td>
								<td>
								<label class="iinfo-lable"><font color="red"><form:errors path="consignee"></form:errors> </font></label>
								</td>
								<td></td>
								<td>
								<label class="iinfo-lable"><font color="red"> <form:errors path="mobile"></form:errors> </font></label>
								</td>
								</tr>
								<tr >
									<td align="right">电子邮件</td>
									<td><label class="iinfo-lable"> <form:input
												path="email" /> </label></td>
									<td align="right">邮编</td>
									<td><label class="iinfo-lable"> <form:input
												path="zipCode" /></label></td>
								</tr>
								<tr style="height:10px">
								<td></td>
								<td>
								<label class="iinfo-lable"><font color="red"><form:errors path="email"></form:errors> </font></label>
								</td>
								<td></td>
								<td>
								<label class="iinfo-lable"><font color="red"> <form:errors path="zipCode"></form:errors> </font></label>
								</td>
								</tr>
								<tr>
									<td align="right">区域</td>
									<td colspan="3"><label class="iinfo-lable"> </label>		
									 <!-- 国家 -->
											<form:select areaLevel ="country" path="country" id="country" cssClass="area-size "  multiple="">
											</form:select> 
											<!-- 省 --> 
											<form:select areaLevel="province" path="province" id="province" cssClass="area-size "  multiple="">
												<form:option value="-1">请选择省份</form:option>
											</form:select> 
											<!-- 城市 -->
											 <form:select path="city" areaLevel="city" multiple="" id="city" cssClass="area-size " >
												<form:option value="-1">请选择城市</form:option>
											</form:select> 
											 <!-- 区县 --> 
											<form:select path="district" areaLevel="district" multiple="" id="district" cssClass="area-size " >
												<form:option value="-1">请选择区县</form:option>
											</form:select>		
											<!-- 乡镇 -->
											<form:select path="town" areaLevel="town" multiple="" id="town" cssClass="area-size " >
												<form:option value="-1">请选择乡镇</form:option>
											</form:select>								
											</td>
								</tr>
								<tr style="height:10px">
								<td></td>
								<td colspan="3">
								<font color="red">
								<form:errors path="country"></form:errors> 
								<form:errors path="province"></form:errors>
								<form:errors path="city"></form:errors>
								<form:errors path="district"></form:errors>
								</font>
								</td>
								</tr>
								<tr>
									<td align="right">详细地址</td>
									<td colspan="3"><label class="iinfo-lable"> <form:input
												size="70" path="address" />  </label></td>
								</tr>
								<tr style="height:10px">
								<td></td>
								<td colspan="3">
								<label class="iinfo-lable"><font color="red">
								<form:errors path="address"></form:errors> 
								</font></label>
								</td>
								</tr>
								<tr>
									<td colspan="4" align="center"><input id="cancle" type="button"
										class="cbstyle" value="取消" /> <input type="submit"
										class="cnstyle" value="下一步" /></td>
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
	<script type="text/javascript" src="${contextPath}/js/area/areadata.js"
		></script>
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
			
<input type="hidden" id="icountry" value="${orderInvoiceCreateForm.country}"></input>
<input type="hidden" id="iprovince" value="${orderInvoiceCreateForm.province}"></input>
<input type="hidden" id="icity" value="${orderInvoiceCreateForm.city}"></input>
<input type="hidden" id="idistrict" value="${orderInvoiceCreateForm.district}"></input>
<input type="hidden" id="itown" value="${orderInvoiceCreateForm.town}"></input>
<input type="hidden" id="path" value="${contextPath}"></input>
</body>
</html>