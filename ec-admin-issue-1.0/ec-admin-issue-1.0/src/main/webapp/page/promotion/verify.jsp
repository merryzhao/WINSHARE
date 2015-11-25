<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/scripts.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>审核促销活动</title>
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
				<div id="content-result">
					<c:choose>
						<c:when test="${!empty unVerifyPromotions}">
							<h4>审核促销活动</h4>
							<c:if test="${!empty pagination}">
								<winxuan:page pagination="${pagination}" bodyStyle="html"></winxuan:page>
							</c:if>
							<button type="button" name="submit">批量审核</button>
							<table class="list-table">
								<colgroup>
									<col class="select" />
									<col class="id" />
									<col class="title" />
									<col class="description" />
									<col class="number" />
									<col class="name" />
									<col class="date" />
									<col class="operate" />
								</colgroup>
								<tr>
									<th><input type="checkbox" name="selectAll" /></th>
									<th>活动编号</th>
									<th>活动标题</th>
									<th>活动描述</th>
									<th>活动时间范围</th>
									<th>创建人</th>
									<th>创建时间</th>
									<th>操作</th>
								</tr>
								<c:forEach var="promotion" items="${unVerifyPromotions}"
									varStatus="status">
									<c:set var="rowStatus" value="odd" />
									<c:if test="${status.index%2==1}">
										<c:set var="rowStatus" value="trs" />
									</c:if>
									<tr class="${rowStatus}">
										<td><input type="checkbox" name="item"
											value="${promotion.id}" /></td>
										<td id="idTd${status.index }"><a href="${contextPath}/promotion/${promotion.id }/edit">${promotion.id}</a></td>
										<td id="titleTd${status.index }">${promotion.title}</td>
										<td>${promotion.description}</td>
										<td><fmt:formatDate value="${promotion.startDate}"
												type="date" />~ <fmt:formatDate
												value="${promotion.endDate}" type="date" />
										</td>
										<td>${promotion.createUser.name }</td>
										<td><fmt:formatDate value="${promotion.createTime}"
												type="date" />
										</td>
										<td><a class="operate-link" id="verifyPrefix" href="javascript:void(0);"
											onclick="openSingleDialog(${promotion.id});">审核</a></td>
									</tr>
								</c:forEach>
								<tr>
									<th><input type="checkbox" name="selectAll2" /></th>
									<th>活动编号</th>
									<th>活动标题</th>
									<th>活动描述</th>
									<th>活动时间范围</th>
									<th>创建人</th>
									<th>创建时间</th>
									<th>操作</th>
								</tr>
							</table>
							<button type="button" name="submit">批量审核</button>
							<c:if test="${!empty pagination}">
								<winxuan:page pagination="${pagination}" bodyStyle="html"></winxuan:page>
							</c:if>
						</c:when>
						<c:otherwise>
				没有促销活动需要审核
			</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
	<div id="verifySucess">审核通过成功</div>
	<div id="verifyFail">审核不通过成功</div>
	<div id="verifyError">审核失败</div>
	<div id="verifyResult" align="center">
		<h4>促销活动审核结果:</h4>
		<table>
			<tr>
				<td>总计审核：</td>
				<td><span id="totalVerifyCount"></span>条</td>
			</tr>
			<tr>
				<td>成功：</td>
				<td><span id="passVerifyCount"></span>条</td>
			</tr>
			
			<tr>
				<td>失败：</td>
				<td><span id="failVerifyCount"></span>条</td>
			</tr>

		</table>
	</div>
	<input type="hidden" id="currentId" />
	<div id="verifySelectSingle" align="center">
		请选择审核类型： <select id="verifyTypeSingle">
			<option selected="selected" value="1">审核通过</option>
			<option value="0">审核不通过</option>
		</select>
	</div>
	<div id="verifySelectBatch" align="center">
		<table class="list-table" id="batchTable">
			<colgroup>
				<col class="id" />
				<col class="title" />
			</colgroup>
		</table>
		请选择审核类型： <select id="verifyTypeBatch">
			<option selected="selected" value="1">审核通过</option>
			<option value="0">审核不通过</option>
		</select>
	</div>
	<script type="text/javascript"
		src="${contextPath}/js/promotion/verify.js"></script>

</body>
</html>