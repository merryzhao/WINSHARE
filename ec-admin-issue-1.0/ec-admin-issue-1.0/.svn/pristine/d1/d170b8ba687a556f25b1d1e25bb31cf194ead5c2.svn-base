<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>修改礼品卡密码</title>
<style type="text/css">
#presentCardModifyForm input.error {
	padding: 0px;
	border: 1px solid red;
}

#presentCardModifyForm .error {
	padding: 0px;
	border: 0px solid red;
}
.fontcl {
	font-size: 15px;
	color: #6699cc;
	font-weight: bold;
	font-family: Microsoft YaHei;
	margin-right:20px;
}
.errorstyle {
	font-size: 12px;
	color: #ff0000;
	font-weight: bold;
	font-family: Microsoft YaHei;
	margin-right:20px;
}
</style>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="content-result">
					<h4>修改礼品卡密码</h4><hr>
					<form:form action="${contextPath }/presentcard/modifyPassword"
						commandName="presentCardModifyForm" method="post"
						id="presentCardModifyForm">
	<label class="fontcl">礼品卡号 : </label><form:input path="id" /><label id="error" class="errorstyle"></label>
						<div id="canceldiv" style="display: none">
			      <table class="list-table">
			         <tr>
			            <td>礼品卡号：<label id="presentCardId"></label></td>
			            <td>礼品卡状态:<label id="status"></label></td>
			            <td>礼品卡类型:<label id="type"></label></td>
			         </tr>
			         <tr>
			            <td>创建日期：<label id="createdate"></label></td>
			            <td>有效期：<label id="enddate"></label></td>
			            <td>余额:<label id="balance"></label></td>
			         </tr>
			          <tr>
			            <td>面额:<label id="denomination"></label></td>
			            <td>是否有效：<label id="expired"></label></td>
			         </tr>
			       </table> 
			      <table>
							
							<tr>
								<td>密码：</td>
								<td><form:password path="password" /></td>
								<td><span id="passwordMessage" class="error"></span></td>
								<form:errors path="password" />
							</tr>
							<tr>
								<td>确认密码：</td>
								<td><input type="password" id="repeatedPassword" /> <span
									id="repeatMessage" class="error"></span></td>

							</tr>
							<tr>
								<td><input type="button" value="修改" id="sbm"  />
								</td>
								<td><input type="reset" value="重置" /></td>
							</tr>
						</table>
			   </div>
						
						
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" value="${result }" id="result"/>
	<div id="successMessage">修改成功！</div>
	<div id="failMessage">修改失败！</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/presentcard/modify_password.js"></script>
</body>
</html>