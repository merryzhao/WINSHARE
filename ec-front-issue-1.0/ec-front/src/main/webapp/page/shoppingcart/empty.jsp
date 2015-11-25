<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
  <title>文轩网购物车</title>
  <link rel="stylesheet" href="${miscServer}/css/v1/reset.css"/>
  <link rel="stylesheet" href="${miscServer}/css/v1/app/cart.css"/>
  <!--[if lte IE 6]>
	<link rel="stylesheet" href="${miscServer}/css/v1/app/cart-ie-fix.css"/>
  <![endif]-->
</head>
<body class="cart">
<div class="layout">
<div class="header">
	<h1><img src="${miscServer}/css2/images/logo.png"/></h1>
	<hr/>
	<div class="progress"></div>
</div>
<div class="content">
	<div class="empty">
      <p class="follow">您的购物车是空的，赶紧去</p>
	  <div class="follow"><c:import url="http://www.winxuan.com/fragment/2000"/></div>
	  <p class="follow">看看吧</p>
      <p>您也可以从<a href="http://www.winxuan.com/customer/favorite" class="red">我的收藏</a>中取出商品</p>
  	</div>
</div>
<div class="footer">
	<ul>
		<li><a href="http://www.winxuan.com/company/partner.html" target="_blank">合作伙伴</a></li>           		
		<li><a href="http://www.winxuan.com/company/about_us.html" target="_blank">公司简介</a></li>           		
		<li><a href="http://www.winxuan.com/company/job.html" target="_blank">诚聘英才</a></li>           		
		<li><a href="http://api.winxuan.com/" target="_blank">文轩网API</a></li>           		
		<li><a href="http://union.winxuan.com/" target="_blank">联盟合作</a></li>           		
 	</ul>
 	<p>电子公告许可证编号：川邮局[2001]012号 川预审F42T-02PV-XF08-B2N0号</p>
 	<p>地址：四川省成都市金牛区蓉北商贸大道文轩路6号2楼 邮编：610081</p>
 	<p>Copyright (C)四川文轩在线电子商务有限公司 2000-2011, All Rights Reserved</p>
</div>
</div>
<script src="${miscServer}/libs/core.js?${version}"></script>
<script src="${serverPrefix}js/thirdparty.js?${version}"></script>
</body>
</html>