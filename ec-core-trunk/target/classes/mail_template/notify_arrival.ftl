<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>到货通知</title>
</head>
<body style="margin:0; padding:0; background-color:#F0EFEF; " >
<#setting url_escaping_charset='UTF-8'> 
<div style=" margin:0 auto; padding-top:10px; width:100%; background-color:#F0EFEF; font-size:12px; color:#656464; font-family:'宋体';">
  <div id="logo" style="margin:0 auto; width:650px; height:62px;"><a href="http://www.winxuan.com/" target="_blank"><img src="http://www.winxuan.com/images/mail/logo2.jpg" width="650px" height="62px" border="0" /></a></div>
  <div id="menu" style="margin:0 auto; width:650px;  background-color:#9A0117; color:#CC0B27; height:18px; text-align:right;"><a href="http://www.winxuan.com" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">文轩首页</font></a>&nbsp;|&nbsp;<a href="http://www.winxuan.com/customer" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">我的文轩</font></a>&nbsp;|&nbsp;<a href="http://www.winxuan.com/help/help_center.html" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">帮助中心</font></a></div>
  <div style="margin:0 auto; width:650px; background-color:#ffffff;">
<div id="01txt" style="margin:0 auto; width:570px; padding-top:22px; line-height:22px;"><strong>
亲爱的<#if customerNotifies[0].customer.realName??>
${customerNotifies[0].customer.realName}
<#else>${customerNotifies[0].customer.displayName}</#if></strong><br />
  &nbsp;&nbsp;&nbsp;&nbsp;您曾经收藏/关注过的某商品刚刚<font style=" font-size:12px; font-weight:bold; color:#FF0000; ">到货啦</font>，赶快去抢购吧！</div>
<div id="03prodtucts_2" style="margin:0 auto; width:570px; padding-top:22px;"><strong>已到货商品：</strong>
    <table width="570" border="0" cellspacing="0" cellpadding="15" style=" margin-top:10px; border-bottom:1px solid #EDEBEC; border-top:1px solid #EDEBEC;">
	<#list customerNotifies as customerNotify>
		<tr>
        <td style="border-bottom:1px solid #EDEBEC;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="23%" align="center" valign="top"><a href="${customerNotify.url}" target="_blank"><img src="${customerNotify.productSale.imageUrl}" width="80" height="112" border="0" /></a></td>
              <td width="77%" valign="top" style="line-height:22px;"><div style="margin-top:5px; margin-bottom:5px; "><a href="${customerNotify.url}" target="_blank" style="text-decoration:none; color:#656464; font-weight:bold; text-decoration:none; line-height:18px; ">${customerNotify.productSale.sellName} &nbsp;<#if customerNotify.productSale.promValue?? >${customerNotify.productSale.promValue}</#if></a></div>
                  <font style="color:#2A5B90; text-decoration:none;">${customerNotify.productSale.product.defaultAuthorsATag} / <a href="http://search.winxuan.com/search?manufacturer=${customerNotify.productSale.product.manufacturer?url}">${customerNotify.productSale.product.manufacturer}</a></font><br />
                收藏/关注时间：${customerNotify.productSale.product.productionDate?string('yyyy-MM-dd')}
                <div style="margin:0 auto; padding-top:10px; ">文轩价：<span style="margin:0 auto; width:570px; padding-top:22px; line-height:22px;"><font style=" font-size:12px; font-weight:bold; color:#FF0000; ">￥${customerNotify.productSale.effectivePrice?string("00.00")}</font></span>(${customerNotify.productSale.discount*100}折)<span>&nbsp;&nbsp;&nbsp;&nbsp;</span><a href="${customerNotify.url}" target="_blank"><font style=" font-size:12px; font-weight:bold; color:#C79000; text-decoration:underline; ">立即购买</font></a></div></td>
            </tr>
        </table></td>
      </tr>
	</#list>
    </table>
</div>
<#if productRecommendation?? >
<div id="03prodtucts_3" style="margin:0 auto; width:570px; padding-top:22px;"><a href="http://www.winxuan.com" target="_blank"><img src="http://static.winxuancdn.com/images/mail/more.jpg" width="570" height="30" border="0" /></a>
  <table width="570" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
  
    <tr>
	<#list productRecommendation as productSale>
      <td width="25%" align="center" valign="top"><a href="${productSale.url}" target="_blank"><img src="${productSale.imageUrl}" width="80" height="112" border="0" /></a>
        <div style="margin-top:5px; margin-bottom:5px; "><a href="${productSale.url}" target="_blank" style=" text-decoration:none;"><font style="color:#2A5B90; text-decoration:none; line-height:18px;">${productSale.sellName} &nbsp;<#if productSale.promValue?? >${productSale.promValue}</#if></font></a></div>
        <span style="margin:0 auto; padding-top:10px; "><span style="margin:0 auto; width:570px; padding-top:22px; line-height:22px;"><font style=" font-size:12px; font-weight:bold; color:#FF0000; ">￥${productSale.effectivePrice?string("00.00")}</font></span>(${productSale.discount*100}折)</span></td>
	</#list>
    </tr>
	
  </table>
</div>
</#if>
    <div id="04help" style="margin:0 auto; width:570px; padding-top:22px; padding-bottom:30px;font-family:'宋体';font-size: 12px;"><strong>常见问题及相关帮助：</strong>
      	<div style=" margin:0 auto; width:570px; line-height:18px; padding-top:5px;font-family:'宋体';font-size: 12px;">
		      订单状态：<a href="http://www.winxuan.com/help/order_state.html" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">http://www.winxuan.com/help/order_state.html</font></a><br />
		        支付方式：<a href="http://www.winxuan.com/help/payment_online.html" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">http://www.winxuan.com/help/payment_online.html</font></a><br />
		        物流配送：<a href="http://www.winxuan.com/help/logistic_scope.html" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">http://www.winxuan.com/help/logistic_scope.html</font></a><br />
		        退换货服务：<a href="http://www.winxuan.com/help/return_rules.html" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">http://www.winxuan.com/help/return_rules.html</font></a><br />
		        常见问题：<a href="http://www.winxuan.com/help/faq.html" target="_blank" style="text-decoration:none;"><font style="color:#2A5B90; text-decoration:none;">http://www.winxuan.com/help/faq.html</font></a>
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
