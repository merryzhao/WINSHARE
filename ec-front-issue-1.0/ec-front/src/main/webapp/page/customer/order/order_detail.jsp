<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网 订单状态-发货</title>
<jsp:include page="../../snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="my_acc_order" />
</jsp:include>
</head>

<body class="order_detail">
<jsp:include page="/page/snippets/version2/header.jsp"></jsp:include>

 

<div class="layout">
<div class="order_border2 margin10">
<span class="fr margin10">
	<c:if test="${(order.processStatus.id == 8005 || order.processStatus.id == 8004) && not empty order.receive}"><a class="batch_but2" href="javascript:;" bind="feedback" data-id="${order.id}">收货确认</a></c:if>
	<c:if test="${order.processStatus.id == 8005 && not empty order.receive}"><a class="batch_but2" href="/customer/returnorder">申请退换货</a></c:if>
</span>
<h3 class="myfav_tit margin10">我的资料</h3>
<ul class="order_info">
  <li>订 单 号：${order.id}</li>
  <li>下单时间：<fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
  <li>订单状态：<c:choose>
  		<c:when test="${order.processStatus.id == 8011}">已发货</c:when>
  		<c:when test="${order.processStatus.id >= 8006 && order.processStatus.id <= 8010}">已取消</c:when>
  		<c:otherwise>${order.processStatus.name}</c:otherwise></c:choose> <a class="l_gray haveline" href="http://www.winxuan.com/help/order_state.html" target="_blank">订单状态说明</a></li>
  <li>订单总额：<b class="fb red">￥${order.salePrice}</b></li>
  <li>支付状态：${order.paymentStatus.name}&nbsp;<a class="l_gray haveline" href="http://www.winxuan.com/help/payment_online.html" target="_blank">支付说明</a> 
  	<c:if test="${order.processStatus.id == 8001}">
		<c:if test="${!order.requidCod}">
			<a href="/customer/checkout/${order.id}">
				<span class="red_but">现在支付</span>
		  		<%-- <input type="button" value="现在支付" name="" class="red_but"> --%>
			</a>
	  	</c:if>
  	</c:if>
  </li>
  <li>物流信息：
  <c:if test="${order.processStatus.id == 8011 || order.processStatus.id == 8005 || order.processStatus.id == 8004}">
  	<a href="javascript:;" bind="showloistics" data-id="${order.id}" 
  	  data-canshow="${order.allowedQueryLogistics}"
	  data-loistics="{dcode:'${order.deliveryCode}',dtime:'${order.deliveryTime}',dtype:'${order.deliveryType != null ? order.deliveryType.name : ''}',dcompany:'${order.deliveryCompany != null ? order.deliveryCompany.company : ''}'}">
	  <b class="fb red">查看详细>></b></a>
  </c:if>
  <c:if test="${!(order.processStatus.id == 8011 || order.processStatus.id == 8005 || order.processStatus.id == 8004)}">
  	<a href="javascript:;" bind="noloistics" data-id="${order.id}"><b class="fb red">查看详细>></b></a>
  </c:if>
  </li>
</ul>

