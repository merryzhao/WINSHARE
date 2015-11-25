<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-用户_礼品卡修改密码</title>
<link href="${serverPrefix }css2/images/giftcard.css" rel="stylesheet" type="text/css">
	<jsp:include page="../snippets/version2/meta.jsp" flush="true" >
		<jsp:param name="type" value="acc_order" />
	</jsp:include>
</head>

<body>

<%@include file="../snippets/version2/header.jsp" %>
<div class="layout">
  <div class="your_path cl">你现在的位置： <span>文轩网 > 我的帐户</span></div>
  <div class="left_box">
        
        <c:import url="left_box.jsp">
         <c:param name="jspname" value="1"></c:param>
        </c:import>
   
    </div>
  <div class="right_box">
    <h3 class="myfav_tit margin10">礼品卡修改密码</h3>
     <form id="activate" action="/presentcard/update" method="post" name="presentCardForm">
     <input type="hidden" name="card" value="${presentCard.id}"/>
    <table class="change_password" width="100%" border="0" cellspacing="0">
    <tr>
        <td class="txt_right">礼品卡卡号：</td>
        
        <td>${presentCard.id}</td>
      </tr>
      <tr>
        <td class="txt_right">旧密码：</td>
        <td>
        <input name="password" type="password">
        </td>
      </tr>
      <tr>
        <td class="txt_right">新密码：</td>
        <td>
        <input name="newPassWord" class="newPassWord" type="password">
        </td>
      </tr>
      <tr>
        <td class="txt_right">重复新密码：</td>
        <td>
        <input name="" class="newPassWord2" type="password">
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td><input class="pas_save"  type="button" value="保存">
            <input class="pas_cancel" type="button" value="取消"></td>
      </tr>
    </table>
    </form>
  </div>
  <div class="hei10"></div>
</div>
<script type="text/javascript" src="${serverPrefix}js/presentcard/cardupdatepwd.js?${version}"></script>
<%@include file="../snippets/version2/footer.jsp" %>
</body>
</html>
