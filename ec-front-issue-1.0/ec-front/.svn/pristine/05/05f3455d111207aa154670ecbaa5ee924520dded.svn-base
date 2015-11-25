<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>确认您的订单</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="v1" name="type"/>
</jsp:include>
</head>
<body id="order-confirm" seller="${seller.id}">
<div class="header other_header">
	<p class="service-tel">客服热线：400-702-0808</p>
    <div class="logo_box"><a title="文轩网,新华书店" href="http://www.winxuan.com/"><img src="${serverPrefix}images/logo.gif" alt="文轩网" width="188" height="46"></a></div>
</div>
<div class="layout">
    <div class="progress atf-progress" style="display:none;"><span class="step3"></span></div>
    <div class="progress"><span class="step3"></span></div>
    <p class="promotions">请填写如下信息，然后提交订单！</p>
    <div class="order_border">
    	<div class="new" step="consignee">
	        <h4 class="info_title">收货人信息</h4>
	        <div class="info_bg">
	        <div class="form-info">
	         	<p></p>			
	        </div>
	        <form action="${contextPath}/customer/address" method="POST">
	        <input type="hidden" name="id" value=""/>
	         <ul class="fill_out">
	            <li><span>收&nbsp;&nbsp;货&nbsp;&nbsp;人：</span><input class="blue_border consigneeName" name="consignee" type="text" value="${consigneeName}"/></li>
	            <li><span>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区：</span>
	            <select areaLevel ="country" name="country.id"></select>
	            <select areaLevel ="province" name="province.id"><option value="-1">请选择省份</option></select>
	            <select areaLevel="city" name="city.id"><option value="-1">请选择城市</option></select>
	            <select areaLevel="district" name="district.id"><option value="-1">请选择区县</option></select>
	            <select areaLevel="town" name="town.id" class="town"><option value="-1">请选择乡镇</option></select><span class="town-message" style="display:none;"></span>
	            <a class="link2" href="javascript:alert('TODO')" style="display:none;">查看可货到付款的地区 &gt;&gt;</a>
	            </li>
	            <li><span>街道地址：</span><input type="text" name="address" class="blue_border wider_input required" minlength="4" maxlength="200" title="街道地址由4-200字符组成"></li>
	            <li><span>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</span><input class="blue_border zipCode" name="zipCode" type="text"></li>
	            <li><span>手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</span><input class="blue_border mobile" name="mobile" type="text"> <b class="gray">&nbsp;&nbsp;或者&nbsp;&nbsp;</b> <span>固定电话：</span><input class="blue_border phone" name="phone" type="text" minlength="7" maxlength="20"></li>
	            <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Email：</span><input class="blue_border required email" name="email" type="text" minlength="6" maxlength="100" title="请按Email格式输入,6-60个字符" value="${customer.email}"/></li>
	            <li class='form-message' style='display:none;'></li>
	            <li><button class="confirm_but" type="submit">确认收货人信息</button><span class="loading">正在保存请稍候...</span></li>
	         </ul>
	         </form>
	        </div>
        </div>
        <div class="step" step="delivery">
		<h4 class="info_title">送货方式</h4>
      		<div class="info_bg">
	      		<div class="form-info">
		         	<p></p>			
		        </div>
		        <div class="deliveryTable"></div>
			</div>
        </div>
        <div class="step" step="payment">
        	<h4 class="info_title">付款方式</h4>
	        <div class="info_bg">
		        <div class="form-info">
			         <p></p>			
		        </div>
	        	<div class="paylist">
	        		<ul class="fill_out">
		        		<li><input name="paymentType" type="radio" value="22" checked="checked"> <b class="fb">网上支付</b> <b class="gray">您需要先用有一张已经开通网上支付功能的银行卡</b>
	           				<div class="banks">
	           					<p>文轩网支持以下银行机构在线支付，订单提交后即可选择：</p>
	           					<p><img  src="${serverPrefix}images/bank_list.gif" alt="支持的银行"/></p>
	           					<p>文轩网支持以下平台机构在线支付，订单提交后即可选择：</p>
	           					<p><img  src="${serverPrefix}images/bank_list2.gif" alt="支持的机构"/></p>
	           					<%--
	           					<p>文轩网支持以下国际信用卡在线支付，订单提交后即可选择：</p>
	           					<p><img  src="${serverPrefix}images/bank_list3.gif" alt="支持的国际信用卡"/></p>
	           					--%>
	           				</div>
	           			</li>
	         		</ul>
	        	</div>
	        </div>
        </div>
        <div class="step" step="goods-list">
        	<div class="tableList">
        		<h4 class="info_title">商品清单</h4>
			    <div class="info_bg">
			        <p class="shippers"><a class="fr" href="${contextPath}/shoppingcart">[返回修改购物车]</a>发货商：${shop.shopName}<span class="promotion"></span></p>
			        <table class="order_goods" width="100%" border="0" cellspacing="0" cellpadding="0">
			        	<tr><th align="left">商品名称</th><th>现价</th><th>优惠</th><th>数量</th><th>小计</th></tr>
					    <c:forEach var="item" items="${shoppingcartlist}">
					    	<tr>
					    	<td><p class="txt_left">${item.productSale.sellName}
					    		<c:if test="${not empty item.gifts}">
									<span class="gift">赠:		
									<c:forEach items="${item.gifts}" var="gift">
										<a href="http://www.winxuan.com/product/${gift.giftId}">${gift.giftName}</a>(x ${gift.sendNum})
									</c:forEach>
									</span>
								</c:if>
					    	</p></td>
					    	<td>${item.salePrice}</td>
					    	<td>减${item.savePrice}</td>
					    	<td>${item.quantity}</td>
					    	<td>${item.totalSalePrice}</td>
					    	</tr>
					    </c:forEach>
					</table>
				    <ul class="amount_info">
				      <li bind="salePrice">商品金额总计:￥0.00</li>
				      <li bind="deliveryFee"><span class="freight">运费:￥0.00</span></li>
				      <li bind="presentCardMoney" style="display:none;">礼品卡：￥0.00</li>
				      <li bind="presentMoney" style="display:none;">礼券：￥0.00</li>
				      <li bind="accountMoney" style="display:none;">暂存款：￥0.00</li>
				      <li class="hei10"></li>
				      <li class="black">您共需支付:  <b class="red amount_no">￥31.80</b></li>
				      <li><input class="checkout_but" id="submitOrder" type="button" value="提交订单"></li>
				      <li class="invoice" needValue="0" id="needInvoice">
				      	<p id="askFor" class="black fb cursor_pointer"><input type="checkbox" name="invoiceCheckbox"/>索取发票</p><p class="black" id="invoiceForm">发票抬头：<input name="type" type="radio" id="geren" value="3460" checked="checked"/> <label for="geren">个人</label> <input name="type" type="radio" id="danwei" value="3461"/> <label for="danwei">单位</label> <input class="company_name" name="title" type="text" value="请填写单位名称" default="请填写单位名称,长度在2-40个字符之间" min="2" maxlength="40" style="display:none;"/></p>
				      	<c:if test="${(not empty customer.channel)&&(customer.channel.id==6)}">
			      		<p id="otherInvoiceInfo" style="display:none;">
							<label for="invoiceValue">开票金额：</label>
				      		<input class="invoice_money" name="invoiceValue" type="text" default="${submitOrderForm.invoiceValue}" value="${submitOrderForm.invoiceValue}" min="2" maxlength="20" title="开票金额界于0和商品总码洋之间，不能超过商品总码洋（￥${submitOrderForm.invoiceValue}）"/>
				      	</p>
				      	</c:if>
				      	<p>注：商家可以开具的发票内容请根据商家经营范围选择<br/>（礼券、礼品卡不开具发票）</p>
				      </li>
				    </ul>
		    	</div>
        	</div>
        </div>
    </div>  
