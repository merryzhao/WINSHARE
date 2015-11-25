<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<%@page import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-退换货服务</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>
<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  文轩网-退换货服务</span></div>
  <jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="1_2" />
</jsp:include>
  <div class="right_box">
    <h3 class="myfav_tit margin10">退换货服务：退货申请</h3>
    <ul class="step_box">
    <li class="step_statu"><span>1、输入订单号</span></li>
    <li class="step_statu"><span>2、选择需退货商品</span></li>
    <li><span>3、填写退货原因</span></li>
    <li><span>4、确认退货信息</span></li>
    <li class="last_step"><span>5、成功提交</span></li>
    </ul>
    <form:form action="/customer/returnorder/test">
   	 <input type="checkbox" name="a" value="0"></input>
	  <input type="text" name="b" value="3"></input>
	  <input type="checkbox" name="a" value="1"></input>
	 <input type="text" name="b" value="2"></input>
    <p class="txt_center margin10"><input class="red_but" name="" type="submit" value="下一步"></p>
    </form:form>
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/footer.jsp"%>
</body>
</html>
