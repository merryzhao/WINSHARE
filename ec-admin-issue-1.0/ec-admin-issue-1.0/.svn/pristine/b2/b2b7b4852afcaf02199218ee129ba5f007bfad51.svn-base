<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>提现申请详情</title>
<style>
 .info-lable{
  padding:0 0 0 15px; 
}

.oinfo-lable{
  padding:0 15px 0 0; 
}

.showinfo{
   border:2px solid #DFDFDF;
   margin-top: 30px;
}
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
<div class="showinfo">
	<h4>提现申请详细</h4>
	<table class="list-table">
		<tr>
			<td align="left">注册名<label class="info-lable">${customerCashApply.customer.name}</label>
			</td>
			<td align="left">申请日期<label class="info-lable"><fmt:formatDate type="both" value="${customerCashApply.createTime}"/></label>
			</td>
			<td align="left">状态<label class="info-lable" ><span id="status">${customerCashApply.status.name}</span></label>
			</td>
			<td align="left">退款方式<label class="info-lable">${customerCashApply.type.name}</label>
			</td>
		</tr>
		<tr>
			<td align="left">提现金额<label class="info-lable">${customerCashApply.money}</label>
			</td>
			<td align="left">文轩手续费<label class="info-lable">${customerCashApply.companyPayFee}</label>
			</td>
			<td align="left" colspan="2">顾客手续费<label class="info-lable">${customerCashApply.customerPayFee}</label>
			</td>
		</tr>
	</table>
</div>
<c:if test="${customerCashApply.type.id==43003}">
<div class="showinfo">
	<h4>银行转账</h4>
	<table class="list-table">
		<tr>
			<td align="left">银行名称 <label class="info-lable" title="">${customerCashApply.bankAccount.bank.name}</label>
			</td>
			<td align="left">所在支行<label class="info-lable"></label>
			</td>
			<td align="left">卡主姓名<label class="info-lable">${customerCashApply.bankAccount.bankAccountName}</label>
			</td>
		</tr>
		<tr>
			<td align="left">银行卡号 <label class="info-lable">${customerCashApply.bankAccount.bankAccountCode}</label>
			</td>
			<td align="left">联系电话<label class="info-lable">${customerCashApply.customer.mobile}</label></td>
 		</tr>
	</table>
</div>
</c:if>
<c:if test="${customerCashApply.type.id==43004}">
<div class="showinfo">
	<h4>邮局汇款</h4>
	<table class="list-table">
		<tr>
			<td align="left">收款人姓名 <label class="info-lable" title="">${customerCashApply.postAccount.postAccountName}</label>
			</td>
			<td align="left">邮编<label class="info-lable"><span>${customerCashApply.postAccount.postCode}</span></label>
			</td>
			<td align="left">联系电话<label class="info-lable"><span>${customerCashApply.customer.mobile}</span></label></td>
		</tr>
		<tr>
			<td align="left" colspan="3">地区<label class="info-lable">${customerCashApply.postAccount.postCountry.name}&nbsp;${customerCashApply.postAccount.postProvince.name}&nbsp;${customerCashApply.postAccount.postCity.name}&nbsp;${customerCashApply.postAccount.postDistrict.name}</label>
			</td>
		</tr>
		<tr>
			<td align="left" colspan="3">街道/村庄 <label class="info-lable">${customerCashApply.postAccount.postAddress}</label>
			</td>
		</tr>
	</table>
</div>
</c:if>
<c:if test="${customerCashApply.type.id==43001}">
<div class="showinfo">
	<h4>支付宝</h4>
	<table class="list-table">
		<tr>
			<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;支付宝账号 <label class="info-lable" title="">${customerCashApply.alipay}</label></td>
		</tr>
		<tr> 
			<td align="left">支付宝认证姓名 <label class="info-lable" title="">${customerCashApply.alipayName}</label></td>
		</tr>
	</table>
</div>
</c:if>
<div class="showinfo">
	<h4>处理日志</h4>
	<table class="list-table">
		<tr>
			<th>处理状态</th>
			<th>处理时间</th>
			<th>操作人</th>
			<th>回填流水号</th>
			<th>备注</th>
 		</tr>
		<c:forEach var="log" items="${customerCashApply.updateLogList}" varStatus="index">

			<tr>
 				<td>${log.status.name}</td>
				<td><fmt:formatDate type="both" value="${log.operateTime}"/></td>
				<td>${log.operator.name}</td>
				<td>${log.customerCashApply.id}</td>
				<td>${log.remark}</td>
 			</tr>
		</c:forEach>
	</table>
</div>
<br/>
<div>
    
</div>
<br/>
<div>
	<c:if test="${customerCashApply.status.id==44001}">
		<input type="button" value="办理" id="processButton"/>
	</c:if>
	<c:if test="${customerCashApply.status.id==44002}">
		<input type="button" value="回填" id="returnButton"/>
	</c:if>
	<c:if test="${customerCashApply.status.id==44002}">
		<c:if test="${customerCashApply.type.id==43004}">
			<input type="button" value="打印"/>
		</c:if>
	</c:if>
	<c:if test="${customerCashApply.status.id!=44003&&customerCashApply.status.id!=44004}">
		<input type="button" value="撤回" id="cancelButton"/>
	</c:if>
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
 <input type="hidden" id="id" name="id" value="${customerCashApply.id}">
  <%@include file="../snippets/scripts.jsp"%>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/customer/balance_return_info.js"></script>
 </body>
 </html>