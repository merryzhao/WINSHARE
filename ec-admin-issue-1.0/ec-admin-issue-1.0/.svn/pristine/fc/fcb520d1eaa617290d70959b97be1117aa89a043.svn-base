<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>抽单对账系统</title>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/returnorder/returnorder.css"
	rel="stylesheet" />
</head>
<body onload="">
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<!-- 请求发送中... -->
				<div id="requestInfo" align="center" style="font-size: 15px; font-weight: bold;"></div>
				<!-- 抽单对账系统 -->
				<div>
					<h4>渠道容差</h4>
					<div>
						<form action="/ordersettle/updateTolerance" method="get" id="billchannel_form">
							<div>
								<select name="channelId" id="settleorder_channelid" onchange="window.location.href='/ordersettle/channelView?channelId='+this.value">
									<option value="">请选择渠道</option>
									<c:forEach var="channelItem"
										items="${channelList }">
										<option <c:if test="${channelItem.id==channelId }">selected="selected"</c:if>value="${channelItem.id}">${channelItem.name}</option>
									</c:forEach>
								</select>
								<p>
									容差输入：<input id="tolerance_amount" type="text" name="amount" value="${billChannel.tolerance}"/>
									<button type="button" onclick="confirmTolerance();">确定</button>
								</p>
							</div>
						</form>
					</div>
					<br>
					<!-- <div>
						<c:if test="${pagination!=null&&bill.status.id!=90004}">
							<p>分配金额：${bill.allotment }   结算金额：${bill.settlement }  退货金额：${bill.refound }
								本次余额：${bill.balance }  上次余额：${bill.lastBalance }</p>  
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
							<div>
								<table class="list-table" style="width: 1200px;">
									<tr>
										<th>订单号</th>
										<th>商品ID</th>
										<th>商品名</th>
										<th>实洋</th>
										<th>发货数量</th>
										<th>已结算数量</th>
										<th>已退货数量</th>
										<th>本次退货数量</th>
										<th>本次结算数量</th>
										<th>本次退货金额</th>
										<th>本次结算金额</th>
										<th>操作</th>
									</tr>
									<c:forEach var="billItem" items="${billItemList}">
										<tr>
											<td>
												<a href="/order/${billItem.orderItem.order.id}" target="_blank">${billItem.orderItem.order.id}</a>
											</td>
											<td>
												${billItem.orderItem.productSale.id }
												<%--
												<a href="#" onclick="$('#product_info_${billItem.id }').show();return false;">${billItem.orderItem.productSale.id }</a>
												<div id="product_info_${billItem.id }" class="ui-dialog-content ui-widget-content" style="display: none; z-index: 1002; outline: 0px none; position: absolute; height: auto;">
													<table style="width:300px">
														<tr>
															<th>ID</th><th>退货数量</th>
														</tr>
														<c:forEach var="billReturnOrderItem" items="${billItem.billReturnOrderItems }">
															<tr>
																<td>${billReturnOrderItem.id }</td>
																<td>${billReturnOrderItem.refoundQuantity }</td>
															</tr>
														</c:forEach>
													</table>
													<p align="center"><button type="button" onclick="$('#product_info_${billItem.id }').hide();return false;">关闭</button></p>
												</div>
												 --%>
											</td>
											<td>
												<span title="${billItem.orderItem.productSale.sellName }">${fn:substring(billItem.orderItem.productSale.sellName,"0","10")}</span>
											</td>
											<td>${billItem.orderItem.salePrice }</td>
											<td>${billItem.billItemStatistics.deliveryQuantity }</td>
											<td>${billItem.billItemStatistics.settlementQuantity }</td>
											<td>${billItem.billItemStatistics.refoundQuantity }</td>
											<td>${billItem.refoundQuantity }</td>
											<td>${billItem.settlementQuantity }</td>
											<td>
												<c:set value="${billItem.refoundQuantity * billItem.orderItem.salePrice }" var="refound_amount"></c:set>
												${refound_amount }
											</td>
											<td><c:set value="${billItem.settlementQuantity * billItem.orderItem.salePrice }" var="settle_amount"></c:set>
												${settle_amount }</td>
											<td>修改<c:if test="${refound_item==0 }">|删除</c:if></td>
										</tr>
									</c:forEach>
									<c:if test="${pagination.count>0}">
										<tr>
											<th>订单号</th>
											<th>商品ID</th>
											<th>商品名</th>
											<th>实洋</th>
											<th>发货数量</th>
											<th>已结算数量</th>
											<th>已退货数量</th>
											<th>本次退货数量</th>
											<th>本次结算数量</th>
											<th>本次退货金额</th>
											<th>本次结算金额</th>
											<th>操作</th>
										</tr>
									</c:if>
								</table>
							</div>
							<c:if test="${pagination.count>0}">
								<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
							</c:if>
						</c:if>
					</div> -->
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/ordersettle/billchannel.js"></script>
</body>
</html>
