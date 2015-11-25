<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/scripts.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>监控任务.</title>

<link type="text/css" href="../../css/redmond/jquery-ui-1.8.14.custom.css" rel="stylesheet" />
<link type="text/css" href="../../css/global.css" rel="stylesheet" />
<script type="text/javascript" src="../../js/monitor/new.js"></script>
</head>
<body>
<div class="frame">
<!-- 引入top部分 -->
<%@include file="../snippets/frame-top.jsp"%>
<!-- 引入left菜单列表部分 -->
<%@include file="../snippets/frame-left-product.jsp"%>
<div class="frame-main">
	<!-- 核心内容部分div -->
	<div class="frame-main-inner" id="content">
		<div>
			<table class="list-table">
					<tbody>
						<tr>
							<th>任务号</th>
							<th>任务名</th>
							<th>描述</th>
							<th>创建人</th>
							<th>创建时间</th>
							<th>监控日期</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
						<c:forEach var="t" items="${list }">
							<tr>
								<td>${t.id }</td>
								<td>${t.name }</td>
								<td>${t.description }</td>
								<td>
									${t.creator.name }
								</td>
								<td>
									<fmt:formatDate value="${t.createtime}" pattern="yyyy-MM-dd HH:mm" />
								</td>
								<td>
									<fmt:formatDate value="${t.start}" pattern="yyyy-MM-dd HH:mm" />
									至
									<fmt:formatDate value="${t.end }" pattern="yyyy-MM-dd HH:mm" />
								</td>
								<td>
									<c:if test="${t.status == 1}">启用</c:if>
									<c:if test="${t.status == 0 }">禁用</c:if>
									
								</td>
								<td>
									<input type="button" value="<c:if test="${t.status == 0 }">启用</c:if><c:if test="${t.status == 1 }">禁用</c:if>" 
										onclick="monitor.updateStatus(${t.id},<c:if test="${t.status == 1}">0</c:if><c:if test="${t.status == 0 }">1</c:if>);"
									/>
									
								</td>
							</tr>
						</c:forEach>
						<tr>
							<th>任务号</th>
							<th>任务名</th>
							<th>描述</th>
							<th>创建人</th>
							<th>创建时间</th>
							<th>监控日期</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</tbody>
				</table>
		</div>
		<c:if test="${not empty pagination}">	
				<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
		</c:if>
	</div>
</div>
</div>
</body>
</html>