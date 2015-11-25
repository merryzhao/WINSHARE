<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-修改密码-成功</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>

<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>
<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的帐户</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="3_2" />
</jsp:include>
  <div class="right_box">
    <h3 class="myfav_tit margin10">我的资料</h3>
    <p class="border_style changed_suc margin10"><span class="greenhook">&nbsp;</span><b>密码修改成功</b><br><br><a class="link2" href="/customer/updatePassword">返回</a></p>
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/version2/footer.jsp" %>
</body>
</html>
