<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>代理商家信息查询</title>
<style type="text/css">
input.codingContent {
	margin-left: 50px;
}

input.sellerName {
	margin-left: 50px;
}

input.productAuthor {
	margin-left: 25px;
}

label.moretrem {
	margin-left: 25px;
	color: #5CACEE;
}

div.moreterm {
	display: none;
}
</style>
<!-- 引入JS -->
</head>
<body>
	<div class="frame">
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-channel.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>代理商信息管理:</h4>
				<!-- 查询表单div -->
				<div id="queryForm">
					<form class="inline" action="/agent/agentorder" method="post">
						<table>
							<tr>
								<td><span>用户账号 ：</span></td>
								<td><textarea style="width: 200px; height: 60px;"
										id="userName" name="userName"></textarea></td>
								<td align="left"><span style="padding-left: 10px;"><input
										type="submit" id="cx" value="检索"
										style="width: 60px; height: 30px;" />
								</span></td>
							</tr>
							<tr>
								<td colspan="3">下单开始时间：<input id="startDate" type="text"
									name="startDate" readonly="readonly" bind="datepicker" />
									下单结束时间：<input id="endDate" type="text" name="endDate"
									readonly="readonly" bind="datepicker" /><span
									style="padding-left: 10px;"><input type="reset"
										value="重置" style="width: 60px; height: 30px;">
								</span>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div>
					<c:if test="${fn:length(orders)!=0}">
						<input style="width: 60px; height: 30px;" type="button" value="导出"
							id="exButton" />
					</c:if>
					<c:if test="${fn:length(orders)==0}">
						<input style="width: 60px; height: 30px;" type="button" value="导出"
							disabled="disabled" />
					</c:if>
					<br />
					<c:if test="${pagination!=null}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<form action="">
						<table class="list-table" id="datatable">
							<tr>
								<th><input type="checkbox" class="selectAll" /></th>
								<th>订单号</th>
								<th>下单时间</th>
								<th>代理商账号</th>
								<th>商品实洋比例</th>
								<th>发货数量</th>
								<th>订单有效金额</th>
							</tr>
							<c:if test="${fn:length(orders)!=0}">

								<c:forEach var="order" items="${orders}">
									<tr>
										<td><input type="checkbox" name="orderid"
											value="${order.id}" /></td>
										<td><a href="/order/${order.id}">${order.id}</a></td>
										<td><fmt:formatDate value="${order.createTime}"
												type="both" />
										</td>
										<td>${order.customer.name}</td>
										<td>${customer.discount}</td>
										<td>${order.deliveryQuantity}</td>
										<td>${order.effiveMoney}</td>
									</tr>
								</c:forEach>

							</c:if>
							<c:if test="${fn:length(orders)==0}">
								<tr>
									<td align="center" colspan="12">暂无数据</td>
								</tr>
							</c:if>

							<tr>
								<th><input type="checkbox" class="selectAll"/></th>
								<th>订单号</th>
								<th>下单时间</th>
								<th>代理商账号</th>
								<th>商品实洋比例</th>
								<th>发货数量</th>
								<th>订单有效金额</th>
							</tr>
						</table>
					</form>
					<c:if test="${pagination!=null&&pagination.pageCount!=0}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
				</div>

			</div>
		</div>
		<div class="loading">数据处理中... 请稍候</div>
	</div>
	<form action="/excel/exagentinfo" target="_blank" method="post" name="exForm" id="exForm">
 		<input type="hidden" value="${userName}" id="exids" name="exids"/>
 		<input type="hidden" value="${startDate}" id="exstartDate" name="exstartDate"/>
 		<input type="hidden" value="${endDate}" id="exendDate" name="exendDate"/>
  		<input type='hidden' name='format' value='xls' />
  		<input type='hidden' name='actionType' id="actionType" value='all' />							    
 	</form>
 <%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript" src="${contextPath}/js/agent/agent.js"></script>
</body>
</html>