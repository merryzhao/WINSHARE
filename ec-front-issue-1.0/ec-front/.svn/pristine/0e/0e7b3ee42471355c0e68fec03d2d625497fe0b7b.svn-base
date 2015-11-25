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
            <input type=hidden name="MerId" value="${bank.partner}" />
            <input type=hidden name="OrdId" value="${bank.ordId}" /> 
            <input type=hidden name="TransAmt" value="${bank.transAmt}" />
            <input type=hidden name="CuryId" value="${bank.curyId}" /> 
            <input type=hidden name="TransDate" value="${bank.transDate}" />
            <input type=hidden name="TransType" value="${bank.transType}" /> 
            <input type=hidden name="Version" value="${bank.version}" /> 
            <input type=hidden name="BgRetUrl" value="${bank.notifyUrl}" /> 
            <input type=hidden name="PageRetUrl" value="${bank.returnUrl}" />
            <input type=hidden name="GateId" value="${bank.gateId}" /> 
            <input type=hidden name="Priv1" value="${bank.priv1}" /> 
            <input type=hidden name="ChkValue" value="${bank.chkValue}" />
        </form>

    </body>
</html>
