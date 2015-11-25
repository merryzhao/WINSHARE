<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
label.error {
	padding:0.1em;
}
#dataSourceForm input.error{
	padding:0px;
	border: 2px solid red;
}
</style>
<%@include file="../snippets/meta.jsp"%>
<title>新建数据源</title>
</head>
<body>
	<!-- 引入JS -->
	<%@include file="../snippets/scripts.jsp"%>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-form.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<form:form action="${contextPath}/report/datasource/update"
					method="post" commandName="dataSourceForm" id="dataSourceForm">
					<table>
						<tr>
							<td>数据源名称：</td>
							<td><form:input path="name" /></td>
							<td>jdbc驱动类名：</td>
							<td><form:input path="className" />
							</td>
						</tr>
						<tr>
							<td>url：</td>
							<td colspan="3"><form:input path="url" /></td>
						</tr>
						<tr>
							<td>用户名：</td>
							<td><form:input path="userName" />
							</td>
							<td>密码：</td>
							<td><form:input type="password" path="password" />
							</td>
						</tr>
						<tr>
							<td><input type="submit" value="提交" />
								<button type="button" onClick="history.back(-1) ">取消</button>
							</td>
						</tr>
					</table>
					<form:hidden path="id" />
				</form:form>
			</div>
		</div>
	</div>
	
			    <script type="text/javascript" 
		src="${contextPath}/js/jquery.validate.min.js"></script>
 		<script type="text/javascript" 
		src="${contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript" 
		src="${contextPath}/js/jquery.validate.methods.min.js"></script>
	<script type="text/javascript" 
		src="${contextPath}/js/report/datasource_create_validate.js"></script>
</body>
</html>