<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-我的积分</title>
<jsp:include page="../../snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="acc_order" />
</jsp:include>
</head>
<body>
<div class="layout">
    <%@include file="../../snippets/version2/header.jsp" %>
	<dl class="coupon_info margin10">
	  <dt><img src="${serverPrefix}${presentExchange.image}"></dt>
	  <dd><h1>礼券名称，文轩网图书百货通用${presentExchange.value}元礼券</h1>
	  <p >兑换所需级别：所有会员<br>兑换所需积分：<b class="red">${presentExchange.points}</b><br>当前拥有积分：${points}</p>
	  <p class="margin_t20">
	  	<c:if test="${canExchange==true}">
	  		<a class="exchange" href="javascript:;" bind="exchange">立即兑换</a>
	  	</c:if>
	  	<c:if test="${canExchange==false}">
	  		<span class="no_exchange" >积分不够，不能兑换</span>
	  	</c:if>
	  </p></dd>
	  <dd class="hei10"></dd>
	 </dl>
	  <p class="coupon_tips f14 margin10">
	  <b class="fb">礼券描述</b><br/><br/>
	1、<b class="fb">产品名称：</b>文轩网图书百货通用5元礼券<br/>
	2、<b class="fb">礼券有效期：</b>自兑换之日起至2011年9月30日<br/>
	3、<b class="fb">兑换限量：</b>每个帐户每日可兑换3张，每日数量有限先到先得。<br/>
	4、<b class="fb">产品介绍：</b><br/>
	文轩网图书百货通用5元礼券可在当当网购物时使用，<span class="red">单张订单满25元即可用</span>。<br/>
	1)、每张礼券只能使用一次，使用后从需要支付的订单中扣抵面值同等金额。若用户放弃支付订单，该礼券作废，不可再次使用；<br/>
	2)、礼券与兑换帐户绑定，不可赠送；<br/>
	8)、礼券不可与当当礼品卡、贵宾礼品卡同时使用；<br/>
	4)、礼券必须在有效期前使用，过期后不再有效；<br/>
	5)、礼券适用当当网负责配送的图书/音像/百货所有商品，使用礼券后不能再享受钻石卡/金卡会员折上折；<br/>
	6)、使用礼券的订单将无法享受当当网的促销优惠<br/>
	7)、客服电话：010-51236699；客服邮箱：service@cs.dangdang.com<br/>
	5、使用方法：<br/>
	  下单过程中的核对订单信息页面，点击"使用礼券"，在弹出的对话框中选中BDH10开头的礼券，点击"确定使用"。
	  </p>
	  <div class="activate_box" style="display:none;">
	    <h3><a href="javascript:;" class="close">close</a>兑换确认</h3>
	    <div class="coupon_box">
	      <p class="txt_center input_cou black"><span class="fb f14">您申请的文轩网${presentExchange.value}元礼券</span>，需要扣除积分：<b class="red">${presentExchange.points}</b><br/><br/><button class="pas_save" name="exchange" id="${presentExchange.id}">确定</button><button class="pas_cancel" name="cancel">放弃</button></p>
	    </div>
	  </div>
</div>
<div class="hei10"></div>
<%@include file="../../snippets/version2/footer.jsp" %>

<script src="${serverPrefix}js/points/points.js"></script>

</body>
</html>