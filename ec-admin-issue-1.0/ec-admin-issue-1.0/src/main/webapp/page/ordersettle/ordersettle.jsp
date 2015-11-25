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
				<!-- 列出订单商品项 -->
				<div id="orderItemInfo" align="center" style="font-size: 15px; font-weight: bold;">
					<div id="orderItemList"></div>
				</div>
				<!-- 抽单对账系统 -->
				<div>
					<h4>抽单对账系统</h4>
					<div>
						<form action="/ordersettle/toCreateBill" method="get" id="ordersettle_form">
							<div>
								<select name="billAccountId" id="settleorder_billaccountid" onchange="showChannelList(this.value)">
									<option value="">请选择渠道</option>
									<c:forEach var="billAccountItem"
										items="${billAccounts }">
										<option <c:if test="${billAccountItem.id==billAccount.id }">selected="selected"</c:if>value="${billAccountItem.id}">${billAccountItem.name}</option>
									</c:forEach>
								</select>
								<c:forEach var="billAccountItem"
									items="${billAccounts }">
									<span class="channel_list_display" id="channel_list_${billAccountItem.id }" style="display:none">
										<c:forEach var="channelItem"
											items="${billAccountItem.channels }">
											<input type="checkbox" name="channelIds" value="${channelItem.id }"/>${channelItem.name}
										</c:forEach>
									</span>
								</c:forEach>
								<input type="text" name="amount" id="settleorder_amount" <c:if test="${amount==null}"> value="请输入金额"</c:if><c:if test="${amount!=null&&amount>0}"> value="${amount }"</c:if> onfocus="if(this.value=='请输入金额'){this.value=''}" onblur="if(this.value==''){this.value='请输入金额'}"/>
								<input type="text" name="list" id="settleorder_list" <c:if test="${list==null||list==''}"> value="请输入清单"</c:if><c:if test="${list!=null&&list!=''}"> value="${list }"</c:if> onfocus="if(this.value=='请输入清单'){this.value=''}" onblur="if(this.value==''){this.value='请输入清单'}"/>
								<input type="text" name="invoice" id="settleorder_invoice" <c:if test="${invoice==null||invoice==''}"> value="请输入发票"</c:if><c:if test="${invoice!=null&&invoice!=''}"> value="${invoice }"</c:if> onfocus="if(this.value=='请输入发票'){this.value=''}" onblur="if(this.value==''){this.value='请输入发票'}"/>
								<button id="settle_button" type="submit" onclick="startSettle();return false;">分配</button>
							</div>
						</form>
						<c:if test="${bill!=null }">
							<div>
								账单号：${bill.id }
								清单号： ${bill.list }
								发票：${bill.invoice }
								<c:if test="${bill.status.id==90004}">（账单分配中，请稍后刷新...）<button type="button" onclick="window.location.reload();">刷新</button></c:if>
							</div>
						</c:if>
					</div>
					<br>
					<div>
						<c:if test="${pagination!=null&&bill.status.id!=90004}">
							<p>分配金额：${bill.allotment }   结算金额：${bill.settlement }  退货金额：${bill.refound }  本次余额：${bill.balance }  上次余额：${bill.lastBalance }  容差：${billAccount.tolerance }</p>
							<div>
								<input type="text" id="order_id" name="orderId" value="请输入订单号" onfocus="if(this.value=='请输入订单号'){this.value=''}" onblur="if(this.value==''){this.value='请输入订单号'}"/>
								<button type="button" onclick="showOrderItems('${bill.id}');return false;">添加结算项</button>
							</div>  
							<div>
								<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
								<table class="list-table" style="width: 100%;">
									<tr>
										<th>订单号</th>
										<th>订单状态</th>
										<th>商品ID</th>
										<th>商品名</th>
										<th>码洋</th>
										<th>实洋</th>
										<th>发货数量</th>
										<th>已结算数量</th>
										<th>已退货数量</th>
										<th>本次结算数量</th>
										<th>本次退货数量</th>
										<th>本次结算金额</th>
										<th>本次退货金额</th>
										<th>操作</th>
									</tr>
									<c:forEach var="billItem" items="${billItemList}">
										<tr>
											<td>
												<a href="/order/${billItem.orderItem.order.id}" target="_blank">${billItem.orderItem.order.id}</a>
											</td>
											<td>
												${billItem.orderItem.order.processStatus.name }
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
											<td><c:set value="${billItem.orderItem.salePrice * billItem.billItemStatistics.deliveryQuantity }" var="mayang"></c:set>
												${mayang }</td>
											<td>${billItem.orderItem.salePrice }</td>
											<td>${billItem.billItemStatistics.deliveryQuantity }</td>
											<td>${billItem.historySettlement }</td>
											<td>${billItem.historyRefound }</td>
											<td>${billItem.settlementQuantity }</td>
											<td>${billItem.refoundQuantity }</td>
											<td><c:set value="${billItem.settlementQuantity * billItem.orderItem.salePrice }" var="settle_amount"></c:set>
												${settle_amount }
											</td>
											<td>
												<c:set value="${billItem.refoundQuantity * billItem.orderItem.salePrice }" var="refound_amount"></c:set>
												${refound_amount }
											</td>
											<td><a href="/ordersettle/delelteBillItem?orderItemId=${billItem.orderItem.id }&billId=${billItem.bill.id}">删除</a></td>
										</tr>
									</c:forEach>
									<c:if test="${pagination.count>0}">
										<tr>
											<th>订单号</th>
											<th>订单状态</th>
											<th>商品ID</th>
											<th>商品名</th>
											<th>码洋</th>
											<th>实洋</th>
											<th>发货数量</th>
											<th>已结算数量</th>
											<th>已退货数量</th>
											<th>本次结算数量</th>
											<th>本次退货数量</th>
											<th>本次结算金额</th>
											<th>本次退货金额</th>
											<th>操作</th>
										</tr>
									</c:if>
								</table>
								<c:if test="${pagination.count>0}">
									<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
								</c:if>
							</div>
							<p align="center">
								<c:choose>
								<c:when test="${bill.status.id==90005}">
									<span>等待审核</span>
								</c:when>
								<c:otherwise>
									<button type="button" onclick="confirmSettle('${bill.balance }','${bill.id}');return false;">确认分配</button>
								</c:otherwise>	
								</c:choose>
							</p>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/ordersettle/ordersettle.js"></script>
</body>
</html>
