<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网我的订单</title>
<jsp:include page="../../snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="acc_order" />
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
     <div class ="ui-widget" >       
           <div class ="ui-widget-content">         
    <form:form action="/customer/order/cancelList" commandName="orderForm"  method="get" >
      <table>
      <tr ><td width="10%"></td><td width="15%" ></td><td width="10%"></td><td width="15%"></td><td width="10%"></td><td width="5%"></td><td width="30%"></td></tr>
   	    <tr><td align="right">订单编号：</td><td> <input type="text" name="orderId" style="width:120px" value="${selectedParameters['orderId']}"/> </td>
               <td align="right">收货人：</td><td><input type="text" name="consignee" style="width:100px" value="${selectedParameters['consignee']}"/></td>			        
	              <td align="right">下单时间：</td><td colspan="3"><input class ="starttime" name="startCreateTime"  bind="datepicker" style="width:100px" value="<fmt:formatDate value="${selectedParameters['startCreateTime']}" pattern="yyyy-MM-dd"/>">
			   ~<input class ="endtime" name="endCreateTime"  bind="datepicker" style="width:100px" value="<fmt:formatDate value="${selectedParameters['endCreateTime']}" pattern="yyyy-MM-dd"/>"/>  </td>       
				  <td align="center" colspan="1"><input type="submit" value="查询" style="width:50px"/>  </td>
				  </tr>
		</table>		                    
   			 </form:form>
           </div>        
        </div>  
    <div class="cl"><a href ="/customer/order"><h3 class="order_tab cancel_order">我的订单</h3></a><h3 class="order_tab ">取消订单</h3></div>
         <table width="100%" class="order_coll txt_center" cellspacing="0" cellpadding="0">           
            <thead>
                <tr>	
                    <th>订单编号</th>
                    <th>收货人</th>
                    <th>支付方式</th>
                    <th>订单总金额</th>
                    <th>订单状态</th>
                    <th>下单时间</th>
                    <th>商家</th>
                </tr>
            </thead>
            <tbody>
            <c:choose>
            <c:when test="${!empty orderList}">
            <c:forEach var="order" items="${orderList}">
             <tr attr="${order.id}">
                    <td>
                    <a href ="/customer/order/${order.id}">${order.id}</a>
                    </td>
                    <td>${order.consignee.consignee}</td>
                    <td>${order.payType.name}</td>
                    <td>${order.salePrice+order.deliveryFee}</td>
                    <td>${order.processStatus.name}</td>
                    <td><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>${order.shop.shopName}</td>             
                </tr> 
                </c:forEach>               
			</c:when>
			<c:otherwise>
				<tr > <td colspan="7" align="center">-- 无取消订单 --</td></tr>
			</c:otherwise>
			</c:choose>
			</tbody>
        </table>
        <c:if test="${!empty orderList}">
        <c:if test="${!empty pagination}">		   
			<div class="margin10 fav_pages"><winxuan-page:page  bodyStyle="front-default"  pagination="${pagination}" ></winxuan-page:page></div>
		</c:if>
		</c:if>
    </div>
    <div class="hei10"></div>
</div>
<%@include file="/page/snippets/version2/footer.jsp" %>
<form action="/customer/checkout/orders" method="POST" name="batchPayOrder" target="_self">
	<input type="hidden" name="payOrder" value="${param.sellerId}"/>
</form>
<script type="text/javascript" src="${serverPrefix}js/customer/batchOrder.js"></script>
</body>
</html>
