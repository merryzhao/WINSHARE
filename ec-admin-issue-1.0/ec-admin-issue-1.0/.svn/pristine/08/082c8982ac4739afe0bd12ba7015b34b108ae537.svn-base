<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
.text {
	width: 240px;
	height: 20px;
	line-height: 20px;
	padding: 0 10px;
	color: #6699cc;
	font-size: 18px;
	font-weight: bold;
	font-family: Microsoft YaHei;
}

.cl-table {
	width: 100%;
	border: 1px solid #DFDFDF
}

.fontcl {
	font-size: 20px;
	color: #6699cc;
	font-weight: bold;
	font-family: Microsoft YaHei;
}

table.cl-table tr {
	height: 30px;
	line-height: 30px;
	border: 1px solid #DFDFDF;
}

table.cl-table tr.hover {
	background: #ffffe1
}

.cl1 {
	width: 120px;
}

.cl2 {
	width: 600px;
}

table.cl-table .odd {
	background: #FCFCFC
}

table.cl-table .even {
	background: #F9F9F9
}

table.cl-table button {
	height: 26px;
}

#cltextarea {
	float: left;
	width: 190px;
	height: 48px;
	font-size: 13px;
}

label.error {
	padding: 0.1em;
	color: red;
	font-weight: normal;
}

#form input.error {
	padding: 0px;
	border: 1px solid red;
}
</style>
<style type="text/css">
.error {
	padding: 0em;
	margin-bottom: 0em;
	border: 1px solid #ddd;
}
</style>
<title>账户充值</title>
</head>
<body>
	<!-- 引入JS -->
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>账户充值</h4>
				<hr>
				<div id="content-result">
					<div>
						<form:form action="/customer/confirmBalance" onsubmit="return check();" method="post"
							id="form" commandName="balanceAddForm">
							<token:token></token:token>
							<table>
								<tr>
									<td class="cl1"><label class="fontcl">充值账户名:</label>
									</td>
									<td class="cl2"><form:input class="text" path=""
											id="accountname" name="customer"
											style="padding:10px;color:#6699cc;" /><font color="red"><form:errors
												path="customer" />
									</font><label id="error" class="fontcl"></label>
									</td>
								</tr>
								<tr>
									<td id="mytd"></td>
									<td><label id="money" class="fontcl"></label>
									</td>
								</tr>
								<tr>
									<td class="cl1">支付方式<font color="red">*</font>:</td>
									<td class="cl2"><form:select path="payment">
											<c:forEach items="${listPayment}" var="payment">
												<form:option value="${payment.id}">${payment.name}</form:option>
											</c:forEach>
										</form:select></td>
								</tr>
								<tr>
									<td>外部单号:</td>
									<td><form:input path="outerId" id="outerId" />
									</td>
								</tr>
								<tr>
									<td>支 付 人 :</td>
									<td><form:input path="payer" />
									</td>
								</tr>
								<tr>
									<td>实际到款日期<font color="red">*</font>:</td>
									<td><form:input path="payTime" id="payTime"
											readonly="true" bind="datepicker" /><font color="red"><form:errors
												path="payTime" />
									</font>
									</td>
								</tr>
								<tr>
									<td>实际到款金额<font color="red">*</font>:</td>
									<td><form:input path="money" id="money" /><font
										color="red"><form:errors path="money" />
									</font>
									</td>
								</tr>
								<tr>
									<td>备 注 :</td>
									<td><form:textarea path="remark" id="cltextarea" />
									</td>
								</tr>
								<tr>
									<td><input type="submit" id="tijiao" value="提交" /><input type="submit" disabled="disabled" style="display:none;" id="tijiao2" value="提交" />
									</td>
									<td><input type="reset" value="重置" /><input type="hidden"
										name="balance" />
									</td>
								</tr>
							</table>

						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/customer/balance.js"></script>
</body>
</html>