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
<form id="dataForm" action="${bank.action}" method="get">
	<input type=hidden name="body" value="${bank.body}">
	<input type=hidden name="notify_url" value="${bank.notifyUrl}">
	<input type=hidden name="out_trade_no" value="${bank.outTradeNo}">
	<input type=hidden name="partner" value="${bank.partner}">
	<input type=hidden name="seller_logon_id" value="${bank.sellerEmail}">
	<input type=hidden name="service" value="${bank.service}">
	<input type=hidden name="sign" value="${bank.sign}"> 
	<input type=hidden name="sign_type" value="${bank.signType}">      
	<input type=hidden name="subject" value="${bank.subject}">
	<input type=hidden name="total_fee" value="${bank.totalFee}">
	<input type=hidden name="show_url" value="${bank.showUrl}">
	<input type=hidden name="return_url" value="${bank.returnUrl}">
	<input type=hidden name="_input_charset" value="${bank.charset}">
	<input type=hidden name="default_bank" value="${bank.defaultBank}">
</form> 

</body>
</html>
