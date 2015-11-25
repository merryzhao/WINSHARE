<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-修改密码</title>
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
    <h3 class="myfav_tit margin10">修改登陆密码</h3>
 <form:form action="/customer/updatePassword" method="post" commandName="updatePasswordForm">
        <input name="_method" type="hidden" value="put"/>
    <table class="change_password" width="100%" border="0" cellspacing="0">
      <tr>
        <td class="txt_right" style="width: 255px;">旧密码：</td>
        <td>
            <form:password path="oldPassword" value="${updatePassword.oldPassword}"/>
            <form:errors path="oldPassword" cssStyle="color: red;" />
        </td>
      </tr>
      <tr>
        <td class="txt_right" style="width: 255px;">新密码：</td>
        <td>
            <form:password path="newPassword" value="${updatePassword.newPassword}"/>
            <form:errors path="newPassword" cssStyle="color: red;" />
		</td>
      </tr>
      <tr>
        <td class="txt_right" style="width: 255px;">重复新密码：</td>
        <td>
            <form:password path="newPasswordConfirm" value="${updatePassword.newPasswordConfirm}"/>
            <form:errors path="newPasswordConfirm" cssStyle="color: red;" />
		</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>
        <input class="pas_save" name="save_but" id="save_but" type="submit" value="保存">
        <input class="pas_cancel" name="" type="reset" value="取消">
        </td>
      </tr>
    </table>
    </form:form>
    <h3 class="myfav_tit margin10">设置支付密码</h3>
    </br>
    <a href="javascript:;" class="link1" bind="set-checkpass">设置暂存款支付密码</a>
    
    
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/version2/footer.jsp" %>
<script type="text/javascript" src="${serverPrefix}js/password/password.js?${version}"></script>	
<script type="text/javascript" src="${serverPrefix}js/checkpassword.js"></script>	
</body>
</html>