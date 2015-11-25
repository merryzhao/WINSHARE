<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>退换货订单查询</title>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/returnorder/returnorder.css"
	rel="stylesheet" />
	<link type="text/css" href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" />
</head>
<body onload="">
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<!-- 退换货订单查询 -->
				<div>
					<h4>退换货订单查询</h4>
					<!--查询条件 -->
					<div>
						<form action="/returnorder/list" method="post"
							id="returnorder_query_form">
							<div>
								订单来源：
								<button type="button" id="add_channel" >添加渠道</button><span id="channelChecked"></span>
							</div>
							<!--查询条件 1-->
							<div>
								<select name="idType">
									<option value="orderId"
										<c:if test="${form.idType=='orderId'}">selected="selected"</c:if>>订单号</option>
									<option value="returnOrderId"
										<c:if test="${form.idType=='returnOrderId'}">selected="selected"</c:if>>退换货单号</option>
									<option value="outerId"
										<c:if test="${form.idType=='outerId'}">selected="selected"</c:if>>外部交易号</option>
								</select>
								<textarea class="query" name="ids">${form.ids }</textarea>
								<button class="sumbit" type="submit">查询</button>
							</div>
							<!--查询条件 2 -->
							<div class="queryBuilder2">
								<label>收货人：</label><input class="long" type="text"
									name="consignee" value="${form.consignee }"> <label>创建人：</label><input
									class="long" type="text" name="creator"
									value="${form.creator }"> <label>类型：</label> <select
									name="type">
									<option>请选择</option>
									<c:forEach var="type" items="${types.children}">
										<option value="${type.id }"
											<c:if test="${form.type==type.id}">selected="selected"</c:if>>${type.name
											}</option>
									</c:forEach>
								</select> <label>状态：</label> <select name="status">
									<option>请选择</option>
									<c:forEach var="state" items="${status.children}">
										<option value="${state.id }"
											<c:if test="${form.status==state.id}">selected="selected"</c:if>>${state.name
											}</option>
									</c:forEach>
								</select>
							</div>
							<!--查询条件 3-->
							<div>
								退货DC信息：
								<c:forEach var="returnDc" items="${dcList}">
									<input name="returnDc" id="returnDc" type="checkbox" value="${returnDc.id}" />${returnDc.name}
								</c:forEach>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								收货DC信息：
								<c:forEach var="receiveDc" items="${dcList}">
									<input name="receiveDc" id="receiveDc" type="checkbox" value="${receiveDc.id}" />${receiveDc.name}
								</c:forEach>
							</div>
							<div>
								<select name="timeType">
									<option value="CreateTime"
										<c:if test="${form.timeType=='CreateTime'}">selected="selected"</c:if>>退换货订单创建时间</option>
									<option value="DeliveryTime"
										<c:if test="${form.timeType=='DeliveryTime'}">selected="selected"</c:if>>原订单发货时间</option>
								</select> <input type="radio" name="time" value="1" onclick="getTime(1);">最近一天
								<input type="radio" name="time" value="2" onclick="getTime(2);">最近一周
								<input type="radio" name="time" value="3" onclick="getTime(3);">最近一月
								<input type="radio" name="time" value="4" id="time4" onclick="getTime(4);">最近三月
								<label>其它时间：</label> 
								<input id="startDate" class="long"  style="width: 120px" type="text" name="startDate" bind="datetimepicker" value="${form.startDate}" onclick="noChecked()"> <label>至</label>
								<input id="endDate" class="long" style="width: 120px" type="text" name="endDate" bind="datetimepicker" value="${form.endDate}" onclick="noChecked()">								
							</div>
						</form>
					</div>
					<br>
					<!-- 查询结果 -->
					<div>
						<c:if test="${pagination!=null}">
							<button type="button" onclick="audit(0,1);">批量审核</button>
							<button type="button" onclick="refund(0);">批量标记退款</button>
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
							<div>
								<form action="" method="post" id="returnorderForm">
									<table class="list-table" style="width: 1200px;">
										<tr>
											<th><input type="checkbox" name="selectA">
											</th>
											<th>退换货订单</th>
											<th>退换货订单状态</th>
											<th>类型</th>
											<th>订单号</th>
											<th>发货时间</th>
											<th>创建时间</th>
											<th>退换货数量</th>
											<th>退换货金额</th>
											<th>退换货运费</th>
											<th>渠道</th>
											<th>所属卖家</th>
										</tr>
										<c:forEach var="returnOrder" items="${returnOrders}">
											<tr>
												<td align="center"><input type="checkbox"
													name="returnOrderIds" value="${returnOrder.id }">
												</td>
												<td><a href="/returnorder/${returnOrder.id }/detail">${returnOrder.id
														}</a>
												</td>
												<td>${returnOrder.status.name }</td>
												<td>${returnOrder.type.name }</td>
												<td><a href="/order/${returnOrder.originalOrder.id}">${returnOrder.originalOrder.id
														}</a>
												</td>
												<td><fmt:formatDate
														value="${returnOrder.originalOrder.deliveryTime }"
														type="date" />
												</td>
												<td><fmt:formatDate value="${returnOrder.createTime }"
														type="date" />
												</td>
												<td><c:set value="0" var="count"></c:set> <c:forEach
														var="itemList" items="${returnOrder.itemList}">
														<c:set value="${itemList.appQuantity + count }"
															var="count"></c:set>
													</c:forEach> ${count }</td>
												<td>${returnOrder.refundGoodsValue }</td>
												<td>${returnOrder.refundDeliveryFee }</td>
												<td>${returnOrder.originalOrder.channel.name}</td>
												<td>${returnOrder.originalOrder.shop.shopName }</td>
											</tr>
										</c:forEach>
										<c:if test="${pagination.count>0}">
											<tr>
												<th><input type="checkbox" name="selectB">
												</th>
												<th>退换货订单</th>
												<th>订单状态</th>
												<th>类型</th>
												<th>订单号</th>
												<th>发货时间</th>
												<th>创建时间</th>
												<th>退换货数量</th>
												<th>退换货金额</th>
												<th>退换货运费</th>
												<th>渠道</th>
												<th>所属卖家</th>
											</tr>
										</c:if>
									</table>
								</form>
							</div>
							<c:if test="${pagination.count>0}">
								<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
								<button type="button" onclick="audit(0,1);">批量审核</button>
								<button type="button" onclick="refund(0);">批量标记退款</button>
							</c:if>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="channelDiv">
		<ul id="channel_tree" class="tree"></ul>
		<br />
		<button type=button onclick="insertNodes()" id=getChecktree>确定</button>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${contextPath}/js/tree/channel_tree.js"></script>	
	<script type="text/javascript" src="/js/jquery.ztree-2.6.min.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/returnorder/returnorder.js"></script>
		<script type="text/javascript">
		
		$(function(){
			if($("#startDate").val() == ""){
				$("#time4").click();
			}
		});
	

		$(document).ready(function(){
			$("input[bind='datetimepicker']").datetimepicker({
				regional:"zh-CN"	
			});	
			
			$("input[bind='datepicker']").datepicker({
				regional:"zh-CN"	
			});	
			
			$("input[bind='mindatemepicker']").datetimepicker({
				minDate: new Date(),
				regional:"zh-CN"
			});	
			
			$("input[bind='mindate']").datepicker({
				minDate: new Date(),
				regional:"zh-CN"
			});	
			
			$("input[bind='greaterstart']").datepicker({
				minDate: $("input[bind='mindate']").val(),
				regional:"zh-CN"
			});	
		 
			
			// 弹出式初始化渠道树
			$("#channelDiv").dialog({
				autoOpen : false,
				bgiframe : true,
				modal : true,
				width : 350
			});
			
			$("#add_channel").click(function(){
				$("#channelDiv").dialog("open");
			});
			
			$( "#accordion" ).accordion({
				collapsible: true
			}).accordion( "activate" , 1) ;
			
		});
	</script> 
</body>
</html>
