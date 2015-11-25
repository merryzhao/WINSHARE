<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<%!
	private static final Map<Long,String> promMap=new HashMap<Long,String>();
	static{
		promMap.put(20004L,"满￥minMoney省￥amount");
		promMap.put(20005L,"满￥minMoney送￥amount礼券");
		promMap.put(20006L,"满￥minMoney减￥amount运费");
	}
%>
<%pageContext.setAttribute("promMap",promMap);%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择订单--文轩网</title>
<jsp:include page="../snippets/version2/meta.jsp">
	<jsp:param value="v1" name="type"/>
</jsp:include>
</head>
<body>
	<div class="header other_header">
		<p class="service-tel">客服热线：400-702-0808</p>
		<div class="logo_box">
			<a title="文轩网,新华书店" href="http://www.winxuan.com/"><img
				src="${serverPrefix}images/logo.gif" alt="文轩网" width="188" height="46"> </a>
		</div>
	</div>
	<div class="layout">
		<!-- 悬浮表头 开始 -->
		<div class="atf-head" style="display:none;">	
			<div class="progress"><span class="step2"></span></div>
			<div class="thead">
				<div class="product">商品名称</div>
				<div class="price">现价</div>
				<div class="privilege">优惠</div>
				<div class="quantity">数量</div>
				<div class="total">小计</div>
			</div>
		</div>
		<!-- 悬浮表头 结束 -->
    	<div class="progress"><span class="step2"></span></div>
		<div class="order_border">
			<form action="../customer/checkout" method="GET">
				<input type="hidden" name="supplyTypeId"/>
				<p class="order_tips">请依次提交每个商家的订单</p>
				<c:set var="groupedShoppingcart" value="${shoppingcart.groupItems}" />
				<c:set var="groupedProms" value="${shoppingcart.groupProms}"/>
				<c:set var="choice" value="" />
				<c:forEach var="seller" items="${groupedShoppingcart}" varStatus="status">
					<c:if test="${status.count==1}">
						<c:set var="choice" value="${seller.key.shop.shopName}" />
						<c:set var="total" value="${shoppingcart.groupSalePrice[seller.key]}" />
					</c:if>
				<div>
					<p class="order_no">
						<input name="shopId" type="radio" supplyType="${seller.key.supplyType.id}" value="${seller.key.shop.id}" ${status.count==1?"checked":""}>
						<label>
							订单${status.count}：${seller.key.shop.shopName}${seller.key.supplyType.id==13102?"&nbsp;&nbsp;<b class='red'>新品预售</b>":""}
						</label>
					</p>
					<div class="thead">
						<div class="product">商品名称</div>
						<div class="price">现价</div>
						<div class="privilege">优惠</div>
						<div class="quantity">数量</div>
						<div class="total">小计</div>
					</div>
					<table class="product_list" width="100%" cellspacing="0" cellpadding="0">
						<c:forEach var="item" items="${seller.value}">
							<tr>
								<td class="product"><p class="txt_left">${item.sellName}
									<c:if test="${not empty item.gifts}">
										<span class="gift">赠:		
										<c:forEach items="${item.gifts}" var="gift">
											<a href="http://www.winxuan.com/product/${gift.giftId}">${gift.giftName}</a>(x ${gift.sendNum})
										</c:forEach>
										</span>
									</c:if>
								</p></td>
								<td class="price">￥${item.salePrice}</td>
								<td class="privilege">减￥${item.savePrice}</td>
								<td class="quantity">${item.quantity}</td>
								<td class="total"><b class="red fb">￥${item.totalSalePrice}</b></td>
							</tr>
						</c:forEach>
					</table>
					<p class="order_amount">
						<c:if test="${not empty groupedProms[seller.key]}">
							<span class="promotion">促销:
							<c:forEach items="${groupedProms[seller.key]}" var="prom">
								<c:if test="${prom.promotionPrice.saveMoney>0}">
									<c:set var="promString" value="${promMap[prom.promotionPrice.promType]}"/>
									${fn:replace(fn:replace(promString,"minMoney",prom.promotionPrice.minMoney),"amount",prom.promotionPrice.amount)}
								</c:if>
							</c:forEach>
							</span>
						</c:if>
						合计（不含运费）: <b class="total_price">￥${shoppingcart.groupSalePrice[seller.key]}</b>
					</p>
				</div>
				</c:forEach>
				<p class="order_next">
					<a class="link2 fl" href="../shoppingcart"> &lt;返回修改购物车</a>你已经选择了“<b class="red fb tips">订单1：${choice}</b>”，请继续下一步提交该订单！ <button class="checkout_next" type="submit"></button>
				</p>
				<div class="atf-next" style="display:none">
					<p>
						你已经选择了：<b class="red fb tips">订单1：${choice}</b>费用合计（不含运费）：<b class="t_price">￥${total}</b>
					</p>
					<button class="checkout_next" type="submit"></button>
				</div>
			</form>
		</div>
	</div>
	<%@include file="../snippets/footer.jsp"%>
	<script>
		seajs.use(["jQuery","miniATF"],function($,miniATF){
			var headATF = miniATF({context:".atf-head",vertical:"top",refer:".order_amount:last",animate:false,scrollT:90}).init();
			var nextATF = miniATF({context:".atf-next",vertical:"bottom",refer:".order_amount:last",animate:true,scrollT:0}).init();
			var choice=$("input[type=radio]"),
				supplyTypeId=$("input[type='hidden'][name='supplyTypeId']"),
				total=$('.t_price'),
				tips=$('.tips');
			choice.click(function(e){
				var el=$(this);
				total.html(el.parent().siblings(".order_amount").find(".total_price").html());
				tips.html(el.siblings("label").html());
				supplyTypeId.val(el.attr("supplyType"));
				window.location.hash=el.attr("supplyType");
			});
			supplyTypeId.val(choice.filter(":checked").attr("supplyType"));
			if(window.location.hash.length>0){
				tips.html(choice.filter(":checked").siblings("label").html());
				total.html(choice.filter(":checked").parent().siblings(".order_amount").find(".total_price").html());				
			}
		});
	</script>
</body>
</html>
