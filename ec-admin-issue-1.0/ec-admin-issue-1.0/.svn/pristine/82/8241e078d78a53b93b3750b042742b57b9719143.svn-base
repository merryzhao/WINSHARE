<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>退换货订单详细信息</title>
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
				<div id="requestInfo" align="center"
					style="font-size: 15px; font-weight: bold;"></div>
				<div id="tabs">
					<ul>
						<li><a href="#returnOrder_detail">退换货信息${returnOrder.id }</a>
						</li>
						<li><a href="#returnOrder_status_log">退换货状态日志</a></li>
						<li><a href="#returnOrder_track">退换货跟踪(<label
								id="trackSize">${fn:length(returnOrder.trackList)}</label>)</a></li>
					</ul>
					<!-- **************************退换货详细***************************** -->
					<div id="returnOrder_detail">
						<h4>退换货申请订单</h4>
						<div>
							<table class="returnorder_new">
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
									<td><a href="/order/${returnOrder.originalOrder.id}">${returnOrder.originalOrder.id}</a></td>
									<td align="right">外部交易单号：</td>
									<td>${returnOrder.originalOrder.outerId }</td>
									<td align="right">新订单号：</td>
									<td><a href ="/order/${returnOrder.newOrder.id }" target="_blank">${returnOrder.newOrder.id }</a></td>
									<td align="right">退换货订单状态：</td>
									<td>${returnOrder.status.name}</td>
								</tr>
								<tr>
									<td align="right">运费：</td>
									<td id="deliveryFee">${returnOrder.originalOrder.deliveryFee } 元</td>
									<td align="right">支付方式：</td>
									<td><c:forEach var="payment"
											items="${returnOrder.originalOrder.paymentList }">
									${payment.payment.name },
									</c:forEach>
									</td>

									<td align="right">配送公司：</td>
									<td>${returnOrder.originalOrder.deliveryCompany.company}</td>
									<td align="right">发货时间：</td>
									<td>
									<fmt:formatDate value="${returnOrder.originalOrder.deliveryTime }" type="both" />
									</td>
								</tr>
								<tr>

									<td align="right">礼券支付：</td>
									<td><c:set var="present" value="0.00"></c:set> <c:forEach
											var="orderPayment"
											items="${returnOrder.originalOrder.paymentList}">
											<c:if test="${orderPayment.payment.id==20}">
												<c:set var="present"
													value="${present+orderPayment.deliveryMoney}"></c:set>
											</c:if>
										</c:forEach> ${present} 元</td>
									<td align="right">礼品卡支付</td>
									<td><c:set var="presentCard" value="0.00"></c:set> <c:forEach
											var="orderPayment"
											items="${returnOrder.originalOrder.paymentList}">
											<c:if test="${orderPayment.payment.id==21}">
												<c:set var="presentCard"
													value="${presentCard+orderPayment.deliveryMoney}"></c:set>
											</c:if>
										</c:forEach> ${presentCard} 元</td>

									<td align="right">区域：</td>
									<td>
										<c:choose>
											<c:when test="${returnOrder.originalOrder.consignee.country.name != returnOrder.originalOrder.consignee.province.name }">
												${returnOrder.originalOrder.consignee.province.name }
												${returnOrder.originalOrder.consignee.city.name }
												${returnOrder.originalOrder.consignee.district.name }
												${returnOrder.originalOrder.consignee.town.name }</c:when>
											<c:otherwise>
												${returnOrder.originalOrder.consignee.country.name }
											</c:otherwise>
										</c:choose></td>
									<td align="right">姓名：</td>
									<td>${returnOrder.originalOrder.consignee.consignee}</td>
								</tr>
								<tr>
									<td align="right">电话：</td>
									<td>${returnOrder.originalOrder.consignee.phone}</td>
									<td align="right">手机：</td>
									<td >${returnOrder.originalOrder.consignee.mobile}</td>
									<td align="right">详细地址：</td>
									<td colspan="4">${returnOrder.originalOrder.consignee.address
										}</td>
								</tr>
								<tr>
									<td align="right">退换货类型：</td>
									<td>${returnOrder.type.name}</td>
									<td align="right" >责任方：</td>
									<td id="responsiblename">${returnOrder.responsible.name} <c:if
											test="${returnOrder.status.id==25001}">
											<img src="/imgs/returnorder/edit.jpg"
												onclick="showUpdate('select','责任方：','responsibleSelect','responsible','${returnOrder.responsible.id}');" />
										</c:if></td>
									<td align="right" >承担方：</td>
									<td id="holdername">${returnOrder.holder.name} <c:if
											test="${returnOrder.status.id==25001}">
											<img src="/imgs/returnorder/edit.jpg"
												onclick="showUpdate('select','承担方：','holderSelect','holder','${returnOrder.holder.id}');" />
										</c:if></td>
									<td align="right">应退运费合计：</td>
									<td>${returnOrder.refundDeliveryFee} 元 <c:if
											test="${returnOrder.status.id==25001 && returnOrder.type.id==24001}">
											<img src="/imgs/returnorder/edit.jpg"
												onclick="showUpdate('input','发货运费退款：','inputbox','refundDeliveryFee','${returnOrder.refundDeliveryFee}');" />
										</c:if></td>
								</tr>
								<tr>
									<td align="right">退换货原因(后台)：</td>
									<td>${returnOrder.reason.name} <c:if
											test="${returnOrder.status.id==25001}">
											<img src="/imgs/returnorder/edit.jpg"
												onclick="showUpdate('select','退换货原因：','reasonSelect','reason','${returnOrder.reason.id}');" />
										</c:if></td>
									<td align="right">退换货方式：</td>
									<td>${returnOrder.pickup.name} <c:if
											test="${returnOrder.status.id==25001}">
											<img src="/imgs/returnorder/edit.jpg"
												onclick="showUpdate('select','退换货方式：','pickupSelect','pickup','${returnOrder.pickup.id}');" />
										</c:if></td>
									<c:if test="${returnOrder.type.id==24003 || returnOrder.type.id==24004}">
									<td align="right">付退金额：</td>
									<td>${returnOrder.refundCompensating} 元 <c:if
											test="${returnOrder.status.id==25001}">
											<img src="/imgs/returnorder/edit.jpg"
												onclick="showUpdate('input','付退金额：','inputbox','refundCompensating','${returnOrder.refundCompensating}');" />
										</c:if></td>
									<td align="right">付退礼券：</td>
									<td>${returnOrder.refundCouponValue} 元 <c:if
											test="${returnOrder.status.id==25001}">
											<img src="/imgs/returnorder/edit.jpg"
												onclick="showUpdate('select','付退礼券：','refundcouponvalueSelect','refundCouponValue','${returnOrder.refundCouponValue}');" />
										</c:if></td>
									</c:if>	
								</tr>
								<tr>
									<c:if test="${not empty returnOrderTagList }">
										<td align="right">退货标签：</td>
										<td>
											<c:forEach var="returnOrderTag" items="${returnOrderTagList }" varStatus="status">
												<c:if test="${!status.last }"><span style="background-color: ${returnOrderTag.tag.description }">${returnOrderTag.tag.name }</span>,</c:if>
												<c:if test="${status.last }"><span style="background-color: ${returnOrderTag.tag.description }">${returnOrderTag.tag.name }</span></c:if>
											</c:forEach>
										</td>
									</c:if>
									<td align="right">退货DC：</td>
									<td>
										${returnOrder.returnOrderDc.targetDc.name}
									</td>
									<td align="right">收货DC：</td>
									<td>
										<c:if test="${returnOrder.returnOrderDc.targetRealDc != null}">
											${returnOrder.returnOrderDc.targetRealDc.name}
										</c:if>
									</td>
									<td align="right">备注：</td>
									<td colspan="7">${returnOrder.remark}</td>
								</tr>
							</table>
						</div>
						<c:if test="${!(returnOrder.type.id ==24003 || returnOrder.type.id ==24004)}">
							<!-- 商品信息  -->
							<div>
								<table id="orderProduct" class="list-table">
									<tr>
										<th>商品编号</th>
										<th>供应商</th>
										<th>商品名</th>
										<th>码洋</th>
										<th>实洋</th>
										<th>实际退书款</th>
										<th>发货数量</th>
										<th>发货金额</th>
										<th>退换货数量</th>
										<th>实退换货数量</th>
										<th>退换货原因（前台）</th>
									</tr>
									<c:forEach var="item" items="${returnOrder.itemList}">
										<tr>
											<td>${item.orderItem.productSale.product.id }</td>
											<td>${item.orderItem.productSale.product.vendor }</td>
											<td>${item.orderItem.productSale.product.name }</td>
											<td>${item.orderItem.listPrice }</td>
											<td>${item.orderItem.salePrice }</td>
											<td>${item.orderItem.balancePrice }</td>
											<td>${item.orderItem.deliveryQuantity }</td>
											<td>${item.orderItem.salePrice*item.orderItem.deliveryQuantity
												}</td>
											<td>${item.appQuantity} <c:if
													test="${returnOrder.status.id==25001}">
													<img src="/imgs/returnorder/edit.jpg"
														onclick="showUpdate('input','退换货数量：','inputbox','appQuantity','${item.id}-${item.appQuantity}-${item.orderItem.deliveryQuantity}');" />
												</c:if></td>
											<td>${item.realQuantity}</td>
											<td>${item.reason.name}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>
						<c:if test="${returnOrder.status.id == 25006}">
							<div>
								不通过原因:${returnOrder.cause}<br/>
							</div>
						</c:if>
						<br> <br>
						<!-- 功能处理按钮 -->
						<div>
						    <!-- 所有状态为已提交的退换货订单  显示  审核 -->
							<c:if test="${returnOrder.status.id==25001 }">
								不通过原因:<textarea id="cause" style="width:250px; height:80px;"></textarea><br/>
								<button type="button" onclick="audit(${returnOrder.id },1);">审核通过
								</button>
								<button type="button" onclick="notAudit(${returnOrder.id },0);">审核不通过
								</button>
							</c:if>
							<!-- 标记为不下传中启的订单, 可接受后续的操作 -->
							<c:if test="${returnOrder.originalOrder.transferResult.id == 35003 || returnOrder.originalOrder.transferResult.id==35005 }">
								 <!--所有状态为已审核且非补偿的退换货订单  显示  下传中启-->
								<c:if test="${returnOrder.type.id !=24003&&returnOrder.status.id==25002}">
								<button type="button" onclick="downloadZQ(${returnOrder.id })">等待收货</button>
								</c:if>
								<!--所有状态为正在退货且非补偿的退换货订单   显示 回传EC  -->
								<c:if test="${returnOrder.type.id !=24003&&returnOrder.status.id==25003}">
								<button type="button" onclick="passBackECShow();">实物入库</button>
								</c:if>
								<!-- 标记退款 -->
								<c:if
									test="${(returnOrder.type.id ==24003&&returnOrder.status.id==25002)
									||(returnOrder.type.id ==24001&&returnOrder.status.id==25004) }">
									<button type="button" onclick="refund(${returnOrder.id })">标记退款
									</button>
								</c:if> 
							</c:if>
							<!-- 标记为下传中启，且已回传EC，状态为实物入库 的货到付款（直销，代理，联盟）退货单，由财务手动判断是否退款 -->
							<c:if test="${returnOrder.transferred == true && returnOrder.originalOrder.transferResult.id == 35002
									&& returnOrder.originalOrder.payType.id == 5002 
									&& (returnOrder.originalOrder.channel.parent.id == 700 || returnOrder.originalOrder.channel.parent.id == 1001) 
							 }">
								<c:if test="${returnOrder.shouldrefund.id == 580002 && returnOrder.status.id==25004 && returnOrder.type.id ==24001 }">
									<span style="color:red">注：该退货单为货到付款（直销，代理，联盟）退货单，由财务手动判断是否退款！</span>
									<button type="button" onclick="refund(${returnOrder.id })">退款</button>
									<button type="button" onclick="notRefund(${returnOrder.id })">不退款</button>
								</c:if>
							</c:if>
						</div>
					</div>
					<!-- ************************************************************ -->
					<!-- ********************  退换货状态日志     **************************** -->
					<div id="returnOrder_status_log">
						<h4>退换货状态日志</h4>
						<br>
						<table class="list-table">
							<tr>
								<th>日志编号</th>
								<th>状态</th>
								<th>变更时间</th>
								<th>操作人</th>
							</tr>
							<c:forEach var="log" items="${returnOrder.logList}">
								<tr>
									<td>${log.id }</td>
									<td>${log.status.name }</td>
									<td><fmt:formatDate value="${log.logTime }" type="both" />
									</td>
									<td>${log.operator.name }</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<!-- ************************************************************ -->
					<!-- ********************  退换货跟踪     **************************** -->
					<div id="returnOrder_track">
						<h4>退换货跟踪</h4>
						<br>
						<button type="button" onclick="showTrack();">新建退换货跟踪</button>
						<table class="list-table">
							<tr>
								<th>跟踪编号</th>
								<th>退换货类型</th>
								<th>创建时间</th>
								<th>操作人</th>
								<th>内容</th>
							</tr>
							<c:forEach var="returnOrderTrack"
								items="${returnOrder.trackList}">
								<tr>
									<td>${returnOrderTrack.id }</td>
									<td>${returnOrderTrack.type.name }</td>
									<td><fmt:formatDate
											value="${returnOrderTrack.createTime }" type="both" /></td>
									<td>${returnOrderTrack.operator.name }</td>
									<td>${returnOrderTrack.content }</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<!-- ************************************************************ -->
				</div>
				<div id="newTrack">
					<form action="/returnorder/newTrack" method="post"
						id="newTrackForm">
						<input type="hidden" name="id" value="${returnOrder.id }">
						<label>跟踪类型</label> <select name="trackType">
							<c:forEach var="returnOrderTrackType"
								items="${returnOrderTrackTypes.children }">
								<option value="${returnOrderTrackType.id}">${returnOrderTrackType.name}</option>
							</c:forEach>
						</select> <br> <label>跟踪备注</label> <input type="text"
							name="trackRemark"> <br>
						<button type="button"
							onclick="newTrack('/returnorder/newTrack','newTrackForm','returnOrder_track')">提交</button>
					</form>
				</div>
				<!-- 以下是修改数据 -->
				<div>
					<!-- 表单 -->
					<div id="updateDiv">
						<form action="" id="updateForm">
							<div id="updateDivContent" class="updateContent"></div>
							<div id="updateName">
								<input name="name" type="hidden">
							</div>
							<input type="hidden" name="format" value="json"> <input
								type="hidden" name="id" value="${returnOrder.id }">
							<div class="updateSubmit">
								<button type="button" onclick="update();">修改</button>
								<button type="button" onclick="closeUpdate();">取消</button>
							</div>
						</form>
					</div>
					<!-- 显示替换 -->
					<div class="none">
						<!-- 输入框 -->
						<div id="inputbox">
							<label></label> <input id="inputValue" name="value" type="text">
						</div>
						<!-- 责任方 -->
						<div id="responsibleSelect">
							<label></label> <select name="value" id="responsible">
								<c:forEach var="responsibleOrholder"
									items="${responsibleOrholders.children}">
									<option value="${responsibleOrholder.id }">${responsibleOrholder.name}</option>
								</c:forEach>
							</select>
						</div>
						<!-- 承担方 -->
						<div id="holderSelect">
							<label></label> <select name="value" id="holder">
								<c:forEach var="responsibleOrholder"
									items="${responsibleOrholders.children}">
									<option value="${responsibleOrholder.id }">${responsibleOrholder.name}</option>
								</c:forEach>
							</select>
						</div>
						<!-- 退换货原因 -->
						<div id="reasonSelect">
							<label></label> <select name="value" id="reason">
								<c:forEach var="reason" items="${reasons.children}">
									<option value="${reason.id }">${reason.name}</option>
								</c:forEach>
							</select>
						</div>
						<!-- 退换货方式 -->
						<div id="pickupSelect">
							<label></label> <select name="value" id="pickup">
								<c:forEach var="pickup" items="${pickups.children}">
									<option value="${pickup.id }">${pickup.name}</option>
								</c:forEach>
							</select>
						</div>
						<!-- 付退礼券 -->
						<div id="refundcouponvalueSelect">
							<label></label> <select name="value">
								<option value="0.00">请选择</option>
								<c:forEach var="presentValue" items="${presentValues}">
									<option value="${presentValue }">${presentValue} 元</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<!-- 回传EC时设置实际退货数量 -->
				<div id="passBackEC_DIV">
				<h4>回传EC实际退换货数量设置</h4>
				<br>
				<form action="" id="passBackEC_FORM">
							<table class="list-table" id="dataTable">
									<tr>
										<th>商品编号</th>
										<th>商品名</th>
										<th>应退换货数量</th>
										<th>实际退换货数量</th>
									</tr>
									<c:forEach var="item" items="${returnOrder.itemList}">
										<tr>
											<td class="proId">${item.orderItem.productSale.id }
											<input type="hidden" name="returnOrderItem" value="${item.id}">
											</td>
											<td class="proName">${item.orderItem.productSale.product.name }</td>
											<td class="appQuantity">${item.appQuantity}</td>
											<td><input class="short" type="text" name="realQuantity" value="0"  
											onkeyup="this.value=this.value.replace(/\D/g,'')" ></td>
										</tr>
									</c:forEach>
								</table>
								<div class="updateSubmit"><button type="button" onclick="passBackEC(${returnOrder.id },'passBackEC_FORM')">确定</button> 
								<button type="button" onclick="passBackECClose()">取消</button></div>
							</form>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/returnorder/returnorder.js"></script>
</body>
</html>