<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>报表数据源列表</title>
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

				<h4>数据源列表</h4>
				<hr>
				<div>
					<a class="operate-link" href="${contextPath}/report/datasource/new">新建</a>
				</div>
				<div id="content-result">
					<table class="list-table">
						<colgroup>
							<col class="id" />
							<col class="name" />
							<col class="name" />
							<col class="address" />
							<col class="operate" />
						</colgroup>
						<tr>
							<th>编号</th>
							<th>数据库名</th>
							<th>驱动名</th>
							<th>URL</th>
							<th>操作</th>
						</tr>
						<c:if test="${!(empty dataSources)}">
							<c:forEach items="${dataSources}" var="dataSource"
								varStatus="status">
									<c:if test="${status.index%2==0}">
										<tr>
									</c:if>
									<c:if test="${status.index%2==1}">
										<tr class="trs">
									</c:if>
									<td>${dataSource.id}</td>
									<td>${dataSource.name}</td>
									<td>${dataSource.className }</td>
									<td>${dataSource.url}</td>
									<td><a class="operate-link"
										href="${contextPath}/report/datasource/edit?dataSourceId=${dataSource.id}">编辑</a>&nbsp;
										<a class="operate-link"
										href="${contextPath}/report/datasource/delete?dataSourceId=${dataSource.id}" onclick="return confirm('确认删除？');">删除</a>
									</td>
									</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>