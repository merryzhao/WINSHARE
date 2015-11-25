<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>订单审核列表</title>
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
				<div id="content-result">
					<c:choose>
						<c:when test="${!empty unAuditOrders}">
							<h4>未审核通过订单列表</h4>
							<c:if test="${!empty pagination}">
								<winxuan:page pagination="${pagination}" bodyStyle="html"></winxuan:page>
							</c:if>
							<button type="button" name="submit">批量审核</button>
							<table class="list-table">
								<colgroup>
									<col class="select" />
									<col class="id" />
									<col class="type" />
									<col class="type" />
									<col class="area" />
									<col class="address" />
									<col class="name" />
									<col class="name" />
									<col class="state" />
									<col class="name" />
									<col class="number" />
									<col class="reason" />
									<col class="reason" />
									<col class="operate" />
								</colgroup>
								<tr>
									<th><input type="checkbox" name="selectAll" /></th>
									<th>订单号</th>
									<th>支付方式</th>
									<th>配送方式</th>
									<th>区域</th>
									<th>地址</th>
									<th>电话</th>
									<th>收货人</th>
									<th>码洋</th>
									<th>渠道</th>
									<th>商品数量</th>
									<th>备注</th>
									<th>审核未通过原因</th>
									<th>操作</th>
								</tr>
								<c:forEach var="order" items="${unAuditOrders}"
									varStatus="status">
									<c:set var="consignee" value="${order.consignee}" />
									<c:set var="rowStatus" value="odd" />
									<c:if test="${status.index%2==1}">
										<c:set var="rowStatus" value="trs" />
									</c:if>
									<tr class="${rowStatus}">
										<td><input type="checkbox" name="item"
											value="${order.id}" /></td>
										<td><a href="${contextPath}/order/${order.id}" target="_blank">${order.id}</a>
										</td>
										<td>${order.payType.name}</td>
										<td>${order.deliveryType.name}</td>
										<td>${consignee.city.name}&nbsp;${consignee.district.name}</td>
										<td>${consignee.address}</td>

										<td><c:if test="${empty consignee.mobile}">
									${consignee.phone}
								</c:if> <c:if test="${!empty consignee.mobile}">
									${consignee.mobile}
								</c:if></td>
										<td>${consignee.consignee}</td>
										<td>${order.listPrice}</td>
										<td>${order.channel.name}</td>
										<td>${order.purchaseQuantity}</td>
										<td>${consignee.remark}</td>
										<td>${consignee.unAuditReason.name}</td>
										<td><a  class="operate-link"
											href="javascript:void(0);" onclick="confirmSingle(${order.id});">审核</a>
										</td>
									</tr>
								</c:forEach>
								<tr>
									<th><input type="checkbox" name="selectAll2" /></th>
									<th>订单号</th>
									<th>支付方式</th>
									<th>配送方式</th>
									<th>区域</th>
									<th>地址</th>
									<th>电话</th>
									<th>收货人</th>
									<th>码洋</th>
									<th>渠道</th>
									<th>商品数量</th>
									<th>备注</th>
									<th>审核未通过原因</th>
									<th>操作</th>
								</tr>
							</table>
							<button type="button" name="submit">批量审核</button>
							<c:if test="${!empty pagination}">
								<winxuan:page pagination="${pagination}" bodyStyle="html"></winxuan:page>
							</c:if>
						</c:when>
						<c:otherwise>
				没有订单需要审核
			</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="isAudit" value="${isAudit}" />
	<input type="hidden" id="isAlert" value="${isAlert}" />	
	<div id="auditResult" align="center">
		<h4>订单审核结果:</h4>
		<table>
			<tr>
				<td>共计审核：</td>
				<td><span id="preCount"></span>条</td>
			</tr>
			<tr>
				<td>审核通过：</td>
				<td><span id="passCount"></span>条</td>
			</tr>
			<tr>
				<td>审核未通过：</td>
				<td><span id="currentCount"></span>条</td>
			</tr>
			<tr><td id="reasonStr"></td></tr>
			<tr><td colspan="2" id="finalReason"></td></tr>
		</table>
	</div>
	<div id="confirmDiv" align="center">
		<span >是否确定审核？</span>
	</div>
	<div id="confirmDiv2" align="center">
		<span >是否确定审核？</span>
	</div>
	<input type="hidden" id="ids" />
	<input type="hidden" id="len" />
	<input type="hidden" id="thisId" />
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/order/orderAuditBatch.js"></script>
</body>
</html>