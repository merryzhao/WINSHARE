<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户暂存款余额</title>
</head>
<body>
 余额：<c:out value="${balance}"></c:out>元<br/>
 <table>
 <tr>
 	<th>订单号</th><th>交易类型</th><th>金额</th><th>交易时间</th>
 </tr>
<c:forEach var="accountDetail" items="${accountDetails }">
	<tr>	
		<td>
			<c:out value="${accountDetail.order.id }"></c:out>
		</td>
		<td>
			<c:out value="${accountDetail.type.name}"></c:out>
		</td>
		<td>
			<c:out value="${accountDetail.amount}"></c:out>
		</td>
		<td>
			<c:out value="${accountDetail.useTime }"></c:out>
		</td>
	</tr>
</c:forEach>
</table>
</body>
</html>