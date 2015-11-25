<%@page import="java.net.URLDecoder"%>
<%@page import="com.hisun.iposm.HiiposmUtil"%>
<%@page import="com.winxuan.ec.model.bank.ShouJiZhiFu"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<%
	ShouJiZhiFu bank = (ShouJiZhiFu)request.getAttribute("bank");
	HiiposmUtil util = new HiiposmUtil();
	String buf = "characterSet=" + bank.getCharacterSet() 
			+ "&callbackUrl=" + bank.getReturnUrl() 
			+ "&notifyUrl=" + bank.getNotifyUrl() 
			+ "&ipAddress=" + bank.getIpAddress()
			+ "&merchantId=" + bank.getPartner()
			+ "&requestId=" + bank.getRequestId()
			+ "&signType=" + bank.getSignType()
			+ "&type=" + bank.getType()
			+ "&version=" + bank.getVersion()
			+ "&hmac=" + bank.getHmac()
			+ "&amount=" + bank.getAmount()
			+ "&currency=" + bank.getCurrency()
			+ "&orderDate=" + bank.getOrderDate()
			+ "&orderId=" + bank.getOrderId()
			+ "&merAcDate=" + bank.getMerAcDate()
			+ "&period=" + bank.getPeriod()
			+ "&productName=" + bank.getProductName()
			+ "&periodUnit=" + bank.getPeriodUnit();
	//发起http请求，并获取响应报文
	String res = util.sendAndRecv(bank.getAction(), buf, bank.getCharacterSet());
	//获得手机支付平台的消息摘要，用于验签,
	String hmac1 = util.getValue(res, "hmac");
	String vfsign = util.getValue(res, "merchantId")
			+ util.getValue(res, "requestId")
			+ util.getValue(res, "signType")
			+ util.getValue(res, "type")
			+ util.getValue(res, "version")
			+ util.getValue(res, "returnCode")
			+ URLDecoder.decode(util.getValue(res, "message"),
					"UTF-8") + util.getValue(res, "payUrl");

	//响应码
	String code = util.getValue(res, "returnCode");
	//下单交易成功
	if (!code.equals("000000")) {
		out.println("下单错误:" + code + URLDecoder.decode(util.getValue(res,"message"),"UTF-8"));
		return;
	}

	// -- 验证签名
	boolean flag = false;
	flag = util.MD5Verify(vfsign, hmac1, bank.getSignKey());

	if (!flag) {
		//request.getSession().setAttribute("message", "验证签名失败！");
		out.println("验签失败");
		return;
	}

	String payUrl = util.getValue(res, "payUrl");
	String submit_url = util.getRedirectUrl(payUrl);

	//RequestDispatcher rd2 = request.getRequestDispatcher("/submit_cmpay.jsp?cmpayUrl="+submit_url+"&SESSIONID="+sessionId);
	//RequestDispatcher rd2 = request.getRequestDispatcher("/submit_cmpay.jsp?sessionId="+sessionId);  
	//out.println("12");
	//rd2.forward(request,response);
	//System.out.println("submit_url:" + submit_url);
	response.sendRedirect(submit_url);
	
%>
</body>
</html>
