<%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>投诉与建议-文轩网</title>
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
			  	<jsp:param value="help_20" name="label"/>
			  </jsp:include>
			  <div class="right_box">
    <div id="help_r">
      <h3>投诉与建议</h3>
      <dl>
        <dt >如果您对我们的商品或者服务有不满之处，请在此提交<a href="http://www.winxuan.com/customer/complaint" target="_blank">投诉与建议</a>，我们会尽快给予答复或处理！谢谢！<br>
        或者您可以联系我们的客服立即为您解决，点击<a href="http://www.winxuan.com/help/contact.html" target="_blank">联系我们</a>！</dt>
      </dl>
    </div>
  </div>
			  <div class="hei10"></div>
			</div>
		<jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
		</div>
	</body>
</html>