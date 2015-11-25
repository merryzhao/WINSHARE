<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.fontcl {
	font-size: 16px;
	color: #6699cc;
	font-weight: bold;
	font-family: Microsoft YaHei;
}
.cl1{width:120px;}
.cl2{width:600px;}
table tr{height:25px;}
</style>
<%@include file="../snippets/meta.jsp"%>
<title>账户充值确认</title>
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

				<h4>账户充值信息</h4>
				<hr>
				<div id="content-result">
					<div>
						<form:form action="/customer/addBalance" method="post"
							commandName="balanceAddForm">
							<token:token></token:token>
							<table>
								<tr><td class="cl1"><label class="fontcl">充值账户名 :</label> </td><td class="cl2"><label class="fontcl">${balanceAddForm.customer}</label></td></tr>
								<tr><td>账户余额 :</td><td> ${balance}元</td></tr>
								<tr><td>支付方式 :</td><td> ${payment}</td></tr>
								<tr><td>外部单号 :</td><td> ${balanceAddForm.outerId }</td></tr>
								<tr><td>支 付 人 :</td><td> ${balanceAddForm.payer}</td></tr>
								<tr><td>实际到款日期 :</td><td> ${balanceAddForm.payTime }</td></tr>
								<tr><td>实际到款金额 :</td><td> ${balanceAddForm.money}</td></tr>
								<tr><td>备 注 :</td><td> ${balanceAddForm.remark}</td></tr>
								<tr><td><input type="submit" value="提交" /></td><td> <a href="/customer/gotoAddBalance"><input
									type="button" onclick="/customer/gotoAddBalance" value="返回修改" /></a>
								</td></tr>
							</table>
							<form:hidden path="customer" name="customer"
								vlaue="${balanceAddForm.customer}" />
							<form:hidden path="payment" value="${balanceAddForm.payment}" />
							<form:hidden path="outerId" value="${balanceAddForm.outerId }" />
							<form:hidden path="payer" value="${balanceAddForm.payer }" />
							<form:hidden path="payTime" value="${balanceAddForm.payTime }" />
							<form:hidden path="money" value="${balanceAddForm.money }" />
							<form:hidden path="remark" value="${balanceAddForm.remark }" />
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>