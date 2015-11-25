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
				<c:if test="${order==null }">
					<div>
						<form action="/returnorder/new" method="get">
							<label>订单号：</label><input type="text" name="id" value="${orderId}">
							<button type="submit">退换货</button>
							<c:if test="${error==1}">
						    <label class="errorstyle"> 此订单当前的状态，不能退换货。</label>
						    </c:if>
						    <c:if test="${error==0}">
						    <label class="errorstyle"> 未找到此订单，或者您输入的订单号不正确。</label>
						    </c:if>
						</form>
					</div>
					<div>
						<form action="/returnorder/productReturnUpload" id="excelForm"  method="post" enctype="multipart/form-data">
							<label>商品导入（.xls）<a href="javascript:void(0);" onclick="$('#exForm').submit();">模板下载</a>：</label>
						    <input type="file" name="fileName" id="fileName">
						    <input type="button" value="上传" id="sbm">
						    <c:if test="${message==1 }">
						    	<span style="color:green">上传成功！</span>
						    </c:if>
						</form>
						<form action="/excel/exportReturnProductTemplate" target="_blank" method="post" name="exForm" id="exForm">
   							<input type='hidden' name='format' value='xls' />
  			    		</form>
					</div>
				</c:if>
				<c:if test="${order!=null }">
					<!-- 退换货新建 -->
					<div id="newreturn">
						<!-- setup -->
						<div id="setup">
							<form action="/returnorder/tonext" method="post"
								id="returnorderForm">
								<!-- 图片 -->
								<div>
									<img class="img_width"
										src="${pageContext.request.contextPath}/imgs/returnorder/setup.jpg">
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
											<td align="right">发货仓：</td>
											<td>${order.distributionCenter.dcOriginal.name }</td>
											<td align="right">区域：</td>
											<td>${order.consignee.district.name }</td>
											<td align="right">详细地址：</td>
											<td colspan="4">${order.consignee.address }</td>
										</tr>
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
											<input class="short" id="refunddeliveryfee"
												type="text" name="refunddeliveryfee" value="0">
												
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
											<td align="right" id="expressid_title_td"><label id="expressid_title">退货运单号：</label></td>
											<td id="expressid_td">
												<input type="text" id="expressid" name="expressid" value="${expressid }">
												<input type="text" name="packageid" hidden="hidden" value="${packageid }"/>
											</td>
											<td align="right" id="targetDc_title_td"><label id="targetDc_title">退货仓：</label></td>
											<td id="targetDc_td">
												<select name="targetDc" id="targetDc">
													<c:forEach var="target" items="${targetDcs }">
														<option value="${target.id }" ${target.id == order.distributionCenter.dcOriginal.id?'selected':'' }>${target.name }</option>
													</c:forEach>
												</select>
											</td>
											<td align="right"><label id="refundcompensating_title">付退金额：</label>
											</td>
											<td><input class="short" id="refundcompensating"
												type="text" name="refundcompensating"></td>
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
											<td colspan="6"><textarea id="remark" class="textareawh"
													rows="1" cols="1" name="remark"></textarea>
											</td>
										</tr>
									</table>
								</div>
								
								<!-- 商品信息  -->
								<div id="product">
									<table class="list-table"  id="dataTable" style="width:1200px;">
										<tr>
											<th><input type="checkbox" name="selectAll">
											</th>
											<th>商品编号</th>
											<th>供应商</th>
											<th>商品名</th>
											<th>码洋</th>
											<th>实洋</th>
											<th>发货数量</th>
											<th>已退货数量</th>
											<th>发货金额</th>
											<th>退换货数量</th>
											<th>暂存库位</th>
										</tr>
										<c:forEach var="orderItem" items="${order.itemList }">
										  <c:if test="${orderItem.deliveryQuantity>0}">
											<tr>
												<td>
													<c:choose>
														<c:when test="${orderItem.productSale.remark!=null&&orderItem.productSale.remark!=''&&orderItem.productSale.remark!=0}">
															<input type="checkbox" name="item" checked="checked" value="${orderItem.id }">
														</c:when>
														<c:otherwise>
															<input type="checkbox" name="item" value="${orderItem.id }">
														</c:otherwise>
													</c:choose>
												</td>
												<td class="proId">${orderItem.productSale.id }</td>
												<td>${orderItem.productSale.product.vendor }</td>
												<td class="proName">${orderItem.productSale.product.name }</td>
												<td>${orderItem.listPrice }</td>
												<td>${orderItem.salePrice }</td>
												<td>${orderItem.deliveryQuantity }<input type="hidden"
													name="deliveryQuantity"
													value="${orderItem.deliveryQuantity}"></td>
												<td class="returnQuantity">${orderItem.returnQuantity }</td>
												<td>${orderItem.salePrice*orderItem.deliveryQuantity }</td>
												<c:if test="${empty packageItemList }">
													<td>
														<input class="short" type="text" bind="itemcount" name="itemcount1"
																value="${orderItem.productSale.remark}">
													</td>
													<td>
														<input type="text" name="locations" value="" placeholder="请手工填写暂存库位.." />
													</td>
												</c:if>
												<c:if test="${!empty packageItemList }">
													<td><input class="short" type="text" bind="itemcount" name="itemcount1"
															<c:forEach var="packageItem" items="${packageItemList }">
																<c:if test="${packageItem.eccode == orderItem.productSale.id }">
																	<c:if test="${packageItem.quantity-packageItem.dealQuantity <= orderItem.deliveryQuantity-orderItem.returnQuantity }">
																	<%-- <td><input class="short" type="text" bind="itemcount" name="itemcount1"
																		value="${packageItem.quantity-packageItem.dealQuantity}"></td> --%>
																		value="${packageItem.quantity-packageItem.dealQuantity}"
																	</c:if>
																	<c:if test="${packageItem.quantity-packageItem.dealQuantity > orderItem.deliveryQuantity-orderItem.returnQuantity }">
																	<%-- <td><input class="short" type="text" bind="itemcount" name="itemcount1"
																		value="${orderItem.deliveryQuantity-orderItem.returnQuantity}"></td> --%>
																		value="${orderItem.deliveryQuantity-orderItem.returnQuantity}"
																	</c:if>
																</c:if>
															</c:forEach>
														/>
													</td>
													<td>
														<input type="text" name="locations" placeholder="匹配不成功，请手工填写.." 
															<c:forEach var="packageItem" items="${packageItemList }">
																<c:if test="${packageItem.eccode == orderItem.productSale.id }">
																	value="${packageItem.location }"
																</c:if>
															</c:forEach>
														/>
													</td>		
													<%-- <c:forEach var="packageItem" items="${packageItemList }">
														<c:if test="${packageItem.eccode == orderItem.productSale.id }">
															<c:if test="${packageItem.quantity-packageItem.dealQuantity <= orderItem.deliveryQuantity-orderItem.returnQuantity }">
															<td><input class="short" type="text" bind="itemcount" name="itemcount1"
																value="${packageItem.quantity-packageItem.dealQuantity}"></td>
																value="${packageItem.quantity-packageItem.dealQuantity}"
															</c:if>
															<c:if test="${packageItem.quantity-packageItem.dealQuantity > orderItem.deliveryQuantity-orderItem.returnQuantity }">
															<td><input class="short" type="text" bind="itemcount" name="itemcount1"
																value="${orderItem.deliveryQuantity-orderItem.returnQuantity}"></td>
																value="${orderItem.deliveryQuantity-orderItem.returnQuantity}"
															</c:if>
														</c:if>
													</c:forEach> --%>
												</c:if>
											</tr>
										  </c:if>
										</c:forEach>
									</table>
								</div>
								
								<div id="returnOrder">
									<table class="list-table"  id="dataTable" style="width:1200px;">
										<tr>
											<th>退换货编码</th>
											<th>类型</th>
											<th>商品名/已退数量</th>
											<th>责任方</th>
											<th>承担方</th>
											<th>备注</th>
										</tr>
										<c:set var="zeroreturn" value="true" scope="page"/>
										<c:forEach var="ro" items="${returnOrders}">
											<c:if test="${ro.type.id == 24001 && zeroreturn}">
												<c:set var="zeroreturn" value="false"/>
											</c:if>
											<tr>
												<td class="proId"><a href="/returnorder/${ro.id}/detail" target="_blank">${ro.id }</a></td>
												<td>${ro.type.name}</td>
												<td>
													<c:forEach items="${ro.itemList}" var="il">
														<div>${il.orderItem.productSale.product.name} : ${il.realQuantity}</div>
													</c:forEach>
												</td>
												<td>${ro.responsible.name}</td>
												<td>${ro.holder.name}</td>
												<td>${ro.remark}</td>
											</tr>
										</c:forEach>
									</table>
								</div>
								
								<div class="next">
									<button type="button" onclick="submitForm();">下一步</button>
								</div>
							</form>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	
	<input type="hidden" value="${fn:length(returnOrders)}" id="TreturnOrdersSize">
	<input type="hidden" value="${zeroreturn}" id="zeroreturn">
	<input type="hidden" value="${order.deliveryFee }" id="TdeliveryFee"/>
	<%@include file="../snippets/scripts.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/returnorder/returnorder.js"></script>
</body>
</html>
