<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>【文轩网】订单${order.id}已发货，请做好收货准备</title>
</head>
<body style="margin:0; padding:0; background-color:#F0EFEF; " >
<div style=" margin:0 auto; padding-top:10px; width:100%; background-color:#F0EFEF; font-size:12px; color:#656464; font-family:'宋体';">
  <div id="logo" style="margin:0 auto; width:650px; height:62px;"><a href="http://www.winxuan.com/" target="_blank"><img src="http://www.winxuan.com/images/mail/logo2.jpg" width="650px" height="62px" border="0" /></a></div>
  <div id="menu" style="margin:0 auto; width:650px;  background-color:#9A0117; color:#CC0B27; height:18px; text-align:right;"><a href="http://www.winxuan.com" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">文轩首页</font></a>&nbsp;|&nbsp;<a href="http://www.winxuan.com/customer" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">我的文轩</font></a>&nbsp;|&nbsp;<a href="http://www.winxuan.com/help/help_center.html" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">帮助中心</font></a></div>
 <div style="margin:0 auto; width:650px; background-color:#ffffff;">
    <div id="01txt" style="margin:0 auto; width:570px; padding-top:22px; line-height:22px;font-family:'宋体';font-size: 12px;"><strong>亲爱的${order.customer.displayName}：</strong><br />
&nbsp;&nbsp;&nbsp;&nbsp;感谢您在文轩网购物，您的订单${order.id}<font style=" font-weight:bold; color:#FF0000; font-family:'宋体';font-size: 12px;">已经发货</font>，请做好收货准备。
<div style=" margin:0 auto; padding-top:5px; width:532px; line-height:18px; ">1、具体订单状态请关注：<a href="http://www.winxuan.com/customer/order/${order.id}" target="_blank"><font style="color:#2A5B90; text-decoration:underline;">订单详情</font></a>；<br />
2、配送物流信息，请点击：<a href="http://www.winxuan.com/customer/order/${order.id}" target="_blank"><font style="color:#2A5B90; text-decoration:underline;">物流配送</font></a>；<br />
</div>
    </div>
    <div id="02form" style="margin:0 auto; width:570px; padding-top:22px; line-height:22px;"><strong>订单信息： </strong>
      <table width="550" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:0px;">
        <tr>
          <td width="187" height="22">订单编号：<a href="http://www.winxuan.com/customer/order/${order.id}" target="_blank"><font style="color:#2A5B90; text-decoration:underline; ">${order.id}</font></a></td>
          <td width="182">订单总金额：<font style=" font-weight:bold; color:#FF0000; ">${order.totalPayMoney}</font></td>
          <td width="181">下单时间：${order.createTime?string('yyyy-MM-dd')}</td>
        </tr>
        <tr>
          <td height="22">支付方式：<a href="http://www.winxuan.com/help/payment_online.html" target="_blank">
          <font style=" font-size:12px; font-weight:bold; color:#C79000; text-decoration:underline; ">
          <#list order.paymentList as orderPayment>
          	<#if orderPayment.payment.id != 20>
				${orderPayment.payment.name}
			</#if>
          </#list>
          </font></a></td>
          <td>收货人姓名：${order.consignee.consignee}</td>
          <td>&nbsp;</td>
        </tr>
      </table>
    </div>
    <div id="03prodtucts" style="margin:0 auto; width:570px; padding-top:22px;"><strong>商品信息：
      </strong>
      <table width="550" border="0" align="center" cellpadding="5" cellspacing="1" style="margin-top:10px; background-color:#f2f2f2;font-family:'宋体';font-size: 12px;">
        <tr>
          <td width="37" align="center" bgcolor="#FFFFFF">序号</td>
          <td width="342" align="center" bgcolor="#FFFFFF">商品名称</td>
          <td width="48" align="center" bgcolor="#FFFFFF">数量</td>
          <td width="78" align="center" bgcolor="#FFFFFF">总价</td>
        </tr>
        <#list order.itemList as item>
	        <tr>
	          <td align="center" bgcolor="#FFFFFF">${item_index+1}</td>
	          <td align="left" bgcolor="#FFFFFF"><a href="http://www.winxuan.com/product/${item.productSale.product.id}" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">${item.productSale.product.name}</font></a></td>
	          <td align="center" bgcolor="#FFFFFF">${item.deliveryQuantity}</td>
	          <td align="right" bgcolor="#FFFFFF"><font style=" font-weight:bold; color:#FF0000; ">￥${item.salePrice*item.deliveryQuantity}</font></td>
	        </tr>        
        </#list>
      </table>
    </div>
    <div id="04help" style="margin:0 auto; width:570px; padding-top:22px; padding-bottom:30px;font-family:'宋体';font-size: 12px;"><strong>常见问题及相关帮助：</strong>
      	<div style=" margin:0 auto; width:570px; line-height:18px; padding-top:5px;font-family:'宋体';font-size: 12px;">
		      订单状态：<a href="http://www.winxuan.com/help/order_state.html" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">http://www.winxuan.com/help/order_state.html</font></a><br />
		        支付方式：<a href="http://www.winxuan.com/help/payment_online.html" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">http://www.winxuan.com/help/payment_online.html</font></a><br />
		        物流配送：<a href="http://www.winxuan.com/help/logistic_scope.html" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">http://www.winxuan.com/help/logistic_scope.html</font></a><br />
		        退换货服务：<a href="http://www.winxuan.com/help/return_rules.html" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">http://www.winxuan.com/help/return_rules.html</font></a><br />
		        常见问题：<a href="http://www.winxuan.com/help/faq.html" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">http://www.winxuan.com/help/faq.html</font></a>
        </div>
	  	<div style="margin:0 auto; width:570px; line-height:18px; padding-top:10px;font-family:'宋体';font-size: 12px;">如果您需要查看或修改订单，请进入<a href="http://www.winxuan.com/customer/order/${order.id}" target="_blank" style=" text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">我的订单</font></a><br />
	    	如果您仍有问题，请联系我们：<a href="http://www.winxuan.com/help/contact.html" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">http://www.winxuan.com/contact/</font></a>
	    </div>
    </div>
  </div>
  <div style="margin:5px auto; width:650px; background-color:#CD0B27; ">
    <div style="margin:0px auto; padding:12px 0 12px 0; width:620px; line-height:17px; color:#ED7082;font-family:'宋体';font-size: 12px;">·您之所以收到这封邮件，是因为您曾经注册成为文轩网用户<br />
      ·本邮件由文轩网邮件系统自动发出，请勿直接回复！<br />
      ·如果您有任何疑问或建议，请 <a href="http://www.winxuan.com/help/contact.html" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">联系我们</font></a><br />
      ·文轩网(<a href="http://www.winxuan.com" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none; ">www.winxuan.com</font></a>) - 您的网上新华书店！
    </div>
  </div>
</div>
</body>
</html>