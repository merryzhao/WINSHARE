<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<%@page import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的暂存款 - 文轩网</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>

<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的暂存款</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="3_7" />
</jsp:include>
 <div class="right_box">
    <h3 class="myfav_tit margin10">我的暂存款：退款进度查询</h3>
    <div class="hei10"></div>
    <table width="100%" class="favorite_goods record" cellspacing="0" cellpadding="0">
      <thead>
        <tr>
          <th width="12%">申请日期</th>
          <th width="18%">退款渠道</th>
          <th width="10%">退款金额</th>
          <th width="9%">收款人</th>
          <th width="11%">退款状态</th>
          <th width="12%">处理日期</th>
          <th width="28%">备注</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="nodash"><b class="black">2011-09-10</b></td>
          <td class="nodash"><b class="black">邮局汇款(<a class="link2" href="#">详细</a>)</b></td>
          <td class="nodash"><b class="black">￥30.24</b></td>
          <td class="nodash"><b class="black">张三丰</b></td>
          <td class="nodash"><b class="fb orange">待处理</b></td>
          <td class="nodash"><b class="black">----</b></td>
          <td class="nodash"><p class="txt_left gray">您可以<a class="link2" href="#">修改</a>，<a class="link2" href="#">撤销</a>改申请</p></td>
        </tr>
        <tr>
          <td><b class="black">2011-09-10</b></td>
          <td><b class="black">支付宝(<a class="link2" href="#">详细</a>)</b></td>
          <td><b class="black">￥36.00</b></td>
          <td><b class="black">张三丰</b></td>
          <td><b class="fb green">已退款</b></td>
          <td><b class="black">2011-8-26</b></td>
          <td><p class="txt_left gray">不能撤销</p></td>
        </tr>
        <tr>
          <td><b class="black">2011-09-10</b></td>
          <td><b class="black">邮局汇款(<a class="link2" href="#">详细</a>)</b></td>
          <td><b class="black">￥89.04</b></td>
          <td><b class="black">张三丰</b></td>
          <td><b class="fb orange">待处理</b></td>
          <td><b class="black">2011-5-21</b></td>
          <td><p class="txt_left gray">退款成功</p></td>
        </tr>
      </tbody>
    </table>
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/footer.jsp"%>
</body>
</html>
