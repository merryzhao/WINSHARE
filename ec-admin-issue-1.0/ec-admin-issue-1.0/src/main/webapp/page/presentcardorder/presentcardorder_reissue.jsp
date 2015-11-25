<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>礼品卡订单</title>
<link type="text/css" href="${pageContext.request.contextPath}/css/presentcardorder/presentcardorder.css" rel="stylesheet"/>
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
				<div id="requestInfo" align="center"
					class="dialog"></div>
				<h4> 补发礼品卡订单</h4>
				<!-- 内容部分 -->
				<div>
				    <!-- 条件查询部分 -->
				    <div>
				    <form action="${contextPath }/presentcardorder/toresendofdata" method="post">
				    <label>订单编号：</label><input type="text" name="orderId" value="${orderId }">
				    <button type="submit">提交</button>
				    </form>
				    </div>
				    <hr>
				    <!-- 结果部分 -->
				    <c:if test="${order!=null }">
				    <div>
					<!-- 礼品卡订单详情 -->
					<div>
						<h4>礼品卡订单详情</h4>
						<table class="cardorder_detail">
							<colgroup>
								<col class="title" />
								<col class="content" />
								<col class="title" />
								<col class="content" />
							</colgroup>
							<tr>
								<td align="right">订单号： </td>
								<td>${order.id}</td>
								<td align="right">下单时间：</td>
								<td><fmt:formatDate type="both" value="${order.createTime}"/></td>
							</tr>
							<tr>
								<td align="right">下单处理人：</td>
								<td>
								<c:forEach var="orderStatusLog" items="${order.statusLogList}">
								<c:if test="${orderStatusLog.status.id==8001}">
								${orderStatusLog.operator.realName }
								</c:if>
								</c:forEach>
                                </td>
								<td align="right">到款处理人：</td>
								<td>
								<c:if test="${order.paymentStatus.id==4001}">
								<c:forEach var="orderPayment" items="${order.paymentList}">
								<c:if test="${orderPayment.pay==true}">
								<label>${orderPayment.credential.operator.realName }</label>
								</c:if>
								</c:forEach>
								</c:if>
								</td>
							</tr>
							<tr>
								<td align="right">订单状态：</td>
								<td>${order.processStatus.name}</td>
								<td align="right">收货人姓名：</td>
								<td>${order.consignee.consignee}</td>
							</tr>
							<tr>
								<td align="right">收货人电话：</td>
								<td>${order.consignee.phone}</td>
								<td align="right">收货人email：</td>
								<td>${order.consignee.email}</td>
							</tr>
							<tr>
								<td align="right">收货人邮编：</td>
								<td>${order.consignee.zipCode}</td>
								<td align="right">收货人地址：</td>
								<td>${order.consignee.address}</td>
							</tr>
							<tr>
								<td align="right">发票抬头：</td>
								<td>${order.consignee.invoiceTitle}</td>
								<td align="right">订单金额：</td>
								<td>${order.salePrice}</td>
							</tr>
						</table>

					</div>
					<!-- 礼品卡 -->
					<c:if test="${presentCards!=null }">
					<div>
						<h4>礼品卡信息</h4>
						<table class="list-table">
							<tr>
								<th>卡号</th>
								<th>类型</th>
								<th>状态</th>
								<th>有效期</th>
								<th>面值</th>
								<th>余额</th>
							</tr>
							<c:forEach var="presentCard" items="${presentCards}">
								<tr>
									<td><a href="/presentcard/cardinfo/${presentCard.id}">${presentCard.id}</a>
									</td>
									<td>${presentCard.type.name}</td>
									<td>${presentCard.status.name}</td>
									<td><fmt:formatDate value="${presentCard.endDate}"
											type="date" />
									</td>
									<td>${presentCard.denomination}元</td>
									<td>${presentCard.balance}元</td>
								</tr>
							</c:forEach>
								<tr>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								</tr>
						</table>
					</div>
					</c:if>
					<!-- 操作 -->
					<br> <br>
					<div>
						<button type="button" onclick="ajaxSubmit('${order.id}','reissue');">补发</button>
					</div>
				</div>
				</c:if>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/presentcardorder/presentcardorder.js"></script>
</body>
</html>
