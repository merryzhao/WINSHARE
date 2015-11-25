<%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>银行转账/汇款-文轩网</title>
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
			  	<jsp:param value="help_14" name="label"/>
			  </jsp:include>
			  <div class="right_box">
    <div id="help_r">
      <h3>银行转账/汇款</h3>
      <dl>
      	<h4>一、	网银转帐</h4>
        <dt>1．	支持建设银行网银转账；</dt>
        <dt>2．	到款时间一般为办理转帐手续之后的1-2个工作日内。</dt>
      </dl>
        <dl>
		<h4>二、汇款转帐</h4>
        <dt>1．国内顾客可以在全国任何一家银行，通过填写银行电汇单向我公司帐户进行汇款。</dt>
        <dt>2．到款时间一般为办理转帐手续之后的1-3个工作日内。</dt>
      </dl>
        <dl><dt><span class="fontbold">公司账户的信息如下：</span>  <br>
  户名：<span class="tel">四川文轩在线电子商务有限公司 
  </span><br>
  开户行：<span class="tel">建设银行成都新华支行</span> <br>
  帐号：<span class="tel">51001870836051517375 </span></dt>
  <dt><span class="fontbold">汇款转帐填写说明：</span> </dt>
  <dt><img src="images/20130506-huikuandan.jpg"></dt>
      </dl>
<dl class="worm_word">      <span class="fontbold">温馨提示：</span><br>
请您务必在备注栏注明您的订单号和汇款人联系电话。若没有您的订单信息和汇款人联系电话，您的订单将无法处理。</dl>
    </div>
  </div>
			  <div class="hei10"></div>
			</div>
		<jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
		</div>
	</body>
</html>