<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>促销活动查询</title>
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
				<div id="requestInfo" align="center"
					style="font-size: 15px; font-weight: bold;"></div>
				<!-- 促销活动查询 -->
				<div>
					<!-- 查询条件部分 -->
					<div>
						<form action="/promotion/list" method="post">
							<!-- 条件一部分 -->
							<div>
								<label>活动编号：</label> <input type="text" name="promotionId">
								<label>活动标题关键字：</label><input type="text" name="promotionTitle">
								<button class="submit" type="submit">查询</button>
							</div>
							<!-- 条件二部分 -->
							<div>
								<label>有效时间：</label> <input type="text"
									name="promotionStartdate" bind="datepicker"> <label>至</label>
								<input type="text" name="promotionEnddate" bind="datepicker">
								<label>活动类型：</label> <select name="promotionType">
									<option value="-1">全部</option>
									<c:forEach var="pType" items="${promotionType.children}">
										<option value="${pType.id}">${pType.name}</option>
									</c:forEach>
								</select> <label>活动状态：</label> <select name="promotionStatus">
									<option value="-1">全部</option>
									<c:forEach var="pStatus" items="${promotionStatus.children}">
										<option value="${pStatus.id }">${pStatus.name}</option>
									</c:forEach>
								</select> 
							</div>
						</form>
					</div>
					<br>
					<hr>
					<br>
					<!-- 查询结果部分 -->
					<div>
						<c:if test="${pagination!=null}">
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
						</c:if>
						<table class="list-table">
							<c:if test="${pagination!=null}">
								<tr>
									<th>活动编号</th>
									<th>活动标题</th>
									<th>活动状态</th>
									<th>活动类型</th>
									<th>活动有效时间</th>
									<th>创建人</th>
									<th>创建时间</th>
									<th>最后更新者</th>
									<th>最后更新时间</th>
									<th>操作</th>
								</tr>
							</c:if>
							<c:forEach var="promotion" items="${promotions}"
								varStatus="status">
								<tr>
									<td><a href="/promotion/${promotion.id}/edit">${promotion.id}</a>
									</td>
									<td>${promotion.title }</td>
									<td id="statusTd${status.index }">${promotion.status.name
										}</td>
									<td>${promotion.type.name }</td>
									<td><fmt:formatDate value="${promotion.startDate}"
											type="date" />~ <fmt:formatDate value="${promotion.endDate}"
											type="date" />
									</td>
									<td>${promotion.createUser.name }</td>
									<td><fmt:formatDate value="${promotion.createTime}"
											type="date" /></td>
									<td id="assessorTd${status.index }">${promotion.assessor.name}</td>
									<td id="assessTimeTd${status.index }"><fmt:formatDate value="${promotion.assessTime}"
											type="date" /></td>
									<td id="operateTd${status.index}"><c:if
											test="${promotion.status.id==29001 }">
											<a href="javascript:void(0);"
												onclick="excuteVerifySingle(${promotion.id},${status.index});">审核</a>
										</c:if> <c:if
											test="${promotion.status.id==29002 ||promotion.status.id==29003 }">
											<a href="javascript:void(0);"
												onclick="promotionStop(${promotion.id},${status.index });">停用</a>
										</c:if></td>
								</tr>
							</c:forEach>
							<c:if test="${pagination!=null&&pagination.pageCount!=0}">
								<tr>
									<th>活动编号</th>
									<th>活动标题</th>
									<th>活动状态</th>
									<th>活动类型</th>
									<th>活动有效时间</th>
									<th>创建人</th>
									<th>创建时间</th>
									<th>最后更新者</th>
									<th>最后更新时间</th>
									<th>操作</th>
								</tr>
							</c:if>
						</table>
						<c:if test="${pagination!=null&&pagination.pageCount!=0}">
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="rowId" />
	<input type="hidden" id="currentId" />
	<div id="verifySelectSingle2" align="center">
		请选择审核类型： <select id="verifyTypeSingle2">
			<option selected="selected" value="1">审核通过</option>
			<option value="0">审核不通过</option>
		</select>
	</div>
	<div id="verifySucess2">审核成功</div>
	<div id="verifyFail2">审核失败</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/promotion/verify.js"></script>
</body>
</html>