</div>
<div style="display:none;">
	<form action="${contextPath}/customer/checkout" method="POST" name="order" target="_self">
		<input type="hidden" name="shopId" value="${shop.id}"/>
		<input type="hidden" name="supplyTypeId" value="${supplyType.id}"/>
		<input type="hidden" name="areaId" value=""/>
		<input type="hidden" name="addressId" value=""/>
		<input type="hidden" name="deliveryTypeId" value=""/>
		<input type="hidden" name="deliveryoption" value=""/>
		<input type="hidden" name="paymentId" value=""/>
		<input type="hidden" name="useAccount" value="false"/>
		<input type="hidden" name="usePresent" value="false"/>
		<input type="hidden" name="usePresentCard" value="false"/>
		<input type="hidden" name="needInvoice" value="0"/>
		<input type="hidden" name="invoiceTitle" value=""/>
		<input type="hidden" name="invoiceType" value=""/>
		<input type="hidden" name="payPassword" value=""/>
		<c:if test="${(not empty customer.channel)&&(customer.channel.id==6)}">
		<input type="hidden" name="invoiceValue" value="${submitOrderForm.invoiceValue}"/>
		</c:if>
	</form>
</div>
<%@include file="../../snippets/footer.jsp" %>
<script src="${serverPrefix}js/checkout_new.js?${version}"></script>
</body>
</html>
