<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.winxuan.com/tag/constant" prefix="winxuan-constant"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>XXX</title>
</head>
<body>

<form action="/customer/checkout" method="post">
	卖家编号<input name="sellerId" type="text" value="3"><br/>
	地址编号<input name="addressId" type="text" value="62"><br/>
	配送编号<input name="deliveryId" type="text" value="697"><br/>
	送货时间<input name="deliveryoption" type="text" value="3435"><br/>
	支付方式<input name="paymentId" type="text" value="9"><br/>
	是否使用暂存款<input name="useAccount" type="radio" value="1" >是<input name="useAccount" type="radio" value="0" checked="checked">否<br/>
	是否需要发票<input name="needInvoice" type="radio" value="1">是<input name="needInvoice" type="radio" value="0" checked="checked">否<br/>
	发票抬头<input name="invoiceTitle" type="text" value="XXTV"><br/>
	发票类型<input name="invoiceType" type="text" value="3460"><br/>
	<input type="submit" value=" Submit"><br/>
	
</form>
</body>
</html>