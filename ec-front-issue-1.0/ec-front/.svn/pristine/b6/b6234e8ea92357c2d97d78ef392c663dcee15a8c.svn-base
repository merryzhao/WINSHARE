<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<c:if test="${empty shoppingcart.groupItems}">
	<jsp:forward page="empty.jsp"></jsp:forward>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<title>文轩网购物车</title>
<c:choose>
	<c:when test="${isRelease}">
		<link rel="stylesheet" href="${cssPrefix}/??reset.css,app/cart.css,util/ui.css,wml/signin.css?${version}"/>
	</c:when>
	<c:otherwise>
		<link rel="stylesheet" href="${cssPrefix}/reset.css" />
		<link rel="stylesheet" href="${cssPrefix}/app/cart.css" />
		<link rel="stylesheet" href="${cssPrefix}/util/ui.css" />
		<link rel="stylesheet" href="${cssPrefix}/wml/signin.css" />
	</c:otherwise>
</c:choose>
  <!--[if lte IE 6]>
	<link rel="stylesheet" href="${miscServer}/css/v1/app/cart-ie-fix.css"/>
  <![endif]-->
</head>
<body class="cart">
<div class="layout">
<div class="header">
	<h1><img src="${miscServer}/css2/images/logo.png"/></h1>
	<hr/>
	<c:set var="progressClass" value='${shoppingcart.splited?" split":""}'/>
	<div class="progress${progressClass}"></div>
