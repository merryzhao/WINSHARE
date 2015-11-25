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
	border: 1px solid red;
}
</style>
<title>添加支付方式</title>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<div class="step">
				<img src="${contextPath}/css/images/payment_create.jpg">
			</div>
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="content-result" >
					<form:form action="${contextPath}/payment/create" method="post"
						commandName="paymentEditForm">
						<token:token></token:token>
						<div class="row">
						  <div>
								<span class="tablehead">支付方式名称<font class="impTag">*</font>:</span>
								<span class="tabletext">
									<form:input path="name" alt="--请输入支付方式名称" />
									<font class="errors"><form:errors path="name" /> </font>
								</span>
								<span class="tablehead">支付方式状态<font class="impTag">*</font>:</span>
								<span class="tabletext">
									<form:radiobutton path="available" value="true" label="生效" />
									<form:radiobutton path="available" value="false" label="失效" />
								</span>
							</div>
						</div>
						<div class="row">
							<div>
								<span class="tablehead">支付方式类型<font class="impTag">*</font>:</span>
								<span class="tabletext">
									<form:select path="type">
										<c:forEach var="payType" items="${paymentType.children }">
											<form:option value="${payType.id }" label="${payType.name }"/>
										</c:forEach>
									</form:select>
								</span>
								<span class="tablehead">是否前台显示<font class="impTag">*</font>:</span>
								<span class="tabletext">
									<form:radiobutton path="show" value="true" label="是" />
									<form:radiobutton path="show" value="false" label="否" />
								</span>
							</div>
						</div>

						<div class="row">
						<div>
								<span class="tablehead">收款手续费费率:</span>
								<span class="tabletext">
									<form:input path="payFee" />% <font class="errors">
									<form:errors path="payFee" /> </font>
								</span>
							</div>
							<div>
								<span class="tablehead">收款手续费最小收取金额:</span>
								<span class="tabletext">
									<form:input path="payFeeMin" />
									元 <font class="errors"><form:errors path="payFeeMin" />
									</font>
								</span>
							</div>
							<div>
								<span class="tablehead">收款手续费最小收取方式:</span>
								<span class="tabletext">
									<form:select path="payFeeType">
										<form:option value="" label="请选择..." />
										<form:option value="3456" label="四舍五入" />
										<form:option value="3457" label="向上取整" />
										<form:option value="3458" label="向下取整" />
									</form:select>
								</span>
							</div>
						</div>
						<div class="row">
							<div>
								<span class="tablehead">是否可退款<font class="impTag">*</font>:</span>
								<span class="tabletext">
									<form:radiobutton path="allowRefund" value="true" label="是" />
									<form:radiobutton path="allowRefund" value="false" label="否" />
								</span>
								<span class="tablehead">退款最大期限:</span>
								<span class="tabletext">
									<form:select path="refundTerm">
										<form:option value="3" label="3个月" />
										<form:option value="6" label="6个月" />
										<form:option value="12" label="12个月" />
										<form:option value="0" label="无期限限制" />
									</form:select>
								</span>
							</div>
						</div>

						<div class="row">
							<div>
								<span class="tablehead">退款手续费费率:</span>
								<span class="tabletext">
									<form:input path="refundFee" />
									% <font class="errors"><form:errors path="refundFee" />
									</font>
								</span>
							</div>
							<div>
								<span class="tablehead">退款手续费最小收取金额:</span>
								<span class="tabletext">
									<form:input path="refundFeeMin" />
									元 <font class="errors"><form:errors path="refundFeeMin" />
									</font>
								</span>
							</div>
							<div>
								<span class="tablehead">收款手续费最小收取方式:</span>
								<span class="tabletext">
									<form:select path="refundFeeType">
										<form:option value="" label="请选择..." />
										<form:option value="3456" label="四舍五入" />
										<form:option value="3457" label="向上取整" />
										<form:option value="3458" label="向下取整" />
									</form:select>
								</span>
							</div>
						</div>
						<div>
							<div>
							<span class="tablehead">描述:</span>
							<form:textarea path="description" style="width:400px;height:60px;"/>
							</div>
						</div>
						<div class="row">
							<div style="margin:20px;">
								<span style="width:260px;margin:0 40px;text-align:right"><input type="submit" value="  提 交  " /></span>
								<span style="width:260px;margin:0 0 0 40px"><input type="reset" value="  重 置  " /></span>
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