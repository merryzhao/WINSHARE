<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>成功提交订单--文轩网 </title>
<style media="print" type="text/css">
.header,.shop_progress,.footer,.links,.order_print {display:none!important;}
</style>  
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="v1" name="type"/>
</jsp:include>
<c:if test="${order.detail != null}">
<script type="text/javascript">
	var _ozprm = "${order.detail}";
</script>
</c:if>
</head>
<body>
	<div class="header other_header">
		<p class="service-tel">客服热线：400-702-0808</p>
		<div class="logo_box">
			<a title="文轩网,新华书店" href="http://www.winxuan.com/"><img src="${serverPrefix}images/logo.gif" alt="文轩网" width="188" height="46">
			</a>
		</div>
	</div>
	<div class="layout">
		<div class="progress"><span class="step4"></span></div>
		<!----新版本---->
        <!--暂存款提示 -->
      
        <c:if test="${order.requidPayMoney == '0.00'}">
         <div class="order_tips_box">
			<div class="order_tips_box_content">
        <p><h4>支付完成！</h4></p>
        <p class="info">10秒后自动跳转，点击<a href="http://www.winxuan.com" class="link6">返回</a>继续购物。</p>
        </div>
		</div>
        <script type="text/javascript">
		setTimeout('window.location="http://www.winxuan.com"',10000);
		</script>
        
        </c:if>
		<c:if test="${order.useAccount && order.requidOnlinePay}">
		 <div class="order_tips_box">
			<div class="order_tips_box_content">
        <p class="info">订单${order.id}已使用暂存款支付<span class="red">￥${order.accountPaidMoney }</span>元，<br/>还须支付<span class="red">￥${order.requidPayMoney}</span>元。请使用网上银行继续完成支付。</p>
		</div>
		</div>
		</c:if>
		
         
       
        <!--暂存款提示 end -->
        <c:if test="${order.requidOnlinePay}">
		<!--提交订单提示框 -->
		<c:if test="${!order.useAccount}">
		<div class="order_tips_box">
			<div class="order_tips_box_content">
				<p><h4>订单提交成功，请您尽快付款！</h4></p>
				<p class="info">订单号：${order.id} | 应付金额： <span class="red">¥ ${order.requidPayMoney}</span><br/>请您在 <strong>24小时</strong> 内完成支付，否则订单会被自动取消。</p>
				<p class="link"><a class="link6" href="/customer/order/${order.id}">查看订单详情</a></p>
			</div>
		</div>
		</c:if>
		<!--提交订单提示框 end-->
		<!--选择付款方式-->
		<div class="payment_choose_box">
			<h3>付款方式</h3>
			<div class="payment_choose_box_content">
				<div class="title">通过网上银行付款<a class="link_help" href="http://www.winxuan.com/help/payment_online.html" target="_blank">查看帮助</a></div>
				<ul class="payment_banks_list cfix">
					<li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=ICBCB2C" name="banks" /><img class="bankimg" src="${serverPrefix}images/gongshang.gif" alt="中国工商银行" /></li>
					<li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=CCB" name="banks" /><img class="bankimg" src="${serverPrefix}images/jianshe.gif" alt="中国建设银行" /></li>
					<li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=ABC" name="banks" /><img class="bankimg" src="${serverPrefix}images/nongye.gif" alt="中国农业银行" /></li>
					<li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=COMM" name="banks" /><img class="bankimg" src="${serverPrefix}images/jiaotong.gif" alt="交通银行" /></li>
					<li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=CMB" name="banks" /><img class="bankimg" src="${serverPrefix}images/zhaoshang.gif" alt="招商银行" /></li>
					<li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=BOCB2C" name="banks" /><img class="bankimg" src="${serverPrefix}images/zhongguo.gif" alt="中国银行" /></li>
                    <li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=CEB-DEBIT" name="banks" /><img class="bankimg" src="${serverPrefix}images/guangda.gif" alt="中国光大银行" /></li>
                    <%--
                    <li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=CITIC" name="banks" /><img class="bankimg" src="${serverPrefix}images/zhongxin.gif" alt="中信银行" /></li>
                    --%>
                    <li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=SDB" name="banks" /><img class="bankimg" src="${serverPrefix}images/SZfazhan.gif" alt="深圳发展银行" /></li>
                    <li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=SPDB" name="banks" /><img class="bankimg" src="${serverPrefix}images/pufa.gif" alt="浦发银行" /></li>
                    <li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=CMBC" name="banks" /><img class="bankimg" src="${serverPrefix}images/minsheng.gif" alt="中国民生银行" /></li>
                    <li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=CIB" name="banks" /><img class="bankimg" src="${serverPrefix}images/xingye.gif" alt="兴业银行" /></li>
                    <li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=GDB" name="banks" /><img class="bankimg" src="${serverPrefix}images/GDfazhan.gif" alt="广东发展银行" /></li>
                    <li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=26&code=HZCBB2C" name="banks" /><img class="bankimg" src="${serverPrefix}images/hangzhou.gif" alt="杭州银行" /></li>
                    <%--
					<li class="lipop"><input class="radio" value="/customer/order/${order.id}/pay?bank=123&code=boc-visa" type="radio" name="banks" /><img class="bankimg" src="${serverPrefix}images/visa.png" alt="VISA" /><span class="banktag"></span>		
                    	<!--弹出提示层-->
						<div class="bankpop">
							<h5>国际信用卡计费方式：</h5>
							<span>顾客联系文轩网客服要求代为取消已支付订单所产生的一切交易费用，由顾客自行承担；<br />若您不同意文轩网关于国际信用卡的计费方式，可使用文轩网其它的支付方式进行支付。</span>
						</div>
                        <!--弹出提示层 end-->
					</li>
                    <li class="lipop"><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=125&code=boc-master" name="banks" /><img class="bankimg" src="${serverPrefix}images/masterCard.png" alt="MasterCard" /><span class="banktag"></span>
                    	<!--弹出提示层-->
						<div class="bankpop">
							<h5>国际信用卡计费方式：</h5>
							<span>顾客联系文轩网客服要求代为取消已支付订单所产生的一切交易费用，由顾客自行承担；<br />若您不同意文轩网关于国际信用卡的计费方式，可使用文轩网其它的支付方式进行支付。</span>
						</div>
                        <!--弹出提示层 end-->
                    </li>
                    <li class="lipop"><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=124&code=boc-jcb" name="banks" /><img class="bankimg" src="${serverPrefix}images/jcb.png" alt="JCB" /><span class="banktag"></span>
                    	<!--弹出提示层-->
						<div class="bankpop">
							<h5>国际信用卡计费方式：</h5>
							<span>顾客联系文轩网客服要求代为取消已支付订单所产生的一切交易费用，由顾客自行承担；<br />若您不同意文轩网关于国际信用卡的计费方式，可使用文轩网其它的支付方式进行支付。</span>
						</div>
                        <!--弹出提示层 end-->
                    </li>
                    --%>
				</ul>
				<div class="title">通过支付平台付款<a class="link_help" href="http://www.winxuan.com/help/payment_online.html" target="_blank">查看帮助</a></div>
				<ul class="payment_banks_list cfix">
					<li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=9" name="banks" /><img class="bankimg" src="${serverPrefix}images/zhifubao.gif" alt="支付宝" /></li>
                    <li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=33" name="banks" /><img class="bankimg" src="${serverPrefix}images/caifutong.gif" alt="财付通" /></li>
                    <li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=82" name="banks" /><img class="bankimg" src="${serverPrefix}images/yinlian.gif" alt="银联电子支付" /></li>
                    <li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=126" name="banks" /><img class="bankimg" src="${serverPrefix}images/shoujizhifu.jpg" alt="手机支付" /></li>
                    <li><input class="radio" type="radio" value="/customer/order/${order.id}/pay?bank=130" name="banks" /><img class="bankimg" src="${serverPrefix}images/alipay-cm.gif" alt="扫码支付" /></li>
				</ul>
				
			</div>
			<div class="payment_btn"><a class="btn" bind="confirm-post" href="##" title="确定付款方式">确定付款方式</a></div>
		</div>
		<!--选择付款方式 end-->
		<!--常见问题-->
		<div class="payment_question">
			<h3>常见问题：</h3>
			<p>关于国际信用卡（VISA、MasterCard、JCB）的计费方式：顾客联系文轩网客服要求代为取消已支付订单所产生的一切交易费用，由顾客自行承担；若您不同意文轩网关于国际信用卡的计费方式，可使用文轩网其它的支付方式进行支付。<a href="http://www.winxuan.com/help/gjwed/index.html" target="_blank">查看详情&gt;</a></p>
			<p>付款时浏览器自动关闭怎么办？<br/>答：点击Intenet选项,点击连接→局域网设置→高级→勾选 “对所有协议均使用相同的代理服务器”。<br/><a href="http://www.winxuan.com/help/help_center.html" target="_blank">查看更多帮助</a></p>
		</div>
		<!--常见问题 end-->
        </c:if>
        <c:if test="${order.requidCod}">
        <!--提交订单提示框-->
		<div class="order_tips_box">
			<div class="order_tips_box_content">
				<p><h4>订单提交成功，请等待收货！</h4></p>
				<p class="info">订单号：${order.id} | 应付金额： <span class="red">¥ ${order.requidPayMoney}</span><br/>您的订单已经在处理中，发货后查看订单信息会显示订单的物流信息</p>
			</div>
			<div class="order_tips_box_attach">
				<h4>货到付款须知：</h4>
				<p class="info">您选择支付方式为货到付款，请您在收货时用现金向送货员支付订单款项。</p>
				<p class="links"><a href="/customer/order/${order.id}">查看订单物流信息</a><a href="/customer/order/${order.id}">查看订单详情</a><a href="/">继续购物</a></p>
			</div>
			
		</div>
		<!--提交订单提示框end-->
        </c:if>
        
        <c:if test="${order.requidBankPay}">
        <div class="order_tips_box">
			<div class="order_tips_box_content">
				<p><h4>订单提交成功，请等待收货！</h4></p>
				<p class="info">订单号：${order.id} | 应付金额： <span class="red">¥ ${order.requidPayMoney}</span><br/>您的订单已经在处理中，发货后查看订单信息会显示订单的物流信息</p>
			</div>
        	<div class="order_tips_box_attach">
				<h4>请将款项转入到以下账户</h4>
				<p class="info">
				银行转账户名：  四川文轩在线电子商务有限公司<br/>
				　开　户　行：  建设银行成都新华支行<br/>
				账　　　　 号： <span class="red">5100 1870 8360 5151 7375</span>
				</p>
				<p class="links"><a href="/customer/order/${order.id}">查看订单物流信息</a><a href="/customer/order/${order.id}">查看订单详情</a><a href="/">继续购物</a></p>
				<a class="order_print" href="javascript:window.print();">打印</a>
			</div>
        </div>
        </c:if>
		<!----新版本 end---->     
    <%--    <!-- 老版本 -->
		<div class="order_border">
			<div class="succ_orders">
				<p class="f14 fb">
				<c:choose>
					<c:when test="${order.requidPayMoney == '0.00'}">
						<span class="hook"></span><b class="red">支付完成！</b>
						<br/><span style="text-decoration:none;color:#0099FF;">10 </span>秒后自动跳转，点击<a href="http://www.winxuan.com" style="font-size:14px">返回</a>继续购物。
						<script type="text/javascript">
						 	setTimeout('window.location="http://www.winxuan.com"',10000);
						</script>
					</c:when>
					<c:when test="${order.useAccount && order.requidOnlinePay}">
					订单${order.id}已使用暂存款支付<b class="red">￥${order.accountPaidMoney }</b>元，还须支付<b class="red">￥${order.requidPayMoney}</b>元。请使用网上银行继续完成支付。
					</c:when>
					<c:otherwise>
					<p>
						<span class="hook"></span><b class="red">恭喜，订单提交成功了！</b>
					</p>
					<p class="f14 fb">
						订单${order.id}已提交，您需要支付 <b class="red">¥ ${order.requidPayMoney}</b>
						元。 <a href="/customer/order/${order.id}">
					</c:otherwise>
				</c:choose>
					<a href="/customer/order/${order.id}">查看订单详情 &gt;&gt;</a>
					<c:if test="${! empty shoppingcart.itemList}">
						<a href="../../shoppingcart/select">继续提交订单&gt;&gt;</a>
					</c:if>
				</p>
				<c:if test="${order.requidOnlinePay}">
					<p class="select_bank">
						请点击以下网银或者支付平台图标进行支付，支付成功后，我们将尽快为您发货。您也可以与其它订单一起 <a href="/customer/order">批量支付</a>。
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
					<dl class="bank_list cl">
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=ICBCB2C"
								target="_blank"><img src="${serverPrefix}images/gongshang.gif"
								alt="中国工商银行"> </a>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=CCB"
								target="_blank"><img src="${serverPrefix}images/jianshe.gif"
								alt="中国建设银行"> </a>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=ABC"
								target="_blank"><img src="${serverPrefix}images/nongye.gif"
								alt="中国农业银行"> </a>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=COMM"
								target="_blank"><img src="${serverPrefix}images/jiaotong.gif"
								alt="交通银行"> </a>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=CMB"
								target="_blank"><img src="${serverPrefix}images/zhaoshang.gif"
								alt="招商银行"> </a>
						</dd>
					</dl>
					<dl class="bank_list cl">
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=BOCB2C"
								target="_blank"><img src="${serverPrefix}images/zhongguo.gif"
								alt="中国银行"> </a>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=CEBBANK"
								target="_blank"><img src="${serverPrefix}images/guangda.gif"
								alt="中国光大银行"> </a>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=CITIC"
								target="_blank"><img src="${serverPrefix}images/zhongxin.gif"
								alt="中信银行"> </a>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=SDB"
								target="_blank"><img src="${serverPrefix}images/SZfazhan.gif"
								alt="深圳发展银行"> </a>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=SPDB"
								target="_blank"><img src="${serverPrefix}images/pufa.gif" alt="浦发银行">
							</a>
						</dd>
					</dl>
					<dl class="bank_list cl">
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=CMBC"
								target="_blank"><img src="${serverPrefix}images/minsheng.gif"
								alt="中国民生银行"> </a>
						</dd>

						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=CIB"
								target="_blank"><img src="${serverPrefix}images/xingye.gif"
								alt="兴业银行"> </a>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=GDB"
								target="_blank"><img src="${serverPrefix}images/GDfazhan.gif"
								alt="广东发展银行"> </a>
						</dd>

						<dd>
							<a href="/customer/order/${order.id}/pay?bank=26&code=HZCBB2C"
								target="_blank"><img src="${serverPrefix}images/hangzhou.gif"
								alt="杭州银行"> </a>
						</dd>
						
					</dl>
					<dl class="bank_list cl">
						<dd class="activity-present">
							<a href="/customer/order/${order.id}/pay?bank=9" target="_blank"><img src="${serverPrefix}images/zhifubao.gif" alt="支付宝"></a>
							<span></span>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=33" target="_blank"><img src="${serverPrefix}images/caifutong.gif" alt="财付通"> </a>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=82" target="_blank"><img src="${serverPrefix}images/yinlian.gif" alt="银联电子支付"> </a>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=126" target="_blank"><img src="${serverPrefix}images/shoujizhifu.jpg" alt="手机支付"> </a>
						</dd>
					</dl>
					<dl class="bank_list cl">
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=123&code=boc-visa" target="_blank"><img src="${serverPrefix}images/visa.png" alt="VISA"> </a>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=125&code=boc-master" target="_blank"><img src="${serverPrefix}images/masterCard.png" alt="MasterCard"> </a>
						</dd>
						<dd>
							<a href="/customer/order/${order.id}/pay?bank=124&code=boc-jcb" target="_blank"><img src="${serverPrefix}images/jcb.png" alt="JCB"> </a>
						</dd>
					</dl>
					<p class="card-tips"><strong>关于国际信用卡（VISA、MasterCard、JCB）的计费方式：</strong>顾客联系文轩网客服要求代为取消已支付订单所产生的一切交易费用，由顾客自行承担；若您不同意文轩网关于国际信用卡的计费方式，可使用文轩网其它的支付方式进行支付。<a href="http://www.winxuan.com/help/gjwed/index.html" target="_blank">详见帮助中心&gt;&gt;</a></p>
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
		</div>
        <!-- 老版本 end -->
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
	<%@include file="../../snippets/footer.jsp"%>
	<script type="text/javascript" src="${serverPrefix}js/post.js?${version}"></script>
</body>
</html>