</div>
<div class="content">
	<!-- 购物车商品列表 开始 -->
	<div class="table-list" id="cart-list">
		<div class="thead">
			<div class="checkbox"><input name="all" type="checkbox"/><label>全选</label></div>
			<div class="product">商品</div>
			<div class="price">文轩价</div>
			<div class="credits">积分</div>
			<div class="privilege">优惠</div>
			<div class="quantity">数量</div>
			<div class="operation">操作</div>
		</div>
		<%-- 购物车条目开始 --%>
		<c:forEach var="seller" items="${shoppingcart.groupItems}">
		<div class="group" data-shop-id="${seller.key.shop.id}" data-supply="${seller.key.supplyType.id}">
			<c:set var="saveProm"/>
			<div class="shop proms"><b>商家：${seller.key.shop.shopName}</b>
				<c:set var="className" value='${(seller.key.supplyType.id==13102)?"new":""}'/>
				<label class="${className}">新品预售</label>
				<ul class="prom-tag">
					<c:forEach var="prom" items="${shoppingcart.proms}">
						<c:if test="${prom.supplyTypeCode==seller.key.supplyType.id&&prom.shopId==seller.key.shop.id}">
							<c:choose>
								<c:when test="${prom.promotionPrice.promType!=20004&&prom.promotionPrice.promType!=20008}">
									<li data-type="${prom.promotionPrice.promType}" data-prom-price="{promType:${prom.promotionPrice.promType},minMoney:${prom.promotionPrice.minMoney},amount:${prom.promotionPrice.amount},saveMoney:${prom.promotionPrice.saveMoney},needMoney:${prom.promotionPrice.needMoney},moreSaveMoney:${prom.promotionPrice.moreSaveMoney}}"></li>
								</c:when>
								<c:otherwise>
									<c:if test="${prom.promotionPrice.promType==20004}">
										<c:set var="saveProm" value="${prom}"/>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<div class="proms">
				<c:if test="${not empty saveProm}">
					<p data-type="${saveProm.promotionPrice.promType}" data-prom-price="{promType:${saveProm.promotionPrice.promType},minMoney:${saveProm.promotionPrice.minMoney},amount:${saveProm.promotionPrice.amount},saveMoney:${saveProm.promotionPrice.saveMoney},needMoney:${saveProm.promotionPrice.needMoney},moreSaveMoney:${saveProm.promotionPrice.moreSaveMoney}}"></p>
				</c:if>
			</div>
			<c:forEach var="item" items="${seller.value}">
			<c:set var="border" value='${(not empty item.gifts)?" no-border":""}'/>
			<c:set var="state" value='${(item.statusCode==13001)?" out-of-stock":""}'/>
			<div class="trow${border}${state}">
				<c:choose>
					<c:when test="${item.statusCode!=13001}">
						<div class="checkbox"><input name="item" type="checkbox" value="${item.productSaleId}"/></div>
						<div class="product">
							<a href="${item.url}" target="_blank"><img src="${item.imageUrl}"/></a>
							<a href="${item.url}" target="_blank">${item.name}</a>
						</div>
						<div class="price">&yen;<em>${item.salePrice}</em></div>
						<div class="credits">${item.points}</div>
						<div class="privilege">&nbsp;</div>
						<div class="quantity">
							<a href="javascript:;" title="减少" class="reduce" bind="reduce" data-id="${item.productSaleId}">减少</a>
							<input name="quantity" value="${item.quantity}" data-value="${item.quantity}" data-id="${item.productSaleId}" bind="change"/>
							<a href="javascript:;" title="增加" class="increase" bind="increase" data-id="${item.productSaleId}">增加</a>
						</div>
						<div class="operation"><a href="javascript:;" bind="remove" data-id="${item.productSaleId}">删除</a></div>
					</c:when>
					<c:otherwise>
						<div class="checkbox"><input name="item" type="checkbox" value="${item.productSaleId}" disabled="disabled"/></div>
						<div class="product">
							<a href="${item.url}" target="_blank"><img src="${item.imageUrl}"/></a>
							<a href="${item.url}" target="_blank">${item.name}</a>
						</div>
						<div class="price">--</div>
						<div class="credits">--</div>
						<div class="privilege">&nbsp;</div>
						<div class="quantity"><em data-id="${item.productSaleId}">此商品已下架</em></div>
						<div class="operation"><a href="javascript:;" bind="arrival_notice" data-id="${item.productSaleId}">到货通知</a></div>
					</c:otherwise>
				</c:choose>
			</div>
			<c:if test="${not empty item.gifts}">
			<div class="gift">
				<c:forEach var="gift" items="${item.gifts}">
				<p>[赠品] ${gift.giftName} x${gift.sendNum}</p>
				</c:forEach>
			</div>
			</c:if>
			</c:forEach>
		</div>
		</c:forEach>
		<%-- 购物车条目结束 --%>
		<%-- 结算信息开始  --%>
		<div class="tfoot">
			<div class="summary">
				<ul>
					<li class="delete"><a href="javascript:;" bind="remove_select">删除所选商品</a></li>
					<li class="favorite"><a href="javascript:;" bind="favorite_select">收藏所选商品</a></li>
				</ul>
				<div class="brief">
					<div>
						<span><b>${shoppingcart.count}</b>件商品</span>
						<label>总计</label>
						<p class="price">&yen;<em>${shoppingcart.salePrice}</em></p>
					</div>
					<div class="save">
						<label>优惠</label>
						<p class="privilege">-&yen;<em>${shoppingcart.saveMoney}</em></p>
					</div>
				</div>
			</div>
			<div class="count">
				<div class="fetch"><a href="#favorite" bind="open_favorite">从收藏中取出商品</a></div>
				<div class="balance">
					<span style="visibility:hidden">本单已免运费</span>
					<label>合计</label>
					<p class="price">&yen;<em>${shoppingcart.salePrice}</em></p>
				</div>
			</div>
			<%-- 结算信息结束  --%>
		</div>
	</div>
	<%-- 购物车商品列表 结束 --%>
	<div class="button-wrap"><a class="continue"><i>继续购物</i><b></b></a><a class="balance"><i>去结算</i><b></b></a></div>
	<div class="history-list">
		<div class="thead">您最近删除的商品，可以重新购买或收藏</div>
	</div>
	<%-- 收藏夹列表 开始--%>
	<div class="combo-tab" id="combo-tab" data-config="{index:0,page:1,size:6}">
		<ul class="combo-nav">
			<li class="selected" id="favorite">我的收藏</li>
		</ul>
		<div class="panels">
			<div class="panel">
				<div class="list"><p class="loading"></p></div>
				<div class="page"></div>
			</div>
		</div>		
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
<%-- <!-- debug -->
<script src="${jsPrefix}/lib/seajs/1.3.0/sea.js?${version}" id="seajsnode"></script>
<script src="${jsPrefix}/etc/seajs-config.js"></script>
<script>
	seajs.use("${jsPrefix}/app/shoppingcart");
</script> --%>

<c:choose>
<c:when test="${isRelease}">
	<script src="${jsPrefix}/??lib/seajs/1.3.0/sea.js,etc/seajs-config.js?${version}" data-main="${jsPrefix}/app/shoppingcart" id="seajsnode"></script>
</c:when>
<c:otherwise>
	<script src="${jsPrefix}/lib/seajs/1.3.0/sea.js"></script>
	<script src="${jsPrefix}/etc/seajs-config.js"></script>
	<script>
		seajs.use("${jsPrefix}/app/shoppingcart");
	</script> 
</c:otherwise>
</c:choose>

</body>	
</html>