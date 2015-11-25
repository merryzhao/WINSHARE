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
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   文轩网-退换货服务</span></div>
  <jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="1_2" />
</jsp:include>
 <div class="right_box">
    <h3 class="myfav_tit margin10">退换货服务：退货申请</h3>
    <ul class="step_box">
    <li class="step_statu"><span>1、输入订单号</span></li>
    <li><span>2、选择需退货商品</span></li>
    <li><span>3、填写退货原因</span></li>
    <li><span>4、确认退货信息</span></li>
    <li class="last_step"><span>5、成功提交</span></li>
    </ul>
    <form:form action="http://www.winxuan.com/customer/returnorder/step2" method="post" bind="applyReturnForm">
    <p class="return_box txt_center">   
    <b class="f14 black fb">订单号：</b>
    	<input class="blue_input" type="text" size="40" bind="orderId" name="orderId">&nbsp;&nbsp;<b class="red" bind="errorMessage"></b><br/>
    	<input class="red_but" name="" bind="applyReturnSubmit" type="button" value="申请退款"></p>
    </form:form>
        <div>
     		温馨提示：为了保障您的资金安全，我们对退款方式做了部分调整，<a href="http://www.winxuan.com/help/return_capital.html" target="_black" class="red">查看详情></a>
     		 </div>
    <p class="border_style margin10"> 
    <b class="fb red">退货须知：</b><br/>
文轩网承诺自顾客收到商品之日起(以发票日期为准)7日内，如符合以下条件，我们将提供全款退货的服务：<br/>
1．商品及商品的外包装没有损坏(包括包裹填充物及外包装箱或外包装袋)，并保持文轩网出售时的原质原样；<br/>
2．注明退货原因，如果商品存在质量问题，请务必说明；<br/>
3．准备好原始发票及文轩网发货清单；<br/>
4．如果成套商品中有部分商品存在质量问题，在办理退货时，必须提供成套商品。<br/>
5．参加多买多折扣促销的商品，退货时必须全部退回。加申请过换货，则不再允许退货。<br/><br/>
<b class="fb">以下情况不予办理退货：</b><br/>
▪ 任何非由文轩网出售的商品；<br/>
▪ 任何已使用过的商品，但有质量问题除外；<br/>
▪ 任何因非正常使用及保管导致出现质量问题的商品。<br/>
▪ 赠品未返还的商品(商品退货时，如含赠品，赠品需同时退回)。<br/><br/>
<b class="fb">如果您购买的商品属于下列范围，请查看相应的<a class="link2" href="http://www.winxuan.com/help/return_rules.html" target="_blank">特殊说明</a>：</b><br/>
▪ 软件、数码、耗材、手机、笔记本、小家电等类商品；<br/>
▪ 手表、珠宝首饰等类商品；<br/>
▪ 童品、化特品及个人护理、母婴用品、玩具、服装鞋帽、家居等类商品。</p>
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/footer.jsp"%>
	<script type="text/javascript" src="${serverPrefix}js/returnorder/returnorder.js"></script>	
</body>
</html>
