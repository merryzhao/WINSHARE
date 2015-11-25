<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<%@page import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-退换货服务</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>
<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  文轩网-退换货服务</span></div>
  <jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="1_2" />
</jsp:include>
  <div class="right_box">
    <h3 class="myfav_tit margin10">退换货服务：换货申请</h3>
    <ul class="step_box">
    <li class="step_statu"><span>1、输入订单号</span></li>
    <li class="step_statu"><span>2、选择需换货商品</span></li>
    <li><span>3、填写换货原因</span></li>
    <li><span>4、确认换货信息</span></li>
    <li class="last_step"><span>5、成功提交</span></li>
    </ul>
    <p class="return_box txt_center f16 fb">您正在提交订单 <b class="red fb">${order.id }</b> 的商品换货申请</p>
   
   
    <table width="100%" class="favorite_goods record margin10" cellspacing="0" cellpadding="0">
      <thead>
        <tr>
          <th width="40%"><b class="black">商品名称</b></th>
          <th width="15%"><b class="black">价格</b></th>
          <th width="15%"><b class="black">可退数量</b></th>
          <th width="15%"><b class="black">换货数量</b></th>
          <th width="15%"><b class="black">申请换货</b></th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="orderItem" items="${order.itemList }" varStatus="status">
        <c:if test="${orderItem.canReturnQuantity>0}">
        <tr>
          <td class="nodash"><p class="txt_left">${orderItem.productSale.product.name}</p></td>
          <td class="nodash">￥${orderItem.salePrice}</td>
          <td class="nodash"><b id="deliveryquantity">${orderItem.canReturnQuantity}</b></td>
          <td class="nodash"><input class="txt_center" type="text" size="3" name="quantity"/></td>
          <td class="nodash"><input name="returnOrderItem" type="checkbox" value="${orderItem.id}"/></td>
        </tr>
        </c:if>
        </c:forEach>
      </tbody>
    </table>
     <form:form action="/customer/returnorder/step3" name="selectReturnItemForm">
     	<input type="hidden" name="isReturn" value="false"/>
     	 <input type="hidden" name="orderId" value="${order.id }"/>
    	<p class="txt_center margin10"><input class="red_but" bind="selectReturnItemSubmit" name="selectReturnItemSubmit" type="button" value="下一步"></p>
    </form:form>
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/footer.jsp"%>
<script type="text/javascript" src="${serverPrefix}js/returnorder/returnorder.js"></script>	
</body>
</html>
