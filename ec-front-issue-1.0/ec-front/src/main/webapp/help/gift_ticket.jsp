<%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>礼券(优惠券)-文轩网</title>
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
			  	<jsp:param value="help_26" name="label"/>
			  </jsp:include>
		  <div class="right_box">
    <div id="help_r">
      <h3>礼券(优惠券)</h3>
      <dl>
        <dt>网站会不定期开展礼券促销活动，在活动前期，网站会在网站公告栏发出通知，同时会制定相应的礼券获取规则，请您留意网站最新活动。
        	<h5>礼券使用规则
</h5>
            <dd>1、每个订单仅限使用一张礼券，与其它优惠券合并使用无效。 
</dd>
            <dd>2、所赠礼券不可兑换现金，不设找零。 
</dd>
            <dd>3、请在礼券有效期内使用，过期无效。 
</dd>
            <dd>4、礼券的订单要办理退货，我们将会先扣除礼券金额，再将实际支付金额部分返还。 
</dd>
            <dd>5、礼券不能抵运费，包装费用。 
</dd>
            <dd>6、如果取消订单或订单出现缺货现象时，此订单中使用的礼券将不再恢复。 
</dd>
            <dd>7、礼券在网站部分促销活动中会被设定限制使用，具体规定会在促销活动里提示
</dd>

        </dt>
      </dl>
      <dl>
      	<dt>
        	<h5>礼券的使用方法
</h5>激活礼券：

            <dd>1、登录后，点击“我的帐户”进入帐户操作。
</dd>
            <dd>2、进入我的礼券。
</dd>
            <dd>3、如果栏目中没有礼券，请点击“激活一张礼券”按钮，请输入礼券编码激活一张礼券。
</dd>
            <dd>4、礼券成功激活后，在订单结算页面的支付方式里面选择已经激活的一张礼券，然后再选择一项其他支付方式，支付剩余款项，生成订单。
</dd>
        </dt>
      </dl>

      <dl>
      	<dt>
        	<h5>使用礼券：
</h5>
            <dd>1、请在支付方式时勾选“选择礼券支付”。
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