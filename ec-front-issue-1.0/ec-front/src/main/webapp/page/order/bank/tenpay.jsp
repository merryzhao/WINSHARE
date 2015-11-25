<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<script type="text/javascript">
	onload=function(){
		var dataForm=document.getElementById("dataForm");
		dataForm.submit();
	};
</script>
</head>
<body>
    <form id="dataForm" action="${bank.action}" method="post">
    <input type=hidden name="sign" value="${bank.sign}">
	<input type=hidden name="partner" value="${bank.partner}">
	<input type=hidden name="out_trade_no" value="${bank.outTradeNo}">
	<input type=hidden name="total_fee" value="${bank.totalFee}">
	<input type=hidden name="return_url" value="${bank.returnUrl}">
	<input type=hidden name="notify_url" value="${bank.notifyUrl}">
	<input type=hidden name="body" value="${bank.body}">
	<input type=hidden name="bank_type" value="${bank.bankType}">
	<input type=hidden name="spbill_create_ip" value="${bank.spbillCreateIp}">
	<input type=hidden name="fee_type" value="${bank.feeType}">
	<input type=hidden name="sign_type" value="${bank.signType}">
	<input type=hidden name="service_version" value="${bank.serviceVersion}">
	<input type=hidden name="input_charset" value="${bank.inputCharset}">
	<input type=hidden name="sign_key_index" value="${bank.signKeyIndex}">
	</form> 
  </body>
</html>
