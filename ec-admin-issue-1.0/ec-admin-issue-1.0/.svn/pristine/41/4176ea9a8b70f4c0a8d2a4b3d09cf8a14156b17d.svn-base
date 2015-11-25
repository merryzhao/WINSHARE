<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
				<h4>发放礼品卡订单</h4>
				<!-- 内容部分 -->
				<div>
				    <!-- 条件查询部分 -->
				    <div>
				    <form:form action="${contextPath }/presentcardorder/tosendofdata" method="post" id="cardQueryForm" commandName="cardQueryForm">
				    <label>订单编号：</label><input type="text" name="orderId" value="${orderId }">
				    <button type="button" id ="queryPresentCard">提交</button><button type="button" id="exPresentCard">导出礼品卡订单</button>
				    </form:form>
				    </div>
				    <hr>
				    <!-- 结果部分 -->
				    <c:if test="${order!=null }">
				    <div>
					<!-- 礼品卡订单详情 -->
					<div>
						<h4>礼品卡订单详情</h4>
						<table class="cardorder_detail" style="width:1150px;">
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
					<!-- 订单商品信息 -->
					<div>
						<h4>订单商品信息</h4>
						<table class="list-table"  style="width:1150px;">
							<tr>
								<th>商品编号</th>
								<th>卖家</th>
								<th>卖家商品编号</th>
								<th>商品名</th>
								<th>码洋</th>
								<th>实样</th>
								<th>购买数量</th>
								<th>小计</th>
								<th>可用量</th>
								<th>库存信息</th>
							</tr>
							<c:forEach var="orderItem" items="${order.itemList}"
								varStatus="index">

								<tr>
									<td>${orderItem.productSale.product.id}</td>
									<td>${orderItem.productSale.shop.name}</td>
									<td>${orderItem.productSale.id}</td>
									<td>${orderItem.productSale.product.name}</td>
									<td>${orderItem.listPrice}</td>
									<td>${orderItem.salePrice}</td>
									<td>${orderItem.purchaseQuantity}</td>
									<td>${orderItem.purchaseQuantity*orderItem.salePrice}</td>
									<td><c:if
											test="${orderItem.productSale.stockQuantity > orderItem.productSale.saleQuantity}">
					    ${orderItem.productSale.stockQuantity - orderItem.productSale.saleQuantity}
					</c:if> <c:if
											test="${orderItem.productSale.stockQuantity <= orderItem.productSale.saleQuantity}">
					   0
					</c:if></td>
									<td><c:forEach var="productWarehouse"
											items="${orderItem.productSale.stockList}">
				           ${productWarehouse.location}${productWarehouse.stock}
				     </c:forEach></td>
								</tr>
							</c:forEach>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>

								<td>${order.listPrice}</td>
								<td>${order.salePrice}</td>
								<td>${order.purchaseQuantity}</td>
								<td>${order.deliveryListPrice}</td>
								<td></td>
							</tr>
						</table>
					</div>
					<!-- 操作 -->
					<br> <br>
					<div>
						<button type="button" onclick="ajaxSubmit('${order.id}','grant');">发卡</button>
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
