<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>【文轩网礼品卡】这是您的文轩网礼品卡，请妥善保管！</title>
</head>
<body style="margin:0; padding:0; background-color:#F0EFEF; " >
<div style=" margin:0 auto; padding-top:10px; width:100%; background-color:#F0EFEF; font-size:12px; color:#656464; font-family:'宋体';">
  <div id="logo" style="margin:0 auto; width:650px; height:62px;"><a href="http://www.winxuan.com/" target="_blank"><img src="http://www.winxuan.com/images/mail/logo2.jpg" width="650px" height="62px" border="0" /></a></div>
  <div id="menu" style="margin:0 auto; width:650px;  background-color:#9A0117; color:#CC0B27; height:18px; text-align:right;"><a href="http://www.winxuan.com" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">文轩首页</font></a>&nbsp;|&nbsp;<a href="http://www.winxuan.com/customer" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">我的文轩</font></a>&nbsp;|&nbsp;<a href="http://www.winxuan.com/help/help_center.html" target="_blank" style="text-decoration:none;"><font style="color:#fff; text-decoration:none;">帮助中心</font></a></div>
  <div style="margin:0 auto; width:650px; background-color:#ffffff;">
  <div id="01txt" style="margin:0 auto; width:570px; padding-top:22px; line-height:22px;"><strong>亲爱的${order.customer.displayName}：</strong><br />
	&nbsp;&nbsp;&nbsp;下面是您的礼品卡账号、密码，请妥善保管哦！<br />
  </div>
  <#if (presentCards??)&&(presentCards?size == 1)>
	  <div style=" margin:0 auto; padding-top:5px; width:532px; line-height:18px; ">
		  <#list presentCards as presentCard>
			  礼品卡账号：${presentCard.id}<br />
			  礼品卡密码：<#list passwords as password>${password}</#list>
		  </#list>
	  </div>
	<#elseif (presentCards??)&&(presentCards?size > 1)>
	<div id="03prodtucts" style="margin:0 auto; width:570px; padding-top:22px;"><strong>礼品卡信息：</strong>
	  <table width="550" border="0" align="center" cellpadding="5" cellspacing="1" style="margin-top:10px; background-color:#f2f2f2;">
	    <tr>
	      <td width="37" align="center" bgcolor="#FFFFFF">序号</td>
	      <td width="90" align="center" bgcolor="#FFFFFF">礼品卡面值</td>
	      <td width="200" align="center" bgcolor="#FFFFFF">礼品卡账号</td>
	      <td width="100" align="center" bgcolor="#FFFFFF">礼品卡密码</td>
	    </tr>
	    <#list presentCards as presentCard>
	    <tr>
	      <td align="center" bgcolor="#FFFFFF">${presentCard_index + 1}</td>
	      <td align="center" bgcolor="#FFFFFF">￥${presentCard.denomination}</td>
	      <td align="center" bgcolor="#FFFFFF">${presentCard.id}</td>
	      <td align="center" bgcolor="#FFFFFF">
	      	<#list passwords as password>
	      		<#if password_index == presentCard_index>
	      			${password}
	      		</#if>
	      	</#list>
	      </td>
	    </tr>
	    </#list>
	  </table>
	</div>
  </#if>

    <div id="-01user" style="margin:0 auto; width:570px; padding-top:22px; line-height:22px;"><strong>拿到礼品卡后：</strong><br />
        <div style=" margin:0 auto; padding-top:5px; width:532px; line-height:18px; ">1、<a href="http://www.winxuan.com/presentcardorder/activate?orderId=${order.id}&sign=${sign}" target="_blank"><font style="color:#2A5B90; text-decoration: underline;">立即激活</font></a>；<br />
          2、去文轩网消费，点击进入<a href="http://www.winxuan.com" target="_blank"><font style="color:#2A5B90; text-decoration: underline;">文轩网首页</font></a>；<br />
        </div>
    </div>
    <div id="04help" style="margin:0 auto; width:570px; padding-top:22px; padding-bottom:30px"><strong>礼品卡使用须知：</strong>
      <div style=" margin:0 auto; width:570px; line-height:18px; padding-top:5px;">1、<span style=" margin:0 auto; padding-top:5px; width:532px; line-height:18px; "><a href="http://www.winxuan.com/giftcard" target="_blank"><font style="color:#2A5B90; text-decoration: underline;">礼品卡使用方法</font></a></span>；<br />
        2、<span style=" margin:0 auto; padding-top:5px; width:532px; line-height:18px; "><a href="http://www.winxuan.com/giftcard" target="_blank"><font style="color:#2A5B90; text-decoration: underline;">礼品卡如何与账号绑定</font></a></span>；<br />
      3、<span style=" margin:0 auto; padding-top:5px; width:532px; line-height:18px; "><a href="http://www.winxuan.com/giftcard" target="_blank"><font style="color:#2A5B90; text-decoration: underline;">查询礼品卡余额入口</font></a></span>；</div>
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