<%-- 订单状态图形
	<div class="order_status">
      <p class="status_img"><span>5 - 6 天</span></p>
      <ul class="order_process">
        <li><b class="process_tit">提交订单</b><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
        <c:if test="${!empty order.invoiceList}">
        <c:set var ="invoiceList" value="${order.invoiceList}"></c:set>
        <li>         
              <c:choose>
              		<c:when test="${fn:length(invoiceList)>0}"><b class="process_tit">网上支付</b></c:when>
              		<c:otherwise><b class="unfini_pro">网上支付</b></c:otherwise>
              </c:choose>
              <c:forEach var="invoice" items="${invoiceList}"><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></c:forEach></li>
              <li>   
              <c:choose>
              		<c:when test="${fn:length(invoiceList)>0}"><b class="process_tit">款项到达</b></c:when>
              		<c:otherwise><b class="unfini_pro">款项到达</b></c:otherwise>
              </c:choose>
              <c:forEach var="invoice" items="${invoiceList}"><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></c:forEach></li>
              <li>   
              <c:choose>
              		<c:when test="${fn:length(invoiceList)>0}"><b class="process_tit">订单审核通过</b></c:when>
              		<c:otherwise><b class="unfini_pro">订单审核通过</b></c:otherwise>
              </c:choose>
              <c:forEach var="invoice" items="${invoiceList}"><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></c:forEach></li>
              <li>   
              <c:choose>
              		<c:when test="${orer.deliveryTime!=null}"><b class="process_tit">成都发货</b></c:when>
              		<c:otherwise><b class="unfini_pro">成都发货</b></c:otherwise>
              </c:choose>
              </li>
              </c:if>
        <li><b class="unfini_pro">收货并确认</b></li>
      </ul>
    </div>
     --%>
  <c:set var="consignee" value="${order.consignee}"></c:set>
  <h4 class="info_title">收货人信息</h4>
  <p class="buyer_info">${consignee.consignee}&nbsp;${consignee.mobile}<br>${consignee.country.name},${consignee.province.name},
  <c:if test="${consignee.city.name == consignee.district.name}">
    ${consignee.city.name},
  </c:if>
  <c:if test="${consignee.city.name != consignee.district.name}">
   ${consignee.city.name},${consignee.district.name},${consignee.town.name }
  </c:if>
    ${consignee.address},${consignee.zipCode}</p>
     <h4 class="info_title">送货方式</h4>
  <p class="buyer_info">${order.deliveryType.name}，${order.consignee.deliveryOption.name}&nbsp;&nbsp;
  
  
  <c:if test="${order.processStatus.id == 8011 || order.processStatus.id == 8005 || order.processStatus.id == 8004}">
  	<a  class="l_gray" href="javascript:;" bind="showloistics" data-id="${order.id}" 
  	  data-canshow="${order.allowedQueryLogistics}"
	  data-loistics="{dcode:'${order.deliveryCode}',dtime:'${order.deliveryTime}',dtype:'${order.deliveryType != null ? order.deliveryType.name : ''}',dcompany:'${order.deliveryCompany != null ? order.deliveryCompany.company : ''}'}">
	  查看详细>></a>
  </c:if>
  <c:if test="${!(order.processStatus.id == 8011 || order.processStatus.id == 8005 || order.processStatus.id == 8004)}">
  	<a href="javascript:;" bind="noloistics" data-id="${order.id}">查看详细>></a>
  </c:if>
  </p>
  <h4 class="info_title">付款方式</h4>
  <p class="buyer_info"><c:forEach var="payment" items="${order.paymentList}">                            
             	    			 ${payment.payment.name}
             		 		</c:forEach></p>
  <h4 class="info_title">积分说明</h4>
  <p class="buyer_info">此订单获取积分：${order.purchaseTotalPoints}</p>
  <h4 class="info_title">发票信息</h4>
  	<c:if test="${fn:length(order.invoiceList) == 0}"><p class="buyer_info">无</p></c:if>
	<c:forEach var="invoice" items="${order.invoiceList}">
		<p class="buyer_info">发票抬头：<c:choose><c:when test="${invoice.titleType.id ==3460 }">${order.consignee.consignee }</c:when><c:otherwise>${invoice.title}</c:otherwise></c:choose><br>发票内容：${invoice.content}</p>
	</c:forEach>
  <h4 class="info_title">商品清单</h4>
  <p class="buyer_info">发货方：${order.shop.shopName}</p>
  <table class="cart_goods" width="100%" border="0" cellspacing="0" cellpadding="0" id="product_list">
        <thead>
            <tr>
                <th width="45%" colspan="2">商品名称</th>
                <th>积分</th>
                <th>文轩价</th>
                <th>优惠</th>
                <th>数量</th>
                <th>小计</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
        	<c:forEach var ="orderitem" items ="${order.itemList}">              
            <tr>
                <td><input type="hidden" name="product" data-id="${orderitem.productSale.id}"/><div class="orderDetail"><img src="${orderitem.productSale.product.imageUrlFor55px}" alt="${orderitem.productSale.effectiveName}" class="fl"></div></td>
                <td><p class="ac_goods_intro"><a href="${orderitem.productSale.product.url}" title="${orderitem.productSale.effectiveName}">${orderitem.productSale.effectiveName}</a><br/>
                       </p></td>
                   <td>
                      ${orderitem.points*orderitem.purchaseQuantity}
                   </td>
                <td>￥${orderitem.salePrice} （${fn:substringBefore(orderitem.salePrice/orderitem.listPrice*100,".")}折）</td>
                <td>￥${orderitem.listPrice-orderitem.salePrice}</td>
                <td>
                	<c:choose>
                		<c:when test="${order.processStatus.id == 8011 || order.processStatus.id == 8005}">
                			<c:choose>
                				<c:when test="${orderitem.deliveryQuantity == 0}">(缺货)</c:when>
                				<c:when test="${0 < orderitem.deliveryQuantity && orderitem.deliveryQuantity < orderitem.purchaseQuantity}">${orderitem.purchaseQuantity}(缺货${orderitem.purchaseQuantity - orderitem.deliveryQuantity})</c:when>
		                		<c:otherwise>${orderitem.purchaseQuantity}</c:otherwise>
                			</c:choose>
                		</c:when>
                		<c:otherwise>${orderitem.purchaseQuantity}</c:otherwise>
                	</c:choose>
                </td>
                <td>￥${orderitem.purchaseQuantity*orderitem.salePrice}</td>
                <td><a class="link1" href="${orderitem.productSale.product.url}/comment/_new">写评论</a></td>
            </tr>
        	</c:forEach> 
        </tbody>
    </table>
    <ul class="amount_info bot_line">
      <li>商品金额总计:￥${order.salePrice}</li>
      <li>运费:￥${order.deliveryFee}</li>
      <li>暂存款支付:￥${order.accoutMoney}</li>
      <li class="hei10"></li>
      <li>
      	<c:forEach items="${order.promotionList}" var="promotion">
      		<c:if test="${promotion.type.id != 20005}">
      			<span>&nbsp;&nbsp;&nbsp;
	      		${promotion.type.name}
	      		<c:if test="${promotion.saveMoney != null}">
	      		冲抵: ￥-${promotion.saveMoney}
	      		</c:if>
      			</span>
      		</c:if>
      	</c:forEach>
      	<c:if test="${fn:length(order.sendPresentPromotion)>0 }">
      	&nbsp;&nbsp;&nbsp;<span>${promotion.type.name} 赠送礼券:
      		</span>
      	</c:if>
      	<c:forEach items="${order.sendPresentPromotion}" var="promotion">
	      		<span>
	      		<c:if test="${promotion.saveMoney != null}">
	      		 (￥${promotion.saveMoney} × ${promotion.presentNum})
	      		</c:if>
    	  		</span>
      	</c:forEach>
      </li>
      <li class="black">您需支付:  <b class="red amount_no">￥${order.requidPayMoney}</b></li>
    </ul>
    
</div>
<%-- 
<p class="ogoods_tips border_style">如需再次购买订单中的商品，你可以在此 <a class="link2" href="javascript:;" bind="addItemsToCart" provider="${contextPath}/customer/order/${order.id}">将商品放入购物车</a> 或者 <a class="link2" href="javascript:;" provider="${contextPath}/customer/order/${order.id}" bind="addItemsToFavorite">将商品加入收藏</a></p>
 --%>
<p class="ogoods_tips border_style">如需再次购买订单中的商品，你可以在此 <a class="link2" href="javascript:;" bind="batchAddToCart" data-items="#product_list input[name='product']">将商品放入购物车</a> 或者 <a class="link2" href="javascript:;" bind="batchFavorite" data-items="#product_list input[name='product']">将商品加入收藏</a></p>
</div>
<script type="text/javascript" src="${serverPrefix}js/order_detail.js?${version}"></script>
<%@include file="/page/snippets/version2/footer.jsp" %>
</body>
</html>