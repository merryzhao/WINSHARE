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
				<!-- 抽单对账系统 -->
				<div>
					<h4>账单查询</h4>
					<form action="/ordersettle/listBills" method="post" id="billorder_form">
						<input type="text" name="billId" id="bill_search_id" <c:if test="${billId==null}"> value="请输入账单号"</c:if><c:if test="${billId!=null}"> value="${billId }"</c:if> onfocus="if(this.value=='请输入账单号'){this.value=''}" onblur="if(this.value==''){this.value='请输入账单号'}"/>
						<input type="text" name="list" id="bill_search_list" <c:if test="${list==null||list==''}"> value="请输入清单"</c:if><c:if test="${list!=null&&list!=''}"> value="${list }"</c:if> onfocus="if(this.value=='请输入清单'){this.value=''}" onblur="if(this.value==''){this.value='请输入清单'}"/>
						<input type="text" name="invoice" id="bill_search_invoice" <c:if test="${invoice==null||invoice==''}"> value="请输入发票"</c:if><c:if test="${invoice!=null&&invoice!=''}"> value="${invoice }"</c:if> onfocus="if(this.value=='请输入发票'){this.value=''}" onblur="if(this.value==''){this.value='请输入发票'}"/>
						<input type="text" name="accountName" id="bill_search_accountname" <c:if test="${accountName==null}"> value="请输入结算账户名"</c:if><c:if test="${accountName!=null}"> value="${accountName }"</c:if> onfocus="if(this.value=='请输入结算账户名'){this.value=''}" onblur="if(this.value==''){this.value='请输入结算账户名'}"/>
						<br/>
						开始时间：<input type="text" name="startDate" size="18" value="${startDate}"  bind="datepicker">
						截止时间：<input type="text" name="endDate" size="18" value="${endDate}"  bind="datepicker">
					</form>
					<button type="button" onclick="searchBill();">查询</button>
				</div>
				<c:if test="${bills!=null&&fn:length(bills)>0 }">
					<div>
						<table class="list-table">
							<tr>
								<th>账单号</th>
								<th>创建时间</th>
								<th>状态</th>
								<th>清单号</th>
								<th>发票</th>
								<th>结算账户</th>
								<th>渠道查看</th>
								<th>分配金额</th>
								<th>结算金额</th>
								<th>退货金额</th>
								<th>本次余额</th>
								<th>上次余额</th>
								<th>容差</th>
							</tr>
							<c:forEach var="billObj" items="${bills }">
								<tr>
									<td><a href="/ordersettle/viewBill?billId=${billObj.id }" target="_blank">${billObj.id }</a></td>
									<td>${billObj.createtime }</td>
									<td>${billObj.status.name }</td>
									<td>${billObj.list }</td>
									<td>${billObj.invoice }</td>
									<td>${billObj.billAccount.name }</td>
									<td>
										<a href="#" onclick="$('#bill_channels_list').toggle();return false;">查看</a>
										<div id="bill_channels_list" class="ui-dialog-content ui-widget-content" style="display: none; z-index: 1002; outline: 0px none; position: absolute; height: auto;">
											<ul>
												<c:forEach var="channelItem" items="${billObj.channels }"><li>${channelItem.name }</li></c:forEach>
											</ul>
										</div>
									</td>
									<td>${billObj.allotment }</td>
									<td>${billObj.settlement }</td>
									<td>${billObj.refound }</td>
									<td>${billObj.balance }</td>
									<td>${billObj.lastBalance }</td>
									<td>${billObj.billAccount.tolerance }</td>
								</tr>
							</c:forEach>
						</table>
						<c:if test="${pagination.count>0}">
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
						</c:if>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/ordersettle/billchannel.js"></script>
</body>
</html>
