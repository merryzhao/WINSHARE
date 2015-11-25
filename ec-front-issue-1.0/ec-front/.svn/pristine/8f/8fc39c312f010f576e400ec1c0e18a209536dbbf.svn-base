<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>成功提交订单--文轩网 22</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="v1" name="type"/>
</jsp:include>
</head>
<body>
	<div class="header other_header">
		<div class="logo_box">
			<a title="文轩网,新华书店" href="http://www.winxuan.com/"><img src="${serverPrefix}images/logo.gif" alt="文轩网" width="188" height="46">
			</a>
		</div>
	</div>
	<div class="layout">
		<div class="shop_progress">
			<span id="progress3"></span>
		</div>
		<div class="order_border">
			<div class="succ_orders">
				<p>
					<span class="hook"></span><b class="red">恭喜，订单提交成功了！</b>
				</p>
				<p class="f14 fb">
					订单${order.id}已提交，请按以下方式完成支付。<a href="/customer/order/${order.id}">查看订单详情 &gt;&gt;</a>
					<c:if test="${! empty shoppingcart.itemList}">
						<a href="../../shoppingcart/select">继续提交订单&gt;&gt;</a>
					</c:if>
				</p>
				<c:if test="${order.requidOnlinePay}">
					<div class="select_bank">
						支付成功后，我们将尽快为您发货。您也可以与其它订单一起 <a href="/customer/order">批量支付</a>。
						<c:set value="false" var="highLock"/>
						<c:forEach items="${order.itemList}" var="item">
							<c:if test="${item.purchaseQuantity > 21}">
									<c:set value="true" var="highLock"/>
							</c:if>
						</c:forEach>
						<c:if test="${highLock}">
							</br> <p class="f14 fb">由于商品数量较多，订单<B>6小时</B>内未支付将被系统取消，请尽快支付!</p>
						</c:if>
						<c:if test="${!highLock}">
							</br> 商品库存有限，订单<B>24小时</B>内未支付将被系统取消，请尽快支付!
						</c:if>
					</div>
				</c:if>
				<c:if test="${order.requidCod}">
					<p>您选择的货到付款，请您在收货时用现金向送货员支付订单款项。</p>
				</c:if>
				<c:if test="${order.requidPostPay}">
					<p>
						您选择的邮局汇款，请参照以下信息付款：<br />
						请您在邮局汇款单中的&quot;汇款方式&quot;中选择&quot;现金&quot;<br />
						&quot;收款人姓名&quot;中填写：四川文轩在线电子商务有限公司<br />
						在&quot;入帐汇款：汇入帐户&quot;中填写：100434268700010001，同时要填写汇款的金额<br />
						按地址汇款方式：<br /> 收款人姓名：四川文轩在线电子商务有限公司<br />
						收款人地址：四川省成都市金牛区蓉北商贸大道文轩路6号2楼<br /> 邮编：610081<br />
						您订购的商品会在汇款到达文轩网帐户后发出。<br /> 注意事项：办理邮局汇款时，请您务必在邮局汇款单的用途栏内注明订单号。<a
							href="#">查看邮局汇款使用帮助</a>
					</p>
				</c:if>
				<c:if test="${order.requidCash}">
					<p>您选择的现金支付，请您在收货时用现金向文轩在线工作人员支付订单款项。</p>
				</c:if>
				<c:if test="${order.requidBankPay}">
					<p>
						您选择的银行转账付款，请参照以下信息付款：<br /> 银行账户名：四川文轩在线电子商务有限公司<br /> 开 户
						行：建设银行成都新华支行<br /> 帐 号：51001870836051517375
					</p>
				</c:if>
			</div>
			<%--
			<p class="order_tips">
				* 您可以在<a href="../order">我的订单</a>中查看或取消您的订单。<br /> *
				我们会在72小时内为您保留未支付的订单。请及时去<a href="../order">我的订单</a>完成支付
			</p>
			 --%>
			<c:forEach var="orderitem" items="${order.itemList}">
					<input name="cname" type="hidden" value="${orderitem.productSale.product.name}" /><!-- 名称 -->
		            <input name="price" type="hidden" value="${orderitem.balancePrice}" /><!-- 现价 -->
		            <input name="quantity" type="hidden" value="${orderitem.purchaseQuantity}" /><!-- 数量 -->
		            <input name="sort" type="hidden" value="${orderitem.productSale.product.sort.id}" /><!-- 分类 -->
		            <input name="vendor" type="hidden" value="${orderitem.productSale.shop.id}"  /> <!-- 供货商 -->
	         </c:forEach>
	         <input id ="agent" type ="hidden" value="${order.customer.channel.id}"/>
			<c:if test="${cookie.cps_7 != null}">
					<script>
							var orderid = "${order.id}"; 
							var registername = "${order.customer.id}";
					</script>
					<script type="text/javascript" src="${serverPrefix}js/sendtochanet.js"></script>
			</c:if>
		</div>
		
		<div class="order_border">
			<h4 class="check_title f14 fb">付款方式：暂存款支付<span class="red"> &yen;${order.accountUnpaidMoney}</span></h4>
			<div class="check_orders">
			<c:choose>
				<c:when test="${!hasPayPassword }">
					<p>您还没有设置暂存款支付密码，为了保障您的帐户安全，请先<a href="###" bind="set-checkpass">设置支付密码</a></p>
				</c:when>
				<c:otherwise>
				<p class="password_tip"></p>
				<form:form action="/customer/checkout/order/payByAccount" method="POST">
					<div class="pass_field">
						<label>支付密码：</label>
						<input type="password" id="password" name="payPassword"/>
						<input name="orderId" value="${order.id }" type="hidden">
						<a href="###" bind="set-checkpass">忘记密码？</a>
						<c:if test="${!empty message}"><br/>&nbsp;&nbsp;<span style="color:red">${message }</span></c:if>
					</div>
					<div class="btn_field">
						<input type="submit" class="check_btn" value="确认支付">
					</div>
				</form:form>
				</c:otherwise>
			</c:choose>
			</div>
		</div>
	</div>
	<%@include file="../../snippets/footer.jsp"%>
	<script src="${serverPrefix}js/checkpassword.js"></script>
</body>
</html>
