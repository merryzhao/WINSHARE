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
#enumerationForm input.error,textarea.error{
	padding:0px;
	border: 2px solid red;
}
</style>
<%@include file="../snippets/meta.jsp"%>
<title>编辑数据源</title>
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
				<form:form action="${contextPath}/report/enumeration/update"
					method="post" commandName="enumerationForm" id="enumerationForm">
					<table>
						<tr>
							<td>datasource:</td>
							<td>
								<form:select path="dataSourceId" >
									<c:if test="${fn:length(dataSources)!=0}">
										<c:forEach var="ds" items="${dataSources}">
											<option <c:if test="${ds.id==enumerationForm.dataSourceId }">selected="selected"</c:if>  value="${ds.id}">${ds.name }</option>
										</c:forEach>
									</c:if>
								</form:select>
							</td>
						</tr>
						<tr>
						<td>name：</td>
							<td><form:input path="name" />
							</td>
						</tr>
						<tr>
							<td>sql：</td>
							<td colspan="3"><form:textarea path="sql" />
							</td>
						</tr>
						<tr>
							<td><input type="submit" value="提交" /><button type="button" onClick="history.back(-1) ">取消</button></td>
						</tr>
					</table>
					<form:hidden path="id"/>
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
		src="${contextPath}/js/report/enumeration_create_validate.js"></script>
</body>
</html>