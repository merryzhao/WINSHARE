<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>退货包件信息查询</title>
<%@include file="../snippets/meta.jsp"%>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<h4>退货包件备注列表</h4>
				<div>
					<table class="list-table" style="">
						<tr>
							<th>备注信息</th>
							<th>备注时间</th>
							<th>备注人</th>
						</tr>
						<tbody>
							<c:if test="${!empty remarkList }">
								<c:forEach var="attr" items="${remarkList }">
									<tr>
										<td>${attr.remark }</td>
										<td>${attr.remarktime }</td>
										<td>${attr.user.name }</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
					<div><button onclick="window.history.go(-1)">返回</button></div>
				</div>
			</div>
		</div>
	</div>
<%@include file="../snippets/scripts.jsp"%>
</body>
</html>
