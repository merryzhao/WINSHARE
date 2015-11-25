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
<style type="text/css">
.selected{background-color: #efefef;}

input[type=button]{padding:5px;margin:10px 10px 0px 10px;}
#showOrderItems tr, #showCollect tr{border:1px solid #DFDFDF;}
#showOrderItems td, #showOrderItems th, #showCollect td, #showCollect td{padding: 0px 8px; line-height: 24px;}
#showProdcutItem{padding: 10px 0px;}
#showProdcutItem span{padding: 5px 15px 5px 0px;}
</style>
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
					<div id="newreturn">
						<!-- setup -->
						<div id="setup">
							<form action="/returnorder/tonext" method="post"
								id="returnorderForm">
								<h4>退换货申请新建</h4>
								<div>
									<table class="returnorder_new" style="">
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
										
										<!-- 
										<tr>
											<td align="right">原订单号：</td>
											<td><input type="hidden" name="originalorder"
												value="${order.id}"> ${order.id}</td>
											<td align="right">外部交易单号：</td>
											<td>${order.outerId }</td>
											<td align="right">运费：</td>
											<td id="deliveryFee">${order.deliveryFee } 元</td>
											<td align="right">支付方式：</td>
											<td><c:forEach var="payment"
													items="${order.paymentList }">
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
										
										-->
										<!--数据输入 -->
										<tr>
											<td align="right">退换货类型：</td>
											<td><select name="type" id="type">
													<c:forEach var="type" items="${types.availableChildren}">
														<option value="${type.id }">${type.name}</option>
													</c:forEach>
											</select>
											</td>
											<td align="right">责任方：</td>
											<td><select name="responsible" id="responsible">
													<c:forEach var="responsibleOrholder"
														items="${responsibleOrholders.availableChildren}">
														<option value="${responsibleOrholder.id }">${responsibleOrholder.name}</option>
													</c:forEach>
											</select>
											</td>
											<td align="right">承担方：</td>
											<td ><select name="holder" id="holder">
													<c:forEach var="responsibleOrholder"
														items="${responsibleOrholders.availableChildren}">
														<option value="${responsibleOrholder.id }">${responsibleOrholder.name}</option>
													</c:forEach>
											</select>
											</td>
											<td align="right"><span class="fhyf">应退运费合计：</span></td>
											<td><span class="fhyf">
											<input class="short" id="refunddeliveryfee" class='refunddeliveryfee'
												type="text" name="refunddeliveryfee" value="5">
												
												</span>
											</td>
										</tr>
										<tr>
											<td align="right">退换货原因：</td>
											<td><select name="reason" id="reason">
													<c:forEach var="reason" items="${reasons.availableChildren}">
														<option value="${reason.id }">${reason.name}</option>
													</c:forEach>
											</select>
											</td>
											<td align="right">退换货方式：</td>
											<td><select name="pickup" id="pickup">
													<c:forEach var="pickup" items="${pickups.availableChildren}">
														<option value="${pickup.id }">${pickup.name}</option>
													</c:forEach>
											</select>
											</td>
											<td align="right"><label id="refundcompensating_title">付退金额：</label>
											</td>
											<td><input class="short" id="refundcompensating"
												type="text" name="refundcompensating" value="0"></td>
											<td align="right"><label id="refundcouponvalue_title">付退礼券：</label>
											</td>
											<td><select name="refundcouponvalue"
												id="refundcouponvalue">
													<option value="0.00">请选择</option>
													<c:forEach var="presentValue" items="${presentValues}">
														<option value="${presentValue }">${presentValue}
															元</option>
													</c:forEach>
											</select>
											</td>
										</tr>
										<tr>
											<td align="right"><label id="returnorder_tag">是否原包非整退：</label></td>
											<td>
												<select name="originalreturned" id="originalreturned">
													<option value="570002">是</option>
													<option value="570001" selected="selected">否</option>
												</select>
											</td>
											<td align="right">备注：</td>
											<td colspan="7"><textarea id="remark" class="textareawh"
													rows="1" cols="1" name="remark"></textarea>
											</td>
											<td></td>
											<td></td>
										</tr>
									</table>
								</div>
								<!-- 
								<div><a href="javascript:;" id="addProduct">添加商品</a></div>
								 -->
								 
								 <div class="next">
									<button type="button" id="checkForm">下一步</button>
								</div>
								<!-- 商品信息  -->
								<div id="product">
									<div><input type="button" id="addProduct" value="添加商品"></input></div>
									<table class="list-table"  id="dataTable" style="">
										<tr>
											<th>订单号</th>
											<th>注册名</th>
											<th>收货人</th>
											<th>商品编号</th>
											<th>商品名</th>
											<th>码洋</th>
											<th>可退量</th>
											<th>退换数<span id="productCount" style="color:red"></span></th>
											<th>操作</th>
										</tr>
										<tbody id="producttable">
											
										</tbody>
									</table>
								</div>
								
								<div id="orders">
									<div><input type="button" id="addOrder" value="添加订单"/></div>
									<table class="list-table"  id="ordersTable">
										<tr>
											<th>订单号</th>
											<th>结果</th>
											<th>新订单</th>
											<th>操作</th>
										</tr>
										<tbody id="orderData">
										</tbody>
									</table>
								</div>
								
							</form>
						</div>
					</div>
			</div>
		</div>
	</div>
	
	<div id="productSelect">
		<div id="findproduct">
			<span>
				<select id="findtype">
					<option value="barcode">ISBN</option>
					<option value="product">商品编码</option>
					<option value="owncode">SAP编码</option>
					<option value="merchid">商品主数据编码</option>
			</select> <input type="text" name="name" id="findtypevalue" value="">
			</span> <span>注册名:<input type="text" name="name" id="findname">
			</span> <span>收货人:<input type="text" name="consignee"
				id="findconsignee"> </span> <span>是否下传中启:<input type="radio"
				name="transferResult" value="35002" checked="checked">是 <input
				type="radio" name="transferResult" value="35003">否</span> <br>
			<span>时间: <input type="radio" name="Date1" value="1"
				onclick="clickRadio(this,1)" class="day" />最近一天 <input type="radio"
				name="Date1" value="2" onclick="clickRadio(this,2)" class="week" />最近一周
				<input type="radio" name="Date1" value="3"
				onclick="clickRadio(this,3)" class="month" />最近一月 
				<input type="radio" name="Date1" value="4"
				onclick="clickRadio(this,4)" class="month" />最近三月<input
				class="starttime" name="startCreateTime" width="10px"
				style="width: 100px"> ~<input class="endtime"
				name="endCreateTime" style="width: 100px"> </span> <br> <span>
				<input type="button" value="查询" id="findProductBtn"> <input
				type="button" value="插入" id="insertbtn"> </span>
		</div>
		<div id="showCount"></div>
		<div id = "showProdcutItem"></div>
		<div id = "showOrderItems">
			<table border = 1>
				<tr>
					<th>订单号</th>
					<th>注册名</th>
					<th>收货人</th>
					<th>卖家</th>
					<th>发货量</th>
					<th>已退量</th>
					<th>可退量</th>
					<th>退换货数量</th>
					<th>是否下传中启</th>
					<th>发货DC</th>
				</tr>
				<tbody id="showOrderItemsTable">
				</tbody>
			</table>
		</div>
	</div>
	
	<div id="orderPanel">
		<p>订单号以回车/空格/逗号(,) 进行分割</p>
		<div>
			<textarea rows="10" cols="40" style="width:700px;height:100px;" id="ordersList"></textarea>
		</div>
		<input type="button" value="插入" id="insertOrder">
	</div>
	
	
	<div id="collectPanel">
		<div>
			<table id="showCollect">
				<tr>
					<th>商品编码</th>
					<th>商品名</th>
					<th>码洋</th>
					<th>退换数</th>
				</tr>
				<tbody  id="collect">
				</tbody>
			</table>
			<div id="collectMsg"></div>
			
		</div>
		<div><input type="button" value="提交" id="submitBtn"></div>
	</div>
	
	<input type="hidden" value="${fn:length(returnOrders)}" id="TreturnOrdersSize">
	<input type="hidden" value="${zeroreturn}" id="zeroreturn">
	<input type="hidden" value="${order.deliveryFee }" id="TdeliveryFee"/>
	<%@include file="../snippets/scripts.jsp"%>
	<script src="${pageContext.request.contextPath}/js/returnorder/returnorderBatch.js"></script>
</body>
</html>
