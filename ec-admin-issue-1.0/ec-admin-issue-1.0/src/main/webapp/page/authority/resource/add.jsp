<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>资源新建</title>
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
		 		 <form:form action="/resource/create" method="post" commandName="createResourceForm">
			        <input name="_method" type="hidden" value="post"/>
				    <table class="view" width="100%" border="0" cellspacing="0">
				      <tr>
				        <td class="txt_right" style="width: 255px;">资源编码：</td>
				        <td>
				            <form:textarea path="code" class="textarea_txt" value="${createResourceForm.code}"/>
				            <form:errors path="code" cssStyle="color: red;" />
				        </td>
				      </tr>
				      <tr>
				        <td class="txt_right" style="width: 255px;">资源值：</td>
				        <td>
				            <form:textarea path="value" class="textarea_txt" value="${createResourceForm.value}"/>
				            <form:errors path="value" cssStyle="color: red;" />
						</td>
				      </tr>
				      <tr>
				        <td class="txt_right" style="width: 255px;">资源描述：</td>
				        <td >
				            <form:textarea path="description" class="textarea_txt" value="${createResourceForm.description}"/>
				            <form:errors path="description" cssStyle="color: red;" />
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