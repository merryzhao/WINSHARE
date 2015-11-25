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
				<div>
					<div>
						<table class="list-table">
							<tr>
								<th>账单号</th>
								<th>创建时间</th>
								<th>状态</th>
								<th>清单号</th>
								<th>发票</th>
								<th>结算账户</th>
								<th>渠道查看</th>
								<th>分配金额</th>
								<th>结算金额</th>
								<th>退货金额</th>
								<th>本次余额</th>
								<th>上次余额</th>
								<th>容差</th>
							</tr>
							<tr>
								<td>${bill.id }</td>
								<td>${bill.createtime }</td>
								<td><c:if test="${bill.status.id==90001}">未确认</c:if><c:if test="${bill.status.id==90005}">已确认</c:if><c:if test="${bill.status.id==90002}">审核完成</c:if></td>
								<td>${bill.list }</td>
								<td>${bill.invoice }</td>
								<td>${billAccount.name }</td>
								<td>
									<a href="#" onclick="$('#bill_channels_list').toggle();return false;">查看</a>
									<div id="bill_channels_list" class="ui-dialog-content ui-widget-content" style="display: none; z-index: 1002; outline: 0px none; position: absolute; height: auto;">
										<ul>
											<c:forEach var="channelItem" items="${bill.channels }"><li>${channelItem.name }</li></c:forEach>
										</ul>
									</div>
								</td>
								<td>${bill.allotment }</td>
								<td>${bill.settlement }</td>
								<td>${bill.refound }</td>
								<td>${bill.balance }</td>
								<td>${bill.lastBalance }</td>
								<td>${billAccount.tolerance }</td>
							</tr>
						</table>
					</div>
				</div>
				<c:if test="${bill!=null}">
					<div>
						<h4>到款记录</h4>
						<div>
							<c:choose>
								<c:when test="${bill.billReceiptRecords==null || fn:length(bill.billReceiptRecords)==0 }">
									暂无到账时间登记记录
								</c:when>
								<c:otherwise>
									<table class="list-table" style="width:30%">
										<tr>
											<th>到款时间</th>
											<th>到款金额</th>
											<th>操作</th>
										</tr>
										<c:forEach var="billReceiptRecord" items="${bill.billReceiptRecords }">
											<tr>
												<td><fmt:formatDate value="${billReceiptRecord.updatetime }" pattern="yyyy-MM-dd"/></td>
												<td>${billReceiptRecord.money }</td>
												<td><a href="removeBillReceiptRecord?billReceiptRecordId=${billReceiptRecord.id}">删除</a></td>
											</tr>	
										</c:forEach>
									</table>
								</c:otherwise>
							</c:choose>
							<form action="saveBillReceiptRecord" method="post">
								<input type="hidden" name="billId" value="${bill.id }"/>
								到款时间：<input type="text" name="updatetime" bind="datepicker"/>
								金额：<input type="text" name="money"/>
								<input type="submit" value="登记到款时间"/>
							</form>
						</div>
						<br>
						<div>
							<div>
								<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
								<table class="list-table" style="width: 95%;">
									<tr>
										<th>订单号</th>
										<th>商品ID</th>
										<th>商品名</th>
										<th>实洋</th>
										<th>发货数量</th>
										<th>已结算数量</th>
										<th>已退货数量</th>
										<th>本次结算数量</th>
										<th>本次退货数量</th>
										<th>本次结算金额</th>
										<th>本次退货金额</th>
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
											<td>${billItem.settlementQuantity }</td>
											<td>${billItem.refoundQuantity }</td>
											<td><c:set value="${billItem.settlementQuantity * billItem.orderItem.salePrice }" var="settle_amount"></c:set>
												${settle_amount }
											</td>
											<td>
												<c:set value="${billItem.refoundQuantity * billItem.orderItem.salePrice }" var="refound_amount"></c:set>
												${refound_amount }
											</td>
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
											<th>本次结算数量</th>
											<th>本次退货数量</th>
											<th>本次结算金额</th>
											<th>本次退货金额</th>
										</tr>
									</c:if>
								</table>
								<c:if test="${pagination.count>0}">
									<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
								</c:if>
							</div>
							<p align="center">
								<c:if test="${bill.status.id==90005 }">
									<button type="button" onclick="confirmCheckBill('${bill.id}');return false;">确认审核</button>
								</c:if>
								<c:if test="${bill.status.id==90001 }">
									<button type="button" onclick="lockBill('${bill.id}');return false;">确认分配</button>
								</c:if>
							</p>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/ordersettle/billchannel.js"></script>
</body>
</html>
