<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>礼券批次审核列表</title>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="content-result">
					<c:choose>
						<c:when test="${!empty unVerifyPresentBatches}">
							<h4>审核礼券批次</h4>
							<c:if test="${!empty pagination}">
								<winxuan:page pagination="${pagination}" bodyStyle="html"></winxuan:page>
							</c:if>
							<button type="button" name="submit">批量审核</button>
							<table class="list-table">
								<colgroup>
									<col class="select" />
									<col class="id" />
									<col class="title" />
									<col class="number" />
									<col class="number" />
									<col class="number" />
									<col class="state" />
									<col class="type" />
									<col class="date" />
									<col class="date" />
									<col class="number" />
									<col class="operate" />
								</colgroup>
								<tr>
									<th><input type="checkbox" name="selectAll" />
									</th>
									<th>批次编号</th>
									<th>批次标题</th>
									<th>礼券面额</th>
									<th>生成数量</th>
									<th>订单基准金额</th>
									<th>是否通用</th>
									<th>针对商品种类</th>
									<th>礼券有效开始日期</th>
									<th>礼券有效截止日期</th>
									<th>礼券有效期</th>
									<th>操作</th>
								</tr>
								<c:forEach var="presentBatch" items="${unVerifyPresentBatches}"
									varStatus="status">
									<c:set var="consignee" value="${order.consignee}" />
									<c:set var="rowStatus" value="odd" />
									<c:if test="${status.index%2==1}">
										<c:set var="rowStatus" value="trs" />
									</c:if>
									<tr class="${rowStatus}">
										<td><input type="checkbox" name="item"
											value="${presentBatch.id}" />
										</td>
										<td><a href="${contextPath }/presentbatch/${presentBatch.id}/edit">${presentBatch.id}</a></td>
										<td>${presentBatch.batchTitle}</td>
										<td>${presentBatch.value}元</td>
										<td>${presentBatch.num}</td>
										<td>${presentBatch.orderBaseAmount}</td>
										<td><c:if test="${presentBatch.general}">是</c:if> <c:if
												test="${!presentBatch.general}">否</c:if></td>
										<td><c:if test="${presentBatch.productType=='B'}">图书</c:if>
											<c:if test="${presentBatch.productType=='V'}">音像</c:if> <c:if
												test="${presentBatch.productType=='G'}">百货</c:if></td>
										<td><fmt:formatDate value="${presentBatch.presentStartDate}" type="date"/></td>
										<td><fmt:formatDate value="${presentBatch.presentEndDate}" type="date"/></td>
										<td>${presentBatch.presentEffectiveDay}天</td>
										<td><a class="operate-link" href="javascript:void(0);"
											onclick="openType(1,${presentBatch.id});">审核</a></td>
									</tr>
								</c:forEach>
								<tr>
									<th><input type="checkbox" name="selectAll2" />
									</th>
									<th>批次编号</th>
									<th>批次标题</th>
									<th>礼券面额</th>
									<th>生成数量</th>
									<th>订单基准金额</th>
									<th>是否通用</th>
									<th>针对商品种类</th>
									<th>礼券有效开始日期</th>
									<th>礼券有效截止日期</th>
									<th>礼券有效期</th>
									<th>操作</th>
								</tr>
							</table>
							<button type="button" name="submit">批量审核</button>
							<c:if test="${!empty pagination}">
								<winxuan:page pagination="${pagination}" bodyStyle="html"></winxuan:page>
							</c:if>
						</c:when>
						<c:otherwise>
							没有礼券批次需要审核
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
	<div id="auditResult" align="center">
		<h4>礼券批次审核结果:</h4>
		<table>
			<tr>
				<td>数量：</td>
				<td><span id="passVerifyCount"></span>条</td>
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
		请选择审核类型： <select id="verifyTypeBatch">
			<option selected="selected" value="1">审核通过</option>
			<option value="0">审核不通过</option>
		</select>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/present/verify_batch.js"></script>

</body>
</html>