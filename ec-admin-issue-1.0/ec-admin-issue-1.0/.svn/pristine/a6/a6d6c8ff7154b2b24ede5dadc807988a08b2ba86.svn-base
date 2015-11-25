<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/meta.jsp"%>
				<h4>退换货跟踪</h4>
				<br>
				<button type="button" onclick="showTrack();">新建退换货跟踪</button>
				<table class="list-table">
				<tr>
				<th>跟踪编号</th>
				<th>退换货类型</th>
				<th>创建时间</th>
				<th>操作人</th>
				<th>内容</th>
				</tr>
				<c:forEach var="returnOrderTrack" items="${returnOrder.trackList}">
				<tr>
				<td>${returnOrderTrack.id }</td>
				<td>${returnOrderTrack.type.name }</td>
				<td>
				<fmt:formatDate value="${returnOrderTrack.createTime }" type="both" />
				</td>
				<td>${returnOrderTrack.operator.name }</td>
				<td>${returnOrderTrack.content }</td>
				</tr>
				</c:forEach>
				</table>
