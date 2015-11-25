<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<%@page import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品评论 - 会员中心 - 文轩网</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
	
</jsp:include>

<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的评论</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="4_1" />
</jsp:include>


  <div class="right_box">
    <h3 class="myfav_tit margin10">我的评论社区</h3>
    <div class="border_style margin10 text_align_center">
    <span class="greenhook">&nbsp;发表评论失败:${message}</span><b>
  </div>
 </div>
</div>
<%@include file="../../snippets/footer.jsp"%>
</body>
</html>
