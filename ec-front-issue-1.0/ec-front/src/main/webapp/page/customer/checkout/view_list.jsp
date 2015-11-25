<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单合并付款--文轩网 </title>
<jsp:include page="${contextPath}/page/snippets/version2/meta.jsp">
	<jsp:param value="v1" name="type"/>
</jsp:include> 
</head>
<body>
	<div class="header other_header">
		<p class="service-tel">客服热线：400-702-0808</p>
		<div class="logo_box">
			<a title="文轩网,新华书店" href="http://www.winxuan.com/"><img
				src="${serverPrefix}images/logo.gif" alt="文轩网" width="188" height="46">
			</a>
		</div>
	</div>
	<div class="layout">
		<div class="progress"><span class="step4"></span></div>
		<div class="order_border">
			<div class="succ_orders">
				<table width="100%" class="order_coll txt_center" cellspacing="0" cellpadding="0">
					 		<thead>
					 		 <tr>
								<th>订单号</th>
								<th>收货人</th>
								<th>下单时间</th>
								<th>需要支付金额</th>
							</tr>
					 		</thead>
				<c:if test="${!empty orderListFailure}">
					 	<tr>
							<td colspan="4"><b >下列订单不能进行合并付款:</b></td>
		      		</tr>
						<c:forEach var="order" items="${orderListFailure}">
							<tr>
							<td><a href="/customer/order/${order.id}">${order.id}</a></td>
							<td>${order.consignee.consignee}</td>
							<td>${order.createTime}</td>
							<td>¥${order.requidPayMoney}</td>
							</tr>					
						</c:forEach>
			</c:if>
			<c:if test="${!empty orderList}">
			<c:set value="true" var="allPaySuccess"></c:set>
			  <tr>
					<td colspan="4"><b class="red">可以合并付款的订单:</b></td>
		      </tr>
		      			
						<c:forEach var="order" items="${orderList}">
							<c:if test="${order.requidPayMoney != '0.00'}">
								<c:set value="false" var="allPaySuccess"></c:set>
							</c:if>	
							<tr>
							<td><input name="order" type="hidden" value="${order.id}"/> <a href="/customer/order/${order.id}">${order.id}</a></td>
							<td>${order.consignee.consignee}</td>
							<td>${order.createTime}</td>
							<td>¥${order.requidPayMoney}</td>
							</tr>			
						</c:forEach>
						</table>
				<p class="f14 fb">
					<c:choose>
						<c:when test="${allPaySuccess}">
							<span class="hook"></span><b class="red">支付完成！</b>
							<br/><span style="text-decoration:none;color:#0099FF;">10 </span>秒后自动跳转，点击<a href="http://www.winxuan.com" style="font-size:14px">返回</a>继续购物。
							<script type="text/javascript">
							 	setTimeout('window.location="http://www.winxuan.com"',10000);
							</script>
						</c:when>
						<c:otherwise>
							您一共需要支付 <b class="red">¥ ${requiredPayMoney}</b>
					元。 <a href="/customer/order">查看订单列表 &gt;&gt;</a>
						</c:otherwise>
					
					</c:choose>
				</p>
				<p class="select_bank">
					请选择以下网银或者支付平台图标后点击确认付款方式进行支付，支付成功后，我们将尽快为您发货。。
				</p>
				<c:if test="${!allPaySuccess}"> 
					<!--选择付款方式-->
					<div class="payment_choose_box">
						<h3>付款方式</h3>
						<div class="payment_choose_box_content">
							<div class="title">通过网上银行付款<a class="link_help" href="http://www.winxuan.com/help/payment_online.html" target="_blank">查看帮助</a></div>
							<ul class="payment_banks_list cfix">
								<li><input class="radio" type="radio" data-bank="26" data-code="ICBCB2C" name="banks" /><img class="bankimg" src="${serverPrefix}images/gongshang.gif" alt="中国工商银行" /></li>
								<li><input class="radio" type="radio" data-bank="26" data-code="CCB" name="banks" /><img class="bankimg" src="${serverPrefix}images/jianshe.gif" alt="中国建设银行" /></li>
								<li><input class="radio" type="radio" data-bank="26" data-code="ABC" name="banks" /><img class="bankimg" src="${serverPrefix}images/nongye.gif" alt="中国农业银行" /></li>
								<li><input class="radio" type="radio" data-bank="26" data-code="COMM" name="banks" /><img class="bankimg" src="${serverPrefix}images/jiaotong.gif" alt="交通银行" /></li>
								<li><input class="radio" type="radio" data-bank="26" data-code="CMB" name="banks" /><img class="bankimg" src="${serverPrefix}images/zhaoshang.gif" alt="招商银行" /></li>
								<li><input class="radio" type="radio" data-bank="26" data-code="BOCB2C" name="banks" /><img class="bankimg" src="${serverPrefix}images/zhongguo.gif" alt="中国银行" /></li>
			                    <li><input class="radio" type="radio" data-bank="26" data-code="CEB-DEBIT" name="banks" /><img class="bankimg" src="${serverPrefix}images/guangda.gif" alt="中国光大银行" /></li>
			                    <%--
			                    <li><input class="radio" type="radio" data-bank="26" data-code="CITIC" name="banks" /><img class="bankimg" src="${serverPrefix}images/zhongxin.gif" alt="中信银行" /></li>
			                    --%>
			                    <li><input class="radio" type="radio" data-bank="26" data-code="SDB" name="banks" /><img class="bankimg" src="${serverPrefix}images/SZfazhan.gif" alt="深圳发展银行" /></li>
			                    <li><input class="radio" type="radio" data-bank="26" data-code="SPDB" name="banks" /><img class="bankimg" src="${serverPrefix}images/pufa.gif" alt="浦发银行" /></li>
			                    <li><input class="radio" type="radio" data-bank="26" data-code="CMBC" name="banks" /><img class="bankimg" src="${serverPrefix}images/minsheng.gif" alt="中国民生银行" /></li>
			                    <li><input class="radio" type="radio" data-bank="26" data-code="CIB" name="banks" /><img class="bankimg" src="${serverPrefix}images/xingye.gif" alt="兴业银行" /></li>
			                    <li><input class="radio" type="radio" data-bank="26" data-code="GDB" name="banks" /><img class="bankimg" src="${serverPrefix}images/GDfazhan.gif" alt="广东发展银行" /></li>
			                    <li><input class="radio" type="radio" data-bank="26" data-code="HZCBB2C" name="banks" /><img class="bankimg" src="${serverPrefix}images/hangzhou.gif" alt="杭州银行" /></li>
			                    <%--
								<li class="lipop"><input class="radio" data-bank="123" data-code="boc-visa" type="radio" name="banks" /><img class="bankimg" src="${serverPrefix}images/visa.png" alt="VISA" /><span class="banktag"></span>		
			                    	<!--弹出提示层-->
									<div class="bankpop">
										<h5>国际信用卡计费方式：</h5>
										<span>顾客联系文轩网客服要求代为取消已支付订单所产生的一切交易费用，由顾客自行承担；<br />若您不同意文轩网关于国际信用卡的计费方式，可使用文轩网其它的支付方式进行支付。</span>
									</div>
			                        <!--弹出提示层 end-->
								</li>
			                    <li class="lipop"><input class="radio" type="radio" data-bank="125" data-code="boc-master" name="banks" /><img class="bankimg" src="${serverPrefix}images/masterCard.png" alt="MasterCard" /><span class="banktag"></span>
			                    	<!--弹出提示层-->
									<div class="bankpop">
										<h5>国际信用卡计费方式：</h5>
										<span>顾客联系文轩网客服要求代为取消已支付订单所产生的一切交易费用，由顾客自行承担；<br />若您不同意文轩网关于国际信用卡的计费方式，可使用文轩网其它的支付方式进行支付。</span>
									</div>
			                        <!--弹出提示层 end-->
			                    </li>
			                    <li class="lipop"><input class="radio" type="radio" data-bank="124" data-code="boc-jcb" name="banks" /><img class="bankimg" src="${serverPrefix}images/jcb.png" alt="JCB" /><span class="banktag"></span>
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
								<li><input class="radio" type="radio" data-bank="9" name="banks" /><img class="bankimg" src="${serverPrefix}images/zhifubao.gif" alt="支付宝" /></li>
			                 <%--    <li><input class="radio" type="radio" data-bank="33" name="banks" /><img class="bankimg" src="${serverPrefix}images/caifutong.gif" alt="财付通" /></li>--%>
			                    <li><input class="radio" type="radio" data-bank="82" name="banks" /><img class="bankimg" src="${serverPrefix}images/yinlian.gif" alt="银联电子支付" /></li>
			                    <li><input class="radio" type="radio" data-bank="126" name="banks" /><img class="bankimg" src="${serverPrefix}images/shoujizhifu.jpg" alt="手机支付" /></li>
			                    <li><input class="radio" type="radio" data-bank="130" name="banks" /><img class="bankimg" src="${serverPrefix}images/alipay-cm.gif" alt="扫码支付" /></li>
							</ul>
							
						</div>
						<div class="payment_btn"><a class="btn" bind="confirm-post-list" href="##" title="确定付款方式">确定付款方式</a></div>
					</div>
					<!--选择付款方式 end-->
					</c:if>
				</c:if> 
			</div>
			<!--常见问题-->
			<div class="payment_question">
				<h3>常见问题：</h3>
				<p>关于国际信用卡（VISA、MasterCard、JCB）的计费方式：顾客联系文轩网客服要求代为取消已支付订单所产生的一切交易费用，由顾客自行承担；若您不同意文轩网关于国际信用卡的计费方式，可使用文轩网其它的支付方式进行支付。<a href="http://www.winxuan.com/help/gjwed/index.html" target="_blank">查看详情&gt;</a></p>
				<p>付款时浏览器自动关闭怎么办？<br/>答：点击Intenet选项,点击连接→局域网设置→高级→勾选 “对所有协议均使用相同的代理服务器”。<br/><a href="http://www.winxuan.com/help/help_center.html" target="_blank">查看更多帮助</a></p>
			</div>
			<!--常见问题 end-->
			<form action="/customer/order/pay" name="batchPayOrder" method="post">
			</form>
		</div>
	</div>
	<%@include file="../../snippets/footer.jsp"%>
	<script type="text/javascript" src="${serverPrefix}js/post.js?${version}"></script>
</body>
</html>
