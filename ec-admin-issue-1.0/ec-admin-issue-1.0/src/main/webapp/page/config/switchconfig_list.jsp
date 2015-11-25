<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>应用状态管理</title>
<%@include file="../snippets/meta.jsp"%>
<style>
	table#tablesorter th {
		color: #333;
	}
</style>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-system.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>锁定信息列表</h4>
				<!-- 查询表单div -->
					<c:if test="${pagination!=null}">
						${pagination}
					</c:if>
				<table id="tablesorter" class="tablesorter list-table">
					<tr>
						<th>编号</th>
						<th>类型</th>
						<th>创建时间</th>
						<th>修改时间</th>
						<th>是否有效</th>
						<th>操作</th>
 					</tr>
					<c:forEach items="${switchConfigs}" var="switch">
						<tr>
							<td>${switch.id}</td>						
							<td>${switch.type}</td>
							<td>
								<fmt:formatDate value="${switch.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td>
								<fmt:formatDate value="${switch.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td>
								<c:if test="${switch.isOpen}">是</c:if>
								<c:if test="${!switch.isOpen}">否</c:if>
							</td>
							<td>                	
								<c:if test="${!switch.isOpen}">
								<form action="/switchconfig/update/${switch.id }" method="post" >
									<input type="hidden" name="valueOpen" value="true">
									<input class="active" type="submit" value="启用"/>
								</form>
								</c:if>
								<c:if test="${switch.isOpen}">
								<form action="/switchconfig/update/${switch.id }" method="post" >
								<input type="hidden" name="valueOpen" value="false">
									<input class="block" type="submit" value="禁用"/>
								</form>
								</c:if>
							</td>
 						</tr>
					</c:forEach>
				</table>
				<c:if test="${pagination!=null}"> 
						${pagination}
					</c:if>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>