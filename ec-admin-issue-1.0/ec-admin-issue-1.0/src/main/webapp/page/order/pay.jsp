<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan-page"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">


.cl1{width:100px}

.cl2{width:360px}
.fontcl {
	font-size: 18px;
	color: #6699cc;
	font-weight: bold;
	font-family: Microsoft YaHei;
	margin-right: 20px;
}

.label {
	margin: 0 30px 15px 0px;
}
.table tr {
	height: 35px;
	margin-top: 20px
}

.table tr td {
	width: 300px;
	margin-top: 20px
}
.mydiv {
	height: 25px;
	margin-top: 15px;
}
#cltextarea{float : left;
				width : 190px;
				height : 48px;
				font-size: 13px;}
label.error {
	padding:0.1em;
}
#orderPayForm input.error {
	padding:0px;
	border: 1px solid red;
}
</style>
<title>普通订单登记到款</title>
</head>
<body>
	<div class="frame">
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<div id="content">
				<h4>普通订单登记到款</h4>
				<hr>
				<div>
					<label class="fontcl">订单号 :${order.id }</label>
				</div>
				<div class="mydiv">
					<label class="label">登记类型:普通订单登记到款</label> 订单号:<label
						id="orderIdLabel" class="label">${order.id }</label> 注册名:<label
						id="nameLabel" class="label">${order.customer.name}</label> 账户余额:<label
						id="balanceLabel" class="label">${order.customer.account.balance
						}</label>
						 还需支付金额:<label
						id="requidPayMoney" class="label">${requidPayMoney}</label>
						
				</div>
				<form:form action="/order/${order.id}/pay" method="post" onsubmit="return confirm('确认登记到款?');"
					commandName="orderPayForm">
					<token:token></token:token>
					<table>
						<tr>
							<td class="cl1">支付方式:</td><td class="cl2"><form:select path="payment">
									<c:forEach items="${listPayment}" var="payment">
										<option value="${payment.id }">${payment.name}</option>
									</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
							<td>外 部 单 号:<font color="red">*</font>
							</td><td><form:input path="outerId" />
							</td>
						</tr>

						<tr>
							<td>支付人:</td><td><form:input path="payer" />
							</td>
						</tr>
						<tr>
							<td>实际到款日期:<font color="red">*</font></td><td><form:input path="payTime" bind="datepicker" value="${today}" />
							</td>
						</tr>
						<tr>
							<td>实际到款金额:<font color="red">*</font>
							</td><td><form:input path="money" />
							</td>
						</tr>
						<tr>
							<td>备 注 :</td><td><form:textarea id="cltextarea" path="remark" />
							</td>
						</tr>
					</table>
					<form:hidden path="customer" value="${order.customer.name}" />
					<input type="submit" value="提交" />
				</form:form>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
<script type="text/javascript">
$().ready(function() {
	$("#orderPayForm").validate({
		rules : {
			
			money : {
				min:0.01,
				required : true,
				number:true 
			},
			payTime : {
				required : true
			},
			outerId :{
				required:true,
				number:true
			}
		},
		messages : {
			money : {
				min:"金额不能为负数和零",
				required : "金额必填",
				number:"金额必须为数字"
			},
			payTime : {
				required : "支付时间必填"
			},
			outerId :{
				required:"外部订单号必填",
				number:"外部订单号必须为数字"
			}
		}
	});
});
</script>
</body>
</html>