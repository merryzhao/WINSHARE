<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>文轩发货DashBoard</title>
	<link href="css/screen.css" media="screen, projection" rel="stylesheet" type="text/css" />
	<link href ="css/print.css" media="print" rel="stylesheet" type="text/css" />
	<!--[if IE]>
	<link href ="/stylesheets/ie.css" media="screen, projection" rel="stylesheet" type="text/css" />
	<![endif]-->
	<%-- 
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
--%>
</head>
<body>
<div class="layout" id="app">
	<div id="map-canvas"></div>
	<div id="theme">双11物流战报(实时更新)</div>
	<dl class="count-down">
			<dt>距离11月20日24点</dt>
			<dd>
				<ul>
					<li class="days num">00</li>
					<li>天</li>
					<li class="hours num">00</li>
					<li>时</li>
					<li class="minutes num">00</li>
					<li>分</li>
					<li class="seconds num">00</li>
					<li>秒</li>
				</ul>
			</dd>
		</dl>
	<div class="content">
	<div id="dev-progressBar" class="progressBar"></div>
	<aside>
		
		<div class="dev-success order-success panel">
			<h2>码洋</h2>
			<div class="count">
				<p>发货目标</p>
				<em>&yen;0.00</em>
			</div>
			<div class="price">
				<p>已发货</p>
				<em>&yen;0.00</em>
			</div>
			<div class="surplus price">
				<p>剩余</p>
				<em>&yen;0.00</em>
			</div>
			<div class="today price">
				<p>今日已发</p>
				<em>&yen;0.00</em>
			</div>
		</div>
	</aside>
	<aside>
		<div class="dev-order-succecc order-success panel">
			<h2>订单</h2>
			<div class="count">
				<p>发货目标</p>
				<em>0.00</em>
			</div>
			<div class="price">
				<p>已发订单</p>
				<em>0.00</em>
			</div>
			<div class="surplus price">
				<p>剩余订单</p>
				<em>0.00</em>
			</div>
			<div class="today price">
				<p>今日已发</p>
				<em>0.00</em>
			</div>
		</div>
	</aside>
	<div id="order-progressBar" class="progressBar"></div>
	</div>
	<div id="curtain">
		<object width="100%" height="100%">
			<param name="movie" value="flash/fireworks.swf"/>
			<param name="wmode" value="transparent">
		</object>
	</div>
</div>
<script src="js/lib/seajs/1.2.1/sea.js" data-main="./js/main.js"></script>
<script src="js/etc/seajs-config.js"></script>
</body>
</html>