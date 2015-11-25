<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-绑定文轩网账号</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>

<body>
  <%@include file="../../snippets/version2/header.jsp" %>
<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的帐户</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="3_8" />
</jsp:include>
  <div class="right_box">
    <h3 class="myfav_tit margin10">绑定文轩网账号</h3>
    <c:if test="${login==null}">
	 <form:form action="/customer/bindingAccount" method="post" commandName="updatePasswordForm">
	        <input name="_method" type="hidden" value="put"/>
	    <table class="change_password" width="100%" border="0" cellspacing="0">
	      <tr>
	        <td class="txt_right" style="width: 255px;">邮箱：</td>
	        <td>
	        	 <form:input path="username" value="${updatePasswordForm.username}"/>
	             <form:errors path="username" cssStyle="color: red;" />
	        </td>
	      </tr>
	      <tr>
	        <td class="txt_right" style="width: 255px;">密码：</td>
	        <td>
	            <form:password path="newPassword" value="${updatePasswordForm.newPassword}"/>
	            <form:errors path="newPassword" cssStyle="color: red;" />
			</td>
	      </tr>
	      <tr>
	        <td class="txt_right" style="width: 255px;">重复密码：</td>
	        <td>
	            <form:password path="newPasswordConfirm" value="${updatePasswordForm.newPasswordConfirm}"/>
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
    </c:if>
    <c:if test="${login!=null}">
    	使用文轩网用户登陆：
    	<a href = "https://passport.winxuan.com/signin">
    		<input type="button" name="name" value="${login}">
    	</a>
    </c:if>
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/version2/footer.jsp" %>
<script type="text/javascript" src="${serverPrefix}js/password/password.js"></script>	
<script type="text/javascript" src="${serverPrefix}js/checkpassword.js"></script>	
</body>
</html>