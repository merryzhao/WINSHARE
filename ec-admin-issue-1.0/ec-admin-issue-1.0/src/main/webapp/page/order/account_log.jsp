<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div id="content-result">
	<c:if test="${ !empty presentLogs}">
	<div class="showinfo">
		<center>
			<h4>礼券</h4>
			<table class="list-table">
				<tr>
					<th>礼券编号</th>
					<th>面值</th>
					<th>实际消费</th>
					<th>状态</th>
					<th>时间</th>
				</tr>
				<c:forEach items="${presentLogs }" var="presentLog">
				    <tr>
					<td>${presentLog.present.id }</td>
					<td>${presentLog.present.value }</td>
					<td>
						<c:if test="${presentLog.state.id==17003 }">${presentLog.present.value }</c:if>
						<c:if test="${presentLog.state.id!=17003 }">-${presentLog.present.realPay }</c:if>
					</td>
					<td>${presentLog.state.name }</td>
					<td><fmt:formatDate type="both" value="${presentLog.updateTime }"></fmt:formatDate></td>
					</tr>
				</c:forEach>
			</table>
		</center>
	</div>
	</c:if>
	
	<c:if test="${ !empty presentCardDealLogList}">
	<div class="showinfo">
		<center>
			<h4>礼品卡</h4>
			<table class="list-table">
				<tr>
					<th>礼品卡编号</th>
					<th>余额</th>
					<th>实际消费</th>
					<th>类型</th>
					<th>时间</th>
				</tr>
				<c:forEach items="${presentCardDealLogList}" var="presentCardDealLog">
					<tr>
						<td>${presentCardDealLog.presentCard.id}</td>
						<td>${presentCardDealLog.currentBalance}</td>
						<td>${presentCardDealLog.dealMoney}</td>
						<td>${presentCardDealLog.presentCard.type.name}</td>
						<td><fmt:formatDate type="both" value="${presentCardDealLog.createDate}"></fmt:formatDate>
						</td>
					</tr>
				</c:forEach>
			</table>
		</center>
	</div>
	</c:if>
	
	<c:if test="${!empty accountDetailList}">
	<div class="showinfo">
		<center>
			<h4><a href="/customer/advanceaccountQuery">暂存款</a></h4>
			<table class="list-table">
				<tr>
					<th>时间</th>
					<th>支出/收入金额</th>
					<th>类型</th>
					<th>余额</th>
				</tr>
				<c:forEach items="${accountDetailList}" var="detail">
					<tr>
						<td><fmt:formatDate type="both" value="${detail.useTime}"></fmt:formatDate>
						</td>
						<td>${detail.amount}</td>
						<td>${detail.type.name}</td>
						<td>${detail.balance}</td>
					</tr>
				</c:forEach>
			</table>
		</center>
	</div>
	</c:if>
	
	<div class="showinfo">
		<center>
			<h4>其他支付方式</h4>
			<table class="list-table">
				<tr>
					<th>支付方式</th>
					<th>到款金额</th>
					<th>外部订单号</th>
					<th>支付人</th>
					<th>处理人</th>
					<th>实际到款时间</th>
					<th>处理时间</th>
				</tr>
				<c:forEach items="${order.paymentList}" var="paymentList">
				<c:if test="${paymentList.payment.id!=15&&paymentList.payment.id!=20&&paymentList.payment.id!=21}">
					<tr>
						<td>${paymentList.credential.payment.name}</td>
						<td>${paymentList.credential.money}</td>
						<td>${paymentList.credential.outerId}</td>
						<td>${paymentList.credential.payer}</td>
						<td>${paymentList.credential.operator.name}</td>
						<td><fmt:formatDate type="date"
								value="${paymentList.credential.payTime}"></fmt:formatDate></td>
						<td><fmt:formatDate type="date"
								value="${paymentList.credential.payTime}"></fmt:formatDate></td>
					</tr>
					</c:if>
				</c:forEach>
			</table>
		</center>
	</div>
	<c:if test="${!empty refundCredentialList }">
	
	<div class="showinfo">
		<center>
			<h4>原卡原退</h4>
			<table class="list-table">
				<tr>
					<th>退款方式</th>
					<th>退款金额</th>
					<th>外部交易号</th>
					<th>申请退款时间</th>
					<th>实际退款时间</th>
					<th>状态</th>
					<th>处理人</th>
				</tr>
				<c:forEach items="${refundCredentialList}" var="refundCredential">
					<tr>
						<td>${refundCredential.payment.name}</td>
						<td>${refundCredential.money}</td>
						<td>${refundCredential.outerId}</td>
						<td>${refundCredential.createTime}</td>
						<td>${refundCredential.refundTime }</td>
						<td>${refundCredential.status.name}</td>
						<td>${refundCredential.operator.name }</td>
					</tr>
				</c:forEach>
			</table>
		</center>
	</div>
	</c:if>
	
	
</div>
