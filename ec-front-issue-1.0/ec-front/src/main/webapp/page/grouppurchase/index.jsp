	<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网_团购首页</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="bookshop" name="type"/>
</jsp:include>
</head>
<link href="${serverPrefix }css2/images/tuangou/tuangou.css?20130527" rel="stylesheet" type="text/css">
<body class="grouppurchase">
<jsp:include page="/page/snippets/version2/header.jsp">
		<jsp:param value="book" name="label" />
</jsp:include>
<div class="layout">
  <h1 class="tuangou_tit">文轩网团体购书，正品保障 专人服务 价格更优惠</h1>
  <div class="g_borer">
  <div class="service_info">
    <h2 class="t_service">特色服务</h2>
    <ul class="ser_list">
     <li class="first_s"><h3>海量正品 品牌保证</h3><b class="fb orange">60</b>万正品图书  <b class="fb orange">20</b>万预订品种<br>新华书店品牌保障</li>
     <li class="second_s"><h3>专业客服 全程服务</h3>专业客服全程服务</li>
     <li class="third_s"><h3>540家出版社 直接采购</h3><b class="fb orange">540</b>家出版社直接采购，超低缺货率</li>
     <li class="forth_s"><h3>图书定制 个性服务</h3>提供定制书目服务</li>
    </ul>
    <h2 class="s_service">六大特权</h2>
    <ol class="privilege">
     <li><b>特权一:</b>支付方式更加灵活；</li>
     <li><b>特权二:</b>代下订单运费全免；</li>
     <li><b>特权三:</b>大批采购价格超优；</li>
    </ol>
   <ol class="privilege">
     <li><b>特权四:</b>缺货商品单独采购；</li>
     <li><b>特权五:</b>订购商品优先配送；</li>
     <li><b>特权六:</b>到货跟踪全程掌握；</li>
   </ol>
   <p class="hei10"></p>
   <p class="contact_info">
   </p>
   <div class="margin10 wirte_info">
            <h4 class="comment_tit2">如果您是为企业批量购买商品，请填写如下信息，我们会第一时间与您联系！</h4>
            <div class="consulting">
            <%-- 
            <form action="/groupinfo/save" method="post" name="groupInfoForm"> </form>
            --%>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="14%" align="right">您的姓名：</td>
                        <td width="86%"><input name="name" type="text" id="textfield" value="游客"> <%--还无企业账号？<a class="link2" href="javascript:;">点击注册企业账号</a> --%></td>
                    </tr>
                    <tr>
                        <td align="right">联系电话：</td>
                        <td><input class="unity_m" type="text" name="phone" > 如：010-66886600  13898886333</td>
                    </tr>
                    <tr>
                        <td align="right">公司名称：</td>
                        <td><input class="unity_m" type="text" name="companyName" > 如：新华文轩网上书城</td>
                    </tr>
                    <tr>
                        <td align="right" style="vertical-align:text-top;">咨询内容：</td>
                        <td><textarea class="unity_w" name="content" id="textarea" cols="45" rows="5">比如：《乔布斯传》沃尔特著 中信出版社的那本书是否有货，我们要100本，可否打6折，8天内送到？我们在江苏盐城</textarea></td>
                    </tr>
                    <tr>
                        <td align="right">验证码：</td>
                        <td><input name="sRand" type="text" size="10" >
                            <img data-lazy="false" src="/verifyCode" id="safeCode"/> <a class="link2" href="javascript:changeRandCode()">换一张</a></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td align="right"><input class="save_l submit" name=""  type="button" value="提 交"></td>
                    </tr>
                </table>
               
            </div>
        </div>
  </div>
  </div>
</div>
<script type="text/javascript">
function changeRandCode(){
	var img =document.getElementById("safeCode"); 
	img.src=img.src+"?d="+Math.random; 
}

</script>
<script src="${serverPrefix}js/groupinfo/groupinfo.js?${version}"></script>
<%@include file="/page/snippets/version2/footer.jsp" %>
</body>
</html>
