<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
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
    <h3 class="myfav_tit margin10">退换货服务</h3>
    <p class="border_style margin10">如果您需要退货或是换货服务，您可以<a class="return_but1" href="/customer/returnorder/return">申请退货</a><a class="return_but2" href="/customer/returnorder/change" >申请换货</a></p>
     <br>
     <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;温馨提示：为了保障您的资金安全，我们对退款方式做了部分调整，<a href="http://www.winxuan.com/help/return_capital.html" target="_black" class="red">查看详情></a></div>
    <p class="margin10 d_yellow fb f14">您的退换货申请：</p>
    <c:choose>
    <c:when test="${!empty returnOrders }">
    <table width="100%" class="favorite_goods record margin10" cellspacing="0" cellpadding="0">
      <thead>
        <tr>
          <th width="20%"><b class="black">订单号</b></th>
          <th width="15%"><b class="black">申请单号</b></th>
          <th width="15%"><b class="black">申请类型</b></th>
          <th width="15%"><b class="black">状态</b></th>
          <th width="25%"><b class="black">申请时间</b></th>
          <th width="10%"><b class="black">操作</b></th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="returnorder" items="${returnOrders }" varStatus="status">
       <c:if test="${returnorder.type.id != 24003 }">
        <tr>
          <td class="nodash"><a class="link1" href="/customer/order/${returnorder.originalOrder.id}"  target="_blank">${returnorder.originalOrder.id}</a></td>
          <td class="nodash"><a class="link1" href="/customer/returnorder/${returnorder.id}"  target="_blank">${returnorder.id }</a></td>
          <td class="nodash">${returnorder.pickup.name }</td>
          <td class="nodash"><b class="orange fb"><b class="statusName${returnorder.id}">${returnorder.status.name }</b></b></td>
          <td class="nodash"><fmt:formatDate value="${ returnorder.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
          <td class="nodash"><b class="status${returnorder.id}"><a class="link1" href="/customer/returnorder/${returnorder.id}"  target="_blank">查看</a> <c:if test ="${returnorder.status.id ==25001 }"><a class="link1" href="#" bind="cancel" parma="${returnorder.id}">撤销</a></c:if></b></td>
        </tr>
        </c:if>
        </c:forEach>   
      </tbody>
    </table>
      <c:if test="${!empty pagination }">
    				<div class="margin10 fav_pages"><winxuan-page:page bodyStyle="front-default" pagination="${pagination}" ></winxuan-page:page></div>
   				</c:if>
    </c:when>
    <c:otherwise>
    	<div class="null_context_tip">暂无退换货申请记录</div>
    </c:otherwise>
    </c:choose>
    <p class="border_style margin10"> 
      <b class="fb">退货须知：</b><br/>
1. 使用文轩网礼券的订单退货时不退券，退款将先扣除礼券抵扣额，退回抵扣后的订单金额。<a href="http://www.winxuan.com/help/gift_ticket.html" class="link2" target="_blank">礼券使用帮助</a><br/>
<%-- 2. 如果您的收货地址在<a href="http://www.winxuan.com/help/logistic_scope.html" class="link2"  target="_blank">上门退换货范围</a>内，可享受文轩网免费上门退货服务一次<br/> --%>
2. 详细的退货操作流程，请查看<a href="http://www.winxuan.com/help/return_rules.html" class="link2"  target="_blank">退货规则</a><br/><br/><br/>

<b class="fb">换货须知：</b><br/>
1、使用文轩网礼券的订单换货时不退券，退款将先扣除礼券抵扣额，退回抵扣后的应退订单金额。<a href="http://www.winxuan.com/help/gift_ticket.html" class="link2"  target="_blank">礼券使用帮助</a><br/>
<!-- 2、如果您的收货地址在<a href="http://www.winxuan.com/help/logistic_scope.html" class="link2"  target="_blank">上门退换货范围</a>内，可享受文轩网免费上门换货服务一次<br/> -->
2、详细的换货操作流程，请查看换货规则<br/>

    </p>
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/footer.jsp"%>
<script type="text/javascript" src="${serverPrefix}js/returnorder/returnorder.js"></script>	
</body>
</html>
