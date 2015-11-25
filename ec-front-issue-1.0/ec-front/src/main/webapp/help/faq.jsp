<%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>常见问题-文轩网</title>
		<jsp:include page="/page/snippets/v2/meta.jsp"><jsp:param value="help" name="type"/></jsp:include>
	</head>
	<body>
	<div class="wrap">
		<jsp:include page="/page/snippets/v2/header.jsp"></jsp:include>
		<jsp:include page="/page/snippets/v2/navigation.jsp">
 			<jsp:param value="false" name="index" />
		</jsp:include>
		<div class="layout">
		<jsp:include page="help_menu.jsp">
			  	<jsp:param value="help_4" name="label"/>
			  </jsp:include>
		  <div class="right_box">
    <div id="help_r">
      <h3>常见问题</h3>
      <dl>
        <h4>账户常见问题：</h4>
        <dt style="line-height:20px;">Q：订单提交成功后还可以修改收货信息吗？<br>
          A：您好，非常抱歉文轩网当前暂不支持订单页面直接修改收货信息，请您下单前仔细核对你的收货地址姓名电话，以保证商品送达准确无误，请您谅解。 <br>
          <br>
          Q：登陆密码忘记了，可以找回么？<br>
          A：您好，您在登陆界面点击“忘记密码”，通过您填写的邮箱找回密码。 <br>
          <br>
          Q:为什么收不到密码找回邮件？<br>
        A:您好，请您核实您的邮箱垃圾邮件中是否有本店发送的邮件哦。部分电子邮箱会自动屏蔽以公司名称开头的邮件。</dt>
      </dl>
      <dl>
        <h4>购物类常见问题：</h4>
        <dt style="line-height:20px;">Q:无货商品什么时候可以到货呢？<br>
          A:您好，无货商品本店暂时无法为您明确具体到货时间，您可以将商品加关注，获得商品到货提醒。 <br>
          <br>
          Q:商品的规格是是什么？有无附件(光盘)<br>
          A:您好：具体的商品规格参数问题请您关注商品页面信息。 <br>
          <br>
          Q:可以拨打热线电话订购商品吗？<br>
          A:您好，文轩网暂不提供电话订购服务，请您登陆文轩网注册下单，感谢您的配合！ <br>
          <br>
          Q:如何取消订单？<br>
          A:您好，您可以进入“<a href="http://www.winxuan.com/customer/order">我的订单</a>“自行操作订单取消或致电文轩网客服中心由客服为您取消订单；如果订单已进入配送环节不确保能够拦截成功，配送上门时请您拒收，感谢您的配合。 <br>
          <br>
          Q：订单取消后优惠券/礼品卡如何返还？<br>
          A:您好，订单取消成功后礼券会自动返还到您的账户，若礼券过期则不返还，请您谅解。礼品卡金额会自动返回至您的礼品卡。<br> 
          <br>
          Q:订单提交成功后商品降价了，可以退给我差价吗？<br>
          A:文轩网商品随市场价格的波动而每日都有部分调整，请您关注本店，第一时间获得优惠活动信息，更实惠，感谢您的关注！<br>
          <br>
          Q:礼券怎么使用？<br>
          A:<br>
          1) 选购商品后进入订单结算流程，在“<a href="http://www.winxuan.com/shoppingcart">订单信息确认</a>”页面的“<a href="http://www.winxuan.com/shoppingcart">结算</a>”，点击(+)使用优惠券抵消部分总额；<br>
        2) 在<a href="http://www.winxuan.com/customer/present">我的账户-我的礼券</a>输入框中输入礼券编号激活，进入订单内结算即可使用。 </dt>
      </dl>
      <dl>
        <h4>配送类常见问题： </h4>
        <dt style="line-height:20px;">Q:订单已经提交，什么时候可以发货？<br>
          A:您好，订单提交成功后，我们会尽快发货，详情您可以进入“<a href="http://www.winxuan.com/customer/order">我的订单</a>”，查看实时订单进度。<br> 
          <br>
          Q:签收时发现商品包装破损，该如何处理？<br>
        A:您好，商品签收时如包装有破损，请您直接拒收；商品签收后如商品本身存在质量问题，请您直接联系本店<span class="tel">400-702-0808服务热线</span>提交退换货申请，或者您也可以点击“<a href="http://www.winxuan.com/customer">我的账户</a>”-“<a href="http://www.winxuan.com/customer/order">我的订单</a>”找到对应订单进行退换货申请，将有专业售后人员为您解决。<a href="http://www.winxuan.com/customer/returnorder">退换货详情&gt;&gt;</a> </dt>
      </dl>
      <dl>
      <h4>电子书常见问题：</h4>
	      <dt style="line-height:20px;">
	     	Q:我购买了电子书，什么时候能发货？<br>
			A:由于电子书刊商品属于虚拟电子商品，无发货一说，在您成功购买后，系统会自动帮您将电子书刊放入您的个人书架，您可直接到您的订单或书架中找到您已购买的电子书刊。<br>
			<br>
			Q:购买后，发现不是自己想要的，能否退货退款？<br>
			A:您好，很抱歉，由于电子书刊商品的特殊性，只要您的订单完成，商品不支持退货或换货服务；当您点击“支付”，订单会进行数据处理，状态为“等待付款”，此种情况支付已经成功，商品也不支持退货或换货服务。如果您提交订单，支付未成功，不会扣除您任何费用，此次商品购买失败。<br>
			<br>
			Q:付费后允许下载吗？<br>
			A:9月网旗下电子书，大部分是可以在付费后下载阅读的，需要使用专用阅读器；有少量电子书暂未提供下载功能，这类电子书在网页上都是有提示“仅支持在线阅读”，例如http://www.winxuan.com/product/1200631812<br>
			<br>
			Q:购买的电子书内容允许打印或者复制、粘贴吗？（为什么我购买下载的电子书无法打印）
			A:由于网站的电子书都是正版书籍，出于对版权的保护，根据出版社的版权保护要求。目前我们的电子书都不能打印、复制、以及各式转换。
			<br>
			Q:网站电子书都有哪些格式？<br>
			A:9月网目前有：pdf、epub 2种格式的电子书。,PC支持PDF，移动端支持EPUB。<br>
			<br>
			Q:电子书刊有哪些支付方式？<br>
			A:由于电子书刊属于虚拟商品，需要通过在线支付方式进行支付，不支持货到付款方式。在线支付方式包括账户余额、网银在线支付、第三方平台支付（如支付宝、财付通、手机支付等）；（本站账户余额，可通过网银、支付宝、财付通等支付方式进行充值）；<br>
			财付通和支付宝均支持网上银行和信用卡支付（无需注册账号）;如果输入金额大于应支付金额，则多余金额自动充值到帐户；<br>
			<br>
			Q:是否有赠送功能？<br>
			A:自己购买的书下载到本地后，第一次阅读时需要密码认证，只要通过认证后赠送给好友，两方都可以阅读； 如果A买了书，验证后送给B，B验证后送给C，可以一直循环下去。<br>
			<br>
			Q:多人可共用我这个账号阅览吗？<br>
			A:可以的；为了保障您的账号安全，不建议您这样做。<br>
			<br>
			Q: 买了本电子书，“在线阅读”提示出错，“下载阅读”提示空白页？<br>
			A：一、如果您使用了非IE内核浏览器，建议您更换为IE浏览器<br>
			二、您是通过第三方账号(QQ)登陆的用户,购买电子书只支持在线阅读,暂不能在(pc客户端,手机等终端设备)阅读!如想在这些设备上阅读,请先绑定帐号,请谅解!<br>
			<br>
			Q：请问如何绑定帐号？<br>
			A：一、添写邮箱时请注意，所添写的邮箱必须用新邮箱，非注册过的邮箱，否则会提示邮箱已注册。<br>
			二、邮箱无需验证，格式容许出错例如：12345@qq.com.cm、12345@qq.com.com都可以，但是必须记住下次登录要与所添写的内容一致才能登录成功。<br>
			<br>
			Q：我买的电子书内容出现乱码和重叠?<br>
			A：请点击该链接下载字体包安装到你的电脑中：<a target="_blank" href="http://www.9yue.com/readerapp/android2/fonts.rar" title="字体包">点击下载字体包 </a><br>
	      </dt>
      </dl>
    </div>
  </div>
		  <div class="hei10"></div>
		</div>
		<jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
		</div>
	</body>
</html>