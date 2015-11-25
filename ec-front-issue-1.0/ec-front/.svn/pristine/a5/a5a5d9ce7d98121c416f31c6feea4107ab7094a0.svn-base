<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-我的礼品卡</title>
<jsp:include page="../../snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="acc_order" />
</jsp:include>
</head>
<body>
<div class="layout">
    <%@include file="../../snippets/version2/header.jsp" %>
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的帐户</span></div>
    <div class="left_box">
    	<jsp:include page="../../left_menu.jsp" flush="true" >
			<jsp:param name="id" value="3_4" />
		</jsp:include>
    </div>
	 <div class="right_box">
	    <h3 class="myfav_tit margin10">我的礼品卡</h3>
	     <ul class="infor_tab">
	      <li class="current_info" bind="card">我的礼品卡</li>
	      <li bind="used">使用明细</li>
	      <li class="other_link"><a class="f14 haveline" href="javascript:;" bind="bindCard">绑定一张礼品卡</a></li>
	    </ul>
	    <table width="100%" class="favorite_goods record margin10" cellspacing="0" cellpadding="0">
	      <thead>
	        <tr>
	          <th width="13%"><span class="black">礼品卡卡号</span></th>
	          <th width="11%"><span class="black">面值</span></th>
	          <th width="10%"><span class="black">余额</span></th>
	          <th width="11%"><span class="black">状态</span></th>
	          <th width="14%"><span class="black">绑定时间</span></th>
	          <th width="14%"><span class="black">有效期</span></th>
	          <th width="27%"><span class="black">备注</span></th>
	        </tr>
	      </thead>
	      <tbody>
		      <c:forEach items="${presentCards}" var="presentCard" varStatus="status">
			        <tr>
			          <td class="nodash">${presentCard.id}</td>
			          <td class="nodash" style="text-align:right;">￥${presentCard.denomination}</td>
			          <td class="nodash" style="text-align:right;">￥${presentCard.balance}</td>
			          <c:choose>
			          	<c:when test='${presentCard.status.id=="18006" || presentCard.status.id=="18005"}'>
				          	<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set> 
							<c:if test="${nowDate-presentCard.endDate.time > 0}"> 
								<td class="nodash"><b class="presentcard_has_past">已过期</b></td>
							</c:if>
							<c:if test="${nowDate-presentCard.endDate.time < 0}"> 
								<c:if test="${presentCard.balance>0}"> 
									<td class="nodash"><b class="green fb">有效</b></td>
								</c:if>
								<c:if test="${presentCard.balance.doubleValue() == 0}"> 
									<td class="nodash"><b >已用完</b></td>
								</c:if>
							</c:if>

			          	</c:when>
			          </c:choose>
			          <td class="nodash"><fmt:formatDate value="${presentCard.bindTime}" pattern="yyyy.MM.dd"/></td>
			          <td class="nodash"><fmt:formatDate value="${presentCard.endDate}" pattern="yyyy.MM.dd"/></td>
					<c:if test="${presentCard.type.id == '19001'}"> 
						<td class="nodash">电子卡</td>
					</c:if>
					<c:if test="${presentCard.type.id == '19002'}"> 
						<td class="nodash">实物卡</td>
					</c:if>
			        </tr>
			    </c:forEach>
	      </tbody>
	    </table>
	    
	    <c:if test="${pagination.count == 0}"><div class="tips">暂无礼品卡</div></c:if>
	    <c:if test="${pagination.count > 0}"><winxuan-page:page pagination="${pagination}" bodyStyle="front-user" /></c:if>
	
		<p class="border_style margin10 gray">
	      <a class="link2" href="http://www.winxuan.com/help/gift_card.html" target="_blank">如何使用礼品卡?</a><br/><br/>
	      <b class="fb f14">如何得到礼品卡</b><br/>在线购买或联系团购购买，了解更多礼品卡信息及团购联系方式，<a class="link2" href="http://www.winxuan.com/help/gift_card.html" target="_blank">请点击这里>></a><br/><br/>
	      <b class="fb f14">礼品卡使用规则</b><br/>
1. 礼品卡一旦激活成功，将会与文轩网会员账户绑定，无法跨账户使用。<br/>
2. 品卡金额在购物中如未一次性使用完，其余额可保留于礼品卡账户中，在礼品卡的有效期内再次使用，无使用次数限制。<br/>
3. 除文轩网礼品卡以外，礼品卡可以用于购买文轩网内提供的任何商品。<br/>
4. 结算时，单张订单可使用多张礼品卡合并支付，优先使用距离到期日最近的礼品卡，购物金额不足部分以其他支付方式补足。<br/>
5. 若订单未成功交易，使用的礼品卡金额会在1个工作日内返还至礼品卡账户。<br/>
6. 若订单发生退货，优先返还距离到期日最远的礼品卡金额。<br/>
7. 礼品卡不记名、不挂失、不兑换现金、不退卡。<br/>
	    </p>
	    
	    
	   	<div class="activate_box" style="display:none;">
	    	<h3><a href="javascript:;" class="close">close</a><span>绑定一张礼品卡</span></h3>
	    	<div class="coupon_box">
		    	<p class="cou_tips">绑定是对礼品卡的验证过程，绑定后的礼品卡才能使用；<br>一旦绑定，则只能在此账号使用</p>
		    	<div class="txt_center input_cou">
			    	<div class="text_space"><span>礼品卡卡号：</span><input name="code" type="text" value="" size="25"/></div>
			    	<div class="text_space"><span>密码：</span><input name="password" type="password" value="" size="25"></div>
		    	</div>
		    	<div style="text-align:center;margin-bottom:20px;"><button class="pas_save" name="bind">绑定</button></div>
		  	</div>
	    </div>
	    

	  </div>
	  <div class="hei10"></div>
	</div>
	<div class="hei10"></div>
	<%@include file="../../snippets/version2/footer.jsp" %>
	
	<script src="${serverPrefix}js/presentcard/presentcard.js"></script>

</body>
</html>
