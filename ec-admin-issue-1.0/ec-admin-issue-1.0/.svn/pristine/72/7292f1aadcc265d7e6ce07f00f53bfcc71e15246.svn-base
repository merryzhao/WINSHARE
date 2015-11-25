<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<div id="content-result">
          		<div class="showinfo">
		<h4>物流跟踪信息</h4>
			<table class="list-table">
			     <tr>
					<th>时间</th>
					<th>状态</th>
				</tr>
				<c:forEach var="orderLogistics" items="${order.logisticsList}">
					<tr>
						<td>${orderLogistics.time}</td>
						<td>${orderLogistics.context}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		</div>
