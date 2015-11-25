<%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>邮局汇款-文轩网</title>
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
			  	<jsp:param value="help_13" name="label"/>
			  </jsp:include>
			  <div class="right_box">
    <div id="help_r">
      <h3>邮局汇款</h3>
      <dl>
<br>
      	<table width="100%" border="0" cellpadding="4" cellspacing="1" class="table">
          <tr>
            <td width="168" align="left" class="table_td">在收款人姓名处填写：</td>
            <td width="590" class="table2_td2">四川文轩在线电子商务有限公司</td>
          </tr>
          <tr>
            <td align="left" class="table_td">在客户签名处填写：</td>
            <td class="table2_td2">您的姓名</td>
          </tr>
          <tr>
            <td align="left" class="table_td">在汇款金额处填写：</td>
            <td class="table2_td2">您的汇款金额(小写)</td>
          </tr>
          <tr>
            <td align="left" class="table_td">在入帐汇款：汇入帐户填写：</td>
            <td class="table2_td2">1004 3426 8700 0100 01 </td>
          </tr>
          <tr>
            <td align="left" class="table_td">在汇款人姓名处填写：</td>
            <td class="table2_td2">您的姓名</td>
          </tr>
          <tr>
            <td align="left" class="table_td">在附言栏处：</td>
            <td class="table2_td2">“√”；同时一定要填写：您的订单号</td>
          </tr>
        </table>
		<h4>邮局汇款单填写说明</h4>
        <dt><img src="images/1218-huikuan.jpg"></dt>
      	<h4>邮政汇款注意事项：</h4>
        <dt>1、请您尽量不要采用“密码汇款”方式，因为该汇款方式的取款速度较慢，会耽误订单的处理进程。</dt>
<dt>2、请您务必在汇款单的附言处注明订单号和联系电话。汇款后请将汇款凭证(注明订单号)传真至028-83157480，以便我们核实您的款项。若没有接到您的汇款凭证，您的订单将无法处理。</dt>
<dt>3、汇款的到款时间一般为自办理汇款手续之日起2-7个工作日，您订购的商品会在汇款到达文轩网帐户后发出。</dt>
<dt>4、自下单之日起15天，文轩网没有收到您的货款，系统将自动取消订单。</dt>
      </dl>
      <dl class="worm_word"><span class="fontbold">温馨提示：</span><br>
如果您所在地区的邮局不支持“入账汇款”，请您选择“按地址汇款”。“按地址汇款”需注意以下必填项目：<br>
收款人姓名：四川文轩在线电子商务有限公司
<br>
收款人地址：四川省成都市金牛区蓉北商贸大道文轩路6号2楼
<br>
邮编：610081
<br>
同时要填写汇款金额、汇款人相关信息、并在附言栏注明订单号。

      </dl>
      
    </div>
  </div>
			  <div class="hei10"></div>
			</div>
		<jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
		</div>
	</body>
</html>