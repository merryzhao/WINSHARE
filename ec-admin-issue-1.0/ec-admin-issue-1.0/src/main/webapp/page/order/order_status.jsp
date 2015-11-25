<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<div id="content-result">
              <div  class="showinfo">
              		<h4>订单状态信息</h4>
				<table class="list-table" >
					<tr>
						<th>订单状态</th>
						<th>操作人</th>
						<th>处理时间</th>
					</tr>
					<c:set var="statusLogs" value="${order.statusLogList}"></c:set>
					<c:forEach items="${statusLogs}" var="statusLog">
						<tr>
						    <td>${statusLog.status.name}</td>
							<td>${statusLog.operator.name}</td>
							<td><fmt:formatDate value="${statusLog.operateTime}" type="both" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div  class="showinfo">
				<c:if test="${order.erpStatus != null}">
              		<h4>ERP状态信息	: ${order.erpStatus.name}</h4>
              </c:if>
			</div>
		</div>
