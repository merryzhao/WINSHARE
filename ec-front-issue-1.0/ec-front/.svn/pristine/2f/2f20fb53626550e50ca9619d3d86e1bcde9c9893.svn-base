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
		<div class="logo_box">
			<a title="文轩网,新华书店" href="http://www.winxuan.com/"><img
				src="${serverPrefix}images/logo.gif" alt="文轩网" width="188" height="46">
			</a>
		</div>
	</div>
	<div class="layout">
		<div class="shop_progress">
			<span id="progress3"></span>
		</div>
	
		
		<div class="order_border">
			<div class="succ_orders">
				<table width="100%" class="order_coll txt_center" cellspacing="0" cellpadding="0">
					 		<thead>
					 		 <tr>
								<th>订单号</th>
								<th>收货人</th>
								<th>下单时间</th>
								<th>暂存款支付金额</th>
								<th>还需要支付金额</th>
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
							<td>¥${order.accountUnpaidMoney}</td>
							<td>¥${order.requidPayMoney}</td>
							</tr>					
						</c:forEach>
			</c:if>
			<c:if test="${!empty orderList}">
			  <tr>
					<td colspan="4"><b class="red">可以合并付款的订单:</b></td>
		      </tr>
		      			<c:set var="totalAccountPayMoney" value="0"></c:set>
						<c:forEach var="order" items="${orderList}">
							<c:set var="totalAccountPayMoney" value="${totalAccountPayMoney + order.accountUnpaidMoney}"></c:set>
							<tr>
							<td><input name="order" type="hidden" value="${order.id}"/> <a href="/customer/order/${order.id}">${order.id}</a></td>
							<td>${order.consignee.consignee}</td>
							<td>${order.createTime}</td>
							<td>
							<c:choose>
								<c:when test="${order.accoutMoney != 0 && order.accountUnpaidMoney == 0}">
								¥${order.accountPaidMoney}(已支付)
								</c:when>
								<c:when test="${order.accountUnpaidMoney == 0}">
								--
								</c:when>
								<c:otherwise>¥${order.accountUnpaidMoney}</c:otherwise>
							</c:choose>
							</td>
							<td>¥${order.requidPayMoney}</td>
							</tr>			
						</c:forEach>
						
			</c:if>
			</table>
				<p class="f14 fb">
					您一共需要支付 <b class="red">¥ ${requiredPayMoney}</b>
					元。 <a href="/customer/order">查看订单列表 &gt;&gt;</a>
				</p> 
			</div>
			<p class="order_tips">
				&nbsp;&nbsp;&nbsp;&nbsp;* 您可以在<a href="/customer/order">我的订单</a>中查看或取消您的订单。<br /> 
				&nbsp;&nbsp;&nbsp;&nbsp;* 我们会在72小时内为您保留未支付的订单。请及时去<a href="/customer/order">我的订单</a>完成支付
			</p>
		</div>
		<div class="order_border">
			<h4 class="check_title f14 fb">付款方式：暂存款支付<span class="red"> &yen;${totalAccountPayMoney}</span></h4>
			<div class="check_orders">
				<c:choose>
					<c:when test="${!hasPayPassword }">
						<p>您还没有设置暂存款支付密码，为了保障您的帐户安全，请先<a href="###" bind="set-checkpass">设置支付密码</a></p>
					</c:when>
					<c:otherwise>
						<p class="password_tip"></p>
						<form:form action="/customer/checkout/orders/payByAccount" method="POST">
						<div class="pass_field">
							<label>支付密码：</label>
							<input type="password" id="password" name="payPassword"/>
							<c:forEach var="order" items="${orderList}">
								<input name="orderId" value="${order.id }" type="hidden">	
							</c:forEach>
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
	<script type="text/javascript">
	seajs.use(["jQuery"], function($){
		function selectBank(el){
			var bank = $(el).data("bank");
			var code = $(el).data("code");
	        $("input[@name='order'][type='hidden']").each(function(index, order){
	            if ($(order).attr("name") == 'order') {
	                $("form[name='batchPayOrder']").append("<input type='hidden' name='id' value='" + $(order).val() + "'>");
	            }
	        });
	        $("form[name='batchPayOrder']").append("<input type='hidden' name='bank' value='" + bank + "'>");
	        if(!!code){
	        	$("form[name='batchPayOrder']").append("<input type='hidden' name='code' value='" + code + "'>");	        	
	        }
	        var form = $("form[name='batchPayOrder']");
	        form.submit();
	    }
		$("a[bind]").click(function(e){
			selectBank($(this));
			e.preventDefault();
			e.stopPropagation();
		});
	});
	</script>
	<script src="${serverPrefix}js/checkpassword.js"></script>
</body>
</html>
