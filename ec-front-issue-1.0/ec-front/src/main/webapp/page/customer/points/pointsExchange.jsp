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
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的帐户</span></div>
    <div class="left_box">
    	<jsp:include page="../../left_menu.jsp" flush="true" >
			<jsp:param name="id" value="3_5" />
		</jsp:include>
    </div>
	<div class="right_box">
	    <h3 class="myfav_tit margin10">我的积分</h3>
	    <h4 class="now_score f14">当前积分：<b class="fb red">${points}</b></h4>
	    <ul class="infor_tab">
	      <li bind="record">积分记录</li>
	      <li class="current_info"  bind="exchange">积分兑换</li>
	    </ul>

	    <ul class="redeem">
	    	<c:forEach items="${presentExchanges}" var="presentExchange" varStatus="status">
		      <li>
		      	<div><a href="javascript:;"><img bind="img" name="${presentExchange.id}" src="${serverPrefix}${presentExchange.image}"/></a></div>
		        <div><a class="link1" href="javascript:;" bind="link" id="${presentExchange.id}">文轩网通用${presentExchange.value}元礼券</a></div>
		        <div>积分：<b class="red fb">${presentExchange.points}</b></div>
		      </li>
		    </c:forEach>
	      <li>
	        <p class="more_cert"><a class="link3 fb" href="javascript:;">更多>></a></p>
	      </li>
	    </ul>
	    <div class="hei10"></div>
	    <p class="border_style"> <b class="fb">积分提示</b><br/>
	      1．您兑换的购物券为电子券，有效期为一年，自成功兑换之日起计算<br/>
	      2．文轩礼券券仅限本ID使用，不能折算为现金、也不能再次兑换为积分<br/>
	      3．每个ID每日限换取三张电子礼券<br/>
	      4．详细积分规则，请参考<a class="link2" href="http://www.winxuan.com/help/score.html" target="_blank">会员积分</a></p>
	  </div>
	  <div class="hei10"></div>
	</div>
<div class="hei10"></div>
<%@include file="../../snippets/version2/footer.jsp" %>

<script src="${serverPrefix}js/points/points.js"></script>

</body>
</html>
