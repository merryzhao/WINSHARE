<%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>退款及提现-文轩网</title>
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
			  	<jsp:param value="help_18" name="label"/>
			  </jsp:include>
			  <div class="right_box">
    <div id="help_r">
      <h3>退款及提现</h3>
      <dl><h4>退款条件：</h4>
        <dt>由于“多支付货款”、“缺货取消”、“客户取消”等原因产生的退款,我们将采用以下方式进行处理,其中退回到暂存款的资金,由客户自主进行提现或再次消费使用。
        <table class="returnInfoTable" border="0" cellspacing="0" cellpadding="0" width="99%">
        <thead>
          <tr> 
           <td colspan="3">支付方式</td>
           <td>退款方式</td>
           <td>文轩退款处理周期</td>
           <td>银行退款处理周期</td>
           </tr> 
        </thead>
        <tbody>
           <tr>
           <td rowspan="3">网上支付</td>
           <td style="height:153px;">储蓄卡</td>
           <td style="height:153px;width:300px;">交通银行、中国光大银行、广东发展银行、<br><br>中国建设银行、中国工商银行、深圳发展银行、<br><br>中国民生银行、中信银行、杭州银行、兴业银行、<br><br>浦发银行、中国银行、中国农业银行、招商银行</td>
           <td style="height:153px;">原储蓄卡</td>
           <td style="height:153px;">3-5个工作日</td>
           <td style="height:153px;">1-7个工作日</td>
           </tr>
            <tr>
           <td>支付平台</td>
           <td>支付宝、财付通、银联电子支付、手机支付、支付宝钱包</td>
            <td>原支付账号</td>
             <td>3-5个工作日</td>
              <td>1-12个工作日</td>
           </tr>
            <tr>
           <td>信用卡</td>
           <td>VISA、JCB、MasterCard</td>
            <td>原信用卡</td>
             <td>3-5个工作日</td>
              <td>1-12个工作日</td>
           </tr>
           <tr>
           <td>货到付款/自提支付</td>
           <td></td>
           <td></td>
           <td>暂存款</td>
           <td>3-5个工作日</td> 
           <td></td>
           </tr>
           <tr>
           <td>银行转账</td>
           <td></td>
           <td></td>
           <td>暂存款</td>
           <td>3-5个工作日</td> 
           <td></td>
           </tr>
           <tr>
           <td>暂存款</td>
           <td></td>
           <td></td>
           <td>暂存款</td>
           <td>3-5个工作日</td> 
           <td></td>
           </tr>
        </tbody>
        </table>
        	注：顾客由于“商品退货”产生的退款，无论您是哪种支付方式，我们都会将款项退还至您的暂存款账户，财务退款处理周期为3-5个工作日。
        </dt>
      </dl>
      <dl><h4>申请暂存款提现(仅针对于可以退回到暂存款的资金)：</h4>
        <dt>
您可以登陆您的“我的账户－我的暂存款”，点击“提现”，即可申请退款。
温馨提示：如要求我们通过你选择的方式办理退款，请在申请退款时，准确填写相应的信息。申请退款后，我们会在系统受理后5个工作日内为您办理退款。<br>
注：2011年12月20日前在文轩网注册，且在2011年12月20日以后未进行登录的会员只能查看暂存款信息，而不能进行提现操作。
</dt>
      </dl>
      <dl><h4>暂存款退款查询：</h4>
        <dt>您可以登陆您的“<a href="http://www.winxuan.com/customer">我的账户</a>－<a href="http://www.winxuan.com/customer/advanceaccount">我的暂存款</a>”，点击“<a href="http://www.winxuan.com/customer/advanceaccount">暂存款明细列表</a>”“<a href="http://www.winxuan.com/customer/advanceaccount/refundlist">提现申请列表</a>”，即可显示您的暂存款使用及暂存款提现的详细纪录。<br>图例：<br>
        <div class="moneybg"><p>1.暂存款账户余额是由于“多支付货款”、“缺货取消”、“客户取消”“等原因产生的退款金额，请详见"<a href="http://www.winxuan.com/help/payment_others.html">其他支付方式</a>"中的相关说明 </p></div> </dt>
      </dl>
    </div>
  </div>
			  <div class="hei10"></div>
			</div>
		<jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
		</div>
	</body>
</html>