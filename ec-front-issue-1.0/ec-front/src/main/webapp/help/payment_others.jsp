<%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>其他支付方式-文轩网</title>
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
			  	<jsp:param value="help_15" name="label"/>
			  </jsp:include>
			  <div class="right_box">
    <div id="help_r">
      <h3>其他支付方式</h3>
      <dl><h4>暂存款支付：</h4>
        <dt>
        	<span class="fontbold">暂存款支付名词解释：</span>暂存款支付是文轩网为了便于顾客支付而新增的一种新型支付方式，仅限顾客进行在线支付时使用；<br>
        	<span class="fontbold">暂存款账户余额来源：</span>由于“多支付货款”、“缺货取消”、“客户取消”“等原因产生的退款金额。 针对下表中所述的部分支付方式，我们会以暂存款的方式直接退回至您的暂存款账户，以方便您下次使用；<br>
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
           <td>支付宝、财付通、银联电子支付、手机支付</td>
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
        	注：顾客由于“商品退货”产生的退款，无论您是哪种支付方式，我们都会将款项退还至您的暂存款账户，财务退款处理周期为3-5个工作日。<br>
        	<span class="fontbold">暂存款使用：</span>在“付款方式”中勾选“使用暂存款支付”。
        </dt>
      </dl>
      <dl><h4>礼品卡支付：</h4>
        <dt>
在订单结算页面的“<span class="tel">支付方式</span>”中勾选“<span class="tel">使用礼品卡</span>”，输入礼品卡卡号、密码和验证码后，点击 “确定”按钮，确定使用礼品卡支付。<br>
        </dt>
      </dl>
      <dl><h4>礼券支付：</h4>
        <dt>
激活礼券：<br>
1、登录后，点击“<span class="tel">我的帐户</span>”进入帐户操作。<br>

2、进入我的礼券。<br>

3、如果栏目中没有礼券，请点击“<span class="tel">激活一张礼券</span>”按钮，请输入礼券编码激活一张礼券。<br>

4、礼券成功激活后，在订单结算页面的支付方式里面选择已经激活的一张礼券，然后再选择一项其他支付方式，支付剩余款项，生成订单。 <br>

<span class="fontbold">使用礼券：</span>　　<br>
　　(1)、请在支付方式时勾选“选择礼券支付”。<br> 

　　(2)、如果已经有激活的礼券，直接选择一张礼券使用，然后再选择其他支付方式，支付剩余款项。</dt>
      </dl>
    </div>
  </div>
			  <div class="hei10"></div>
			</div>
		<jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
		</div>
	</body>
</html>