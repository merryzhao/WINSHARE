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
    <h3 class="myfav_tit margin10">退换货服务-退货申请</h3>
    <ul class="step_box">
    <li class="step_statu"><span>1、输入订单号</span></li>
    <li class="step_statu"><span>2、选择需退货商品</span></li>
    <li class="step_statu"><span>3、填写退货原因</span></li>
    <li class="step_statu"><span>4、确认退货信息</span></li>
    <li class="last_step"><span>5、成功提交</span></li>
    </ul>
    <p class="return_box txt_center f16 fb">您正在提交订单 <b class="red fb">${orderId}</b> 的商品退货申请</p>
    <h3 class="app_title">退货商品详情</h3>
    <div class="hei10"></div>
    <form:form action="/customer/returnorder/step5" method="POST" commandName="returnOrderForms">
    <input type="hidden" name="orderId" value="${orderId}"/>
     <c:forEach var="item" items="${returnOrderItems }" varStatus="status">
    	<input type="hidden" value="${item.appQuantity}" name="returnOrderForm[${status.index }].quantity"></input>
    	<input type="hidden" value="${item.orderItem.id}" name="returnOrderForm[${status.index }].orderItemId"></input>
    	<input type="hidden" value="${item.reason.id}" name="returnOrderForm[${status.index }].reason"></input>
    	<input type="hidden" value="${item.remark}" name="returnOrderForm[${status.index }].remark"></input>
    </c:forEach>
    <table width="100%" class="favorite_goods record" cellspacing="0" cellpadding="0">
      <thead>
        <tr>
          <th width="30%"><b class="black">商品名称</b></th>
          <th width="15%"><b class="black">价格</b></th>
          <th width="13%"><b class="black">退货数量</b></th>
          <th width="42%"><b class="black">退货原因</b></th>
        </tr>
      </thead>
      <tbody>
       <c:forEach var="item" items="${returnOrderItems }">
        <tr>
          <td class="nodash"><p class="txt_left">${item.orderItem.productSale.product.name}</p></td>
          <td class="nodash">￥${item.orderItem.balancePrice}</td>
          <td class="nodash">${item.appQuantity }</td>
          <td class="nodash"><p class="txt_left"><c:out value="${item.remark }"></c:out></p></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <h3 class="app_title">退款方式</h3>
    <p class="margin10">退还到您的<a href="#" class="link2">暂存款</a>账户（您可以随时将款项取出，或者下次购书）</p> 
    <h3 class="app_title">商品退还方式</h3>
    <p class="margin10">自己将商品退还给文轩网</p>
    <p class="return_tips"><b class="fb">提示：</b><br>1、您可以通过邮局，将商品退还给我们，我们收到商品，审核通过后，会将邮费返回到您的<a href="/customer/account" class="link2">暂存款</a>中<br>2、请务必将您的联系方式（姓名+联系电话+邮箱），以及订单号附在包裹中，以便我们及时与你联系。</p>
    <p class="border_style margin10"> <b class="fb">退换提示：</b><br/>
      1．因库存变化较快，在处理换货时可能会发生商品缺货，如遇缺货，我们将作退货处理<br/>
      2．请您将发货清单随包裹寄出，若没有发货清单将会影响您的退换货办理
    </p>
    <p class="txt_center margin10"><input class="red_but" name="" type="submit" value="下一步"></p>
    </form:form>
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/footer.jsp"%>
</body>
</html>
