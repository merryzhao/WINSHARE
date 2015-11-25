<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-退换货服务-申请详情</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>
<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  文轩网-退换货服务-申请详情</span></div>
  <jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="1_2" />
</jsp:include>
 <div class="right_box">
    <h3 class="myfav_tit margin10">退换货服务-申请详情</h3>
    <table class="returns_info" width="100%" border="0" cellspacing="0">
      <tr>
        <td width="11%"><b class="fb">申请单号：</b></td>
        <td width="89%">${returnOrder.id}（<b class="fb orange">${returnOrder.status.name }</b>）</td>
      </tr>
      <tr>
        <td><b class="fb">申请人：</b></td>
        <td>${returnOrder.customer.name}</td>
      </tr>
      <tr>
        <td><b class="fb">申请时间：</b></td>
        <td><fmt:formatDate value="${returnOrder.createTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
      </tr>
      <tr>
        <td><b class="fb">涉及订单：</b></td>
        <td><a class="link2" href="/customer/order/${returnOrder.originalOrder.id }" target="_blank">${returnOrder.originalOrder.id }</a>
        	<c:if test="${!empty returnOrder.newOrder }">( 换货新订单：<a class="link2" href ="/customer/order/${returnOrder.newOrder.id }" target="_blank">${returnOrder.newOrder.id }</a> )</c:if>
        </td>
      </tr>
    </table>
    <h3 class="app_title">退货商品详情</h3>
    <div class="hei10"></div>
    <table width="100%" class="favorite_goods record" cellspacing="0" cellpadding="0">
      <thead>
        <tr>
          <th width="27%"><b class="black">商品名称</b></th>
          <th width="11%"><b class="black">价格</b></th>
          <th width="10%"><b class="black">退货数量</b></th>
          <th width="34%"><b class="black">退货原因</b></th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="item" items="${returnOrder.itemList }" varStatus="status">
        <tr>
          <td class="nodash"><p class="txt_left"><a href ="${item.orderItem.productSale.product.url }" target="_blank">${item.orderItem.productSale.product.name}</a></p></td>
          <td class="nodash">￥${item.orderItem.balancePrice}</td>
          <td class="nodash">${item.appQuantity }</td>
          <td class="nodash"><p class="txt_left">${item.remark} </p></td>
        </tr>
        </c:forEach>
      </tbody>
    </table>
 </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/footer.jsp"%>
</body>
</html>
