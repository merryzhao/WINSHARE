<%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>下单演示（非电子书商品）-文轩网</title>
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
			  	<jsp:param value="help_21" name="label"/>
			  </jsp:include>
		  <div class="right_box">
    <div id="help_r">
      <h3>下单演示（非电子书商品）</h3>
      <dl><dt>&nbsp;</dt>
      </dl>
      <dl><dt>进入文轩网，选择“登录”或者“免费注册”；</dt>
        <dt><img src="images/buy-01.jpg" width="750" height="218"></dt>
        <dt>　输入您的“用户名”和“密码”，然后点击“登录；</dt>
        <dt><img src="images/buy-02.jpg" width="750" height="495"></dt>
        <dt>　找寻您需要的商品，可以进入搜索页面，输入您感兴趣的图书，例如“乔布斯”，然后点击“搜索”；</dt>
        <dt><img src="images/buy-03.jpg" width="750" height="414"></dt>
        <dt>　您可以在搜索到的列表页面点击：“购买”，操作成功后，会弹出快捷购物车，你可以选择“继续购物”，还可以直接“结算”；</dt>
        <dt><img src="images/buy-04.jpg" width="750" height="402"></dt>
        <dt>　您可以在商品详细页面点击“加入购物车”</dt>
        <dt><img src="images/buy-05.jpg" width="750" height="487"></dt>
      </dl>
    </div>
  </div>
		  <div class="hei10"></div>
		</div>
		<jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
		</div>
	</body>
</html>