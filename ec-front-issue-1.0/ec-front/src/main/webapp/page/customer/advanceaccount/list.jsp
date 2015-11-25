<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的暂存款 - 文轩网</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>
<div class="layout">
	<div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的暂存款</span></div>
	<jsp:include page="/page/left_menu.jsp">
		<jsp:param name="id" value="3_7" />
	</jsp:include>
    <div class="right_box">
    	<div class="now_score">
      		<ul class="money_info">
        		<li class="fl">现在暂存款总额：<b class="fb red f16" bind="balance">￥${account.total}</b></li>
        		<li class="fl">被锁定的金额：<b class="fb red f16" bind="frozen">￥${account.frozen}</b></li>
        		<li class="fl">您可以提款：<b class="fb green f16" bind="overagebalance">￥${account.balance}</b>
        		<c:if test="${account.balance >=1}">
        			(<a class="red fb haveline" href="/customer/advanceaccount/refund" id="refundhref" style="display:black">申请退款</a>)
        		</c:if>
				</li>
     		 </ul>
     		 
     		  <div>
     		温馨提示：为了保障您的资金安全，我们对退款方式做了部分调整，<a href="http://www.winxuan.com/help/return_capital.html" target="_black" class="red">查看详情></a>
     		 </div>
     		 
     		 <div>
     		 <c:if test="${!hasPayPassword}">您还没有设置暂存款支付密码，为了保障您的帐户安全，请先<a href="javascript:;" class="link1" bind="set-checkpass">设置支付密码</a></c:if>
     		 </div>
      		 <div class="hei1"></div>
    	</div>
    	<p class="margin10 fb f14">我的暂存款记录</p>  
    		<ul class="infor_tab">
     			<li class="current_info" target="tbl1">暂存款明细</li>
     			<li target="tbl2"><a href="/customer/advanceaccount/refundlist">退款进度查询</a></li>
    		</ul>    
    	<div class="hei10"></div>
    	<c:choose>
    		<c:when test="${!empty details}">
    			<table width="100%" class="favorite_goods record" cellspacing="0" cellpadding="0">
      				<thead>
        				<tr>
          					<th>日期</th> 
          					<th>订单号</th>       
          					<th>存入</th>
          					<th>支出</th>
          					<th>余额</th>
         					<th>状态</th>
        				</tr>
      				</thead>
      				<tbody>
       					<c:forEach var="detail" items="${details}" varStatus="status">
        					<tr>
          						<td class="nodash"><fmt:formatDate value="${detail.useTime}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
          						<td class="nodash"><c:choose><c:when test="${!empty detail.order }">
          							<a href="/customer/order/${detail.order.id}" class="link1">${detail.order.id}</a>
          							</c:when>
          							<c:otherwise>---</c:otherwise></c:choose></td>
          						<td class="nodash">￥<c:choose><c:when test="${detail.hasAdd}">${detail.amount}</c:when><c:otherwise>0.00</c:otherwise></c:choose></td>
          						<td class="nodash">￥<c:choose><c:when test="${!detail.hasAdd}">${detail.absAmount}</c:when><c:otherwise>0.00</c:otherwise></c:choose></td>
          						<td class="nodash">￥${detail.balance }</td>
          						<td class="nodash"><b class="red fb">${detail.type.name}</b></td>
        					</tr>
        				</c:forEach>
      				</tbody>
    			</table>
      			<c:if test="${!empty pagination }">
    				<div class="margin10 fav_pages"><winxuan-page:page bodyStyle="front-user" pagination="${pagination}" ></winxuan-page:page></div>
   				</c:if>
    		</c:when>
    		<c:otherwise>
    			<div class="null_context_tip">你还没有暂存款记录!</div>
    		</c:otherwise>
    	</c:choose>
    	<div class="hei10"></div>
    	<p class="border_style margin10"> 
    		<b class="fb orange">提示：</b><br/>
    		1．	暂存款账户余额是您在文轩网购物时，遗留的未使用现金；<br/>
			2．	在订单结算时，你可以使用您的暂存款进行支付订单金额；<br/>
			3．	您可以申请退款或提现，请参考：<a class="link2" href="http://www.winxuan.com/help/return_capital.html" target="_blank">退款及提现 </a>
      	</p>
    </div>
</div>
<div class="hei10"></div>
<%@include file="../../snippets/footer.jsp"%>
<script type="text/javascript" src="${serverPrefix}js/account/account.js"></script>
<script type="text/javascript" src="${serverPrefix}js/checkpassword.js"></script>
</body>
</html>
