<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/meta_payment.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
label.error {
	padding:0.1em;
}
#paymentEditForm input.error {
	padding:0px;
	border: 2px solid red;label.error {
	padding:0.1em;
}
#paymentEditForm input.error {
	padding:0px;
	border: 2px solid red;
}
}
</style>
<title>修改支付方式</title>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
		<div class="step">
				<img src="${contextPath}/css/images/payment_edit.jpg">
			</div>
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="content-result">
					<form:form action="${contextPath}/payment/${paymentId}"
						method="put" commandName="paymentEditForm">
						<token:token></token:token>
						<div class="row">
							<div>
								支付方式名称<font class="impTag">*</font>:
								<form:input path="name" alt="--请输入支付方式名称" />
								<font class="errors"><form:errors path="name" />
								</font>
							</div>
							<div>
								支付方式状态<font class="impTag">*</font>:
								<form:radiobutton path="available" value="true" label="生效" />
								<form:radiobutton path="available" value="false" label="失效" />
							</div>
						</div>
						<div class="row">
							<div>
								支付方式类型<font class="impTag">*</font>:
								<form:select path="type">
									<form:option value="10002" label="网上支付" />
									<form:option value="10001" label="线下支付" />
								</form:select>
							</div>
							<div>
								是否前台显示<font class="impTag">*</font>:
								<form:radiobutton path="show" value="true" label="是" />
								<form:radiobutton path="show" value="false" label="否" />
							</div>
						</div>

						<div class="row">
							<div>
								收款手续费费率:
								<form:input path="payFee" />
								% <font class="errors"><form:errors path="payFee" />
								</font>
							</div>
							<div>
								收款手续费最小收取金额:
								<form:input path="payFeeMin" />
								元 <font class="errors"><form:errors path="payFeeMin" />
								</font>
							</div>
							<div>
								收款手续费最小收取方式:
								<form:select path="payFeeType">
									<form:option value="" label="请选择..." />
									<form:option value="3456" label="四舍五入" />
									<form:option value="3457" label="向上取整" />
									<form:option value="3458" label="向下取整" />
								</form:select>
							</div>
						</div>
						<hr />
						<div class="row">
							<div>
								是否可退款<font class="impTag">*</font>:
								<form:radiobutton path="allowRefund" value="true" label="是" />
								<form:radiobutton path="allowRefund" value="false" label="否" />
							</div>
							<div>
								退款最大期限:
								<form:select path="refundTerm">
									<form:option value="3" label="3个月" />
									<form:option value="6" label="6个月" />
									<form:option value="12" label="12个月" />
									<form:option value="0" label="无期限限制" />
								</form:select>
							</div>
						</div>

						<div class="row">
							<div>
								退款手续费费率:
								<form:input path="refundFee" />
								% <font class="errors"><form:errors path="refundFee" />
								</font>
							</div>
							<div>
								退款手续费最小收取金额:
								<form:input path="refundFeeMin" />
								元 <font class="errors"><form:errors path="refundFeeMin" />
								</font>
							</div>
							<div>
								收款手续费最小收取方式:
								<form:select path="refundFeeType">
									<form:option value="" label="请选择..." />
									<form:option value="3456" label="四舍五入" />
									<form:option value="3457" label="向上取整" />
									<form:option value="3458" label="向下取整" />
								</form:select>
							</div>
						</div>
						<div>
							描述:
							<form:textarea path="description" />
						</div>
						<div class="row">
							<div>
								<input type="submit" value="   修改     " />
							</div>
							<div>
								<input type="reset" value="  重置     " />
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/payment/payment.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
</body>
</html>