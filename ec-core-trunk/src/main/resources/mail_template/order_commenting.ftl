<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>【文轩网】您有未评价的商品哦，评价就送积分！</title>
</head>
<body style="margin:0; padding:0; background-color:#F0EFEF; " >
<div style=" margin:0 auto; padding-top:10px; width:100%; background-color:#F0EFEF; font-size:12px; color:#656464; font-family:'宋体';">
  <div id="logo" style="margin:0 auto; width:650px; height:62px;"><a href="http://www.winxuan.com/" target="_blank"><img src="http://www.winxuan.com/images/mail/logo2.jpg" width="650px" height="62px" border="0" /></a></div>
  <div id="menu" style="margin:0 auto; width:650px;  background-color:#9A0117; color:#CC0B27; height:18px; text-align:right;"><a href="http://www.winxuan.com" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">文轩首页</font></a>&nbsp;|&nbsp;<a href="http://www.winxuan.com/customer" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">我的文轩</font></a>&nbsp;|&nbsp;<a href="http://www.winxuan.com/help/help_center.html" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">帮助中心</font></a></div>
  <div style="margin:0 auto; width:650px; background-color:#ffffff;">
<div id="01txt" style="margin:0 auto; width:570px; padding-top:22px; line-height:22px;font-size:12px; color:#656464; font-family:'宋体';"><strong>亲爱的${order.customer.displayName}：</strong><br />
  &nbsp;&nbsp;&nbsp;&nbsp;感谢您在文轩网购物！在此邀请您对所购买的商品发表评价，我们认为您对所购买的商品有更深入的了解，评价也更具参考性和权威性，您的观点将帮助其他用户在购买时作出更好的选择。<br />
</div>
<div id="05warmword" style="margin:0 auto; width:570px; padding-top:22px; font-size:12px; color:#656464; font-family:'宋体';"><strong><font style="color:#FF0000">温馨提示：</font></strong>
    <div style="margin:0 auto; width:570px; line-height:18px; padding-top:10px;font-size:12px; color:#656464; font-family:'宋体';"> 1、文轩网会根据您的级别及所购买商品金额赠送相应的积分，查看“<a href="#" target="_blank"><font style="color:#2A5B90; text-decoration: underline;">积分规则</font></a>”；<br />
2、实用、生动的评价不仅会被展示到网站首页，还可获得额外的积分奖励；<br />
3、您可进入文轩网-&gt;我的文轩-&gt;<a href="http://www.winxuan.com/customer/comment/product" target="_blank"><font style="color:#2A5B90; text-decoration: underline;">我的评价</font></a> 页面查看您的所有待评价商品。 </div>
</div>
<div id="03prodtucts" style="margin:0 auto; width:570px; padding-top:22px; line-height:18px;font-size:12px; color:#656464; font-family:'宋体';"><strong>待评价商品：
      </strong>
  <table width="550" border="0" align="center" cellpadding="5" cellspacing="1" style="margin-top:10px; background-color:#f2f2f2;font-size:12px; color:#656464; font-family:'宋体';">
    <tr>
      <td width="32" align="center" bgcolor="#FFFFFF">序号</td>
      <td width="332" align="center" bgcolor="#FFFFFF">商品名称</td>
      <td width="63" align="center" bgcolor="#FFFFFF">购买时间</td>
      <td width="78" align="center" bgcolor="#FFFFFF">操作</td>
    </tr>
    <#list order.itemList as item>
        <tr>
          <td align="center" bgcolor="#FFFFFF">${item_index+1}</td>
          <td align="left" bgcolor="#FFFFFF"><a href="http://www.winxuan.com/product/${item.productSale.id}" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">${item.productSale.product.name}</font></a></td>
          <td align="center" bgcolor="#FFFFFF"><font style=" font-weight:bold; color:#FF0000; ">${order.createTime?string('yyyy-MM-dd')}</font></td>
          <td align="center" bgcolor="#FFFFFF"><a href="http://www.winxuan.com/customer/comment/${item.productSale.id}" target="_blank"><font style=" font-size:12px; font-weight:bold; color:#C79000; text-decoration:underline; ">发表评价</font></a></td>
        </tr>        　　　　　
    </#list>
  </table>
</div>
    <div id="04help" style="margin:0 auto; width:570px; padding-top:22px; padding-bottom:30px">
      如果您仍有问题，请联系我们：<a href="http://www.winxuan.com/help/contact.html" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">http://www.winxuan.com/help/contact.html</font></a></div>
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