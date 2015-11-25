<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>暂存款提现</title>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/returnorder/returnorder.css"
	rel="stylesheet" />
	<style type="text/css">
		table.page{width:1200px;}
	</style>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<!-- 退换货订单查询 -->
				<div>
					<h4>暂存款提现查询办理</h4>
					<!--查询条件 -->
					<div>
						<form action="/customer/findinfo" method="post"  id="queryForm"
							id="returnorder_query_form">
							<!--查询条件 1-->
							<div>
							   时间： <input type="radio" name="time" value="1" onclick="getTime(1);">最近一天
								<input type="radio" name="time" value="2" onclick="getTime(2);">最近一周
								<input type="radio" name="time" value="3" onclick="getTime(3);">最近一月
								<label>其它时间：</label> <input id="startDate" class="long" readonly="readonly"
									type="text" name="startDate" bind="datepicker"
									value="${startDate}" /> <label>至</label>
								<input id="endDate" class="long" type="text" name="endDate" readonly="readonly"
									bind="datepicker" value="${endDate}"/>
 								<a id="clearnTime" href="javascript:void(0)">清空时间</a>
							   <input style="width: 80px;" type="submit" value="查询">
							</div>
							<!--查询条件2 -->
							<div class="queryBuilder2">
								<label>注册名：</label><input class="long" type="text" value="${customer}"
									name="customer" id="customer" /><label style="padding-left: 8px;">
									申请状态：</label> 
									<select name="status">
										<option value="-1">请选择</option>
										<c:forEach items="${statuses}" var="s">
											<c:if test="${s.id==status}">
												<option selected="selected" value="${s.id}">${s.name}</option>
											</c:if>
											<c:if test="${s.id!=status}">
												<option value="${s.id}">${s.name}</option>
											</c:if>
										</c:forEach>
									 </select>
 									<label style="padding-left: 8px;"> 退款方式：</label> <select name="type">
										<option value="-1">请选择</option>
									<c:forEach items="${types}" var="t">
									<c:if test="${t.id==type}">
										<option selected="selected" value="${t.id}">${t.name}</option>
									</c:if>
									<c:if test="${t.id!=type}">
										<option value="${t.id}">${t.name}</option>
									</c:if>
									</c:forEach>
								</select>
								 <label style="padding-left: 8px;">提现编号：</label><input id="code" name="code"
								value="${code}"	class="long" type="text" />
							</div>
						</form>
						<br/>
 						<div class="queryBuilder3">
 						<form action="/excel/exbankifno" target="_blank" method="post" name="exForm" id="exForm">
 						 							<input type="hidden" id="exids" name="exids"/>
  												    <input type='hidden' name='format' value='xls' />
  												    <input type='hidden' name='actionType' id="actionType" value='xls' />
  												    
 						</form>
 						 <form action="/excel/exbankifno" target="_blank" method="post" name="printForm" id="printForm">
 						 							<input type="hidden" id="exids" name="exids"/>
  												    <input type='hidden' name='format' value='xls' />
  												    <input type='hidden' name='actionType' id="actionType" value='xls' />
  												    
 						</form>
 						 <form action="/excel/exrefundifno" target="_blank" method="post"  id="refundForm">
 						 							<input type="hidden" id="exrefundids" name="exids"/>
  												    <input type='hidden' name='format' value='xls' />					    
 						</form>
 						<form action="/customer/successcashall" target="blank" method="post" id="fileForm" enctype="multipart/form-data">
 							<input type="button" value="导出银行转账信息" id="exButton">
 							<input type="button" value="导出支付宝账户" id="exApplyButton">
 							<input type="button" value="导出退款信息" id="exRefundButton">
 							<input type="button" value="批量办理" id="processButton">
 							<input type="file" name="fileName" id="fileName" >
 							<input type="button" value="批量回填" id="fileButton">
 							<input type="button" value="批量撤回" id="cancelButton">
 						</form>
 						</div>
					</div>
					<br>
					<!-- 查询结果 -->
					<div>
						<c:if test="${pagination!=null}">
  							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
  					     </c:if>
							<div>
 									<table class="list-table" id="datatable" style="width:1200px;">
										<tr>
											<th><input type="checkbox" id="selectAll" name="selectAll"></th>
											<th>提现编号</th>
											<th>申请日期</th>
											<th>注册名</th>
											<th>提现金额</th>
 											<th>退款方式</th>
											<th>申请状态</th>
											<th>操作</th>
										</tr>
									 	<c:forEach var="c" items="${customerCashApplys}">
											<tr>
												<td><input type="checkbox" name="ids"
													value="${c.id}"></td>
												<td><a href="/customer/returninfo/${c.id }">${c.id}</a></td>
												<td><fmt:formatDate type="date" value="${c.createTime}"/></td>
												<td>${c.customer.name }</td> 
												<td>${c.money}</td>
 												<td>${c.type.name}</td>
												<td class="statuscss">${c.status.name}</td>
												<td><input type="hidden" value="${c.id}" class="cid"/>
												    <input type="hidden" class="statusId" value="${c.status.id}">
												    <input type="hidden" class="type" value="${c.type.id}">
												<a href="javascript:void(0)" class="back">回填</a>
												<a class="print" style="padding-left: 8px;" href="javascript:void(0)">打印</a>
												</td>
											</tr>
										</c:forEach>  
									</table>
 							</div>
 					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="backDiv">
	<center>
	 <table>
	 <tr>
	 	<td>提现编号：<span id="ccode"></span></td>
	</tr>
	<tr>
	 	<td>交易编号：<input type="text" id="tradeNo" name="tradeNo"/> </td>
	</tr>
	<tr>
	 	<td>备注信息：<input type="text" id="mark" name="mark"/> </td>
	</tr>
	<tr>
	 	<td align="center"><input style="width: 80px;" type="button" id="sbm" name="sbm" value="提交"/> </td>
	 </tr>
	 </table>
	 </center>
	</div>
	<iframe name="blank" style="display: none;"></iframe>
	 <input type="hidden" id="cids" name="cids"/>
 	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/customer/balance_return_query.js"></script>
</body>
</html>
