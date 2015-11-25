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
	<input type=hidden name=mer_code value="${bank.partner}"> 
	<input type=hidden name=Billno value="${bank.billno}"> 
	<input type=hidden name=Amount value="${bank.amount}"> 
	<input type=hidden name=DispAmount value="${bank.dispAmount}"> 
	<input type=hidden name=Date value="${bank.date}"> 
	<input type=hidden name=OrderEncodeType value="${bank.orderEncodeType}"> 
	<input type=hidden name=RetEncodeType value="${bank.retEncodeType}"> 
	<input type=hidden name=RetType value="${bank.retType}"> 
	<input type=hidden name=SignMD5 value="${bank.signMD5}"> 
	<input type=hidden name=Merchanturl value="${bank.returnUrl}"> 
	<input type=hidden name=serverurl value="${bank.notifyUrl}"> 
	<input type=hidden name=Attach value="${bank.attach}">
	<input type=hidden name=Currency_Type value="${bank.currencyType}">
	<input type=hidden name=Lang value="${bank.lang}">
	<input type=hidden name=Gateway_type value="${bank.gatewayType}">
</form>
</body>
</html>
