<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>退换货新建</title>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/returnorder/returnorder.css"
	rel="stylesheet" />
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
				<!-- 退换货新建 -->
				<div>
					<!-- 图片 -->
					<div>
						<img class="img_width"
							src="${pageContext.request.contextPath}/imgs/returnorder/confirm.jpg">
					</div>
					<h4>退换货申请新建</h4>

					<div>
						<table class="returnorder_new" style="width:1200px;">
							<!-- 订单详细 -->
							<colgroup>
								<col class="title" />
								<col class="content" />
								<col class="title" />
								<col class="content" />
								<col class="title" />
								<col class="content" />
								<col class="title" />
								<col class="content" />
							</colgroup>
							<tr>
								<td align="right">原订单号：</td>
								<td>${order.id}</td>
								<td align="right">外部交易单号：</td>
								<td>${order.outerId }</td>
								<td align="right">运费：</td>
								<td>${order.deliveryFee } 元</td>
								<td align="right">支付方式：</td>
								<td><c:forEach var="payment" items="${order.paymentList }">
									${payment.payment.name },
									</c:forEach></td>
							</tr>
							<tr>
								<td align="right">配送公司：</td>
								<td>${order.deliveryCompany.company}</td>
								<td align="right">发货时间：</td>
								<td>${order.deliveryTime }</td>
								<td align="right">礼券支付：</td>
								<td><c:set var="present" value="0.00"></c:set> <c:forEach
										var="orderPayment" items="${order.paymentList}">
										<c:if test="${orderPayment.payment.id==20}">
											<c:set var="present"
												value="${present+orderPayment.deliveryMoney}"></c:set>
										</c:if>
									</c:forEach> ${present} 元</td>
								<td align="right">礼品卡支付</td>
								<td><c:set var="presentCard" value="0.00"></c:set> <c:forEach
										var="orderPayment" items="${order.paymentList}">
										<c:if test="${orderPayment.payment.id==21}">
											<c:set var="presentCard"
												value="${presentCard+orderPayment.deliveryMoney}"></c:set>
										</c:if>
									</c:forEach> ${presentCard} 元</td>
							</tr>
							<tr>
								<td align="right">区域：</td>
								<td>${order.consignee.district.name }</td>
								<td align="right">详细地址：</td>
								<td colspan="6">${order.consignee.address }</td>
							</tr>
							<!--数据输入 -->
							<tr>
								<td align="right">退换货类型：</td>
								<td>${type.name}</td>
								<td align="right">责任方：</td>
								<td>${responsible.name}</td>
								<td align="right">承担方：</td>
								<td >${holder.name}</td>
								<td align="right">应退运费合计：</td>
								<td >${rONForm.refunddeliveryfee} 元</td>
							</tr>
							<tr>
								<td align="right">退换货原因：</td>
								<td>${reason.name}</td>
								<td align="right">退换货方式：</td>
								<td>${pickup.name}</td>
								<c:if test="${type.id==24003 || type.id==24004 }">
									<td align="right">付退金额：</td>
									<td>${rONForm.refundcompensating} 元</td>
									<td align="right">付退礼券：</td>
									<td>${rONForm.refundcouponvalue} 元</td>
								</c:if>
								<c:if test="${type.id==24001 || type.id==24002 }">
									<td align="right">退货运单号：</td>
									<td>${rONForm.expressid }</td>
								</c:if>
							</tr>
							<tr>
								<td align="right">是否原包非整退：</td>
								<td>
									<c:choose>
										<c:when test="${rONForm.originalreturned == 570002}">是</c:when>
										<c:otherwise>否</c:otherwise>
									</c:choose>
								</td>
								<td align="right">备注：</td>
								<td colspan="7">${rONForm.remark}</td>
							</tr>
						</table>
					</div>
					<!-- 商品信息  -->
					<c:if test="${!(returnOrder.type.id ==24003 || returnOrder.type.id ==24004)}">
					<div>
						<table class="list-table" style="width:1200px;">
							<tr>
								<th>商品编号</th>
								<th>供应商</th>
								<th>商品名</th>
								<th>码洋</th>
								<th>实洋</th>
								<th>发货数量</th>
								<th>发货金额</th>
								<th>退换货数量</th>
								<th>暂存库位</th>
							</tr>
							<c:forEach var="orderItem" items="${order.itemList }">
								<c:forEach var="itemId" items="${rONForm.item }" varStatus="i">
									<c:if test="${orderItem.id ==itemId}">
										<tr>
											<td>${orderItem.productSale.product.id }</td>
											<td>${orderItem.productSale.product.vendor }</td>
											<td>${orderItem.productSale.product.name }</td>
											<td>${orderItem.listPrice }</td>
											<td>${orderItem.salePrice }</td>
											<td>${orderItem.deliveryQuantity }</td>
											<td>${orderItem.salePrice*orderItem.deliveryQuantity }</td>
											<td>${rONForm.itemcount[i.index]}</td>
											<td>${rONForm.locations[i.index]}</td>
										</tr>
									</c:if>
								</c:forEach>
							</c:forEach>
						</table>
					</div>
					</c:if>
					<div class="next">
						<form action="/returnorder/new" method="post">
						<input type="hidden" name="originalorder" value="${rONForm.originalorder}">
						<input type="hidden" name="type" value="${rONForm.type}">
						<input type="hidden" name="responsible" value="${rONForm.responsible}">
						<input type="hidden" name="holder" value="${rONForm.holder}">
						<input type="hidden" name="reason" value="${rONForm.reason}">
						<input type="hidden" name="pickup" value="${rONForm.pickup}">
						<input type="hidden" name="refundcompensating" value="${rONForm.refundcompensating}">
						<input type="hidden" name="refundcouponvalue" value="${rONForm.refundcouponvalue}">
						<input type="hidden" name="refunddeliveryfee" value="${rONForm.refunddeliveryfee}">
						<input type="hidden" name="remark" value="${rONForm.remark}">
						<input type="hidden" name="originalreturned" value="${rONForm.originalreturned}">
						<input type="hidden" name="packageid" value="${rONForm.packageid }">
						<input type="hidden" name="expressid" value="${rONForm.expressid }">
						<input type="hidden" name="targetDc" value="${rONForm.targetDc }">
						
						<c:forEach var="itemId" items="${rONForm.item}">
							<input type="hidden" name="item" value="${itemId}">
						</c:forEach>
						<c:forEach var="itemCount" items="${rONForm.itemcount}">
							<input type="hidden" name="itemcount" value="${itemCount }">
						</c:forEach>
						<c:forEach var="location" items="${rONForm.locations }">
							<input type="hidden" name="locations" value="${location }" />
						</c:forEach>
						<button type="button" onclick="history.go(-1);">上一步</button><button type="submit">提交</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/returnorder/returnorder.js"></script>
</body>
</html>
