<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>【文轩网】订单${order.id}商品现在缺货，请耐心等待</title>
</head>
<body style="margin:0; padding:0; background-color:#F0EFEF; " >
<div style=" margin:0 auto; padding-top:10px; width:100%; background-color:#F0EFEF; font-size:12px; color:#656464; font-family:'宋体';">
  <div id="logo" style="margin:0 auto; width:650px; height:62px;"><a href="http://www.winxuan.com/" target="_blank"><img src="http://www.winxuan.com/images/mail/logo2.jpg" width="650px" height="62px" border="0" /></a></div>
  <div id="menu" style="margin:0 auto; width:650px;  background-color:#9A0117; color:#CC0B27; height:18px; text-align:right;"><a href="http://www.winxuan.com" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">文轩首页</font></a>&nbsp;|&nbsp;<a href="http://www.winxuan.com/customer" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">我的文轩</font></a>&nbsp;|&nbsp;<a href="http://www.winxuan.com/help/help_center.html" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">帮助中心</font></a></div>
  <div style="margin:0 auto; width:650px; background-color:#ffffff;">
    <div id="01txt" style="margin:0 auto; width:570px; padding-top:22px; line-height:22px;"><strong>亲爱的${order.customer.displayName}：</strong><br />
&nbsp;&nbsp;&nbsp;&nbsp;感谢您在文轩网购物，您的订单<a href="http://www.winxuan.com/customer/order/${order.id}" target="_blank"><font style="color:#2A5B90; text-decoration:underline;">${order.id}</font></a><font style=" font-weight:bold; color:#FF0000; ">商品有缺货</font>。</div>
    <div id="03prodtucts" style="margin:0 auto; width:570px; padding-top:22px; line-height:18px;"><strong>缺货商品：
      </strong>
      <table width="550" border="0" align="center" cellpadding="5" cellspacing="1" style="margin-top:10px; background-color:#f2f2f2;">
        <tr>
          <td width="37" align="center" bgcolor="#FFFFFF">序号</td>
          <td width="347" align="center" bgcolor="#FFFFFF">商品名称</td>
          <td width="66" align="center" bgcolor="#FFFFFF">文轩价</td>
          <td width="55" align="center" bgcolor="#FFFFFF">缺货数量</td>
        </tr>
        <#list order.itemList as item>
	        <tr>
	          <td align="center" bgcolor="#FFFFFF">${item_index+1}</td>
	          <td align="left" bgcolor="#FFFFFF"><a href="http://www.winxuan.com/product/${item.productSale.product.id}" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">${item.productSale.product.name}</font></a></td>
	          <td align="left" bgcolor="#FFFFFF"><font style=" font-weight:bold; color:#FF0000; ">￥${item.salePrice}</font></td>
	          <td align="center" bgcolor="#FFFFFF">${item.purchaseQuantity-item.purchaseQuantity}</td>
	        </tr>        
        </#list>
      </table>
    </div>
    <div id="05warmword" style="margin:0 auto; width:570px; padding-top:22px; padding-bottom:30px"><strong><font style="color:#FF0000">温馨提示：</font></strong>
      <div style="margin:0 auto; width:570px; line-height:18px; padding-top:10px;">1、如果您已经支付了，我们会将货款退还到<a href="http://www.winxuan.com/customer/account" target="_blank" style=" text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">您的暂存款</font></a>，您可以提现或用作下次购物；<br />
      2、如果该商品已经到货，我们会邮件通知您；同时您也可以进入“我的文轩-<a href="http://www.winxuan.com/customer/notify/arrival?hasStock=true" target="_blank" style=" text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">到货通知</font></a>”查看；</div>
    </div>
  </div>
  <div style="margin:5px auto; width:650px; background-color:#CD0B27; ">
    <div style="margin:0px auto; padding:12px 0 12px 0; width:620px; line-height:17px; color:#ED7082;">·您之所以收到这封邮件，是因为您曾经注册成为文轩网用户<br />
      ·本邮件由文轩网邮件系统自动发出，请勿直接回复！<br />
      ·如果您有任何疑问或建议，请 <a href="http://www.winxuan.com/help/contact.html" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">联系我们</font></a><br />
      ·文轩网(<a href="http://www.winxuan.com" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none; ">www.winxuan.com</font></a>) - 您的网上新华书店！</div>
  </div>
</div>
</body>
</html>
