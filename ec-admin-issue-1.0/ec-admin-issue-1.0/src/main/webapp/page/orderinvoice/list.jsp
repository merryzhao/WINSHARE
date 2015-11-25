<%@page pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
#cltextarea {
	float: left;
	width: 190px;
	height: 48px;
	font-size: 13px;
}
</style>
<%@include file="../snippets/meta.jsp"%>
<title>发票查询</title>
</head>
<body>
	<!-- 引入JS -->
	<%@include file="../snippets/scripts.jsp"%>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>发票查询</h4>
				<!-- 查询表单div -->
				<div id="queryForm">
					<form class="inline" action="/orderinvoice/search" method="post">
						<table style="border: none;">
							<tr>
								<td><select class="cl_leftfloat" name="searchType">
										<option value="orderId">订单号</option>
										<option value="invoiceId">发票订单号</option>
										<option value="outerId">外部订单号</option>
										<option value="name">注册名</option>
								</select> <textarea id="cltextarea" name="information"></textarea>
									<button type="submit">检 索</button> <a href=""
									id="showCondition"> 显示条件 </a><span id="condition"> <input
										name="condition" type="checkbox" value="processstatus" />订单状态
										<input type="checkbox" name="condition" value="deliverycode" />寄单号
										<input type="checkbox" name="condition" value="needinvoice" />是否需要发票
								</span></td>
							</tr>
							<tr>
								<td>发货日期<input type="radio" value="oneday" name="time" />最近一天<input
									type="radio" name="time" value="oneweek" />最近一周<input
									type="radio" name="time" value="onemonth" />最近一月 <a href=""
									id="othertime">其他时间</a> <span id="timeslot">开始时间：<input
										bind='datepicker' name="starttime" />结束时间：<input
										bind='datepicker' name="endtime" /> </span></td>
							</tr>
						</table>
					</form>
				</div>
				<div>
					<c:if test="${pagination!=null}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<c:if test="${listInvoice!=null}">
						<form action="" id="form" method="get">
							<table class="list-table">
								<tr>
									<th>发票订单号</th>
									<th>订单号</th>
									<th>外部订单号</th>
									<th>注册名</th>
									<th>下单日期</th>
									<th>运费</th>
									<th>发票金额</th>
									<th>操作人</th>
									<th>开票日期</th>
									<th>开票次数</th>
									<c:if test="${processstatus!=null}">
										<th>订单状态</th>
									</c:if>
									<c:if test="${deliverycode!=null}">
										<th>寄单号</th>
									</c:if>
									<c:if test="${needinvoice!=null}">
										<th>是否需要发票</th>
									</c:if>
									<th>补开发票</th>
									<th>详细</th>
								</tr>

								<c:if test="${fn:length(listInvoice)!=0}">
									<c:forEach items="${listInvoice}" var="dto">
										<tr>
											<td>${dto.orderInvoice.id}</td>
											<td><a href="/order/${dto.order.id}" target="content">${dto.order.id}</a>
											</td>
											<td>${dto.order.outerId }</td>
											<td>${dto.order.customer.name }</td>
											<td><fmt:formatDate value="${dto.order.createTime}" />
											</td>
											<td>${dto.order.deliveryFee }</td>
											<td>${dto.orderInvoice.money }</td>
											<td>${dto.orderInvoice.operator.name }</td>
											<td><fmt:formatDate
													value="${dto.orderInvoice.createTime }" /></td>
											<td>${fn:length(dto.order.invoiceList) }</td>
											<c:if test="${processstatus!=null}">
												<td>${dto.order.processStatus.name }</td>
											</c:if>
											<c:if test="${deliverycode!=null}">
												<td>${dto.order.deliveryCode }</td>
											</c:if>
											<c:if test="${needinvoice!=null}">
												<td><c:choose>
														<c:when test="${dto.order.consignee.needInvoice }">需要</c:when>
														<c:otherwise>不需要</c:otherwise>
													</c:choose>
												</td>
											</c:if>
											<td> <c:if test="${dto.order.processStatus.id==8004}">
													<a href="/orderinvoice/invoice/${dto.order.id}">补开发票</a>
												</c:if> <c:if test="${dto.order.processStatus.id==8005}">
													<a href="/orderinvoice/invoice/${dto.order.id}">补开发票</a>
												</c:if> <c:if test="${dto.order.processStatus.id==8011}">
													<a href="/orderinvoice/invoice/${dto.order.id}">补开发票</a>
												</c:if>
											</td>
											<td><a
												href="/orderinvoice/orderinvoice_list?orderid=${dto.order.id}">详细</a>
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${fn:length(listInvoice)==0}">
									<tr>
										<td colspan="12">暂无数据</td>
									</tr>
								</c:if>
								<tr>
									<th>发票订单号</th>
									<th>订单号</th>
									<th>外部订单号</th>
									<th>注册名</th>
									<th>下单日期</th>
									<th>运费</th>
									<th>发票金额</th>
									<th>操作人</th>
									<th>开票日期</th>
									<th>开票次数</th>
									<c:if test="${processstatus!=null}">
										<th>订单状态</th>
									</c:if>
									<c:if test="${deliverycode!=null}">
										<th>寄单号</th>
									</c:if>
									<c:if test="${needinvoice!=null}">
										<th>是否需要发票</th>
									</c:if>
									<th>补开发票</th>
									<th>详细</th>
								</tr>
							</table>
						</form>
					</c:if>
					<c:if test="${pagination!=null}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
				</div>
			</div>
		</div>
		<script type="text/javascript"
			src="${contextPath}/js/order/orderInvoice.js"></script>
		<script type="text/javascript"
			src="${contextPath}/js/order/orderAuditBatch.js"></script>
		<script type="text/javascript">
			jQuery(function($){
				var v = $("#timeslot");
				v.hide();
				$("#othertime").toggle(function(){
					v.show();
				},function(){v.hide();});
				
				v.click(function(){
					$("input[name='time']:checked").attr("checked",false);
				});
				$("input[name='time']").click(function(){
					$("input[name='starttime']").val("");
					$("input[name='endtime']").val("");
				});
				
			});
			$(function(){
				$("#condition").hide();
				$("#showCondition").toggle(function(){
					$("#condition").show();
				},function(){
					$("#condition").hide();
				});
			})
			</script>
	</div>
</body>
</html>