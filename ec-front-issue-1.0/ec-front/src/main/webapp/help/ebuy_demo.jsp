<%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>下单演示（电子书商品）-文轩网</title>
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
			  	<jsp:param value="help_21_1" name="label"/>
			  </jsp:include>
		  <div class="right_box">
    <div id="help_r">
      <h3>下单演示（电子书商品）</h3>
      <dl>
      	<dt>&nbsp;</dt>
      </dl>
      <dl>
      	<dt><img src="images/libg.png" class="limg" />  进入文轩网首页或电子书频道首页，选择登录或免费注册，如下图所示：</dt>
        <dt><img src="images/ebuy-01.png" width="750" height="145"></dt>
        <dt><img src="images/libg.png"  class="limg" />  选择免费注册，填写以下注册信息：</dt>
        <dt><img src="images/ebuy-02.png" width="750" height="410"></dt>
        <dt><img src="images/libg.png" class="limg" />  注册成功后输入您的账户（注册邮箱），如果系统检测到您在文轩网和原九月网有同一账户时，会提示您选择账户类型（文轩网、九月网），然后输入对应的密码，点击“登录”即可。也可通过第三方账号：如QQ、支付宝、新浪微博、豆瓣账户进行登录：</dt>
        <dt><img src="images/ebuy-03.png" width="750" height="490"></dt>
        <dt><img src="images/libg.png" class="limg" />  登录成功后，在顶部搜索框输入您要搜索的商品名，如“凯特王妃”，并在左侧下拉列表中选择“电子书”，回车或点击搜索，即进入搜索结果页，如下图：</dt>
        <dt><img src="images/ebuy-04.png" width="750" height="255"></dt>
        <dt><img src="images/libg.png"  class="limg" />  搜索结果页显示与您搜索词相匹配的商品信息，您可以在搜索结果页，点击“购买电子书”，即进入“我的购书框”页面，确认订单信息：</dt>
        <dt><img src="images/ebuy-05.png" width="750" height="340"></dt>
        <dt><img src="images/libg.png" class="limg" /> 同时该书已自动添加到页面顶部导航栏右侧的“购物车-电子书”中，您可以直接去“我的购书框”结算：</dt>
        <dt><img src="images/ebuy-06.png" width="750" height="400"></dt>
        <dt><img src="images/libg.png" class="limg" /> 在电子书页面，您可以点击“在线试读”，或点击“购买电子书”，将该电子书加入您的购书框：</dt>
        <dt><img src="images/ebuy-07.png" width="750" height="380"></dt>
        <dt><img src="images/warning.png" class="limg" /><b class="warning">  注意：“在线试读”需安装浏览器插件，可能个别浏览器不支持我们的插件，建议您更换其他浏览器试一下！</b></dt>
        <dt><img src="images/libg.png" class="limg" /> 进入“我的购书框”，确认您的订单信息后，您可以选择“继续购物”或“去付款”：</dt>
        <dt><img src="images/ebuy-08.png" width="750" height="380"></dt>
        <dt><img src="images/libg.png" class="limg" /> 进入支付页面，您可以选择平台支付：支付宝、财付通、手机支付，或电子书余额支付（原九月网账户余额）：</dt>
        <dt><img src="images/ebuy-09.png" width="750" height="450"></dt>
        <dt><img src="images/love.png" /> 温馨提示：支付成功后进入“我的文轩” &rarr; “我的数字馆” &rarr; “我的书架”中，查看到您已购买的电子书，将该书下载到本地，并安装电子书专用阅读器（Ipad版请<a href="http://cnrdn.com/uWy5">点击下载</a>；Android版请<a href="http://cnrdn.com/hNy5">点击下载</a>；PC端请<a href="http://cnrdn.com/kNy5">点击下载</a>），登录您的账号进行阅读。</dt>
      </dl>
    </div>
  </div>
		  <div class="hei10"></div>
		</div>
		<jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
		</div>
	</body>
</html>