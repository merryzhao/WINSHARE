<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-我的礼券</title>
<jsp:include page="../../snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="acc_order" />
</jsp:include>
</head>
<body>
    <%@include file="../../snippets/version2/header.jsp" %>
<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的帐户</span></div>
    <div class="left_box">
    	<jsp:include page="../../left_menu.jsp" flush="true" >
			<jsp:param name="id" value="3_3" />
		</jsp:include>
    </div>
    <div class="right_box">
	    <h3 class="myfav_tit margin10">我的礼券</h3>
	     <ul class="infor_tab">
	      <li class="current_info">我的礼券</li>
	      <c:if test="${customer.channel.id!=6}">
	      <li class="other_link">
	      <a class="f14 haveline" href="javascript:;" id="active_link">激活一张新礼券</a>
	      </c:if>
	      </li>
	    </ul>
	    <p class="margin10">
	      <select class="selectstyle" id="select" name="select"  bind="present_type">
	      	<option value="all" <c:if test="${empty param.presentType || param.presentType == 'all'}">selected="selected"</c:if>>全部礼券</option>
	      	<option value="valid" <c:if test="${param.presentType == 'valid'}">selected="selected"</c:if>>有效礼券</option>
	      	<option value="used" <c:if test="${param.presentType == 'used'}">selected="selected"</c:if>>已使用礼券</option>
	      	<option value="past" <c:if test="${param.presentType == 'past'}">selected="selected"</c:if>>已过期礼券</option>
	      </select>
	    </p>
	    <div class="hei10"></div>
	    <table width="100%" class="favorite_goods record" cellspacing="0" cellpadding="0">
	      <thead>
	        <tr>
	          <th width="10%"><span class="black">礼券编号</span></th>
	          <th width="9%"><span class="black">面值</span></th>
	          <th width="10%"><span class="black">状态</span></th>
	          <th width="22%"><span class="black">有效期</span></th>
	          <th width="20%"><span class="black">来源</span></th>
	          <th width="29%"><span class="black">说明</span></th>
	        </tr>
	      </thead>
	      <tbody>
	      	<c:forEach items="${presents}" var="present" varStatus="status">
		        <tr>
		          <td class="nodash">${present.id}</td>
		          <td class="nodash" style="text-align:right;">￥${present.value}</td>
		          <c:choose>
		          	<c:when test='${present.state.id=="17003"}'>
			          	<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set> 
						<c:if test="${nowDate-present.endDate.time > 0}"> 
							<td class="nodash"><b class="present_has_past">已过期</b></td>
						</c:if>
						<c:if test="${nowDate-present.endDate.time <= 0}"> 
							<td class="nodash"><b class="green fb">有效</b></td>
						</c:if>
		          	</c:when>
		          	<c:when test='${present.state.id=="17004"}'>
		          		<td class="nodash"><b>已使用</b></td>
		          	</c:when>
		          	<c:when test='${present.state.id=="17005"}'>
		          		<td class="nodash"><b>已使用</b></td>
		          	</c:when>
		          </c:choose>
		          <td class="nodash"><fmt:formatDate value="${present.startDate}" pattern="yyyy.MM.dd"/>～<fmt:formatDate value="${present.endDate}" pattern="yyyy.MM.dd"/></td>
		          <td class="nodash" style="text-align:left;">${present.origin.name}<c:if test="${present.origin.id==17103}">，来源订单（<a href="http://www.winxuan.com/customer/order/${present.originOrder.id}">${present.originOrder.id}</a>）</c:if></td>
		          <td class="nodash" style="text-align:left;"><p class="txt_left">${present.batch.productTypeName}类满${present.batch.orderBaseAmount}元使用
		          <c:if test="${!present.batch.ploy}">，不参与促销活动</c:if>
		          </p></td>
		        </tr>
		    </c:forEach>
	      </tbody>
	    </table>
	    <c:if test="${pagination.count == 0}"><div class="tips">暂无礼券</div></c:if>
	    <c:if test="${pagination.count > 0}"><winxuan-page:page pagination="${pagination}" bodyStyle="front-user" /></c:if>
	    
	    <p class="border_style margin10 gray">
	      <b class="fb f14">什么是礼券</b><br/>礼券是购物支付结算时，用于抵扣一部分应付订单金额的支付抵扣券<br/><br/>
	      <b class="fb f14">如何使用礼券?</b><br/>了解更多礼券使用的详情，请点击<a class="link1" href="http://www.winxuan.com/help/gift_ticket.html" target="_blank">礼券使用细则</a><br/><br/>
	      <b class="fb f14">如何得到购物礼券?</b><br/>您可以通过积分兑换或者参加文轩网不定期举行的促销赠礼券活动获得购物礼券。
	    </p>
	    
	    <div class="activate_box" style="display:none;">
	    	<h3><a href="javascript:;" class="close">close</a><span>激活新礼券</span></h3>
	    	<div class="coupon_box">
		    	<p class="cou_tips">激活是对礼券的验证过程，激活后的礼券才能使用</p>
		    	<p class="txt_center input_cou">激活码：<input name="code" type="text" value=""/></p>
		    	<div style="text-align:center;margin-bottom:40px;"><button class="pas_save" name="active">激活</button></div>
		  	</div>
	    </div>
	  </div>
	  <div class="hei10"></div>
	</div>
<div class="hei10"></div>
<%@include file="../../snippets/version2/footer.jsp" %>

<script src="${serverPrefix}js/present/present.js"></script>

</body>
</html>
