<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<div id="content-result">
              <div  class="showinfo" >
              		<h4>修改日志</h4>
				<table class="list-table" >
					<tr>
						<th>字段</th>
						<th>原始值</th>
						<th>修改值</th>
						<th>修改时间</th>
						<th>修改人</th>
					</tr>
					<c:set var="updateLogs" value="${order.updateLogList}"></c:set>
					<c:forEach items="${updateLogs}" var="updateLog">
						<tr>
							<td>${updateLog.fieldName}</td>
							<td>${updateLog.originalValue}</td>
							<td>${updateLog.changedValue}</td>
							<td><fmt:formatDate value="${updateLog.updateTime}" type="both" /></td>
							<td>${updateLog.operator.name}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
