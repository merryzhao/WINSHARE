<%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>购物保障-文轩网</title>
		<jsp:include page="/page/snippets/v2/meta.jsp"><jsp:param value="help" name="type"/></jsp:include>
	</head>
	<body>
	<div class="wrap">
		<jsp:include page="/page/snippets/v2/header.jsp"></jsp:include>
		<jsp:include page="/page/snippets/v2/navigation.jsp">
	 		<jsp:param value="false" name="index" />
		</jsp:include>
			<div class="layout">
			<jsp:include page="help_menu.jsp">
			  	<jsp:param value="help_1" name="label"/>
			  </jsp:include>
			  
			  <div class="right_box">
    <div id="help_r">
      <h3>购物保障</h3>
      <dl><dt><h5>文轩正品，低价优质，安全又放心</h5>
      <dd class="dot">新华书店正品保障，全场80万种商品4-8折；</dd>
<dd class="dot">支持8种付款方式，支持19种在线网银支付；
<dd class="dot">
  物流覆盖国内外100多个城市地区，配送质检严格把关；</dd>
<dd class="dot">售后7天自由退换货，退款即时到账，支持上门退货取件！</dd></dt>
<dt>
  <h5>文轩网和新华书店是什么关系？担心买到盗版？</h5>
<dd class="dot">文轩网是四川新华书店<a href="http://www.winshare.com.cn">(新华文轩出版传媒股份有限公司)</a>的网上书城</dd>
<dd class="dot">文轩网与新华书店全国连锁店享有同一图书音像来源，低价优质，安全又放心
</dd>
<dd class="dot">用路边书摊的价钱，享受新华书店的图书品质
</dd>
<dd class="dot">物流配送严格质检，国内外100多个城市闪电发货
</dd>
</dt>
<dt><h5>收到的商品不满意，想退货又怕麻烦？ 
</h5>
<dd class="dot">文轩网的所有商品在售后7日内均可自由退换货
</dd>
<dd class="dot">由于商品质量问题产生的退换，文轩网将承担全额运费
</dd>
<dd class="dot">参照文轩网 <a href="http://www.winxuan.com/help/return_flow.html">退换货流程</a> 操作，退款金额将立刻到账，安全又快捷</dd>
<dd class="dot">文轩网还支持退换货上门取件服务
</dd>
</dt>
      </dl>
      </div>
  </div>
			  <div class="hei10"></div>
			</div>
		<jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
		</div>
	</body>
</html>