<%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>验货与签收-文轩网</title>
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
			  	<jsp:param value="help_10" name="label"/>
			  </jsp:include>
			  <div class="right_box">
    <div id="help_r">
      <h3>验货与签收</h3>
      <dl>
        <h4>1、验货：</h4>
        <dt>如您验收商品时发现商品包装破损、商品短缺或错误、请您一定要当面向送货员说明情况并将相关的赠品和优惠商品一起当场拒收；对于有塑封包装或开箱即损贴纸的商品，如您已打开塑封包装或撕开开箱即损贴纸则不可拒收，但商品缺少或发错商品的除外。
        <dd>(1)	送货上门订单：<span class="fontbold">请客户当场向送货人员指出后拒收。</span></dd>
        <dd>(2)	邮政特快专递、平邮订单：未打开文轩网外包装箱，可以当场全部拒收；如开箱验货后发现上述问题，请与文轩网客服中心联系；平邮订单，如果发现包裹破损，请您要求邮局出具包裹破损证明。</dd>
        </dt>
      </dl>
      <dl>
        <h4>2、签收：</h4>
        <dt>
        <dd> (1)<span class="fontbold"> 请您仔细核对：</span>商品的名称，数量，价格，等信息无误，商品包装完好、商品没有划痕、破损等表面质量问题，请在发货单正面客户签收处签字确认。您或您委托的收货人的签字表示您已确认上述内容无误，文轩网有权不接受以此为由的退换货(商品质量问题除外)。</dd>
        <dd>(2)如您在下订单时选择了“<span class="fontbold">索取发票</span>”， 请仔细检查发票是否随商品一起送到， 如果没有随商品送到，请联系文轩网客服，我们会在3天内通过平邮为您补寄。</dd>
        <dd>&nbsp;</dd>
        </dd>
        </dt>
        </dd>
        </dt>
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