<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网我的订单</title>
<jsp:include page="/page/snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="my_acc_order" />
</jsp:include>
</head>
<body>
<jsp:include page="/page/snippets/version2/header.jsp"></jsp:include>
<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的帐户</span></div>
    <jsp:include page="/page/left_menu.jsp">
		<jsp:param name="id" value="1_1" />
	</jsp:include>
    <div class="right_box">
    	<h3 class="myfav_tit margin10">我的订单(共${pagination.count}张)</h3>
    	<div class="o_inquire"> <span class="fr">
            <input class="inqurie_input" type="text" id="orderIdOrConsignee">
            <input class="inqurie_but" type="button" value="查询" id="find_btn">
            </span>
            <select id="createTime">
                <option value="1">近1个月的订单</option>
                <option value="3">近3个月的订单</option>
                <option value="6" selected="selected">近6个月的订单</option>
                <option value="12">近一年的订单</option>
                <option value="0">全部订单</option>
            </select>
            
            <form:form action="/customer/order" commandName="orderForm"  method="get" >
	            <input type="hidden" value="false" name="isCancel"/>
	            <input type="hidden" value="${selectedParameters['orderId']}" name="orderId"/>
	            <input type="hidden" value="${selectedParameters['consignee']}" name="consignee"/>
	            <input type="hidden" value="<fmt:formatDate value="${selectedParameters['startCreateTime']}" pattern="yyyy-MM-dd"/>" name="startCreateTime" time="${selectedParameters['startCreateTime'].time}"/>
	            
	            <select name="processStatus" class ="processStatus" style="width:100px">
					<option value="-1">订单状态 </option>
					<c:forEach var="processStatus" items="${processStatusList}">
						<option value="${processStatus.id}" <c:if test="${processStatus.id == selectedParameters['processStatus'] }">selected="selected"</c:if>> ${processStatus.name} </option>					
					</c:forEach>					
				</select> <select name="payment" style="width: 100px">
					<option value="-1">支付方式</option>
					<%-- 
					<c:forEach var="paymentType" items="${paymentTypeList}">
						<option value="${paymentType.id}"
							<c:if test="${paymentType.id == selectedParameters['paymentType'] }">selected="selected"</c:if>>${paymentType.name}</option>
					</c:forEach>
					 --%>
					 <option value="0" <c:if test="${0 == selectedParameters['payment'] }">selected="selected"</c:if>>网上支付</option>
					 <option value="1" <c:if test="${1 == selectedParameters['payment'] }">selected="selected"</c:if>>货到付款</option>					 
					 <option value="4" <c:if test="${4 == selectedParameters['payment'] }">selected="selected"</c:if>>银行转帐</option>
					 <option value="15" <c:if test="${15 == selectedParameters['payment'] }">selected="selected"</c:if>>暂存款支付</option>
					 <option value="20" <c:if test="${20 == selectedParameters['payment'] }">selected="selected"</c:if>>礼券支付</option>
					 <option value="21" <c:if test="${21 == selectedParameters['payment'] }">selected="selected"</c:if>>礼品卡支付</option>
				</select>
				<select name="invoiceType">
					<option value="-1">发票</option>
					<option value="0" <c:if test="${0 == selectedParameters['invoiceType'] }">selected="selected"</c:if>>未开发票</option>
					<option value="1" <c:if test="${1 == selectedParameters['invoiceType'] }">selected="selected"</c:if>>已开发票</option>
				</select>
			</form:form>
        </div>
        
      <table width="100%" class="orders_data" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <th width="4%"><div><input type="checkbox" id="batchSelectAll" value=""></div></th>
                    <th width="15%">订单编号</th>
                    <th width="14%">收货人</th>
                    <th width="11%">付款方式</th>
                    <th width="9%">订单金额</th>
                    <th width="9%">订单状态  </th>
                    <th width="13%">下单时间 </th>
                    <th width="6%">商家</th>
                    <th width="19%">操作</th>
                </tr>
            </thead>
            <tbody>
            <tr>
                <td colspan="9"><p class="txt_right"><a class="batch_but2" href="javascript:;" id="batchPayOrder">批量支付</a></p></td>
            </tr>
            <c:choose>
            <c:when test="${!empty orderList}">
            <c:forEach var="order" items="${orderList}">
             <tr attr="${order.id}">
             		<td>
             			<input height="30" name="batchOrder" type="checkbox" value="${order.id}"  <c:if test="${!(order.processStatus.id == 8001 && !order.requidCod)}">disabled="disabled"</c:if>/>
             		</td>
                    <td>
                    <a href ="/customer/order/${order.id}"  class="link1" target="_black">${order.id}</a>
                    </td>
                    <td>${order.consignee.consignee}</td>
                    <td>
                    	<ul>
                    	<c:forEach items="${order.paymentList}" var="pl" varStatus="status">
							<li>${pl.payment.name}</li>
                    	</c:forEach>
                    	</ul>
                    <td>${order.salePrice+order.deliveryFee}</td>
                    <td><c:choose>
                    	<c:when test="${order.processStatus.id == 8011}">已发货</c:when>
                    	<c:when test="${order.processStatus.id >= 8006 && order.processStatus.id <= 8010}">已取消</c:when>
                    	<c:otherwise>${order.processStatus.name}</c:otherwise>
                    	</c:choose></td>
                    <td><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>${order.shop.shopName}</td>
                    <td attr="control">
	                    <c:choose>
	                    	<c:when test="${order.processStatus.id == 8001}">
		                    	<c:if test="${!order.requidCod}">
			                    	<a class="link1" href="/customer/checkout/${order.id}">支付</a>&nbsp; 
		                    	</c:if>
		                    	<a class="link1" href="javascript:;" bind="cancelOrder" data-id="${order.id}">取消</a>
	                    	</c:when>
	                    	<c:when test="${(order.processStatus.id == 8004 || order.processStatus.id == 8011) && empty order.receive}">
	                    		<a class="link1" href="javascript:;" bind="receiveOrder" data-id="${order.id}">收货确认</a>&nbsp;
	                    		<c:if test="${order.consignee.needInvoice == false && empty order.invoiceList && order.lastProcessTime.time > lastMonth.time && order.invoiceMoney > 0}">
	                    			<a class="link1" href="/customer/order/invoice/${order.id}">补开发票</a>
	                    		</c:if>
	                    	</c:when>
	                    	<c:when test="${order.processStatus.id == 8005}">
	                    		<a class="link1" href="/customer/bought/${order.id}">写评论</a>&nbsp;
	                    		<c:if test="${order.consignee.needInvoice == false && empty order.invoiceList && order.lastProcessTime.time > lastMonth.time && order.invoiceMoney > 0 }">
	                    			<a class="link1" href="/customer/order/invoice/${order.id}">补开发票</a>
	                    		</c:if>
	                    	</c:when>
	                    	<c:otherwise>---</c:otherwise>
	                    </c:choose>
                    </td>
                </tr> 
                </c:forEach>     
			</c:when>
			<c:otherwise>
				<tr> <td colspan="9" align="center">-- 暂无订单 --</td></tr>
			</c:otherwise>
			</c:choose>
			</tbody>
        </table>
        <c:if test="${!empty orderList}">
        <c:if test="${!empty pagination}">		   
			<winxuan-page:page  bodyStyle="front-user"  pagination="${pagination}" ></winxuan-page:page>
		</c:if>
		</c:if>
		
    </div>
    <div class="hei10"></div>
</div>
<%@include file="/page/snippets/version2/footer.jsp" %>
<form action="/customer/checkout/orders" method="POST" name="batchPayOrder" target="payment">
	<input type="hidden" name="payOrder" value="${param.sellerId}"/>
</form>
<script type="text/javascript" src="${serverPrefix}js/customer/batchOrder.js?20121128"></script>
</body>
</html>
