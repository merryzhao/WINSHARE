<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan-page"%>
					<div class="tablepart">
						<c:if test="${pagination!=null}">
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
						</c:if>
						<table class="track-table">
							<colgroup>
								<col class="number" />
								<col class="type" />
								<col class="name" />
								<col class="date-time" />
								<col class="reason" />
							</colgroup>
							<c:if test="${pagination!=null}">
								<tr>
									<th>订单号</th>
									<th>类型</th>
									<th>操作人</th>
									<th>创建时间</th>
									<th>订单跟踪描述</th>
								</tr>
							</c:if>
							<c:forEach var="orderTrack" items="${orderTracks}">
								<tr>
									<td><a href="${contextPath}/order/${orderTrack.order.id}">${orderTrack.order.id}</a></td>
									<td>${orderTrack.type.name}</td>
									<td>${orderTrack.employee.realName}</td>
									<td><fmt:formatDate value="${orderTrack.createTime}" type="both"></fmt:formatDate></td>
									<td>${orderTrack.content}</td>
								</tr>
							</c:forEach>
							<c:if test="${pagination!=null&&pagination.pageCount!=0}">
								<tr>
									<th>订单号</th>
									<th>类型</th>
									<th>操作人</th>
									<th>创建时间</th>
									<th>订单跟踪描述</th>
								</tr>
							</c:if>
						</table>
						<c:if test="${pagination!=null&&pagination.pageCount!=0}">
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
						</c:if>
					</div>