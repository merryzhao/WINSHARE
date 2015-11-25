<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>用户创建</title>
	<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
	<%@include file="../../snippets/meta.jsp"%>
	<link type="text/css"
	href="${pageContext.request.contextPath}/css/authority/authority.css"
	rel="stylesheet" />
</head>
<body>
	<div class="frame" >
		<!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-system.jsp"%>
		<div class="frame-main">
			<div class="frame-main-inner" id="content">
			<div>
		 		 <form:form action="/user/create" method="post" commandName="createUserForm">
			        <input name="_method" type="hidden" value="post"/>
				    <table class="view" width="100%" border="0" cellspacing="0">
				      <tr>
				        <td class="txt_right" style="width: 255px;">账号：</td>
				        <td>
				            <form:input path="name" class="input_txt" value="${createUserForm.name}"/>
				            <form:errors path="name" cssStyle="color: red;" />
				        </td>
				      </tr>
				      <tr>
				        <td class="txt_right" style="width: 255px;">密码：</td>
				        <td>
				            <form:input path="password" class="input_txt" value="${createUserForm.password}"/>
				            <form:errors path="password" cssStyle="color: red;" />
						</td>
				      </tr>
				      <tr>
				        <td class="txt_right" style="width: 255px;">邮箱：</td>
				        <td >
				            <form:input path="email" class="input_txt" value="${createUserForm.email}"/>
				            <form:errors path="email" cssStyle="color: red;" />
						</td>
				      </tr>
				      <tr>
				        <td>&nbsp;</td>
				        <td>
				        <button class="save" name="save_but" id="save_but" type="submit">保		存</button>
				        <button class="cancel" name="cancel" type="reset">重		置</button>
				        </td>
				      </tr>
				    </table>
			    </form:form>
			</div>
 		</div>
 		</div>
 	</div>
 	<%@include file="../../snippets/scripts.jsp"%>
</body>
</html>